package bookify.repository.impl;

import bookify.Models.BookifyDatabase;
import bookify.repository.UsuarioRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

public class UsuarioRepositoryImpl implements UsuarioRepository{
    
    private final BookifyDatabase db = BookifyDatabase.getInstancia();

    @Override
    public ResultSet buscarPorIdentificador(String identificador) throws SQLException {
        return db.get(
                "usuario",
                String.format("cpf = '%s' OR matricula = '%s'", identificador, identificador)
        );
    }
}
