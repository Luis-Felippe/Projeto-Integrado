package bookify.Controller;

import bookify.Interface.IButtonHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class PopupEmprestimoController {
    
    private IButtonHandler eventRenovar;
    private IButtonHandler eventEncerrar;
    private IButtonHandler eventClose;

    @FXML
    private Text autorText;

    @FXML
    private Text dataDevolucaoText;

    @FXML
    private Text dataInicioText;

    @FXML
    private Text errorText;

    @FXML
    private Text idText;

    @FXML
    private Text numRegText;

    @FXML
    private Text responsavelText;

    @FXML
    private Text tituloText;

    protected void setRenovarManipulador(IButtonHandler event){
        this.eventRenovar = event;
    }
    
    protected void setEncerrarManipulador(IButtonHandler event){
        this.eventEncerrar = event;
    }
    
    protected void setFecharManipulador(IButtonHandler event){
        this.eventClose = event;
    }
    
    @FXML
    public void renovar(ActionEvent event) {
        eventRenovar.handler();
    }

    @FXML
    public void encerrar(ActionEvent event) {
        eventEncerrar.handler();
    }
    
    // seta as informações que serão mostradas no popup de empréstimo
    protected void setInfo(String titulo, String numReg, String autor, String matricula, String cpf, String responsavel, String dataIncio, String dataDevolucao){
        if(titulo.length() > 40){
            titulo =titulo.substring(0,40) + "...";
        }
         if(responsavel.length() >= 36){
            responsavel =responsavel.substring(0,36) + "...";
        }
         if(autor.length() > 36){
            autor =autor.substring(0,36) + "...";
        }
        tituloText.setText(titulo);
        numRegText.setText(numReg);
        autorText.setText(autor);
        responsavelText.setText(responsavel);
        if(matricula == null){
            idText.setText(cpf);
        } else{
            idText.setText(matricula);
        }
        dataInicioText.setText(dataIncio);
        dataDevolucaoText.setText(dataDevolucao);
    }
    
    @FXML
    public void fechar(ActionEvent event){
        eventClose.handler();
    }
}
