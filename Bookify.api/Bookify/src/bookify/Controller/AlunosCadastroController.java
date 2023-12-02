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

public class AlunosCadastroController extends TelasAlunoController {

    private BookifyDatabase repositorio = BookifyDatabase.getInstancia();
    
    @FXML
    private Text erroText;
    
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
    
    @FXML
    protected void cadastrarAluno(ActionEvent e) throws IOException {
        if (this.aluTextCurso.getText().isEmpty()
            || this.aluTextEmail.getText().isEmpty()
            || this.aluTextMatricula.getText().isEmpty()
            || this.aluTextNome.getText().isEmpty()
            || this.aluTextSerie.getText().isEmpty()
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
                this.aluTextSerie.getText(),
                this.aluTextCurso.getText(),
                this.aluTextEmail.getText()
            };

            try {
                repositorio.save("usuario", columns, values);
            } catch (SQLException ex) {
                erroText.setText("Erro: matricula já vinculada");
                return;
            }
            
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Popup-cadastrar-confirmar.fxml"));
            Pane popupConfirm = loader.load();
            PopupCadastrarMsgController controller = loader.getController();
            controller.setManipulador(()->{
                mainContainer.getChildren().remove(popupConfirm);
            });
            mainContainer.getChildren().add(popupConfirm);
            
            this.aluTextNome.setText("");
            this.aluTextTelefone.setText("");
            this.aluTextMatricula.setText("");
            this.aluTextSerie.setText("");
            this.aluTextCurso.setText("");
            this.aluTextEmail.setText("");
            this.erroText.setText("");

        }
    }
}
