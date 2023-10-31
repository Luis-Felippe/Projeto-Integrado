package bookify.Controller;
import bookify.Treinando;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class AlunosWindowController {
    @FXML
    protected void cadastrar_aluno(ActionEvent e){
        Treinando.mudarTela(0);
    }
}
