package bookify.Controller;

import bookify.model.dao.BookifyDatabase;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LivrosEditController {
    TelasController tela = new TelasController();
    
    BookifyDatabase repository = new BookifyDatabase();
    
    private Object params;
    
    @FXML
    private TextField livroTextAnoPublicacao;

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

    @FXML
    void alunoMenu(ActionEvent event) throws IOException {
        tela.switchScreen(1);
    }

    @FXML
    void homeMenu(MouseEvent event) throws IOException {
        tela.switchScreen(4);
    }

    @FXML
    void livroMenu(ActionEvent event) throws IOException {
        tela.switchScreen(7);
    }

    @FXML
    void professorMenu(ActionEvent event) throws IOException {
        tela.switchScreen(2);
    }
    
    @FXML
    protected void emprestimoMenu() throws IOException{
        tela.switchScreen(16);
    }
    
    @FXML
    protected void listarLivro() throws IOException{
        tela.switchScreen(12);
    }

     protected void setParams(Object obj){
        this.params = obj;
        loadInformation();
    }
    
    @FXML
    protected void update() throws SQLException, IOException{
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
        
        repository.update("livro", columns, values, String.format("num_registro = '%s'", params));
        listarLivro();
    }
    
   
    private void loadInformation(){
        try {
            ResultSet result = repository.get("Livro", String.format("num_registro = '%s'", params));
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
