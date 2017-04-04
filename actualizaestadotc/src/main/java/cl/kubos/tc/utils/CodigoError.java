package cl.kubos.tc.utils;

/**
 * <b>CodigoError<b/><br></b>
 * Clase CodigoError contiene todos los codigos de error que despliega la
 * aplicación. <br>
 * Registo de versiones:
 * <ul>
 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
 * </ul>
 * <p>
 * <b>Todos los derechos reservados por Scotiabank.</b>
 * </p>
 */
public final class CodigoError {

	/** Constante de salida completado. */
	public static final int COMPLETADO = 0;

	/** Constante de salida error general. */
	public static final int ERROR_GENERAL = 1;

	/** Constante de salida error de registro. */
	public static final int ERROR_REGISTRO = 2;

	/** Constante de salida error día anterior habíl. */
	public static final int ERROR_DIA_ANTERIOR = 3;

	/** Constante de salida error por archivo corrupto. */
	public static final int ERROR_ARCHIVO_CORRUPTO = 4;

	/** Constante de salida error archivo vacío 1. */
	public static final int ERROR_ARCHIVO_VACIO = 5;

	/** Constante de salida error día actual. */
	public static final int ERROR_DIA_HOY = 6;

	/** Constante de salida error archivo vacío 2. */
	public static final int ERROR_ARCHIVO_VACIO2 = 7;

	/** Constante de estado. */
	public static  int estado = ERROR_GENERAL;
}