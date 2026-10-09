package main.java.com.aguil.aguilonchas.util;

import main.java.com.aguil.aguilonchas.dto.UsuarioDTO;


/**
 *
 * @author AGUILON
 */
public class SesionUsuario {

    private UsuarioDTO usuario;

    public void iniciar(UsuarioDTO usuario) {
        this.usuario = usuario;
    }

    public void cerrar() {
        this.usuario = null;
    }

    public UsuarioDTO getUsuario() {
        return usuario;
    }
}
