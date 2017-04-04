package cl.kubos.tc.separator;

import org.springframework.batch.item.file.separator.SimpleRecordSeparatorPolicy;

/**
 * <b>SkipBlankLinePolicy<b/><br></b>
 * Clase que  configura la lógica cuando las líneas estan en blanco<br>
 * Registo de versiones:
 * <ul>
 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
 * </ul>
 * <p>
 * <b>Todos los derechos reservados por Scotiabank.</b>
 * </p>
 */
public class SkipBlankLinePolicy extends SimpleRecordSeparatorPolicy {

	/**
	 * Método isEndOfRecord verifica si llego al último caracter.
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * @return boleean si es el final de la línea.
	 * @param line con el número de líneas.
	 */
	@Override
	public boolean isEndOfRecord(String line) {
		if (line.trim().length() == 0) {
			return false;
		}
		return super.isEndOfRecord(line);
	}

	/**
	 * Método postProcess se ejecuta cuando termina el job.
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * 
	 * @return linea en caso de true, y nullo en caso de que falle.
	 * @param record
	 *            con la línea.
	 */
	@Override
	public String postProcess(String record) {
		if (record == null || record.trim().length() == 0) {
			return null;
		}
		return super.postProcess(record);
	}
}
