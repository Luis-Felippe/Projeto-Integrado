package bookify.Controller;

import bookify.Controller.PopupMensagem.FabricaPopupMsg;
import bookify.Interface.IFabricaPopupMsg;
import bookify.Interface.IPopupMsg;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import bookify.Models.BookifyDatabase;
import java.net.URL;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.util.List;
import java.util.ArrayList;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import bookify.dto.EmprestimoDTO;
import bookify.Service.EmprestimoService;
import bookify.dto.LivroDTO;
import bookify.dto.UsuarioDTO;

public class RealizarEmprestimoController extends TelasController implements Initializable {

    private String currentUser = "";
    
    private String currentLiv = "";    
    private String currentLivSelected = "";

    
    private BookifyDatabase repositorio =  BookifyDatabase.getInstancia();
    
    private IFabricaPopupMsg MsgFabrica = new FabricaPopupMsg();
    
    private final EmprestimoService emprestimoService = new EmprestimoService();
    
    private String tipoUsuario = "A"; //A = aluno e P = Professor
    
    @FXML
    private Pane mainContainer;
    
    @FXML
    private Text error_livro;
    
    @FXML
    private DatePicker LivDateDevolucao;

    @FXML
    private DatePicker LivDateInicio;
    
    @FXML
    private Text error_usuario;
    
    @FXML
    private Text error;
    
    @FXML
    private TextField LivTextAutor;

    @FXML
    private TextField LivTextCod;

    @FXML
    private ChoiceBox<String> volume;
    
    @FXML
    private ChoiceBox<String> exemplar;

    @FXML
    private TextField LivTextMatricula;

    @FXML
    private TextField LivTextNome;

    @FXML
    private TextField LivTextObservacao;

    @FXML
    private TextField LivTextTelefone;

    @FXML
    private TextField LivTextTitulo;
    
    @FXML
    private TextField LivTextTurma;
    
    @FXML
    protected void emprestar(){
        try{            
            EmprestimoDTO dto = montarDTO();
            
            emprestimoService.realizarEmprestimo(dto);
            
            limparTelaAposEmprestimo();
            mostrarPopupSucesso();
            
        } catch (IllegalArgumentException e){
            error.setText(e.getMessage());
            
        } catch (IllegalStateException e){
            error.setText(e.getMessage());
            
        } catch(SQLException e){
            System.out.println(e.getMessage());
            error.setText("Erro ao realizar emprestimo");
        }
    }
    
    private void limparInformacoes(boolean livro, boolean usuario){
        if(livro){
            LivTextTitulo.setText("");
            LivTextAutor.setText("");
            volume.getItems().clear();
            LivTextObservacao.setText("");
            exemplar.getItems().clear();
            currentLiv = "";
            currentLivSelected = "";
        }
        if(usuario){
            LivTextNome.setText("");
            LivTextTelefone.setText("");
            LivTextTurma.setText("");
            LivDateInicio.setValue(null);
            LivDateDevolucao.setValue(null);
            currentUser = "";
        }
    }

    
    private void carregarInformacao(ResultSet resLiv, ResultSet resUser) throws SQLException{
        carregarLivro(montarLivroDTO(resLiv));
        carregarUsuario(montarUsuarioDTO(resUser));
    }
    
    private void carregarLivro(LivroDTO livro){
        if(livro == null){
//            error_livro.setText("Livro nao disponivel");
            limparInformacoes(true, false);
            return;
        }
        
        error_livro.setText("");
        LivTextTitulo.setText(livro.getTitulo());
        LivTextAutor.setText(livro.getAutor());
        volume.getItems().setAll(livro.getVolumes());
        
        currentLiv = livro.getCodigo();
        currentLivSelected = currentLiv;
        
    }
    
    private void carregarUsuario(UsuarioDTO usuario){
        if(usuario == null){
//            error_usuario.setText("Usuario nao encontrado");
            limparInformacoes(false, true);
            return;
        }
        
        error_usuario.setText("");
        LivTextNome.setText(usuario.getNome());
        LivTextTelefone.setText(usuario.getTelefone());
        LivTextTurma.setText(usuario.getTurma());
        LivDateInicio.setValue(usuario.getDataInicio());
        LivDateDevolucao.setValue(usuario.getDataDevolucao());
        
        currentUser = usuario.getId();
        
    }
    
    // aciona a função de busca após teclar ENTER
    @FXML
    protected void buscarTeclaPressionada(){
        LivTextCod.setOnKeyPressed(event->{
            if(event.getCode() == KeyCode.ENTER){
                buscar();
            }
        });
        LivTextMatricula.setOnKeyPressed(event->{
            if(event.getCode() == KeyCode.ENTER){
                buscar();
            }
        });
    }
    
    private void buscar(){
        try{
            ResultSet resultLiv = buscarLivroDisponivel(LivTextCod.getText());
            ResultSet resultUser = buscarUsuario(LivTextMatricula.getText());
            
            carregarInformacao(resultLiv, resultUser);
            error.setText("");
            
        } catch(SQLException e){
            System.out.println(e.getMessage());
            error.setText("Verifique se as informcoes Cod Livro e CPF/Matricula estao corretas");
        }
    }
    
    private void carregarExemplares() {
        try {
            if (volume.getItems().isEmpty() || volume.getValue() == null) {
                exemplar.getItems().clear();
                LivTextObservacao.clear();
                return;
            }

            String codigoLivro = LivTextCod.getText();
            String volumeSelecionado = volume.getValue();

            if (codigoLivro == null || codigoLivro.isEmpty() || volumeSelecionado == null) {
                exemplar.getItems().clear();
                LivTextObservacao.clear();
                return;
            }

            ResultSet busca = repositorio.get("livro", 
                String.format("num_registro = '%s' and volume = '%s' and disponibilidade = 'true' " +
                             "ORDER BY exemplar ASC", 
                             codigoLivro, volumeSelecionado));

            exemplar.getItems().clear();
            LivTextObservacao.clear();

            boolean encontrouExemplares = false;

            while (busca.next()) {
                exemplar.getItems().add(busca.getString("exemplar"));
                encontrouExemplares = true;
            }

            if (encontrouExemplares) {
                exemplar.setValue(exemplar.getItems().get(0));
                error_livro.setText(""); // Limpa mensagem de erro
            } else {
              
                exemplar.setValue(null);
                exemplar.getItems().add("Nenhum exemplar disponível");
                exemplar.setDisable(true); // Opcional: desabilita a escolha
                error_livro.setText("Este volume não possui exemplares disponíveis para empréstimo");
                LivTextObservacao.clear();
            }

        } catch (SQLException ex) {
            Logger.getLogger(RealizarEmprestimoController.class.getName()).log(Level.SEVERE, null, ex);
            error_livro.setText("Erro ao buscar exemplares");
        }
    }
    
    private void carregarInformacoes() {
        try {
            if (exemplar.getItems().isEmpty() || exemplar.getValue() == null) {
                LivTextObservacao.clear();
                return;
            }

            String valorExemplar = exemplar.getValue();

            // Se for a mensagem de "nenhum exemplar disponível", não busca
            if ("Nenhum exemplar disponível".equals(valorExemplar)) {
                LivTextObservacao.clear();
                return;
            }

            String codigoLivro = LivTextCod.getText();
            String volumeSelecionado = volume.getValue();

            if (codigoLivro == null || codigoLivro.isEmpty() || 
                volumeSelecionado == null || valorExemplar == null) {
                LivTextObservacao.clear();
                return;
            }

            ResultSet busca = repositorio.get("livro", 
                String.format("num_registro = '%s' and volume = '%s' and disponibilidade = 'true' " +
                             "and exemplar = '%s'", 
                             codigoLivro, volumeSelecionado, valorExemplar));

            if (busca.next()) {
                LivTextObservacao.setText(busca.getString("observacao"));
            } else {
                LivTextObservacao.clear();
            }

        } catch (SQLException ex) {
            Logger.getLogger(RealizarEmprestimoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        volume.setOnAction(event ->{
            carregarExemplares();
        });
        exemplar.setOnAction(event ->{
            carregarInformacoes();
        });
    }
    
    private EmprestimoDTO montarDTO(){
        EmprestimoDTO dto = new EmprestimoDTO();
        
        dto.setNumRegistroLivro(currentLiv);
        dto.setIdUsuario(currentUser);
        dto.setDataInicio(LivDateInicio.getEditor().getText());
        dto.setDataDevolucao(LivDateDevolucao.getEditor().getText());
        dto.setVolume(volume.getValue());
        dto.setExemplar(exemplar.getValue());
        
        dto.setTituloLivro(LivTextTitulo.getText());
        dto.setNomeUsuario(LivTextNome.getText());
        dto.setTurmaUsuario(LivTextTurma.getText());
        dto.setTelefoneUsuario(LivTextTelefone.getText());
        dto.setIdentificadorUsuario(LivTextMatricula.getText());
        dto.setAutorLivro(LivTextAutor.getText());
        
        return dto;
    }
    
    private void limparTelaAposEmprestimo() throws SQLException{
        carregarInformacao(null, null);
        LivTextCod.clear();
        LivTextMatricula.clear();
        error.setText("");
    }
    
    private void mostrarPopupSucesso(){
        IPopupMsg controller = MsgFabrica.criaPopupMsg("PopupAcaoMsg");
        controller.setManipulador(() -> {
            mainContainer.getChildren().remove(controller.getPopup());
        });
        mainContainer.getChildren().add(controller.getPopup());
    }
    
    private LivroDTO montarLivroDTO(ResultSet res) throws SQLException {
        if (res == null || !res.next()) {
            return null;
        }
        
        String codigo = res.getString("num_registro");
        String titulo = res.getString("titulo");
        String autor = res.getString("autor");
        
        ResultSet volumesRes = repositorio.get("livros", String.format("num_registro = '%s'", codigo));
        List<String> volumes = new ArrayList<>();
        while (volumesRes.next()) {
            volumes.add(volumesRes.getString("volume"));
        }
        volumesRes.close();
        
        return new LivroDTO(codigo, titulo, autor, volumes);
    }
    
    private UsuarioDTO montarUsuarioDTO(ResultSet res) throws SQLException {
        if (res == null || !res.next()) {
            return null;
        }
        
        String id = res.getString("id");
        String nome = res.getString("nome");
        String telefone = res.getString("telefone");
        String turma = res.getString("turma");
        
        return new UsuarioDTO(id, nome, telefone, turma);
    }
    
    private ResultSet buscarLivroDisponivel(String codigo) throws SQLException {
        String query = String.format(
            "num_registro = '%s' AND num_registro NOT IN " +
            "(SELECT num_registro_livro FROM emprestimo)",
            codigo
        );
        return repositorio.get("livros", query);
    }
    
    private ResultSet buscarUsuario(String identificador) throws SQLException {
        String query;
        if (tipoUsuario.equals("A")) {
            query = String.format("matricula = '%s'", identificador);
            return repositorio.get("alunos", query);
        } else {
            query = String.format("cpf = '%s'", identificador);
            return repositorio.get("professores", query);
        }
    }
}
