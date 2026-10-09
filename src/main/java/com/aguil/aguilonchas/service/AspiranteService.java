package main.java.com.aguil.aguilonchas.service;


import java.util.List;
import main.java.com.aguil.aguilonchas.dto.AspiranteDTO;

/**
 *
 * @author AGUILON
 */
public interface AspiranteService {

    List<AspiranteDTO> listar();

    void agregar(AspiranteDTO aspirante);

    void actualizar(AspiranteDTO aspirante);

    void eliminar(int id);
}
