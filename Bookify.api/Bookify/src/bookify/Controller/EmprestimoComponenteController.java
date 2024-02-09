package bookify.Controller;

import bookify.Interface.IButtonHandler;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;

public class EmprestimoComponenteController {

    private IButtonHandler event;
    
    @FXML 
    private StackPane statusPane;
   
    @FXML 
    private Text statusText;
   
    @FXML
    private Text dataDevolucaoText;

    @FXML
    private Text dataInicioText;

    @FXML
    private Text idText;

    @FXML
    private Text nomeText;

    @FXML
    private Text tituloText;
    
    @FXML
    private Text clienteText;
    
    // seta as informações mostradas no componente
    protected void setTexto(String titulo, String nome, String matricula, String cpf ,String dataInicio, String dataDevolucao){
        if(titulo.length() > 30){
            titulo= titulo.substring(0,30) + "...";
        }  
        if(nome.length() > 40){
            nome= nome.substring(0,40) + "...";
        }  
        tituloText.setText(titulo);
          nomeText.setText(nome);
          if(matricula == null){
              idText.setText(cpf);
              clienteText.setText("Professor:");
          }else {
            idText.setText(matricula);
            clienteText.setText("Aluno:");
          }
          dataInicioText.setText(formataData(dataInicio));
          dataDevolucaoText.setText(formataData(dataDevolucao));
    }

    // mostra o estado de um empréstimo, se ele está atrasado ou ativo
    protected void setStatus(boolean status){
        
            if(status){
                statusPane.setStyle("-fx-background-color: #42f58a;"
                        + "-fx-background-radius: 20px");
                statusText.setText("ATIVO");
                statusPane.setAlignment(statusText, javafx.geometry.Pos.CENTER);
            } else{
                statusPane.setStyle("-fx-background-color: #eb3434;"
                + "-fx-background-radius: 20px");
                statusText.setText("ATRASADO");
                statusPane.setAlignment(statusText, javafx.geometry.Pos.CENTER);
            }
    }

    public void setEvento(IButtonHandler event) {
        this.event = event;
    }
     
    @FXML
    protected void ManipuladorEvento(){
        event.handler();
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
