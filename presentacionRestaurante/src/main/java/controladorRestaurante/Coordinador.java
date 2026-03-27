/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladorRestaurante;

import objetosNegocioRestaurante.ClienteBO;
import objetosNegocioRestaurante.IClienteBO;
import pantallas.VentanaMenuAdmin;
import pantallas.VentanaMenuCliente;

/**
 *
 * @author RAMSES
 */
public class Coordinador {
    
//Capas de negocio (BOs)
    private final IClienteBO clienteBO;
    
    //Ventanas que se usaran para la navegacion
    private VentanaMenuAdmin ventana_menu_admin;
    private VentanaMenuCliente ventana_menu_cliente;
    
    public Coordinador(){
        this.clienteBO = ClienteBO.getInstanceClienteBO();
    }
     
    /**
     * Hacer visible la pantalla de menu admin, si esta no ha sido abierta entonces creamos una nueva
     */
    public void iniciarMenuAdmin(){
        if (ventana_menu_admin == null) {
            ventana_menu_admin = new VentanaMenuAdmin(this);
        }
        
        ventana_menu_admin.setVisible(true);
        
    }

    
    /**
     * Hacer visible la ventana de menu cliente donde es la edicion, registro, busqueda de clientes
     */
    public void mostrarMenuCliente(){
        if (ventana_menu_admin != null) {
            ventana_menu_admin.setVisible(false);
        }
        
        if (ventana_menu_cliente == null) {
            ventana_menu_cliente = new VentanaMenuCliente(this);
        }
        
        ventana_menu_cliente.setVisible(true);
        ventana_menu_cliente.toFront();
        
    }
    
    
    public void regresarMenuAdmin(){
        if (ventana_menu_cliente != null) {
            ventana_menu_cliente.dispose();
        }
        
        if (ventana_menu_admin != null) {
            ventana_menu_admin.setVisible(true);
            ventana_menu_admin.toFront();
        }
    }
    
    
}
