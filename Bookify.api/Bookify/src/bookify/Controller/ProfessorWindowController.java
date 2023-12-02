
package bookify.Controller;

import java.io.IOException;
import javafx.fxml.FXML;

public class ProfessorWindowController {

    private TelasController tela = new TelasController();
    
    @FXML
    protected void cadastrarProfessor() throws IOException{
        tela.trocarTela("professores/cadastro"); 
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
    @FXML
    protected void listarProfessor() throws IOException{
        tela.trocarTela("professores/listagem");
    }
}
