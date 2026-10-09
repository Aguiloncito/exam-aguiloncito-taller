package main.java.com.aguil.aguilonchas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import main.java.com.aguil.aguilonchas.config.ConnectionDb;
import main.java.com.aguil.aguilonchas.exception.PersistenciaException;
import main.java.com.aguil.aguilonchas.model.Rol;

/**
 *
 * @author AGUILON
 */
public class RolRepositoryJdbc implements RolRepository {

    private static final String SELECT_TODOS = "SELECT id_rol, nombre FROM rol ORDER BY nombre";
    private static final String SELECT_POR_ID = "SELECT id_rol, nombre FROM rol WHERE id_rol = ?";

    private final ConnectionDb conexionBD;

    public RolRepositoryJdbc(ConnectionDb conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public List<Rol> listar() {
        List<Rol> roles = new ArrayList<>();
        try (Connection con = conexionBD.obtener();
             PreparedStatement ps = con.prepareStatement(SELECT_TODOS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                roles.add(new Rol(rs.getInt("id_rol"), rs.getString("nombre")));
            }
            return roles;
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudieron obtener los roles.", e);
        }
    }

    @Override
    public Optional<Rol> buscarPorId(int id) {
        try (Connection con = conexionBD.obtener();
             PreparedStatement ps = con.prepareStatement(SELECT_POR_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next()
                        ? Optional.of(new Rol(rs.getInt("id_rol"), rs.getString("nombre")))
                        : Optional.empty();
            }
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo obtener el rol.", e);
        }
    }
}
