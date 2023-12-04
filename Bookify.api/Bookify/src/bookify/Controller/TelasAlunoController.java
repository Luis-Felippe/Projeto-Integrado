
package bookify.Controller;

import java.io.IOException;
import javafx.fxml.FXML;

public class TelasAlunoController extends TelasController {
    
    // troca a tela para a tela de cadastrar alunos
    @FXML
    protected void cadastrarAluno() throws IOException{
        tela.trocarTela("alunos/cadastro");
    }
    // torca para a tela que lista os alunos
    @FXML
    protected void listarAluno() throws IOException{
        tela.trocarTela("alunos/listagem");
    }
    // troca para a tela de edição de alunos
    @FXML
    protected void editarAluno(Object obj) throws  IOException{
        tela.trocarTela("alunos/edicao", obj);
    }
}
