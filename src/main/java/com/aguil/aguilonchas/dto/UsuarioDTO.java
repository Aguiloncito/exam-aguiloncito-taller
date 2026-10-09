package main.java.com.aguil.aguilonchas.dto;

/**
 *
 * @author AGUILON
 */
public class UsuarioDTO {

    private int id;
    private String nombreUsuario;
    private String correo;
    private String rol;

    public UsuarioDTO() {
    }

    public UsuarioDTO(int id, String nombreUsuario, String correo, String rol) {
        this.id = id;
        this.nombreUsuario = nombreUsuario;
        this.correo = correo;
        this.rol = rol;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public String getRol() {
        return rol;
    }

    public void setRol(String rol) {
        this.rol = rol;
    }
}