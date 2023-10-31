package bookify;

import java.io.IOException;
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
    private static Stage Menustage;
    private static Stage Livrosstage;
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
     
        System.out.println("Opa");
        primaryStage.setTitle("BOOKIFY");
        var resource = getClass().getResource("View/Livros-window2.fxml");
        Parent root = FXMLLoader.load(resource);
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
       
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}