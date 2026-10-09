package main.java.com.aguil.aguilonchas.repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import main.java.com.aguil.aguilonchas.config.ConnectionDb;
import main.java.com.aguil.aguilonchas.exception.PersistenciaException;
import main.java.com.aguil.aguilonchas.model.Aspirante;

/**
 *
 * @author AGUILON
 */
public class AspiranteRepositoryJdbc implements AspiranteRepository {

    private static final String SELECT_TODOS =
            "SELECT id_aspirante, nombre, apellido, correo, telefono, direccion, edad "
                    + "FROM aspirante ORDER BY id_aspirante";
    private static final String INSERT =
            "INSERT INTO aspirante (nombre, apellido, correo, telefono, direccion, edad) VALUES (?, ?, ?, ?, ?, ?)";
    private static final String UPDATE =
            "UPDATE aspirante SET nombre = ?, apellido = ?, correo = ?, telefono = ?, direccion = ?, edad = ? "
                    + "WHERE id_aspirante = ?";
    private static final String DELETE = "DELETE FROM aspirante WHERE id_aspirante = ?";
    private static final String EXISTE_ID = "SELECT 1 FROM aspirante WHERE id_aspirante = ?";
    private static final String EXISTE_CORREO =
            "SELECT 1 FROM aspirante WHERE correo = ? AND id_aspirante <> ?";

    private final ConnectionDb conexionBD;

    public AspiranteRepositoryJdbc(ConnectionDb conexionBD) {
        this.conexionBD = conexionBD;
    }

    @Override
    public List<Aspirante> listar() {
        List<Aspirante> aspirantes = new ArrayList<>();
        try (Connection con = conexionBD.obtener();
             PreparedStatement ps = con.prepareStatement(SELECT_TODOS);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                aspirantes.add(mapear(rs));
            }
            return aspirantes;
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo obtener la lista de aspirantes.", e);
        }
    }

    @Override
    public void guardar(Aspirante a) {
        try (Connection con = conexionBD.obtener();
             PreparedStatement ps = con.prepareStatement(INSERT)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getApellido());
            ps.setString(3, a.getCorreo());
            ps.setString(4, a.getTelefono());
            ps.setString(5, a.getDireccion());
            ps.setInt(6, a.getEdad());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo guardar el aspirante.", e);
        }
    }

    @Override
    public void actualizar(Aspirante a) {
        try (Connection con = conexionBD.obtener();
             PreparedStatement ps = con.prepareStatement(UPDATE)) {
            ps.setString(1, a.getNombre());
            ps.setString(2, a.getApellido());
            ps.setString(3, a.getCorreo());
            ps.setString(4, a.getTelefono());
            ps.setString(5, a.getDireccion());
            ps.setInt(6, a.getEdad());
            ps.setInt(7, a.getId());
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo actualizar el aspirante.", e);
        }
    }

    @Override
    public void eliminar(int id) {
        try (Connection con = conexionBD.obtener();
             PreparedStatement ps = con.prepareStatement(DELETE)) {
            ps.setInt(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo eliminar el aspirante.", e);
        }
    }

    @Override
    public boolean existeId(int id) {
        try (Connection con = conexionBD.obtener();
             PreparedStatement ps = con.prepareStatement(EXISTE_ID)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo verificar el aspirante.", e);
        }
    }

    @Override
    public boolean existeCorreo(String correo, Integer idExcluido) {
        try (Connection con = conexionBD.obtener();
             PreparedStatement ps = con.prepareStatement(EXISTE_CORREO)) {
            ps.setString(1, correo);
            ps.setInt(2, idExcluido == null ? -1 : idExcluido);
            try (ResultSet rs = ps.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            throw new PersistenciaException("No se pudo verificar el correo del aspirante.", e);
        }
    }

    private Aspirante mapear(ResultSet rs) throws SQLException {
        return new Aspirante(
                rs.getInt("id_aspirante"),
                rs.getString("nombre"),
                rs.getString("apellido"),
                rs.getString("correo"),
                rs.getString("telefono"),
                rs.getString("direccion"),
                rs.getInt("edad"));
    }
}
