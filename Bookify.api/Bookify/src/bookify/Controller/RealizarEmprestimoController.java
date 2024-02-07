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
import javafx.scene.control.ChoiceBox;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

public class RealizarEmprestimoController extends TelasController{

    private String currentUser = "";
    
    private String currentLiv = "";    
    private String currentLivSelected = "";

    
    private BookifyDatabase repositorio =  BookifyDatabase.getInstancia();
    
    private IFabricaPopupMsg MsgFabrica = new FabricaPopupMsg();
    
    @FXML
    private Pane mainContainer;
    
    @FXML
    private Text error_livro;
    
    @FXML
    private DatePicker LivDateDevolucao;

    @FXML
    private DatePicker LivDateInicio;
    
    @FXML
    private Text error_usuario;
    
    @FXML
    private Text error;
    
    @FXML
    private TextField LivTextAutor;

    @FXML
    private TextField LivTextCod;

    @FXML
    private ChoiceBox<String> volume;
    
    @FXML
    private ChoiceBox<String> exemplar;

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
    
    @FXML
    private TextField LivTextTurma;
    
    // cria e adiciona um empréstimo feito ao banco de dados
    @FXML
    protected void emprestar(){
       try{
            if(!currentLiv.isEmpty() && !currentUser.isEmpty() && !volume.getValue().equals("Selecione o volume") && !exemplar.getValue().equals("Selecione o exemplar")){
                var result = repositorio.get("emprestimo",String.format("id_usuario = '%s'", currentUser));
                if(!result.next()){
                    String[] values = {currentLiv, currentUser, 
                        LivDateInicio.getEditor().getText(), LivDateDevolucao.getEditor().getText(), volume.getValue(), exemplar.getValue()
                    };
                    String [] columns = {"num_registro_livro","id_usuario",
                        "data_inicio","data_devolucao", "volume_livro", "exemplar_livro"
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
                            + " verifique se o usuário já possui empréstimo vinculado");
                }
            }else{
                error.setText("Preencha todos os campos!");
            }        
        }catch(SQLException ex){
            error.setText(ex.getMessage());
        }
    }
    
    private void limparInformacoes(boolean livro, boolean usuario){
        if(livro){
            LivTextTitulo.setText("");
            LivTextAutor.setText("");
            volume.getItems().clear();
            LivTextObservacao.setText("");
            exemplar.getItems().clear();
            currentLiv = "";
            currentLivSelected = "";
        }
        if(usuario){
            LivTextNome.setText("");
            LivTextTelefone.setText("");
            LivTextTurma.setText("");
            LivDateInicio.setValue(null);
            LivDateDevolucao.setValue(null);
            currentUser = "";
        }
    }
    
    // Pega as informações do banco e mostra nos campos de texto 
    private void carregarInformacao(ResultSet resLiv, ResultSet resUser) throws SQLException{
        if(resLiv != null){
            if(resLiv.next()){
                if(!currentLivSelected.equals(resLiv.getString("num_registro"))){
                    volume.getItems().clear();
                    exemplar.getItems().clear();
                    error_livro.setText("");
                    LivTextTitulo.setText(resLiv.getString("titulo"));
                    LivTextAutor.setText(resLiv.getString("autor"));
                    volume.setValue("Selecione o volume");
                    LivTextObservacao.setText(resLiv.getString("observacao"));
                    exemplar.setValue("Selecione o exemplar");
                    currentLiv = resLiv.getString("num_registro");
                    do{
                        volume.getItems().add(resLiv.getString("volume"));
                        exemplar.getItems().add(resLiv.getString("exemplar"));
                    } while(resLiv.next());
                    currentLivSelected = currentLiv;
                }
                
            }else{
                error_livro.setText("O livro pesquisado não possui mais exemplares disponives");
                limparInformacoes(true, false);
            }
        }else{
            limparInformacoes(true, false);
            error_livro.setText("");
        }
        if(resUser != null){
            if(resUser.next()){
                error_usuario.setText("");
                LivTextNome.setText(resUser.getString("nome"));
                LivTextTelefone.setText(resUser.getString("telefone"));
                LocalDate localdate = LocalDate.now();  
                if(resUser.getString("turma") == null){
                    LivTextTurma.setText("PROFESSOR!");
                }
                else{
                    LivTextTurma.setText(resUser.getString("turma"));
                }
                LivDateInicio.setValue(localdate);
                LivDateDevolucao.setValue(localdate.plusDays(5));
                currentUser = resUser.getString("id_usuario");    
            }else{
                error_usuario.setText("Usuário não encontrado!");
                limparInformacoes(false, true);
            }
            
        }else{
            limparInformacoes(false, true);
            error_usuario.setText("");
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
                    String.format("num_registro = '%s' and disponibilidade = 'true'",
                    searchLivro));
            }
            if(!searchUsuario.isEmpty()){
                resultUser = repositorio.get("usuario", 
                    String.format("cpf = '%s' or matricula = '%s'",
                    searchUsuario, searchUsuario));
            }
            carregarInformacao(resultLiv, resultUser);
//            error.setText("");
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            error.setText("Verifique se as informações Cód.Livro e CPF/MATRICULA estão corretas");
        }
    }
}
