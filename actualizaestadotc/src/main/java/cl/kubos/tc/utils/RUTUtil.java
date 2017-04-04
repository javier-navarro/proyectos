package cl.kubos.tc.utils;

////import java.util.regex.*;

/**
 * Esta clase contiene métodos estáticos que permiten hacer cálculos y otras
 * cosas referidas a los RUT.
 * <P>
 * Todos los métodos tienen versiones que manejan el número del RUT como número
 * propiamente dicho o como string, y el dígito verificador como char o como
 * string.
 * <UL>
 * <LI>el número se maneja como un <B>long</B>
 * <LI>el dígito verificador se maneja como un <B>char</B>.
 * </UL>
 * <P>
 * OJO: Los métodos base son los que reciben un parámetro long o una tupla
 * (long, char) - cualquier otro método que tenga el mismo nombre y distinta
 * firma simplemente hace la conversión de tipos de datos <B>sin preocuparse de
 * corregir errores</B> y luego los invoca.
 * <P>
 * Nota: el método extraeNumero() depende de funcionalidad disponible en Java
 * 1.4 o superior, por lo que se dejará comentado hasta que se abandone
 * definitivamente Weblogic 7.0 (que usa Java 1.3).
 * <P>
 * Registro de versiones:
 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
 *
 * <b>Todos los derechos reservados por Scotiabank.</b>
 * <p>
 */
public class RUTUtil {

	/**
	 * Calcula el dígito verificador de un número de RUT.
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT cuyo dígito verificador se quiere calcular
	 *            (se presume que es mayor o igual que 0).
	 * @return El dígito verificador que corresponde.
	 */
	static public char calculaDigitoVerificador(long numero) {
		int digito;
		int multiplicador = 2;
		int suma = 0;
		String s = String.valueOf(numero);
		char digitoVerificador;

		for (int i = s.length() - 1; i >= 0; i--) {
			suma += multiplicador * (new Integer(s.substring(i, i + 1)).intValue());
			multiplicador++;
			if (multiplicador >= 8) {
				multiplicador = 2;
			}
		}
		digito = 11 - (suma % 11);
		if (digito == 11)
			digito = 0;

		digitoVerificador = ((digito == 10) ? 'K' : String.valueOf(digito).charAt(0));
		return digitoVerificador;
	}

	/**
	 * Calcula el dígito verificador de un número de RUT.
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * *
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT cuyo dígito verificador se quiere calcular
	 *            (se presume que es mayor o igual que 0).
	 * @return El dígito verificador que corresponde.
	 * @see #calculaDigitoVerificador(long)
	 */
	static public char calculaDigitoVerificador(int numero) {
		return calculaDigitoVerificador((long) numero);
	}

	/**
	 * Calcula el dígito verificador de un número de RUT.
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT cuyo dígito verificador se quiere calcular
	 *            (se presume que es una simple secuencia de dígitos que
	 *            corresponde a un número mayor o igual que cero).
	 * @return El dígito verificador que corresponde.
	 * @see #calculaDigitoVerificador(long)
	 */
	static public char calculaDigitoVerificador(String numero) {
		return calculaDigitoVerificador(new Long(numero).longValue());
	}

	/**
	 * Determina si el dígito verificador de un RUT es válido o no.
	 * <P>
	 * Nota: la definición del RUT dicta que el dígito verificador 'K' es la
	 * letra mayúscula y no la minúscula; en atención a la cantidad de registros
	 * incorrectos, también se acepta la minúscula.
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT.
	 * @param digitoVerificador
	 *            El dígito verificador a validar.
	 * @return La veracidad/falsedad de la proposición.
	 * @see #calculaDigitoVerificador(long)
	 */
	static public boolean validaDigitoVerificador(long numero, char digitoVerificador) {
		char digitoCalculado = calculaDigitoVerificador(numero);
		return ((digitoVerificador == digitoCalculado) || ((digitoVerificador == 'k') && (digitoCalculado == 'K')));
	}

	/**
	 * Determina si el dígito verificador de un RUT es válido o no.
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT.
	 * @param digitoVerificador
	 *            El dígito verificador a validar.
	 * @return La veracidad/falsedad de la proposición.
	 * @see #validaDigitoVerificador(long, char)
	 * @since 1.0
	 */
	static public boolean validaDigitoVerificador(long numero, String digitoVerificador) {
		return validaDigitoVerificador(numero, digitoVerificador.charAt(0));
	}

	/**
	 * Determina si el dígito verificador de un RUT es válido o no.
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT.
	 * @param digitoVerificador
	 *            El dígito verificador a validar.
	 * @return La veracidad/falsedad de la proposición.
	 * @see #validaDigitoVerificador(long, char)
	 */
	static public boolean validaDigitoVerificador(int numero, char digitoVerificador) {
		return validaDigitoVerificador((long) numero, digitoVerificador);
	}

	/**
	 * Determina si el dígito verificador de un RUT es válido o no.
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT.
	 * @param digitoVerificador
	 *            El dígito verificador a validar.
	 * @return La veracidad/falsedad de la proposición.
	 * @see #validaDigitoVerificador(long, char)
	 */
	static public boolean validaDigitoVerificador(int numero, String digitoVerificador) {
		return validaDigitoVerificador((long) numero, digitoVerificador.charAt(0));
	}

	/**
	 * Determina si el dígito verificador de un RUT es válido o no.
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT.
	 * @param digitoVerificador
	 *            El dígito verificador a validar.
	 * @return La veracidad/falsedad de la proposición.
	 * @see #validaDigitoVerificador(long, char)
	 */
	static public boolean validaDigitoVerificador(String numero, char digitoVerificador) {
		return validaDigitoVerificador(new Long(numero).longValue(), digitoVerificador);
	}

	/**
	 * Determina si el dígito verificador de un RUT es válido o no.
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT.
	 * @param digitoVerificador
	 *            El dígito verificador a validar.
	 * @return La veracidad/falsedad de la proposición.
	 * @see #validaDigitoVerificador(long, char)
	 * 
	 */
	static public boolean validaDigitoVerificador(String numero, String digitoVerificador) {
		return validaDigitoVerificador(new Long(numero).longValue(), digitoVerificador.charAt(0));
	}

	/**
	 * Determina si el dígito verificador de un RUT es válido o no.
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param rut
	 *            El RUT completo, en formato [0-9]+-[0-9K] (i.e.: CON el guión
	 *            separador).
	 * @return La veracidad/falsedad de la proposición.
	 * @see #validaDigitoVerificador(long, char)
	 */
	static public boolean validaDigitoVerificador(String rut) {
		long numero = new Long(rut.substring(0, rut.length() - 2)).longValue();
		char digitoVerificador = rut.charAt(rut.length() - 1);
		return validaDigitoVerificador(numero, digitoVerificador);
	}

	/**
	 * Entrega un RUT formateado de acuerdo con la definición estricta (esto es:
	 * el número, seguido de un guión, seguido del dígito verificador).
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT.
	 * @param digitoVerificador
	 *            El dígito verificador.
	 * @return El RUT debidamente formateado.
	 */
	static public String RUTFormateado(long numero, char digitoVerificador) {
		return (numero + "-" + digitoVerificador);
	}

	/**
	 * Entrega un RUT formateado de acuerdo con la definición estricta (esto es:
	 * el número, seguido de un guión, seguido del dígito verificador).
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT.
	 * @param digitoVerificador
	 *            El dígito verificador.
	 * @return El RUT debidamente formateado.
	 * @see #RUTFormateado(long, char)
	 */
	static public String RUTFormateado(long numero, String digitoVerificador) {
		return RUTFormateado(numero, digitoVerificador.charAt(0));
	}

	/**
	 * Entrega un RUT formateado de acuerdo con la definición estricta (esto es:
	 * el número, seguido de un guión, seguido del dígito verificador).
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT.
	 * @param digitoVerificador
	 *            El dígito verificador.
	 * @return El RUT debidamente formateado.
	 * @see #RUTFormateado(long, char)
	 */
	static public String RUTFormateado(int numero, char digitoVerificador) {
		return RUTFormateado((long) numero, digitoVerificador);
	}

	/**
	 * Entrega un RUT formateado de acuerdo con la definición estricta (esto es:
	 * el número, seguido de un guión, seguido del dígito verificador).
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * *
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT.
	 * @param digitoVerificador
	 *            El dígito verificador.
	 * @return El RUT debidamente formateado.
	 * @see #RUTFormateado(long, char)
	 */
	static public String RUTFormateado(int numero, String digitoVerificador) {
		return RUTFormateado((long) numero, digitoVerificador.charAt(0));
	}

	/**
	 * Entrega un RUT formateado de acuerdo con la definición estricta (esto es:
	 * el número, seguido de un guión, seguido del dígito verificador).
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT.
	 * @param digitoVerificador
	 *            El dígito verificador.
	 * @return El RUT debidamente formateado.
	 * @see #RUTFormateado(long, char)
	 */
	static public String RUTFormateado(String numero, char digitoVerificador) {
		return RUTFormateado(new Long(numero).longValue(), digitoVerificador);
	}

	/**
	 * Entrega un RUT formateado de acuerdo con la definición estricta (esto es:
	 * el número, seguido de un guión, seguido del dígito verificador).
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT.
	 * @param digitoVerificador
	 *            El dígito verificador.
	 * @return El RUT debidamente formateado.
	 * @see #RUTFormateado(long, char)
	 */
	static public String RUTFormateado(String numero, String digitoVerificador) {
		return RUTFormateado(new Long(numero).longValue(), digitoVerificador.charAt(0));
	}

	/**
	 * Entrega un RUT formateado <B>SIN GUIÓN</B>.
	 * <P>
	 * Este método existe para facilitar la entrega de datos a bases de datos en
	 * que el RUT se almacena de esta (incorrecta) manera.
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT.
	 * @param digitoVerificador
	 *            El dígito verificador.
	 * @return El RUT formateado sin guión.
	 * @see #RUTFormateado(long, char)
	 */
	static public String RUTFormateadoSinGuion(long numero, char digitoVerificador) {
		return (String.valueOf(numero) + digitoVerificador);
	}

	/**
	 * Entrega un RUT formateado <B>SIN GUIÓN</B>.
	 * <P>
	 * Este método existe para facilitar la entrega de datos a bases de datos en
	 * que el RUT se almacena de esta (incorrecta) manera.
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT.
	 * @param digitoVerificador
	 *            El dígito verificador.
	 * @return El RUT formateado sin guión.
	 * @see #RUTFormateadoSinGuion(long, char)
	 */
	static public String RUTFormateadoSinGuion(long numero, String digitoVerificador) {
		return RUTFormateadoSinGuion(numero, digitoVerificador.charAt(0));
	}

	/**
	 * Entrega un RUT formateado <B>SIN GUIÓN</B>.
	 * <P>
	 * Este método existe para facilitar la entrega de datos a bases de datos en
	 * que el RUT se almacena de esta (incorrecta) manera.
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT.
	 * @param digitoVerificador
	 *            El dígito verificador.
	 * @return El RUT formateado sin guión.
	 * @see #RUTFormateadoSinGuion(long, char)
	 */
	static public String RUTFormateadoSinGuion(int numero, char digitoVerificador) {
		return RUTFormateadoSinGuion((long) numero, digitoVerificador);
	}

	/**
	 * Entrega un RUT formateado <B>SIN GUIÓN</B>.
	 * <P>
	 * Este método existe para facilitar la entrega de datos a bases de datos en
	 * que el RUT se almacena de esta (incorrecta) manera.
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT.
	 * @param digitoVerificador
	 *            El dígito verificador.
	 * @return El RUT formateado sin guión.
	 * @see #RUTFormateadoSinGuion(long, char)
	 */
	static public String RUTFormateadoSinGuion(int numero, String digitoVerificador) {
		return RUTFormateadoSinGuion((long) numero, digitoVerificador.charAt(0));
	}

	/**
	 * Entrega un RUT formateado <B>SIN GUIÓN</B>.
	 * <P>
	 * Este método existe para facilitar la entrega de datos a bases de datos en
	 * que el RUT se almacena de esta (incorrecta) manera.
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT.
	 * @param digitoVerificador
	 *            El dígito verificador.
	 * @return El RUT formateado sin guión.
	 * @see #RUTFormateadoSinGuion(long, char)
	 */
	static public String RUTFormateadoSinGuion(String numero, char digitoVerificador) {
		return RUTFormateadoSinGuion(new Long(numero).longValue(), digitoVerificador);
	}

	/**
	 * Entrega un RUT formateado <B>SIN GUIÓN</B>.
	 * <P>
	 * Este método existe para facilitar la entrega de datos a bases de datos en
	 * que el RUT se almacena de esta (incorrecta) manera.
	 * <P>
	 * Registro de versiones:
	 * <UL>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero
	 *            El número de RUT.
	 * @param digitoVerificador
	 *            El dígito verificador.
	 * @return El RUT formateado sin guión.
	 * @see #RUTFormateadoSinGuion(long, char)
	 */
	static public String RUTFormateadoSinGuion(String numero, String digitoVerificador) {
		return RUTFormateadoSinGuion(new Long(numero).longValue(), digitoVerificador.charAt(0));
	}

	/**
	 * Método que obtiene el dígito verificador de un rut. Se asume que el
	 * dígito verificador esté incluido en el valor de parámetro. En caso que el
	 * RUT enviado sea nulo o su largo sea menor a 1 se retornará un char de
	 * espacio.
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </ul>
	 * <p>
	 * 
	 * @param sRut
	 *            rut completo, no importa el formato que tenga pero se asume
	 *            que contiene el dígito verificador.
	 * @return dígito verificador del rut enviado. Se retorna char de espacio en
	 *         caso de que el rut enviado como parámetro es nulo o su largo es
	 *         menor a 1
	 */
	public static char extraeDigitoVerificador(String sRut) {
		char dv = ' ';
		sRut = RUTUtil.reformateaRUT(sRut);
		if (sRut != null) {
			dv = sRut.charAt(sRut.length() - 1);
		}
		return dv;
	}

	/**
	 * Método que obtiene la parte numérica del rut. En caso de que el RUT
	 * enviado es nulo o su largo sea menor a 1 o que no pueda ser transformado
	 * a número se retornará -1
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </ul>
	 * <p>
	 * 
	 * @param sRut
	 *            rut completo, no importa el formato que tenga pero se asume
	 *            que contiene el dígito verificador.
	 * @return parte numérica del rut. Se retorna -1 en caso de que el rut
	 *         enviado como parámetro es nulo o su largo es menor a 1
	 * @since 1.2
	 */
	public static long extraeRUT(String sRut) {
		long rut = -1;
		sRut = RUTUtil.reformateaRUT(sRut);
		if (sRut != null) {
			try {
				rut = Long.parseLong(sRut.substring(0, sRut.indexOf("-")));
			}
			catch (NumberFormatException nfe) {
				return rut;
			}
		}
		return rut;
	}

	/**
	 * Formatea el rut con el formato xxxxxxxx-x, independiente del tipo de
	 * formato que posea el rut enviado. En caso de que el parámetro es nulo o
	 * de largo menor a 1 se retornará nulo
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 * </ul>
	 * <p>
	 * 
	 * @param rut
	 *            rut completo, no importa el formato que tenga pero se asume
	 *            que contiene el dígito verificador.
	 * @return rut formateado del tipo xxxxxxxx-x. En caso que RUT enviado sea
	 *         nulo o de largo menor a 1 se retornará nulo.
	 */
	public static String reformateaRUT(String rut) {
		if (rut == null || rut.trim().length() <= 1) {
			return null;
		}
		rut = StringUtil.eliminaCaracteres(rut, ".");
		rut = StringUtil.eliminaCaracteres(rut, "-");

		try {
			rut = RUTFormateado(rut.substring(0, rut.length() - 1), rut.substring(rut.length() - 1));
		}
		catch (NumberFormatException e) {
			rut = null;
		}
		return rut;
	}

	/**
	 * Método que valida el RUT. Se utiliza método:
	 * 
	 * @see RUTUtil#validaDigitoVerificador(long, char)
	 *      <p>
	 *      Registro de versiones:
	 *      <ul>
	 *      *
	 *      <LI>1.0 (24/02/2017, Sebastian Soto): versión inicial.
	 *      </ul>
	 *      <p>
	 * @param rut
	 *            RUT completo, no importa el formato que tenga pero se asume
	 *            que contiene el dígito verificador.
	 * @return true si es válido y false si no lo es.
	 */
	public static boolean validaRut(String rut) {
		rut = RUTUtil.reformateaRUT(rut);
		long numero = 0;
		if (rut == null) {
			return false;
		}
		try {
			numero = new Long(rut.substring(0, rut.length() - 2)).longValue();
		}
		catch (NumberFormatException nfe) {
			return false;
		}
		char digitoVerificador = rut.charAt(rut.length() - 1);
		return RUTUtil.validaDigitoVerificador(numero, digitoVerificador);
	}

}
