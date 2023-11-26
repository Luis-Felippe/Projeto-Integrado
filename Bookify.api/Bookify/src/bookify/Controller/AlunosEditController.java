package bookify.Controller;

import bookify.model.dao.BookifyDatabase;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
public class AlunosEditController {

    BookifyDatabase repository = new BookifyDatabase();
    
    TelasController tela = new TelasController();
    
    Object params;
    
    @FXML
    private TextField aluTextCurso;

    @FXML
    private TextField aluTextEmail;

    @FXML
    private TextField aluTextMatricula;

    @FXML
    private TextField aluTextNome;

    @FXML
    private TextField aluTextSerie;

    @FXML
    private TextField aluTextTelefone;


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
    protected void listarAluno() throws IOException{
        tela.switchScreen(9);
    }
    
    
    protected void setParams(Object obj){
        this.params = obj;
        loadInformation();
    }
    
    @FXML
    protected void update() throws SQLException, IOException{
        String [] values = {  aluTextCurso.getText(),
            aluTextEmail.getText(),
            aluTextMatricula.getText(),
            aluTextNome.getText(),
            aluTextSerie.getText(),
            aluTextTelefone.getText()};
        
        String [] columns = {"curso", "email", "matricula", "nome", "turma", "telefone"};
        
        repository.update("usuario", columns, values, "id_usuario = " + params);
        listarAluno();
    }
    
   
    private void loadInformation(){
        try {
            ResultSet result = repository.get("Usuario", "id_usuario = " + params);
            result.next();
            aluTextCurso.setText(result.getString("curso"));
            aluTextEmail.setText(result.getString("email"));
            aluTextMatricula.setText(result.getString("matricula"));
            aluTextNome.setText(result.getString("nome"));
            aluTextSerie.setText(result.getString("turma"));
            aluTextTelefone.setText(result.getString("telefone"));
        } catch (SQLException ex) {
            Logger.getLogger(ProfessorEdicaoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
}
