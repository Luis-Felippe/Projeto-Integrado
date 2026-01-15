package bookify.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import bookify.Exception.EmprestimoException;
import bookify.Models.BookifyDatabase;
import bookify.dto.EmprestimoDTO;

public class EmprestimoService {
    private static final int DIAS_RENOVACAO = 5;
    private static final String TABELA_EMPRESTIMO = "emprestimo";
    private static final String TABELA_EMPRESTIMO_ENCERRADO = "emprestimos_encerrados";
    private static final String TABELA_EMPRESTIMOS_ATRASADOS = "emprestimos_atrasados";
    
    private final BookifyDatabase repositorio;
    
    public EmprestimoService(){
        this.repositorio = BookifyDatabase.getInstancia();
    }
    
    public EmprestimoService(BookifyDatabase repositorio) {
        this.repositorio = repositorio;
    }
    
    public List<EmprestimoDTO> listarEmprestimos(String filtro, boolean apenasAtrasados) 
            throws EmprestimoException {
        try {
            String filtroUpper = filtro != null ? filtro.toUpperCase() : "";
            String consulta = String.format(
                "UPPER(nome_usuario) like '%%%s%%' OR UPPER(titulo_livro) like '%%%s%%' " +
                "ORDER BY data_devolucao asc",
                filtroUpper, filtroUpper
            );
            
            String tabela = apenasAtrasados ? TABELA_EMPRESTIMOS_ATRASADOS : TABELA_EMPRESTIMO;
            ResultSet resultSet = repositorio.get(tabela, consulta);
            
            return converterResultSetParaLista(resultSet);
            
        } catch (SQLException ex) {
            throw new EmprestimoException("Erro ao listar empréstimos", ex);
        }
    }
    
    public void renovarEmprestimo(String idEmprestimo) throws EmprestimoException {
        try {
            LocalDate novaDataDevolucao = LocalDate.now().plusDays(DIAS_RENOVACAO);
            String[] colunas = {"data_devolucao"};
            String[] valores = {novaDataDevolucao.toString()};
            String condicao = String.format("id_emprestimo = '%s'", idEmprestimo);
            
            repositorio.update(TABELA_EMPRESTIMO, colunas, valores, condicao);
            
        } catch (SQLException ex) {
            throw new EmprestimoException("Erro ao renovar empréstimo", ex);
        }
    }
    
    public void encerrarEmprestimo(String idEmprestimo, EmprestimoDTO dadosEmprestimo) 
            throws EmprestimoException {
        try {
            String[] colunas = {
                "data_emprestimo", 
                "data_devolucao",
                "id_usuario",
                "num_registro_livro",
                "titulo_livro",
                "volume_livro",
                "exemplar_livro",
                "nome_usuario",
                "turma_usuario",
                "telefone_usuario"
            };
            
            String[] valores = {
                dadosEmprestimo.getDataInicio().toString(),
                LocalDate.now().toString(),
                dadosEmprestimo.getIdUsuario(),
                dadosEmprestimo.getNumRegistroLivro(),
                dadosEmprestimo.getTituloLivro(),
                dadosEmprestimo.getVolumeLivro(),
                dadosEmprestimo.getExemplarLivro(),
                dadosEmprestimo.getNomeUsuario(),
                dadosEmprestimo.getTurmaUsuario(),
                dadosEmprestimo.getTelefoneUsuario()
            };
            
            repositorio.save(TABELA_EMPRESTIMO_ENCERRADO, colunas, valores);
            repositorio.delete(TABELA_EMPRESTIMO, String.format("id_emprestimo = '%s'", idEmprestimo));
            
        } catch (SQLException ex) {
            throw new EmprestimoException("Erro ao encerrar empréstimo", ex);
        }
    }
    
    private List<EmprestimoDTO> converterResultSetParaLista(ResultSet resultSet) 
            throws SQLException {
        List<EmprestimoDTO> emprestimos = new ArrayList<>();
        
        while (resultSet.next()) {
            emprestimos.add(EmprestimoDTO.fromResultSet(resultSet));
        }
        
        return emprestimos;
    }
}
