package bookify;

import bookify.Controller.TelasController;
import java.io.IOException;
import java.net.URL;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

public class Treinando extends Application {
    
    private static Stage stage;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        this.stage = primaryStage;
        TelasController tela = new TelasController();
        tela.switchScreen(5);
        stage.setResizable(false);
        stage.show();

    }
  
    public void setStage(Scene cena){
        stage.setScene(cena);
    }
   
    public static void main(String[] args) {
        launch(args);
    }
    
}