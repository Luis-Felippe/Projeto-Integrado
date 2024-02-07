package bookify.Controller.PopupAcao;

import bookify.Interface.IButtonHandler;
import bookify.Interface.IPopupAcao;
import javafx.fxml.FXML;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.scene.control.ChoiceBox;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class PopupLivroController implements IPopupAcao{
    @FXML
    private Text errorText;
    @FXML
    private ChoiceBox<String> exemplar;
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
        errorText.setText("❌ Não foi possível deletar o livro! Verifique os empréstimos ativos");
    }
    
    public void preencherExemplares(List<String> resLiv){
        for(String e:resLiv){
            exemplar.getItems().add(e);
        }
        exemplar.setValue("NENHUM");
    }
    
    public String getExemplar(){
        return exemplar.getValue();
    }
}
