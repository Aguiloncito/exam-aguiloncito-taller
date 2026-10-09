package main.java.com.aguil.aguilonchas.dto;

/**
 *
 * @author AGUILON
 */
public class LoginDTO {

    private String nombreUsuario;
    private String contraseña;

    public LoginDTO() {
    }

    public LoginDTO(String nombreUsuario, String contraseña) {
        this.nombreUsuario = nombreUsuario;
        this.contraseña = contraseña;
    }

    public String getNombreUsuario() {
        return nombreUsuario;
    }

    public void setNombreUsuario(String nombreUsuario) {
        this.nombreUsuario = nombreUsuario;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }
}