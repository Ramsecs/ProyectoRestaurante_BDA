/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package objetosNegocioRestaurante;


import dtosDelRestaurante.ProductoDTO;
import entidadesRestaurante.ProductoIngrediente;
import excepcionesRestaurante.NegocioException;
import java.util.List;

/**
 *
 * @author RAMSES
 */
public interface IProductoBO {
    
    public List<ProductoDTO> listarProductos() throws NegocioException;
    
    public boolean registrarProducto(ProductoDTO productoDTO, List<ProductoIngrediente> detalles) throws NegocioException;
    
    public void actualizarNombre(Long id, String nuevoNombre) throws NegocioException;
    
    public void actualizarPrecio(Long id, Double nuevoPrecio) throws NegocioException;
    
}
