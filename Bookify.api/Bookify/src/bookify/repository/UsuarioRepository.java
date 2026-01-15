package bookify.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author renan-almeida
 */
public interface UsuarioRepository {
    
    ResultSet buscarPorIdentificador(String identificador) throws SQLException;
}
