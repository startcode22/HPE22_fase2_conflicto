package main;

public class Embalse {

	private int id;
	private String nombre;
	private float areaEnHm2;
	private float limiteInferiorEnHm3;
	private float limiteSuperiorEnHm3;
	private float aportacionMediaAnualEnHm3;

	public Embalse(int id, String nombre, float areaEnHm2, float limiteInferiorEnHm3, float limiteSuperiorEnHm3,
			float aportacionMediaAnualEnHm3) {
		super();
		this.id = id;
		this.nombre = nombre;
		this.areaEnHm2 = areaEnHm2;
		this.limiteInferiorEnHm3 = limiteInferiorEnHm3;
		this.limiteSuperiorEnHm3 = limiteSuperiorEnHm3;
		this.aportacionMediaAnualEnHm3 = aportacionMediaAnualEnHm3;
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

	public float getAreaEnHm2() {
		return areaEnHm2;
	}

	public void setAreaEnHm2(float areaEnHm2) {
		this.areaEnHm2 = areaEnHm2;
	}

	public float getLimiteInferiorEnHm3() {
		return limiteInferiorEnHm3;
	}

	public void setLimiteInferiorEnHm3(float limiteInferiorEnHm3) {
		this.limiteInferiorEnHm3 = limiteInferiorEnHm3;
	}

	public float getLimiteSuperiorEnHm3() {
		return limiteSuperiorEnHm3;
	}

	public void setLimiteSuperiorEnHm3(float limiteSuperiorEnHm3) {
		this.limiteSuperiorEnHm3 = limiteSuperiorEnHm3;
	}

	public float getAportacionMediaAnualEnHm3() {
		return aportacionMediaAnualEnHm3;
	}

	public void setAportacionMediaAnualEnHm3(float aportacionMediaAnualEnHm3) {
		this.aportacionMediaAnualEnHm3 = aportacionMediaAnualEnHm3;
	}

}
