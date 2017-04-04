package cl.kubos.tc.utils;

import java.io.BufferedWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;

/**
 * <b>TxtFileWriter<b/><br></b>
 * Clase que se encarga de crear el archivo txt.<br>
 * Registo de versiones:
 * <ul>
 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
 * </ul>
 * <p>
 * <b>Todos los derechos reservados por Scotiabank.</b>
 * </p>
 */
public class TxtFileWriter {

	/** Atributo log de clase. */
	private static Logger logger = Logger.getLogger(TxtFileWriter.class);

	
	/**
	 * Método crearArchivoTxt encargado de crear el archivo txt.
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * @param nombre que le da el nombre al archivo.
	 * @param mensaje que le da el contenido al archivo.
	 */
	public static void crearArchivoTxt(String nombre, String mensaje) {

		
		if (logger.isDebugEnabled()) {
			logger.debug("[crearArchivoTxt] Creando mensaje...");
		}
		Writer writer = null;
		OutputStream out = null;
		OutputStreamWriter sw = null;
		try {
			out = new FileOutputStream(nombre);
			sw = new OutputStreamWriter(out, "utf-8");
			writer = new BufferedWriter(sw);
			writer.write(mensaje);

			if (logger.isDebugEnabled()) {
				logger.debug("[crearArchivoTxt] Mensaje creado con exito.");
			}
		} 
		catch (IOException e) {
			if (logger.isEnabledFor(Level.ERROR)) {
                logger.error("[crearArchivoTxt][FINEX] Error: "+e,e);
            }
		} 
		finally {

			try {
				writer.close();
				out.close();
				sw.close();
			} 
			catch (Exception e) {
				if (logger.isEnabledFor(Level.ERROR)) {
	                logger.error("[crearArchivoTxt][FINEX] Error: "+e,e);
	            }
			}
		}
	}
}
