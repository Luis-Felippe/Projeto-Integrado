package bookify.service;

import java.sql.SQLException;
import bookify.repository.*;
import bookify.repository.impl.EmprestimoRepositoryImpl;
import bookify.repository.impl.LivroRepositoryImpl;
import bookify.repository.impl.UsuarioRepositoryImpl;

/**
 *
 * @author renan-almeida
 */
public class EmprestimoService {
    private final EmprestimoRepository emprestimoRepo;
    private final UsuarioRepository usuarioRepo;
    private final LivroRepository livroRepo;
    
    public EmprestimoService(){
        this.emprestimoRepo = new EmprestimoRepositoryImpl();
        this.usuarioRepo = new UsuarioRepositoryImpl();
        this.livroRepo = new LivroRepositoryImpl();
    }
    
    // Construtor alternativo para testes
    public EmprestimoService(EmprestimoRepository emprestimoRepo, 
                             UsuarioRepository usuarioRepo,
                             LivroRepository livroRepo
                             ) {
        this.emprestimoRepo = emprestimoRepo;
        this.usuarioRepo = usuarioRepo;
        this.livroRepo = livroRepo;
    }
    
    public void realizarEmprestimo(EmprestimoDTO dto) throws SQLException {
        if(!dadosValidos(dto)){
            throw new IllegalArgumentException("Preencha todos os campos obrigatórios");
        }
        
        var usuarioResult = usuarioRepo.buscarPorIdentificador(dto.getIdentificadorUsuario());
        if(!usuarioResult.next()) {
            throw new IllegalArgumentException("Usuario nao encontrado");
        }
        usuarioResult.close();
        
        var livroResult = livroRepo.buscarDisponiveisPorCodigo(dto.getNumRegistroLivro());
        boolean livroEncontrado = false;
        while(livroResult.next()) {
            if(livroResult.getString("volume").equals(dto.getVolume()) && livroResult.getString("exemplar").equals(dto.getExemplar())){
                livroEncontrado = true;
                break;
            }
        }
        
        livroResult.close();
        
        if(!livroEncontrado){
            throw new IllegalArgumentException("Livro nao disponivel para emprestimo");
        }
        
        if(emprestimoRepo.usuarioPossuiEmprestimo(dto.getIdUsuario())){
            throw new IllegalStateException("Usuario ja possui emprestimo ativo");
        }
        
        String[] columns = {
            "num_registro_livro", "id_usuario", "data_inicio", "data_devolucao",
            "volume_livro", "exemplar_livro", "titulo_livro", "nome_usuario",
            "turma_usuario", "telefone_usuario", "identificador_usuario", "autor_livro"
        };
        
        String[] values = {
            dto.getNumRegistroLivro(),
            dto.getIdUsuario(),
            dto.getDataInicio(),
            dto.getDataDevolucao(),
            dto.getVolume(),
            dto.getExemplar(),
            dto.getTituloLivro(),
            dto.getNomeUsuario(),
            dto.getTurmaUsuario(),
            dto.getTelefoneUsuario(),
            dto.getIdentificadorUsuario(),
            dto.getAutorLivro()
        };
        
        emprestimoRepo.salvarEmprestimo(columns, values);
    }
    
    private boolean dadosValidos(EmprestimoDTO dto){
        return dto != null && dto.getIdUsuario() != null
                && dto.getNumRegistroLivro() != null
                && dto.getVolume() != null
                && dto.getExemplar() != null;
    }
}
