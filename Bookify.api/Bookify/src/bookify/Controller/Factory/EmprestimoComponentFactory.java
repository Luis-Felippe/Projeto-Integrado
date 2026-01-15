package bookify.Controller.Factory;

import bookify.Controller.EmprestimoComponenteController;
import bookify.dto.EmprestimoDTO;
import java.io.IOException;
import java.util.function.Consumer;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class EmprestimoComponentFactory {
    
    private static final String COMPONENTE_FXML = "../View/Emprestimo-componente-window.fxml";
    
    public Pane criarComponente(EmprestimoDTO emprestimo, Consumer<EmprestimoDTO> onClickHandler) 
            throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(COMPONENTE_FXML));
        Pane painel = loader.load();
        
        EmprestimoComponenteController controller = loader.getController();
        
        controller.setTexto(
            emprestimo.getTituloLivro(),
            emprestimo.getNomeUsuario(),
            emprestimo.getIdentificadorUsuario(),
            emprestimo.getIdentificadorUsuario(),
            emprestimo.getDataInicio().toString(),
            emprestimo.getDataDevolucao().toString()
        );
        
        controller.setStatus(!emprestimo.isAtrasado());
        
        controller.setEvento(() -> onClickHandler.accept(emprestimo));
        
        return painel;
    }
}
