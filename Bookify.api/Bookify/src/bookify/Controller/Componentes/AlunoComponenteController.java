package bookify.Controller.Componentes;

import bookify.Interface.IButtonHandler;
import bookify.Interface.IComponente;
import java.util.Map;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class AlunoComponenteController implements IComponente{
    private IButtonHandler deleteEvent; 
    
    private IButtonHandler editEvent;
    
    private Pane componenteFxml;
    
    @FXML
    private Text cursoText;

    @FXML
    private Text matriculaText;

    @FXML
    private Text nomeText;

    @FXML
    private Text telefoneText;

    @FXML
    private Text turmaText;
    
    public void setFxml(Pane painel){
        componenteFxml = painel;
    }
    
    public Pane getFxml(){
        return componenteFxml;
    }
    
    // seta as informações do componente aluno
    @FXML
    public void setTexto(Map<String, String> atributos){
        if(atributos.get("nome").length() > 49){
            String aux = atributos.get("nome");
            aux = aux.substring(0,49) + "...";
            atributos.replace("nome", aux);
        }
        nomeText.setText(atributos.get("nome"));
        matriculaText.setText(atributos.get("matricula"));
        cursoText.setText(atributos.get("curso"));
        telefoneText.setText(atributos.get("telefone"));
        turmaText.setText(atributos.get("turma"));
    }
    public void setDeletarManipulador(IButtonHandler event){
        this.deleteEvent = event;
    }
    
    @FXML
    public void deletar(){
        deleteEvent.handler();
    }
    
    public void setEditarManipulador(IButtonHandler event){
        this.editEvent = event;
    }
    
    @FXML
    public void editar(){
        editEvent.handler();
    }
    
}
