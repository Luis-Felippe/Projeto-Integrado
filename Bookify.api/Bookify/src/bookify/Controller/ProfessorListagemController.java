package bookify.Controller;

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
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane; 
import java.sql.ResultSet;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class ProfessorListagemController implements Initializable {

    private TelasController tela = new TelasController();
    
    private String currentEditProfessor;
    
    @FXML
    private Pane mainContainer;

    @FXML
    private VBox render_box_elements;
    
    @FXML
    private TextField pesquisarText;
 

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
    
    public String getCurrentEditId(){
        return currentEditProfessor;
    }
    
    private void editProfessorHandler(String id) {
        this.currentEditProfessor = id;
        try {
            tela.switchScreen(13, currentEditProfessor);
        } catch (IOException ex) {
            Logger.getLogger(ProfessorListagemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void addComponent(HBox box, ResultSet res) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/Professor-componente-window.fxml"));
        Pane painel = loader.load();
        ProfessorComponenteController componente = loader.getController();
        componente.setTexto(res.getString("nome"), 
        res.getString("cpf"), 
        res.getString("disciplina"), 
        res.getString("telefone"), 
        res.getString("email"));
        
        String id = res.getString("id_usuario");
        
        componente.setDeleteHandler(()->{
            mainPopupHandler(id, mainContainer);
        });
        
        componente.setEditHandler(()->{
            editProfessorHandler(id);
            
        });
        
        box.getChildren().add(painel);
    }

    private void mainPopupHandler(String id, Pane mainContainer){
        try {
                FXMLLoader loaderPopup = new FXMLLoader();
                loaderPopup.setLocation(getClass().getResource("../View/Popup.fxml"));
                Pane popup = loaderPopup.load();
                
                mainContainer.getChildren().add(popup);
                
                PopupController popupController = loaderPopup.getController();
                
                popupController.setCancelHandler(()->{
                    cancelHandler(popup, mainContainer);
                });
                
                popupController.setConfirmHandler(()->{
                    confirmHandler(id, mainContainer, popupController, popup);
                });
            } catch (IOException ex) {
                Logger.getLogger(ProfessorListagemController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void confirmHandler(String id, Pane mainContainer, PopupController controlador, Pane popup){
        var repository = new BookifyDatabase();
                    try {
                        repository.delete("Usuario", String.format("id_usuario = %s", id));
                        mainContainer.getChildren().remove(popup);
                        search();
                    } catch (SQLException ex) {
                        controlador.erro();
                    }
    }
    
    private void cancelHandler(Pane popup, Pane mainContainer){
        mainContainer.getChildren().remove(popup);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        search();
    }
    
    @FXML
    protected void searchKeyListener(){
        pesquisarText.setOnKeyPressed(event->{
            if(event.getCode() == KeyCode.ENTER){
                search();
            }
        });
    }
    
    @FXML
    public void search(){
        render_box_elements.getChildren().clear();
        var repository = new BookifyDatabase();
        String searchBar = pesquisarText.getText().toUpperCase();
        String consult = String.format("Tipo = 'P' AND ((UPPER(nome) LIKE '%%%s%%') OR"
                + " (UPPER(disciplina) LIKE '%%%s%%') OR "
                + "(UPPER(email) LIKE '%%%s%%')) ORDER BY nome ASC",searchBar, searchBar, searchBar, searchBar );
        
        try {
        var response = repository.get("Usuario", consult);
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