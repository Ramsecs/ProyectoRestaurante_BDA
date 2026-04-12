/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package seguridad;

import org.mindrot.jbcrypt.BCrypt;

/**
 *
 * @author RAMSES
 */
public class PasswordHasher {
    /**
     * Metodo que nos ayuda a generar el hash para guardarlo en la bse de datos.
     * @param password
     * @return 
     */
    public static String hash(String password) {
        return BCrypt.hashpw(password, BCrypt.gensalt());
    }

    /**
     * Mediante este metodo verificamos que la contraseña que se ingresa sea correcta 
     * @param passwordPlano
     * @param passwordHash
     * @return 
     */
    public static boolean verificar(String passwordPlano, String passwordHash) {
        try {
            return BCrypt.checkpw(passwordPlano, passwordHash);
        } catch (Exception e) {
            return false;
        }
    }
}
