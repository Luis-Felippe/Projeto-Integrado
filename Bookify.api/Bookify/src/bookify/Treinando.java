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
    private static Scene MenuScene;
    private static Scene AlunosMenuScene;
    private static Scene LivrosScene;
    private static Scene AlunoCadastroScene;
    private static Scene ProfessorMenuScene;    
    private static Scene ProfessorCadastroScene;
    
    
    
    @Override
    public void start(Stage primaryStage) throws Exception {
     
        
        this.stage = primaryStage;
        Parent alunoMenufxml = FXMLLoader.load(getClass().getResource("View/Alunos-window.fxml"));
        AlunosMenuScene = new Scene( alunoMenufxml);
        Parent alunoCadastrofxml = FXMLLoader.load(getClass().getResource("View/Alunos-cadastro-window.fxml"));
        AlunoCadastroScene = new Scene(alunoCadastrofxml);
        Parent professorMenufxml = FXMLLoader.load(getClass().getResource("View/Professor-window.fxml"));
        ProfessorMenuScene = new Scene( professorMenufxml);
        Parent professorCadastrofxml = FXMLLoader.load(getClass().getResource("View/Professor-cadastro-window.fxml"));
        ProfessorCadastroScene = new Scene(professorCadastrofxml);
        this.stage.setScene(AlunosMenuScene);
        stage.setResizable(false);
        //AlunoCadastroScene.setRoot(alunoMenufxml);
        stage.show();
        /*
        System.out.println("Opa");
        primaryStage.setTitle("BOOKIFY");
        var resource = getClass().getResource("View/Alunos-window.fxml");
        Parent root = FXMLLoader.load(resource); 
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.setResizable(true);
        primaryStage.show();
       */
    }
    
    public static void mudarTela(int i){
        switch(i){
            case 0:
                stage.setScene(AlunoCadastroScene);
                break;
            case 1:
                stage.setScene(AlunosMenuScene);
                break;
            case 2:
                stage.setScene(ProfessorMenuScene);
                break;
            case 3:
                stage.setScene(ProfessorCadastroScene);
                break;
            default:
                System.out.println("teste");
                break;
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
    
}