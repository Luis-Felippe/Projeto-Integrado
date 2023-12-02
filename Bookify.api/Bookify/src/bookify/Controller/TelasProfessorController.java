
package bookify.Controller;

import java.io.IOException;
import javafx.fxml.FXML;

public class TelasProfessorController extends TelasController {
    @FXML
    protected void cadastrarProfessor() throws IOException{
        tela.trocarTela("professores/cadastro"); 
    }
    @FXML
    protected void listarProfessor() throws IOException{
        tela.trocarTela("professores/listagem");
    }
    @FXML
    protected void editarProfessor(Object obj) throws  IOException{
        tela.trocarTela("professores/edicao", obj);
    }
}
