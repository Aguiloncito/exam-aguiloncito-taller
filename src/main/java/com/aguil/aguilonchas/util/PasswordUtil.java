package main.java.com.aguil.aguilonchas.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author AGUILON
 */
public class PasswordUtil {

    private static final int COSTO = 12;

    public String hashear(String contrasenaPlana) {
        return BCrypt.hashpw(contrasenaPlana, BCrypt.gensalt(COSTO));
    }

    public boolean verificar(String contrasenaPlana, String hash) {
        try {
            return BCrypt.checkpw(contrasenaPlana, hash);
        } catch (IllegalArgumentException e) {
            return false; // hash con formato inválido
        }
    }
}
