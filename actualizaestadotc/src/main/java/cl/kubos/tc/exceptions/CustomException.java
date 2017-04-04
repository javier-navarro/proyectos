package cl.kubos.tc.exceptions;

/**
 * <b>CustomException<b/><br></b>
 * Clase que personaliza el tipo de excepciones a utilizar .<br>
 * Registo de versiones:
 * <ul>
 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
 * </ul>
 * <p>
 * <b>Todos los derechos reservados por Scotiabank.</b>
 * </p>
 */
public class CustomException extends Exception {

	/** Identificador para serialización. */
	private static final long serialVersionUID = 1L;

	/**
	 * Método CustomException.
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * @param message contiene el mensaje.
	 */
	public CustomException(String message) {
		super(message);
	}
}
