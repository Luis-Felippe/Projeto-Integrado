package bookify.Controller;

import bookify.Interface.IButtonHandler;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ProfessorComponenteController {
    
    IButtonHandler deleteEvent;
    IButtonHandler editEvent;
    

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
    
    @FXML
    protected void setTexto(String nome, String cpf, String disciplina, String telefone, String email){
        if(nome.length() > 32){
            nome = nome.substring(0,32) + "...";
        }
        nomeText.setText(nome);
        cpfText.setText(cpf);
        disciplinaText.setText(disciplina);
        telefoneText.setText(telefone);
        emailText.setText(email);
    }
    
    protected void setDeletarManipulador(IButtonHandler event){
        this.deleteEvent = event;
    }
    
    protected void setEditarManipulador(IButtonHandler event){
        this.editEvent = event;
    }
    
    @FXML
    protected void deletar(){
        deleteEvent.handler();
    }
    
    @FXML
    protected void editar(){
        editEvent.handler();
    }
}
