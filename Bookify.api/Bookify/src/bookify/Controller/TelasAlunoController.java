
package bookify.Controller;

import bookify.Controller.PopupAcao.FabricaPopupAcao;
import bookify.Controller.PopupMensagem.FabricaPopupMsg;
import bookify.Interface.IFabricaPopupAcao;
import bookify.Interface.IFabricaPopupMsg;
import bookify.Interface.IPopupAcao;
import bookify.Interface.IPopupMsg;
import bookify.Models.BookifyDatabase;
import java.io.IOException;
import java.sql.SQLException;
import javafx.fxml.FXML;
import javafx.scene.layout.Pane;

public class TelasAlunoController extends TelasController {
    
    private BookifyDatabase repositorio = BookifyDatabase.getInstancia();
    private IFabricaPopupAcao popupAcaoFabrica = new FabricaPopupAcao();
    private IFabricaPopupMsg MsgFabrica = new FabricaPopupMsg();
    
    @FXML
    private Pane mainContainer;
    
    // troca a tela para a tela de cadastrar alunos
    @FXML
    protected void cadastrarAluno() throws IOException{
        tela.trocarTela("alunos/cadastro");
    }
    // torca para a tela que lista os alunos
    @FXML
    protected void listarAluno() throws IOException{
        tela.trocarTela("alunos/listagem");
    }
    // troca para a tela de edição de alunos
    @FXML
    protected void editarAluno(Object obj) throws  IOException{
        tela.trocarTela("alunos/edicao", obj);
    }
    
    @FXML
    protected void teste(){
        atualizarTurmas(mainContainer);
    }
    
    @FXML
    protected void atualizarTurmas(Pane mainContainer){
        IPopupAcao controller = popupAcaoFabrica.criaPopupAcao("PopupAtualizarTurma");
        Pane popup = controller.getFxml();
        mainContainer.getChildren().add(popup);
        controller.setCancelarManipulador(()->{
            cancelarManipulador(popup, mainContainer);
        });
        controller.setConfirmarManipulador(()->{
            confirmarManipulador(mainContainer, controller, popup);
        });
    }
    
    // Chama a função de excluir e exibe a mensagem de exclusão realizada.
    private void confirmarManipulador(Pane mainContainer, IPopupAcao controlador, Pane popup){
        try {
            repositorio.getFunction("atualizar_turma()");
            mainContainer.getChildren().remove(popup);
            IPopupMsg controller = MsgFabrica.criaPopupMsg("PopupAcaoMsg");
            controller.setManipulador(()->{
                mainContainer.getChildren().remove(controller.getPopup());
            });
            mainContainer.getChildren().add(controller.getPopup());
        } catch (SQLException ex){
            controlador.erro();
        }
    }
    
    // Fecha o popup
    private void cancelarManipulador(Pane popup, Pane mainContainer){
        mainContainer.getChildren().remove(popup);
    }
    
}
