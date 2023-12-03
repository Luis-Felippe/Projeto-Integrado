package bookify.Controller;

import bookify.Controller.PopupMensagem.FabricaPopupMsg;
import bookify.Controller.PopupAcao.FabricaPopupAcao;
import bookify.Controller.Componentes.FabricaComponente;
import bookify.Interface.IComponente;
import bookify.Interface.IFabricaComponente;
import bookify.Interface.IFabricaPopupAcao;
import bookify.Interface.IFabricaPopupMsg;
import bookify.Interface.IPopupAcao;
import bookify.Interface.IPopupMsg;
import bookify.Models.BookifyDatabase;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class LivroListagemController extends TelasLivrosController implements Initializable {
    
    private BookifyDatabase repositorio = BookifyDatabase.getInstancia();
    private IFabricaPopupMsg MsgFabrica = new FabricaPopupMsg();
    private IFabricaComponente componenteFabrica = new FabricaComponente();
    private IFabricaPopupAcao popupAcaoFabrica = new FabricaPopupAcao();
    
    private String currentEditLivro;
    
    @FXML
    private Pane mainContainer;

    @FXML
    private TextField pesquisarText;

    @FXML
    private VBox render_box_elements;
    
    private void editarLivroManipulador(String id) {
        this.currentEditLivro = id;
        try {
            super.editarLivro(currentEditLivro);
        } catch (IOException ex) {
            Logger.getLogger(ProfessorListagemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void adicionarComponente(HBox box, ResultSet res) throws IOException, SQLException{
        IComponente componente = componenteFabrica.criaComponente("LivroComponente");
        Map<String, String> atributos = new HashMap<>();
        atributos.put("titulo", res.getString("titulo"));
        atributos.put("num_registro", res.getString("num_registro"));
        atributos.put("autor", res.getString("autor"));
        atributos.put("volume", res.getString("volume"));
        atributos.put("exemplar", res.getString("exemplar"));
        atributos.put("data", res.getString("data"));
        atributos.put("observacao", res.getString("observacao"));
        
        componente.setTexto(atributos);

        String id = res.getString("num_registro");
        
        componente.setEditarManipulador(()->{
            editarLivroManipulador(id);    
        });

        componente.setDeletarManipulador(()->{
            deletarLivroManipulador(id, mainContainer);
        });
        
        box.getChildren().add(componente.getFxml());
    }


    private void deletarLivroManipulador(String id, Pane mainContainer){
        IPopupAcao controller = popupAcaoFabrica.criaPopupAcao("PopupLivro");
        Pane popup = controller.getFxml();
        mainContainer.getChildren().add(popup);
        controller.setCancelarManipulador(()->{
            cancelarManipulador(popup, mainContainer);
        });
        controller.setConfirmarManipulador(()->{
            confirmarManipulador(id, mainContainer, controller, popup);
        });
    }
    
    private void confirmarManipulador(String id, Pane mainContainer, IPopupAcao controlador, Pane popup){
        try {
            repositorio.delete("livro", String.format("num_registro = '%s'", id));
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
    protected void buscar(){
        render_box_elements.getChildren().clear();
        String searchBar = pesquisarText.getText().toUpperCase();
        String consult = String.format("(UPPER (autor) like '%%%s%%' ) OR (UPPER(titulo) like '%%%s%%') ORDER BY titulo ASC",searchBar, searchBar);
        
        try {
            var response = repositorio.get("Livro", consult);
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
