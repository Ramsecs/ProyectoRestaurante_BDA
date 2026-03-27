/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladorRestaurante;

import dtosDelRestaurante.ClienteDTO;
import excepcionesRestaurante.NegocioException;
import javax.swing.JOptionPane;
import objetosNegocioRestaurante.ClienteBO;
import objetosNegocioRestaurante.IClienteBO;
import pantallas.VentanaMenuMesero;
import pantallas.VentanaMenuCliente;

/**
 *
 * @author RAMSES
 */
public class Coordinador {
    
//Capas de negocio (BOs)
    private final IClienteBO clienteBO;
    
    //Ventanas que se usaran para la navegacion
    private VentanaMenuMesero ventana_menu_admin;
    private VentanaMenuMesero ventana_menu_mesero;
    private VentanaMenuCliente ventana_menu_cliente;
    
    public Coordinador(){
        this.clienteBO = ClienteBO.getInstanceClienteBO();
    }
     
    /**
     * Hacer visible la pantalla de menu admin, si esta no ha sido abierta entonces creamos una nueva
     */
    public void iniciarMenuAdmin(){
        if (ventana_menu_admin == null) {
            ventana_menu_admin = new VentanaMenuMesero(this);
        }
        
        ventana_menu_admin.setVisible(true);
        
    }
    
    public void iniciarMenuMesero(){
        if (ventana_menu_mesero == null) {
            ventana_menu_mesero = new VentanaMenuMesero(this);
        }
        
        ventana_menu_mesero.setVisible(true);
        
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
    
    /**
     * Metodo para regresar de la pantalla del menu de clientes para ir a la pantalla de menu administrador
    * */
    public void regresarMenuAdmin(){
        if (ventana_menu_cliente != null) {
            ventana_menu_cliente.dispose();
        }
        
        if (ventana_menu_admin != null) {
            ventana_menu_admin.setVisible(true);
            ventana_menu_admin.toFront();
        }
    }
    
    /**
     *
     * 
     * */
    
    public void agregarClienteFrecuente(ClienteDTO clienteDTO){
        
        try{
            clienteBO.registrarCliente(clienteDTO);
        }catch(NegocioException e){
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
        
    }
}
