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
import javafx.scene.input.KeyCode;


public class AlunoListagemController extends TelasAlunoController implements Initializable {
    private BookifyDatabase repositorio = BookifyDatabase.getInstancia();
    private IFabricaPopupMsg MsgFabrica = new FabricaPopupMsg();
    private IFabricaComponente componenteFabrica = new FabricaComponente();
    private IFabricaPopupAcao popupAcaoFabrica = new FabricaPopupAcao();
    
    private String currentEditAluno;
    
    @FXML
    private Pane mainContainer;

    @FXML
    private VBox render_box_elements;
    
    @FXML
    private TextField pesquisarText;
    
     // Recebe o id de um aluno para passar as informações do aluno para a tela de edição
    private void editarAlunoManipulador(String id) {
        this.currentEditAluno = id;
        try {
            super.editarAluno(currentEditAluno);
        } catch (IOException ex) {
            Logger.getLogger(ProfessorListagemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Adiciona o componente que mostra as informações do aluno e seta seus atributos e funções
    private void adicionarComponente(HBox box, ResultSet res) throws IOException, SQLException{
        
        IComponente componente = componenteFabrica.criaComponente("AlunoComponente");
        
        Map<String, String> atributos = new HashMap<>();
        atributos.put("nome", res.getString("nome"));
        atributos.put("matricula", res.getString("matricula"));
        atributos.put("curso", res.getString("curso"));
        atributos.put("telefone", res.getString("telefone"));
        atributos.put("turma", res.getString("turma"));
        
        componente.setTexto(atributos);
        
        String id = res.getString("id_usuario");
        

        componente.setEditarManipulador(()->{
            editarAlunoManipulador(id);    
        });
        componente.setDeletarManipulador(()->{
            deletarAlunoManipulador(id, mainContainer);
        });
        
        box.getChildren().add(componente.getFxml());
    }
    
    // Cria popup de confirmação de exclusão
    private void deletarAlunoManipulador(String id, Pane mainContainer){
        IPopupAcao controller = popupAcaoFabrica.criaPopupAcao("PopupAluno");
        Pane popup = controller.getFxml();
        mainContainer.getChildren().add(popup);
        controller.setCancelarManipulador(()->{
            cancelarManipulador(popup, mainContainer);
        });
        controller.setConfirmarManipulador(()->{
            confirmarManipulador(id, mainContainer, controller, popup);
        });
    }
    
    // Chama a função de excluir e exibe a mensagem de exclusão realizada.
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
        } catch (SQLException ex){
            controlador.erro();
        }
    }
    
    // Fecha o popup
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
    protected void buscar(){
        render_box_elements.getChildren().clear();
        String searchBar = pesquisarText.getText().toUpperCase();
        String consult = String.format("Tipo = 'A' AND ((UPPER(nome) LIKE '%%%s%%') OR"
                + " (UPPER(curso) LIKE '%%%s%%') OR (UPPER(turma) LIKE '%%%s%%') OR "
                + "(UPPER(email) LIKE '%%%s%%' )) ORDER BY nome ASC",searchBar, searchBar, searchBar, searchBar );
        
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



