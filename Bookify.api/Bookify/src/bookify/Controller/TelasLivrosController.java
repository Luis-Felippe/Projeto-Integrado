
package bookify.Controller;

import java.io.IOException;
import javafx.fxml.FXML;


public class TelasLivrosController extends TelasController {
    // troca para a tela de realizar um empréstimo
    @FXML
    protected void realizarEmprestimo() throws IOException{
        tela.trocarTela("emprestimos/realizar");
    }
    // troca para a tela de cadastrar um livro
    @FXML
    protected void cadastrarLivro() throws IOException{
         tela.trocarTela("livros/cadastro");
    }
    // troca para a tela de listagem de livros
    @FXML
    protected void listarLivro() throws IOException{
        tela.trocarTela("livros/listagem");
    }
    // troca para a tela de edição de livros
    @FXML
    protected void editarLivro(Object obj) throws  IOException{
        tela.trocarTela("livros/edicao", obj);
    }
}
