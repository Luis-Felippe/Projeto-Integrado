package bookify.Controller;

import java.io.IOException;
import javafx.fxml.FXML;


public class LivrosWindowController {

    private TelasController tela = new TelasController();
    
    @FXML
    protected void realizarEmprestimo() throws IOException{
        tela.trocarTela("emprestimos/realizar");
    }
    @FXML
    protected void cadastrarLivro() throws IOException{
         tela.trocarTela("livros/cadastro");
    }
    @FXML
    protected void listarLivro() throws IOException{
        tela.trocarTela("livros/listagem");
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
