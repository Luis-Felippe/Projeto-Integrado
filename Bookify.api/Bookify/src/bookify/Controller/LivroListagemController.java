package bookify.Controller;

import bookify.Controller.PopupMensagem.FabricaPopupMsg;
import bookify.Controller.PopupAcao.FabricaPopupAcao;
import bookify.Controller.Componentes.FabricaComponente;
import bookify.DAO.LivroDAO;
import bookify.Interface.IComponente;
import bookify.Interface.IFabricaComponente;
import bookify.Interface.IFabricaPopupAcao;
import bookify.Interface.IFabricaPopupMsg;
import bookify.Interface.IPopupAcao;
import bookify.Interface.IPopupMsg;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;

import bookify.Models.Livro;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

public class LivroListagemController extends TelasLivrosController implements Initializable {
    private LivroDAO livroDAO = new LivroDAO();

    private IFabricaPopupMsg MsgFabrica = new FabricaPopupMsg();
    private IFabricaComponente componenteFabrica = new FabricaComponente();
    private IFabricaPopupAcao popupAcaoFabrica = new FabricaPopupAcao();

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
        try {
            super.editarLivro(id,volume);
        } catch (IOException ex) {
            Logger.getLogger(ProfessorListagemController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    // Adiciona o componente que mostra as informações do aluno e seta seus atributos e funções
    private void adicionarComponente(HBox box, Livro livro, List<String> exemplares) throws IOException {
        IComponente componente = componenteFabrica.criaComponente("LivroComponente");

        Map<String, String> atributos = new HashMap<>();
        atributos.put("titulo", livro.getTitulo());
        atributos.put("num_registro", livro.getNumRegistro());
        atributos.put("autor", livro.getAutor());
        atributos.put("volume", livro.getVolume());
        atributos.put("data", livro.getDataLivro());
        atributos.put("observacao", livro.getObservacao());
        atributos.put("exemplar", String.valueOf(exemplares.size() - 1));

        componente.setTexto(atributos);

        componente.setEditarManipulador(() -> {
            editarLivroManipulador(livro.getNumRegistro(), livro.getVolume());
        });

        componente.setDeletarManipulador(() -> {
            deletarLivroManipulador(livro.getNumRegistro(), mainContainer, exemplares, livro.getVolume());
        });

        box.getChildren().add(componente.getFxml());
    }

    // abre o popup de exclusão de livro
    private void deletarLivroManipulador(String id, Pane mainContainer, List<String> lista, String volume){
        IPopupAcao controller = popupAcaoFabrica.criaPopupAcao("PopupLivro");
        Pane popup = controller.getFxml();
        mainContainer.getChildren().add(popup);
        controller.preencherExemplares(lista);

        controller.setCancelarManipulador(() -> mainContainer.getChildren().remove(popup));

        controller.setConfirmarManipulador(() -> {
            confirmarExclusao(id, popup, controller, volume);
        });
    }

    private void confirmarExclusao(String id, Pane popup, IPopupAcao controlador, String volume){
        try {
            String exemplarSelecionado = controlador.getExemplar();

            if (exemplarSelecionado == null || exemplarSelecionado.equals("NENHUM")){
                System.out.println("Selecione um exemplar");
                return;
            }

            livroDAO.deletar(id, volume, exemplarSelecionado);

            mainContainer.getChildren().remove(popup);
            exibirPopupSucesso("PopupExcluirMsg");
            buscar();

        } catch (SQLException | IOException ex) {
            controlador.erro();
        }
    }

    private void exibirPopupSucesso(String nomePopup) throws IOException {
        IPopupMsg controller = MsgFabrica.criaPopupMsg(nomePopup);
        controller.setManipulador(() -> mainContainer.getChildren().remove(controller.getPopup()));
        mainContainer.getChildren().add(controller.getPopup());
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

    @FXML
    protected void buscar() {
        render_box_elements.getChildren().clear();
        String termo = pesquisarText.getText().toUpperCase();

        try {
            List<Livro> registrosRaw = livroDAO.buscar(termo, livrosEmprestadosBtn.isSelected());

            if (registrosRaw.isEmpty()) return;

            class LivroAgrupado {
                Livro livro;
                List<String> exemplares = new ArrayList<>();
                LivroAgrupado(Livro l) { this.livro = l; this.exemplares.add("TODOS"); }
            }

            List<LivroAgrupado> listaParaRenderizar = new ArrayList<>();
            LivroAgrupado atual = null;
            String chaveAnterior = "";

            for (Livro reg : registrosRaw) {
                String chaveAtual = reg.getNumRegistro() + "-" + reg.getVolume();

                if (!chaveAtual.equals(chaveAnterior)) {
                    atual = new LivroAgrupado(reg);
                    listaParaRenderizar.add(atual);
                    chaveAnterior = chaveAtual;
                }
                atual.exemplares.add(reg.getExemplar());
            }

            HBox linha = null;
            int itensNaLinha = 0;

            for (LivroAgrupado item : listaParaRenderizar) {
                if (itensNaLinha == 0) {
                    linha = new HBox();

                    render_box_elements.getChildren().add(linha);
                }

                adicionarComponente(linha, item.livro, item.exemplares);

                itensNaLinha++;
                if (itensNaLinha == 2) {
                    itensNaLinha = 0;
                }
            }

        } catch (SQLException | IOException ex) {
            ex.printStackTrace();
        }
    }
}
