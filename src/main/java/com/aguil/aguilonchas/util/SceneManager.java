package main.java.com.aguil.aguilonchas.util;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Supplier;

/**
 *
 * @author AGUILON
 */
public class SceneManager {

    private final Stage stage;
    private final Map<Class<?>, Supplier<?>> fabricas = new HashMap<>();

    public SceneManager(Stage stage) {
        this.stage = stage;
    }

    public <T> void registrarControlador(Class<T> tipo, Supplier<T> fabrica) {
        fabricas.put(tipo, fabrica);
    }

    public void mostrar(Vista vista) {
        try {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(vista.getRuta()));
            loader.setControllerFactory(this::crearControlador);
            Parent raiz = loader.load();

            stage.setTitle(vista.getTitulo());
            stage.setScene(new Scene(raiz));
            stage.sizeToScene();
            stage.centerOnScreen();
            stage.show();
        } catch (IOException e) {
            throw new IllegalStateException("No se pudo cargar la vista " + vista.getRuta(), e);
        }
    }

    private Object crearControlador(Class<?> tipo) {
        Supplier<?> fabrica = fabricas.get(tipo);
        if (fabrica == null) {
            throw new IllegalStateException("Controlador no registrado: " + tipo.getName());
        }
        return fabrica.get();
    }
}
