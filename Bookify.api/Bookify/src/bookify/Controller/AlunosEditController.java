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
    protected void alunoMenu(ActionEvent event) throws IOException {
        tela.trocarTela("alunos/menu");
    }

    @FXML
    protected void homeMenu(MouseEvent event) throws IOException {
        tela.trocarTela("home");
    }

    @FXML
    protected void livroMenu(ActionEvent event) throws IOException {
        tela.trocarTela("livros/menu");
    }

    @FXML
    protected void professorMenu(ActionEvent event) throws IOException {
        tela.trocarTela("professores/menu");
    }
    @FXML
    protected void listarAluno() throws IOException{
        tela.trocarTela("alunos/listagem");
    }
    
    @FXML
    protected void emprestimoMenu() throws IOException{
        tela.trocarTela("emprestimos/listagem");
    }
    
    
    protected void setParametros(Object obj){
        this.params = obj;
        carregarInformacao();
    }
    
    @FXML
    protected void atualizar() throws SQLException, IOException{
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
    
   
    private void carregarInformacao(){
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
