package bookify.Controller;

import bookify.Interface.IButtonHandler;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;

public class PopupAcaoMsgController {
    IButtonHandler eventHandler;
    
    @FXML
    protected void handlerEvent(MouseEvent event) {
        eventHandler.handler();
    }

    public void setHandler(IButtonHandler event){
        this.eventHandler = event;
    }
}
