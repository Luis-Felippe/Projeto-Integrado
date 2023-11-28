package bookify.Controller;

import bookify.Interface.IButtonHandler;
import javafx.fxml.FXML;


public class PopupExcluirMsgController {

    private IButtonHandler event;

    public void setHandler(IButtonHandler event) {
        this.event = event;
    }
    
    @FXML
    protected void handlerEvent(){
        event.handler();
    }
    
}
