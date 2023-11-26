package bookify.Controller;

import bookify.Interface.IButtonHandler;
import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ProfessorComponenteController {
    
    IButtonHandler event;

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
        nomeText.setText(nome);
        cpfText.setText(cpf);
        disciplinaText.setText(disciplina);
        telefoneText.setText(telefone);
        emailText.setText(email);
    }
    
    protected void setHandler(IButtonHandler event){
        this.event = event;
    }
    
    @FXML
    protected void delete(){
        event.handler();
    }
}
