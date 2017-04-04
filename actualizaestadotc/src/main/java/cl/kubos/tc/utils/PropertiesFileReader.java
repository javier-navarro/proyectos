package cl.kubos.tc.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;


/**
 * <b>PropertiesFileReader<b/><br></b>
 * Clase     <br>
 * Registo de versiones:
 * <ul>
 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
 * </ul>
 * <p>
 * <b>Todos los derechos reservados por Scotiabank.</b>
 * </p>
 */
public class PropertiesFileReader {

	/** Atributo log de clase.*/
	private static Logger log = Logger.getLogger(PropertiesFileReader.class);

	/** Atributo properties de la clase. */
	private static Properties propiedades = new Properties();


	/**
	 * Método que carga el archivo properties
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 27/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * @param archivoProperties con el archivo properties.
	 */

	public PropertiesFileReader(String archivoProperties) {
		try {
			propiedades.load(new FileInputStream(archivoProperties));
		} 
		catch (IOException e) {
			if (log.isEnabledFor(Level.ERROR)) {
				log.error("[PropertiesFileReader][FINEX] Error: "+e,e);
			}
		}
	}

	/**
	 * Método que realiza la lectura del archivo properties
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 27/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * @param archivoProperties con el archivo properties.
	 */
	public PropertiesFileReader(InputStream archivoProperties) {
		try {
			propiedades.load(archivoProperties);
		} 
		catch (IOException e) {
			if (log.isEnabledFor(Level.ERROR)) {
				log.error("[PropertiesFileReader][FINEX] Error: "+e,e);
			}
		}
	}

	/**
	 * Método getProperty.
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 27/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * @param nombre con el archivo properties.
	 */
	public String getProperty(String nombre) {
		return propiedades.getProperty(nombre);
	}
}
