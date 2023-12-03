package bookify.Controller.PopupMensagem;

import bookify.Interface.IButtonHandler;
import bookify.Interface.IPopupMsg;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public class PopupExcluirMsgController implements IPopupMsg{

    private IButtonHandler event;
    private Pane popup;

    public void setManipulador(IButtonHandler event){
        this.event = event;
    }
    
    @FXML
    public void manipuladorEvento(MouseEvent event){
        this.event.handler();
    }
    
    public void setPopup(Pane pane){
        this.popup = pane;
    }
    
    public Pane getPopup(){
        return this.popup;
    }
    
}
