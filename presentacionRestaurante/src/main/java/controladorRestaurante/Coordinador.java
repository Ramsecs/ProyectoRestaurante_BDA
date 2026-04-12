/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladorRestaurante;

import dtosDelRestaurante.ClienteBusquedaDTO;
import dtosDelRestaurante.ClienteDTO;
import dtosDelRestaurante.IngredienteDTOLista;
import dtosDelRestaurante.ProductoDTO;
import dtosDelRestaurante.ProductoIngredienteDTO;
import entidadesRestaurante.Ingrediente;
import entidadesRestaurante.ProductoIngrediente;
import dtosDelRestaurante.IngredienteBusquedaDTO;
import dtosDelRestaurante.IngredientesDTO;
import excepcionesRestaurante.NegocioException;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import objetosNegocioRestaurante.ClienteBO;
import objetosNegocioRestaurante.IClienteBO;
import objetosNegocioRestaurante.IIngredienteProductoBO;
import objetosNegocioRestaurante.IProductoBO;
import objetosNegocioRestaurante.IngredienteProductoBO;
import objetosNegocioRestaurante.ProductoBO;
import objetosNegocioRestaurante.IIngredienteBO;
import objetosNegocioRestaurante.IngredienteBO;
import observadorRestaurante.Observador;
import pantallas.*;
import validadores.Validaciones;

/**
 *
 * @author RAMSES
 */
public class Coordinador implements Observador {

    //Lista temporal para los ingredientes que el usuario seleccione para cuando agrega el producto
    private List<ProductoIngredienteDTO> ingredientesTemporales;
    // Guardamos temporalmente los datos del producto que estan en la ventana
    private ProductoDTO productoTemporal;
    //Ventana para mostrar detalles
    private VentanaDialogVerIngredientes ventana_ver_detalles;

//Capas de negocio (BOs)
    private final IClienteBO clienteBO;
    private final IProductoBO productoBO;
    private final IIngredienteProductoBO ingredienteProductoBO;

    private Validaciones validar;

    private final IIngredienteBO ingredienteBO;

    //Ventanas que se usaran para la navegacion
    private VentanaMenuAdmin ventana_menu_admin;
    private VentanaMenuMesero ventana_menu_mesero;
    private VentanaMenuCliente ventana_menu_cliente;
    private VentanaMenuProducto ventana_menu_producto;
    private VentanaMenuIngrediente ventana_menu_ingrediente;
    private VentanaMenuComanda ventana_menu_comanda;
    private VentanaCrearComanda ventana_crear_comanda;
    private VentanaDialogAgregarIngrediente ventana_agregar_ingredientes;
    private VentanaInicioSesion ventana_inicio_sesion;

    public Coordinador() {
        this.clienteBO = ClienteBO.getInstanceClienteBO();
        this.productoBO = ProductoBO.getInstanceProductoBO();
        this.ingredienteProductoBO = IngredienteProductoBO.getInstanceIngredienteBO();
        this.ingredienteBO = IngredienteBO.getInstanceIngredienteBO();
        validar = new Validaciones();
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
     * Hacer visible la ventana de menu de ingredientes, donde se puede editar
     * agregar y consultar.
     */
    public void mostrarMenuIngrediente() {
        if (ventana_menu_ingrediente != null) {
            ventana_menu_ingrediente.setVisible(false);
        }

        if (ventana_menu_ingrediente == null) {
            ventana_menu_ingrediente = new VentanaMenuIngrediente(this);
            ventana_menu_ingrediente.setConexionObservador(this);
        }
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

        if (ventana_menu_admin != null) {
            ventana_menu_admin.setVisible(true);
            ventana_menu_admin.toFront();
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
    public void mostrarCrearComanda() {
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
     * Hacer visible la ventana de menu comandas, donde se puede agregar, editar
     * y consultar.
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
    public void volverComandaMesero() {
        if (ventana_menu_comanda != null) {
            ventana_menu_comanda.dispose();
        }

        if (ventana_menu_mesero == null) {
            ventana_menu_mesero = new VentanaMenuMesero(this);
        }
        ventana_menu_mesero.setVisible(true);
        ventana_menu_mesero.toFront();
    }
    /**
     * Con este metodo vamos a poder mostrar el JDialog de la tabla 
     * de clientes para poder adjudicar la comanda al cliente 
     * @param padre que es el frame del que viene el JDialog
     */
    
    public void mostrarDialogClienteComanda(JFrame padre){
        VentanaDialogComandaCliente dialogo = new VentanaDialogComandaCliente(this, padre);
        dialogo.setVisible(true);
    }
    
    public void mostrarDialogModificarComanda(JFrame padre){
        VentanaDialogModificar dialogo = new VentanaDialogModificar(this, padre);
        dialogo.setVisible(true);
    }
    
    //===================FIN METODOS JOS JOS====================================
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    
    /*
    public void mostrarDialogoIngredientesVista(JFrame padre) {
        VentanaDialogVerIngredientes dialogo_vista = new VentanaDialogVerIngredientes(this, padre);

        dialogo_vista.setVisible(true);
    }
     */
    @Override
    public void actualizar_empleado(ClienteBusquedaDTO clienteDTO) {
        this.actualizarCliente(clienteDTO);
    }

    /**
     * =======================================================================
     * METODOS QUE SON USADOS PARA LAS VENTANAS DE PRODUCTO CON INGREDIENTES
     * =======================================================================
     */

    /**
     * Este metodo nos da la lista completa de los productos que hay en el
     * registro.
     *
     * @return
     */
    public List<ProductoDTO> obtenerListaProductos() {
        List<ProductoDTO> lista_productos = new ArrayList<>();
        try {

            lista_productos = productoBO.listarProductos();

        } catch (NegocioException ex) {
            System.out.println("No se consiguio de manera correcta la lista de productos de negocio.");
        }

        return lista_productos;

    }

    /**
     * Mediante este metodo obtenemos la lista de ingredientes que hay en la
     * base de datos.
     *
     * @return
     */
    public List<IngredienteDTOLista> obtenerListaIngredientes() {
        // Inicializa con una lista vacia, no con null
        List<IngredienteDTOLista> lista = new ArrayList<>();
        try {
            lista = ingredienteProductoBO.recuperarListaIngredientes();
        } catch (NegocioException ex) {
            System.out.println("Error en Coordinador: " + ex.getMessage());
            // Aqui podrias mostrar un JOptionPane para avisar al usuario
        }
        return lista; // Si falla, devuelve la lista vacia creada arriba
    }

    /**
     * Este metodo muestra la ventana de agregar ingredientes a el producto que
     * estamos agregando.
     *
     * @param frame
     */
    public void ventanaProductosAAgregarIngredientes(JFrame frame) {

        if (ventana_menu_producto != null) {
            ventana_menu_producto.setVisible(false);
        }

        if (ventana_agregar_ingredientes == null) {
            ventana_agregar_ingredientes = new VentanaDialogAgregarIngrediente(this, frame);
            ventana_agregar_ingredientes.setConexionObservador(this);
        }
        ventana_agregar_ingredientes.setVisible(true);
        ventana_agregar_ingredientes.toFront();

    }

    /**
     * Mediante este metodo cambiamos de la ventana menu productos a ventana de
     * menu administrador.
     */
    public void ventanaMenuProductosAMenuAdmin() {
        if (ventana_menu_producto != null) {
            ventana_menu_producto.setVisible(false);
        }

        if (ventana_menu_admin != null) {
            ventana_menu_admin.setVisible(true);
            ventana_menu_admin.toFront();
        }
    }

    /**
     * Mediante este metodo se manda la lista de ingredientes y el producto a
     * registrar en BO.
     *
     * @param ingredientesElegidos
     */
    @Override
    public void enviarProductoConListaARegistro(List<ProductoIngredienteDTO> ingredientesElegidos) {

        // 1. Convertimos los DTOs a Entidades ProductoIngrediente
        List<ProductoIngrediente> detalles = new ArrayList<>();
        for (ProductoIngredienteDTO dto : ingredientesElegidos) {
            ProductoIngrediente pi = new ProductoIngrediente();
            pi.setCantidad_ingrediente(dto.getCantidad());

            // Creamos un ingrediente dummy solo con el ID para la relación
            Ingrediente ing = new Ingrediente();
            ing.setId(dto.getIdIngrediente());
            pi.setIngredientes(ing);

            detalles.add(pi);
        }

        // 2. Llamamos al BO para registrar
        try {
            productoBO.registrarProducto(productoTemporal, detalles);
            JOptionPane.showMessageDialog(null, "¡Producto registrado con éxito!");

            // Opcional: Refrescar la tabla de la ventana productos
            ventana_menu_producto.llenarTabla();
            ventana_menu_producto.limpiarCampos();

        } catch (NegocioException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage());
        }

    }

    /**
     * Este metodo muestra la ventana de los ingredeintes de un producto cuando
     * se da click en el boton ver detalles en la tabla del menu de productos
     *
     * @param ventana
     * @param datosProducto
     */
    public void mostrarDialogoIngredientes(VentanaMenuProducto ventana, ProductoDTO datosProducto) {
        this.productoTemporal = datosProducto; // Guardamos el nombre, precio, etc.

        VentanaDialogAgregarIngrediente dialog = new VentanaDialogAgregarIngrediente(this, ventana);
        dialog.setConexionObservador(this); // El Coordinador se conecta como observador
        dialog.setVisible(true);
    }

    /**
     * Este metodo devuelve el valor boolean de la validacion que se hace a el
     * texto de nombre.
     *
     * @param nombre
     * @return
     */
    public boolean validarNombre(String nombre) {
        return validar.validarNombres(nombre);
    }

    /**
     * Este metodo devuelve el valor boolean de la validacion que se hace a el
     * texto de cant(Cantidad).
     *
     * @param cant
     * @return
     */
    public boolean validarCantidad(String cant) {
        return validar.validarCant(cant);
    }

    /**
     * Este metodo devuelve el valor boolean de la validacion que se hace a el
     * texto de precio.
     *
     * @param precio
     * @return
     */
    public boolean validarPrecio(String precio) {
        return validar.validarPrecio(precio);
    }

    /**
     * Mediante este metodo se muestra la ventana de los ingredientes de un
     * producto.
     *
     * @param padre
     * @param id_producto
     * @param nombre_producto
     */
    public void mostrarDialogoIngredientesVista(JFrame padre, Long id_producto, String nombre_producto) {
        try {
            // 1. Obtener los datos del BO
            List<IngredienteDTOLista> detalles = ingredienteProductoBO.listarDetallesProducto(id_producto);

            // 2. Instanciar el diálogo (suponiendo que tienes una ventana para VISUALIZAR)
            // Si usas la misma VentanaDialogAgregarIngrediente, asegúrate de pasarle los datos
            ventana_ver_detalles = new VentanaDialogVerIngredientes(this, padre, true, detalles);
            ventana_ver_detalles.setTitle("Ingredientes de: " + nombre_producto);
            ventana_ver_detalles.setVisible(true);

        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(padre, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Mediante este metodo se actualiza el nombre del producto cuando se cambia
     * el nombre del producto en la tabla de productos
     *
     * @param id
     * @param nuevoNombre
     */
    public void actualizarNombreProducto(Long id, String nuevoNombre) {
        try {
            productoBO.actualizarNombre(id, nuevoNombre);
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
    }

    /**
     * Mediante este metodo se actualiza el precio del producto cuando se cambia
     * el precio del producto en la tabla de productos
     *
     * @param id
     * @param nuevoPrecio
     */
    public void actualizarPrecioProducto(Long id, Double nuevoPrecio) {
        try {
            productoBO.actualizarPrecio(id, nuevoPrecio);
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }
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

    /**
     * Manda un cliente de tipo DTO para despues llamar a el metodo de
     * actualizacion, actualizando el liente ya existente
     *
     * @param clienteDTO
     */
    @Override
    public void updated(ClienteBusquedaDTO clienteDTO) {
        this.actualizarCliente(clienteDTO);
    }

    /**
     * Metodo para mostrar la pantalla inicial para hacer que el usuario escoja
     * si es admin o mesero
     */
    public void iniciarInicioSesion() {
        if (ventana_inicio_sesion == null) {
            ventana_inicio_sesion = new VentanaInicioSesion(this);
        }

        ventana_inicio_sesion.setVisible(true);

    }
}
