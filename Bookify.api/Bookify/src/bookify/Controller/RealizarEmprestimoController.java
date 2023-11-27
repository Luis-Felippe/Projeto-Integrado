package bookify.Controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import bookify.model.dao.BookifyDatabase;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Text;

public class RealizarEmprestimoController {

    private String currentUser;
    
    private String currentLiv;
    
    private BookifyDatabase repository = new BookifyDatabase();
    
    private TelasController tela = new TelasController();
    
    @FXML
    private DatePicker LivDateDevolucao;

    @FXML
    private DatePicker LivDateInicio;
    
    @FXML
    private Text error;
    
    @FXML
    private TextField LivTextAutor;

    @FXML
    private TextField LivTextCod;

    @FXML
    private TextField LivTextEditora;

    @FXML
    private TextField LivTextExemplar;

    @FXML
    private TextField LivTextMatricula;

    @FXML
    private TextField LivTextNome;

    @FXML
    private TextField LivTextObservacao;

    @FXML
    private TextField LivTextTelefone;

    @FXML
    private TextField LivTextTitulo;

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
    protected void register(){
       try{
            if(!currentLiv.isEmpty() && !currentUser.isEmpty()){
                var result = repository.get("emprestimo",String.format("num_registro_livro = '%s'", currentLiv));
                if(result == null){
                    String[] values = {currentLiv, currentUser, 
                        LivDateInicio.getEditor().getText(), LivDateDevolucao.getEditor().getText()
                    };
                    String [] columns = {"num_registro_livro","id_usuario",
                        "data_inicio","data_devolucao"
                    };
                    repository.save("emprestimo", columns, values);
                    loadInformation(null, null);
                    LivTextCod.setText("");
                    LivTextMatricula.setText("");
                }else{
                    error.setText("Não foi possivel realizar o emprestimo,"
                            + " verifique se o livro não pertence a outro emprestimo");
                }
            }
        }catch(SQLException ex){
            error.setText(ex.getMessage());
        }
    }
    
    private void loadInformation(ResultSet resLiv, ResultSet resUser) throws SQLException{
        if(resLiv != null){
            resLiv.next();
            LivTextTitulo.setText(resLiv.getString("titulo"));
            LivTextAutor.setText(resLiv.getString("autor"));
            LivTextEditora.setText(resLiv.getString("editora"));
            LivTextObservacao.setText(resLiv.getString("observacao"));
            LivTextExemplar.setText(resLiv.getString("exemplar"));
            currentLiv = resLiv.getString("num_registro");
        }else{
            LivTextTitulo.setText("");
            LivTextAutor.setText("");
            LivTextEditora.setText("");
            LivTextObservacao.setText("");
            LivTextExemplar.setText("");
            currentLiv = "";
        }
        if(resUser != null){
            resUser.next();
            LivTextNome.setText(resUser.getString("nome"));
            LivTextTelefone.setText(resUser.getString("telefone"));
            LocalDate localdate = LocalDate.now();  
            LivDateInicio.setValue(localdate);
            LivDateDevolucao.setValue(localdate.plusDays(5));
            currentUser = resUser.getString("id_usuario");
        }else{
            LivTextNome.setText("");
            LivTextTelefone.setText("");
            LivDateInicio.setValue(null);
            LivDateDevolucao.setValue(null);
            currentUser = "";
        }
    }
    
    @FXML
    protected void searchKeyListener(){
        LivTextCod.setOnKeyPressed(event->{
            if(event.getCode() == KeyCode.ENTER){
                search();
            }
        });
        LivTextMatricula.setOnKeyPressed(event->{
            if(event.getCode() == KeyCode.ENTER){
                search();
            }
        });
    }
    
    
    private void search(){
        ResultSet resultLiv = null, resultUser = null;
        String searchLivro = LivTextCod.getText().toUpperCase();
        String searchUsuario = LivTextMatricula.getText().toUpperCase();
        try {
            if(!searchLivro.isEmpty()){
                    resultLiv = repository.get("livro",
                            String.format("num_registro = '%s'",
                                    searchLivro));
            }
            if(!searchUsuario.isEmpty()){
                resultUser = repository.get("usuario", 
                        String.format("cpf = '%s' or matricula = '%s'",
                        searchUsuario, searchUsuario));
            }
            loadInformation(resultLiv, resultUser);
            error.setText("");
        } catch (SQLException ex) {
                error.setText("Verifique se as informações Cód.Livro e CPF/MATRICULA estão corretas");
        }
    }
}
