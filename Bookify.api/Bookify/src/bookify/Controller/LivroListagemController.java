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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
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
    private ToggleButton livrosEmprestadosBtn;
    
    @FXML
    private Pane mainContainer;

    @FXML
    private TextField pesquisarText;

    @FXML
    private VBox render_box_elements;
    
    // recebe o id de um livro e abre a tela de edição de livro
    private void editarLivroManipulador(String id, String volume) {
        this.currentEditLivro = id;
        try {
            super.editarLivro(id,volume);
        } catch (IOException ex) {
            Logger.getLogger(ProfessorListagemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Adiciona o componente que mostra as informações do aluno e seta seus atributos e funções
    private void adicionarComponente(HBox box, ResultSet res) throws IOException, SQLException{
        IComponente componente = componenteFabrica.criaComponente("LivroComponente");
        Map<String, String> atributos = new HashMap<>();
        atributos.put("titulo", res.getString("titulo"));
        atributos.put("num_registro", res.getString("num_registro"));
        atributos.put("autor", res.getString("autor"));
        atributos.put("volume", res.getString("volume"));
        atributos.put("data", res.getString("data"));
        atributos.put("observacao", res.getString("observacao"));
        
        String id = res.getString("num_registro");
        String volume = res.getString("volume");
        List<String> gambiarra = new ArrayList<String>();
        gambiarra.add("TODOS");
        int cont = 0;
        do{
            if(atributos.get("num_registro").equals(res.getString("num_registro")) && atributos.get("volume").equals(res.getString("volume"))){
                gambiarra.add(res.getString("exemplar"));
                cont++;
            }
            else {
                break;
            }
        }while(res.next());
        
        atributos.put("exemplar", String.valueOf(cont));
        
        
        componente.setTexto(atributos);

        componente.setEditarManipulador(()->{
            editarLivroManipulador(id, volume);    
        });

        componente.setDeletarManipulador(()->{
            deletarLivroManipulador(id, mainContainer, gambiarra, volume);
        });
        
        box.getChildren().add(componente.getFxml());
    }

    // abre o popup de exclusão de livro
    private void deletarLivroManipulador(String id, Pane mainContainer, List<String> lista, String volume){
        IPopupAcao controller = popupAcaoFabrica.criaPopupAcao("PopupLivro");
        Pane popup = controller.getFxml();
        mainContainer.getChildren().add(popup);
        controller.preencherExemplares(lista);
        controller.setCancelarManipulador(()->{
            cancelarManipulador(popup, mainContainer);
        });
        controller.setConfirmarManipulador(()->{
            confirmarManipulador(id, mainContainer, controller, popup, volume);
        });
        
    }
    
    // confirma a exclusão de um livro
    private void confirmarManipulador(String id, Pane mainContainer, IPopupAcao controlador, Pane popup, String volume){
        try {
            if(controlador.getExemplar().equals("NENHUM")){
                System.out.println("SEleciona ai paezin");
            } else {
                if(controlador.getExemplar().equals("TODOS")){
                    repositorio.delete("livro", String.format("num_registro = '%s' and volume = '%s'", id, volume));
                }
                else{
                    repositorio.delete("livro", String.format("num_registro = '%s' and volume = '%s' and exemplar = '%s'", id, volume, controlador.getExemplar()));
                }
                mainContainer.getChildren().remove(popup);
                IPopupMsg controller = MsgFabrica.criaPopupMsg("PopupExcluirMsg");
                controller.setManipulador(()->{
                    mainContainer.getChildren().remove(controller.getPopup());
                });
                mainContainer.getChildren().add(controller.getPopup());
                buscar();
            }
           
        } catch (SQLException ex) {
            controlador.erro();
        }
    }
    
    // fecha o popup 
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
        ResultSet response;
        render_box_elements.getChildren().clear();
        String searchBar = pesquisarText.getText().toUpperCase();
        String consult = String.format("(UPPER (autor) like '%%%s%%' ) OR (UPPER(titulo) like '%%%s%%') OR (UPPER (categoria) like '%%%s%%') "
                + "ORDER BY titulo ASC,num_registro ASC, volume ASC, exemplar ASC",searchBar, searchBar, searchBar);
        
        try {
            if(livrosEmprestadosBtn.isSelected()){
                response = repositorio.get("livros_emprestados", consult);
            } else{
                response = repositorio.get("Livro", consult);
            }
            HBox box = null;
            boolean status = true;
            if(response.next()){
                while(!response.isAfterLast()){
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
            
            
        } 
        catch (SQLException | IOException ex) {
            Logger.getLogger(AlunoListagemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
