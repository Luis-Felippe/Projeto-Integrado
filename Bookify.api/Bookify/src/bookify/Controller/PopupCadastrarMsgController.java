package bookify.Controller;

import bookify.Interface.IButtonHandler;
import javafx.fxml.FXML;


public class PopupCadastrarMsgController {

    private IButtonHandler event;

    public void setManipulador(IButtonHandler event) {
        this.event = event;
    }
    
    @FXML
    protected void ManipuladorEvento(){
        event.handler();
    }
}
