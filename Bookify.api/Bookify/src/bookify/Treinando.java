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
    private static Scene LoginScene;
    private static Scene RealizarEmprestimoScene;
    private static Scene LivroCadastroScene;
    private static Scene AlunoListagemScene;
    
    
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
        
        
        Parent menufxml = FXMLLoader.load(getClass().getResource("View/Home-window.fxml"));
        MenuScene = new Scene(menufxml);

        Parent emprestimoRealizarfxml = FXMLLoader.load(getClass().getResource("View/Realizar-emprestimo.fxml"));
        RealizarEmprestimoScene = new Scene(emprestimoRealizarfxml);
        
        Parent loginfxml = FXMLLoader.load(getClass().getResource("View/Login-window.fxml"));
        LoginScene = new Scene(loginfxml);
        
        Parent livrosfxml = FXMLLoader.load(getClass().getResource("View/Livros-window2.fxml"));
        LivrosScene = new Scene(livrosfxml);
        
        Parent livroCadastrofxml = FXMLLoader.load(getClass().getResource("View/Livros-cadastro-window.fxml"));
        LivroCadastroScene = new Scene(livroCadastrofxml);

        Parent alunoListagemfxml = FXMLLoader.load(getClass().getResource("View/Aluno-listagem-window.fxml"));
        AlunoListagemScene = new Scene(alunoListagemfxml);
        
        this.stage.setScene(LoginScene);
        stage.setResizable(false);
        stage.show();
       
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
            case 4:
                stage.setScene(MenuScene);
                break;
            case 5:;
                stage.setScene(LoginScene);
                break;
            case 6:
                stage.setScene(RealizarEmprestimoScene);
                break;
            case 7: 
                stage.setScene(LivrosScene);
                break;
            case 8:
                stage.setScene(LivroCadastroScene);
                break;
            case 9:
                stage.setScene(AlunoListagemScene);
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