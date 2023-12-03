package bookify.Interface;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;


public interface IPopupMsg {
    public void setManipulador(IButtonHandler event);
    
    @FXML
    public void manipuladorEvento(MouseEvent event);
    
    public void setPopup(Pane pane);
    
    public Pane getPopup();
}
