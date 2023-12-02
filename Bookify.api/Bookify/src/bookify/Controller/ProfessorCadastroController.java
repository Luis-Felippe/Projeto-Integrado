
package bookify.Controller;

import bookify.model.dao.BookifyDatabase;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ProfessorCadastroController {
    
    private BookifyDatabase repository = new BookifyDatabase();
    
    private TelasController tela = new TelasController();
    
    @FXML
    private Text erroText;
    
    @FXML
    private Pane mainContainer;
    
    @FXML
    private TextField profTextNome;
    
    @FXML
    private TextField profTextDisciplina;
    
    @FXML
    private TextField profTextEmail;
    
    @FXML
    private TextField profTextCpf;
    
    @FXML
    private TextField profTextTelefone;
    
    @FXML
    protected void alunoMenu() throws IOException{
        tela.trocarTela("alunos/menu");
    }
    @FXML
    protected void professorMenu() throws IOException{
        tela.trocarTela("professores/menu");
    }
    @FXML
    protected void homeMenu() throws IOException{
        tela.trocarTela("home");
    }
    @FXML
    protected void livroMenu() throws IOException{
        tela.trocarTela("livros/menu");
    }
    @FXML
    protected void emprestimoMenu() throws IOException{
        tela.trocarTela("emprestimos/listagem");
    }
    
    @FXML
    protected void cadastrarProfessor(ActionEvent e) throws IOException{
        if(this.profTextNome.getText().isEmpty() ||
           this.profTextTelefone.getText().isEmpty() ||
           this.profTextCpf.getText().isEmpty() ||
           this.profTextDisciplina.getText().isEmpty() ||
           this.profTextEmail.getText().isEmpty()){
           erroText.setText("Preencha todos os campos !");
        }
        else {
            String [] columns = {
                "nome", "telefone", "tipo", "cpf", "disciplina", "email"
            };
            String [] values = {
                this.profTextNome.getText(),
                this.profTextTelefone.getText(),
                "P",
                this.profTextCpf.getText(),
                this.profTextDisciplina.getText(),
                this.profTextEmail.getText()
            };
            
            try {
                repository.save("usuario", columns, values);
            } catch (SQLException ex) {
                erroText.setText("Erro: CPF já vinculado");
                return;
            }
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Popup-cadastrar-confirmar.fxml"));
            Pane popupConfirm = loader.load();
            PopupCadastrarMsgController controller = loader.getController();
            controller.setManipulador(()->{
                mainContainer.getChildren().remove(popupConfirm);
            });
            mainContainer.getChildren().add(popupConfirm);
            
            this.profTextNome.setText("");
            this.profTextTelefone.setText("");
            this.profTextCpf.setText("");
            this.profTextDisciplina.setText("");
            this.profTextEmail.setText("");
            this.erroText.setText("");
        }
    }
}