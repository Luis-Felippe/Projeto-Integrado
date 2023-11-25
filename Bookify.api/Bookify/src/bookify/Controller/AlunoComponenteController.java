package bookify.Controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class AlunoComponenteController{
    
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
    
    @FXML
    protected void setTexto(String nome, String matricula, String curso, String telefone, String turma){
        nomeText.setText(nome);
        matriculaText.setText(matricula);
        cursoText.setText(curso);
        telefoneText.setText(telefone);
        turmaText.setText(turma);
    }
    
}
