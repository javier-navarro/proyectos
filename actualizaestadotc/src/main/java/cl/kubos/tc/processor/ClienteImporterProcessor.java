package cl.kubos.tc.processor;

import java.util.Date;
import java.util.UnknownFormatConversionException;

import org.apache.log4j.Logger;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.util.Assert;

import cl.kubos.tc.exceptions.CustomException;
import cl.kubos.tc.model.Cliente;
import cl.kubos.tc.utils.CodigoError;
import cl.kubos.tc.utils.FechasUtil;
import cl.kubos.tc.utils.TxtFileWriter;

/**
 * <b>ClienteImporterProcessor<b/><br></b>
 * Clase que procesa la informacion del reader y verifica si estan los campos
 * correspondientes.<br>
 * Registo de versiones:
 * <ul>
 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
 * </ul>
 * <p>
 * <b>Todos los derechos reservados por Scotiabank.</b>
 * </p>
 */
public class ClienteImporterProcessor implements ItemProcessor<Cliente, Cliente> {

	/** Atributo log de la clase. */
	private static Logger logger = Logger.getLogger(ClienteImporterProcessor.class);

	/** Formato definido para el rut. */
	private static final String RUT_MESSAGE = "el Rut";

	/** objeto de tipo Date. */
	private static Date fechaEjecucion =new Date();

	/** constante que guarda la ruta local. */
	private static final String RUTAK="C:/Users/kubos/Desktop/";

	/** constante que guarda la ruta del server. */
	private static final String RUTA="/home/scotiabank/error/";

	/**
	 * Método process verifica que existan los campos.
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * @param readEmployee que representa un Cliente.
	 * @return objeto Cliente readEmployee.
	 * @throws Exception en caso de error.
	 */
	@Override
	public Cliente process(Cliente readEmployee) throws Exception {
		checkRequiredFields(readEmployee);
		return readEmployee;
	}

	/**
	 * Método checkRequiredFields invocado por el método process.
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * @param employee con objeto Cliente.
	 * @throws Exception en caso de error.
	 */
	private void checkRequiredFields(Cliente employee) throws Exception {
		Assert.hasLength(employee.getRut(), requiredFieldError(RUT_MESSAGE, employee));
	}

	/**
	 * Método requiredFieldError verifica si el archivo posee caracteres
	 * ilegibles.
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * @param employee con objeto Cliente.
	 * @param requiredFieldText con un String.
	 * @throws Exception en caso de error.
	 */
	private String requiredFieldError(String requiredFieldText, Cliente employee) throws Exception {
		StringBuilder sb = new StringBuilder();

		sb.append(": Es necesario %s en empleado. ");
		sb.append(employee.toString());

		try {
			return String.format(sb.toString(), requiredFieldText);
		}
		catch (UnknownFormatConversionException e) {
			TxtFileWriter.crearArchivoTxt(RUTA+"mensaje_error4.txt", "Error, archivo corrupto "+ "("+FechasUtil.soloFecha(fechaEjecucion)+")");

			CodigoError.estado = CodigoError.ERROR_ARCHIVO_CORRUPTO;

			if (logger.isDebugEnabled()) {
				logger.debug("[requiredFieldError] Archivo Corrupto");
			}
			throw new CustomException("Error, archivo corrupto");
		}
	}
}