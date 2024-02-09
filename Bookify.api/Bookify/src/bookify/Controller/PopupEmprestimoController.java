package bookify.Controller;

import bookify.Interface.IButtonHandler;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
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
        dataInicioText.setText(formataData(dataIncio));
        dataDevolucaoText.setText(formataData(dataDevolucao));
    }
    
    @FXML
    public void fechar(ActionEvent event){
        eventClose.handler();
    }
    
    private String formataData(String info){
        SimpleDateFormat formatoAtual = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat novoFormato = new SimpleDateFormat("dd/MM/yyyy");
        String novaData = "";
        try{
            Date data = formatoAtual.parse(info);
            novaData = novoFormato.format(data);
        } catch (ParseException ex) {
            Logger.getLogger(EmprestimoComponenteController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return novaData;
    }
}
