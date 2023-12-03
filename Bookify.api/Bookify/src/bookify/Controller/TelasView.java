package bookify.Controller;

import bookify.Main;
import java.io.IOException;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

public class TelasView{
    FXMLLoader fxmlloader = new FXMLLoader();
    
    public void trocarTela(String tela) throws IOException{
        switch(tela){
            case "alunos/cadastro":
                fxmlloader.setLocation(getClass().getResource("../View/Alunos-cadastro-window.fxml"));
                break;
            case "alunos/menu":
                fxmlloader.setLocation(getClass().getResource("../View/Alunos-window.fxml"));
                break;
            case "professores/menu":
                fxmlloader.setLocation(getClass().getResource("../View/Professor-window.fxml"));
                break;
            case "professores/cadastro":
                fxmlloader.setLocation(getClass().getResource("../View/Professor-cadastro-window.fxml"));
                break;
            case "home":
                fxmlloader.setLocation(getClass().getResource("../View/Home-window.fxml"));
                break;
            case "login":
                fxmlloader.setLocation(getClass().getResource("../View/Login-window.fxml"));
                break;
            case "emprestimos/realizar":
                fxmlloader.setLocation(getClass().getResource("../View/Realizar-emprestimo.fxml"));
                break;
            case "livros/menu": 
                fxmlloader.setLocation(getClass().getResource("../View/Livros-window2.fxml"));
                break;
            case "livros/cadastro":
                fxmlloader.setLocation(getClass().getResource("../View/Livros-cadastro-window.fxml"));
                break;
            case "alunos/listagem":
                fxmlloader.setLocation(getClass().getResource("../View/Aluno-listagem-window.fxml"));
                break;
            case "professores/listagem":
                fxmlloader.setLocation(getClass().getResource("../View/Professor-listagem-window.fxml"));
                break;
            case "livros/listagem":
                fxmlloader.setLocation(getClass().getResource("../View/Livro-listagem-window.fxml"));
                break;
            case "emprestimos/listagem":
                fxmlloader.setLocation(getClass().getResource("../View/Emprestimo-listagem-window.fxml"));
                break;
            default:
                System.out.println("teste");
                break;
        }
        Main main = new Main();
        main.setCena(new Scene(fxmlloader.load()));
    }
    
    public void trocarTela(String tela, Object obj) throws IOException{
        Scene teste;
        switch (tela) {
            case "professores/edicao":
                fxmlloader.setLocation(getClass().getResource("../View/Professor-edicao-window.fxml"));
                teste = new Scene(fxmlloader.load());
                ProfessorEdicaoController professorController = fxmlloader.getController();
                professorController.setParametros(obj);
                break;
            case "alunos/edicao":
                fxmlloader.setLocation(getClass().getResource("../View/Aluno-edicao-window.fxml"));
                teste = new Scene(fxmlloader.load());
                AlunosEditController alunosController = fxmlloader.getController();
                alunosController.setParametros(obj);
                break;
            case "livros/edicao":
                fxmlloader.setLocation(getClass().getResource("../View/Livros-edicao-window.fxml"));
                teste = new Scene(fxmlloader.load());
                LivrosEditController livrosController = fxmlloader.getController();
                livrosController.setParametros(obj);
                break;
            default:
                throw new AssertionError();
        }
        Main main = new Main();
        main.setCena(teste);
    }
    
}