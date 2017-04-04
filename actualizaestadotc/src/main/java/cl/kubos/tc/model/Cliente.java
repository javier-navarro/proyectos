package cl.kubos.tc.model;

import java.io.Serializable;

/**
 * <b>Cliente<b/><br></b>
 * Clase Cliente contiene los datos a leer del CSV Rut_Vta_Mes. <br>
 * Registo de versiones:
 * <ul>
 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
 * </ul>
 * <p>
 * <b>Todos los derechos reservados por Scotiabank.</b>
 * </p>
 */
@SuppressWarnings("serial")
public class Cliente implements Serializable {

	/** Captura el rut. */
	private String rut;

	/**
	 * Instancia constructor de clase vacío.
	 */
	public Cliente() {

	}

	/**
	 * Instancia constructor de clase con parámetros.
	 *
	 * @param rut
	 *            the rut
	 */
	public Cliente(String rut) {
		super();
		this.rut = rut;
	}

	public String getRut() {
		return rut;
	}

	public void setRut(String rut) {
		this.rut = rut;
	}

	@Override
	public String toString() {
		return "Cliente [rut=" + rut + "]";
	}
}
