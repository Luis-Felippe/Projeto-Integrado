package bookify.Controller.Componentes;

import bookify.Interface.IButtonHandler;
import bookify.Interface.IComponente;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
    
    // seta as informações do componente livro
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
        dataText.setText(formataData(atributos));
        observacaoText.setText(atributos.get("observacao"));
 
    }
    
    private String formataData(Map<String, String> atributos){
        SimpleDateFormat formatoAtual = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat novoFormato = new SimpleDateFormat("dd/MM/yyyy");
        Date data;
        String novaData = "";
        try{
            data = formatoAtual.parse(atributos.get("data"));
            novaData = novoFormato.format(data);
            
        } catch(ParseException e){
            e.printStackTrace();
        }
        return novaData;
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