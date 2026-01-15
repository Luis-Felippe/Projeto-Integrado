package bookify.Controller.Factory;

import bookify.Controller.PopupEmprestimoController;
import bookify.dto.EmprestimoDTO;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class PopupEmprestimoFactory {
    
    private static final String POPUP_FXML = "../View/Popup-emprestimo.fxml";
    
    public Pane criarPopup(EmprestimoDTO emprestimo, 
                           Runnable onRenovar,
                           Runnable onEncerrar, 
                           Runnable onFechar) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        loader.setLocation(getClass().getResource(POPUP_FXML));
        
        Pane popup = loader.load();
        
        PopupEmprestimoController controller = loader.getController();
        
        controller.setInfo(
            emprestimo.getTituloLivro(),
            emprestimo.getNumRegistroLivro(),
            emprestimo.getAutorLivro(),
            emprestimo.getMatricula(),
            emprestimo.getCpf(),
            emprestimo.getNomeUsuario(),
            emprestimo.getDataInicio().toString(),
            emprestimo.getDataDevolucao().toString()
        );
        
        controller.setRenovarManipulador(() -> onRenovar.run());
        controller.setEncerrarManipulador(() -> onEncerrar.run());
        controller.setFecharManipulador(() -> onFechar.run());
        
        return popup;
    }
}
