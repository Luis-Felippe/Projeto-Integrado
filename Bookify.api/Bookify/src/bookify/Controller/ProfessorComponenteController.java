package bookify.Controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class ProfessorComponenteController {
    

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
}
