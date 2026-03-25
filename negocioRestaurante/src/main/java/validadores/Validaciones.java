/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validadores;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author RAMSES
 */
public class Validaciones {

    public Validaciones() {
    }
    
    public boolean validarCorreo(String correo){
        
        Pattern patt = Pattern.compile("[a-zA-Z0-9._]{1,10}[?<=@][a-zA-Z0-9._]{1,20}");
        Matcher match = patt.matcher(correo);
        
        if (match.find()) {
            return true;
        }
        return false;
    }
    
    public boolean validarTelefono(String telefono){
        Pattern patt = Pattern.compile("^[0-9]{10}$");
        Matcher match = patt.matcher(telefono);
        
        if (match.find()) {
            return true;
        }
        return false;
    }
    
    public boolean validarNombres(String nombres){
        Pattern patt = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{1,100}$");
        Matcher match = patt.matcher(nombres);
        
        if (match.find()) {
            return true;
        }
        return false;
    }
    
    public boolean validarApellidos(String apellido){
        Pattern patt = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]{1,100}$");
        Matcher match = patt.matcher(apellido);
        
        if (match.find()) {
            return true;
        }
        return false;
    }
    
}
