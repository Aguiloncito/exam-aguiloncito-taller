package main.java.com.aguil.aguilonchas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import main.java.com.aguil.aguilonchas.config.ConnectionDb;
import main.java.com.aguil.aguilonchas.exception.PersistenciaException;
import main.java.com.aguil.aguilonchas.model.Rol;
import main.java.com.aguil.aguilonchas.model.Usuario;

/**
 *
 * @author AGUILON
 */
public class UsuarioRepositoryJdbc implements UsuarioRepository {

    private static final String SELECT_POR_NOMBRE =
            "SELECT u.id_usuario, u.nombre_usuario, u.correo, u.contrasena_hash, r.id_rol, r.nombre AS rol_nombre "
                    + "FROM usuario u INNER JOIN rol r ON r.id_rol = u.id_rol "
                    + "WHERE u.nombre_usuario = ?";
    private static final String EXISTE_NOMBRE = "SELECT 1 FROM usuario WHERE nombre_usuario = ?";
    private static final String EXISTE_CORREO = "SELECT 1 FROM usuario WHERE correo = ?";
    private static final String INSERT =
            "INSERT INTO usuario (nombre_usuario, correo, contrasena_hash, id_rol) VALUES (?, ?, ?, ?)";

    private final ConnectionDb conexionBD;

    public UsuarioRepositoryJdbc(ConnectionDb conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public Optional<Usuario> buscarPorNombreUsuario(String nombreUsuario) {
        try (Connection con = conexionBD.obtener();
             PreparedStatement ps = con.prepareStatement(SELECT_POR_NOMBRE)) {
            ps.setString(1, nombreUsuario);
            try (ResultSet rs = ps.executeQuery()) {
                if (!rs.next()) {
                    return Optional.empty();
                }
                Rol rol = new Rol(rs.getInt("id_rol"), rs.getString("rol_nombre"));
                return Optional.of(new Usuario(
                        rs.getInt("id_usuario"),
                        rs.getString("nombre_usuario"),
                        rs.getString("correo"),
                        rs.getString("contrasena_hash"),
                        rol));
            }
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo consultar el usuario.", e);
        }
    }

    @Override
    public boolean existeNombreUsuario(String nombreUsuario) {
        return existe(EXISTE_NOMBRE, nombreUsuario);
    }

    @Override
    public boolean existeCorreo(String correo) {
        return existe(EXISTE_CORREO, correo);
    }

    @Override
    public void guardar(String nombreUsuario, String correo, String contrasenaHash, int idRol) {
        try (Connection con = conexionBD.obtener();
             PreparedStatement ps = con.prepareStatement(INSERT)) {
            ps.setString(1, nombreUsuario);
            ps.setString(2, correo);
            ps.setString(3, contrasenaHash);
            ps.setInt(4, idRol);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo registrar el usuario.", e);
        }
    }

    private boolean existe(String sql, String valor) {
        try (Connection con = conexionBD.obtener();
             PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, valor);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo verificar los datos del usuario.", e);
        }
    }
}
