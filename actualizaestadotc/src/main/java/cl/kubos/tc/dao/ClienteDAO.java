package cl.kubos.tc.dao;

import org.springframework.dao.DataAccessException;

/**
 * <b>ClienteDAO<b/><br></b>
 * Interface ClienteDAO implementa método actualizarEstado en la clase
 * JdbcClienteDAO.<br>
 * Registo de versiones:
 * <ul>
 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
 * </ul>
 * <p>
 * <b>Todos los derechos reservados por Scotiabank.</b>
 * </p>
 */
public interface ClienteDAO {

	/**
	 * Método abstracto actualizarEstado
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * @param rut que representa el rut.
	 * @param estado que representa el estado.
	 * @throws DataAccessException en caso de error.
	 * @throws Exception en caso de error.
	 */
	public void actualizarEstado(int rut, String estado) throws DataAccessException, Exception;
}
