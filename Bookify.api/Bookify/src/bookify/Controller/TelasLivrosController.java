
package bookify.Controller;

import java.io.IOException;
import javafx.fxml.FXML;


public class TelasLivrosController extends TelasController {
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
    protected void editarLivro(Object obj) throws  IOException{
        tela.trocarTela("livros/edicao", obj);
    }
}
