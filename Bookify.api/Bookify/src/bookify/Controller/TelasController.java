
package bookify.Controller;

import java.io.IOException;
import javafx.fxml.FXML;

public abstract class TelasController {
    protected TelasView tela = new TelasView();
    
    @FXML
    protected void sair() throws IOException{
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
