
package bookify.Controller;

import bookify.Treinando;
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
    protected void cadastrarProfessor(ActionEvent e) throws SQLException, IOException{
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
            
            repository.save("usuario", columns, values);
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Popup-cadastrar-confirmar.fxml"));
            Pane popupConfirm = loader.load();
            PopupCadastrarMsgController controller = loader.getController();
            controller.setHandler(()->{
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

    @FXML
    protected void alunoMenu() throws IOException{
        tela.switchScreen(1);
    }
    @FXML
    protected void professorMenu() throws IOException{
        tela.switchScreen(2);
    }
    @FXML
    protected void homeMenu() throws IOException{
        tela.switchScreen(4);
    }
    @FXML
    protected void livroMenu() throws IOException{
        tela.switchScreen(7);
    }
    @FXML
    protected void emprestimoMenu() throws IOException{
        tela.switchScreen(16);
    }
}
