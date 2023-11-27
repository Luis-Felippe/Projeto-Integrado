package bookify.Controller;

import bookify.Interface.IButtonHandler;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
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
    
    protected void setTexto(String titulo, String nome, String matricula, String cpf ,String dataInicio, String dataDevolucao){
          tituloText.setText(titulo);
          nomeText.setText(nome);
          if(matricula == null){
              idText.setText(cpf);
          }else {
              idText.setText(matricula);
          }
          dataInicioText.setText(dataInicio);
          dataDevolucaoText.setText(dataDevolucao);
    }

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

    public void setEvent(IButtonHandler event) {
        this.event = event;
    }
    
    @FXML
    protected void handlerEvent(){
        event.handler();
    }

}
