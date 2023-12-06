package bookify;

import bookify.Controller.TelasView;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {
    
    private static Stage stage;
    
    @Override
    public void start(Stage primaryStage) throws Exception {
        
        this.stage = primaryStage;
        TelasView tela = new TelasView();
        tela.trocarTela("login");
//        tela.trocarTela("home");
        stage.setResizable(false);
        stage.show();
    }
    public void setCena(Scene cena){
        stage.setScene(cena);
    }
    public static void main(String[] args) {
        launch(args);
    }
    
}