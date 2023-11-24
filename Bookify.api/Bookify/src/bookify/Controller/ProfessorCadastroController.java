
package bookify.Controller;

import bookify.Treinando;
import bookify.model.dao.BookifyDatabase;
import java.io.IOException;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ProfessorCadastroController {
    
    private BookifyDatabase repository = new BookifyDatabase();
    
    private TelasController tela = new TelasController();
    
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
}
