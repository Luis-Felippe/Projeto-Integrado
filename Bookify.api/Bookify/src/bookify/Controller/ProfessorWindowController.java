/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package bookify.Controller;

import bookify.Treinando;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

/**
 * FXML Controller class
 *
 * @author felip
 */
public class ProfessorWindowController implements Initializable {

    /**
     * Initializes the controller class.
     */
    @FXML
    protected void cadastrarProfessor(ActionEvent e){
        Treinando.mudarTela(3);
    }
    
    @FXML
    protected void menuAluno(ActionEvent e){
        Treinando.mudarTela(1);
    }
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
