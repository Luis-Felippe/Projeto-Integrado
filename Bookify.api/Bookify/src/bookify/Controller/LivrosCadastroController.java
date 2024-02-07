package bookify.Controller;

import bookify.Controller.PopupMensagem.FabricaPopupMsg;
import bookify.Interface.ICadastrar;
import bookify.Interface.IFabricaPopupMsg;
import bookify.Interface.IPopupMsg;
import bookify.Models.BookifyDatabase;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class LivrosCadastroController extends TelasLivrosController implements ICadastrar{
    
    private BookifyDatabase repositorio =  BookifyDatabase.getInstancia();
    private IFabricaPopupMsg MsgFabrica = new FabricaPopupMsg();
    
    @FXML
    private Pane mainContainer;
    
    @FXML
    private Text erroText;
    
    @FXML
    private TextField livroTextNumReg;
    
    @FXML
    private TextField livroTextTitulo;
    
    @FXML
    private TextField livroTextAutor;
    
    @FXML
    private TextField livroTextVolume;
    
    @FXML
    private TextField livroTextExemplar;
    
    @FXML
    private TextField livroTextLocal;
    
    @FXML
    private DatePicker livroTextData;
    
    @FXML
    private TextField livroTextEditora;
    
    @FXML
    private TextField livroTextAnoPublicacao;
    
    @FXML
    private TextField livroTextFormaAquisicao;
    
    @FXML
    private TextField livroTextObservacao;
    
    // Cadastra um livro no banco de dados
    @FXML
    public void cadastrar(ActionEvent evento) throws IOException{
        if(this.livroTextNumReg.getText().isEmpty() ||
           this.livroTextTitulo.getText().isEmpty() ||
           this.livroTextAutor.getText().isEmpty() ||
           this.livroTextVolume.getText().isEmpty() ||
           this.livroTextExemplar.getText().isEmpty() ||
           this.livroTextLocal.getText().isEmpty() ||
           this.livroTextData.getEditor().getText().isEmpty() ||
           this.livroTextEditora.getText().isEmpty() ||
           this.livroTextAnoPublicacao.getText().isEmpty() ||
           this.livroTextFormaAquisicao.getText().isEmpty() ||
           this.livroTextObservacao.getText().isEmpty()){
           this.erroText.setText("Preencha todos os campos !");
        } else {
            String [] columns = {
                "num_registro", "titulo", "autor", "volume", "exemplar", "local", "data", "editora", 
                "ano_publicacao", "forma_aquisicao", "observacao", "disponibilidade"
            };
            String [] values = {
                this.livroTextNumReg.getText(),
                this.livroTextTitulo.getText(),
                this.livroTextAutor.getText(),
                this.livroTextVolume.getText(),
                this.livroTextExemplar.getText(),
                this.livroTextLocal.getText(),
                this.livroTextData.getEditor().getText(),
                this.livroTextEditora.getText(),
                this.livroTextAnoPublicacao.getText(),
                this.livroTextFormaAquisicao.getText(),
                this.livroTextObservacao.getText(),
                "true"
           };
            try {
                repositorio.save("livro",columns, values);
            } catch (SQLException ex) {
                erroText.setText("Erro: código do livro já existe");
                return;
            }
            IPopupMsg controller =  MsgFabrica.criaPopupMsg("PopupCadastrarMsg");
            controller.setManipulador(()->{
                mainContainer.getChildren().remove(controller.getPopup());
            });
            mainContainer.getChildren().add(controller.getPopup());
            this.erroText.setText("");
        }
    }
}
