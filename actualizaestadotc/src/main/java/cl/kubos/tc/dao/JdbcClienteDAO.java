package cl.kubos.tc.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import javax.sql.DataSource;

import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementSetter;
import org.springframework.jdbc.core.RowMapper;

/**
 * <b>JdbcClienteDAO<b/><br></b>
 * Clase .<br>
 * Registo de versiones:
 * <ul>
 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
 * </ul>
 * <p>
 * <b>Todos los derechos reservados por Scotiabank.</b>
 * </p>
 */
public class JdbcClienteDAO implements ClienteDAO {

	/** Atributo log de la clase. */
	private static Logger logger = Logger.getLogger(JdbcClienteDAO.class);

	/** constante que representa 1. */
	private static final int UNO=1;

	/** constante que representa 2. */
	private static final int DOS=2;

	/** Atributo de conexión. */
	private DataSource dataSource;


	public void setDataSource(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * Método actualizarEstado implementado de la interfaz, el cual realiza un
	 * llamado a la base de datos mediante un String a un sp y setea el campo
	 * estado_cliente en la base de datos mediante los parámetros de entrada.
	 *
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * @param rut que representa el rut.
	 * @param estado que representa el estado.
	 * @return List booleano si se concreto el llamado al sp.
	 * @throws DataAccessException encaso de error de acceso.
	 * @Exception e en caso de error.
	 */
	@Override
	public void actualizarEstado(final int rut, final String estado) throws DataAccessException, Exception {

		final String sql = "select * from sp_cambiar_estado(?,?)";
		PreparedStatementSetter setter = new PreparedStatementSetter() {

			@Override
			public void setValues(PreparedStatement p) throws SQLException {
				p.setInt(UNO, rut);
				p.setString(DOS, estado);
			}
		};
		@SuppressWarnings({ "unchecked", "rawtypes" })
		List<Boolean> res = getJdbcTemplate().query(sql, setter, new RowMapper() {

			@Override
			public Object mapRow(ResultSet rs, int rowNum) {

				try {
					return rs.getString(1);
				}
				catch (SQLException e) {
					if (logger.isEnabledFor(Level.ERROR)) {
						logger.error("[actualizarEstado][FINEX] Error: "+e,e);
					}
				}
				return false;
			}
		});

		if (logger.isDebugEnabled()) {
			logger.debug("[actualizarEstado]Respuesta llamada sp " + res.get(0));
		}
	}

	/**
	 * Método get del JdbcTemplate.
	 * 
	 * <p>
	 * Registro de versiones:
	 * <ul>
	 * <li>1.0 24/02/2017 Sebastian Soto(Kubos): Versión inicial.</li>
	 * </ul>
	 * <p>
	 * @return dataSource como template.
	 * @throws Exception en caso de error.
	 */
	private JdbcTemplate getJdbcTemplate() throws Exception {
		return new JdbcTemplate(dataSource);
	}
}
