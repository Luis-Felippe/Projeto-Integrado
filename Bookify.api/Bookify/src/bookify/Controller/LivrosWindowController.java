package bookify.Controller;

import bookify.Treinando;
import javafx.fxml.FXML;


public class LivrosWindowController {
    @FXML
    protected void alunoMenu(){
       Treinando.mudarTela(1); 
    }
     @FXML
    protected void professorMenu(){
        Treinando.mudarTela(2);
    }
     @FXML
    protected void homeMenu(){
        Treinando.mudarTela(4);
    }
    @FXML
    protected void realizarEmprestimo(){
        Treinando.mudarTela(6);
    }
    @FXML
    protected void livroMenu(){
       Treinando.mudarTela(7); 
    }
    @FXML
    protected void cadastrarLivro(){
        Treinando.mudarTela(8);
    }
}
