
package bookify.Controller;

import java.io.IOException;
import javafx.fxml.FXML;

public class TelasAlunoController extends TelasController {
    @FXML
    protected void cadastrarAluno() throws IOException{
        tela.trocarTela("alunos/cadastro");
    }
    @FXML
    protected void listarAluno() throws IOException{
        tela.trocarTela("alunos/listagem");
    }
    @FXML
    protected void editarAluno(Object obj) throws  IOException{
        tela.trocarTela("alunos/edicao", obj);
    }
}
