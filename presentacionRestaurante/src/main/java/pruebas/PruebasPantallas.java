/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package pruebas;

import javax.swing.SwingUtilities;
import pantallas.VentanaMenuAdmin;

/**
 *
 * @author josma
 */
public class PruebasPantallas {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        SwingUtilities.invokeLater(() -> {
            new VentanaMenuAdmin().setVisible(true);
        });
    }
    
}
