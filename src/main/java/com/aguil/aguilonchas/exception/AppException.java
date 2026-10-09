package main.java.com.aguil.aguilonchas.exception;

/**
 *
 * @author AGUILON
 */
public class AppException extends RuntimeException {

    public AppException(String mensaje) {
        super(mensaje);
    }

    public AppException(String mensaje, Throwable causa) {
        super(mensaje, causa);
    }
}
