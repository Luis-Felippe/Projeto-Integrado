package bookify.Controller;

import bookify.Interface.IEditar;
import bookify.Models.BookifyDatabase;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LivrosEdicaoController extends TelasLivrosController implements IEditar{
    BookifyDatabase repositorio = BookifyDatabase.getInstancia();
    
    private Object params;
    
    @FXML
    private TextField livroTextAnoPublicacao;
    
    @FXML
    private Text erro;

    @FXML
    private TextField livroTextAutor;

    @FXML
    private DatePicker livroTextData;

    @FXML
    private TextField livroTextEditora;

    @FXML
    private TextField livroTextExemplar;

    @FXML
    private TextField livroTextFormaAquisicao;

    @FXML
    private TextField livroTextLocal;

    @FXML
    private TextField livroTextNumReg;

    @FXML
    private TextField livroTextObservacao;

    @FXML
    private TextField livroTextTitulo;

    @FXML
    private TextField livroTextVolume;
    
    // seta a variável parâmetros contendo o id e chama carregarInformação()
    public void setParametros(Object obj){
        this.params = obj;
        carregarInformacao();
    }
    
    // Pega as informações dos campos de texto da tela e chama a função de update do BD.
    @FXML
    public void atualizar() throws IOException{
        String [] values = {   livroTextAnoPublicacao.getText(),
            livroTextAutor.getText(),
            livroTextData.getEditor().getText(),
            livroTextEditora.getText(),
            livroTextExemplar.getText(),
            livroTextFormaAquisicao.getText(),
            livroTextLocal.getText(),
            livroTextNumReg.getText(),
            livroTextObservacao.getText(),
            livroTextTitulo.getText(),
            livroTextVolume.getText(),};
        
        String [] columns = {"ano_publicacao", "autor", "data", "editora", "exemplar", "forma_aquisicao",
                            "local", "num_registro", "observacao", "titulo", "volume"};
        
        try {
            repositorio.update("livro", columns, values, String.format("num_registro = '%s'", params));
            listarLivro();
        } catch (SQLException ex) {
            erro.setText("Não é possível alterar o número de registro do livro enquanto estiver emprestado.");
            //Logger.getLogger(LivrosEdicaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Pega as informações do banco e mostra nos campos de texto
    public void carregarInformacao(){
        try {
            ResultSet result = repositorio.get("Livro", String.format("num_registro = '%s'", params));
            result.next();
            livroTextAnoPublicacao.setText(result.getString("ano_publicacao"));
            livroTextAutor.setText(result.getString("autor"));
            livroTextData.getEditor().setText(result.getString("data"));
            livroTextEditora.setText(result.getString("editora"));
            livroTextExemplar.setText(result.getString("exemplar"));
            livroTextFormaAquisicao.setText(result.getString("forma_aquisicao"));
            livroTextLocal.setText(result.getString("local"));
            livroTextNumReg.setText(result.getString("num_registro"));
            livroTextObservacao.setText(result.getString("observacao"));
            livroTextTitulo.setText(result.getString("titulo"));
            livroTextVolume.setText(result.getString("volume"));
        } catch (SQLException ex) {
            Logger.getLogger(ProfessorEdicaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
