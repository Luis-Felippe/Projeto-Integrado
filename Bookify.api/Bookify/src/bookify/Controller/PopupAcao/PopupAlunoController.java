package bookify.Controller.PopupAcao;

import bookify.Interface.IButtonHandler;
import bookify.Interface.IPopupAcao;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class PopupAlunoController implements IPopupAcao{
    @FXML
    private Text errorText;
    
    private IButtonHandler eventCancel;
    private IButtonHandler eventConfirm;
    private Pane popupFxml;
    
    
    public void setFxml(Pane popupFxml) {
        this.popupFxml = popupFxml;
    }

    public Pane getFxml() {
        return this.popupFxml;
    }
    
    public void setCancelarManipulador(IButtonHandler event){
        this.eventCancel = event;
    }
    
    public void setConfirmarManipulador(IButtonHandler event){
        this.eventConfirm = event;
    }
    
    @FXML
    public void cancelar(){
        eventCancel.handler();
    }
    
    @FXML
    public void confirmar(){
        eventConfirm.handler();
    }
    
    @FXML
    public void erro(){
        errorText.setText("❌ Não foi possível deletar o aluno! Verifique os empréstimos ativos");
    }
}
