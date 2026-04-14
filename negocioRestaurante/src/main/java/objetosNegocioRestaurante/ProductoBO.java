/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocioRestaurante;

import adaptadores.ProductoAdapter;
import daosRestaurante.IProductoDAO;
import daosRestaurante.ProductoDAO;
import dtosDelRestaurante.ProductoComandaDTO;
import dtosDelRestaurante.ProductoDTO;
import entidadesEnumeradorDTO.TipoPlatilloDTO;
import entidadesRestaurante.Producto;
import entidadesRestaurante.ProductoIngrediente;
import enumEntidades.TipoPlatillo;
import excepcionesRestaurante.NegocioException;
import excepcionesRestaurante.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import validadores.Validaciones;

/**
 *
 * @author RAMSES
 */
public class ProductoBO implements IProductoBO {

    //Singleton
    private static ProductoBO productoBO;

    private IProductoDAO productoDAO = ProductoDAO.getInstanceProductoDAO();
    private Validaciones validar = new Validaciones();

    //Constructor privado
    private ProductoBO() {

    }

    /**
     * Obtener la instancia de ProductoBO.
     * 
     * @return ProductoBO.
     */
    public static ProductoBO getInstanceProductoBO() {
        if (productoBO == null) {
            productoBO = new ProductoBO();
        }

        return productoBO;
    }

    /**
     * Lista la lista de productos que hay en la base de datos.
     *
     * @return List.
     * @throws NegocioException.
     */
    @Override
    public List<ProductoDTO> listarProductos() throws NegocioException {

        try {
            List<Producto> productosEntidad = productoDAO.listarTodo();
            List<ProductoDTO> productosDTO = new ArrayList<>();

            for (Producto p : productosEntidad) {
                ProductoDTO dto = new ProductoDTO();
                dto.setId(p.getId());
                dto.setNombre(p.getNombre());
                dto.setPrecio(p.getPrecio());
                dto.setRuta_imagen(p.getRuta_imagen());

                // --- CONVERSIÓN DE ENUM ---
                // p.getTipo_platilo() devuelve el Enum de Entidad
                // .name() obtiene el String (ej: "PLATILLO")
                // TipoPlatilloDTO.valueOf() busca ese String en el Enum del DTO
                if (p.getTipo_platilo() != null) {
                    String nombreEnum = p.getTipo_platilo().name();
                    dto.setTipo_platilo(TipoPlatilloDTO.valueOf(nombreEnum));
                }
                // --------------------------

                productosDTO.add(dto);
            }
            return productosDTO;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Error al mapear productos: " + ex.getMessage());
        }

    }

    /**
     * Mediante este metodo se registra el producto con la lista de detalles que
     * son los ingredientes que tiene el producto.
     *
     * @param productoDTO.
     * @param detalles.
     * @return boolean.
     * @throws NegocioException.
     */
    @Override
    public boolean registrarProducto(ProductoDTO productoDTO, List<ProductoIngrediente> detalles) throws NegocioException {
        ProductoAdapter adapter_producto = new ProductoAdapter();

        if (detalles.isEmpty() || detalles == null) {
            throw new NegocioException("La lista de ingredientes esta vacia.");
        }
        if (!validar.validarNombres(productoDTO.getNombre())) {
            throw new NegocioException("El nombre del producto no es valido.");
        }
        if (!validar.validarPrecio(productoDTO.getPrecio() + "")) {
            throw new NegocioException("El precio que el usuario ingreso no es valido");
        }

        Producto producto = adapter_producto.DTOAEntidadProducto(productoDTO);

        try {

            return productoDAO.registrarProductoConIngredientes(producto, detalles);

        } catch (PersistenciaException ex) {

            throw new NegocioException("Hubo un error al querer hacer el registro en producto DAO: " + ex.getMessage());
        }

    }

    /**
     * Actualizar el nombre de un producto usando el id 
     * para encotrar el producto a actualizar.
     * 
     * @param id.
     * @param nuevoNombre.
     * @throws NegocioException.
     */
    @Override
    public void actualizarNombre(Long id, String nuevoNombre) throws NegocioException {
        // 1. Validaciones de Negocio
        if (id == null || id <= 0) {
            throw new NegocioException("ID de producto no válido.");
        }

        if (nuevoNombre == null || nuevoNombre.trim().isEmpty()) {
            throw new NegocioException("El nombre no puede estar vacío.");
        }

        try {
            // 2. Llamada al DAO para persistir el cambio
            productoDAO.actualizarNombre(id, nuevoNombre.trim());
        } catch (PersistenciaException e) {
            // 3. Relanzar como NegocioException para que la Pantalla lo capture
            throw new NegocioException("No se pudo actualizar el nombre: " + e.getMessage());
        }
    }

    /**
     * Actualizar el precio de un producto usando el id 
     * para encotrar el producto a actualizar.
     * 
     * @param id.
     * @param nuevoPrecio.
     * @throws NegocioException.
     */
    @Override
    public void actualizarPrecio(Long id, Double nuevoPrecio) throws NegocioException {
        // 1. Validaciones de Negocio
        if (id == null || id <= 0) {
            throw new NegocioException("ID de producto no válido.");
        }

        if (nuevoPrecio == null || nuevoPrecio <= 0) {
            throw new NegocioException("El precio debe ser un valor numérico mayor a cero.");
        }

        try {
            // 2. Llamada al DAO
            productoDAO.actualizarPrecio(id, nuevoPrecio);
        } catch (PersistenciaException e) {
            throw new NegocioException("No se pudo actualizar el precio: " + e.getMessage());
        }
    }

    /**
     * Metodo en BO para hacer validación y asi poder llamar al metodo de la DAO
     * para mostrar en el apartado de comandas los productos por categoria.
     *
     * @param tipoDTO.
     * @return List.
     * @throws NegocioException.
     */
    @Override
    public List<ProductoComandaDTO> listarProductosPorCategoria(TipoPlatilloDTO tipoDTO) throws NegocioException {
        if (tipoDTO == null) {
            throw new NegocioException("La categoría de busqueda no puede ser nula");
        }

        try {
            // 2. Convertir a DTO a Entidad (esto puede marcar error por un error de dedo en producto
            //pero no es mi modulo asi que no le muevo)
            TipoPlatillo tipo_entidad = TipoPlatillo.valueOf(tipoDTO.name());

            // 3. Llamada al DAO
            List<Producto> productos_dominio = productoDAO.consultarPorCategoria(tipo_entidad);

            // 4. Transformación a DTOs
            List<ProductoComandaDTO> listaDTO = new ArrayList<>();

            if (productos_dominio != null && !productos_dominio.isEmpty()) {
                for (Producto producto : productos_dominio) {
                    // Usamos el constructor vacio
                    ProductoComandaDTO productoDTO = new ProductoComandaDTO();
                    productoDTO.setId(producto.getId());
                    productoDTO.setNombre(producto.getNombre());
                    productoDTO.setPrecio(producto.getPrecio());

                    listaDTO.add(productoDTO);
                }
            }

            return listaDTO;

        } catch (PersistenciaException e) {
            // Si el DAO falló, lo envolvemos en una excepción de Negocio para el Coordinador
            throw new NegocioException("Error al obtener productos desde la base de datos: " + e.getMessage());
        }

    }
}
