package main.java.com.aguil.aguilonchas.service;

import java.util.List;
import main.java.com.aguil.aguilonchas.dto.AspiranteDTO;
import main.java.com.aguil.aguilonchas.exception.AspiranteException;
import main.java.com.aguil.aguilonchas.exception.ValidacionException;
import main.java.com.aguil.aguilonchas.model.Aspirante;
import main.java.com.aguil.aguilonchas.repository.AspiranteRepository;

/**
 *
 * @author AGUILON
 */
public class AspiranteServiceImpl implements AspiranteService {

    private static final int EDAD_MINIMA = 18;
    private static final int EDAD_MAXIMA = 99;
    private static final String PATRON_CORREO = "^[\\w.+-]+@[\\w-]+(\\.[\\w-]+)+$";
    private static final String PATRON_TELEFONO = "^[0-9+\\- ]{8,20}$";

    private final AspiranteRepository repository;

    public AspiranteServiceImpl(AspiranteRepository repository) {
        this.repository = repository;
    }

    @Override
    public List<AspiranteDTO> listar() {
        return repository.listar().stream().map(this::aDto).toList();
    }

    @Override
    public void agregar(AspiranteDTO dto) {
        validar(dto);
        if (repository.existeCorreo(dto.getCorreo().trim().toLowerCase(), null)) {
            throw new AspiranteException("Ya existe un aspirante con ese correo.");
        }
        repository.guardar(aEntidad(dto, null));
    }

    @Override
    public void actualizar(AspiranteDTO dto) {
        if (dto.getId() == null) {
            throw new ValidacionException("Seleccione un aspirante de la tabla para actualizar.");
        }
        validar(dto);
        if (!repository.existeId(dto.getId())) {
            throw new AspiranteException("El aspirante ya no existe en el sistema.");
        }
        if (repository.existeCorreo(dto.getCorreo().trim().toLowerCase(), dto.getId())) {
            throw new AspiranteException("Otro aspirante ya usa ese correo.");
        }
        repository.actualizar(aEntidad(dto, dto.getId()));
    }

    @Override
    public void eliminar(int id) {
        if (!repository.existeId(id)) {
            throw new AspiranteException("El aspirante ya no existe en el sistema.");
        }
        repository.eliminar(id);
    }

    private void validar(AspiranteDTO dto) {
        if (vacio(dto.getNombre()) || vacio(dto.getApellido()) || vacio(dto.getCorreo())
                || vacio(dto.getTelefono()) || vacio(dto.getDireccion())) {
            throw new ValidacionException("Complete todos los campos del aspirante.");
        }
        if (!dto.getCorreo().trim().matches(PATRON_CORREO)) {
            throw new ValidacionException("El correo electrónico no tiene un formato válido.");
        }
        if (!dto.getTelefono().trim().matches(PATRON_TELEFONO)) {
            throw new ValidacionException("El teléfono solo puede contener números, espacios, + o - (mínimo 8 dígitos).");
        }
        if (dto.getEdad() < EDAD_MINIMA || dto.getEdad() > EDAD_MAXIMA) {
            throw new ValidacionException(
                    "La edad debe estar entre " + EDAD_MINIMA + " y " + EDAD_MAXIMA + " años.");
        }
    }

    private boolean vacio(String texto) {
        return texto == null || texto.isBlank();
    }

    private AspiranteDTO aDto(Aspirante a) {
        return new AspiranteDTO(a.getId(), a.getNombre(), a.getApellido(), a.getCorreo(),
                a.getTelefono(), a.getDireccion(), a.getEdad());
    }

    private Aspirante aEntidad(AspiranteDTO dto, Integer id) {
        return new Aspirante(id, dto.getNombre().trim(), dto.getApellido().trim(),
                dto.getCorreo().trim().toLowerCase(), dto.getTelefono().trim(),
                dto.getDireccion().trim(), dto.getEdad());
    }
}
