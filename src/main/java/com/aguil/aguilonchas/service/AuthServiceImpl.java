package main.java.com.aguil.aguilonchas.service;

import java.util.List;
import main.java.com.aguil.aguilonchas.dto.LoginDTO;
import main.java.com.aguil.aguilonchas.dto.RegistroDTO;
import main.java.com.aguil.aguilonchas.dto.RolDTO;
import main.java.com.aguil.aguilonchas.dto.UsuarioDTO;
import main.java.com.aguil.aguilonchas.exception.AuthException;
import main.java.com.aguil.aguilonchas.exception.ValidacionException;
import main.java.com.aguil.aguilonchas.model.Usuario;
import main.java.com.aguil.aguilonchas.repository.RolRepository;
import main.java.com.aguil.aguilonchas.repository.UsuarioRepository;
import main.java.com.aguil.aguilonchas.util.PasswordUtil;

/**
 *
 * @author AGUILON
 */
public class AuthServiceImpl implements AuthService {

    private static final int LONGITUD_MINIMA_CLAVE = 6;
    private static final String PATRON_CORREO = "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$";

    private final UsuarioRepository usuarioRepository;
    private final RolRepository rolRepository;
    private final PasswordUtil passwordUtil;

    public AuthServiceImpl(UsuarioRepository usuarioRepository, RolRepository rolRepository,
                           PasswordUtil passwordUtil) {
        this.usuarioRepository = usuarioRepository;
        this.rolRepository = rolRepository;
        this.passwordUtil = passwordUtil;
    }

    @Override
    public UsuarioDTO iniciarSesion(LoginDTO login) {
        if (estaVacio(login.getNombreUsuario()) || estaVacio(login.getContraseña())) {
            throw new ValidacionException("Ingrese su usuario y contraseña.");
        }
        Usuario usuario = usuarioRepository.buscarPorNombreUsuario(login.getNombreUsuario().trim())
                .orElseThrow(() -> new AuthException("Usuario o contraseña incorrectos."));

        if (!passwordUtil.verificar(login.getContraseña(), usuario.getContrasenaHash())) {
            throw new AuthException("Usuario o contraseña incorrectos.");
        }
        return new UsuarioDTO(usuario.getId(), usuario.getNombreUsuario(),
                usuario.getCorreo(), usuario.getRol().getNombre());
    }

    @Override
    public void registrar(RegistroDTO registro) {
        validarRegistro(registro);

        String nombreUsuario = registro.getNombreUsuario().trim();
        String correo = registro.getCorreo().trim().toLowerCase();

        if (usuarioRepository.existeNombreUsuario(nombreUsuario)) {
            throw new AuthException("El nombre de usuario ya está en uso.");
        }
        if (usuarioRepository.existeCorreo(correo)) {
            throw new AuthException("Ya existe una cuenta con ese correo.");
        }
        if (rolRepository.buscarPorId(registro.getIdRol()).isEmpty()) {
            throw new AuthException("El rol seleccionado no existe.");
        }
        usuarioRepository.guardar(nombreUsuario, correo,
                passwordUtil.hashear(registro.getContrasena()), registro.getIdRol());
    }

    @Override
    public List<RolDTO> listarRoles() {
        return rolRepository.listar().stream()
                .map(rol -> new RolDTO(rol.getId(), rol.getNombre()))
                .toList();
    }

    private void validarRegistro(RegistroDTO r) {
        if (estaVacio(r.getNombreUsuario()) || estaVacio(r.getCorreo())
                || estaVacio(r.getContrasena()) || estaVacio(r.getConfirmacion())) {
            throw new ValidacionException("Complete todos los campos del registro.");
        }
        if (r.getIdRol() <= 0) {
            throw new ValidacionException("Seleccione un rol.");
        }
        if (!r.getCorreo().trim().matches(PATRON_CORREO)) {
            throw new ValidacionException("El correo electrónico no tiene un formato válido.");
        }
        if (r.getContrasena().length() < LONGITUD_MINIMA_CLAVE) {
            throw new ValidacionException(
                    "La contraseña debe tener al menos " + LONGITUD_MINIMA_CLAVE + " caracteres.");
        }
        if (!r.getContrasena().equals(r.getConfirmacion())) {
            throw new ValidacionException("Las contraseñas no coinciden.");
        }
    }

    private boolean estaVacio(String texto) {
        return texto == null || texto.isBlank();
    }
}
