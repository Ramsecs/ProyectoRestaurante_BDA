/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladorRestaurante;

import pantallas.VentanaMenuCliente;
import dtosDelRestaurante.ClienteBusquedaDTO;
import dtosDelRestaurante.ClienteDTO;
import dtosDelRestaurante.ComandaDTO;
import dtosDelRestaurante.ComandaListaDTO;
import dtosDelRestaurante.ComandaProductoDTO;
import dtosDelRestaurante.DetalleComandaDTO;
import dtosDelRestaurante.EmpleadoRegistroDTO;
import dtosDelRestaurante.IngredienteDTOLista;
import dtosDelRestaurante.ProductoDTO;
import dtosDelRestaurante.ProductoIngredienteDTO;
import entidadesRestaurante.Ingrediente;
import entidadesRestaurante.ProductoIngrediente;
import dtosDelRestaurante.IngredienteBusquedaDTO;
import dtosDelRestaurante.IngredientesDTO;
import dtosDelRestaurante.ProductoComandaDTO;
import dtosDelRestaurante.ReporteClienteDTO;
import dtosDelRestaurante.ReporteComandaDTO;
import entidadesEnumeradorDTO.EstadoComandaDTO;
import entidadesEnumeradorDTO.TipoPlatilloDTO;
import entidadesRestaurante.Empleado;
import excepcionesRestaurante.NegocioException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import static javax.swing.SwingUtilities.invokeLater;
import javax.swing.table.DefaultTableModel;
import objetosNegocioRestaurante.ClienteBO;
import objetosNegocioRestaurante.ComandaBO;
import objetosNegocioRestaurante.EmpleadoBO;
import objetosNegocioRestaurante.IClienteBO;
import objetosNegocioRestaurante.IComandaBO;
import objetosNegocioRestaurante.IEmpleadoBO;
import objetosNegocioRestaurante.IIngredienteProductoBO;
import objetosNegocioRestaurante.IProductoBO;
import objetosNegocioRestaurante.IngredienteProductoBO;
import objetosNegocioRestaurante.ProductoBO;
import objetosNegocioRestaurante.IIngredienteBO;
import objetosNegocioRestaurante.IMesaBO;
import objetosNegocioRestaurante.IReporteBO;
import objetosNegocioRestaurante.IngredienteBO;
import objetosNegocioRestaurante.MesaBO;
import objetosNegocioRestaurante.ReporteBO;
import observadorRestaurante.Observador;
import pantallas.*;
import validadores.Validaciones;

/**
 *
 * @author RAMSES
 */
public class Coordinador implements Observador {

    // Guardamos temporalmente los datos del producto que estan en la ventana
    private ProductoDTO producto_temporal;
    //Ventana para mostrar detalles
    private VentanaDialogVerIngredientes ventana_ver_detalles;
    /**
     * Variable para el empleado que este en sesión, esto sera util para poder
     * relacionar el mesero y la comanda.
     */
    private EmpleadoRegistroDTO empleado_sesion;

//Capas de negocio (BOs)
    private final IClienteBO clienteBO;
    private final IProductoBO productoBO;
    private final IIngredienteProductoBO ingredienteProductoBO;
    private final IEmpleadoBO empleadoBO;
    private final IReporteBO reporteBO;
    private final IMesaBO mesaBO;

    private final IComandaBO comandaBO;

    private Validaciones validar;

    private final IIngredienteBO ingredienteBO;

    //Parte del dto para las comandas
    private ClienteBusquedaDTO cliente_Seleccionado_comanda;

    //Ventanas que se usaran para la navegacion
    private VentanaMenuAdmin ventana_menu_admin;
    private VentanaMenuMesero ventana_menu_mesero;
    private VentanaMenuCliente ventana_menu_cliente;
    private VentanaMenuProducto ventana_menu_producto;
    private VentanaMenuIngrediente ventana_menu_ingrediente;
    private VentanaMenuComanda ventana_menu_comanda;
    private VentanaCrearComanda ventana_crear_comanda;
    private VentanaDialogAgregarIngrediente ventana_agregar_ingredientes;
    private VentanaDialogModificar ventana_modifcar_comanda;
    private VentanaInicioSesion ventana_inicio_sesion;
    private VentanaDialogComandaCliente ventana_dialog_cliente;
    private VentanaReportes ventana_reportes;
    //No mover es mio (Jos)

    public Coordinador() {
        this.clienteBO = ClienteBO.getInstanceClienteBO();
        this.productoBO = ProductoBO.getInstanceProductoBO();
        this.ingredienteProductoBO = IngredienteProductoBO.getInstanceIngredienteBO();
        this.ingredienteBO = IngredienteBO.getInstanceIngredienteBO();
        this.empleadoBO = EmpleadoBO.getInstanceEmpleadoBO();
        this.reporteBO = ReporteBO.getInstanceReporteBO();
        this.comandaBO = ComandaBO.getInstanceComandaBO();
        this.mesaBO = MesaBO.getInstanceComandaBO();
        validar = new Validaciones();
    }

    /**
     * Guardamos este inicio de sesión, lo vamos a utilizar más adelante
     *
     * @param empleado empleado en sesión
     */
    public void setMeseroEnSesion(EmpleadoRegistroDTO empleado) {
        this.empleado_sesion = empleado;
    }

    /**
     * Obtener el empleado de la sesión
     *
     * @return
     */
    public EmpleadoRegistroDTO geMeseroEnSesion() {
        return empleado_sesion;
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

    /**
     * Metodo para inciar el menu del mesero.
     */
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

    /**
     * Metodo para regresar al menu administrador desde la ventana de menu
     * ingrediente.
     */
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
     * Metodo para registrar un cliente frecuente en la base de datos.
     *
     * @param clienteDTO
     */
    public void agregarClienteFrecuente(ClienteDTO clienteDTO) {

        try {
            clienteBO.registrarCliente(clienteDTO);
            JOptionPane.showMessageDialog(null, "Se agrego el cliente");
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, e.getMessage());
        }

    }

    /**
     * Mediante este metodo buscamos un cliente usando un filtro de busqueda.
     *
     * @param filtro
     */
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

    //==========================================================================
    //==========================METODOS JOS JOS=================================
    //==========================================================================
    public void prepararVentanaModificacion(ComandaListaDTO comanda_resumen, JFrame padre) {
        try {
            List<DetalleComandaDTO> detalles_actuales = comandaBO.consultarDetallesPorComanda(comanda_resumen.getId());
            List<ProductoDTO> catalogo_completo = productoBO.listarProductos();

            if (ventana_modifcar_comanda == null) {
                ventana_modifcar_comanda = new VentanaDialogModificar(this, padre);
            }

            ventana_modifcar_comanda.cargarDatosEdicion(comanda_resumen, detalles_actuales, catalogo_completo);
            ventana_modifcar_comanda.setVisible(true);
            ventana_modifcar_comanda.toFront();
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, "Error al cargar la comanda: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metodo para guardar todos los cambios realizados en la ventana de
     * modificación.
     *
     * * @param idComanda El ID de la comanda padre.
     * @param modificados Lista de productos que ya existían pero cambiaron
     * (cantidad/notas).
     * @param eliminados Lista de productos que el usuario marcó para borrar.
     * @param nuevos Lista de productos que se agregaron desde el catálogo.
     */
    public void guardarCambiosComanda(Long id_comanda, List<DetalleComandaDTO> modificados,
            List<DetalleComandaDTO> eliminados, List<DetalleComandaDTO> nuevos) {
        try {
            // 1. Mandamos llamar al BO para que procese la transacción
            // El BO se encargará de validar que las cantidades sean correctas
            comandaBO.guardarCambiosComanda(id_comanda, modificados, eliminados, nuevos);

            // 2. Si no hubo excepciones, notificamos al usuario
            JOptionPane.showMessageDialog(null,
                    "La comanda ha sido actualizada exitosamente.",
                    "Operación Exitosa",
                    JOptionPane.INFORMATION_MESSAGE);

            if (ventana_menu_comanda != null) {
                List<ComandaListaDTO> lista_actualizada = comandaBO.mostrarComandasAbiertas();
                ventana_menu_comanda.actualizarTabla(lista_actualizada);
            }

        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudieron aplicar los cambios: " + e.getMessage(),
                    "Error de Validación",
                    JOptionPane.WARNING_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Ocurrió un error inesperado en el sistema: " + e.getMessage(),
                    "Error Crítico",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Cambia el estado de la comanda desde la tabla
     *
     * @param id de la comanda
     * @param estado de la comanda
     */
    public void actualizarEstadoComanda(Long id, EstadoComandaDTO estado) {
        try {
            comandaBO.actualizarEstadoComanda(id, estado);

            invokeLater(() -> {
                cargarComandasAbiertas();
            });

            JOptionPane.showMessageDialog(ventana_menu_comanda, "Estado actualizado con éxito", "Confirmación", JOptionPane.INFORMATION_MESSAGE);
            cargarComandasAbiertas();
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null,
                    "No se pudo actualizar " + e.getMessage(),
                    "Error de Sistema",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Metodo que coordina unica y exclusivamente las comandas abiertas.
     */
    public void cargarComandasAbiertas() {
        try {
            List<ComandaListaDTO> lista = comandaBO.mostrarComandasAbiertas();

            if (ventana_menu_comanda != null) {
                ventana_menu_comanda.llenarTabla(lista); //Falta programar esto
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(null,
                    "Error al cargar las comandas: " + e.getMessage(),
                    "Error de Sistema",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }
    }

    /**
     * Metodo para poder cancelar una comanda desde los botones de mesa
     *
     * @param id_mesa pertenece a la mesa asociada con la comanda
     */
    public void cancelarComandas(Long id_mesa) {
        try {
            mesaBO.cancelarComandaMesa(id_mesa);
        } catch (NegocioException e) {
            System.err.println("Error al cancelar la comanda: " + e.getMessage());
        }
    }

    /**
     * Metodo para obtener las mesas ocupadas en el restaurante
     *
     * @return
     */
    public List<Long> obtenerMesasOcupadas() {
        try {
            return mesaBO.obtenerMesasOcupadas();
        } catch (NegocioException e) {
            System.err.println(e.getMessage());
            return new ArrayList<>();
        }
    }

    /**
     * Para obtener el id del cliente general
     *
     * @return
     */
    public Long obtenerIdClienteGeneral() {
        try {
            // Buscamos al cliente que se llame "Cliente" y apellido "Frecuente" (según tu imagen)
            List<ClienteBusquedaDTO> lista = clienteBO.buscarClientes("Cliente");
            for (ClienteBusquedaDTO cliente : lista) {
                if (cliente.getNombre().equals("Cliente") && cliente.getApellido_paterno().equals("Frecuente")) {
                    return cliente.getId(); // Aquí devolvería el 8 automáticamente
                }
            }
        } catch (Exception e) {
            System.out.println("Error al recuperar ID de cliente general");
        }
        return null;
    }

    /**
     * Solo para la main para poder registrar el cliente general
     *
     * @param cliente
     * @throws NegocioException
     */
    public void registrarCliente(ClienteDTO cliente) throws NegocioException {
        this.clienteBO.registrarCliente(cliente);
    }

    /**
     * Metodo para guardar la comanda
     *
     * @param comandaDTO
     * @throws NegocioException
     */
    public void guardarComanda(ComandaDTO comandaDTO) throws NegocioException {
        try {
            // Extraemos la lista de productos que ya vive dentro del DTO 
            // y se la pasamos al BO junto con el DTO principal
            this.comandaBO.guardarComanda(comandaDTO, comandaDTO.getProductos());
        } catch (NegocioException ex) {
            // Mantenemos la logica de lanzar la excepción hacia la ventana
            throw new NegocioException(ex.getMessage());
        }
    }

    /**
     * Metodo para mostrar por categoria los productos en el apartado de comanda
     *
     * @param tipo
     */
    public void cargarProductosPorCategoria(TipoPlatilloDTO tipo) {
        try {
            //1.Llamar al BO para que nos despliegue los platillos segun el tipo
            List<ProductoComandaDTO> productos = productoBO.listarProductosPorCategoria(tipo);

            if (productos != null) {
                //2. Pasamos los  DTOs a la ventana para que los muestre en la tabla
                this.ventana_crear_comanda.cargarTablaProductos(productos);
            } else {
                System.out.println("No se encontraron productos para la categoria: " + tipo);
                //Limpiamos la tabla visualmente 
                this.ventana_crear_comanda.cargarTablaProductos(new ArrayList<>());
            }
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(ventana_menu_cliente, e.getMessage(),
                    "Error de Consulta", JOptionPane.ERROR_MESSAGE);
            this.ventana_crear_comanda.cargarTablaProductos(new ArrayList<>());
        }
    }

    /**
     * Este metodo abre la ventana para agregar una comanda nueva.
     */
    public void mostrarCrearComanda() {
        if (ventana_menu_comanda != null) {
            ventana_menu_comanda.dispose();
        }

        if (ventana_crear_comanda == null) {
            ventana_crear_comanda = new VentanaCrearComanda(this);
        }
        ventana_crear_comanda.prerarVistaAgain();
        ventana_crear_comanda.setVisible(true);
        ventana_crear_comanda.toFront();
    }

    public void volverMenuComanda() {
        if (ventana_crear_comanda != null) {
            ventana_crear_comanda.dispose();
        }
        if (ventana_menu_comanda == null) {
            ventana_menu_comanda = new VentanaMenuComanda(this);
        }

        ventana_menu_comanda.setVisible(true);
        ventana_menu_comanda.toFront();
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
        cargarComandasAbiertas();
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
     * Metodo para que se muestre el Dialog de la tabla con clientes que pueden
     * ser seleccionados para poder adjudicarselo a una comanda
     *
     * @param padre que es el frame padre
     */
    public void mostrarSelectorCliente(VentanaCrearComanda padre) {
        // 1. Guardamos la instancia en la variable global de la clase
        this.ventana_dialog_cliente = new VentanaDialogComandaCliente(this, padre);

        // 2. Cargamos la lista inicial
        this.buscarClientesParaComanda("");

        this.ventana_dialog_cliente.setVisible(true);
    }

    /**
     * Este metodo ejecuta la busqueda del cliente, reutilizamos el bo y el dao
     *
     * @param filtro lo que va a recibir para buscarlo, en este caso el nombre
     */
    public void buscarClientesParaComanda(String filtro) {
        try {
            // Pedimos la lista al BO
            List<ClienteBusquedaDTO> lista = clienteBO.buscarClientes(filtro);

            // Si el diálogo está abierto, le refrescamos la tabla
            if (this.ventana_dialog_cliente != null) {
                this.ventana_dialog_cliente.cargarTabla(lista);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    /**
     * Buscamos los clientes que tengan una comanda
     *
     * @param filtro nombre del cliente
     */
    public void buscarClientesComanda(String filtro) {

        try {
            List<ComandaListaDTO> lista = comandaBO.filtrarComandasAbiertas(filtro);
            ventana_menu_comanda.actualizarTabla(lista);
        } catch (NegocioException e) {
            JOptionPane.showMessageDialog(null, "No se pudo filtrar" + e.getMessage());
        }

    }

    /**
     * Abrir el dialog para modificar la comanda
     *
     * @param padre frame del dialog
     */
    public void mostrarDialogModificarComanda(JFrame padre) {

        if (ventana_menu_comanda != null) {
            ventana_menu_comanda.setVisible(false);
        }

        if (ventana_modifcar_comanda == null) {
            ventana_modifcar_comanda = new VentanaDialogModificar(this, padre);
        }
        ventana_modifcar_comanda.setVisible(true);
        ventana_modifcar_comanda.toFront();

    }

    /**
     * Asginar cliente comanda
     *
     * @param cliente de la comanda
     */
    public void setClienteEnComanda(ClienteBusquedaDTO cliente) {

// 1. Guardamos el cliente en una variable del coordinador para la persistencia final
        if (cliente != null) {
            this.cliente_Seleccionado_comanda = cliente;
        }

        // 2. Actualizamos el boton en la ventana de la comanda
        if (cliente != null) {
            String nombre = cliente.getNombre() + " " + cliente.getApellido_paterno();
            // Usamos el método que ya tienes en tu VentanaCrearComanda
            ventana_crear_comanda.setClienteSeleccionado(cliente.getId(), nombre);
        }
        //Cerrar una vez que se selecciona un cliente
        if (this.ventana_dialog_cliente != null) {
            this.ventana_dialog_cliente.dispose();
            this.ventana_dialog_cliente = null; // Limpiamos la referencia
        } else {
            System.out.println("El objeto ventana_dialog_cliente es NULL en el Coordinador");
        }
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
     * @return Una lista de productos DTO con la que podemos trabajar.
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
     * @return Una lista de ingredientes DTO con la cual podamos trabajar.
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
            productoBO.registrarProducto(producto_temporal, detalles);
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
        this.producto_temporal = datosProducto; // Guardamos el nombre, precio, etc.

        VentanaDialogAgregarIngrediente dialog = new VentanaDialogAgregarIngrediente(this, ventana);
        dialog.setConexionObservador(this); // El Coordinador se conecta como observador
        dialog.setVisible(true);
    }

    /**
     * Devuelve el valor boolean de la validacion que se hace a el texto de
     * nombre.
     *
     * @param nombre
     * @return boolean
     */
    public boolean validarNombre(String nombre) {
        return validar.validarNombres(nombre);
    }

    /**
     * Este metodo devuelve el valor boolean de la validacion que se hace a el
     * texto de cant(Cantidad).
     *
     * @param cant
     * @return boolean
     */
    public boolean validarCantidad(String cant) {
        return validar.validarCant(cant);
    }

    /**
     * Este metodo devuelve el valor boolean de la validacion que se hace a el
     * texto de precio.
     *
     * @param precio
     * @return boolean
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

    /**
     * Llama al BO para realizar un registro masivo de empleados (Admin o
     * Meseros).
     *
     * @param empleados Lista de entidades a registrar.
     * @throws Exception Si ocurre un error en la persistencia o encriptación.
     */
    public void registrarEmpleadosMasivo(List<Empleado> empleados) throws Exception {
        this.empleadoBO.registrarEmpleados(empleados);
    }

    /**
     * Llama al BO para validar el acceso mediante el código.
     *
     * @param codigo El código ingresado en el campo amarillo de la ventana.
     * @return Un objeto DTO con la información del empleado y su Rol.
     * @throws Exception Si el código es incorrecto o el usuario no existe.
     */
    public EmpleadoRegistroDTO validarIngresoPorCodigo(String codigo) throws Exception {
        return this.empleadoBO.loginPorCodigo(codigo);
    }

    /**
     * Mostrar las ventana de menu admin.
     */
    public void abrirVentanaMenuAdmin() {
        if (ventana_inicio_sesion != null) {
            ventana_inicio_sesion.dispose();
        }
        if (ventana_menu_admin == null) {
            ventana_menu_admin = new VentanaMenuAdmin(this);
        }

        ventana_menu_admin.setVisible(true);
        ventana_menu_admin.toFront();

    }

    /**
     * Mostrar las ventana de menu mesero.
     */
    public void abrirVentanaMenuMesero() {
        if (ventana_inicio_sesion != null) {
            ventana_inicio_sesion.dispose();
        }
        if (ventana_menu_mesero == null) {
            ventana_menu_mesero = new VentanaMenuMesero(this);
        }

        ventana_menu_mesero.setVisible(true);
        ventana_menu_mesero.toFront();

    }

    /**
     * Oculta la pantalla con la que estamos trabajando, para despues
     * regresarnos a la ventana de incio de sesion.
     *
     * @param frame
     */
    public void regresarInicioSesion(JFrame frame) {
        frame.setVisible(false);

        if (ventana_inicio_sesion == null) {
            ventana_inicio_sesion = new VentanaInicioSesion(this);
        }
        ventana_inicio_sesion.setVisible(true);
        ventana_inicio_sesion.toFront();
    }

    /**
     * Valida la estrcutura del correo del cliente.
     *
     * @param correo
     * @return boolean
     */
    public boolean validarCorreo(String correo) {
        return validar.validarCorreo(correo);
    }

    /**
     * Valida la estructura de cualquier apellido.
     *
     * @param apellido
     * @return boolean
     */
    public boolean validarApellidos(String apellido) {
        return validar.validarApellidos(apellido);
    }

    /**
     * Valida la estructura del telefono.
     *
     * @param telefono
     * @return boolean
     */
    public boolean validarTelefono(String telefono) {
        return validar.validarTelefono(telefono);
    }

    public List<ReporteComandaDTO> generarReporteComandas(LocalDateTime ini, LocalDateTime fin) {
        return reporteBO.generarReporteComandas(ini, fin);
    }

    public List<ReporteClienteDTO> generarReporteClientes(String nombre, int visitas) {
        return reporteBO.obtenerReporteClientes(nombre, visitas);
    }

    public void abrirReportes() {
        if (ventana_menu_admin != null) {
            ventana_menu_admin.setVisible(false);
        }
        if (ventana_reportes == null) {
            ventana_reportes = new VentanaReportes(this);
        }
        ventana_reportes.setVisible(true);
        ventana_reportes.toFront();
    }

}
