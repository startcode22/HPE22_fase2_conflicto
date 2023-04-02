package main;

public class Central {

	private int id;
	private String nombre;
	private String ubicacion;
	private float potencia_instalada_en_mw;
	private float latitud;
	private float longitud;
	private int embalse;
	
	public Central(int id, String nombre, String ubicacion, float potencia_instalada_en_mw, float latitud,
			float longitud, int embalse) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.ubicacion = ubicacion;
		this.potencia_instalada_en_mw = potencia_instalada_en_mw;
		this.latitud = latitud;
		this.longitud = longitud;
		this.embalse = embalse;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getNombre() {
		return nombre;
	}

	public void setNombre(String nombre) {
		this.nombre = nombre;
	}

	public String getUbicacion() {
		return ubicacion;
	}

	public void setUbicacion(String ubicacion) {
		this.ubicacion = ubicacion;
	}

	public float getPotencia_instalada_en_mw() {
		return potencia_instalada_en_mw;
	}

	public void setPotencia_instalada_en_mw(float potencia_instalada_en_mw) {
		this.potencia_instalada_en_mw = potencia_instalada_en_mw;
	}

	public float getLatitud() {
		return latitud;
	}

	public void setLatitud(float latitud) {
		this.latitud = latitud;
	}

	public float getLongitud() {
		return longitud;
	}

	public void setLongitud(float longitud) {
		this.longitud = longitud;
	}

	public int getEmbalse() {
		return embalse;
	}

	public void setEmbalse(int embalse) {
		this.embalse = embalse;
	}
	
}
