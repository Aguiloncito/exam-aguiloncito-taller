package main.java.com.aguil.aguilonchas.dto;

/**
 *
 * @author AGUILON
 */
public class RegistroDTO {

    private String nombreUsuario;
    private String correo;
    private String contrasena;
    private String confirmacion;
    private int idRol;

    public RegistroDTO() {
    }

    public RegistroDTO(String nombreUsuario, String correo, String contrasena, String confirmacion, int idRol) {
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.contrasena = contrasena;
        this.confirmacion = confirmacion;
        this.idRol = idRol;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() {
        return contrasena;
    }

    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    public String getConfirmacion() {
        return confirmacion;
    }

    public void setConfirmacion(String confirmacion) {
        this.confirmacion = confirmacion;
    }

    public int getIdRol() {
        return idRol;
    }

    public void setIdRol(int idRol) {
        this.idRol = idRol;
    }
}