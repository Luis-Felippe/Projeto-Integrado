package bookify.Controller;
import bookify.Treinando;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;



public class AlunosWindowController {
    @FXML
    protected void cadastrar_aluno(ActionEvent e){
        Treinando.mudarTela(0);
    }
}
