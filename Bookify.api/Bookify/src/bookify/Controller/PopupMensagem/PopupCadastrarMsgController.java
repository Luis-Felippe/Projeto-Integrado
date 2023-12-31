package bookify.Controller.PopupMensagem;

import bookify.Interface.IButtonHandler;
import bookify.Interface.IPopupMsg;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class PopupCadastrarMsgController implements IPopupMsg{
    private Pane popup = null;
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