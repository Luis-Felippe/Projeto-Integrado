package bookify.Controller;

import bookify.Controller.PopupMensagem.FabricaPopupMsg;
import bookify.Interface.ICadastrar;
import bookify.Interface.IFabricaPopupMsg;
import bookify.Interface.IPopupMsg;
import bookify.Models.BookifyDatabase;
import bookify.Service.AlunoService;
import bookify.Service.AlunoValidator;
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

    private AlunoService alunoService;
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

    private void inicializarService() {
        this.alunoService = new AlunoService(BookifyDatabase.getInstancia());
    }

    public void preecherTurmas() {
        Turma.getItems().clear();
        Turma.getItems().add("1-A");
        Turma.getItems().add("1-B");
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

    @FXML
    public void cadastrar(ActionEvent evento) throws IOException {
        String nome = this.aluTextNome.getText();
        String telefone = this.aluTextTelefone.getText();
        String matricula = this.aluTextMatricula.getText();
        String curso = this.aluTextCurso.getText();
        String turma = this.Turma.getValue();
        String email = this.aluTextEmail.getText();

        if (!AlunoValidator.validarCamposObrigatorios(nome, telefone, matricula, curso, turma, email)) {
            this.erroText.setText("Preencha todos os campos !");
            return;
        }

        try {
            alunoService.cadastrarAluno(nome, telefone, matricula, turma, curso, email);

            exibirMensagemSucesso();

            limparCampos();

        } catch (SQLException ex) {
            exibirErroMatriculaDuplicada();
        }
    }

  
    private void exibirMensagemSucesso() {
        IPopupMsg controller = MsgFabrica.criaPopupMsg("PopupCadastrarMsg");
        controller.setManipulador(() -> {
            mainContainer.getChildren().remove(controller.getPopup());
        });
        mainContainer.getChildren().add(controller.getPopup());
    }

    private void exibirErroMatriculaDuplicada() {
        this.erroText.setText("Erro: matricula já vinculada");
    }

    private void limparCampos() {
        this.aluTextNome.setText("");
        this.aluTextTelefone.setText("");
        this.aluTextMatricula.setText("");
        this.aluTextCurso.setText("");
        this.aluTextEmail.setText("");
        this.erroText.setText("");
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        inicializarService();
        preecherTurmas();
    }
}
