/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocioRestaurante;

import daosRestaurante.*;
import dtosDelRestaurante.ComandaDTO;
import dtosDelRestaurante.ComandaProductoDTO;
import entidadesRestaurante.Comanda;
import entidadesRestaurante.ComandaProducto;
import entidadesRestaurante.*;
import excepcionesRestaurante.NegocioException;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;
import validadores.Validaciones;

/**
 *
 * @author josma
 */
public class ComandaBO implements IComandaBO {
//Singleton

    private static ComandaBO comandaBO;

    // DAOs como variables de instancia
    private final IComandaDAO comandaDAO;
    private final IMesaDAO mesaDAO;
    private final IClienteDAO clienteDAO;
    private final IMeseroDAO meseroDAO;
    private final IProductoDAO productoDAO;
    private final Validaciones validar;

    private ComandaBO() {
        // Inicializamos todo aquí adentro
        this.comandaDAO = ComandaDAO.getInstanceComandaDAO();
        this.mesaDAO = MesaDAO.getInstanceMesaDAO();
        this.clienteDAO = ClienteDAO.getInstanceClienteDAO();
        this.meseroDAO = MeseroDAO.getInstanceMeseroDAO();
        this.productoDAO = ProductoDAO.getInstanceProductoDAO();
        this.validar = new Validaciones();
    }

    //Metodo para el singleton
    public static ComandaBO getInstanceComandaBO() {
        if (comandaBO == null) {
            comandaBO = new ComandaBO();
        }

        return comandaBO;
    }

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

}
