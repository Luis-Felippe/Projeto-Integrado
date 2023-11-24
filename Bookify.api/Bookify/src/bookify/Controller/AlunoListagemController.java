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
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane; 
import java.sql.ResultSet;


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
    private Button editarButton;
  

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


    private void addComponent(HBox box, ResultSet res) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/Aluno-componente-window.fxml"));
        Pane painel = loader.load();
        AlunoComponenteController componente = loader.getController();
        componente.setTexto(res.getString("nome"), 
        res.getString("matricula"), 
        res.getString("curso"), 
        res.getString("telefone"), 
        res.getString("turma"));
        
        box.getChildren().add(painel);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        var repository = new BookifyDatabase();
        
        try {
            var response = repository.get("Usuario", "Tipo = 'A'");
            HBox box = null;
            boolean status = true;
            while(response.next()){
                if(status){
                   box = new HBox();
                   render_box_elements.getChildren().add(box);
                   status = false;
                   addComponent(box, response);
                } else{
                   status = true;
                   addComponent(box, response);
                }
            }
        } catch (SQLException ex) {
            Logger.getLogger(AlunoListagemController.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(AlunoListagemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
