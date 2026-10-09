package main.java.com.aguil.aguilonchas.controller;

import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import main.java.com.aguil.aguilonchas.dto.RegistroDTO;
import main.java.com.aguil.aguilonchas.dto.RolDTO;
import main.java.com.aguil.aguilonchas.exception.AppException;
import main.java.com.aguil.aguilonchas.exception.ValidacionException;
import main.java.com.aguil.aguilonchas.service.AuthService;
import main.java.com.aguil.aguilonchas.util.AlertaUtil;
import main.java.com.aguil.aguilonchas.util.SceneManager;
import main.java.com.aguil.aguilonchas.util.Vista;

/**
 *
 * @author AGUILON
 */
public class RegistroController {

    private final AuthService authService;
    private final SceneManager sceneManager;

    @FXML private TextField txtUsuario;
    @FXML private TextField txtCorreo;
    @FXML private PasswordField txtContrasena;
    @FXML private PasswordField txtConfirmacion;
    @FXML private ComboBox<RolDTO> cmbRol;

    public RegistroController(AuthService authService, SceneManager sceneManager) {
        this.authService = authService;
        this.sceneManager = sceneManager;
    }

    @FXML
    private void initialize() {
        try {
            cmbRol.setItems(FXCollections.observableArrayList(authService.listarRoles()));
        } catch (AppException e) {
            AlertaUtil.error(e.getMessage());
        }
    }

    @FXML
    private void registrar() {
        try {
            RolDTO rol = cmbRol.getValue();
            authService.registrar(new RegistroDTO(
                    txtUsuario.getText(), txtCorreo.getText(),
                    txtContrasena.getText(), txtConfirmacion.getText(),
                    rol == null ? 0 : rol.getId()));
            AlertaUtil.informacion("Usuario registrado correctamente. Ya puede iniciar sesión.");
            sceneManager.mostrar(Vista.LOGIN);
        } catch (ValidacionException e) {
            AlertaUtil.advertencia(e.getMessage());
        } catch (AppException e) {
            AlertaUtil.error(e.getMessage());
        }
    }

    @FXML
    private void volver() {
        sceneManager.mostrar(Vista.LOGIN);
    }
}
