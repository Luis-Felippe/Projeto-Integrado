package bookify.Controller.PopupMensagem;

import bookify.Interface.IFabricaPopupMsg;
import bookify.Interface.IPopupMsg;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class FabricaPopupMsg implements IFabricaPopupMsg{

    public IPopupMsg criaPopupMsg(String tipo) {
        IPopupMsg controller = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            Pane popup;
            switch (tipo) {
                case "PopupCadastrarMsg":
                    loader.setLocation(getClass().getResource("../../View/PopupMensagem/Popup-cadastrar-confirmar.fxml"));
                    popup = loader.load();
                    controller = loader.getController();
                    controller.setPopup(popup);        
                    break;
                case "PopupAcaoMsg":
                    loader.setLocation(getClass().getResource("../../View/PopupMensagem/Popup-acao-confirmar.fxml"));
                    popup = loader.load();
                    controller = loader.getController();
                    controller.setPopup(popup);
                    break;
                case "PopupExcluirMsg":
                    loader.setLocation(getClass().getResource("../../View/PopupMensagem/Popup-excluir-confirmar.fxml"));
                    popup = loader.load();
                    controller = loader.getController();
                    controller.setPopup(popup);
                    break;
                default:
                    return null;
            }
        } catch (IOException ex) {
            Logger.getLogger(FabricaPopupMsg.class.getName()).log(Level.SEVERE, null, ex);
        }
        return controller;
    }
    
}
