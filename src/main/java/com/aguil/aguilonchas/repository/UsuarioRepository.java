package main.java.com.aguil.aguilonchas.repository;


import java.util.Optional;
import main.java.com.aguil.aguilonchas.model.Usuario;

/**
 *
 * @author AGUILON
 */
public interface UsuarioRepository {

    Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario);

    boolean existeNombreUsuario(String nombreUsuario);

    boolean existeCorreo(String correo);

    void guardar(String nombreUsuario, String correo, String contrasenaHash, int idRol);
}
