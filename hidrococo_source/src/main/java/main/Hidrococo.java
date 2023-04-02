package main;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class Hidrococo {
	public static final String MYSQL_DRIVER_CLASS_NAME = "com.mysql.cj.jdbc.Driver";
	private Connection connection;
	
	private SimpleDateFormat monthFormatter = new SimpleDateFormat("mm-yyyy");
	
	public void ejecutar(String[] args) {
		if (args.length < 1) {
			System.out.println("No se ha introducido comando");
			imprimirAyuda();
			return;
		}
		
		try {
			procesarArgumentos(args);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			desconectar();
		}
	}
	
	private void procesarArgumentos(String[] args) throws ClassNotFoundException, SQLException {
		String comando = args[0].toLowerCase();
		switch (comando) {
		case "lista":
			conectar();
			lista();
			break;
		case "reporte":
			conectar();
			reporte(args);
			break;
		case "ayuda":
			imprimirAyuda();
			break;
		default:
			System.out.printf("Comando '%s' desconocido\n", comando);
			imprimirAyuda();
			break;
		}
	}

	private void conectar() throws ClassNotFoundException, SQLException {
		connection = getMySQLConnection();
	}

	private void desconectar() {
		if (connection != null) {
			try {
				connection.close();
			} catch (SQLException e) {}
		}
	}
	
	public Connection getMySQLConnection() throws ClassNotFoundException, SQLException {
		return getMySQLConnection("localhost:3306", "startcode", "startcode");
	}
	
	public Connection getMySQLConnection(String databaseLocation, String user, String password) throws ClassNotFoundException, SQLException {
		Class.forName(MYSQL_DRIVER_CLASS_NAME);
		String connectionString = String.format("jdbc:mysql://%s", databaseLocation);
		return DriverManager.getConnection(connectionString, user, password);
	}
	
	private void lista() throws SQLException {
		List<Central> centrales = leerCentrales();
		String pattern = String.format("%%%dd. %%s\n", (int) Math.log10(centrales.size() - 1) + 1);
		for (Central central : centrales) {
			System.out.printf(pattern, central.getId(), central.getNombre());
		}
	}
	
	private void reporte(String[] args) throws SQLException {
		if (args.length < 2) {
			System.out.println("No se ha introducido un id de central");
			return;
		}
		
		int idCentral = Integer.parseInt(args[1]);
		Central central = leerCentral(idCentral);
		if (central == null) {
			System.out.printf("No existe central con id '%s'\n", idCentral);
			return;
		}
		
		Embalse embalse = leerEmbalse(central.getEmbalse());
		if (embalse == null) {
			System.out.printf("No existe embalse asociado a la central '%s'\n", idCentral);
			return;
		}
		
		LocalDate now = LocalDate.now();
		String mes = monthFormatter.format(now);
		float[] previsiones = leerPrevisionesEmbalseEnElMes(embalse.getId(), now);
		if (previsiones == null) {
			System.out.printf("No existen previsiones para la central '%s' en el mes %s\n", 
					idCentral, mes);
			return;
		}
		
		float volumenActualHm3 = previsiones[0];
		float previsionConsumoM3 = previsiones[1];
		float previsionPrecipitacionMmPorM2 = previsiones[2];
		
		float precipitacionEnMetros = previsionPrecipitacionMmPorM2 / 1000;
		float areaEnM2 = embalse.getAreaEnHm2() * 10000;
		float llenadoPorPrecipitacionEnHm3 = (precipitacionEnMetros * areaEnM2) / 1000000;
		
		float aportacionMensualEnHm3 = embalse.getAportacionMediaAnualEnHm3() / 12;
		float consumoEnHm3 = previsionConsumoM3 / 1000000;
		float previsionLlenadoEnHm3 = llenadoPorPrecipitacionEnHm3 + aportacionMensualEnHm3;

		float transvaseNecesario = consumoEnHm3 + Math.max(0, 
				volumenActualHm3 + previsionLlenadoEnHm3 - embalse.getLimiteSuperiorEnHm3());
		float transvasePosible = Math.max(transvaseNecesario, 
				volumenActualHm3 + previsionLlenadoEnHm3 - embalse.getLimiteInferiorEnHm3());

		System.out.printf("Embalse: %s\n"
				+ "Mes: %s\n"
				+ "Volumen actual: %s hm3\n"
				+ "Límites: %s - %s hm3\n"
				+ "Prevision consumo (uso obligatorio): %s m3\n"
				+ "Previsión llenado: %.2d hm3\n"
				+ "Transvase\n"
				+ "    Necesario: %.2d hm3\n"
				+ "    Disponible: %.2d hm3\n",
				
				embalse.getNombre(), mes, volumenActualHm3, embalse.getLimiteInferiorEnHm3(), 
				embalse.getLimiteSuperiorEnHm3(), previsionConsumoM3, previsionLlenadoEnHm3,
				transvaseNecesario, transvasePosible
		);
	}

	private float[] leerPrevisionesEmbalseEnElMes(int idEmbalse, LocalDate fechaMes) throws SQLException {
		String sql = "SELECT * FROM previsiones_embalses WHERE embalse = ? AND fecha_mes"
				+ "BETWEEN ? AND ?";
		String mesInicio = monthFormatter.format(fechaMes) + "-01";
		String mesFin = monthFormatter.format(fechaMes) + "-31";
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, idEmbalse);
			statement.setString(2, mesInicio);
			statement.setString(3, mesFin);

			ResultSet results = statement.executeQuery();
			
			if (results.next()) {
				float volumenActual = results.getFloat(3);
				float previsionConsumo = results.getFloat(4);
				float previsionPrecipitacion = results.getFloat(5);
				return new float[] {volumenActual, previsionConsumo, previsionPrecipitacion};
			}
		}
		
		return null;
	}
	
 	private List<Central> leerCentrales() throws SQLException {
		List<Central> centrales = new ArrayList<>();
		String sql = "SELECT * FROM centrales";
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet results = statement.executeQuery();
			
			while (results.next()) {
				Central central = leerCentralDeResultSet(results);
				centrales.add(central);
			}
		}
		
		return centrales;
	}
	
 	private Central leerCentral(int id) throws SQLException {
		String sql = "SELECT * FROM centrales WHERE id = ?";
		
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			statement.setInt(1, id);
			
			ResultSet results = statement.executeQuery();
			
			if (results.next()) {
				return leerCentralDeResultSet(results);
			}
		}
		
		return null;
 	}
 	
	public Central leerCentralDeResultSet(ResultSet results) throws SQLException {
		int id = results.getInt(1);
		String nombre = results.getString(2);
		String ubicacion = results.getString(3);
		float potencia_instalada_en_mw = results.getFloat(4);
		float latitud = results.getFloat(5);
		float longitud = results.getFloat(6);
		int idEmbalse = results.getInt(7);
		return new Central(id, nombre, ubicacion, potencia_instalada_en_mw, latitud, longitud, idEmbalse);
	}
	
	private Embalse leerEmbalse(int id) throws SQLException {
		String sql = "SELECT * FROM embalses WHERE id = ?";
		Embalse embalse = null;
		try (PreparedStatement statement = connection.prepareStatement(sql)) {
			ResultSet results = statement.executeQuery();
			if (results.next()) {
				embalse = leerEmbalseDeResultSet(results);
			}
		}
		return embalse;
	}

	private Embalse leerEmbalseDeResultSet(ResultSet results) throws SQLException {
		int id = results.getInt(1);
		String nombre = results.getString(2);
		float areaEnHm2 = results.getFloat(3);
		float limiteInferiorEnHm3 = results.getFloat(4);
		float limiteSuperiorEnHm3 = results.getFloat(4);
		float aportacionMediaAnualEnHm3 = results.getFloat(5);
		return new Embalse(id, nombre, areaEnHm2, limiteInferiorEnHm3, limiteSuperiorEnHm3, aportacionMediaAnualEnHm3);
	}
	
	private void imprimirAyuda() {
		System.out.println("Forma de uso: programa <argumento> [opciones]\n\n"
				+ "Argumentos:\n"
				+ "    lista\n"
				+ "        imprime una lista de las centrales con sus ids.\n\n"
				+ "    reporte <id central> [mes]\n"
				+ "        imprime información sobre la central, su estado y la estimación del\n"
				+ "        volumen de agua que puede ser usada para la generación de energía.\n"
				+ "        En caso de no introducir mes se utilizará el actual.\n\n"
				+ "    ayuda\n"
				+ "        imprime este menú de ayuda.\n");
	}

	public static void main(String[] args) {
		Hidrococo programa = new Hidrococo();
		programa.ejecutar(args);
	}

}
