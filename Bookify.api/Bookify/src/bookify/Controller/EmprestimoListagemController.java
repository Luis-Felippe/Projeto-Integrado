package bookify.Controller;

import bookify.Controller.PopupMensagem.FabricaPopupMsg;
import bookify.Interface.IFabricaPopupMsg;
import bookify.Interface.IPopupMsg;
import bookify.Models.BookifyDatabase;
import java.io.IOException;
import java.net.URL;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class EmprestimoListagemController extends TelasController implements Initializable{
    
    private BookifyDatabase repositorio = BookifyDatabase.getInstancia();
    private IFabricaPopupMsg MsgFabrica = new FabricaPopupMsg();
    
    @FXML
    private ToggleButton atrasadosBtn;
    
    @FXML
    private Pane mainContainer;
    
    @FXML
    private TextField pesquisarText;

    @FXML
    private VBox render_box_elements;
    
    // Adiciona o componente que mostra as informações do empréstimo e seta seus atributos e funções
    @FXML
    private void adicionarComponente(HBox box, ResultSet res) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/Emprestimo-componente-window.fxml"));
        Pane painel = loader.load();
        EmprestimoComponenteController componente = loader.getController();
        componente.setTexto(res.getString("titulo_livro"), 
        res.getString("nome_usuario"),
        res.getString("identificador_usuario"), 
        res.getString("identificador_usuario"),
        res.getString("data_inicio"), 
        res.getString("data_devolucao"));
        

       
        Map<String, String> values = new HashMap<>();
        values.put("data_inicio",res.getString("data_inicio"));
        values.put("data_devolucao",res.getString("data_devolucao"));
        values.put("id_usuario",res.getString("id_usuario"));
        values.put("num_registro",res.getString("num_registro_livro"));
        values.put("nome",res.getString("nome_usuario"));
        values.put("titulo",res.getString("titulo_livro"));
        values.put("autor",res.getString("autor_livro"));
        values.put("cpf",res.getString("identificador_usuario"));
        values.put("matricula",res.getString("identificador_usuario"));
        values.put("turma", res.getString("turma_usuario"));
        values.put("volume", res.getString("volume_livro"));
        values.put("exemplar", res.getString("exemplar_livro"));
        values.put("telefone", res.getString("telefone_usuario"));
        
        if(values.get("cpf") == null) values.replace("cpf", values.get("matricula"));
        else values.replace("matricula", values.get("cpf"));
        String id = res.getString("id_emprestimo");

        boolean status = LocalDate.now().isBefore(LocalDate.parse(res.getString("data_devolucao")).plusDays(1));
        componente.setStatus(status);  

        componente.setEvento(()->{
            emprestimoManipulador(id, mainContainer, values);
        });
        
        box.getChildren().add(painel);
    }
    
    // cria o popup de um empréstimo
    private void emprestimoManipulador(String id, Pane mainContainer, Map values){
        try {
                FXMLLoader loaderPopup = new FXMLLoader();
                loaderPopup.setLocation(getClass().getResource("../View/Popup-emprestimo.fxml"));
                
                Pane popup = loaderPopup.load();
                
                mainContainer.getChildren().add(popup);
                
                PopupEmprestimoController popupController = loaderPopup.getController();
                popupController.setInfo(values.get("titulo").toString(),values.get("num_registro").toString(),
                        values.get("autor").toString(),values.get("matricula").toString(),values.get("cpf").toString(),
                        values.get("nome").toString(),values.get("data_inicio").toString(),values.get("data_devolucao").toString());
                
                popupController.setRenovarManipulador(()->{
                    renovarManipulador(popup, mainContainer, id);
                });
                
                popupController.setEncerrarManipulador(()->{
                    encerrarManipulador(popup, mainContainer, id, values);
                });
                
                popupController.setFecharManipulador(()->{
                    mainContainer.getChildren().remove(popup);
                });
            } catch (IOException ex) {
                Logger.getLogger(ProfessorListagemController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    // Encerra o empréstimo
    private void encerrarManipulador(Pane popup, Pane mainContainer, String id, Map values) {
        String[] columns = {
            "data_emprestimo", 
            "data_devolucao",
            "id_usuario",
            "num_registro_livro",
            "titulo_livro",
            "volume_livro",
            "exemplar_livro",
            "nome_usuario",
            "turma_usuario",
            "telefone_usuario"
        };
        String [] valuesSave = {
            values.get("data_inicio").toString(),
            LocalDate.now().toString(),
            values.get("id_usuario").toString(),
            values.get("num_registro").toString(),
            values.get("titulo").toString(),
            values.get("volume").toString(),
            values.get("exemplar").toString(),
            values.get("nome").toString(),
            values.get("turma").toString(),
            values.get("telefone").toString()
        };
        
        try {
            repositorio.save("emprestimos_encerrados", columns, valuesSave);
            repositorio.delete("emprestimo", String.format("id_emprestimo = '%s'", id));
            IPopupMsg controller = MsgFabrica.criaPopupMsg("PopupAcaoMsg");
            controller.setManipulador(()->{
                mainContainer.getChildren().remove(controller.getPopup());
            });
            mainContainer.getChildren().add(controller.getPopup());
            mainContainer.getChildren().remove(popup);
            buscar();
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoListagemController.class.getName()).log(Level.SEVERE, null, ex);
        }  
    }
    
    // Renova um empréstimo
    private void renovarManipulador(Pane popup, Pane mainContainer, String id) {
        String[] values = {LocalDate.now().plusDays(5).toString()};
        String[] columns = {"data_devolucao"};
        try {
            repositorio.update("emprestimo", columns, values, String.format("id_emprestimo = '%s'", id));
            IPopupMsg controller = MsgFabrica.criaPopupMsg("PopupAcaoMsg");
            controller.setManipulador(()->{
                mainContainer.getChildren().remove(controller.getPopup());
            });
            mainContainer.getChildren().add(controller.getPopup());
            mainContainer.getChildren().remove(popup);
            buscar();
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoListagemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    // Faz uma busca no banco de dados, de acordo com o filtro
    @FXML
    public void buscar(){
        ResultSet response;
        render_box_elements.getChildren().clear();
        String searchBar = pesquisarText.getText().toUpperCase();
        String consult = String.format( "UPPER(nome_usuario) like '%%%s%%' OR UPPER(titulo_livro) like '%%%s%%'"
                + "ORDER BY data_devolucao asc",searchBar, searchBar);
        
        try {
            if(atrasadosBtn.isSelected()){
                response = repositorio.get("emprestimos_atrasados", consult); 
            }else {
                  response = repositorio.get("emprestimo", consult);
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

    // Pesquisa acionada pela tecla ENTER
    @FXML
    protected void buscarTeclaPressionada(){
        pesquisarText.setOnKeyPressed(event->{
            if(event.getCode() == KeyCode.ENTER){
                buscar();
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        buscar();
    }

}
