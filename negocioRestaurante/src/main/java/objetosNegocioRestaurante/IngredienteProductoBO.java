/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocioRestaurante;

import adaptadores.IngredienteAdapter;
import daosRestaurante.IIngredienteProductoDAO;
import daosRestaurante.IngredienteProductoDAO;
import dtosDelRestaurante.IngredienteDTOLista;
import dtosDelRestaurante.ProductoIngredienteDTO;
import entidadesEnumeradorDTO.UnidadMedidaDTO;
import entidadesRestaurante.Ingrediente;
import entidadesRestaurante.ProductoIngrediente;
import excepcionesRestaurante.NegocioException;
import excepcionesRestaurante.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import validadores.Validaciones;

/**
 *
 * @author RAMSES
 */
public class IngredienteProductoBO implements IIngredienteProductoBO{
    
    
    //Singleton
    private static IngredienteProductoBO ingredienteProductoBO;
    
    private IngredienteAdapter ingrediente_adapter;

    private IIngredienteProductoDAO ingredienteProductoDAO = IngredienteProductoDAO.getInstanceIngredienteProductoDAO();

    //Constructor privado
    private IngredienteProductoBO() {
        this.ingrediente_adapter = new IngredienteAdapter();
    }

    /**
     * Obtener instancia de IngredienteProductoBO.
     * 
     * @return IngredienteProductoBO.
     */
    public static IngredienteProductoBO getInstanceIngredienteBO() {
        if (ingredienteProductoBO == null) {
            ingredienteProductoBO = new IngredienteProductoBO();
        }

        return ingredienteProductoBO;
    }
    
    /**
     * Mediante el uso de este metodo recuperamos la lista de ingredientes en el formato de una lista
     * de IngredientesDTOLista.
     * 
     * @return List.
     * @throws NegocioException.
     */
    @Override
    public List<IngredienteDTOLista> recuperarListaIngredientes() throws NegocioException{
        List<Ingrediente> entidades = new ArrayList<>();
        
        try {
            entidades = ingredienteProductoDAO.listarTodo();
        } catch (PersistenciaException ex) {
            throw new NegocioException("Hubo un error al conseguir la lista de los ingredientes. "+ex.getMessage());
        }
        
        List<IngredienteDTOLista> dtos = new ArrayList<>();

        for (Ingrediente i : entidades) {
            dtos.add(ingrediente_adapter.convertirADTO(i));
        }

        return dtos;
    }
    
    /**
     * Este metodo obtiene la lista de ingredientes del producto y la convierte en una lista de ingredientes 
     * tipo DTO.
     * 
     * @param idProducto.
     * @return List.
     * @throws NegocioException.
     */
    @Override
    public List<IngredienteDTOLista> listarDetallesProducto(Long idProducto) throws NegocioException {
        try {
            List<ProductoIngrediente> entidades = ingredienteProductoDAO.buscarPorProducto(idProducto);
            List<IngredienteDTOLista> dtos = new ArrayList<>();

            for (ProductoIngrediente pi : entidades) {
                // 1. Convertimos el Enum de la entidad al Enum del DTO usando name() y valueOf()
                enumEntidades.UnidadMedida unidadEntidad = pi.getIngredientes().getUnidad_medida();
                UnidadMedidaDTO unidadDto = UnidadMedidaDTO.valueOf(unidadEntidad.name());

                // 2. Creamos el DTO con los datos correctos
                IngredienteDTOLista dto = new IngredienteDTOLista(
                    pi.getIngredientes().getId(),
                    pi.getIngredientes().getNombre(),
                    pi.getCantidad_ingrediente(), // Cantidad específica para este producto
                    unidadDto// La unidad ya convertida
                );

                dtos.add(dto);
            }
            return dtos;
        } catch (PersistenciaException e) {
            throw new NegocioException("Error en negocio al procesar detalles: " + e.getMessage());
        }
    }
    
    
}
