package main.java.com.aguil.aguilonchas.util;

/**
 *
 * @author AGUILON
 */
public enum Vista {

    LOGIN("/main/resources/view/login-view.fxml", "Reclutamiento - Iniciar sesión"),
    REGISTRO("/main/resources/view//registro-view.fxml", "Reclutamiento - Registro"),
    INVENTARIO("/main/resources/view/inventario-view.fxml", "Reclutamiento - Inventario de aspirantes");

    private final String ruta;
    private final String titulo;

    Vista(String ruta, String titulo) {
        this.ruta = ruta;
        this.titulo = titulo;
    }

    public String getRuta() { return ruta; }
    public String getTitulo() { return titulo; }
}
