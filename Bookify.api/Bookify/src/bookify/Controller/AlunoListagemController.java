package bookify.Controller;

import bookify.Treinando;
import bookify.model.dao.BookifyDatabase;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Circle;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class AlunoListagemController implements Initializable {

    @FXML
    private Button btnAlunos;

    @FXML
    private Button btnEmprestimos;

    @FXML
    private ImageView btnHome;

    @FXML
    private Button btnLivros;

    @FXML
    private Button btnProfessores;

    @FXML
    private VBox render_box_elements;
    
    @FXML
    private TextField nomeTxt;
    
     @FXML
    private TextField telefoneTxt;

    @FXML
    private TextField turmaTxt;
    
     @FXML
    private TextField cursoTxt;

    @FXML
    private TextField emailTxt;
    
     @FXML
    private TextField matriculaTxt;
     
     @FXML
     private Button btnCancelarEdit;
     
     @FXML
     private Button btnConfirmarEdit;


    @FXML
    void alunoMenu(ActionEvent event) {
        Treinando.mudarTela(1);
    }

    @FXML
    void homeMenu(MouseEvent event) {
        Treinando.mudarTela(4);
    }

    @FXML
    void livroMenu(ActionEvent event) {
        Treinando.mudarTela(7);
    }

    @FXML
    void professorMenu(ActionEvent event) {
        Treinando.mudarTela(2);
    }
    
    

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var repository = new BookifyDatabase();
        AlunoComponenteController componente;
        nomeTxt.setDisable(true);
        emailTxt.setDisable(true);
        cursoTxt.setDisable(true);
        turmaTxt.setDisable(true);
        telefoneTxt.setDisable(true);
        matriculaTxt.setDisable(true);
        
        
        
        try {

  
            var response = repository.get("Usuario", "Tipo = 'A'");
            HBox aux = new HBox();
            int i = -1;
            render_box_elements.getChildren().add(aux);
            while (response.next()) {
                if(i == 1){
                    i = -1;
                    aux = new HBox();
                    render_box_elements.getChildren().add(aux);
                }
                FXMLLoader loader = new FXMLLoader();
                loader.setLocation(getClass().getResource("../View/Aluno-componente-window.fxml"));
                Pane painel = loader.load();
                componente = loader.getController();
                
                componente.setTexto(response.getString("nome"), 
                        response.getString("matricula"), 
                        response.getString("curso"), 
                        response.getString("telefone"), 
                        response.getString("turma"));
                
                String Nome = response.getString("Nome");
                Label label = new Label(Nome);
                aux.getChildren().add(painel);
                System.out.println(aux);
               i++;
                
                
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlunoListagemController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            System.out.println("printa ai pai");
            Logger.getLogger(AlunoListagemController.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }

}
