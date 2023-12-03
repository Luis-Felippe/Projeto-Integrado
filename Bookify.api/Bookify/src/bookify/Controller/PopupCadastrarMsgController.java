package bookify.Controller;

import bookify.Interface.IButtonHandler;
import bookify.Interface.IPopupMsg;
import javafx.fxml.FXML;

import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class PopupCadastrarMsgController implements IPopupMsg{
    Pane popup = null;
    private IButtonHandler event;

    public void setManipulador(IButtonHandler event) {
        this.event = event;
    }

    public void manipuladorEvento(MouseEvent event){
       this.event.handler();
    }
    
    public Pane getPopup(){
        return this.popup;
    }
    
    public void setPopup(Pane pane){
        this.popup = pane;
    }
}