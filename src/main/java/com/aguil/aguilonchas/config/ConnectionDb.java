/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package main.java.com.aguil.aguilonchas.config;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import static main.java.com.aguil.aguilonchas.config.CredentialsDb.PASS_DB;
import static main.java.com.aguil.aguilonchas.config.CredentialsDb.URL_DB;
import static main.java.com.aguil.aguilonchas.config.CredentialsDb.USER_DB;
import main.java.com.aguil.aguilonchas.exception.PersistenciaException;

/**
 *
 * @author AGUILON
 */
public class ConnectionDb {
    //atributos 
    private static Connection connection;

    public ConnectionDb(){}
    //metodo
    public static Connection getConnectionDb() throws SQLException{
        if(connection == null || connection.isClosed()){
            connection = DriverManager.getConnection(CredentialsDb.URL_DB, CredentialsDb.USER_DB, CredentialsDb.PASS_DB);
        }
        return connection;
    }
    
    public Connection obtener() {
        try {
            return DriverManager.getConnection(URL_DB, USER_DB, PASS_DB);
        } catch (SQLException e) {
            throw new PersistenciaException(
                    "No se pudo conectar con la base de datos. Verifique que MySQL esté activo y las credenciales sean correctas.", e);
        }
    }
}
