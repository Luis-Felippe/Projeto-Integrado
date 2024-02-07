package bookify.Controller.PopupAcao;

import bookify.Interface.IButtonHandler;
import bookify.Interface.IPopupAcao;
import java.sql.ResultSet;
import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class PopupAtualizarController implements IPopupAcao{
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
        errorText.setText("❌ Não foi possível atualizar as turmas. Verifique os empréstimos!");
    }
    
    @Override
    public void preencherExemplares(List<String> res) {
        System.out.println("desculpe isso é gambiarra");
    }

    @Override
    public String getExemplar() {
        System.out.println("Espero que vc nem encontre isso");
        return "sorry";
    }
    
}