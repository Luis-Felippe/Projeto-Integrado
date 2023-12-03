
package bookify.Controller;

import bookify.Interface.ICadastrar;
import bookify.Interface.IFabricaPopupMsg;
import bookify.Interface.IPopupMsg;
import bookify.model.dao.BookifyDatabase;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class ProfessorCadastroController extends TelasProfessorController implements ICadastrar{
    
    private BookifyDatabase repositorio = BookifyDatabase.getInstancia();
    private IFabricaPopupMsg MsgFabrica = new FabricaPopupMsg();
    
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
    public void cadastrar(ActionEvent evento) throws IOException{
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
                repositorio.save("usuario", columns, values);
            } catch (SQLException ex) {
                erroText.setText("Erro: CPF já vinculado");
                return;
            }
            IPopupMsg controller = MsgFabrica.criaPopupMsg("PopupCadastrarMsg");
            controller.setManipulador(()->{
                mainContainer.getChildren().remove(controller.getPopup());
            });
            mainContainer.getChildren().add(controller.getPopup());
            
            this.profTextNome.setText("");
            this.profTextTelefone.setText("");
            this.profTextCpf.setText("");
            this.profTextDisciplina.setText("");
            this.profTextEmail.setText("");
            this.erroText.setText("");
        }
    }
}