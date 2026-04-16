/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocioRestaurante;

import daosRestaurante.*;
import dtosDelRestaurante.ComandaDTO;
import dtosDelRestaurante.ComandaListaDTO;
import dtosDelRestaurante.ComandaProductoDTO;
import dtosDelRestaurante.DetalleComandaDTO;
import entidadesEnumeradorDTO.EstadoComandaDTO;
import entidadesRestaurante.Comanda;
import entidadesRestaurante.ComandaProducto;
import entidadesRestaurante.*;
import enumEntidades.EstadoComanda;
import excepcionesRestaurante.NegocioException;
import excepcionesRestaurante.PersistenciaException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import validadores.Validaciones;

/**
 * Clase BO donde se hacen construcciones de DTO y validaciones de negocio
 *
 * @author josma
 */
public class ComandaBO implements IComandaBO {

    /**
     * Variable para la BO.
     */
    private static ComandaBO comandaBO;

    /**
     * Variable para la IDAO de comanda.
     */
    private final IComandaDAO comandaDAO;
    /**
     * Variable para la IDAO de mesa.
     */
    private final IMesaDAO mesaDAO;
    /**
     * Variable para la IDAO de cliente.
     */
    private final IClienteDAO clienteDAO;
    /**
     * Variable para la IDAO de mesero.
     */
    private final IMeseroDAO meseroDAO;
    /**
     * Variable para la IDAO de producto.
     */
    private final IProductoDAO productoDAO;
    /**
     * Variable para las validaciones.
     */
    private final Validaciones validar;

    /**
     * Constructor privado.
     */
    private ComandaBO() {
        // Inicializamos todo aquí adentro
        this.comandaDAO = ComandaDAO.getInstanceComandaDAO();
        this.mesaDAO = MesaDAO.getInstanceMesaDAO();
        this.clienteDAO = ClienteDAO.getInstanceClienteDAO();
        this.meseroDAO = MeseroDAO.getInstanceMeseroDAO();
        this.productoDAO = ProductoDAO.getInstanceProductoDAO();
        this.validar = new Validaciones();
    }

    /**
     * Metodo para aplicar el singleton
     *
     * @return
     */
    public static ComandaBO getInstanceComandaBO() {
        if (comandaBO == null) {
            comandaBO = new ComandaBO();
        }

        return comandaBO;
    }

    /**
     * Metodo BO que construye y guarda las comandas
     *
     * @param comandaDTO el DTO de la comanda en bruto
     * @param detalles por producto
     * @throws NegocioException
     */
    @Override
    public void guardarComanda(ComandaDTO comandaDTO, List<ComandaProductoDTO> detalles) throws NegocioException {

        // Validamos que lleguen datos
        if (detalles == null || detalles.isEmpty()) {
            throw new NegocioException("La comanda no tiene productos seleccionados.");
        }

        try {
            // 1. Convertimos DTO a Entidad Comanda (Padre)
            Comanda comanda = new Comanda();
            comanda.setMesa(mesaDAO.buscarPorId(comandaDTO.getIdMesa()));
            comanda.setMesero(meseroDAO.buscarPorId(comandaDTO.getIdMesero()));
            comanda.setCliente(clienteDAO.buscarPorId(comandaDTO.getIdCliente()));
            comanda.setEstado_comanda(comandaDTO.getEstado());
            comanda.setTotal_venta(comandaDTO.getTotal());
            comanda.setFecha_hora_creacion(LocalDateTime.now());

            // 2. Procesamos los detalles (Hijos)
            for (ComandaProductoDTO detalleDTO : detalles) {
                ComandaProducto detalle_entidad = new ComandaProducto();

                // Buscamos el producto real en la BD
                Producto p = productoDAO.buscarPorId(detalleDTO.getId_producto());

                detalle_entidad.setProductos(p);
                detalle_entidad.setCant_cada_producto(detalleDTO.getCantidad());
                detalle_entidad.setDetalles_producto(detalleDTO.getDetalles());

                //Usamos nuestro metodo de ayuda para agregar el producto a la lista
                //de la comanda y decirle al produdcto a que comanda pertenece
                comanda.agregarProducto(detalle_entidad);
            }

            // 3. Guardamos solo la Comanda. 
            // El CascadeType.PERSIST hará que los productos se guarden automáticamente.
            comandaDAO.registrarComanda(comanda);

        } catch (Exception e) {
            throw new NegocioException("Error al registrar en base de datos: " + e.getMessage());
        }
    }

    /**
     * Meotodo del BO donde se construye la lista de comandas abiertas DTO para
     * poder usarla despues
     *
     * @return
     * @throws NegocioException
     */
    @Override
    public List<ComandaListaDTO> mostrarComandasAbiertas() throws NegocioException {
        try {
            List<Comanda> comandas = comandaDAO.obtenerComandasAbiertas(EstadoComanda.ABIERTA);
            List<ComandaListaDTO> comandas_listas = new ArrayList<>();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

            for (Comanda comanda : comandas) {
                ComandaListaDTO dto = new ComandaListaDTO();

                //1. Mapeo de ids
                dto.setId(comanda.getId());
                dto.setId_mesa(comanda.getMesa().getId());

                //2.Construccion del Folio OB-YYYYMMDD-XXX               
                String fecha_formateada = (comanda.getFecha_hora_creacion() != null) ? comanda.getFecha_hora_creacion().format(dtf) : "00000000";
                String digitos_unicos = String.format("%03d", comanda.getId());
                dto.setFolio("OB-" + fecha_formateada + "-" + digitos_unicos);

                //3. Datos del cliente 
                if (comanda.getCliente() != null) {
                    dto.setId_cliente(comanda.getCliente().getId());
                    dto.setNombre_cliente(comanda.getCliente().getNombre());
                    dto.setApellido_cliente_paterno(comanda.getCliente().getApellido_paterno());
                    dto.setApellido_cliente_materno(comanda.getCliente().getApellido_materno());
                } else {
                    dto.setNombre_cliente("Cliente No Frecuente");
                }

                //4. Mapeo de Estado
                if (comanda.getEstado_comanda() != null) {
                    dto.setEstado(EstadoComandaDTO.valueOf(comanda.getEstado_comanda().name()));
                }

                comandas_listas.add(dto);

            }
            return comandas_listas;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error al cargar comandas: " + e.getMessage());
        }
    }

    @Override
    public void actualizarEstadoComanda(Long id, EstadoComandaDTO estado) throws NegocioException {
        try {
            EstadoComanda estado_entidad = EstadoComanda.valueOf(estado.name());

            comandaDAO.actualizarEstadoComanda(id, estado_entidad);
        } catch (Exception e) {
            throw new NegocioException("No se pudo actualizar el estado: " + e.getMessage());
        }
    }

    @Override
    public List<ComandaListaDTO> filtrarComandasAbiertas(String filtro) throws NegocioException {
        try {
            List<Comanda> comanda_entidad = comandaDAO.buscarComandasAbiertasPorCliente(filtro, EstadoComanda.ABIERTA);
            List<ComandaListaDTO> comandas = new ArrayList<>();

            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd");

            for (Comanda comanda : comanda_entidad) {
                ComandaListaDTO comanda_dto = new ComandaListaDTO();

                comanda_dto.setId(comanda.getId());
                comanda_dto.setId_mesa(comanda.getMesa().getId());

                // Folio
                String fecha = (comanda.getFecha_hora_creacion() != null) ? comanda.getFecha_hora_creacion().format(dtf) : "00000000";
                comanda_dto.setFolio("OB-" + fecha + "-" + String.format("%03d", comanda.getId()));

                // Cliente
                if (comanda.getCliente() != null) {
                    comanda_dto.setNombre_cliente(comanda.getCliente().getNombre());
                    comanda_dto.setApellido_cliente_paterno(comanda.getCliente().getApellido_paterno());
                }

                // Estado (Conversión de Enums)
                if (comanda.getEstado_comanda() != null) {
                    comanda_dto.setEstado(EstadoComandaDTO.valueOf(comanda.getEstado_comanda().name()));
                }
                comandas.add(comanda_dto);
            }
            return comandas;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error en la lógica del filtrado: " + e.getMessage());
        }
    }

    @Override
    public void guardarCambiosComanda(Long idComanda, List<DetalleComandaDTO> modificarDTO, List<DetalleComandaDTO> eliminarDTO, List<DetalleComandaDTO> nuevosDTO) throws NegocioException {
        try {
            // --- 1. ELIMINACIÓN ---------------------------
            List<ComandaProducto> lista_eliminar = new ArrayList<>();
            for (DetalleComandaDTO dto : eliminarDTO) {
                ComandaProducto cp = new ComandaProducto();
                cp.setId(dto.getId());
                lista_eliminar.add(cp);
            }

            // --- 2. MODIFICACIÓN ------------------------------
            List<ComandaProducto> lista_modificar = new ArrayList<>();
            for (DetalleComandaDTO dto : modificarDTO) {
                ComandaProducto cp = comandaDAO.buscarComandaProductoPorId(dto.getId());
                if (cp != null) {
                    cp.setCant_cada_producto(dto.getCantidad());
                    // Si la nota está vacía, no pasa nada, se guarda así
                    cp.setDetalles_producto(dto.getNotas());
                    lista_modificar.add(cp);
                }
            }

            // --- 3. PROCESO DE ADICIÓN (Opcional) ---
            List<ComandaProducto> lista_nuevos = new ArrayList<>();
            for (DetalleComandaDTO dto : nuevosDTO) {
                // Validación única: La cantidad debe ser coherente
                if (dto.getCantidad() <= 0) {
                    throw new NegocioException("Cantidad inválida para: " + dto.getNombre_producto());
                }

                ComandaProducto cp = new ComandaProducto();
                Producto p = productoDAO.buscarPorId(dto.getId_producto());

                cp.setProductos_comprados(p);
                cp.setCant_cada_producto(dto.getCantidad());
                cp.setDetalles_producto(dto.getNotas()); // Nota opcional
                lista_nuevos.add(cp);
            }

            // Solo llamar al DAO si realmente hay algo que hacer
            if (!lista_eliminar.isEmpty() || !lista_modificar.isEmpty() || !lista_nuevos.isEmpty()) {
                comandaDAO.actualizarDetallesComanda(idComanda, lista_modificar, lista_eliminar, lista_nuevos);
            }

        } catch (PersistenciaException e) {
            throw new NegocioException("Error de base de datos: " + e.getMessage());
        }
    }

    @Override
    public List<DetalleComandaDTO> consultarDetallesPorComanda(Long id_comanda) throws NegocioException {
        try {
            List<ComandaProducto> entidades = comandaDAO.consultarPorComanda(id_comanda);
            List<DetalleComandaDTO> listaDTO = new ArrayList<>();

            for (ComandaProducto cp : entidades) {
                DetalleComandaDTO dto = new DetalleComandaDTO();
                dto.setId(cp.getId());
                dto.setId_producto(cp.getProductos_comprados().getId());
                dto.setNombre_producto(cp.getProductos_comprados().getNombre());
                dto.setPrecio_unitario(cp.getProductos_comprados().getPrecio());
                dto.setCantidad(cp.getCant_cada_producto());
                dto.setNotas(cp.getDetalles_producto());
                listaDTO.add(dto);
            }
            return listaDTO;
        } catch (PersistenciaException e) {
            throw new NegocioException(e.getMessage());
        }
    }

}
