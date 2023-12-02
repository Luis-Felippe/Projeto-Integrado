package bookify.Controller;

import bookify.Interface.IButtonHandler;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PopupController {
    @FXML
    protected Text errorText;
    
    IButtonHandler eventCancel;
    IButtonHandler eventConfirm;
    
    protected void setCancelarManipulador(IButtonHandler event){
        this.eventCancel = event;
    }
    
    protected void setConfirmarManipulador(IButtonHandler event){
        this.eventConfirm = event;
    }
    
    @FXML
    protected void cancelar(){
        eventCancel.handler();
    }
    
    @FXML
    protected void confirmar(){
        eventConfirm.handler();
    }
    
    @FXML
    protected void erro(){
        errorText.setText("❌ Não foi possível deletar o professor! Verifique os empréstimos ativos");
    }
}
