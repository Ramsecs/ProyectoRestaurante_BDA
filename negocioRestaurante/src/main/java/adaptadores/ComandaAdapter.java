/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import daosRestaurante.ClienteDAO;
import daosRestaurante.IClienteDAO;
import daosRestaurante.IMesaDAO;
import daosRestaurante.IMeseroDAO;
import daosRestaurante.IProductoDAO;
import daosRestaurante.MesaDAO;
import daosRestaurante.MeseroDAO;
import daosRestaurante.ProductoDAO;
import dtosDelRestaurante.ComandaDTO;
import dtosDelRestaurante.ComandaProductoDTO;
import entidadesRestaurante.Comanda;
import entidadesRestaurante.ComandaProducto;
import entidadesRestaurante.Producto;
import excepcionesRestaurante.NegocioException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RAMSES
 */
public class ComandaAdapter {
    
        private IClienteDAO clienteDAO = ClienteDAO.getInstanceClienteDAO();
        private IMesaDAO mesaDAO = MesaDAO.getInstanceMesaDAO();
        private IMeseroDAO meseroDAO = MeseroDAO.getInstanceMeseroDAO();
        private IProductoDAO productoDAO = ProductoDAO.getInstanceProductoDAO();

        public ComandaAdapter() {
            
        }
        
        
        /**
         * Adapta el dto de Comanda a una clase de dominio
         * tipo comanda para poder trabajar con ella 
         * a la hora de hacer el registro de la comanda o actualizacion.
         * 
         * @param comanda_dto
         * @return
         * @throws NegocioException 
         */
        public Comanda convertirDtoAEntidad(ComandaDTO comanda_dto) throws NegocioException {
        Comanda entidad = new Comanda();
        entidad.setEstado_comanda(comanda_dto.getEstado());
        entidad.setFecha_hora_creacion(comanda_dto.getFechaHora());
        entidad.setTotal_venta(comanda_dto.getTotal());

        try {
            // Buscamos las entidades reales usando los IDs del DTO
            entidad.setCliente(clienteDAO.buscarPorId(comanda_dto.getIdCliente()));
            entidad.setMesa(mesaDAO.buscarPorId(comanda_dto.getIdMesa()));
            entidad.setMesero(meseroDAO.buscarPorId(comanda_dto.getIdMesero()));

            // Convertir la lista de productos
            List<ComandaProducto> detalles = new ArrayList<>();
            for (ComandaProductoDTO det_dto : comanda_dto.getProductos()) {
                ComandaProducto detalle = new ComandaProducto();
                detalle.setCant_cada_producto(det_dto.getCantidad());
                detalle.setDetalles_producto(det_dto.getDetalles());
                
                // Buscamos el producto completo para obtener su receta (ProductoIngrediente)
                Producto producto = productoDAO.buscarPorId(det_dto.getId_producto());
                detalle.setProductos_comprados(producto);
                detalle.setComandas(entidad);
                
                detalles.add(detalle);
            }
            entidad.setLista_productos(detalles);

        } catch (Exception e) {
            throw new NegocioException("Error al vincular datos de la comanda: " + e.getMessage());
        }
        return entidad;
    }
    
}
