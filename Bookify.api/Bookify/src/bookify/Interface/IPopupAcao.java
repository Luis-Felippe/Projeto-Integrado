package bookify.Interface;

import java.util.List;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;


public interface IPopupAcao {
    
    public void setFxml(Pane painel);
    
    public Pane getFxml();
    
    public void setCancelarManipulador(IButtonHandler event);
    
    public void setConfirmarManipulador(IButtonHandler event);
    
    @FXML
    public void cancelar();
    
    @FXML
    public void confirmar();
    
    @FXML
    public void erro();
    
    public void preencherExemplares(List<String> res);
    
    public String getExemplar();
}
