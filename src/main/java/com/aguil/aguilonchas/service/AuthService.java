package main.java.com.aguil.aguilonchas.service;


import java.util.List;
import main.java.com.aguil.aguilonchas.dto.LoginDTO;
import main.java.com.aguil.aguilonchas.dto.RegistroDTO;
import main.java.com.aguil.aguilonchas.dto.RolDTO;
import main.java.com.aguil.aguilonchas.dto.UsuarioDTO;

/**
 *
 * @author AGUILON
 */
public interface AuthService {

    UsuarioDTO iniciarSesion(LoginDTO login);

    void registrar(RegistroDTO registro);

    List<RolDTO> listarRoles();
}
