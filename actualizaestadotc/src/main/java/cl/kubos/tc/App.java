package cl.kubos.tc;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.JobParameters;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import cl.kubos.tc.utils.CodigoError;

/**
 * <b>App<b/><br></b>
 * Clase que ejecuta la aplicación.<br>
 * Registo de versiones:
 * <ul>
 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
 * </ul>
 * <p>
 * <b>Todos los derechos reservados por Scotiabank.</b>
 * </p>
 */
public class App {

	/** Atributo log de la clase. */
	private static Logger logger = Logger.getLogger(App.class);

	/**
	 * Método main que permite inicializar la aplicación.
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * @param args que representa el arreglo de argumentos.
	 * @throws Exception cuando surge un error.
	 */
	public static void main(String[] args) throws Exception {


		ApplicationContext applicationContext = new ClassPathXmlApplicationContext("spring-module.xml");
		JobLauncher jobLauncher = (JobLauncher) applicationContext.getBean("jobLauncher");
		Job job = (Job) applicationContext.getBean("importFileJob");

		try {
			jobLauncher.run(job, new JobParameters());
		} 
		catch (Exception e) {

			if (logger.isEnabledFor(Level.ERROR)) {
				logger.error("[main][FINEX] Error "+ e,e);
			}
		}

		if (logger.isDebugEnabled()) {
			logger.debug("[main] Salida " + CodigoError.estado);
		}
		System.exit(CodigoError.estado);
		((ConfigurableApplicationContext) applicationContext).close();
	}
}
