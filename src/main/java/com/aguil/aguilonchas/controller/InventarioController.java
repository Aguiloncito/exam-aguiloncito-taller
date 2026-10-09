package main.java.com.aguil.aguilonchas.controller;

import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import main.java.com.aguil.aguilonchas.dto.AspiranteDTO;
import main.java.com.aguil.aguilonchas.exception.AppException;
import main.java.com.aguil.aguilonchas.exception.ValidacionException;
import main.java.com.aguil.aguilonchas.service.AspiranteService;
import main.java.com.aguil.aguilonchas.util.AlertaUtil;
import main.java.com.aguil.aguilonchas.util.SceneManager;
import main.java.com.aguil.aguilonchas.util.SesionUsuario;
import main.java.com.aguil.aguilonchas.util.Vista;

/**
 *
 * @author AGUILON
 */
public class InventarioController {

    private final AspiranteService aspiranteService;
    private final SceneManager sceneManager;
    private final SesionUsuario sesion;
    private final ObservableList<AspiranteDTO> aspirantes = FXCollections.observableArrayList();

    @FXML private Label lblUsuario;
    @FXML private TableView<AspiranteDTO> tblAspirantes;
    @FXML private TableColumn<AspiranteDTO, Number> colId;
    @FXML private TableColumn<AspiranteDTO, String> colNombre;
    @FXML private TableColumn<AspiranteDTO, String> colApellido;
    @FXML private TableColumn<AspiranteDTO, String> colCorreo;
    @FXML private TableColumn<AspiranteDTO, String> colTelefono;
    @FXML private TableColumn<AspiranteDTO, String> colDireccion;
    @FXML private TableColumn<AspiranteDTO, Number> colEdad;
    @FXML private TextField txtNombre;
    @FXML private TextField txtApellido;
    @FXML private TextField txtCorreo;
    @FXML private TextField txtTelefono;
    @FXML private TextField txtDireccion;
    @FXML private TextField txtEdad;

    public InventarioController(AspiranteService aspiranteService, SceneManager sceneManager,
                                SesionUsuario sesion) {
        this.aspiranteService = aspiranteService;
        this.sceneManager = sceneManager;
        this.sesion = sesion;
    }

    @FXML
    private void initialize() {
        lblUsuario.setText("Usuario: " + sesion.getUsuario().getNombreUsuario()
                + " (" + sesion.getUsuario().getRol()+ ")");

        colId.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getId()));
        colNombre.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getNombre()));
        colApellido.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getApellido()));
        colCorreo.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getCorreo()));
        colTelefono.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getTelefono()));
        colDireccion.setCellValueFactory(c -> new SimpleStringProperty(c.getValue().getDireccion()));
        colEdad.setCellValueFactory(c -> new SimpleIntegerProperty(c.getValue().getEdad()));

        tblAspirantes.setItems(aspirantes);
        tblAspirantes.getSelectionModel().selectedItemProperty()
                .addListener((obs, anterior, seleccionado) -> cargarEnFormulario(seleccionado));

        refrescarTabla();
    }

    @FXML
    private void agregar() {
        ejecutar(() -> {
            aspiranteService.agregar(leerFormulario(null));
            AlertaUtil.informacion("Aspirante agregado correctamente.");
        });
    }

    @FXML
    private void actualizar() {
        AspiranteDTO seleccionado = tblAspirantes.getSelectionModel().getSelectedItem();
        ejecutar(() -> {
            if (seleccionado == null) {
                throw new ValidacionException("Seleccione un aspirante de la tabla para actualizar.");
            }
            aspiranteService.actualizar(leerFormulario(seleccionado.getId()));
            AlertaUtil.informacion("Aspirante actualizado correctamente.");
        });
    }

    @FXML
    private void eliminar() {
        AspiranteDTO seleccionado = tblAspirantes.getSelectionModel().getSelectedItem();
        ejecutar(() -> {
            if (seleccionado == null) {
                throw new ValidacionException("Seleccione un aspirante de la tabla para eliminar.");
            }
            if (AlertaUtil.confirmar("¿Eliminar a " + seleccionado.getNombre()+ " "
                    + seleccionado.getApellido()+ "?")) {
                aspiranteService.eliminar(seleccionado.getId());
            }
        });
    }

    @FXML
    private void limpiar() {
        tblAspirantes.getSelectionModel().clearSelection();
        limpiarFormulario();
    }

    @FXML
    private void cerrarSesion() {
        sesion.cerrar();
        sceneManager.mostrar(Vista.LOGIN);
    }

    /** Ejecuta una acción, muestra los errores como Alert y refresca la tabla al terminar. */
    private void ejecutar(Runnable accion) {
        try {
            accion.run();
            limpiar();
            refrescarTabla();
        } catch (ValidacionException e) {
            AlertaUtil.advertencia(e.getMessage());
        } catch (AppException e) {
            AlertaUtil.error(e.getMessage());
        }
    }

    private void refrescarTabla() {
        try {
            aspirantes.setAll(aspiranteService.listar());
        } catch (AppException e) {
            AlertaUtil.error(e.getMessage());
        }
    }

    private AspiranteDTO leerFormulario(Integer id) {
        return new AspiranteDTO(id, txtNombre.getText(), txtApellido.getText(), txtCorreo.getText(),
                txtTelefono.getText(), txtDireccion.getText(), leerEdad());
    }

    private int leerEdad() {
        try {
            return Integer.parseInt(txtEdad.getText().trim());
        } catch (NumberFormatException e) {
            throw new ValidacionException("La edad debe ser un número entero.");
        }
    }

    private void cargarEnFormulario(AspiranteDTO a) {
        if (a == null) {
            return;
        }
        txtNombre.setText(a.getNombre());
        txtApellido.setText(a.getApellido());
        txtCorreo.setText(a.getCorreo());
        txtTelefono.setText(a.getTelefono());
        txtDireccion.setText(a.getDireccion());
        txtEdad.setText(String.valueOf(a.getEdad()));
    }

    private void limpiarFormulario() {
        txtNombre.clear();
        txtApellido.clear();
        txtCorreo.clear();
        txtTelefono.clear();
        txtDireccion.clear();
        txtEdad.clear();
    }
}
