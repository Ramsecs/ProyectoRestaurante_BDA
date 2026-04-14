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
 * @author RAMSES / DANIEL / JOSMARA
 */
public class Validaciones {

    private IIngredienteDAO ingredienteDAO = IngredienteDAO.getInstanceIngredientesDAO();
    
    public Validaciones() {
    }

    /**
     * Metodo de validacion para el correo electronico.
     * 
     * @param correo
     * @return boolean
     */
    public boolean validarCorreo(String correo) {

        Pattern patt = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$");
        Matcher match = patt.matcher(correo);

        if (match.find()) {
            return true;
        }
        return false;
    }

    /**
     * Metodo de validacion del telefono.
     * 
     * @param telefono
     * @return boolean
     */
    public boolean validarTelefono(String telefono) {
        Pattern patt = Pattern.compile("^[0-9]{10}$");
        Matcher match = patt.matcher(telefono);

        if (match.find()) {
            return true;
        }
        return false;
    }

    /**
     * Metodo de validacion para el nombre.
     * 
     * @param nombres
     * @return boolean
     */
    public boolean validarNombres(String nombres) {
        Pattern patt = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ ]{1,100}$");
        Matcher match = patt.matcher(nombres);

        if (match.find()) {
            return true;
        }
        return false;
    }

    /**
     * Metodo de validacion para los apellidos.
     * 
     * @param apellido
     * @return boolean
     */
    public boolean validarApellidos(String apellido) {
        Pattern patt = Pattern.compile("^[a-zA-ZáéíóúÁÉÍÓÚñÑ]{1,100}$");
        Matcher match = patt.matcher(apellido);

        if (match.find()) {
            return true;
        }
        return false;
    }

    /**
     * Metodo de validacion para el stock.
     * 
     * @param stock
     * @return boolean
     */
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
    
    /**
     * Metodo de validacion para saber si es que ya 
     * esiste un ingrediente registrado.
     * 
     * @param nombre
     * @param unidadMedida
     * @return boolean
     * @throws NegocioException 
     */
    public boolean validarRepetido(String nombre, UnidadMedida unidadMedida) throws NegocioException {
        // Verificamos que los parámetros no sean nulos
        if (nombre == null || unidadMedida == null) {
            throw new NegocioException("Datos insuficientes para validar duplicados.");
        }

        // Consultamos al DAO
        boolean existe = ingredienteDAO.existeIngrediente(nombre.trim(), unidadMedida);

        // Si existe, devolvemos false
        if (existe) {
            return false; // Significa que NO es valido porque ya esta repetido
        }

        return true; // Es valido, no esta repetido
    }
    
    
    /**
     * Metodo de validacion de precios.
     * 
     * @param precio
     * @return boolean
     */
    public boolean validarPrecio(String precio){
        
        Pattern patt = Pattern.compile("^\\d+(\\.\\d{1,2})?$");
        Matcher match = patt.matcher(precio);
        
        if (match.find()) {
            return true;
        }
        return false;
    }
    
    /**
     * Metodo de validacion para una cantidad como
     * un stock o cantidad de productos.
     * 
     * @param cant
     * @return boolean
     */
    public boolean validarCant(String cant){
        Pattern patt = Pattern.compile("^\\d+$");
        Matcher match = patt.matcher(cant);
        
        if (match.find()) {
            return true;
        }
        return false;
    }
    
}
