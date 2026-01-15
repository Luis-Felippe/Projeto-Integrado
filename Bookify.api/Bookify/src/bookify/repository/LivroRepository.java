package bookify.repository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author renan-almeida
 */
public interface LivroRepository {
    ResultSet buscarDisponiveisPorCodigo(String codigo) throws SQLException;
    
    ResultSet buscarExemplares(String codigo, String volume) throws SQLException;
}
