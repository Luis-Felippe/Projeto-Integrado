package bookify.Controller;

import bookify.Treinando;
import javafx.fxml.FXML;


public class HomeController {
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
    protected void login(){
       Treinando.mudarTela(5); 
    }
    @FXML
    protected void livroMenu(){
       Treinando.mudarTela(7); 
    }
}
