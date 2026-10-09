package main.java.com.aguil.aguilonchas;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import main.java.com.aguil.aguilonchas.config.ConnectionDb;
import main.java.com.aguil.aguilonchas.controller.InventarioController;
import main.java.com.aguil.aguilonchas.controller.LoginController;
import main.java.com.aguil.aguilonchas.controller.RegistroController;
import main.java.com.aguil.aguilonchas.repository.AspiranteRepository;
import main.java.com.aguil.aguilonchas.repository.AspiranteRepositoryJdbc;
import main.java.com.aguil.aguilonchas.repository.RolRepository;
import main.java.com.aguil.aguilonchas.repository.RolRepositoryJdbc;
import main.java.com.aguil.aguilonchas.repository.UsuarioRepository;
import main.java.com.aguil.aguilonchas.repository.UsuarioRepositoryJdbc;
import main.java.com.aguil.aguilonchas.service.AspiranteService;
import main.java.com.aguil.aguilonchas.service.AspiranteServiceImpl;
import main.java.com.aguil.aguilonchas.service.AuthService;
import main.java.com.aguil.aguilonchas.service.AuthServiceImpl;
import main.java.com.aguil.aguilonchas.util.AlertaUtil;
import main.java.com.aguil.aguilonchas.util.PasswordUtil;
import main.java.com.aguil.aguilonchas.util.SceneManager;
import main.java.com.aguil.aguilonchas.util.SesionUsuario;
import main.java.com.aguil.aguilonchas.util.Vista;

/**
 *
 * @author AGUILON
 */
public class App extends Application {

    @Override
    public void start(Stage stage) {
        Thread.setDefaultUncaughtExceptionHandler((hilo, error) ->
                Platform.runLater(() -> AlertaUtil.error("Ocurrió un error inesperado: " + error.getMessage())));
 
        ConnectionDb conexionBD = new ConnectionDb();
        PasswordUtil passwordUtil = new PasswordUtil();
        SesionUsuario sesion = new SesionUsuario();

        UsuarioRepository usuarioRepository = new UsuarioRepositoryJdbc(conexionBD);
        RolRepository rolRepository = new RolRepositoryJdbc(conexionBD);
        AspiranteRepository aspiranteRepository = new AspiranteRepositoryJdbc(conexionBD);

        AuthService authService = new AuthServiceImpl(usuarioRepository, rolRepository, passwordUtil);
        AspiranteService aspiranteService = new AspiranteServiceImpl(aspiranteRepository);

        SceneManager sceneManager = new SceneManager(stage);
        sceneManager.registrarControlador(LoginController.class,
                () -> new LoginController(authService, sceneManager, sesion));
        sceneManager.registrarControlador(RegistroController.class,
                () -> new RegistroController(authService, sceneManager));
        sceneManager.registrarControlador(InventarioController.class,
                () -> new InventarioController(aspiranteService, sceneManager, sesion));

        sceneManager.mostrar(Vista.LOGIN);
    }

    public static void main(String[] args) {
        launch(args);
    }
}
