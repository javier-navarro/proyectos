package cl.kubos.tc.writer;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.annotation.BeforeStep;
import org.springframework.batch.item.ItemWriter;

import com.opencsv.CSVReader;

import cl.kubos.tc.dao.ClienteDAO;
import cl.kubos.tc.exceptions.CustomException;
import cl.kubos.tc.model.Cliente;
import cl.kubos.tc.model.Control;
import cl.kubos.tc.utils.CodigoError;
import cl.kubos.tc.utils.FechasUtil;
import cl.kubos.tc.utils.PropertiesFileReader;
import cl.kubos.tc.utils.TxtFileWriter;

/**
 * <b>ClienteImporterWriter<b/><br></b>
 * Clase que ejecuta la aplicación.<br>
 * Registo de versiones:
 * <ul>
 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
 * </ul>
 * <p>
 * <b>Todos los derechos reservados por Scotiabank.</b>
 * </p>
 */
public  class ClienteImporterWriter implements ItemWriter<Cliente> {

	/** Atributo log de la clase. */
	private static Logger log = Logger.getLogger(ClienteImporterWriter.class);

	/** constante que representa 0. */
	private static final int CERO=0;

	/** constante que representa 1. */
	private static final int UNO=1;

	/** constante que representa 2. */
	private static final int DOS=2;

	/** constante que representa 3. */
	private static final int TRES=3;

	/** constante que representa 4. */
	private static final int CUATRO=4;

	/** constante que guarda la ruta local. */
	private static final String RUTAK="C:/Users/kubos/Desktop/";

	/** constante que guarda la ruta del server. */
	private static final String RUTA="/home/scotiabank/error/";

	/** objeto tipo ClienteDAO. */
	private ClienteDAO clienteDAO;

	/** objeto de tipo JobExecution. */
	private JobExecution jobExecution;

	/** objeto de tipo Date. */
	private static Date fechaEjecucion =new Date();

	/**
	 * Método beforeStep que se ejecuta antes del job.
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 */
	@BeforeStep
	public void beforeStep(StepExecution stepExecution) {
		jobExecution = stepExecution.getJobExecution();
	}
	
	public void setClienteDAO(ClienteDAO clienteDAO) {
		this.clienteDAO = clienteDAO;
	}

	/**
	 * Método write el cual realiza las siguientes funciones: -carga el archivo
	 * properties desde el resource. 
	 * 
	 * -FileReader lee el file y le quita comas y
	 * espacio.
	 * 
	 * -CSVReader lee el otro file y le saca los punto y coma.
	 * 
	 * -list.isEmpty() valida si el archivo viene vacio o no, si es asi crea el
	 * mensaje de error correspondiente, si no lee el archivo.
	 * 
	 * -compara en el csv 2 que las fechas esten en correcto margen con respecto a las
	 * restricciones de que el dia anterior a la de proceso sea un dia hábil
	 * anterior, y que la fecha de proceso sea del día actual. 
	 * 
	 * -esHoy valida si el dia de proceso corresponde a la fecha actual, si no crea el mensaje
	 * correspondiente, si no, lee el archivo y lo procesa.
	 * 
	 * -fechaUltimaVenta.compareTo compara la última venta con la fecha de
	 * proceso anterior, si no esta en el rango de 1 dia maximo (día hábil),
	 * crea el mensaje de error correspondiente, en caso contrario lo lee y lo
	 * procesa. 
	 * 
	 * -En caso que la cantidad de registros no coincida con la
	 * cantidad de ruts, se crea el mensaje de error correspondiente, en caso
	 * contrario lo lee y procesa. 
	 * 
	 * -En caso de pasar todas las validaciones, el
	 * sistema procesa al cliente, y en caso de el rut encontrarse en la base de
	 * datos le cambia el estado a "Acepta Tarjeta", de no encontrarse no se
	 * producen cambios.
	 * 
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * @param items trae los items como parametro.
     * @throws Exception de ocurrir alguna excepcion en el método.
	 */

	public void write(List<? extends Cliente> items) throws Exception {

		PropertiesFileReader prop = new PropertiesFileReader(ClassLoader.getSystemResourceAsStream("config.properties"));

		String file2 = prop.getProperty("file1").replace("file:", "");

		FileReader fileReader = new FileReader(file2.replace("file:", ""));
		CSVReader csvReader = new CSVReader(fileReader, ';');
		List<Control> controles = new ArrayList<Control>();
		List<String[]> list = csvReader.readAll();

		if (!list.isEmpty() && list.size() >= DOS) {
			String[] row = list.get(UNO);
			if (row.length < TRES) {
				TxtFileWriter.crearArchivoTxt(RUTA+"mensaje_error7.txt", "Error, csv 2 vacío "+ "("+FechasUtil.soloFecha(fechaEjecucion)+")");
				CodigoError.estado = CodigoError.ERROR_ARCHIVO_VACIO2;
				csvReader.close();
				throw new CustomException("Error, csv 2 vacío");
			}

			Control control = new Control();
			control.setFechaProceso(row[CERO]);
			control.setFechaUltVenta(row[UNO]);
			control.setRegistros(row[DOS]);
			controles.add(control);
		} 
		else {
			TxtFileWriter.crearArchivoTxt(RUTA+"mensaje_error7.txt", "Error, csv 2 vacío " + "("+FechasUtil.soloFecha(fechaEjecucion)+")");
			CodigoError.estado = CodigoError.ERROR_ARCHIVO_VACIO2;
			csvReader.close();
			throw new CustomException("Error, csv 2 vacío");
		}
		csvReader.close();
		fileReader.close();

		if (log.isInfoEnabled()) {
			log.info("[write] file 2 rows " + controles.size());
		}

		if (controles.isEmpty()) {
			TxtFileWriter.crearArchivoTxt(RUTA+"mensaje_error7.txt", "Error, csv 2 vacío " + "("+FechasUtil.soloFecha(fechaEjecucion)+")");
			CodigoError.estado = CodigoError.ERROR_ARCHIVO_VACIO2;
			throw new CustomException("Error, csv 2 vacío");
		}

		String[] fecha1Array = controles.get(CERO).getFechaUltVenta().split("-");
		String[] fecha2Array = controles.get(CERO).getFechaProceso().split("-");

		Calendar fechaUltimaVenta = FechasUtil.toCalendar(fecha1Array[CERO], fecha1Array[UNO],
				fecha1Array[DOS].substring(CERO, CUATRO));
		Calendar fechaProceso = FechasUtil.toCalendar(fecha2Array[CERO], fecha2Array[UNO], fecha2Array[DOS].substring(CERO, CUATRO));
		Calendar fechaProcesoAnt = FechasUtil.diaHabilAnterior(fechaProceso);
		boolean esHoy = FechasUtil.esHoy(fechaProceso);

		if (log.isInfoEnabled()) {
			log.info("[write] Hoy "+esHoy);
		}

		if (!esHoy) {

			TxtFileWriter.crearArchivoTxt(RUTA+"mensaje_error6.txt","Error, fecha de proceso no coincide con el día de hoy  "+ "("+FechasUtil.soloFecha(fechaEjecucion)+")");
			CodigoError.estado = CodigoError.ERROR_DIA_HOY;
			throw new CustomException("ERROR,  fecha de proceso no coincide con el día de hoy");

		}

		if (fechaUltimaVenta.compareTo(fechaProcesoAnt) != CERO) {

			TxtFileWriter.crearArchivoTxt(RUTA+"mensaje_error3.txt", "Error, no es día hábil anterior " + "("+FechasUtil.soloFecha(fechaEjecucion)+")");

			CodigoError.estado = CodigoError.ERROR_DIA_ANTERIOR;
			throw new CustomException("ERROR, no es dia habil anterior");
		}

		if (Integer.valueOf(controles.get(CERO).getRegistros()) != items.size()) {

			TxtFileWriter.crearArchivoTxt(RUTA+"mensaje_error2.txt", "Error, número de registros no coincide "+ "("+FechasUtil.soloFecha(fechaEjecucion)+")");

			CodigoError.estado = CodigoError.ERROR_REGISTRO;
			throw new CustomException("Error, numero de registros no coincide");
		}

		for (Cliente cliente : items) {

			final int rut = Integer.valueOf(cliente.getRut());
			final String estado = "Acepta Tarjeta";

			try {
				clienteDAO.actualizarEstado(rut, estado);
			} 
			catch (Exception e) {
				if (log.isEnabledFor(Level.ERROR)) {
					log.error("[writer][FINEX] Error: "+ e,e);
				}
			}
		}
		TxtFileWriter.crearArchivoTxt(RUTA+"mensaje_error0.txt", "COMPLETADO SIN ERRORES ! "+ "("+FechasUtil.soloFecha(fechaEjecucion)+")");
		CodigoError.estado = CodigoError.COMPLETADO;
	}

}