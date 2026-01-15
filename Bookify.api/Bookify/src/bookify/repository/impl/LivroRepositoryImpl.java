package bookify.repository.impl;

import bookify.Models.BookifyDatabase;
import bookify.repository.LivroRepository;

import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author renan-almeida
 */
public class LivroRepositoryImpl implements LivroRepository{
    private final BookifyDatabase db = BookifyDatabase.getInstancia();

    @Override
    public ResultSet buscarDisponiveisPorCodigo(String codigo) throws SQLException {
        return db.get(
                "livro",
                String.format("num_registro = '%s' AND disponibilidade = 'true' ORDER BY volume ASC, exemplar ASC", codigo)
        );
    }

    @Override
    public ResultSet buscarExemplares(String codigo, String volume) throws SQLException {
        return db.get(
                "livro",
                String.format("num_registro = '%s' AND volume = '%s AND disponibilidade = 'true' ORDER BY exemplar ASC", codigo, volume)
        );
    }
   
}
