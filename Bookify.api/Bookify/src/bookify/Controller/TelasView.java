package bookify.Controller;

import bookify.Main;
import java.io.IOException;
import java.util.Arrays;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import bookify.Factory.ScreenFactory;

import java.util.HashMap;
import java.util.Map;

public class TelasView {
    
    private static TelasView instance;
    private final Map<String, String> telas = new HashMap<>();

    private TelasView() {
        // Mapeamento nome lógico → nome do arquivo FXML
        telas.put("alunos/cadastro", "Alunos-cadastro-window.fxml");
        telas.put("alunos/menu", "Alunos-window.fxml");
        telas.put("professores/menu", "Professor-window.fxml");
        telas.put("professores/cadastro", "Professor-cadastro-window.fxml");
        telas.put("home", "Home-window.fxml");
        telas.put("login", "Login-window.fxml");
        telas.put("emprestimos/realizar", "Realizar-emprestimo.fxml");
        telas.put("livros/menu", "Livros-window2.fxml");
        telas.put("livros/cadastro", "Livros-cadastro-window.fxml");
        telas.put("alunos/listagem", "Aluno-listagem-window.fxml");
        telas.put("professores/listagem", "Professor-listagem-window.fxml");
        telas.put("livros/listagem", "Livro-listagem-window.fxml");
        telas.put("emprestimos/listagem", "Emprestimo-listagem-window.fxml");
        telas.put("professores/edicao", "Professor-edicao-window.fxml");
        telas.put("alunos/edicao", "Aluno-edicao-window.fxml");
        telas.put("livros/edicao", "Livros-edicao-window.fxml");
    }
    
    public static TelasView getInstance() {
        if (instance == null) {
            instance = new TelasView();
        }
        return instance;
    }

    public void trocarTela(String nomeTela) throws IOException {
        String fxml = telas.get(nomeTela);
        if (fxml == null) throw new IllegalArgumentException("Tela não encontrada: " + nomeTela);
        Scene cena = ScreenFactory.criarCena(fxml);
        new Main().setCena(cena);
    }

    public void trocarTela(String nomeTela, Object parametro) throws IOException {
        String fxml = telas.get(nomeTela);
        if (fxml == null) throw new IllegalArgumentException("Tela não encontrada: " + nomeTela);

        Scene cena = ScreenFactory.criarCena(fxml, controller -> {
            try {
                controller.getClass().getMethod("setParametros", Object.class).invoke(controller, parametro);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao injetar parâmetro na tela: " + nomeTela, e);
            }
        });
        new Main().setCena(cena);
    }

    public void trocarTela(String nomeTela, Object... parametros) throws IOException {
        String fxml = telas.get(nomeTela);
        if (fxml == null) throw new IllegalArgumentException("Tela não encontrada: " + nomeTela);

        Scene cena = ScreenFactory.criarCena(fxml, controller -> {
            try {
                Class<?>[] tipos = Arrays.stream(parametros).map(Object::getClass).toArray(Class[]::new);
                controller.getClass().getMethod("setParametros", tipos).invoke(controller, parametros);
            } catch (Exception e) {
                throw new RuntimeException("Erro ao injetar parâmetros na tela: " + nomeTela, e);
            }
        });
        new Main().setCena(cena);
    }
}