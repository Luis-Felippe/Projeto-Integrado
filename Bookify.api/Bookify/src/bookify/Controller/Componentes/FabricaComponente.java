package bookify.Controller.Componentes;

import bookify.Interface.IComponente;
import bookify.Interface.IFabricaComponente;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Pane;

public class FabricaComponente implements IFabricaComponente{

    public IComponente criaComponente(String tipo) {
        IComponente componente = null;
        try {
            FXMLLoader loader = new FXMLLoader();
            Pane painel;
            switch (tipo) {
                case "AlunoComponente":
                    loader.setLocation(getClass().getResource("../../View/Componentes/Aluno-componente-window.fxml"));
                    painel = loader.load();
                    componente = loader.getController();
                    componente.setFxml(painel);
                    break;
                case "ProfessorComponente":
                    loader.setLocation(getClass().getResource("../../View/Componentes/Professor-componente-window.fxml"));
                    painel = loader.load();
                    componente = loader.getController();
                    componente.setFxml(painel);
                    break;
                case "LivroComponente":
                    loader.setLocation(getClass().getResource("../../View/Componentes/Livro-componente-window.fxml"));
                    painel = loader.load();
                    componente = loader.getController();
                    componente.setFxml(painel);
                    break;
                default:
                    return null;
            }
        } catch (IOException ex) {
            Logger.getLogger(FabricaComponente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return componente;
    }
    
}
