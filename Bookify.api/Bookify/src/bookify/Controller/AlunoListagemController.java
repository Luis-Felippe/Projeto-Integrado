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


public class AlunoListagemController implements Initializable {

    private TelasController tela = new TelasController();
    
    private String currentEditAluno;
    
    @FXML
    private Pane mainContainer;

    @FXML
    private VBox render_box_elements;
    
    @FXML
    private TextField pesquisarText;
  
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
    protected void alunoMenu(ActionEvent event) throws IOException {
        tela.trocarTela("alunos/menu");
    }

    @FXML
    protected void homeMenu(MouseEvent event) throws IOException {
        tela.trocarTela("home");
    }

    @FXML
    protected void livroMenu(ActionEvent event) throws IOException {
        tela.trocarTela("livros/menu");
    }
    
    @FXML
    protected void emprestimoMenu() throws IOException{
        tela.trocarTela("emprestimos/listagem");
    }

    @FXML
    protected void professorMenu(ActionEvent event) throws IOException {
        tela.trocarTela("professores/menu");
    }
    
     private void editarAlunoManipulador(String id) {
        this.currentEditAluno = id;
        try {
            tela.trocarTela("alunos/edicao", currentEditAluno);
        } catch (IOException ex) {
            Logger.getLogger(ProfessorListagemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void adicionarComponente(HBox box, ResultSet res) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/Aluno-componente-window.fxml"));
        Pane painel = loader.load();
        AlunoComponenteController componente = loader.getController();
        componente.setTexto(res.getString("nome"), 
        res.getString("matricula"), 
        res.getString("curso"), 
        res.getString("telefone"), 
        res.getString("turma"));
        
        String id = res.getString("id_usuario");
        

        componente.setEditarManipulador(()->{
            editarAlunoManipulador(id);    
        });
        componente.setDeletarManipulador(()->{
            deletarAlunoManipulador(id, mainContainer);
        });
        
        box.getChildren().add(painel);
    }
    
    private void deletarAlunoManipulador(String id, Pane mainContainer){
        try {
                FXMLLoader loaderPopup = new FXMLLoader();
                loaderPopup.setLocation(getClass().getResource("../View/Popup-aluno.fxml"));
                Pane popup = loaderPopup.load();
                
                mainContainer.getChildren().add(popup);
                
                PopupAlunoController popupController = loaderPopup.getController();
                
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
    
    private void confirmarManipulador(String id, Pane mainContainer, PopupAlunoController controlador, Pane popup){
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
            Logger.getLogger(AlunoListagemController.class.getName()).log(Level.SEVERE, null, ex);
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
    protected void buscar(){
        render_box_elements.getChildren().clear();
        var repository = new BookifyDatabase();
        String searchBar = pesquisarText.getText().toUpperCase();
        String consult = String.format("Tipo = 'A' AND ((UPPER(nome) LIKE '%%%s%%') OR"
                + " (UPPER(curso) LIKE '%%%s%%') OR (UPPER(turma) LIKE '%%%s%%') OR "
                + "(UPPER(email) LIKE '%%%s%%' )) ORDER BY nome ASC",searchBar, searchBar, searchBar, searchBar );
        
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



