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

public class AlunosEdicaoController extends TelasAlunoController implements IEditar{
    private BookifyDatabase repositorio = BookifyDatabase.getInstancia();
    
    private Object params;
    
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
    
    // seta a variável parâmetros contendo o id e chama carregarInformação()
    public void setParametros(Object obj){
        this.params = obj;
        carregarInformacao();
    }
    
    // Pega as informações dos campos de texto da tela e chama a função de update do BD.
    @FXML
    public void atualizar() throws SQLException, IOException{
        String [] values = {  aluTextCurso.getText(),
            aluTextEmail.getText(),
            aluTextMatricula.getText(),
            aluTextNome.getText(),
            aluTextSerie.getText(),
            aluTextTelefone.getText()};
        
        String [] columns = {"curso", "email", "matricula", "nome", "turma", "telefone"};
        
        repositorio.update("usuario", columns, values, "id_usuario = " + params);
        listarAluno();
    }
    
    // Pega as informações do banco e mostra nos campos de texto
    public void carregarInformacao(){
        try {
            ResultSet result = repositorio.get("Usuario", "id_usuario = " + params);
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
