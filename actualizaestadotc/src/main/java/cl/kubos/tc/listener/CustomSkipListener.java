package cl.kubos.tc.listener;

import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.AfterStep;
import org.springframework.batch.item.file.FlatFileParseException;

import cl.kubos.tc.exceptions.CustomException;
import cl.kubos.tc.utils.CodigoError;
import cl.kubos.tc.utils.FechasUtil;
import cl.kubos.tc.utils.TxtFileWriter;

/**
 * <b>CustomSkipListener<b/><br></b>
 * Clase que personaliza las omiciones del listener.<br>
 * Registo de versiones:
 * <ul>
 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
 * </ul>
 * <p>
 * <b>Todos los derechos reservados por Scotiabank.</b>
 * </p>
 */
public class CustomSkipListener implements SkipListener<Object, Object> {

	/** Atributo log de la clase. */
	private static Logger logger = Logger.getLogger(CustomSkipListener.class);

	/** constante que representa 0. */
	private static final int CERO=0;

	/** objeto de tipo Date. */
	private static Date fechaEjecucion =new Date();

	/** constante que guarda la ruta local. */
	private static final String RUTAK="C:/Users/kubos/Desktop/";

	/** constante que guarda la ruta del server. */
	private static final String RUTA="/home/scotiabank/error/";

	/**
	 * Método onSkipInRead en caso que no puede leer el archivo.
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * @param t que representa un Throwable.
	 */
	@Override
	public void onSkipInRead(Throwable t) {

		if (logger.isDebugEnabled()) {
			logger.debug("[onSkipInRead] Iniciando Metodo.");
		}

		StringBuilder message = new StringBuilder("ERROR en LECTURA: ");
		if (t instanceof FlatFileParseException) {
			message.append("Linea ").append(((FlatFileParseException) t).getLineNumber())
			.append(": Error de formato para la siguiente entrada: ")
			.append(((FlatFileParseException) t).getInput());
		} 
		else {
			message.append(t.getMessage());
		}
		if (logger.isDebugEnabled()) {
			logger.debug("[onSkipInRead] "+message.toString());
		}
	}

	/**
	 * Método onSkipInWrite en caso que no puede escribir el archivo.
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * @param item que representa un Object.
	 * @param t que representa un Throwable.
	 */
	@Override
	public void onSkipInWrite(Object item, Throwable t) {

		if (logger.isDebugEnabled()) {
			logger.debug("[onSkipInWrite] Iniciando Metodo.");
		}

		StringBuilder message = new StringBuilder("ERROR en ESCRITURA: ").append(t.getMessage());
		if (logger.isDebugEnabled()) {
			logger.debug("[onSkipInWrite] "+message.toString());
		}
	}

	/**
	 * Método onSkipInProcess en caso que no puede procesar el archivo el
	 * archivo.
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * @param item que representa un Object.
	 * @param t que representa un Throwable.
	 * 
	 */
	@Override
	public void onSkipInProcess(Object item, Throwable t) {
		if (logger.isDebugEnabled()) {
			logger.debug("[onSkipInProcess] on skip process");
		}

		StringBuilder message = new StringBuilder("ERROR en PROCESADO: ").append(t.getMessage());
		if (logger.isDebugEnabled()) {
			logger.debug("[onSkipInProcess] "+message.toString());
		}

	}

	/**
	 * Método afterStep el cual se ejecuta antes del step del job y verifica si
	 * el archivo Rut_Vta_Mes viene vacío.
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * @param execution que representa un StepExecution.
	 * @throws Exception cuando surge un error.
	 */
	@AfterStep
	public ExitStatus afterStep(StepExecution execution) throws Exception {
		if (execution.getReadCount() > CERO) {
			return execution.getExitStatus();
		} 
		else {
			TxtFileWriter.crearArchivoTxt(RUTA+"mensaje_error5.txt", "Error, csv 1 vacío "+ "("+FechasUtil.soloFecha(fechaEjecucion)+")");

			if (logger.isDebugEnabled()) {
				logger.debug("[afterStep] Archivo 1 vacio ");
			}

			CodigoError.estado = CodigoError.ERROR_ARCHIVO_VACIO;
			throw new CustomException("Error, ARCHIVO VACIO");
		}
	}
}