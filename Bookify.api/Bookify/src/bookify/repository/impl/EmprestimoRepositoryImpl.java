package bookify.repository.impl;

import bookify.Models.BookifyDatabase;
import bookify.repository.EmprestimoRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class EmprestimoRepositoryImpl implements EmprestimoRepository{
      
    private final BookifyDatabase db = BookifyDatabase.getInstancia();

    @Override
    public boolean usuarioPossuiEmprestimo(String userId) throws SQLException {
        ResultSet rs = db.get("emprestimo", "id_usuario= '" + userId + "'");
        return rs.next();
    }

    @Override
    public void salvarEmprestimo(String[] columns, String[] values) throws SQLException {
        db.save("emprestimo", columns, values);
    }
}
