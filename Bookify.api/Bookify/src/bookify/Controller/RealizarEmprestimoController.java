package bookify.Controller;

import bookify.Controller.PopupMensagem.FabricaPopupMsg;
import bookify.Interface.IFabricaPopupMsg;
import bookify.Interface.IPopupMsg;
import java.io.IOException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import bookify.Models.BookifyDatabase;
import java.sql.SQLException;
import java.sql.ResultSet;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class RealizarEmprestimoController extends TelasController{

    private String currentUser = "";
    
    private String currentLiv = "";
    
    private BookifyDatabase repositorio =  BookifyDatabase.getInstancia();
    
    private IFabricaPopupMsg MsgFabrica = new FabricaPopupMsg();
    
    @FXML
    private Pane mainContainer;
    
    @FXML
    private DatePicker LivDateDevolucao;

    @FXML
    private DatePicker LivDateInicio;
    
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
    
    // cria e adiciona um empréstimo feito ao banco de dados
    @FXML
    protected void emprestar(){
       try{
            if(!currentLiv.isEmpty() && !currentUser.isEmpty()){
                var result = repositorio.get("emprestimo",String.format("num_registro_livro = '%s' OR id_usuario = '%s'", currentLiv, currentUser));
                if(!result.next()){
                    String[] values = {currentLiv, currentUser, 
                        LivDateInicio.getEditor().getText(), LivDateDevolucao.getEditor().getText()
                    };
                    String [] columns = {"num_registro_livro","id_usuario",
                        "data_inicio","data_devolucao"
                    };
                    repositorio.save("emprestimo", columns, values);
                    carregarInformacao(null, null);
                    LivTextCod.setText("");
                    LivTextMatricula.setText("");
                    IPopupMsg controller = MsgFabrica.criaPopupMsg("PopupAcaoMsg");
                    controller.setManipulador(()->{
                        mainContainer.getChildren().remove(controller.getPopup());
                    });
                    mainContainer.getChildren().add(controller.getPopup());
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
        }
    }
    
    // Pega as informações do banco e mostra nos campos de texto 
    private void carregarInformacao(ResultSet resLiv, ResultSet resUser) throws SQLException{
        if(resLiv != null){
            resLiv.next();
            LivTextTitulo.setText(resLiv.getString("titulo"));
            LivTextAutor.setText(resLiv.getString("autor"));
            LivTextEditora.setText(resLiv.getString("editora"));
            LivTextObservacao.setText(resLiv.getString("observacao"));
            LivTextExemplar.setText(resLiv.getString("exemplar"));
            currentLiv = resLiv.getString("num_registro");
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
    
    // aciona a função de busca após teclar ENTER
    @FXML
    protected void buscarTeclaPressionada(){
        LivTextCod.setOnKeyPressed(event->{
            if(event.getCode() == KeyCode.ENTER){
                buscar();
            }
        });
        LivTextMatricula.setOnKeyPressed(event->{
            if(event.getCode() == KeyCode.ENTER){
                buscar();
            }
        });
    }
    
    // chama a função de buscar com os filtros definidos
    private void buscar(){
        ResultSet resultLiv = null, resultUser = null;
        String searchLivro = LivTextCod.getText().toUpperCase();
        String searchUsuario = LivTextMatricula.getText().toUpperCase();
        try {
            if(!searchLivro.isEmpty()){
                resultLiv = repositorio.get("livro",
                    String.format("num_registro = '%s'",
                    searchLivro));
            }
            if(!searchUsuario.isEmpty()){
                resultUser = repositorio.get("usuario", 
                    String.format("cpf = '%s' or matricula = '%s'",
                    searchUsuario, searchUsuario));
            }
            carregarInformacao(resultLiv, resultUser);
            error.setText("");
        } catch (SQLException ex) {
            error.setText("Verifique se as informações Cód.Livro e CPF/MATRICULA estão corretas");
        }
    }
}
