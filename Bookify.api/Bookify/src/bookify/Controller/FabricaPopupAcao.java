package bookify.Controller;

import bookify.Interface.IFabricaPopupAcao;
import bookify.Interface.IPopupAcao;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class FabricaPopupAcao implements IFabricaPopupAcao{

    public IPopupAcao criaPopupAcao(String tipo) {
        IPopupAcao controller = null;
        
        try{
            FXMLLoader loader = new FXMLLoader();
            Pane popup;
            
            switch (tipo) {
                case "PopupLivro":
                    loader.setLocation(getClass().getResource("../View/Popup-livro.fxml"));
                    popup = loader.load();
                    controller = loader.getController();
                    controller.setFxml(popup);
                    break;
                case "PopupAluno":
                    loader.setLocation(getClass().getResource("../View/Popup-aluno.fxml"));
                    popup = loader.load();
                    controller = loader.getController();
                    controller.setFxml(popup);
                    break;
                case "PopupProfessor":
                    loader.setLocation(getClass().getResource("../View/Popup.fxml"));
                    popup = loader.load();
                    controller = loader.getController();
                    controller.setFxml(popup);
                    break;
                default:
                    throw new AssertionError();
            }
            
        } catch (IOException ex) {
            Logger.getLogger(FabricaPopupAcao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return controller; 
    }
    
}
