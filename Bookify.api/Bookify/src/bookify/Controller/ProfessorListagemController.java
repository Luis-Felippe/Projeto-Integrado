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
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;

public class ProfessorListagemController extends TelasProfessorController implements Initializable {

    private BookifyDatabase repositorio =  BookifyDatabase.getInstancia();
    private IFabricaPopupMsg MsgFabrica = new FabricaPopupMsg();
    private IFabricaComponente componenteFabrica = new FabricaComponente();
    private IFabricaPopupAcao popupAcaoFabrica = new FabricaPopupAcao();
    
    private String currentEditProfessor;
    
    @FXML
    private ToggleButton professoresEmprestimosBtn;
    
    @FXML
    private Pane mainContainer;

    @FXML
    private VBox render_box_elements;
    
    @FXML
    private TextField pesquisarText;
    
    // retorna o id do professor selecionado
    public String getIdAtualEditar(){
        return currentEditProfessor;
    }
    
    // recebe o id de um professor e abre a tela de edição de professor
    private void editarProfessorManipulador(String id) {
        this.currentEditProfessor = id;
        try {
            super.editarProfessor(currentEditProfessor);
        } catch (IOException ex) {
            Logger.getLogger(ProfessorListagemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Adiciona o componente que mostra as informações do professor e seta seus atributos e funções
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

    // abre o popup que confirma ou cancela a exclusão do professor
    private void principalPopupManipulador(String id, Pane mainContainer){
        IPopupAcao controller = popupAcaoFabrica.criaPopupAcao("PopupProfessor");
        Pane popup = controller.getFxml();
        mainContainer.getChildren().add(popup);
        controller.setCancelarManipulador(()->{
            cancelarManipulador(popup, mainContainer);
        });
        controller.setConfirmarManipulador(()->{
            confirmarManipulador(id, mainContainer, controller, popup);
        });
    }
    
    // confirma a exclusão do professor
    private void confirmarManipulador(String id, Pane mainContainer, IPopupAcao controlador, Pane popup){
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
    
    // cancela a exclusão do professor
    private void cancelarManipulador(Pane popup, Pane mainContainer){
        mainContainer.getChildren().remove(popup);
    }
    
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buscar();
    }
    
    // Pesquisa acionada pela tecla ENTER
    @FXML
    protected void buscarTeclaPressionada(){
        pesquisarText.setOnKeyPressed(event->{
            if(event.getCode() == KeyCode.ENTER){
                buscar();
            }
        });
    }
    
    // Faz uma busca no banco de dados, de acordo com o filtro
    @FXML
    public void buscar(){
        ResultSet response;
        render_box_elements.getChildren().clear();
        String searchBar = pesquisarText.getText().toUpperCase();
        String consult = String.format("Tipo = 'P' AND ((UPPER(nome) LIKE '%%%s%%') OR"
                + " (UPPER(disciplina) LIKE '%%%s%%') OR "
                + "(UPPER(email) LIKE '%%%s%%')) ORDER BY nome ASC",searchBar, searchBar, searchBar, searchBar );
        
        try {
            if(professoresEmprestimosBtn.isSelected()){
                response = repositorio.get("professores_com_emprestimos", consult);
            } else{
                response = repositorio.get("Usuario", consult);
            }
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