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
    protected void alunoMenu() throws IOException{
        tela.trocarTela("alunos/menu");
    }
    @FXML
    protected void professorMenu() throws IOException{
        tela.trocarTela("professores/menu");
    }
    @FXML
    protected void homeMenu() throws IOException{
        tela.trocarTela("home");
    }
    @FXML
    protected void livroMenu() throws IOException{
        tela.trocarTela("livros/menu");
    }
    @FXML
    protected void emprestimoMenu() throws IOException{
        tela.trocarTela("emprestimos/listagem");
    }
    
    public String getIdAtualEditar(){
        return currentEditProfessor;
    }
    
    private void editarProfessorManipulador(String id) {
        this.currentEditProfessor = id;
        try {
            tela.trocarTela("professores/edicao", currentEditProfessor);
        } catch (IOException ex) {
            Logger.getLogger(ProfessorListagemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void adicionarComponente(HBox box, ResultSet res) throws IOException, SQLException{
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
        
        componente.setDeletarManipulador(()->{
            principalPopupManipulador(id, mainContainer);
        });
        
        componente.setEditarManipulador(()->{
            editarProfessorManipulador(id);
            
        });
        
        box.getChildren().add(painel);
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
        var repository = new BookifyDatabase();
        try {
            repository.delete("Usuario", String.format("id_usuario = %s", id));
            mainContainer.getChildren().remove(popup);
            FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Popup-excluir-confirmar.fxml"));
            Pane popupConfirm = loader.load();
            PopupExcluirMsgController controller = loader.getController();
            controller.setManipulador(()->{
                mainContainer.getChildren().remove(popupConfirm);
            });
            mainContainer.getChildren().add(popupConfirm);
            buscar();
        } catch (SQLException ex) {
            controlador.erro();
        } catch (IOException ex) {
            Logger.getLogger(ProfessorListagemController.class.getName()).log(Level.SEVERE, null, ex);
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