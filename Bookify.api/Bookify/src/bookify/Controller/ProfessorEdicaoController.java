package bookify.Controller;

import bookify.model.dao.BookifyDatabase;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class ProfessorEdicaoController{
    BookifyDatabase repository = new BookifyDatabase();
    
    TelasController tela = new TelasController();
    
    private Object params;
    
    @FXML
    private TextField profTextCpf;

    @FXML
    private TextField profTextDisciplina;

    @FXML
    private TextField profTextEmail;

    @FXML
    private TextField profTextNome;

    @FXML
    private TextField profTextTelefone;

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
    protected void listarProfessor() throws IOException{
        tela.switchScreen(10);
    }
    
    protected void setParams(Object obj){
        this.params = obj;
        loadInformation();
    }

    @FXML
    protected void update() throws SQLException, IOException{
        String [] values = {profTextNome.getText(), 
            profTextDisciplina.getText(), 
            profTextCpf.getText(),
            profTextEmail.getText(),
            profTextTelefone.getText()};
        
        String [] columns = {"nome", "disciplina", "cpf", "email", "telefone"};
        
        repository.update("usuario", columns, values, "id_usuario = " + params);
        listarProfessor();
    }
    
    private void loadInformation(){
        try {
            ResultSet result = repository.get("Usuario", "id_usuario = " + params);
            result.next();
            profTextCpf.setText(result.getString("cpf"));
            profTextNome.setText(result.getString("nome"));
            profTextDisciplina.setText(result.getString("disciplina"));
            profTextEmail.setText(result.getString("email"));
            profTextTelefone.setText(result.getString("telefone"));
        } catch (SQLException ex) {
            Logger.getLogger(ProfessorEdicaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
