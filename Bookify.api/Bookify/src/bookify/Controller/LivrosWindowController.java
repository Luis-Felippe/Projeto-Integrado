package bookify.Controller;

import bookify.Treinando;
import java.io.IOException;
import javafx.fxml.FXML;


public class LivrosWindowController {

    private TelasController tela = new TelasController();
    

    @FXML
    protected void realizarEmprestimo() throws IOException{
        tela.switchScreen(6);
    }
    @FXML
    protected void cadastrarLivro() throws IOException{
         tela.switchScreen(8);
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
    protected void listarLivro() throws IOException{
        tela.switchScreen(12);
    }
}
