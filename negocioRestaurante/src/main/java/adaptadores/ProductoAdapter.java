/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import dtosDelRestaurante.ProductoDTO;
import entidadesEnumeradorDTO.TipoPlatilloDTO;
import entidadesRestaurante.Producto;
import enumEntidades.TipoPlatillo;

/**
 *
 * @author RAMSES
 */
public class ProductoAdapter {
    
    public Producto DTOAEntidadProducto(ProductoDTO productoDTO){
        if (productoDTO == null) {
            return null;
        }
        
        Producto producto = new Producto();
        producto.setNombre(productoDTO.getNombre());
        producto.setPrecio(productoDTO.getPrecio());
        producto.setRuta_imagen(productoDTO.getRuta_imagen());
        
        if (productoDTO.getTipo_platilo() == TipoPlatilloDTO.BEBIDA) {
            producto.setTipo_platilo(TipoPlatillo.BEBIDA);
        }
        if (productoDTO.getTipo_platilo() == TipoPlatilloDTO.PLATILLO) {
            producto.setTipo_platilo(TipoPlatillo.PLATILLO);
        }
        if (productoDTO.getTipo_platilo() == TipoPlatilloDTO.POSTRE) {
            producto.setTipo_platilo(TipoPlatillo.POSTRE);
        }
        
        return producto;
        
    }
    
    
    public ProductoDTO EntidadADTOProducto(Producto producto){
        if (producto == null) {
            return null;
        }
        
        ProductoDTO productoDTO = new ProductoDTO();
        productoDTO.setNombre(producto.getNombre());
        productoDTO.setPrecio(producto.getPrecio());
        productoDTO.setRuta_imagen(producto.getRuta_imagen());
        
        if (producto.getTipo_platilo() == TipoPlatillo.BEBIDA) {
            productoDTO.setTipo_platilo(TipoPlatilloDTO.BEBIDA);
        }
        if (producto.getTipo_platilo() == TipoPlatillo.PLATILLO) {
            productoDTO.setTipo_platilo(TipoPlatilloDTO.PLATILLO);
        }
        if (producto.getTipo_platilo() == TipoPlatillo.POSTRE) {
            productoDTO.setTipo_platilo(TipoPlatilloDTO.POSTRE);
        }
        
        return productoDTO;
        
    }
    
}
