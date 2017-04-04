package cl.kubos.tc.utils;

import java.util.Locale;
import java.util.StringTokenizer;


/**
 * Clase con métodos estáticos que permiten la manipulación de strings.
 * Reemplaza a la antigua clase StrUtl.
 * <P>
 *
 * Registro de versiones:<UL>
 *
 * <LI>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
 *
 * <LI>1.1 12/04/2006 Miguel Farah (BCI): se corrigen los métodos divide*() y
 *         contenidoDe(), y se añaden los métodos arregloEnBlanco() y
 *         cuentaOcurrencias().
 *
 * <LI>1.2 18/04/2006 Miguel Farah (BCI): se corrige un severo error en
 *         contenidoDe() y se agregan los métodos censura(); se añade la
 *         advertencia en la javadocumentación de la constante EOL.
 *
 * <LI>1.3 12/06/2007 Eva Burgos Leal (Sentra)): Se agrega el método
 *         fracciona(), que divide un string en un arreglo de strings, cada
 *         uno con el mismo tamaño; se ajusta la javadocumentación para que
 *         cumpla con la normativa vigente.
 *
 * <li>1.4 21/12/2007 Francisca Craig (Ada): se agrega el método underscores().
 *
 * <li>1.5 08/06/2009 Miguel Farah (BCI): se añaden los métodos
 *         estaContenidoEn().
 *
 * <li>1.6 23/04/2009 Miguel Farah (BCI): Se agrega el método
 *         formateaNumeroDeTarjeta(String); se modifica el método
 *         fracciona(String, int) para permitir fraccionar de izquierda a
 *         derecha o de derecha a izquierda, según lo indicado mediante un
 *         parámetro nuevo; se añade fracciona(String, int) para no estropear
 *         el código preexistente.
 * <li>1.7 07/06/2010 Roberto Rodríguez Pino (ImageMaker IT): se añade la capacidad de censurar un string al
 * final en vez de solo al principio. Ver {@link #censura(String, char, int, boolean, boolean, short)}</li>
 * <li>1.8 18/10/2010 José Verdugo B. (TINet): Se agrega método {@link #esVacio(String)}. 
 *         Ya que esta clase no es un JavaBeans, se elimina implementación Serializable innecesaria junto al 
 *         atributo serialVersionUID.
 *
 * <li>1.9 03/05/2011 Eduardo Mascayano (TInet): Se agrega el método
 *         {@link #escapaAcentosYTildesHTML(String)}.
 * <li>1.10 23/11/2012 Felipe Rivera C.(TINet): Se agregan los métodos {@link #capitalizarString(String, Locale)} y
 * {@link #capitalizarString(String)}.
 * <li>2.0 18/06/2013 Claudia Carrasco H. (SEnTRA): Se agrega el método {@link #esAlfanumerico(String)}.
 * </UL>
 * <P>
 *
 * <B>Todos los derechos reservados Scotiabank</B>
 * <P>
 */
public class StringUtil {

	/**
	 * Constante de fin de línea. <B>NO TOCAR.</B>
	 * <p>
	 * La constante aquí definida tiene un solo caracter. Es vital recordar
	 * que en muchas partes el código fuente asume que esto es así (costumbre
	 * derivada de Unix), por lo que cambiar a, por ejemplo, un par CR/LF
	 * podría tener repercusiones negativas.
	 */
	static public String EOL="\n";

	/**
	 * Constante de fin de línea. <B>NO TOCAR.</B>
	 */
	static public char   EOLchar='\n';

	/**
	 * Indica que el corte de segmentos en los métodos fracciona() debe ser
	 * hecho de izquierda a derecha.
	 */
	public static short DE_IZQUIERDA_A_DERECHA = 1;

	/**
	 * Indica que el corte de segmentos en los métodos fracciona() debe ser
	 * hecho de derecha a izquierda.
	 */
	public static short DE_DERECHA_A_IZQUIERDA = 2;

	/**
	 * Se utiliza para reconocer inicio de abecedario en Ascii.
	 */
	static final int INICIO_ABECEDARIO_ASCII = 96;

	/**
	 * Se utiliza para reconocer fin de abecedario en Ascii.
	 */
	static final int FIN_ABECEDARIO_ASCII = 123;

	/**
	 * Se utiliza para reconocer números en Ascii.
	 */
	static final int INICIO_NUMEROS_ASCII = 47;

	/**
	 * Se utiliza para reconocer números en Ascii.
	 */
	static final int FIN_NUMEROS_ASCII = 58;

	/**
	 * Se utiliza para reconocer la letra "ñ".
	 */
	static final int LETRA_ENHE = 241;

	/**
	 * Se utiliza para reconocer la letra "á".
	 */
	static final int A_ACENTO = 225;

	/**
	 * Se utiliza para reconocer la letra "é".
	 */
	static final int E_ACENTO = 233;

	/**
	 * Se utiliza para reconocer la letra "í".
	 */
	static final int I_ACENTO = 237;

	/**
	 * Se utiliza para reconocer la letra "ó".
	 */
	static final int O_ACENTO = 243;

	/**
	 * Se utiliza para reconocer la letra "ú".
	 */
	static final int U_ACENTO = 250;

	/**
	 * Se utiliza para reconocer espacio en blanco en Ascii.
	 */
	static final int ESPACIO_ASCII = 32;

	/**
	 * Constante utilizada en el método censura() que indica censura al FINAL
	 * del string.
	 * */    
	static final public short CENSURA_AL_FINAL=0;

	/**
	 * Constante utilizada en el método censura() que indica censura al INICIO
	 * del string.
	 * */
	static final public short CENSURA_AL_INICIO=1;


	/**
	 * Entrega un string con el caracter que se indique repetido tantas veces
	 * como se indique. Por ejemplo, <code>repiteCaracter('a', 4)</code>
	 * entrega <code>"aaaa"</code>. Reemplaza al antiguo método repeatChar().
	 * <P>
	 * Registro de versiones:<UL>
	 * <LI>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param repiteme el caracter a repetir.
	 * @param largo el largo del string final; si se entrega un número negativo, se reemplaza por 0 (lo que implica que se retornará "").
	 * @return el string con la repetición indicada.
	 * @since 1.0
	 */
	static public String repiteCaracter(char repiteme, int largo) {
		String resultado="";
		if (largo<0) largo=0;
		for (int i=1;i<=largo;i++) {
			resultado+=repiteme;
		}
		return resultado;
	}



	/**
	 * Entrega un string con la repetición de un string indicado, hasta
	 * alcanzar un largo también indicado. Por ejemplo,
	 * <code>repiteString("abc", 8)</code> entrega <code>"abcabcab"</code>.
	 * <P>
	 * Registro de versiones:<UL>
	 * <LI>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param repiteme el string a repetir; si es null o vacío, se entrega "".
	 * @param largo el largo del string final; si se entrega un número negativo, se reemplaza por 0 (lo que implica que se retornará "").
	 * @return el string con la repetición indicada.
	 * @since 1.0
	 */
	static public String repiteString(String repiteme, int largo) {
		String resultado="";
		if ((repiteme==null)||repiteme.equals("")) return "";
		if (largo<0) largo=0;

		for (int i=1; i<=largo/repiteme.length()+1 ;i++) {
			resultado+=repiteme;
		}
		return resultado.substring(0,largo);
	}



	/**
	 * Entrega un string con la repetición de un string indicado, hasta
	 * alcanzar un largo también indicado Y RECORTANDO POR LA IZQUIERDA. Por
	 * ejemplo, <code>repiteStringPorLaDerecha("abc", 8)</code> entrega
	 * <code>"bcabcabc"</code>.
	 * <P>
	 * Registro de versiones:<UL>
	 * <LI>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param repiteme el string a repetir; si es null o vacío, se entrega "".
	 * @param largo el largo del string final; si se entrega un número negativo, se reemplaza por 0 (lo que implica que se retornará "").
	 * @return el string con la repetición indicada.
	 * @see #repiteString
	 * @since 1.0
	 */
	static public String repiteStringPorLaDerecha(String repiteme, int largo) {
		String resultado="";
		if ((repiteme==null)||repiteme.equals("")) return "";
		if (largo<0) largo=0;

		for (int i=1; i<=largo/repiteme.length()+1 ;i++) {
			resultado+=repiteme;
		}
		return resultado.substring(resultado.length()-largo, resultado.length());
	}



	/**
	 * Entrega un string con tantos espacios como se indique; utiliza a
	 * repiteCaracter() para esto.
	 * <P>
	 * Registro de versiones:<UL>
	 * <LI>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param largo el largo del string final.
	 * @return el string con la repetición indicada.
	 * @see #repiteCaracter
	 * @since 1.0
	 */
	static public String espacios(int largo) {
		return repiteCaracter(' ', largo);
	}



	/**
	 * Entrega un string con tantos asteriscos como se indique; utiliza a
	 * repiteCaracter() para esto.
	 * <P>
	 * Registro de versiones:<UL>
	 * <LI>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param largo el largo del string final.
	 * @return el string con la repetición indicada.
	 * @see #repiteCaracter
	 * @since 1.0
	 */
	static public String asteriscos(int largo) {
		return repiteCaracter('*', largo);
	}



	/**
	 * Entrega un string con tantos guiones como se indique; utiliza a
	 * repiteCaracter() para esto.
	 * <P>
	 * Registro de versiones:<UL>
	 * <LI>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param largo el largo del string final.
	 * @return el string con la repetición indicada.
	 * @see #repiteCaracter
	 * @since 1.0
	 */
	static public String guiones(int largo) {
		return repiteCaracter('-', largo);
	}



	/**
	 * Entrega un string con tantos ceros como se indique; utiliza a
	 * repiteCaracter() para esto.
	 * <P>
	 * Registro de versiones:<UL>
	 * <LI>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param largo el largo del string final.
	 * @return el string con la repetición indicada.
	 * @see #repiteCaracter
	 * @since 1.0
	 */
	static public String ceros(int largo) {
		return repiteCaracter('0', largo);
	}



	/**
	 * Entrega un string con tantos caracteres underscore como se indique;
	 * utiliza a repiteCaracter() para esto.
	 * <P>
	 * Registro de versiones:<UL>
	 * <LI>1.0 21/12/2007 Francisca Craig (Ada): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param largo el largo del string final.
	 * @return el string con la repetición indicada.
	 * @see #repiteCaracter
	 * @since 1.4
	 */
	static public String underscores(int largo) {
		return repiteCaracter('_', largo);
	}



	/**
	 * Entrega un arreglo de strings, con el tamaño que se indique, con cada
	 * elemento inicializado en <code>""</code>. Este método existe para
	 * comodidad del programador, pues un simple <code>new String[n]</code>
	 * dejaría cada elemento en <code>null</code>.
	 * <P>
	 * Registro de versiones:<UL>
	 * <LI>1.0 12/04/2006 Miguel Farah (BCI): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param cantidad La cantidad de elementos que tendrá el arreglo. Si se
	 *                 entrega 0, se retorna un arreglo con cero elementos;
	 *                 si se entrega un número negativo, se retorna
	 *                 <code>null</code>.
	 * @return Un arreglo con la cantidad especificada de strings vacíos.
	 * @since 1.1
	 */
	static public String[] arregloEnBlanco(int cantidad) {
		if (cantidad<0) return null;
		String[] s=new String[cantidad];
		for (int i=0;i<cantidad;i++) { s[i]=""; }
		return s;
	}



	/**
	 * Rellena un string para que tenga el largo indicado, utilizando el
	 * caracter de relleno especificado por la izquierda. Si el string es más
	 * largo que el parámetro correspondiente, no hace nada.
	 * <P>
	 * Registro de versiones:<UL>
	 * <LI>1.0  15/03/2005 Miguel Farah (BCI): versión inicial.
	 * <LI>1.0A 23/02/2006 Miguel Farah (BCI): se trae el método
	 *          rellenaStringPorLaIzquierda() desde la antigua clase StrUtl y
	 *          se pule el nombre y la javadocumentación.
	 * </UL><P>
	 *
	 * @param  texto   El string a rellenar; si es <CODE>null</CODE>, se reemplaza por <CODE>""</CODE>.
	 * @param  largo   El largo en que se quiere dejar el string; un valor negativo será reemplazado por cero.
	 * @param  relleno El caracter con que se va a rellenar el string.
	 * @return El string rellenado.
	 * @since  1.0
	 */
	static public String rellenaPorLaIzquierda(String texto, int largo, char relleno) {
		if (texto==null) texto="";
		if (largo<0) largo=0;
		if (texto.length()>=largo) return texto;
		return (repiteCaracter(relleno, largo-texto.length()) + texto);
	}



	/**
	 * Rellena un string para que tenga el largo indicado, utilizando el
	 * caracter de relleno especificado por la derecha. Si el string es más
	 * largo que el parámetro correspondiente, no hace nada.
	 * <P>
	 * Registro de versiones:<UL>
	 * <LI>1.0  15/03/2005 Miguel Farah (BCI): versión inicial.
	 * <LI>1.0A 23/02/2006 Miguel Farah (BCI): se trae el método
	 *          rellenaStringPorLaDerecha() desde la antigua clase StrUtl y
	 *          se pule el nombre y la javadocumentación.
	 * </UL><P>
	 *
	 * @param  texto   El string a rellenar; si es <CODE>null</CODE>, se reemplaza por <CODE>""</CODE>.
	 * @param  largo   El largo en que se quiere dejar el string; un valor negativo será reemplazado por cero.
	 * @param  relleno El caracter con que se va a rellenar el string.
	 * @return El string rellenado.
	 * @since  1.0
	 */
	static public String rellenaPorLaDerecha(  String texto, int largo, char relleno) {
		if (texto==null) texto="";
		if (largo<0) largo=0;
		if (texto.length()>=largo) return texto;
		return (texto + repiteCaracter(relleno, largo-texto.length()));
	}



	/**
	 * Rellena un string por la izquierda con espacios para que tenga el
	 * largo indicado. Se pretende que este método sea utilizado para el
	 * alineamiento de textos tabulados. Utiliza a rellenaPorLaIzquierda().
	 * <P>
	 * Registro de versiones:<UL>
	 * <LI>1.0  15/03/2005 Miguel Farah (BCI): versión inicial.
	 * <LI>1.0A 23/02/2006 Miguel Farah (BCI): se trae este método desde la antigua
	 *          clase StrUtl y se pule la javadocumentación.
	 * </UL><P>
	 *
	 * @param  texto   El string a rellenar.
	 * @param  largo   El largo en que se quiere dejar el string.
	 * @return El string rellenado.
	 * @see    #rellenaPorLaIzquierda(String, int, char)
	 * @since  1.0
	 */
	static public String rellenaConEspacios(String texto, int largo) {
		return rellenaPorLaIzquierda(texto, largo, ' ');
	}



	/**
	 * Rellena un string por la izquierda con ceros para que tenga el largo
	 * indicado. Se pretende que este método sea utilizado para el
	 * alineamiento de números que deban ser entregados a bases de datos que
	 * requieran este formato.
	 * <P>
	 * Registro de versiones:<UL>
	 * <LI>1.0  15/03/2005 Miguel Farah (BCI): versión inicial.
	 * <LI>1.0A 23/02/2006 Miguel Farah (BCI): se trae este método desde la antigua
	 *          clase StrUtl y se pule la javadocumentación.
	 * </UL><P>
	 *
	 * @param  texto   El string a rellenar.
	 * @param  largo   El largo en que se quiere dejar el string.
	 * @return El string rellenado.
	 * @see    #rellenaPorLaIzquierda(String, int, char)
	 * @since  1.0
	 */
	static public String rellenaConCeros(String texto, int largo) {
		return rellenaPorLaIzquierda(texto, largo, '0');
	}



	/**
	 * Completa un string para que tenga el largo indicado, utilizando el
	 * caracter de relleno especificado por la izquierda. Si el string es más
	 * largo que el parámetro correspondiente, <b>LO RECORTA A DICHA
	 * LONGITUD</b>.
	 * <P>
	 * Registro de versiones:<UL>
	 * <LI>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </UL><P>
	 *
	 * @param  texto   El string a completar; si es <CODE>null</CODE>, se reemplaza por <CODE>""</CODE>.
	 * @param  largo   El largo en que se quiere dejar el string; un valor negativo será reemplazado por cero.
	 * @param  relleno El caracter con que se va a completar el string.
	 * @return El string completado.
	 * @see    #rellenaPorLaIzquierda(String, int, char)
	 * @since  1.0
	 */
	static public String completaPorLaIzquierda(String texto, int largo, char relleno) {
		if (largo<0) largo=0;
		if (texto==null) texto="";
		if (texto.length()>=largo) return texto.substring(0,largo);
		return (repiteCaracter(relleno, largo-texto.length()) + texto);
	}



	/**
	 * Completa un string para que tenga el largo indicado, utilizando el
	 * caracter de relleno especificado por la derecha. Si el string es más
	 * largo que el parámetro correspondiente, <b>LO RECORTA A DICHA
	 * LONGITUD</b>.
	 * <P>
	 * Registro de versiones:<UL>
	 * <LI>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </UL><P>
	 *
	 * @param  texto   El string a completar; si es <CODE>null</CODE>, se reemplaza por <CODE>""</CODE>.
	 * @param  largo   El largo en que se quiere dejar el string; un valor negativo será reemplazado por cero.
	 * @param  relleno El caracter con que se va a completar el string.
	 * @return El string completado.
	 * @see    #rellenaPorLaDerecha(String, int, char)
	 * @since  1.0
	 */
	static public String completaPorLaDerecha(  String texto, int largo, char relleno) {
		if (largo<0) largo=0;
		if (texto==null) texto="";
		if (texto.length()>=largo) return texto.substring(0,largo);
		return (texto + repiteCaracter(relleno, largo-texto.length()));
	}



	/**
	 * Completa un string por la izquierda con espacios para que tenga el
	 * largo indicado. Utiliza a completaPorLaIzquierda().
	 * <P>
	 * Registro de versiones:<UL>
	 * <LI>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </UL><P>
	 *
	 * @param  texto   El string a completar.
	 * @param  largo   El largo en que se quiere dejar el string.
	 * @return El string completado.
	 * @see    #completaPorLaIzquierda(String, int, char)
	 * @since  1.0
	 */
	static public String completaConEspacios(String texto, int largo) {
		return completaPorLaIzquierda(texto, largo, ' ');
	}



	/**
	 * Reemplaza en un string todas las ocurrencias de un substring por otro
	 * (todos entregados como parámetro). Si cualquiera de los tres es
	 * <code>null</code> o el string a procesar o el substring a sacar son
	 * vacíos (<code>""</code>), se entrega el string inicial sin hacer nada.
	 * Nótese que NO se pone en este saco la condición <code>substring de
	 * reemplazo == ""</code>, pues esta ES una situación legítima.
	 * <p>
	 * Registro de versiones:<ul>
	 * <li>1.0  10/05/2005 Miguel Farah (BCI): versión inicial.
	 * <LI>1.0A 23/02/2006 Miguel Farah (BCI): se trae este método desde la antigua
	 *          clase StrUtl.
	 * </ul>
	 * <p>
	 *
	 * @param  texto El string sobre el que se va a operar.
	 * @param  sacar El substring a reemplazar.
	 * @param  poner El substring de reemplazo.
	 * @return El string modificado.
	 * @since  1.0
	 */
	static public String reemplazaTodo(String texto, String sacar, String poner) {
		if ((texto==null)||(texto.equals(""))) return texto;
		if ((sacar==null)||(sacar.equals(""))) return texto;
		if  (poner==null)                      return texto;

		String resultado="";
		int posicion = 0;
		int siguientePosicion;

		while ((siguientePosicion = texto.indexOf(sacar, posicion)) >= 0) {
			resultado += texto.substring(posicion, siguientePosicion) + poner;
			posicion   = siguientePosicion + sacar.length();
		}
		resultado += texto.substring(posicion);
		return resultado;
	}



	/**
	 * Reemplaza en un string todas las ocurrencias de un substring por un
	 * caracter (todos entregados como parámetro). Simplemente invoca a
	 * <code>reemplazaTodo(String, String, String)</code>.
	 * <P>
	 * Registro de versiones:<ul>
	 * <li>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </ul>
	 * <p>
	 *
	 * @param  texto El string sobre el que se va a operar.
	 * @param  sacar El substring a reemplazar.
	 * @param  poner El caracter de reemplazo.
	 * @return El string modificado.
	 * @see    #reemplazaTodo(String, String, String)
	 * @since  1.0
	 */
	static public String reemplazaTodo(String texto, String sacar, char   poner) {
		return reemplazaTodo(texto, sacar, String.valueOf(poner));
	}



	/**
	 * Reemplaza en un string todas las ocurrencias de un caracter por un
	 * string (todos entregados como parámetro). Simplemente invoca a
	 * <code>reemplazaTodo(String, String, String)</code>.
	 * <P>
	 * Registro de versiones:<ul>
	 * <li>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </ul>
	 * <p>
	 *
	 * @param  texto El string sobre el que se va a operar.
	 * @param  sacar El caracter a reemplazar.
	 * @param  poner El string de reemplazo.
	 * @return El string modificado.
	 * @see    #reemplazaTodo(String, String, String)
	 * @since  1.0
	 */
	static public String reemplazaTodo(String texto, char sacar,   String poner) {
		return reemplazaTodo(texto, String.valueOf(sacar), poner);
	}



	/**
	 * Reemplaza en un string todas las ocurrencias de un caracter por otro
	 * (todos entregados como parámetro). Simplemente invoca a
	 * <code>reemplazaTodo(String, String, String)</code>.
	 * <P>
	 * Registro de versiones:<ul>
	 * <li>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </ul>
	 * <p>
	 *
	 * @param  texto El string sobre el que se va a operar.
	 * @param  sacar El caracter a reemplazar.
	 * @param  poner El caracter de reemplazo.
	 * @return El string modificado.
	 * @see    #reemplazaTodo(String, String, String)
	 * @since  1.0
	 */
	static public String reemplazaTodo(String texto, char sacar,   char   poner) {
		return reemplazaTodo(texto, String.valueOf(sacar), String.valueOf(poner));
	}



	/**
	 * Elimina en un string todas las ocurrencias de un substring.
	 * Simplemente invoca a
	 * <code>reemplazaTodo(String, String, String)</code>.
	 * <P>
	 * Registro de versiones:<ul>
	 * <li>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </ul>
	 * <p>
	 *
	 * @param  texto El string sobre el que se va a operar.
	 * @param  sacar El substring a eliminar.
	 * @return El string modificado.
	 * @see    #reemplazaTodo(String, String, String)
	 * @since  1.0
	 */
	static public String eliminaTodo(String texto, String sacar) {
		return reemplazaTodo(texto, sacar, "");
	}



	/**
	 * Elimina en un string todas las ocurrencias de un caracter. Simplemente
	 * invoca a <code>reemplazaTodo(String, String, String)</code>.
	 * <P>
	 * Registro de versiones:<ul>
	 * <li>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </ul>
	 * <p>
	 *
	 * @param  texto El string sobre el que se va a operar.
	 * @param  sacar El caracter a eliminar.
	 * @return El string modificado.
	 * @see    #reemplazaTodo(String, String, String)
	 * @since  1.0
	 */
	static public String eliminaTodo(String texto, char   sacar) {
		return reemplazaTodo(texto, String.valueOf(sacar), "");
	}



	/**
	 * Reemplaza en un string la primera Y SOLAMENTE LA PRIMERA ocurrencia de
	 * un substring por otro (todos entregados como parámetro). Si cualquiera
	 * de los tres es <code>null</code> o el string a procesar o el substring
	 * a sacar son vacíos (<code>""</code>), se entrega el string inicial sin
	 * hacer nada. Nótese que NO se pone en este saco la condición
	 * <code>substring de reemplazo == ""</code>, pues esta ES una situación
	 * legítima.
	 * <p>
	 * Registro de versiones:<ul>
	 * <li>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </ul>
	 * <p>
	 *
	 * @param  texto El string sobre el que se va a operar.
	 * @param  sacar El substring a reemplazar.
	 * @param  poner El substring de reemplazo.
	 * @return El string modificado.
	 * @since  1.0
	 */
	static public String reemplazaUnaVez(String texto, String sacar, String poner) {
		if ((texto==null)||(texto.equals(""))) return texto;
		if ((sacar==null)||(sacar.equals(""))) return texto;
		if  (poner==null)                      return texto;

		String resultado=texto;
		int posicion=texto.indexOf(sacar);

		if (posicion>=0) {
			resultado=texto.substring(0, posicion)
					+poner
					+texto.substring(posicion+sacar.length());
		}
		return resultado;
	}



	/**
	 * Reemplaza en un string la primera Y SOLAMENTE LA PRIMERA ocurrencia de
	 * un substring por un caracter (todos entregados como parámetro).
	 * Simplemente invoca a
	 * <code>reemplazaUnaVez(String, String, String)</code>.
	 * <p>
	 * Registro de versiones:<ul>
	 * <li>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </ul>
	 * <p>
	 *
	 * @param  texto El string sobre el que se va a operar.
	 * @param  sacar El substring a reemplazar.
	 * @param  poner El caracter de reemplazo.
	 * @return El string modificado.
	 * @see    #reemplazaUnaVez(String, String, String)
	 * @since  1.0
	 */
	static public String reemplazaUnaVez(String texto, String sacar, char   poner) {
		return reemplazaUnaVez(texto, sacar, String.valueOf(poner));
	}



	/**
	 * Reemplaza en un string la primera Y SOLAMENTE LA PRIMERA ocurrencia de
	 * un caracter por un string (todos entregados como parámetro).
	 * Simplemente invoca a
	 * <code>reemplazaUnaVez(String, String, String)</code>.
	 * <p>
	 * Registro de versiones:<ul>
	 * <li>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </ul>
	 * <p>
	 *
	 * @param  texto El string sobre el que se va a operar.
	 * @param  sacar El caracter a reemplazar.
	 * @param  poner El string de reemplazo.
	 * @return El string modificado.
	 * @see    #reemplazaUnaVez(String, String, String)
	 * @since  1.0
	 */
	static public String reemplazaUnaVez(String texto, char   sacar, String poner) {
		return reemplazaUnaVez(texto, String.valueOf(sacar), poner);
	}



	/**
	 * Reemplaza en un string la primera Y SOLAMENTE LA PRIMERA ocurrencia de
	 * un caracter por otro (todos entregados como parámetro). Simplemente
	 * invoca a
	 * <code>reemplazaUnaVez(String, String, String)</code>.
	 * <p>
	 * Registro de versiones:<ul>
	 * <li>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </ul>
	 * <p>
	 *
	 * @param  texto El string sobre el que se va a operar.
	 * @param  sacar El caracter a reemplazar.
	 * @param  poner El caracter de reemplazo.
	 * @return El string modificado.
	 * @see    #reemplazaUnaVez(String, String, String)
	 * @since  1.0
	 */
	static public String reemplazaUnaVez(String texto, char   sacar, char   poner) {
		return reemplazaUnaVez(texto, String.valueOf(sacar), String.valueOf(poner));
	}



	/**
	 * Elimina en un string la primera Y SOLAMENTE LA PRIMERA ocurrencia de
	 * un substring. Simplemente invoca a
	 * <code>reemplazaUnaVez(String, String, String)</code>.
	 * <p>
	 * Registro de versiones:<ul>
	 * <li>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </ul>
	 * <p>
	 *
	 * @param  texto El string sobre el que se va a operar.
	 * @param  sacar El substring a eliminar.
	 * @return El string modificado.
	 * @see    #reemplazaUnaVez(String, String, String)
	 * @since  1.0
	 */
	static public String eliminaUnaVez(String texto, String sacar) {
		return reemplazaUnaVez(texto, sacar, "");
	}



	/**
	 * Elimina en un string la primera Y SOLAMENTE LA PRIMERA ocurrencia de
	 * un caracter. Simplemente invoca a
	 * <code>reemplazaUnaVez(String, String, String)</code>.
	 * <p>
	 * Registro de versiones:<ul>
	 * <li>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </ul>
	 * <p>
	 *
	 * @param  texto El string sobre el que se va a operar.
	 * @param  sacar El caracter a eliminar.
	 * @return El string modificado.
	 * @see    #reemplazaUnaVez(String, String, String)
	 * @since  1.0
	 */
	static public String eliminaUnaVez(String texto, char   sacar) {
		return reemplazaUnaVez(texto, String.valueOf(sacar), "");
	}



	/**
	 * Reemplaza en un string todas las ocurrencias de cada uno de los
	 * caracteres de un conjunto por un string (todos entregados como
	 * parámetro). La diferencia con {@link reemplazaTodo()} es que en dicho
	 * método, el string a reemplazar es considerado como una unidad,
	 * mientras que acá se reemplaza <b>cada</b> caracter en forma
	 * independiente. Por ejemplo:<pre>
	 * reemplazaTodo(      "dia de semana", "de", "x") entrega "día x semana"
	 * reemplazaCaracteres("dia de semana", "de", "x") entrega "xía xx sxmana"
	 * </pre>
	 * <p>
	 * Si cualquiera de los tres argumentos es <code>null</code> o el string
	 * a procesar o el string con caracteres a sacar son vacíos
	 * (<code>""</code>), se entrega el string inicial sin hacer nada. Nótese
	 * que NO se pone en este saco la condición <code>substring de reemplazo
	 * == ""</code>, pues esta ES una situación legítima.
	 *
	 * <p>
	 * Registro de versiones:<ul>
	 * <li>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </ul>
	 * <p>
	 *
	 * @param  texto El string sobre el que se va a operar.
	 * @param  sacar El string con los caracteres a reemplazar.
	 * @param  poner El substring de reemplazo.
	 * @return El string modificado.
	 * @see    #reemplazaTodo(String, String, String)
	 * @since  1.0
	 */
	static public String reemplazaCaracteres(String texto, String sacar, String poner) {
		if ((texto==null)||(texto.equals(""))) return texto;
		if ((sacar==null)||(sacar.equals(""))) return texto;
		if  (poner==null)                      return texto;

		for (int i=0;i<sacar.length();i++) {
			texto=reemplazaTodo(texto, sacar.charAt(i), poner);
		}
		return texto;
	}



	/**
	 * Reemplaza en un string todas las ocurrencias de cada uno de los
	 * caracteres de un conjunto por otro caracter (todos entregados como
	 * parámetro). Simplemente invoca a <code>reemplazaCaracteres(String,
	 * String, String)</code>.
	 * <p>
	 * Registro de versiones:<ul>
	 * <li>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </ul>
	 * <p>
	 *
	 * @param  texto El string sobre el que se va a operar.
	 * @param  sacar El string con los caracteres a reemplazar.
	 * @param  poner El caracter de reemplazo.
	 * @return El string modificado.
	 * @see    #reemplazaCaracteres(String, String, String)
	 * @since  1.0
	 */
	static public String reemplazaCaracteres(String texto, String sacar, char   poner) {
		return reemplazaCaracteres(texto, sacar, String.valueOf(poner));
	}



	/**
	 * Elimina en un string todas las ocurrencias de cada uno de los
	 * caracteres de un conjunto entregado como parámetro. Simplemente invoca
	 * a <code>reemplazaCaracteres(String, String, String)</code>.
	 * <p>
	 * Registro de versiones:<ul>
	 * <li>1.0 23/02/2006 Miguel Farah (BCI): versión inicial.
	 * </ul>
	 * <p>
	 *
	 * @param  texto El string sobre el que se va a operar.
	 * @param  sacar El string con los caracteres a eliminar.
	 * @return El string modificado.
	 * @see    #reemplazaCaracteres(String, String, String)
	 * @since  1.0
	 */
	static public String eliminaCaracteres(String texto, String sacar) {
		return reemplazaCaracteres(texto, sacar, "");
	}



	/**
	 * Reemplaza en un string todas las ocurrencias de los caracteres '&amp;'
	 * (ampersand), '&quot;' (comillas), '&lt;' (menor que) y '&gt;' (mayor
	 * que) por, respectivamente, "&amp;amp;", "&amp;quot;", "&amp;lt;" y
	 * "&amp;gt;". El propósito de esto es evitar que textos "normales" sean
	 * indebidamente interpretados si aparecen dentro de un contexto HTML. Si
	 * el string entregado es null o vacío, se retorna sin cambios.
	 * <p>
	 * Registro de versiones:<ul>
	 * <li>1.0  10/05/2005 Miguel Farah (BCI): versión inicial
	 * <li>1.0A 23/02/2006 Miguel Farah (BCI): se trae este método desde la antigua
	 *          clase StrUtl.
	 * </ul>
	 * <p>
	 *
	 * @param  s El string a convertir.
	 * @return El string modificado.
	 * @since  1.0
	 */
	static public String escapaCaracteresHTML(String s) {
		if ((s==null)||s.equals("")) return s;
		s=reemplazaTodo(s, "&",  "&amp;" );
		s=reemplazaTodo(s, "\"", "&quot;");
		s=reemplazaTodo(s, "<",  "&lt;"  );
		s=reemplazaTodo(s, ">",  "&gt;"  );
		return s;
	}



	/**
	 * Saca los ceros ('0') a la izquierda del string entregado. El propósito
	 * de este método es ayudar a filtrar datos numéricos que estén
	 * formateados con un relleno de ceros a la izquierda (por lo general
	 * para completar una longitud predeterminada).
	 * <P>
	 * Solamente se sacan los ceros iniciales, sin hacer otra consideración.
	 * Ejemplos de funcionamiento:<ul>
	 * <li><code> null               --&gt; null               </code>
	 * <li><code> ""                 --&gt; ""                 </code>
	 * <li><code> "0"                --&gt; ""                 </code>
	 * <li><code> "00"               --&gt; ""                 </code>
	 * <li><code> "000"              --&gt; ""                 </code>
	 * <li><code> "01"               --&gt; "1"                </code>
	 * <li><code> "001122"           --&gt; "1122"             </code>
	 * <li><code> "123"              --&gt; "123"              </code>
	 * <li><code> " 23"              --&gt; " 23"              </code>
	 * <li><code> "123456"           --&gt; "123456"           </code>
	 * <li><code> "1234567890"       --&gt; "1234567890"       </code>
	 * <li><code> "000123000"        --&gt; "123000"           </code>
	 * <li><code> "0010101010"       --&gt; "10101010"         </code>
	 * <li><code> "111111000011111"  --&gt; "111111000011111"  </code>
	 * <li><code> "      000000002"  --&gt; "      000000002"  </code>
	 * <li><code> "000000000  0002"  --&gt; "  0002"           </code>
	 * </ul>
	 * <p>
	 *
	 * Registro de versiones:<ul>
	 * <li>1.0  03/03/2003 Miguel Farah (BCI): versión inicial
	 * <li>1.0A 23/02/2006 Miguel Farah (BCI): se trae este método desde la antigua
	 *          clase StrUtl y se pule la javadocumentación para que cumpla
	 *          con la normativa vigente.
	 * </ul>
	 * <p>
	 *
	 * @param  texto el string a "descerificar".
	 * @return el string "descerificado".
	 * @since  1.0
	 */
	static public String sacaCeros(String texto) {
		boolean b=false;
		if (texto==null) return null;
		if (texto.equals("")) return "";
		int indice=texto.length();

		for (int i=0;i<texto.length();i++) {
			if (!(texto.substring(i,i+1).equals("0"))) {
				if (!b) { indice=i; b=true; i=texto.length()+1; }
			}
		}
		return texto.substring(indice,texto.length());
	}



	/**
	 * Recibe un arreglo de strings y los une en uno solo, poniendo el
	 * separador que se indique entremedio de cada par de strings originales.
	 * Este método <B>ES</B> una vil copia de la función <code>join</code> de
	 * Perl.
	 * <p>
	 *
	 * Ejemplos:<pre>
	 * - join("  ", {"hola", "que", "tal"})                 entregará "hola  que  tal" (festival).
	 * - join("/",  {"Pedro", " Pablo", "Vilma ", "Betty"}) entregará "Pedro/ Pablo/Vilma /Betty".
	 * - join("",   {"a", null, "i ", "o", "u"})            entregará "anulli ou".
	 * - join("$",  {"1", "  ", " 2 ", "  "})               entregará "1$  $ 2 $  ".
	 * - join(null, {"hola", null, "mundo"})                entregará "holanullnullnullmundo".
	 * - join("#",  {})                                     entregará "".
	 * - join("#",  null)                                   entregará "".
	 * </pre>
	 * <p>
	 *
	 * Registro de versiones:<ul>
	 * <li>1.0  11/10/2005 Miguel Farah (BCI): versión inicial.
	 * <LI>1.0A 23/02/2006 Miguel Farah (BCI): se trae este método desde la antigua
	 *          clase StrUtl.
	 * </ul>
	 * <p>
	 *
	 * @param separador el string a poner entremedio de cada elemento del
	 *                  arreglo. OJO: no es lo mismo <code>null</code> que
	 *                  <code>""</code> que <code>" "</code> (ver los
	 *                  ejemplos).<p>
	 *
	 * @param elementos los strings a unir; el arreglo puede ser
	 *                  <code>null</code> o tener cero elementos; si tiene
	 *                  elementos, estos pueden ser <code>null</code>.<p>
	 *
	 * @return un string con todos los elementos unidos; en el peor de los
	 *         casos se retorna <code>""</code>.
	 * @see <a href="http://perldoc.perl.org/functions/join.html">función join de Perl</a>
	 * @since 1.0
	 */
	static public String join(String separador, String[] elementos) {
		String x="";
		if (separador==null) separador="null"; // Truco sucio, pero funciona.
		if ((elementos!=null)&&(elementos.length>0)) {
			for(int i=0;i<elementos.length;i++) {
				x+=(elementos[i]+separador);
			}
		}
		if (x.length()>0) x=x.substring(0, x.length()-separador.length()); // Elimino el separador final.
		return x;
	}



	/**
	 * Recibe un arreglo de strings y los une en uno solo, poniendo el
	 * separador que se indique entremedio de cada par de strings originales.
	 * La diferencia con el método join() es que aquí se descartan los
	 * strings nulos, vacíos (<code>""</code>) o blancos (<code>" "</code>,
	 * ectétera).
	 * <p>
	 *
	 * Ejemplos:<pre>
	 * - joinFiltrado("  ", {"hola", "que", "tal"})                 entregará "hola  que  tal" (festival).
	 * - joinFiltrado("/",  {"Pedro", " Pablo", "Vilma ", "Betty"}) entregará "Pedro/Pablo/Vilma/Betty".
	 * - joinFiltrado("",   {"a", null, "i ", "o", "u"})            entregará "aiou".
	 * - joinFiltrado("$",  {"1", "  ", " 2 ", "  "})               entregará "1$2".
	 * - joinFiltrado(null, {"hola", null, "mundo"})                entregará "holamundo".
	 * - joinFiltrado("#",  {})                                     entregará "".
	 * - joinFiltrado("#",  null)                                   entregará "".
	 * </pre>
	 * <p>
	 *
	 * Registro de versiones:<ul>
	 * <li>1.0  11/10/2005 Miguel Farah (BCI): versión inicial.
	 * <LI>1.0A 23/02/2006 Miguel Farah (BCI): se trae este método desde la antigua
	 *          clase StrUtl.
	 * </ul>
	 * <p>
	 *
	 * @param separador el string a poner entremedio de cada elemento del
	 *                  arreglo; si se entrega <code>null</code>, se
	 *                  reemplazará por <code>""</code>. Ojo: no es lo mismo
	 *                  <code>""</code> que <code>" "</code> (ver los
	 *                  ejemplos).<p>
	 *
	 * @param elementos los strings a unir; el arreglo puede ser
	 *                  <code>null</code> o tener cero elementos; si tiene
	 *                  elementos, estos pueden ser <code>null</code> (pero
	 *                  serán descartados).<p>
	 *
	 * @return un string con todos los elementos (que no se descartaron)
	 *         unidos; en el peor de los casos se retorna <code>""</code>.
	 * @see #join
	 * @since 1.0
	 */
	static public String joinFiltrado(String separador, String[] elementos) {
		String x="";
		if (separador==null) separador="";
		if ((elementos!=null)&&(elementos.length>0)) {
			for(int i=0;i<elementos.length;i++) {
				String elemento=elementos[i];
				if ((elemento!=null)&&(!(elemento.trim().equals("")))) x+=(elemento.trim()+separador);
				// Nótese el trim() del elemento - esto es intencional.
			}
		}
		if (x.length()>0) x=x.substring(0, x.length()-separador.length()); // Elimino el separador final.
		return x;
	}



	/**
	 * Recibe un arreglo de strings y retorna una lista separada por comas
	 * basada en su contenido. Lo único que hace es invocar a
	 * <code>joinFiltrado(", ", palabras)</code>.
	 * <p>
	 *
	 * Registro de versiones:<ul>
	 * <li>1.0  12/07/2005 Miguel Farah (BCI): versión inicial
	 * <li>2.0  14/10/2005 Miguel Farah (BCI): se reescribe el método, usando
	 *          joinFiltrado() como base.
	 * <LI>2.0A 23/02/2006 Miguel Farah (BCI): se trae este método desde la antigua
	 *          clase StrUtl.
	 * </ul>
	 * <p>
	 *
	 * @param palabras los strings a unir; el arreglo puede ser
	 *                 <code>null</code> o tener cero elementos; si tiene
	 *                 elementos, estos pueden ser <code>null</code> (pero
	 *                 serán descartados).
	 * @return un string con todos los elementos (que no se descartaron)
	 *         unidos; en el peor de los casos se retorna <code>""</code>.
	 * @see #joinFiltrado
	 * @since 1.0
	 */
	static public String listaSeparadaPorComas(String[] palabras) {
		return joinFiltrado(", ", palabras);
	}



	/**
	 * Parte un string, utilizando como separadores todos los caracteres
	 * delimitadores que se indiquen.
	 * <p>
	 *
	 * Registro de versiones:<ul>
	 * <li>1.0  11/10/2005 Miguel Farah (BCI): versión inicial.
	 * <li>1.0A 23/02/2006 Miguel Farah (BCI): se trae el método divideString()
	 *          desde la antigua clase StrUtl y se pule el nombre y la
	 *          javadocumentación.
	 * <li>1.1  12/04/2006 Miguel Farah (BCI): se cambia la funcionalidad: antes,
	 *          el entregar un string a dividir vacío (<code>""</code>) o
	 *          compuesto solamente por delimitadores implicaba que se
	 *          retornaría <code>null</code>; ahora, ante estas situaciones
	 *          se retorna un arreglo de cero elementos; se retorna
	 *          <code>null</code> únicamente cuando el string a dividir
	 *          también lo es.
	 * </ul>
	 * <p>
	 *
	 * @param divideme      El string a dividir.<p>
	 *
	 * @param delimitadores Los caracteres delimitadores que se usarán; si se
	 *                      entrega <code>null</code> se reemplazará por
	 *                      <code>""</code>; esto último ES válido e implica
	 *                      que el string no se partirá (claro que entonces
	 *                      no tiene mucho sentido invocar a este
	 *                      método...).<p>
	 *
	 * @return un arreglo de strings, con cada uno de los segmentos en que se
	 *         dividió el string inicial; los delimitadores NO serán parte de
	 *         ninguno; <code>null</code> es un resultado válido, producto de
	 *         haber entregado un string nulo; si se entrega un string vacío
	 *         o compuesto solamente por delimitadores, el resultado será un
	 *         arreglo de cero elementos (independientemente de lo que
	 *         contuviere el parámetro delimitadores).<p>
	 *
	 * @see <a href="http://java.sun.com/j2se/1.4.2/docs/api/java/util/StringTokenizer.html">StringTokenizer</a>
	 * @since 1.0
	 */
	static public String[] divide(String divideme, String delimitadores) {
		if (divideme==null) return null;
		if (delimitadores==null) delimitadores="";

		StringTokenizer t=new StringTokenizer(divideme, delimitadores);
		String[] resultado=new String[t.countTokens()];
		int i=0;

		while (t.hasMoreTokens()) {
			resultado[i++]=t.nextToken();
		}
		return resultado;
	}



	/**
	 * Parte un string, utilizando el caracter separador que se indique.
	 * Utiliza a <code>divide(String, String)</code>.
	 * <p>
	 * Registro de versiones:<ul>
	 * <li>1.0  23/02/2006 Miguel Farah (BCI): versión inicial.
	 * <li>1.0A 12/04/2006 Miguel Farah (BCI): se actualiza la javadocumentación
	 *          para que concuerde con los cambios que se hicieron al método
	 *          base.
	 * </ul>
	 * <p>
	 *
	 * @param divideme    El string a dividir.<p>
	 *
	 * @param delimitador El caracter delimitador que se usará.
	 *
	 * @return un arreglo de strings, con cada uno de los segmentos en que se
	 *         dividió el string inicial; el delimitador NO será parte de
	 *         ninguno; <code>null</code> es un resultado válido, producto de
	 *         haber entregado un string nulo; si se entrega un string vacío
	 *         o compuesto solamente por delimitadores, el resultado será un
	 *         arreglo de cero elementos (independientemente de lo que
	 *         contuviere el parámetro delimitador).<p>
	 *
	 * @see #divide(String, String)
	 * @since 1.0
	 */
	static public String[] divide(String divideme, char delimitador) {
		return divide(divideme, String.valueOf(delimitador));
	}




	/**
	 * Convierte un arreglo de strings en un solo string multilínea,
	 * separando cada elemento del arreglo con caracteres EOL. Si el arreglo
	 * entregado es <code>null</code> o tiene cero (0) elementos, se retorna
	 * el string vacío (<code>""</code>).
	 * <p>
	 * Registro de versiones:<ul>
	 * <li>1.0  11/05/2005 Miguel Farah (BCI): versión inicial.
	 * <li>1.0A 23/02/2006 Miguel Farah (BCI): se trae este método desde la antigua
	 *          clase StrUtl.
	 * </ul>
	 * <p>
	 *
	 * @param  arreglo El arreglo de strings.
	 * @return El contenido del arreglo unido en un solo string.
	 * @since  1.0
	 */
	static public String convierteArregloEnMultilinea(String[] arreglo) {
		String multilinea="";
		if (arreglo==null) return "";

		for (int i=0;i<arreglo.length;i++) {
			multilinea+=arreglo[i]+EOL;
		}
		return multilinea;
	}




	/**
	 * Recibe un objeto cualquiera y entrega un string con su contenido;
	 * reconoce los strings, los arreglos, los <code>Hashtable</code> y las
	 * clases que implementan la interfaz <code>java.util.List</code>
	 * (<code>Vector</code>, <code>ArrayList</code>, etcétera), y muestra el
	 * contenido de cada uno de sus elementos; se puede pensar en este método
	 * como la versión "sofisticada" y recursiva del método
	 * <code>toString()</code>.
	 * <p>
	 * Por supuesto, para todas las clases que no reconoce como caso especial
	 * (las arriba nombradas), simplemente invoca al <code>toString()</code>
	 * respectivo.
	 * <p>
	 *
	 * Ejemplo:<pre>
	 * String[] c=new String[3]; c[0]="hola"; c[2]="mundo";
	 *
	 * c.toString()   entregará: [Ljava.lang.String;@16f0472    (YMMV)
	 * contenidoDe(c) entregará: {"hola", null, "mundo"}
	 * </pre><p>
	 *
	 * Registro de versiones:<ul>
	 * <li>1.0  11/10/2005 Miguel Farah (BCI): versión inicial.
	 * <li>1.0A 23/02/2006 Miguel Farah (BCI): se trae este método desde la antigua
	 *          clase StrUtl.
	 * <li>1.1  12/04/2006 Miguel Farah (BCI): se añade el caso "objeto es string",
	 *          para citar su contenido con comillas (así, el ejemplo dado
	 *          antes ya no aparecerá como <code>{hola, null, mundo}</code>).
	 * <li>1.2  18/04/2006 Miguel Farah (BCI): se descubre un serio error: este
	 *          método consideraba arreglos de objetos, pero NO tomaba en
	 *          cuenta arreglos de <i>tipos de datos primitivos</i> (int[],
	 *          char[], boolean[], etcétera). Esta imperdonable omisión ha
	 *          sido corregida.
	 * </ul>
	 * <p>
	 *
	 * @param objeto El objeto cuyo contenido se quiere desplegar.
	 * @return El contenido del objeto; si éste es <code>null</code> se retorna <code>"null"</code>.
	 * @since 1.0
	 */
	static public String contenidoDe(Object objeto) {
		String s="";

		if (objeto==null) {
			s="null";

		} 
		else if (objeto.getClass().isArray()) {
			s="{";
			if        (objeto instanceof boolean[]) {
				boolean[] arreglo=(boolean[])objeto;
				for (int i=0; i<arreglo.length; i++) {
					s+=arreglo[i] + ((i==arreglo.length-1)?"":", ");
				}
			} 
			else if (objeto instanceof char[]) {
				char[] arreglo=(char[])objeto;
				for (int i=0; i<arreglo.length; i++) {
					s+="'"+arreglo[i]+"'" + ((i==arreglo.length-1)?"":", ");
				}
			} 
			else if (objeto instanceof byte[]) {
				byte[] arreglo=(byte[])objeto;
				for (int i=0; i<arreglo.length; i++) {
					s+=arreglo[i] + ((i==arreglo.length-1)?"":", ");
				}
			} 
			else if (objeto instanceof short[]) {
				short[] arreglo=(short[])objeto;
				for (int i=0; i<arreglo.length; i++) {
					s+=arreglo[i] + ((i==arreglo.length-1)?"":", ");
				}
			} 
			else if (objeto instanceof int[]) {
				int[] arreglo=(int[])objeto;
				for (int i=0; i<arreglo.length; i++) {
					s+=arreglo[i] + ((i==arreglo.length-1)?"":", ");
				}
			} 
			else if (objeto instanceof long[]) {
				long[] arreglo=(long[])objeto;
				for (int i=0; i<arreglo.length; i++) {
					s+=arreglo[i] + ((i==arreglo.length-1)?"":", ");
				}
			} 
			else if (objeto instanceof float[]) {
				float[] arreglo=(float[])objeto;
				for (int i=0; i<arreglo.length; i++) {
					s+=arreglo[i] + ((i==arreglo.length-1)?"":", ");
				}
			} 
			else if (objeto instanceof double[]) {
				double[] arreglo=(double[])objeto;
				for (int i=0; i<arreglo.length; i++) {
					s+=arreglo[i] + ((i==arreglo.length-1)?"":", ");
				}
			} 
			else { // Arreglos de objetos propiamente tales.
				Object[] arreglo=(Object[])objeto;
				for (int i=0; i<arreglo.length; i++) {
					s+=contenidoDe(arreglo[i]) + ((i==arreglo.length-1)?"":", ");
				}
			}
			s+="}";

		} 
		else if (objeto instanceof java.lang.String) {
			s="\""+objeto.toString()+"\"";

		} 
		else {
			s=objeto.toString();

		}
		return s;
	}



	/**
	 * Cuenta las ocurrencias de un string dado dentro de otro.
	 * <p>
	 * Ojo: la superposición de substrings no se considera. Esto significa
	 * que <code>cuentaOcurrencias("ana", "banana")</code> retorna <b>1</b> y
	 * no 2.
	 * <p>
	 *
	 * Registro de versiones:<ul>
	 * <li>1.0 12/04/2006 Miguel Farah (BCI): versión inicial.
	 * </ul><p>
	 *
	 * @param loBuscado El string que se va a buscar.
	 * @param enDonde   El string dentro del cual se va a buscar.
	 * @return El número de veces que aparece el primer string dentro del
	 *         segundo. Ojo: si cualquiera de los dos es <code>null</code>,
	 *         se retorna -1; si cualquiera de los dos es <code>""</code>, se
	 *         retorna 0.
	 * @since  1.1
	 */
	static public int cuentaOcurrencias(String loBuscado, String enDonde) {
		if (loBuscado==null) return -1;
		if (  enDonde==null) return -1;
		if (loBuscado.equals("")) return 0;
		if (  enDonde.equals("")) return 0;

		int ocurrencias=0;
		while (!(enDonde.equals(""))) {
			int posicion=enDonde.indexOf(loBuscado);
			if (posicion==-1) {
				enDonde="";
			}
			else {
				enDonde=enDonde.substring(posicion+loBuscado.length());
				ocurrencias++;
			}
		}
		return ocurrencias;
	}



	/**
	 * Cuenta las ocurrencias de un caracter dado dentro de un string.
	 * Simplemente llama a <code>cuentaOcurrencias(String, String)</code>.
	 * <p>
	 *
	 * Registro de versiones:<ul>
	 * <li>1.0 12/04/2006 Miguel Farah (BCI): versión inicial.
	 * </ul><p>
	 *
	 * @param  loBuscado El caracter que se va a buscar.
	 * @param  enDonde   El string dentro del cual se va a buscar.
	 * @return El número de veces que aparece el caracter dentro del string.
	 * @see    #cuentaOcurrencias(String, String)
	 * @since  1.1
	 */
	static public int cuentaOcurrencias(char   elBuscado, String enDonde) {
		return cuentaOcurrencias(String.valueOf(elBuscado), enDonde);
	}



	/**
	 * "Censura" un string, reemplazando todos los caracteres que contenga
	 * por el caracter especificado, excepto la cantidad especificada de los
	 * últimos caracteres al final del string. Por ejemplo,
	 * <code>censura("Esta es una prueba.", '*', 6, false, false)</code>
	 * entrega <code>"*************rueba."</code>.
	 * </pre>
	 * <p>
	 * Este método es la versión generalizada de {@link #censura(String)} y usa {@link #censura(String texto, 
	 * char censurador, int caracteresLibres, boolean respetarEspacios, boolean respetarEOLs, short CENSURA_AL_INICIO)}
	 * <p>
	 * Ojo: la cantidad de caracteres libres a dejar al final NO es afectada
	 * por los parámetros respetarEspacios y respetarEols. Así, en
	 * <code>censura("Esta frase es corta.\n", '_', 10, *, *)</code>, el
	 * substring que no se tocará es <code>"es corta.\n"</code>.
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 18/04/2006 Miguel Farah (BCI): versión inicial.</li>
	 * <li>1.1 14/06/2010 Roberto Rodríguez (ImageMaker IT): se reemplaza por el uso de una nueva versión método 
	 * censura.Ver {@link #censura(String texto, char censurador, int caracteresLibres, boolean respetarEspacios, 
	 * boolean respetarEOLs, short lugar)}
	 * </ul>
	 * <p>
	 *
	 * @param  texto El string sobre el que se va a operar. Si es
	 *               <code>null</code>, se reemplazará por <code>""</code> y
	 *               se seguirá adelante.
	 *
	 * @param  censurador El caracter a usar para tapar el contenido original.
	 *
	 * @param  caracteresLibres El largo del substring al final que no se
	 *                          tocará. Si este valor es negativo, se retorna
	 *                          el string original sin hacer nada. El valor
	 *                          cero es válido e implica que el string
	 *                          completo será censurado.
	 *
	 * @param  respetarEspacios true: los caracteres espacio no son
	 *                          censurados; false: se reemplazan por el
	 *                          caracter censurador.
	 *
	 * @param  respetarEOLs     true: los fines de línea (caracteres EOL) no
	 *                          son censurados; false: se reemplazan por el
	 *                          caracter censurador.
	 *
	 * @return El string "censurado", <code>""</code> en el peor caso; nunca
	 *         se retorna <code>null</code>.
	 *
	 * @since  1.2
	 */
	static public String censura(String texto, char censurador, int caracteresLibres, boolean respetarEspacios, boolean respetarEOLs) {
		return censura(texto,censurador,caracteresLibres,respetarEspacios,respetarEOLs,CENSURA_AL_INICIO);
	}



	/**
	 * "Censura" un string (por lo general un número de cuenta corriente o
	 * de tarjeta de crédito), reemplazando todo su contenido por asteriscos,
	 * excepto los últimos cuatro caracteres (dígitos de la cuenta).
	 * Simplemente invoca a <code>censura(texto, '*', 4, false, false,CENSURA_AL_INICIO)</code>.
	 * <p>
	 * Registro de versiones:<ul>
	 * <li>1.0 18/04/2006 Miguel Farah (BCI): versión inicial.</li>
	 * <li>1.1 14/06/2010 Roberto Rodríguez (ImageMaker IT): Ocupa una nueva version del método para censurar Ver
	 * {@link #censura(String texto, char censurador, int caracteresLibres,boolean respetarEspacios, boolean respetarEOLs, 
	 * short lugar)}
	 * </ul>
	 * <p>
	 *
	 * @param  texto El string sobre el que se va a operar.
	 * @return El string "censurado".
	 * @see    #censura(String, char, int, boolean, boolean, short)
	 * @since  1.2
	 */
	static public String censura(String texto) {
		return censura(texto, '*', 4, false, false,CENSURA_AL_INICIO);
	}

	/**
	 * "Censura" un string, reemplazando todos los caracteres que contenga por el caracter especificado, 
	 * excepto la cantidad especificada ya sea al inicio o al final de este según se
	 * especifique. Un ejemplo dejando sin censura el inicio,
	 * <code>censura("Esta es una prueba.", '*', 6, false, false, CENSURA_AL_FINAL)</code>
	 * entrega <code>"Esta e*******************"</code>.
	 * </pre>
	 * <code>censura("Esta es una prueba.", '*', 6, false, false, CENSURA_AL_INICIO)</code>
	 * entrega
	 * <code>****** una prueba</code>
	 *
	 * Registro de versiones:<ul>
	 * <li>1.0 03/06/2010 Roberto Rodríguez (ImageMaker): versión inicial.
	 * </ul>
	 * <p>
	 *
	 * @param  texto El string sobre el que se va a operar. Si es
	 *               <code>null</code>, se reemplazará por <code>""</code> y
	 *               se seguirá adelante.
	 *
	 * @param  censurador El caracter a usar para tapar el contenido original.
	 *
	 * @param  caracteresLibres El largo del substring al final que no se
	 *                          tocará. Si este valor es negativo, se retorna
	 *                          el string original sin hacer nada. El valor
	 *                          cero es válido e implica que el string
	 *                          completo será censurado.
	 *
	 * @param  respetarEspacios true: los caracteres espacio no son
	 *                          censurados; false: se reemplazan por el
	 *                          caracter censurador.
	 *
	 * @param  respetarEOLs     true: los fines de línea (caracteres EOL) no
	 *                          son censurados; false: se reemplazan por el
	 *                          caracter censurador.
	 *
	 * @param	inicio	CENSURA_AL_INICIO: la censura es puesta al final del string. Ej: XXXsura
	 * 					CENSURA_AL_FINAL:  la censura es puesta al inicio del string. Ej: CensXXX 
	 * 
	 * 
	 * @return El string "censurado", <code>""</code> en el peor caso; nunca
	 *         se retorna <code>null</code>.
	 *
	 * @since  1.7
	 */
	static public String censura (String texto, char censurador, int caracteresLibres, 
			boolean respetarEspacios, boolean respetarEOLs, short lugar){
		if (texto==null) texto="";
		if (caracteresLibres<0) return texto;

		int largo=texto.length();
		if (largo<=caracteresLibres) return texto;

		String resultado="";


		if ((respetarEspacios==false)&&(respetarEOLs==false)) {
			resultado=repiteCaracter(censurador, largo-caracteresLibres);
		} 
		if (lugar == CENSURA_AL_FINAL) {
			for (int i=caracteresLibres;i<texto.length();i++) {
				char c=texto.charAt(i);
				if (  ((c==' '    )&&respetarEspacios)
						||((c==EOLchar)&&respetarEOLs)
						) {
					resultado+=c;
				}
				else {
					resultado+=censurador;
				}
			}
			resultado = texto.substring(0,caracteresLibres) + resultado;
			return resultado;		            
		}
		else {
			for (int i=0;i<texto.length()-caracteresLibres;i++) {
				char c=texto.charAt(i);
				if (  ((c==' '    )&&respetarEspacios)||((c==EOLchar)&&respetarEOLs) ){
					resultado+=c;
				}
				else {
					resultado+=censurador;
				}
			}
			resultado+=texto.substring(largo-caracteresLibres,largo);
			return resultado;                    
		}
	}


	/**
	 * Divide un string en segmentos de igual tamaño, especificado por el
	 * usuario, cortando de izquierda a derecha o de derecha a izquierda
	 * (obviamente, el último o el primer segmento, respectivamente, puede
	 * ser más corto).
	 * <P>
	 *
	 * Registro de versiones:<UL>
	 *
	 * <LI>1.0 11/06/2007 Eva Burgos Leal (SEnTRA): versión inicial.
	 * <LI>2.0 23/04/2010 Miguel Farah (BCI): se añade la opción de cortar de
	 *         izquierda a derecha o de derecha a izquierda, se modifican los
	 *         parámetros texto y largo para que sean <code>final</code>, y
	 *         se efectúan optimizaciones menores en el código fuente.
	 * </UL>
	 *
	 * @param texto El texto que se desea fraccionar. Si se entrega
	 *              <CODE>null</CODE>, se retorna lo mismo. Si se entrega un
	 *              string vacío, se retorna un arreglo con un solo elemento
	 *              (el string vacío).
	 *
	 * @param largo Tamaño de las fracciones resultantes. Si se entrega un
	 *              número negativo o nulo, se reemplaza por el largo del
	 *              string entregado.
	 *
	 * @param orden El orden en que se va a cortar: de izquierda a derecha o
	 *              de derecha a izquierda. Si se especifica un valor
	 *              inválido, se utilizará el primer criterio.
	 *
	 * @return Un arreglo con los strings resultantes de la división.
	 * @since 1.3
	 */
	static public String[] fracciona(final String texto, final int largo, final short orden) {
		if (texto==null) {
			return null;
		}

		if (texto.equals("")) {
			return arregloEnBlanco(1);
		}

		int   elLargo=(largo<=0)?texto.length():largo;
		short elOrden=(orden==DE_DERECHA_A_IZQUIERDA)?orden:DE_IZQUIERDA_A_DERECHA;

		int secciones= texto.length()/elLargo;
		int resto    = texto.length()%elLargo;
		if (resto > 0) {
			secciones++;
		}
		String[] resultado = new String[secciones];

		if ( (elOrden==DE_IZQUIERDA_A_DERECHA) || (resto==0)) {
			for(int i=0; i<secciones-1; i++) {
				resultado[i] = texto.substring(i*elLargo, (i+1)*elLargo);
			}
			resultado[secciones-1]=texto.substring(elLargo*(secciones-1));

		}
		else {
			resultado[0]=texto.substring(0, resto);
			for(int i=1; i<secciones; i++) {
				resultado[i] = texto.substring(resto+(i-1)*elLargo, resto+i*elLargo);
			}
		}

		return resultado;
	}



	/**
	 * Divide un string en segmentos de igual tamaño, especificado por el
	 * usuario, cortando de izquierda a derecha. Simplemente llama a
	 * <code>fracciona(texto, largo, DE_IZQUIERDA_A_DERECHA)</code>.
	 * <P>
	 * Nota: este método tiene la misma firma que la versión antigua de
	 * <code>fracciona()</code> (antes que se añadiera el flag
	 * <code>orden</code>). Se creó para no estropear todo el código fuente
	 * preexistente en que las llamadas a <code>fracciona()</code> no
	 * especifican el orden de corte; además, el orden de izquierda a derecha
	 * es más usado que el otro, por lo que resulta conveniente usarlo por
	 * defecto.
	 *
	 * Registro de versiones:<UL>
	 * <LI>1.0 23/04/2010 Miguel Farah (BCI): versión inicial.
	 * </UL>
	 *
	 * @param texto El texto que se desea fraccionar.
	 * @param largo Tamaño de las fracciones resultantes.
	 * @return Un arreglo con los strings resultantes de la división.
	 * @see fracciona(String, int, short)
	 * @since 1.6
	 */
	public static String[] fracciona(String texto, int largo) {
		return fracciona(texto, largo, DE_IZQUIERDA_A_DERECHA);
	}



	/**
	 * Indica si un string está contenido en un arreglo de strings.
	 * <P>
	 *
	 * Registro de versiones:<UL>
	 *
	 * <LI>1.0 08/06/2009 Miguel Farah (BCI): versión inicial.
	 *
	 * @param buscame El string que se está buscando. <CODE>null</CODE> ES un
	 *                argumento válido (y se retornará <CODE>true</CODE> si
	 *                el arreglo contiene al menos un elemento que también lo
	 *                sea).
	 *
	 * @param lista El arreglo de strings. Si se entrega un arreglo nulo o
	 *              que contenga 0 elementos, se retornará <CODE>false</CODE>.
	 *
	 * @param conOSinMayusculas Si es <CODE>false</CODE>, la comparación será
	 *                          exacta <CODE>("Abc"!="ABC")</CODE>; si es
	 *                          <CODE>true</CODE>, no se hará diferencia
	 *                          entre mayúsculas y minúsculas
	 *                          <CODE>("Abc"=="ABC")</CODE>.
	 *
	 * @return true si el string entregado está al menos una vez en el
	 *         arreglo.
	 *
	 * @since 1.5
	 */
	static public boolean estaContenidoEn(final String buscame, final String[] lista, final boolean conOSinMayusculas) {
		if (  (lista==null)
				||(lista.length==0)
				) {
			return false;
		}

		for (int i=0; i<lista.length; i++) {
			if        ( (buscame==null)&&(lista[i]==null) ) {
				return true;
			} 
			else if ( (buscame!=null)&&(lista[i]!=null) ) {
				if (conOSinMayusculas?buscame.equalsIgnoreCase(lista[i])
						:buscame.equals(lista[i])
						) {
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * Indica si un string está contenido en un arreglo de strings.
	 * Simplemente llama a <CODE>estaContenidoEn(buscame, lista, false)</CODE>.
	 * <P>
	 *
	 * Registro de versiones:<UL>
	 *
	 * <LI>1.0 08/06/2009 Miguel Farah (BCI): versión inicial.
	 *
	 * @param buscame El string que se está buscando. <CODE>null</CODE> ES un
	 *                argumento válido (y se retornará <CODE>true</CODE> si
	 *                el arreglo contiene al menos un elemento que también lo
	 *                sea).
	 *
	 * @param lista El arreglo de strings. Si se entrega un arreglo nulo o
	 *              que contenga 0 elementos, se retornará <CODE>false</CODE>.
	 *
	 * @return true si el string entregado está al menos una vez en el
	 *         arreglo.
	 *
	 * @see   #estaContenidoEn(String, String[], boolean)
	 * @since 1.5
	 */
	static public boolean estaContenidoEn(final String buscame, final String[] lista) {
		return estaContenidoEn(buscame, lista, false);
	}



	/**
	 * Formatea un string correspondiente a un número de tarjeta (de crédito
	 * o de débito), separando con guiones cada segmento de 4 caracteres (que
	 * corresponden a dígitos).
	 * <p>
	 *
	 * Notas:<ul>
	 * <li>Si el parámetro entregado es nulo, se devuelve un string vacío.
	 * <li>Si el parámetro entregado tiene más de 16 caracteres, se corta el
	 *     exceso POR LA DERECHA.
	 * <li>Si el parámetro entregado tiene menos de 16 caracteres, se rellena
	 *     con ceros POR LA IZQUIERDA.
	 * <li>No se verifica el contenido del string mismo, por lo que puede
	 *     perfectamente entregársele "ABCDEF" (y el resultado será
	 *     "0000-0000-00AB-CDEF").
	 * <li>Recuérdese que las tarjetas de crédito (VISA y Mastercard) y de
	 *     débito tienen 16 caracteres de largo.
	 * </ul><p>
	 *
	 * Registro de versiones:<UL>
	 * <LI>1.0 23/04/2010 Miguel Farah (BCI): versión inicial.
	 * </UL>
	 * <P>
	 *
	 * @param numero El "número de tarjeta" (ver nota) que se desea formatear.
	 * @return El número de tarjeta formateado.
	 * @since 1.6
	 */
	public static String formateaNumeroDeTarjeta(final String numero) {
		if (numero == null) {
			return "";
		}

		final int LARGO_DE_TARJETA = 16;
		String elNumero;

		if (numero.length() > LARGO_DE_TARJETA) {
			elNumero = numero.substring(0, LARGO_DE_TARJETA);
		}
		else if (numero.length() < LARGO_DE_TARJETA) {
			elNumero = rellenaPorLaIzquierda(numero, LARGO_DE_TARJETA, '0');
		} 
		else {
			elNumero = numero;
		}

		return join("-", fracciona(elNumero, 4, DE_DERECHA_A_IZQUIERDA));
	}

	/**
	 * Método que valida si el String entregado es o no vacio.<p>
	 * Al texto entregado se le realiza un trim, por tanto el string "   " es considerado como vacio.
	 * 
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 18/10/2010 José Verdugo B. (TINet): Versión inicial.
	 * </ul>
	 * <p>
	 * 
	 * @param texto valor a evaluar.
	 * @return True si el texto es null o vacio, false en caso contrario.
	 * @since 1.8
	 */
	public static boolean esVacio(String texto) {
		return ( (texto == null) || (texto.trim().equals("")) );
	}

	/**
	 * Reemplaza en un string todas las ocurrencias de los caracteres tipo
	 * acentos y tildes de lengua castellana a sus entidades HTML respectivas
	 * para su correcto despliegue dentro de un contexto HTML. Si
	 * el string entregado es null o vacío, se retorna sin cambios.
	 * <p>
	 * Registro de versiones:<ul>
	 * <li>1.0  03/05/2011 Eduardo Mascayano (TInet): versión inicial
	 * </ul>
	 * <p>
	 *
	 * @param  s El string a convertir.
	 * @return El string modificado.
	 * @since  1.9
	 */    
	static public String escapaAcentosYTildesHTML(String s) {
		if ((s == null) || s.equals("")) {
			return s;
		}
		s = reemplazaTodo(s, "á", "&aacute;");
		s = reemplazaTodo(s, "Á", "&Aacute;");
		s = reemplazaTodo(s, "é", "&eacute;");
		s = reemplazaTodo(s, "É", "&Eacute;");
		s = reemplazaTodo(s, "í", "&iacute;");
		s = reemplazaTodo(s, "Í", "&Iacute;");
		s = reemplazaTodo(s, "ó", "&oacute;");
		s = reemplazaTodo(s, "Ó", "&Oacute;");
		s = reemplazaTodo(s, "ú", "&uacute;");
		s = reemplazaTodo(s, "Ú", "&Uacute;");
		s = reemplazaTodo(s, "ñ", "&ntilde;");
		s = reemplazaTodo(s, "Ñ", "&Ntilde;");

		return s;
	}	

	/**
	 * Método encargado de capitalizar un String.
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 23/11/2012 Felipe Rivera C.(TINet): Versión inicial.
	 * </ul>
	 * <p>
	 * 
	 * @param capitaliza String a capitalizar. Si este valor es null o vacio se retorna el mismo valor.
	 * @return String capitalizado.
	 * @since 1.10
	 * @see #capitalizarString(String, Locale);
	 */
	public static String capitalizarString(String capitaliza) {
		return capitalizarString(capitaliza, null);
	}

	/**
	 * Método encargado de capitalizar un String.
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 23/11/2012 Felipe Rivera C.(TINet): Versión inicial.
	 * </ul>
	 * <p>
	 * 
	 * @param capitaliza String a capitalizar. Si este valor es null o vacio se retorna el mismo valor.
	 * @param locale Locale con el que se desea realizar la operación. Si este valor es null, se crea un nuevo
	 *            locale por defecto para "es" "CL".
	 * @return String capitalizado.
	 * @since 1.10
	 */
	public static String capitalizarString(String capitaliza, Locale locale) {
		if (capitaliza == null || capitaliza.trim().equals("")) {
			return capitaliza;
		}
		if (locale == null) {
			locale = new Locale("es", "CL");
		}
		String valor = capitaliza.trim();
		StringBuffer valorCapitalizado = new StringBuffer();

		if (valor.indexOf(" ") != -1) {
			String[] valorSeparado = StringUtil.divide(valor, " ");
			for (int i = 0; i < valorSeparado.length; i++) {
				valorCapitalizado.append(valorSeparado[i].substring(0, 1).toUpperCase(locale));
				valorCapitalizado.append(valorSeparado[i].substring(1).toLowerCase(locale));
				valorCapitalizado.append(" ");
			}
			valor = valorCapitalizado.toString().trim();
		}
		else {
			valor = (valor.substring(0, 1).toUpperCase(locale) + valor.substring(1).toLowerCase(locale));
		}
		return valor.trim();
	}

	/**
	 * Método que valida si el String entregado es alfanumérico, el String solo puede poseer los siguientes
	 * caracteres:<p>
	 *     Letras:  [a-z][A-Z][á,é,í,ó,ú][Á,É,Í,Ó,Ú]<p>
	 *     Números: 0 1 2 3 4 5 6 7 8 9
	 *     Espacio: [ ]
	 * <p>
	 * Registro de versiones:<ul>
	 * <li>1.0 13/06/2013 Claudia Carrasco H. (SEnTRA): Versión inicial.
	 * </ul>
	 * </p>
	 * 
	 * @param texto String que será evaluado.
	 * @return False si el texto contiene caracteres inválidos, true en caso contrario.
	 * @since 1.8
	 */

	public static boolean esAlfanumerico(String texto) {
		if ((texto != null) && !(texto.equals(""))) {
			int largoTexto = texto.length();
			texto = texto.toLowerCase();
			for (int i = 0; i < largoTexto; i++) {
				if (!(((texto.charAt(i) > INICIO_ABECEDARIO_ASCII) && (texto.charAt(i) 
						< FIN_ABECEDARIO_ASCII)) || ((texto.charAt(i) > INICIO_NUMEROS_ASCII) && (texto.charAt(i)
								< FIN_NUMEROS_ASCII)) || (texto.charAt(i) == LETRA_ENHE) 
						|| (texto.charAt(i) == ESPACIO_ASCII) || (texto.charAt(i) == A_ACENTO)
						|| (texto.charAt(i) == E_ACENTO) || (texto.charAt(i) == I_ACENTO)
						|| (texto.charAt(i) == O_ACENTO) || (texto.charAt(i) == U_ACENTO))) {
					return false;
				}
			}
		}
		return true;
	}
}