package bookify.Controller;

import bookify.Treinando;
import bookify.model.dao.BookifyDatabase;
import com.sun.tools.javac.Main;
import java.io.IOException;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class LivrosCadastroController {
    private BookifyDatabase repository = new BookifyDatabase();
    
    private TelasController tela = new TelasController();
    
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
    
    @FXML
    protected void cadastrarLivro(ActionEvent e) throws IOException{
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
                repository.save("livro",columns, values);
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
                erroText.setText("Erro: exemplar do livro já existe");
                return;
            }
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Popup-cadastrar-confirmar.fxml"));
            Pane popupConfirm = loader.load();
            PopupCadastrarMsgController controller = loader.getController();
            controller.setHandler(()->{
                mainContainer.getChildren().remove(popupConfirm);
            });
            mainContainer.getChildren().add(popupConfirm);
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
