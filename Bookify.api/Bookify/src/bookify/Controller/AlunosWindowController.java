package bookify.Controller;
import bookify.Main;
import java.io.IOException;
import javafx.fxml.FXML;

public class AlunosWindowController {
    TelasController tela = new TelasController();
    @FXML
    protected void cadastrarAluno() throws IOException{
        tela.switchScreen(0);
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
    @FXML
    protected void listarAluno() throws IOException{
        tela.switchScreen(9);
    }
    @FXML
    protected void emprestimoMenu() throws IOException{
        tela.switchScreen(16);
    }
}
