
package bookify.Controller;

import bookify.Treinando;
import java.io.IOException;
import javafx.fxml.FXML;

public class ProfessorWindowController {

    private TelasController tela = new TelasController();
    
    @FXML
    protected void cadastrarProfessor() throws IOException{
        tela.switchScreen(3); 
    }
    @FXML
    protected void alunoMenu() throws IOException{
        tela.switchScreen(1);
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
    protected void listarProfessor() throws IOException{
        tela.switchScreen(10);
    }
    
    @FXML
    protected void emprestimoMenu() throws IOException{
        tela.switchScreen(16);
    }
    
}
