
package bookify.Controller;

import java.io.IOException;
import javafx.fxml.FXML;

public abstract class TelasController {
    protected TelasView tela = new TelasView();
    
    // logout do sistema
    @FXML
    protected void sair() throws IOException{
        tela.trocarTela("login");
    }
    // troca para a tela de menu inicial dos alunos
    @FXML
    protected void alunoMenu() throws IOException{
        tela.trocarTela("alunos/menu");
    }
    // troca para a tela de menu dos professores
    @FXML
    protected void professorMenu() throws IOException{
        tela.trocarTela("professores/menu");
    }
    // troca para a tela de menu inicial
    @FXML
    protected void homeMenu() throws IOException{
        tela.trocarTela("home");
    }
    // troca para a tela dos menu dos livros
    @FXML
    protected void livroMenu() throws IOException{
        tela.trocarTela("livros/menu");
    }
    // troca para a tela de listagem de empréstimos
    @FXML
    protected void emprestimoMenu() throws IOException{
        tela.trocarTela("emprestimos/listagem");
    }
}
