
package bookify.Controller;

import bookify.Treinando;
import bookify.model.dao.BookifyDatabase;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ProfessorCadastroController {
    
    private BookifyDatabase repository = new BookifyDatabase();
    
    @FXML
    private TextField profTextNome;
    
    @FXML
    private TextField profTextDisciplina;
    
    @FXML
    private TextField profTextEmail;
    
    @FXML
    private TextField profTextCpf;
    
    @FXML
    private TextField profTextTelefone;
    
    @FXML
    protected void cadastrarProfessor(ActionEvent e) throws SQLException{
        if(this.profTextNome.getText().isEmpty() ||
           this.profTextTelefone.getText().isEmpty() ||
           this.profTextCpf.getText().isEmpty() ||
           this.profTextDisciplina.getText().isEmpty() ||
           this.profTextEmail.getText().isEmpty()){
           System.out.println("Não foi possível cadastrar o professor");
        }
        else {
            String [] columns = {
                "nome", "telefone", "tipo", "cpf", "disciplina", "email"
            };
            String [] values = {
                this.profTextNome.getText(),
                this.profTextTelefone.getText(),
                "P",
                this.profTextCpf.getText(),
                this.profTextDisciplina.getText(),
                this.profTextEmail.getText()
            };
            
            repository.save("usuario", columns, values);
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
