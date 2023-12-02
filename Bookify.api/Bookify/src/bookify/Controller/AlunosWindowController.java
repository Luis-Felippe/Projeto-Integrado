package bookify.Controller;
import java.io.IOException;
import javafx.fxml.FXML;

public class AlunosWindowController {
    TelasController tela = new TelasController();
    
    @FXML
    protected void cadastrarAluno() throws IOException{
        tela.trocarTela("alunos/cadastro");
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
    protected void listarAluno() throws IOException{
        tela.trocarTela("alunos/listagem");
    }
    @FXML
    protected void emprestimoMenu() throws IOException{
        tela.trocarTela("emprestimos/listagem");
    }
}
