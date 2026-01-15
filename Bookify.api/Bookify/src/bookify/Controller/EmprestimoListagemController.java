package bookify.Controller;

import bookify.Controller.Factory.EmprestimoComponentFactory;
import bookify.Controller.Factory.PopupEmprestimoFactory;
import bookify.Controller.PopupMensagem.FabricaPopupMsg;
import bookify.Exception.EmprestimoException;
import bookify.Interface.IFabricaPopupMsg;
import bookify.Interface.IPopupMsg;
import bookify.Service.EmprestimoService;
import bookify.dto.EmprestimoDTO;
import java.io.IOException;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class EmprestimoListagemController extends TelasController implements Initializable{
    
    private static final Logger LOGGER = Logger.getLogger(EmprestimoListagemController.class.getName());
    private static final int COMPONENTES_POR_LINHA = 2;
    
    private final EmprestimoService emprestimoService;
    private final EmprestimoComponentFactory componentFactory;
    private final PopupEmprestimoFactory popupFactory;
    private final IFabricaPopupMsg msgFabrica;
    
    @FXML
    private ToggleButton atrasadosBtn;
    
    @FXML
    private Pane mainContainer;
    
    @FXML
    private TextField pesquisarText;

    @FXML
    private VBox render_box_elements;
    
    public EmprestimoListagemController() {
        this.emprestimoService = new EmprestimoService();
        this.componentFactory = new EmprestimoComponentFactory();
        this.popupFactory = new PopupEmprestimoFactory();
        this.msgFabrica = new FabricaPopupMsg();
    }
    
    @FXML
    public void buscar(){
        render_box_elements.getChildren().clear();
        
        try {
            String filtro = pesquisarText.getText();
            boolean apenasAtrasados = atrasadosBtn.isSelected();
            
            List<EmprestimoDTO> emprestimos = emprestimoService.listarEmprestimos(filtro, apenasAtrasados);
            renderizarEmprestimos(emprestimos);
            
        } catch (EmprestimoException ex) {
            LOGGER.log(Level.SEVERE, "Erro ao buscar empréstimos", ex);
            mostrarErro("Erro ao carregar empréstimos");
        }
    }
    
    private void renderizarEmprestimos(List<EmprestimoDTO> emprestimos) {
        HBox linhaAtual = null;
        int contador = 0;
        
        for (EmprestimoDTO emprestimo : emprestimos) {
            if (contador % COMPONENTES_POR_LINHA == 0) {
                linhaAtual = new HBox();
                render_box_elements.getChildren().add(linhaAtual);
            }
            
            try {
                Pane componente = componentFactory.criarComponente(
                    emprestimo,
                    this::abrirPopupEmprestimo
                );
                linhaAtual.getChildren().add(componente);
            } catch (IOException ex) {
                LOGGER.log(Level.WARNING, "Erro ao criar componente de empréstimo", ex);
            }
            
            contador++;
        }
    }
    
    private void abrirPopupEmprestimo(EmprestimoDTO emprestimo) {
        try {
            Pane popup = popupFactory.criarPopup(
                emprestimo,
                () -> renovarEmprestimo(emprestimo),
                () -> encerrarEmprestimo(emprestimo),
                this::fecharPopup
            );
            
            mainContainer.getChildren().add(popup);
            
        } catch (IOException ex) {
            LOGGER.log(Level.SEVERE, "Erro ao abrir popup de empréstimo", ex);
            mostrarErro("Erro ao abrir detalhes do empréstimo");
        }
    }
    
    private void renovarEmprestimo(EmprestimoDTO emprestimo) {
        try {
            emprestimoService.renovarEmprestimo(emprestimo.getIdEmprestimo());
            mostrarSucesso();
            fecharPopup();
            buscar();
            
        } catch (EmprestimoException ex) {
            LOGGER.log(Level.WARNING, "Erro ao renovar empréstimo", ex);
            mostrarErro("Não foi possível renovar o empréstimo");
        }
    }
    
    private void encerrarEmprestimo(EmprestimoDTO emprestimo) {
        try {
            emprestimoService.encerrarEmprestimo(emprestimo.getIdEmprestimo(), emprestimo);
            mostrarSucesso();
            fecharPopup();
            buscar();
            
        } catch (EmprestimoException ex) {
            LOGGER.log(Level.WARNING, "Erro ao encerrar empréstimo", ex);
            mostrarErro("Não foi possível encerrar o empréstimo");
        }
    }
    
    private void fecharPopup() {
        mainContainer.getChildren().removeIf(node -> 
            node.getId() != null || node instanceof Pane
        );
    }
    
    private void mostrarSucesso() {
        IPopupMsg popup = msgFabrica.criaPopupMsg("PopupAcaoMsg");
        popup.setManipulador(() -> mainContainer.getChildren().remove(popup.getPopup()));
        mainContainer.getChildren().add(popup.getPopup());
    }
    
    private void mostrarErro(String mensagem) {
        LOGGER.warning(mensagem);
        mostrarSucesso();
    }
    
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
