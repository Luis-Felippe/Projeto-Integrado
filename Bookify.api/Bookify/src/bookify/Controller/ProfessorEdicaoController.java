package bookify.Controller;

import bookify.model.dao.BookifyDatabase;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

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
        tela.trocarTela("alunos/menu");
    }
    @FXML
    protected void professorMenu() throws IOException{
        tela.trocarTela("professores/menu");
    }
    @FXML
    protected void homeMenu() throws IOException{
        tela.trocarTela("home");
    }
    @FXML
    protected void livroMenu() throws IOException{
        tela.trocarTela("livros/menu");
    }
    @FXML
    protected void emprestimoMenu() throws IOException{
        tela.trocarTela("emprestimos/listagem");
    }
    
    @FXML
    protected void listarProfessor() throws IOException{
        tela.trocarTela("professores/listagem");
    }
    
    protected void setParametros(Object obj){
        this.params = obj;
        carregarInformacao();
    }

    @FXML
    protected void atualizar() throws SQLException, IOException{
        String [] values = {profTextNome.getText(), 
            profTextDisciplina.getText(), 
            profTextCpf.getText(),
            profTextEmail.getText(),
            profTextTelefone.getText()};
        
        String [] columns = {"nome", "disciplina", "cpf", "email", "telefone"};
        
        repository.update("usuario", columns, values, "id_usuario = " + params);
        listarProfessor();
    }
    
    private void carregarInformacao(){
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
