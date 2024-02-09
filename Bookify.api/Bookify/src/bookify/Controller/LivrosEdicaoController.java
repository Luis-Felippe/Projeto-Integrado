package bookify.Controller;

import bookify.Interface.IEditar;
import bookify.Models.BookifyDatabase;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;

public class LivrosEdicaoController extends TelasLivrosController implements IEditar{
    BookifyDatabase repositorio = BookifyDatabase.getInstancia();
    
    private Object params;
    private Object params2;
    
    
    @FXML
    private ChoiceBox<String> exemplar;
    
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
    public void setParametros(Object obj, Object obj2){
        this.params = obj;
        this.params2 = obj2;
        carregarExemplares(obj, obj2);
        carregarInformacao();
        exemplar.setOnAction(event ->{
            carregarInformacao();
        });
    }
    
    public void setParametros(Object obj){
        this.params = obj;
        carregarInformacao();
    }
    
    
    private void carregarExemplares(Object obj, Object obj2){
        try {
            ResultSet result = repositorio.get("Livro", String.format("num_registro = '%s' and volume = '%s' ORDER BY exemplar ASC", obj, obj2));
            exemplar.getItems().add("TODOS");
            exemplar.setValue("TODOS");
            
            while(result.next()){
                exemplar.getItems().add(result.getString("exemplar"));
            }
        } catch (SQLException ex) {
            Logger.getLogger(LivrosEdicaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Pega as informações dos campos de texto da tela e chama a função de update do BD.
    @FXML
    public void atualizar() throws IOException{
        String [] values = {   livroTextAnoPublicacao.getText(),
            livroTextAutor.getText(),
            livroTextData.getEditor().getText(),
            livroTextEditora.getText(),
            livroTextFormaAquisicao.getText(),
            livroTextLocal.getText(),
            livroTextNumReg.getText(),
            livroTextObservacao.getText(),
            livroTextTitulo.getText(),
            livroTextVolume.getText(),};
        
        String [] columns = {"ano_publicacao", "autor", "data", "editora", "forma_aquisicao",
                            "local", "num_registro", "observacao", "titulo", "volume"};
        
        try {
            if(exemplar.getValue().equals("TODOS")){
                repositorio.update("livro", columns, values, String.format("num_registro = '%s' and volume = '%s'", params,params2));
            } else {
                repositorio.update("livro", columns, values, String.format("num_registro = '%s' and volume = '%s' and exemplar = '%s'", params, params2, exemplar.getValue()));
            }
            listarLivro();
        } catch (SQLException ex) {
            erro.setText("Não é possível alterar o número de registro do livro enquanto estiver emprestado.");
            //Logger.getLogger(LivrosEdicaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Pega as informações do banco e mostra nos campos de texto
    public void carregarInformacao(){
        try {
            ResultSet result = repositorio.get("Livro", String.format("num_registro = '%s' and volume = '%s'", params, params2));
            while(result.next()){
                if(exemplar.getValue().equals(result.getString("exemplar")) || exemplar.getValue().equals("TODOS")){
                    livroTextAnoPublicacao.setText(result.getString("ano_publicacao"));
                    livroTextAutor.setText(result.getString("autor"));
                    livroTextData.getEditor().setText(formataData(result));
                    livroTextEditora.setText(result.getString("editora"));
                    livroTextFormaAquisicao.setText(result.getString("forma_aquisicao"));
                    livroTextLocal.setText(result.getString("local"));
                    livroTextNumReg.setText(result.getString("num_registro"));
                    livroTextObservacao.setText(result.getString("observacao"));
                    livroTextTitulo.setText(result.getString("titulo"));
                    livroTextVolume.setText(result.getString("volume"));
                    break;
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProfessorEdicaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private String formataData(ResultSet res){
        SimpleDateFormat formatoAtual = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat novoFormato = new SimpleDateFormat("dd/MM/yyyy");
        String novaData = "";
        try{
            Date data = formatoAtual.parse(res.getString("data"));
            novaData = novoFormato.format(data);
        } catch(ParseException e){
            e.printStackTrace();
        } catch (SQLException ex) {
            Logger.getLogger(LivrosEdicaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return novaData;
    }
}
