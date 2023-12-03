package bookify.Interface;

import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public interface IComponente {
    
    public void setFxml(Pane painel);
    
    public Pane getFxml();
    
    @FXML
    public void setTexto(Map<String, String> atributos);
    
    @FXML
    public void deletar();
    
    public void setEditarManipulador(IButtonHandler event);
    
    public void setDeletarManipulador(IButtonHandler event);
    
    @FXML
    public void editar();
}
