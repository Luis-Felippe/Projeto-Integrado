package bookify.Controller;

import bookify.Controller.PopupMensagem.FabricaPopupMsg;
import bookify.Repository.LivroRepository;
import bookify.Interface.ICadastrar;
import bookify.Interface.IFabricaPopupMsg;
import bookify.Interface.IPopupMsg;
import java.io.IOException;
import java.sql.SQLException;

import bookify.Models.Livro;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class LivrosCadastroController extends TelasLivrosController implements ICadastrar{
    private LivroRepository livroRepository = new LivroRepository();
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
    private TextField livroTextCategoria;
    
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
           this.livroTextCategoria.getText().isEmpty()){
           this.erroText.setText("Preencha todos os campos !");
        } else {
            try {
                Livro novoLivro = new Livro.Builder(livroTextNumReg.getText(), livroTextTitulo.getText())
                        .autor(livroTextAutor.getText())
                        .volume(livroTextVolume.getText())
                        .exemplar(livroTextExemplar.getText())
                        .lugar(livroTextLocal.getText())
                        .dataLivro(livroTextData.getEditor().getText())
                        .editora(livroTextEditora.getText())
                        .anoPublicacao(livroTextAnoPublicacao.getText())
                        .formaAquisicao(livroTextFormaAquisicao.getText())
                        .observacao(livroTextObservacao.getText())
                        .categoria(livroTextCategoria.getText())
                        .build();

                livroRepository.salvar(novoLivro);

                IPopupMsg controller = MsgFabrica.criaPopupMsg("PopupCadastrarMsg");
                controller.setManipulador(()->{
                    mainContainer.getChildren().remove(controller.getPopup());
                });
                mainContainer.getChildren().add(controller.getPopup());
                this.erroText.setText("");

            } catch (SQLException ex) {
                erroText.setText("Erro: código do livro já existe");
            }
        }
    }
}
