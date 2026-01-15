/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package bookify.Factory;

import java.io.IOException;
import java.util.function.Consumer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;

/**
 *
 * @author luisfelippe
 */
public class ScreenFactory {

    private static final String BASE_PATH = "../View/";

    public static Scene criarCena(String caminhoFXML) throws IOException {
        FXMLLoader loader = new FXMLLoader(ScreenFactory.class.getResource(BASE_PATH + caminhoFXML));
        return new Scene(loader.load());
    }

    public static Scene criarCena(String caminhoFXML, Consumer<Object> configurador) throws IOException {
        FXMLLoader loader = new FXMLLoader(ScreenFactory.class.getResource(BASE_PATH + caminhoFXML));
        Scene cena = new Scene(loader.load());
        Object controller = loader.getController();
        configurador.accept(controller);
        return cena;
    }
}
