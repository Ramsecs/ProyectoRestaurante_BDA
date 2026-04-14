/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import controladorRestaurante.Coordinador;
import entidadesRestaurante.Administrador;
import entidadesRestaurante.Empleado;
import entidadesRestaurante.Mesero;
import java.util.ArrayList;
import java.util.List;
import javax.swing.SwingUtilities;
import pantallas.*;

/**
 *
 * @author josma
 */
public class PruebasPantallas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        Coordinador coordinador = new Coordinador();
        
       
        
        /*
        List<Empleado> nuevos = new ArrayList<>();
        // Agregamos un Admin y un Mesero a la misma lista
        nuevos.add(new Administrador("admin_rest", "CODE123", "Ramses", "Admin", "García", "12345678"));
        nuevos.add(new Mesero("mesero_01", "CODE456", "Daniel", "López", "Méndez", "87654321"));
        nuevos.add(new Administrador ("admin_rest", "CODE098", "Alexa", "Quintana", "Benitez", "6441025765"));
        nuevos.add(new Mesero ("admin_02", "CODE321", "Daniel", "Ruiz", "Jocobi", "6441234567"));
        try {
            coordinador.registrarEmpleadosMasivo(nuevos);
            System.out.println("Cuentas creadas exitosamente.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
        */
        coordinador.iniciarInicioSesion();
        
        
        
    }
    
}
