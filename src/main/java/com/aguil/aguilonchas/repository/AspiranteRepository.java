package main.java.com.aguil.aguilonchas.repository;

import java.util.List;
import main.java.com.aguil.aguilonchas.model.Aspirante;

/**
 *
 * @author AGUILON
 */
public interface AspiranteRepository {

    List<Aspirante> listar();

    void guardar(Aspirante aspirante);

    void actualizar(Aspirante aspirante);

    void eliminar(int id);

    boolean existeId(int id);

    /** Indica si el correo ya lo usa otro aspirante distinto de {@code idExcluido} (puede ser null). */
    boolean existeCorreo(String correo, Integer idExcluido);
}
