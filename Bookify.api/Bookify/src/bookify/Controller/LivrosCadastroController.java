package bookify.Controller;

import bookify.Treinando;
import bookify.model.dao.BookifyDatabase;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;

public class LivrosCadastroController {
    private BookifyDatabase repository = new BookifyDatabase();
    
    @FXML
    protected TextField livroTextNumReg;
    
    @FXML
    protected TextField livroTextTitulo;
    
    @FXML
    protected TextField livroTextAutor;
    
    @FXML
    protected TextField livroTextVolume;
    
    @FXML
    protected TextField livroTextExemplar;
    
    @FXML
    protected TextField livroTextLocal;
    
    @FXML
    protected DatePicker livroTextData;
    
    @FXML
    protected TextField livroTextEditora;
    
    @FXML
    protected TextField livroTextAnoPublicacao;
    
    @FXML
    protected TextField livroTextFormaAquisicao;
    
    @FXML
    protected TextField livroTextObservacao;
    
    @FXML
    protected void cadastrarLivro(ActionEvent e) throws SQLException{
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
        
            System.out.println("Não foi possível cadastrar o livro");
        } else {
            String [] columns = {
                "num_registro", "titulo", "autor", "volume", "exemplar", "local", "data", "editora", 
                "ano_publicacao", "forma_aquisicao", "observacao"
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
                this.livroTextObservacao.getText()
           };
            repository.save("livro",columns, values);
        }
    }
    
    @FXML
    protected void alunoMenu(){
       Treinando.mudarTela(1); 
    }
    @FXML
    protected void professorMenu(){
        Treinando.mudarTela(2);
    }
    @FXML
    protected void homeMenu(){
        Treinando.mudarTela(4);
    }
    @FXML
    protected void livroMenu(){
       Treinando.mudarTela(7); 
    }
    
}
