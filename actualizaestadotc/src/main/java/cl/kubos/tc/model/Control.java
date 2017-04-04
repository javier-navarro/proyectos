package cl.kubos.tc.model;

/**
 * <b>Control<b/><br></b>
 * Clase control contiene los datos a leer del CSV Archivo_Ctl_Vta. <br>
 * Registo de versiones:
 * <ul>
 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
 * </ul>
 * <p>
 * <b>Todos los derechos reservados por Scotiabank.</b>
 * </p>
 */
public class Control {

	/** Captura fecha proceso. */
	private String fechaProceso;

	/** Captura fecha última venta. */
	private String fechaUltVenta;

	/** Captura numero de registros. */
	private String registros;

	/**
	 * Instancia constructor de clase vacío.
	 */
	public Control() {

	}

	/**
	 * Instancia constructor de clase con parámetros.
	 * 
	 * @param fechaProceso the fecha proceso.
	 * @param fechaUltVenta the fecha ult venta.
	 * @param registros the registros.
	 */
	public Control(String fechaProceso, String fechaUltVenta, String registros) {
		super();
		this.fechaProceso = fechaProceso;
		this.fechaUltVenta = fechaUltVenta;
		this.registros = registros;
	}

	public String getFechaProceso() {
		return fechaProceso;
	}

	public void setFechaProceso(String fechaProceso) {
		this.fechaProceso = fechaProceso;
	}

	public String getFechaUltVenta() {
		return fechaUltVenta;
	}

	public void setFechaUltVenta(String fechaUltVenta) {
		this.fechaUltVenta = fechaUltVenta;
	}

	public String getRegistros() {
		return registros;
	}

	public void setRegistros(String registros) {
		this.registros = registros;
	}

	@Override
	public String toString() {
		return "Control [fechaProceso=" + fechaProceso + ", fechaUltVenta=" + fechaUltVenta + ", registros=" + registros
				+ "]";
	}
}
