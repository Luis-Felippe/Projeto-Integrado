package bookify.Controller;

import bookify.Controller.PopupMensagem.FabricaPopupMsg;
import bookify.Interface.ICadastrar;
import bookify.Interface.IFabricaPopupMsg;
import bookify.Interface.IPopupMsg;
import bookify.Models.BookifyDatabase;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class AlunosCadastroController extends TelasAlunoController implements Initializable, ICadastrar {

    private BookifyDatabase repositorio = BookifyDatabase.getInstancia();
    private IFabricaPopupMsg MsgFabrica = new FabricaPopupMsg();
    
    @FXML
    private Text erroText;
    
    @FXML
    private ChoiceBox<String> Turma;
    
    @FXML
    private Pane mainContainer;
    
    @FXML
    private TextField aluTextCurso;

    @FXML
    private TextField aluTextEmail;

    @FXML
    private TextField aluTextEndereco;

    @FXML
    private TextField aluTextMatricula;

    @FXML
    private TextField aluTextNome;

    @FXML
    private TextField aluTextSerie;

    @FXML
    private TextField aluTextTelefone;
    
    public void preecherTurmas(){
        Turma.getItems().clear();
        Turma.getItems().add("1-A");
        Turma.getItems().add("1-B");;
        Turma.getItems().add("1-C");
        Turma.getItems().add("1-D");
        Turma.getItems().add("2-A");
        Turma.getItems().add("2-B");
        Turma.getItems().add("2-C");
        Turma.getItems().add("2-D");
        Turma.getItems().add("3-A");
        Turma.getItems().add("3-B");
        Turma.getItems().add("3-C");
        Turma.getItems().add("3-D");        
    }
    
    // Cadastra o aluno no banco de dados
    @FXML
    public void cadastrar(ActionEvent evento) throws IOException {
        if (this.aluTextCurso.getText().isEmpty()
            || this.aluTextEmail.getText().isEmpty()
            || this.aluTextMatricula.getText().isEmpty()
            || this.aluTextNome.getText().isEmpty()
            || this.Turma.getValue() == null
            || this.aluTextTelefone.getText().isEmpty()) {
            this.erroText.setText("Preencha todos os campos !");   
        } else {
            String[] columns = {
                "nome", "telefone", "tipo", "matricula", "turma", "curso", "email"
            };
            String[] values = {
                this.aluTextNome.getText(),
                this.aluTextTelefone.getText(),
                "A",
                this.aluTextMatricula.getText(),
                this.Turma.getValue(),
                this.aluTextCurso.getText(),
                this.aluTextEmail.getText()
            };

            try {
                repositorio.save("usuario", columns, values);
            } catch (SQLException ex) {
                erroText.setText("Erro: matricula já vinculada");
                return;
            }
            
            IPopupMsg controller = MsgFabrica.criaPopupMsg("PopupCadastrarMsg");

            controller.setManipulador(()->{
                mainContainer.getChildren().remove(controller.getPopup());
            });
            mainContainer.getChildren().add(controller.getPopup());
            
            this.aluTextNome.setText("");
            this.aluTextTelefone.setText("");
            this.aluTextMatricula.setText("");
            this.aluTextCurso.setText("");
            this.aluTextEmail.setText("");
            this.erroText.setText("");

        }
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
       preecherTurmas();
    }
}
