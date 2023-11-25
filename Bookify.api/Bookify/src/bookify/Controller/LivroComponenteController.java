package bookify.Controller;

import javafx.fxml.FXML;
import javafx.scene.text.Text;

public class LivroComponenteController {
    
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
    
    @FXML
    protected void setTexto(String titulo, String numReg, String autor, String volume, String exemplar, String data, String observacao){
        tituloText.setText(titulo);
        numRegText.setText(numReg);
        autorText.setText(autor);
        volumeText.setText(volume);
        exemplarText.setText(exemplar);
        dataText.setText(data);
        observacaoText.setText(observacao);
    }
}