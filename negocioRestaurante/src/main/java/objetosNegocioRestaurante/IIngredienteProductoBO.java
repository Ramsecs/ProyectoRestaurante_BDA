/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package objetosNegocioRestaurante;

import dtosDelRestaurante.IngredienteDTOLista;
import dtosDelRestaurante.ProductoIngredienteDTO;
import excepcionesRestaurante.NegocioException;
import java.util.List;

/**
 *
 * @author RAMSES
 */
public interface IIngredienteProductoBO {
    
    public List<IngredienteDTOLista> recuperarListaIngredientes() throws NegocioException;
    
    public List<IngredienteDTOLista> listarDetallesProducto(Long idProducto) throws NegocioException;
    
}
