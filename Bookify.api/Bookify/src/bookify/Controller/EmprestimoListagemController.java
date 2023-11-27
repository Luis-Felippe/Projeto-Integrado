package bookify.Controller;

import bookify.model.dao.BookifyDatabase;
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
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.input.DataFormat;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class EmprestimoListagemController implements Initializable{

    TelasController tela = new TelasController();
    BookifyDatabase repository = new BookifyDatabase();
    
    @FXML
    private Pane mainContainer;

    @FXML
    private ImageView pesquisarBtn;

    @FXML
    private Button pesquisarButton;

    @FXML
    private TextField pesquisarText;

    @FXML
    private VBox render_box_elements;

    @FXML
    protected void alunoMenu() throws IOException{
        tela.switchScreen(1);
    }
     @FXML
    protected void professorMenu() throws IOException{
        tela.switchScreen(2);
    }
     @FXML
    protected void homeMenu() throws IOException{
        tela.switchScreen(4);
    }
    @FXML
    protected void livroMenu() throws IOException{
        tela.switchScreen(7);
    }
    
    @FXML
    private void addComponent(HBox box, ResultSet res) throws IOException, SQLException{
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource("../View/Emprestimo-componente-window.fxml"));
        Pane painel = loader.load();
        EmprestimoComponenteController componente = loader.getController();
        componente.setTexto(res.getString("titulo"), 
        res.getString("nome"),
        res.getString("matricula"), 
        res.getString("cpf"),
        res.getString("data_inicio"), 
        res.getString("data_devolucao"));
        

       
        Map<String, String> values = new HashMap<>();
        values.put("data_inicio",res.getString("data_inicio"));
        values.put("data_devolucao",res.getString("data_devolucao"));
        values.put("id_usuario",res.getString("id_usuario"));
        values.put("num_registro",res.getString("num_registro"));
        values.put("nome",res.getString("nome"));
        values.put("titulo",res.getString("titulo"));
        values.put("autor",res.getString("autor"));
        values.put("cpf",res.getString("cpf"));
        values.put("matricula",res.getString("matricula"));
        
        if(values.get("cpf") == null) values.replace("cpf", values.get("matricula"));
        else values.replace("matricula", values.get("cpf"));
        String id = res.getString("id_emprestimo");

        boolean status = LocalDate.now().isBefore(LocalDate.parse(res.getString("data_devolucao")));
        componente.setStatus(status);  

        componente.setEvent(()->{
            emprestimoHandler(id, mainContainer, values);
        });
        
        box.getChildren().add(painel);
    }
    
    private void emprestimoHandler(String id, Pane mainContainer, Map values){
        try {
                FXMLLoader loaderPopup = new FXMLLoader();
                loaderPopup.setLocation(getClass().getResource("../View/Popup-emprestimo.fxml"));
                Pane popup = loaderPopup.load();
                
                mainContainer.getChildren().add(popup);
                
                PopupEmprestimoController popupController = loaderPopup.getController();
                popupController.setInfo(values.get("titulo").toString(),values.get("num_registro").toString(),
                        values.get("autor").toString(),values.get("nome").toString(),values.get("matricula").toString(),
                        values.get("cpf").toString(),values.get("data_inicio").toString(),values.get("data_devolucao").toString());
                
                popupController.setRenovarHandler(()->{
                    renovarHandler(popup, mainContainer, id);
                });
                
                popupController.setEncerrarHandler(()->{
                    encerrarHandler(popup, mainContainer, id, values);
                });
            } catch (IOException ex) {
                Logger.getLogger(ProfessorListagemController.class.getName()).log(Level.SEVERE, null, ex);
            }
    }
    
    private void encerrarHandler(Pane popup, Pane mainContainer, String id, Map values) {
        String[] columns = {
            "data_emprestimo", 
            "data_devolucao",
            "id_usuario",
            "num_registro_livro"
        };
        String [] valuesSave = {
            values.get("data_inicio").toString(),
            LocalDate.now().toString(),
            values.get("id_usuario").toString(),
            values.get("num_registro").toString(),
        };
        
        try {
            repository.save("emprestimos_encerrados", columns, valuesSave);
            repository.delete("emprestimo", String.format("id_emprestimo = '%s'", id));
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoListagemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainContainer.getChildren().remove(popup);
        search();
    }
    
    private void renovarHandler(Pane popup, Pane mainContainer, String id) {
        String[] values = {LocalDate.now().plusDays(5).toString()};
        String[] columns = {"data_devolucao"};
        try {
            repository.update("emprestimo", columns, values, String.format("id_emprestimo = '%s'", id));
        } catch (SQLException ex) {
            Logger.getLogger(EmprestimoListagemController.class.getName()).log(Level.SEVERE, null, ex);
        }
        mainContainer.getChildren().remove(popup);
        search();
    }

    @FXML
    public void search(){
        render_box_elements.getChildren().clear();
        String searchBar = pesquisarText.getText().toUpperCase();
        String consult = String.format("JOIN emprestimo e ON e.num_registro_livro = l.num_registro\n" +
            "JOIN usuario u ON u.id_usuario = e.id_usuario "
                + "WHERE UPPER(u.nome) like '%%%s%%' OR UPPER(l.titulo) like '%%%s%%'"
                + "ORDER BY data_inicio asc",searchBar, searchBar);
        
        try {
        var response = repository.get("l.titulo, u.nome, u.matricula, u.cpf ,"
                + "e.data_inicio, e.data_devolucao, l.num_registro,"
                + "u.id_usuario, e.id_emprestimo, l.autor", "livro l", consult);
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

    @FXML
    protected void searchKeyListener(){
        pesquisarText.setOnKeyPressed(event->{
            if(event.getCode() == KeyCode.ENTER){
                search();
            }
        });
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        search();
    }

}
