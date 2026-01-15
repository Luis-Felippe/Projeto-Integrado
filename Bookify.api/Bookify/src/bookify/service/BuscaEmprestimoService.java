package bookify.service;

import bookify.repository.*;
import bookify.Models.BookifyDatabase;
import bookify.repository.impl.LivroRepositoryImpl;
import bookify.repository.impl.UsuarioRepositoryImpl;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 *
 * @author renan-almeida
 */
public class BuscaEmprestimoService {
    
    private final BookifyDatabase repositorio = BookifyDatabase.getInstancia();
    private final UsuarioRepository usuarioRepo;
    private final LivroRepository livroRepo;
    
    public BuscaEmprestimoService(){
        this.usuarioRepo = new UsuarioRepositoryImpl();
        this.livroRepo = new LivroRepositoryImpl();
    }
    
    public ResultSet buscarLivroDisponivel(String codigoLivro) throws SQLException {
        return livroRepo.buscarDisponiveisPorCodigo(codigoLivro);
    }
    
    public ResultSet buscarUsuario(String identificador) throws SQLException {
        return usuarioRepo.buscarPorIdentificador(identificador);
    }
}
