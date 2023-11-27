package bookify.Controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

public class RealizarEmprestimoController {

    private TelasController tela = new TelasController();
    
    @FXML
    private DatePicker LivDateDevolucao;

    @FXML
    private DatePicker LivDateInicio;

    @FXML
    private TextField LivTextAutor;

    @FXML
    private TextField LivTextCod;

    @FXML
    private TextField LivTextEditora;

    @FXML
    private TextField LivTextExemplar;

    @FXML
    private TextField LivTextMatricula;

    @FXML
    private TextField LivTextNome;

    @FXML
    private TextField LivTextObservacao;

    @FXML
    private TextField LivTextTelefone;

    @FXML
    private TextField LivTextTitulo;

    @FXML
    void alunoMenu(ActionEvent event) throws IOException {
        tela.switchScreen(1);
    }

    @FXML
    void homeMenu(MouseEvent event) throws IOException {
        tela.switchScreen(4);
    }

    @FXML
    void livroMenu(ActionEvent event) throws IOException {
        tela.switchScreen(7);
    }

    @FXML
    void professorMenu(ActionEvent event) throws IOException {
        tela.switchScreen(2);
    }
    
    

}
