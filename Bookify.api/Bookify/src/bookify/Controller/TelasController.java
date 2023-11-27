package bookify.Controller;

import bookify.Treinando;
import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class TelasController{
    FXMLLoader fxmlloader = new FXMLLoader();
    
    public void switchScreen(int i) throws IOException{
        
        
        switch(i){
            case 0:
                fxmlloader.setLocation(getClass().getResource("../View/Alunos-cadastro-window.fxml"));
                break;
            case 1:
                fxmlloader.setLocation(getClass().getResource("../View/Alunos-window.fxml"));
                break;
            case 2:
                fxmlloader.setLocation(getClass().getResource("../View/Professor-window.fxml"));
                break;
            case 3:
                fxmlloader.setLocation(getClass().getResource("../View/Professor-cadastro-window.fxml"));
                break;
            case 4:
                fxmlloader.setLocation(getClass().getResource("../View/Home-window.fxml"));
                break;
            case 5:;
                fxmlloader.setLocation(getClass().getResource("../View/Login-window.fxml"));
                break;
            case 6:
                fxmlloader.setLocation(getClass().getResource("../View/Realizar-emprestimo.fxml"));
                break;
            case 7: 
                fxmlloader.setLocation(getClass().getResource("../View/Livros-window2.fxml"));
                break;
            case 8:
                fxmlloader.setLocation(getClass().getResource("../View/Livros-cadastro-window.fxml"));
                break;
            case 9:
                fxmlloader.setLocation(getClass().getResource("../View/Aluno-listagem-window.fxml"));
                break;
            case 10:
                fxmlloader.setLocation(getClass().getResource("../View/Professor-listagem-window.fxml"));
                break;
            case 12:
                fxmlloader.setLocation(getClass().getResource("../View/Livro-listagem-window.fxml"));
                break;
            case 17:
                fxmlloader.setLocation(getClass().getResource("../View/Realizar-emprestimo.fxml"));
                break;
            default:
                System.out.println("teste");
                break;
        }
        Treinando main = new Treinando();
        main.setStage(new Scene(fxmlloader.load()));
    }
    
    public void switchScreen(int i, Object obj) throws IOException{
        Scene teste;
        switch (i) {
            case 13:
                fxmlloader.setLocation(getClass().getResource("../View/Professor-edicao-window.fxml"));
                teste = new Scene(fxmlloader.load());
                ProfessorEdicaoController professorController = fxmlloader.getController();
                professorController.setParams(obj);
                break;
            case 14:
                fxmlloader.setLocation(getClass().getResource("../View/Aluno-edicao-window.fxml"));
                teste = new Scene(fxmlloader.load());
                AlunosEditController alunosController = fxmlloader.getController();
                alunosController.setParams(obj);
                break;
            case 15:
                fxmlloader.setLocation(getClass().getResource("../View/Livros-edicao-window.fxml"));
                teste = new Scene(fxmlloader.load());
                LivrosEditController livrosController = fxmlloader.getController();
                livrosController.setParams(obj);
                break;
            default:
                throw new AssertionError();
        }
        Treinando main = new Treinando();
        main.setStage(teste);
    }
    
}