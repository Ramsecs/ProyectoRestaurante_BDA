/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daosRestaurante;

import entidadesRestaurante.Ingrediente;
import entidadesRestaurante.ProductoIngrediente;
import excepcionesRestaurante.PersistenciaException;
import java.util.List;

/**
 *
 * @author RAMSES
 */
public interface IIngredienteProductoDAO {
    
    public List<Ingrediente> listarTodo() throws PersistenciaException;
    
    public List<ProductoIngrediente> buscarPorProducto(Long idProducto) throws PersistenciaException;
    
}
