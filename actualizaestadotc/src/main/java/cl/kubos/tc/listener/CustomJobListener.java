package cl.kubos.tc.listener;

import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.JobExecutionListener;

/**
 * <b>CustomJobListener<b/><br></b>
 * Clase que personaliza la entrada y salida del job .<br>
 * Registo de versiones:
 * <ul>
 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
 * </ul>
 * <p>
 * <b>Todos los derechos reservados por Scotiabank.</b>
 * </p>
 */
public class CustomJobListener implements JobExecutionListener {

	/** Atributo log de la clase. */
	private static Logger logger = Logger.getLogger(CustomJobListener.class);

	/**
	 * Método beforeJob que se ejecuta antes del job
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * @param jobExecution que representa un JobExecution.
	 */
	@Override
	public void beforeJob(JobExecution jobExecution) {

		if (logger.isDebugEnabled()) {
			logger.debug("[beforeJob] JOBLISTENER: Se va a ejecutar el Job con ID: ".concat(jobExecution.getJobId().toString()));
		}
	}

	/**
	 * Método beforeJob que se ejecuta después del job
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * @param jobExecution que representa un JobExecution.
	 */
	@Override
	public void afterJob(JobExecution jobExecution) {

		if (logger.isDebugEnabled()) {
			logger.debug("[afterJob] JOBLISTENER:  Se ha terminado de ejecutar el Job con ID: ".concat(jobExecution.getJobId().toString()));
		}

		List<Throwable> exceptions = jobExecution.getFailureExceptions();

		for (Throwable t : exceptions) {

			if (logger.isDebugEnabled()) {
				logger.error("[afterJob] ***********");
				logger.error("[afterJob] "+t.getMessage());
				logger.error("[afterJob] "+ t.getCause());
				logger.error("[afterJob] "+t.getStackTrace());
				logger.error("[afterJob] ***********");
			}
		}

	}
}
