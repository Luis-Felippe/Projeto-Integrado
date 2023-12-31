package bookify.Controller;

import bookify.Interface.IEditar;
import bookify.Models.BookifyDatabase;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class ProfessorEdicaoController extends TelasProfessorController implements IEditar{
    BookifyDatabase repositorio = BookifyDatabase.getInstancia();
    
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
    
    // seta a variável parâmetros contendo o id e chama carregarInformação()
    public void setParametros(Object obj){
        this.params = obj;
        carregarInformacao();
    }

    // Pega as informações dos campos de texto da tela e chama a função de update do BD.
    @FXML
    public void atualizar() throws SQLException, IOException{
        String [] values = {profTextNome.getText(), 
            profTextDisciplina.getText(), 
            profTextCpf.getText(),
            profTextEmail.getText(),
            profTextTelefone.getText()};
        
        String [] columns = {"nome", "disciplina", "cpf", "email", "telefone"};
        
        repositorio.update("usuario", columns, values, "id_usuario = " + params);
        listarProfessor();
    }
    
    // Pega as informações do banco e mostra nos campos de texto
    public void carregarInformacao(){
        try {
            ResultSet result = repositorio.get("Usuario", "id_usuario = " + params);
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
