/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladorRestaurante;

import dtosDelRestaurante.ClienteBusquedaDTO;
import dtosDelRestaurante.ClienteDTO;
import excepcionesRestaurante.NegocioException;
import java.util.List;
import javax.swing.JOptionPane;
import objetosNegocioRestaurante.ClienteBO;
import objetosNegocioRestaurante.IClienteBO;
import observadorRestaurante.Observador;
import pantallas.*;

/**
 *
 * @author RAMSES
 */
public class Coordinador implements Observador{

//Capas de negocio (BOs)
    private final IClienteBO clienteBO;

    //Ventanas que se usaran para la navegacion
    private VentanaMenuAdmin ventana_menu_admin;
    private VentanaMenuMesero ventana_menu_mesero;
    private VentanaMenuCliente ventana_menu_cliente;
    private VentanaMenuProducto ventana_menu_producto; 

    public Coordinador() {
        this.clienteBO = ClienteBO.getInstanceClienteBO();
    }

    /**
     * Hacer visible la pantalla de menu admin, si esta no ha sido abierta
     * entonces creamos una nueva
     */
    public void iniciarMenuAdmin(){
        if (ventana_menu_admin == null) {
            ventana_menu_admin = new VentanaMenuAdmin(this);
        }
        
        ventana_menu_admin.setVisible(true);
        
    }
    public void iniciarMenuMesero() {
        if (ventana_menu_mesero == null) {
            ventana_menu_mesero = new VentanaMenuMesero(this);
        }

        ventana_menu_mesero.setVisible(true);

    }

    /**
     * Hacer visible la ventana de menu cliente donde es la edicion, registro,
     * busqueda de clientes
     */
    public void mostrarMenuCliente() {
        if (ventana_menu_mesero != null) {
            ventana_menu_mesero.setVisible(false);
        }

        if (ventana_menu_cliente == null) {
            ventana_menu_cliente = new VentanaMenuCliente(this);
            ventana_menu_cliente.setConexionObservador(this);
        }
        //PARA QUE APAREZA LA TABLA CON LOS CLIENTES
        this.buscarClientes("");
        ventana_menu_cliente.setVisible(true);
        ventana_menu_cliente.toFront();

    }
    
    /**
     * Hacer visible la ventana del menu de Producto donde es la edicion, registro
     * y busqueda de productos.
     */
    
    public void mostrarMenuProducto(){
        if (ventana_menu_admin != null) {
            ventana_menu_admin.setVisible(false);
        }
        
        if (ventana_menu_producto == null) {
            ventana_menu_producto = new VentanaMenuProducto(this);
        }
        
        ventana_menu_producto.setVisible(true);
        ventana_menu_producto.toFront();
    }

    /**
     * Metodo para regresar de la pantalla del menu de clientes para ir a la
     * pantalla de menu administrador
    *
     */
    public void regresarMenuMesero() {
        if (ventana_menu_cliente != null) {
            ventana_menu_cliente.dispose();
        }

        if (ventana_menu_mesero != null) {
            ventana_menu_mesero.setVisible(true);
            ventana_menu_mesero.toFront();
        }
    }

    /**
     *
     *
     *
     */
    public void agregarClienteFrecuente(ClienteDTO clienteDTO) {

        try {
            clienteBO.registrarCliente(clienteDTO);
            JOptionPane.showMessageDialog(null, "Se agrego el cliente");
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    public void buscarClientes(String filtro) {

        try {
            List<ClienteBusquedaDTO> lista = clienteBO.buscarClientes(filtro);
            ventana_menu_cliente.actualizarTabla(lista);
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    /**
     * Recibe un cliente editado desde la tabla y solicita su actualización al
     * BO.
     * @param clienteDTO Objeto con los nuevos datos y el ID recuperado de la
     * lista tipo espejo.
     */
    public void actualizarCliente(ClienteBusquedaDTO clienteDTO) {
        try {
            // 1. Mandamos a llamar el metodo del BO
            clienteBO.actualizarDatosCliente(clienteDTO);

            // 2. Bandera por si a caso
            System.out.println("Cliente con ID " + clienteDTO.getId() + " actualizado.");

            // 3. Refrescamos la tabla para que se reflejen los cambios
            this.buscarClientes("");

        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error de Actualizacion", JOptionPane.ERROR_MESSAGE);

            // Refrescamos la tabla para "deshacer" el cambio visual 
            // que el mesero hizo en la celda y que no se guardó.
            this.buscarClientes("");
        }
    }

    @Override
    public void updated(ClienteBusquedaDTO clienteDTO) {
        this.actualizarCliente(clienteDTO);
    }
}
