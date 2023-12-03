package bookify.Controller;

import bookify.Interface.IButtonHandler;
import bookify.Interface.IComponente;
import javafx.fxml.FXML;
import javafx.scene.text.Text;
import java.util.Map;
import javafx.scene.layout.Pane;

public class ProfessorComponenteController implements IComponente{
    
    private IButtonHandler deleteEvent;
    private IButtonHandler editEvent;
    
    private Pane componenteFxml;

    @FXML
    private Text cpfText;

    @FXML
    private Text disciplinaText;

    @FXML
    private Text emailText;

    @FXML
    private Text nomeText;

    @FXML
    private Text telefoneText;
    
    public void setFxml(Pane painel){
        componenteFxml = painel;
    }
    
    public Pane getFxml(){
        return componenteFxml;
    }
    
    @FXML
    public void setTexto(Map<String, String> atributos){
        if(atributos.get("nome").length() > 32){
            String aux = atributos.get("nome");
            aux = aux.substring(0,32) + "...";
            atributos.replace("nome", aux);
        }
        nomeText.setText(atributos.get("nome"));
        cpfText.setText(atributos.get("cpf"));
        disciplinaText.setText(atributos.get("disciplina"));
        telefoneText.setText(atributos.get("telefone"));
        emailText.setText(atributos.get("email"));
    }
    
    public void setDeletarManipulador(IButtonHandler event){
        this.deleteEvent = event;
    }
    
    public void setEditarManipulador(IButtonHandler event){
        this.editEvent = event;
    }
    
    @FXML
    public void deletar(){
        deleteEvent.handler();
    }
    
    @FXML
    public void editar(){
        editEvent.handler();
    }
}
