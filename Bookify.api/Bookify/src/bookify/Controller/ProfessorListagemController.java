package bookify.Controller;

import bookify.Interface.IComponente;
import bookify.Interface.IFabricaComponente;
import bookify.Interface.IFabricaPopupMsg;
import bookify.Interface.IPopupMsg;
import bookify.model.dao.BookifyDatabase;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.layout.VBox;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane; 
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;

public class ProfessorListagemController extends TelasProfessorController implements Initializable {

    private BookifyDatabase repositorio =  BookifyDatabase.getInstancia();
    private IFabricaPopupMsg MsgFabrica = new FabricaPopupMsg();
    private IFabricaComponente componenteFabrica = new FabricaComponente();
    
    
    private String currentEditProfessor;
    
    @FXML
    private Pane mainContainer;

    @FXML
    private VBox render_box_elements;
    
    @FXML
    private TextField pesquisarText;
    
    public String getIdAtualEditar(){
        return currentEditProfessor;
    }
    
    private void editarProfessorManipulador(String id) {
        this.currentEditProfessor = id;
        try {
            super.editarProfessor(currentEditProfessor);
        } catch (IOException ex) {
            Logger.getLogger(ProfessorListagemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void adicionarComponente(HBox box, ResultSet res) throws IOException, SQLException{
        IComponente componente = componenteFabrica.criaComponente("ProfessorComponente");
        
        
        Map<String, String> atributos = new HashMap<>();
        atributos.put("nome", res.getString("nome"));
        atributos.put("cpf", res.getString("cpf"));
        atributos.put("disciplina", res.getString("disciplina"));
        atributos.put("telefone", res.getString("telefone"));
        atributos.put("email", res.getString("email"));
        
        componente.setTexto(atributos);
        
        String id = res.getString("id_usuario");
        
        componente.setDeletarManipulador(()->{
            principalPopupManipulador(id, mainContainer);
        });
        
        componente.setEditarManipulador(()->{
            editarProfessorManipulador(id);
            
        });
        
        box.getChildren().add(componente.getFxml());
    }

    private void principalPopupManipulador(String id, Pane mainContainer){
        try {
                FXMLLoader loaderPopup = new FXMLLoader();
                loaderPopup.setLocation(getClass().getResource("../View/Popup.fxml"));
                Pane popup = loaderPopup.load();
                
                mainContainer.getChildren().add(popup);
                
                PopupController popupController = loaderPopup.getController();
                
                popupController.setCancelarManipulador(()->{
                    cancelarManipulador(popup, mainContainer);
                });
                
                popupController.setConfirmarManipulador(()->{
                    confirmarManipulador(id, mainContainer, popupController, popup);
                });
        } catch (IOException ex) {
            Logger.getLogger(ProfessorListagemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void confirmarManipulador(String id, Pane mainContainer, PopupController controlador, Pane popup){
        try {
            repositorio.delete("Usuario", String.format("id_usuario = %s", id));
            mainContainer.getChildren().remove(popup);
            IPopupMsg controller = MsgFabrica.criaPopupMsg("PopupExcluirMsg");
            controller.setManipulador(()->{
                mainContainer.getChildren().remove(controller.getPopup());
            });
            mainContainer.getChildren().add(controller.getPopup());
            buscar();
        } catch (SQLException ex) {
            controlador.erro();
        }
    }
    
    private void cancelarManipulador(Pane popup, Pane mainContainer){
        mainContainer.getChildren().remove(popup);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buscar();
    }
    
    @FXML
    protected void buscarTeclaPressionada(){
        pesquisarText.setOnKeyPressed(event->{
            if(event.getCode() == KeyCode.ENTER){
                buscar();
            }
        });
    }
    
    @FXML
    public void buscar(){
        render_box_elements.getChildren().clear();
        String searchBar = pesquisarText.getText().toUpperCase();
        String consult = String.format("Tipo = 'P' AND ((UPPER(nome) LIKE '%%%s%%') OR"
                + " (UPPER(disciplina) LIKE '%%%s%%') OR "
                + "(UPPER(email) LIKE '%%%s%%')) ORDER BY nome ASC",searchBar, searchBar, searchBar, searchBar );
        
        try {
            var response = repositorio.get("Usuario", consult);
            HBox box = null;
            boolean status = true;
            while(response.next()){
                if(status){
                   box = new HBox();
                   render_box_elements.getChildren().add(box);
                   status = false;
                   adicionarComponente(box, response);
                } else{
                   status = true;
                   adicionarComponente(box, response);
                }
            }
        } catch (SQLException | IOException ex) {
            Logger.getLogger(AlunoListagemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}