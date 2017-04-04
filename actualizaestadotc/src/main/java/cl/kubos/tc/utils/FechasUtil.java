package cl.kubos.tc.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;


/**
 * <b>FechasUtil<b/><br></b>
 * Clase que contiene las fechas a utilizar en el aplicativo.<br>
 * Registo de versiones:
 * <ul>
 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
 * </ul>
 * <p>
 * <b>Todos los derechos reservados por Scotiabank.</b>
 * </p>
 */
public class FechasUtil {

	/** Formato definido para día sábado. */
	private static final int SABADO = Calendar.SATURDAY;

	/** Formato definido para día domingo. */
	private static final int DOMINGO = Calendar.SUNDAY;

	/** constante que representa 0. */
	private static final int CERO=0;

	/** constante que representa 1. */
	private static final int UNO=1;

	/** constante que representa 2. */
	private static final int DOS=2;


	/** constante que representa 3. */
	private static final int TRES=3;

	/** constante que representa 5. */
	private static final int CINCO=5;

	/**
	 * Método diaHabilAnterior que devuelve el dáa hábil anterior a la fecha que
	 * llega como parámetro.
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * 
	 * @param fecha
	 *            con la fecha a procesar
	 * @return c con la fecha del día hábil anterior
	 */
	public static Calendar diaHabilAnterior(Calendar fecha) {
		Calendar c = (Calendar) fecha.clone();
		c.add(Calendar.DATE, -UNO);
		while (esFestivo(c)) {
			c.add(Calendar.DATE, -UNO);
		}
		return c;
	}

	/**
	 * Método esFestivo retorna un true or false si es festivo o no.
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * 
	 * @param fecha
	 *            con la fecha a procesar
	 * @return true or false dependiendo de la fecha.
	 */
	public static boolean esFestivo(Calendar fecha) {
		if (fecha == null) {
			return false;
		}

		return (esFinDeSemana(fecha) || esFeriadoEnPropertie(fecha));
	}

	/**
	 * Método esFestivo retorna un true or false si es feriado o no.
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * 
	 * @param fecha
	 *            con la fecha a procesar
	 * @return true or false dependiendo de la fecha.
	 */
	private static  boolean esFeriadoEnPropertie(Calendar fecha) {
		if ((fecha == null)) {
			return false;
		}

		PropertiesFileReader prop = new PropertiesFileReader(
				ClassLoader.getSystemResourceAsStream("feriados.properties"));
		String diasDesdePropertie = prop.getProperty("feriados");

		String[] dias = diasDesdePropertie.split("#");

		if ((dias == null) || (dias.length == CERO)) {
			return false;
		}

		int dia = fecha.get(Calendar.DAY_OF_MONTH);
		int mes = UNO + fecha.get(Calendar.MONTH);

		for (int i = CERO; i < dias.length; i++) {
			int diaFecha = (Integer.valueOf(dias[i].substring(CERO, DOS))).intValue();
			int mesFecha = (Integer.valueOf(dias[i].substring(TRES, CINCO))).intValue();

			if ((dia == diaFecha) && (mes == mesFecha)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Método esFinDeSemana retorna un true or false si es finde semana o no.
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * 
	 * @param fecha
	 *            con la fecha a procesar
	 * @return true or false dependiendo de la fecha.
	 */
	public static  boolean esFinDeSemana(Calendar fecha) {
		if (fecha == null) {
			return false;
		}
		return ((fecha.get(Calendar.DAY_OF_WEEK) == SABADO) || (fecha.get(Calendar.DAY_OF_WEEK) == DOMINGO));
	}

	/**
	 * Método toCalendar transforma un String(fecha) a Calendar.
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * 
	 * @param dia representa el dia 
	 * @param mes representa el mes
	 * @param anio representa el anio   
	 * @return la fecha en formato calendar.
	 */
	public static Calendar toCalendar(String dia, String mes, String anio) {
		Calendar calendar = Calendar.getInstance();
		calendar.set(Calendar.DAY_OF_MONTH, Integer.valueOf(dia));
		calendar.set(Calendar.MONTH, Integer.valueOf(mes) - UNO);
		calendar.set(Calendar.YEAR, Integer.valueOf(anio));
		return calendar;
	}

	/**
	 * Método esHoy retorna true or false dependiendo si es el día actual.
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * 
	 * @param fecha
	 *            a procesar
	 * @return true or false dependiendo si el dia es hoy
	 */

	public static boolean esHoy(Calendar fecha) {

		fecha.set(Calendar.HOUR, CERO);
		fecha.set(Calendar.MINUTE, CERO);
		fecha.set(Calendar.SECOND, CERO);
		fecha.set(Calendar.MILLISECOND, CERO);

		Calendar hoy = Calendar.getInstance();
		hoy.set(Calendar.HOUR, CERO);
		hoy.set(Calendar.MINUTE, CERO);
		hoy.set(Calendar.SECOND, CERO);
		hoy.set(Calendar.MILLISECOND, CERO);

		if (fecha.compareTo(hoy) == CERO) {

			return true;
		}

		return false;
	}

	/**
	 * Método que concadena y da un formato especifico a una fecha.
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 03/03/2017 Javier Navarro (Kubos): Versión inicial.</li>
	 * </ul>
	 * </p>
	 * @param fecha determinada.
	 * @return fecha con formato adecuado.
	 * @throws ParseException en caso de ocurrir un error.
	 */
	public  static String soloFecha(Date fecha) throws ParseException{
		return new SimpleDateFormat("dd/MM/yyyy - HH:mm:ss").format(fecha);
	}
}
