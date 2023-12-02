package bookify.Controller;

import java.io.IOException;
import javafx.fxml.FXML;


public class HomeController extends Teste {
    private TelasController tela = new TelasController();
    
    @FXML
    protected void login() throws IOException{
        tela.trocarTela("login");
    }
    @FXML
    protected void alunoMenu() throws IOException{
        tela.trocarTela("alunos/menu");
    }
     @FXML
    protected void professorMenu() throws IOException{
        tela.trocarTela("professores/menu");
    }
     @FXML
    protected void homeMenu() throws IOException{
        tela.trocarTela("home");
    }
    @FXML
    protected void livroMenu() throws IOException{
        tela.trocarTela("livros/menu");
    }
    @FXML
    protected void emprestimoMenu() throws IOException{
        tela.trocarTela("emprestimos/listagem");
    }

    
}
