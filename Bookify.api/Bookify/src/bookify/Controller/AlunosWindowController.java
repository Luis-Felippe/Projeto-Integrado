package bookify.Controller;
import bookify.Treinando;
import javafx.fxml.FXML;

public class AlunosWindowController {
    @FXML
    protected void cadastrarAluno(){
        Treinando.mudarTela(0);
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
    @FXML
    protected void listarAluno(){
        Treinando.mudarTela(9);
    }
}
