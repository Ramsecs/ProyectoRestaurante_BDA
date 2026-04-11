/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladorRestaurante;

import dtosDelRestaurante.ClienteBusquedaDTO;
import dtosDelRestaurante.ClienteDTO;
import dtosDelRestaurante.IngredienteBusquedaDTO;
import dtosDelRestaurante.IngredientesDTO;
import excepcionesRestaurante.NegocioException;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import objetosNegocioRestaurante.ClienteBO;
import objetosNegocioRestaurante.IClienteBO;
import objetosNegocioRestaurante.IIngredienteBO;
import objetosNegocioRestaurante.IngredienteBO;
import observadorRestaurante.Observador;
import pantallas.*;

/**
 *
 * @author RAMSES
 */
public class Coordinador implements Observador {

//Capas de negocio (BOs)
    private final IClienteBO clienteBO;

    private final IIngredienteBO ingredienteBO;

    //Ventanas que se usaran para la navegacion
    private VentanaMenuAdmin ventana_menu_admin;
    private VentanaMenuMesero ventana_menu_mesero;
    private VentanaMenuCliente ventana_menu_cliente;
    private VentanaMenuProducto ventana_menu_producto;
    private VentanaMenuIngrediente ventana_menu_ingrediente;
    private VentanaMenuComanda ventana_menu_comanda;
    private VentanaCrearComanda ventana_crear_comanda; 

    public Coordinador() {
        this.clienteBO = ClienteBO.getInstanceClienteBO();

        this.ingredienteBO = IngredienteBO.getInstanceIngredienteBO();
    }

    /**
     * Hacer visible la pantalla de menu admin, si esta no ha sido abierta
     * entonces creamos una nueva
     */
    public void iniciarMenuAdmin() {
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
    
    @Override
    public void actualizar_empleado(ClienteBusquedaDTO clienteDTO) {
        this.actualizarCliente(clienteDTO);
    }

    /**
     * Hacer visible la ventana de menu cliente donde es la edicion, registro,
     * busqueda de clientes.
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
     * Metodo para regresar de la pantalla del menu de clientes para ir a la
     * pantalla de menu administrador.
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
    
    public void regresarMenuAdmin() {
        if (ventana_menu_ingrediente != null) {
            ventana_menu_ingrediente.dispose();
        }

        if (ventana_menu_admin == null) {
            ventana_menu_admin = new VentanaMenuAdmin(this);
        }
        ventana_menu_admin.setVisible(true);
        ventana_menu_admin.toFront();
        
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
     *
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

    //----------------------------------VENTANAS TIPO DIALOG--------------------
    /**
     * Abre el diálogo para agregar ingredientes. Al ser un JDialog, necesita
     * saber qué JFrame lo está invocando para mantener la jerarquía visual y la
     * modalidad.
     *
     * @param padre La ventana que invoca el diálogo (usualmente
     * ventana_menu_producto)
     */
    public void mostrarDialogoIngredientes(JFrame padre) {
        // No guardamos el diálogo como atributo de clase porque usualmente
        // estos diálogos se crean y destruyen (dispose) en cada uso.
        VentanaDialogAgregarIngrediente dialogo = new VentanaDialogAgregarIngrediente(this, padre);

        // Aqui mismo se debe de agregar lo de los ingredientes, pero eso ya va despues
        // List<IngredienteDTO> ingredientes = ingredienteBO.obtenerTodos();
        // dialogo.cargarTabla(ingredientes);
        dialogo.setVisible(true);
    }
    
    //-------------------------METODOS JOS JOS----------------------------------
    /**
     * Este metodo abre la ventana para agregar una comanda nueva.
     */
    public void mostrarCrearComanda(){
        if (ventana_menu_mesero != null) {
            ventana_menu_mesero.setVisible(false);
        }

        if (ventana_crear_comanda == null) {
            ventana_crear_comanda = new VentanaCrearComanda(this);
        }

        ventana_crear_comanda.setVisible(true);
        ventana_crear_comanda.toFront();
    }
    
        /**
     * Hacer visible la ventana de menu comandas, donde se puede
     * agregar, editar y consultar.
     */
    public void mostrarMenuComanda() {
        if (ventana_menu_mesero != null) {
            ventana_menu_mesero.dispose();
        }

        if (ventana_menu_comanda == null) {
            ventana_menu_comanda = new VentanaMenuComanda(this);
            ventana_menu_comanda.setConexionObservador(this);
        }
        ventana_menu_comanda.setVisible(true);
        ventana_menu_comanda.toFront();
    }
    
    /**
     * Metodo para volver del MENU DE COMANDAS al MENU DE MESERO.
     *
     */  
    public void volverComandaMesero(){
        if (ventana_menu_comanda != null) {
            ventana_menu_comanda.dispose();
        }
        
        if (ventana_menu_mesero == null) {
            ventana_menu_mesero = new VentanaMenuMesero(this);
        }
        ventana_menu_mesero.setVisible(true);
        ventana_menu_mesero.toFront();
    }
    
    //==========================================================================
    
    //---------------------------------METODOS DANIEL---------------------------
    
    
    /**
     * Hacer visible la ventana de menu de ingredientes, donde se puede editar
     * agregar y consultar.
     */
    public void mostrarMenuIngrediente() {
        if (ventana_menu_admin != null) {
            ventana_menu_admin.dispose();
        }

        if (ventana_menu_ingrediente == null) {
            ventana_menu_ingrediente = new VentanaMenuIngrediente(this);
            ventana_menu_ingrediente.setConexionObservador(this);
        }
        this.buscarIngredientes("");
        ventana_menu_ingrediente.setVisible(true);
        ventana_menu_ingrediente.toFront();
        //------> AQUI FALTA LA PROGRAMACIÓN DE LA TABLA PARA QUE SE MUESTREN LOS INGREDIENTES
        //ES MUY SIMILAR POR NO DECIR QUE IGUAL A LA DE mostrarMenuCliente
    }

    /**
     * Hacer visible la ventana del menu de Producto donde es la edicion,
     * registro y busqueda de productos.
     */
    public void mostrarMenuProducto() {
        if (ventana_menu_admin != null) {
            ventana_menu_admin.setVisible(false);
        }

        if (ventana_menu_producto == null) {
            ventana_menu_producto = new VentanaMenuProducto(this);
        }

        ventana_menu_producto.setVisible(true);
        ventana_menu_producto.toFront();
    }
    
    
    public void mostrarDialogoIngredientesVista(JFrame padre) {
        VentanaDialogVerIngredientes dialogo_vista = new VentanaDialogVerIngredientes(this, padre);

        dialogo_vista.setVisible(true);
    }


    public void registrarIngrediente(IngredientesDTO ingredienteDTO) {
        try {
            ingredienteBO.registrarIngredientes(ingredienteDTO);
            JOptionPane.showMessageDialog(null, "Ingrediente registrado correctamente.");
            this.buscarIngredientes(""); // Refrescar tabla
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Busca ingredientes en la base de datos y le pide a la ventana que
     * refresque la tabla.
     *
     * @param filtro Texto para filtrar por nombre o unidad.
     */
    public void buscarIngredientes(String filtro) {
        try {
            List<IngredienteBusquedaDTO> lista = ingredienteBO.buscarIngredientes(filtro);
            if (ventana_menu_ingrediente != null) {
                // Este método debe existir en tu VentanaMenuIngrediente
                ventana_menu_ingrediente.cargarTablaDesdeCoordinador(lista);
            }
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Actualiza el stock de un ingrediente y refresca la vista.
     *
     * @param ingredienteDTO DTO con el ID y el nuevo stock.
     */
    @Override
    public void actualizarStockIngrediente(IngredienteBusquedaDTO ingredienteDTO) {
        try {
            ingredienteBO.actualizarStock(ingredienteDTO);
            System.out.println("Stock actualizado para: " + ingredienteDTO.getNombre());
            // No refrescamos toda la tabla aquí para no perder el foco del usuario, 
            // a menos que sea necesario.
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage(), "Error de Actualización", JOptionPane.ERROR_MESSAGE);
            this.buscarIngredientes(""); // Revertir cambios visuales si falló
        }
    }
    
}
