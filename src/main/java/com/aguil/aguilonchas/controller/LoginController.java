package main.java.com.aguil.aguilonchas.controller;

import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.aguil.aguilonchas.dto.LoginDTO;
import main.java.com.aguil.aguilonchas.dto.UsuarioDTO;
import main.java.com.aguil.aguilonchas.exception.AppException;
import main.java.com.aguil.aguilonchas.exception.ValidacionException;
import main.java.com.aguil.aguilonchas.service.AuthService;
import main.java.com.aguil.aguilonchas.util.AlertaUtil;
import main.java.com.aguil.aguilonchas.util.SceneManager;
import main.java.com.aguil.aguilonchas.util.SesionUsuario;
import main.java.com.aguil.aguilonchas.util.Vista;

/**
 *
 * @author AGUILON
 */
public class LoginController {

    private final AuthService authService;
    private final SceneManager sceneManager;
    private final SesionUsuario sesion;

    @FXML private TextField txtUsuario;
    @FXML private PasswordField txtContrasena;

    public LoginController(AuthService authService, SceneManager sceneManager, SesionUsuario sesion) {
        this.authService = authService;
        this.sceneManager = sceneManager;
        this.sesion = sesion;
    }

    @FXML
    private void iniciarSesion() {
        try {
            UsuarioDTO usuario = authService.iniciarSesion(
                    new LoginDTO(txtUsuario.getText(), txtContrasena.getText()));
            sesion.iniciar(usuario);
            sceneManager.mostrar(Vista.INVENTARIO);
        } catch (ValidacionException e) {
            AlertaUtil.advertencia(e.getMessage());
        } catch (AppException e) {
            AlertaUtil.error(e.getMessage());
        }
    }

    @FXML
    private void irARegistro() {
        sceneManager.mostrar(Vista.REGISTRO);
    }
}
