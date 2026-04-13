/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daosRestaurante;

import dtosDelRestaurante.ProductoDTO;
import entidadesRestaurante.Producto;
import entidadesRestaurante.ProductoIngrediente;
import enumEntidades.TipoPlatillo;
import excepcionesRestaurante.PersistenciaException;
import java.util.List;

/**
 *
 * @author RAMSES
 */
public interface IProductoDAO {
    
    public List<Producto> listarTodo() throws PersistenciaException;
    
    public boolean registrarProductoConIngredientes(Producto nuevoProducto, List<ProductoIngrediente> detalles) throws PersistenciaException;
    
    public void actualizarNombre(Long id, String nuevoNombre) throws PersistenciaException;
    
    public void actualizarPrecio(Long id, Double nuevoPrecio) throws PersistenciaException;
    
    public List<Producto> consultarPorCategoria(TipoPlatillo tipo) throws PersistenciaException;
    
    public Producto buscarPorId(Long id) throws PersistenciaException;
    
}
