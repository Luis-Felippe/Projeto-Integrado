package bookify.Controller;

import bookify.Treinando;
import java.io.IOException;
import javafx.fxml.FXML;


public class LoginController {
    
    private TelasController tela = new TelasController();
    
    @FXML
    protected void homeMenu() throws IOException{
        tela.switchScreen(4);
    }
 
}
