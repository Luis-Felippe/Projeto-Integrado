package bookify.Controller;

import bookify.Interface.IButtonHandler;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PopupLivroController {
    @FXML
    protected Text errorText;
    
    IButtonHandler eventCancel;
    IButtonHandler eventConfirm;
    
    protected void setCancelHandler(IButtonHandler event){
        this.eventCancel = event;
    }
    
    protected void setConfirmHandler(IButtonHandler event){
        this.eventConfirm = event;
    }
    
    @FXML
    protected void cancel(){
        eventCancel.handler();
    }
    
    @FXML
    protected void confirm(){
        eventConfirm.handler();
    }
    
    @FXML
    protected void erro(){
        errorText.setText("❌ Não foi possível deletar o Livro! Verifique os empréstimos ativos");
    }
}
