
package bookify.Controller;

import bookify.Treinando;
import bookify.model.dao.BookifyDatabase;
import java.sql.SQLException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class ProfessorCadastroController {
    
    @FXML
    protected void menuAluno(ActionEvent e){
        Treinando.mudarTela(1);
    }
    
    @FXML
    protected void voltar(ActionEvent e){
        Treinando.mudarTela(2);
    }
    
}
