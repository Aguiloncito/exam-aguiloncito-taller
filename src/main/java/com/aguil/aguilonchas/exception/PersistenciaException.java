package main.java.com.aguil.aguilonchas.exception;

/**
 *
 * @author AGUILON
 */
public class PersistenciaException extends AppException {

    public PersistenciaException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
