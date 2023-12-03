package bookify.Controller;

import bookify.Interface.IButtonHandler;
import bookify.Interface.IPopupMsg;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;

public class PopupAcaoMsgController implements IPopupMsg{
    private IButtonHandler eventHandler;
    private Pane popup = null;
    
    public Pane getPopup(){
        return this.popup;
    }
    
    public void setPopup(Pane pane){
        this.popup = pane;
    }
    
    public void manipuladorEvento(MouseEvent event) {
        eventHandler.handler();
    }

    public void setManipulador(IButtonHandler event){
        this.eventHandler = event;
    }

}
