package bookify.Controller;

import bookify.Interface.IButtonHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.text.Text;

public class PopupEmprestimoController {
    
    private IButtonHandler eventRenovar;
    private IButtonHandler eventEncerrar;

    @FXML
    private Text autorText;

    @FXML
    private Button cancelButton;

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


    protected void setRenovarHandler(IButtonHandler event){
        this.eventRenovar = event;
    }
    
    protected void setEncerrarHandler(IButtonHandler event){
        this.eventEncerrar = event;
    }
    
    @FXML
    void renovar(ActionEvent event) {
        eventRenovar.handler();
    }

    @FXML
    void encerrar(ActionEvent event) {
        eventEncerrar.handler();
    }
    protected void setInfo(String titulo, String numReg, String autor,String matricula, String cpf, String responsavel, String dataIncio, String dataDevolucao){
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
}
