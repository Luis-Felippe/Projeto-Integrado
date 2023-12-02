package bookify.Controller;

import bookify.Main;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;



public class LoginController {

    private TelasController tela = new TelasController();
    private String user = "biblioteca";
    private String password = "bookify";
    
    @FXML
    private TextField txtLogin;
    
    @FXML
    private PasswordField txtSenha;
    
    @FXML
    private Label lblError;
    
    @FXML
    protected void homeMenu() throws IOException{
        if(user.equals(txtLogin.getText()) && password.equals(txtSenha.getText()) ){
            tela.switchScreen(4);
        }
        else{
            lblError.setText("❌ Usuário ou senha incorretos.");
        }
    }
}