package bookify.Controller;

import bookify.Interface.IButtonHandler;
import bookify.Interface.IComponente;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class LivroComponenteController implements IComponente{  

    private IButtonHandler editEvent;

    private IButtonHandler deleteEvent;
    
    private Pane componenteFxml;
    
    @FXML
    private Text autorText;

    @FXML
    private Text dataText;

    @FXML
    private Text exemplarText;

    @FXML
    private Text numRegText;

    @FXML
    private Text observacaoText;

    @FXML
    private Text tituloText;

    @FXML
    private Text volumeText;
    
    public void setFxml(Pane painel){
        componenteFxml = painel;
    }
    
    public Pane getFxml(){
        return componenteFxml;
    }
    
    @FXML
    public void setTexto(Map<String, String> atributos){
        if(atributos.get("titulo").length() > 51){
            String aux = atributos.get("titulo");
            aux = aux.substring(0,51) + "...";
            atributos.replace("titulo", aux);
        }
        tituloText.setText(atributos.get("titulo"));
        numRegText.setText(atributos.get("num_registro"));
        autorText.setText(atributos.get("autor"));
        volumeText.setText(atributos.get("volume"));
        exemplarText.setText(atributos.get("exemplar"));
        dataText.setText(atributos.get("data"));
        observacaoText.setText(atributos.get("observacao"));
    }
    

    public void setEditarManipulador(IButtonHandler event){
        this.editEvent = event;
    }
    
    @FXML
    public void editar(){
        editEvent.handler();
    }
    
    public void setDeletarManipulador(IButtonHandler event){
        this.deleteEvent = event;
    }
        
    @FXML
    public void deletar(){
        deleteEvent.handler();
    }
}