package bookify.Controller;

import bookify.Interface.IEditar;
import bookify.model.dao.BookifyDatabase;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class AlunosEditController extends TelasAlunoController implements IEditar{
    BookifyDatabase repositorio = BookifyDatabase.getInstancia();
    
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
        
        repositorio.update("usuario", columns, values, "id_usuario = " + params);
        listarAluno();
    }
    
   
    private void carregarInformacao(){
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

    @Override
    public void editar() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
