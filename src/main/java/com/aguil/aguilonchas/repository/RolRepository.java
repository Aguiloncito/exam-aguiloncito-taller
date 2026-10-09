package main.java.com.aguil.aguilonchas.repository;


import java.util.List;
import java.util.Optional;
import main.java.com.aguil.aguilonchas.model.Rol;

/**
 *
 * @author AGUILON
 */
public interface RolRepository {

    List<Rol> listar();

    Optional<Rol> buscarPorId(int id);
}
