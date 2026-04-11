/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package validadores;

import daosRestaurante.IIngredienteDAO;
import daosRestaurante.IngredienteDAO;
import enumEntidades.UnidadMedida;
import excepcionesRestaurante.NegocioException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author RAMSES
 */
public class Validaciones {

    private IIngredienteDAO ingredienteDAO = IngredienteDAO.getInstanceIngredientesDAO();
    
    public Validaciones() {
    }

    public boolean validarCorreo(String correo) {

        Pattern patt = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher match = patt.matcher(correo);

        if (match.find()) {
            return true;
        }
        return false;
    }

    public boolean validarTelefono(String telefono) {
        Pattern patt = Pattern.compile("^[0-9]{10}$");
        Matcher match = patt.matcher(telefono);

        if (match.find()) {
            return true;
        }
        return false;
    }

    public boolean validarNombres(String nombres) {
        Pattern patt = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{1,100}$");
        Matcher match = patt.matcher(nombres);

        if (match.find()) {
            return true;
        }
        return false;
    }

    public boolean validarApellidos(String apellido) {
        Pattern patt = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]{1,100}$");
        Matcher match = patt.matcher(apellido);

        if (match.find()) {
            return true;
        }
        return false;
    }

    public boolean validarStock(Integer stock) {
        // Verificamos que el objeto no sea nulo para evitar un NullPointerException
        if (stock == null) {
            return false;
        }

        // Solo permitimos numeros mayores o iguales a cero
        if (stock >= 0) {
            return true;
        }

        return false;
    }
    
    public boolean validarRepetido(String nombre, UnidadMedida unidadMedida) throws NegocioException {
    // 1. Verificamos que los parámetros no sean nulos
    if (nombre == null || unidadMedida == null) {
        throw new NegocioException("Datos insuficientes para validar duplicados.");
    }

    // 2. Consultamos al DAO
    boolean existe = ingredienteDAO.existeIngrediente(nombre.trim(), unidadMedida);

    // 3. Si existe, devolvemos false (o lanzamos la excepción directamente)
    if (existe) {
        return false; // Significa que NO es válido porque ya está repetido
    }
    
    return true; // Es válido, no está repetido
}
    
}
