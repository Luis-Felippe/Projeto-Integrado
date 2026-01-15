package bookify.repository;

import java.sql.ResultSet;
import java.sql.SQLException;
/**
 *
 * @author renan-almeida
 */
public interface EmprestimoRepository {
    
    boolean usuarioPossuiEmprestimo(String userId) throws SQLException;
    
    void salvarEmprestimo(String[] columns, String[] values) throws SQLException;
}
