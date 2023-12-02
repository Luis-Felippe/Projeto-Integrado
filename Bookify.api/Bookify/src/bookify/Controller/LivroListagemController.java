package bookify.Controller;

import bookify.model.dao.BookifyDatabase;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class LivroListagemController implements Initializable {
    private TelasController tela = new TelasController();
    
    private String currentEditLivro;
    
    @FXML
    private Pane mainContainer;

    @FXML
    private TextField pesquisarText;

    @FXML
    private VBox render_box_elements;
    
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
    
    private void editarLivroManipulador(String id) {
        this.currentEditLivro = id;
        try {
            tela.trocarTela("livros/edicao", currentEditLivro);
        } catch (IOException ex) {
            Logger.getLogger(ProfessorListagemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void adicionarComponente(HBox box, ResultSet res) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/Livro-componente-window.fxml"));
        Pane painel = loader.load();
        LivroComponenteController componente = loader.getController();
        componente.setTexto(res.getString("titulo"), 
        res.getString("num_registro"), 
        res.getString("autor"),
        res.getString("volume"), 
        res.getString("exemplar"), 
        res.getString("data"),
        res.getString("observacao"));

        String id = res.getString("num_registro");
        
        componente.setEditarManipulador(()->{
            editarLivroManipulador(id);    
        });

        componente.setDeletarManipulador(()->{
            deletarLivroManipulador(id, mainContainer);
        });
        
        box.getChildren().add(painel);
    }


    private void deletarLivroManipulador(String id, Pane mainContainer){
        try {
            FXMLLoader loaderPopup = new FXMLLoader();
            loaderPopup.setLocation(getClass().getResource("../View/Popup-livro.fxml"));
            Pane popup = loaderPopup.load();

            mainContainer.getChildren().add(popup);

            PopupLivroController popupController = loaderPopup.getController();

            popupController.setCancelarManipulador(()->{
                cancelarManipulador(popup, mainContainer);
            });

            popupController.setConfirmarManipulador(()->{
                confirmarManipulador(id, mainContainer, popupController, popup);
            });
        } catch (IOException ex) {
            Logger.getLogger(LivroListagemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void confirmarManipulador(String id, Pane mainContainer, PopupLivroController controlador, Pane popup){
        var repository = new BookifyDatabase();
        try {
            repository.delete("livro", String.format("num_registro = '%s'", id));
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
            Logger.getLogger(LivroListagemController.class.getName()).log(Level.SEVERE, null, ex);
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
        String consult = String.format("(UPPER (autor) like '%%%s%%' ) OR (UPPER(titulo) like '%%%s%%') ORDER BY titulo ASC",searchBar, searchBar);
        
        try {
            var response = repository.get("Livro", consult);
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
        } 
        catch (SQLException | IOException ex) {
            Logger.getLogger(AlunoListagemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
