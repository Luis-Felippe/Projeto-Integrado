package bookify.Controller;

import bookify.Treinando;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;






public class LoginController {
    private String user = "biblioteca";
    private String password = "bookify";
    
    @FXML
    private TextField txtLogin;
    
    @FXML
    private PasswordField txtSenha;
    
    @FXML
    private Label lblError;
    
    @FXML
    protected void homeMenu(){
    if(user.equals(txtLogin.getText()) && password.equals(txtSenha.getText()) ){
        Treinando.mudarTela(4);
    }
    else{
        lblError.setText("❌ Usuário ou senha incorretos.");
    }
    }
}
