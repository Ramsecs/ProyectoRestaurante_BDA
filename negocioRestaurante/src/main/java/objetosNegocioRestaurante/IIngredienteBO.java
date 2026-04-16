/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package objetosNegocioRestaurante;

import dtosDelRestaurante.IngredienteBusquedaDTO;
import dtosDelRestaurante.IngredientesDTO;
import entidadesRestaurante.Comanda;
import excepcionesRestaurante.NegocioException;
import java.util.List;

/**
 *
 * @author DANIEL
 */
public interface IIngredienteBO {
    
    public boolean registrarIngredientes(IngredientesDTO ingrediente) throws NegocioException;
    public List<IngredienteBusquedaDTO> buscarIngredientes(String filtro) throws NegocioException;
    public void actualizarStock(IngredienteBusquedaDTO ingredienteDTO) throws NegocioException;
    public boolean procesarStockDeComanda(Comanda comanda);
}
