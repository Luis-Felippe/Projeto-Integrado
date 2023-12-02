package bookify.Controller;

import bookify.Main;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;

public class LoginController {

    private TelasView tela = new TelasView();
    private String user = "biblioteca";
    private String password = "bookify";
    
    @FXML
    private TextField txtLogin;
    
    @FXML
    private PasswordField txtSenha;
    
    @FXML
    private Label lblError;
    
    @FXML
    protected void realizarLogin() throws IOException{
        if(user.equals(txtLogin.getText()) && password.equals(txtSenha.getText()) ){
            tela.trocarTela("home");
        }
        else{
            lblError.setText("❌ Usuário ou senha incorretos.");
        }
    }
}