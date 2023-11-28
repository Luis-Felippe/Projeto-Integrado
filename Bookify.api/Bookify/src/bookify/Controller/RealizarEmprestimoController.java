package bookify.Controller;

import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import bookify.model.dao.BookifyDatabase;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.ComboBox;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class RealizarEmprestimoController {

    private String currentUser = "";
    
    private String currentLiv = "";
    
    private BookifyDatabase repository = new BookifyDatabase();
    
    private TelasController tela = new TelasController();
    
    @FXML
    private Pane mainContainer;
    

    
    @FXML
    private DatePicker LivDateDevolucao;

    @FXML
    private DatePicker LivDateInicio;
    
    @FXML
    private ComboBox<String> menuExemplares;
    
    @FXML
    private Text error;
    
    @FXML
    private TextField LivTextAutor;

    @FXML
    private TextField LivTextCod;

    @FXML
    private TextField LivTextEditora;

    @FXML
    private TextField LivTextExemplar;

    @FXML
    private TextField LivTextMatricula;

    @FXML
    private TextField LivTextNome;

    @FXML
    private TextField LivTextObservacao;

    @FXML
    private TextField LivTextTelefone;

    @FXML
    private TextField LivTextTitulo;
    
    private Map<String, String> currentId;

    @FXML
    void alunoMenu(ActionEvent event) throws IOException {
        tela.switchScreen(1);
    }

    @FXML
    void homeMenu(MouseEvent event) throws IOException {
        tela.switchScreen(4);
    }

    @FXML
    void livroMenu(ActionEvent event) throws IOException {
        tela.switchScreen(7);
    }

    @FXML
    void professorMenu(ActionEvent event) throws IOException {
        tela.switchScreen(2);
    }
    
    @FXML
    protected void emprestimoMenu() throws IOException{
        tela.switchScreen(16);
    }
    
    @FXML
    protected void register(){
       try{
            if(!currentLiv.isEmpty() && !currentUser.isEmpty()){
                String currentLivExe = menuExemplares.getSelectionModel().getSelectedItem();
                String currentLivId = currentId.get(currentLivExe);
                var result = repository.get("emprestimo",String.format("num_registro_livro = '%s' OR id_usuario = '%s'", currentLivId, currentUser));
                if(!result.next()){
                    String[] values = {currentLivId, currentUser, 
                        LivDateInicio.getEditor().getText(), LivDateDevolucao.getEditor().getText()
                    };
                    String [] columns = {"num_registro_livro","id_usuario",
                        "data_inicio","data_devolucao"
                    };
                    repository.save("emprestimo", columns, values);
                    loadInformation(null, null);
                    LivTextCod.setText("");
                    LivTextMatricula.setText("");
                    repository.update("livro", "disponibilidade", "false", currentLivId);
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("../View/Popup-acao-confirmar.fxml"));
                    Pane popupConfirm = loader.load();
                    PopupAcaoMsgController controller = loader.getController();
                    controller.setHandler(()->{
                        mainContainer.getChildren().remove(popupConfirm);
                    });
                    mainContainer.getChildren().add(popupConfirm);
                }else{
                    error.setText("Não foi possivel realizar o emprestimo,"
                            + " verifique se o livro não pertence\n a outro emprestimo ou se "
                            + "o usuário já possui empréstimo vinculado");
                }
            }else{
                error.setText("Preencha todos os campos!");
            }
                
        }catch(SQLException ex){
            error.setText(ex.getMessage());
        } catch (IOException ex) {
            Logger.getLogger(RealizarEmprestimoController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    @FXML
    protected void teste(ActionEvent e){
        System.out.println(menuExemplares.getSelectionModel().getSelectedItem());
    }
    
    private void loadInformation(ResultSet resLiv, ResultSet resUser) throws SQLException{
        if(resLiv != null){
            resLiv.next();
            LivTextTitulo.setText(resLiv.getString("titulo"));
            LivTextAutor.setText(resLiv.getString("autor"));
            LivTextEditora.setText(resLiv.getString("editora"));
            LivTextObservacao.setText(resLiv.getString("observacao"));
            LivTextExemplar.setText(resLiv.getString("exemplar"));
            currentLiv = resLiv.getString("num_registro");
            
            ArrayList<String> array = new ArrayList<String>();
            currentId = new HashMap<>();
            do{
                array.add(resLiv.getString("exemplar"));
                currentId.put(resLiv.getString("exemplar"), resLiv.getString("id_livro"));
            }while(resLiv.next());
            ObservableList<String> list = FXCollections.observableArrayList(array);
            menuExemplares.setItems(list);
        }else{
            LivTextTitulo.setText("");
            LivTextAutor.setText("");
            LivTextEditora.setText("");
            LivTextObservacao.setText("");
            LivTextExemplar.setText("");
            currentLiv = "";
        }
        if(resUser != null){
            resUser.next();
            LivTextNome.setText(resUser.getString("nome"));
            LivTextTelefone.setText(resUser.getString("telefone"));
            LocalDate localdate = LocalDate.now();  
            LivDateInicio.setValue(localdate);
            LivDateDevolucao.setValue(localdate.plusDays(5));
            currentUser = resUser.getString("id_usuario");
        }else{
            LivTextNome.setText("");
            LivTextTelefone.setText("");
            LivDateInicio.setValue(null);
            LivDateDevolucao.setValue(null);
            currentUser = "";
        }
    }
    
    @FXML
    protected void searchKeyListener(){
        LivTextCod.setOnKeyPressed(event->{
            if(event.getCode() == KeyCode.ENTER){
                search();
            }
        });
        LivTextMatricula.setOnKeyPressed(event->{
            if(event.getCode() == KeyCode.ENTER){
                search();
            }
        });
    }
    
    
    private void search(){
        ResultSet resultLiv = null, resultUser = null;
        String searchLivro = LivTextCod.getText().toUpperCase();
        String searchUsuario = LivTextMatricula.getText().toUpperCase();
        try {
            if(!searchLivro.isEmpty()){
                    resultLiv = repository.get("livro",
                            String.format("num_registro = '%s' AND disponibilidade = 'true' ORDER BY exemplar asc",
                                    searchLivro));
            }
            if(!searchUsuario.isEmpty()){
                resultUser = repository.get("usuario", 
                        String.format("cpf = '%s' or matricula = '%s'",
                        searchUsuario, searchUsuario));
            }
            loadInformation(resultLiv, resultUser);
            error.setText("");
        } catch (SQLException ex) {
                error.setText("Verifique se as informações Cód.Livro e CPF/MATRICULA estão corretas");
        }
    }
}
