/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import dtosDelRestaurante.IngredienteDTOLista;
import entidadesEnumeradorDTO.UnidadMedidaDTO;
import entidadesRestaurante.Ingrediente;
import enumEntidades.UnidadMedida;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author RAMSES
 */
public class IngredienteAdapter {
    
    public List<Ingrediente> listaIngredientesDTOAListaIngredientesEntidad(List<IngredienteDTOLista> lista){
        
        List<Ingrediente> lista_ingredientes = new ArrayList<>();
        Ingrediente ingrediente;
        
        //Transformamos la lista de DTO ingrediente a una lista de tipo entidad
        for (IngredienteDTOLista ingredienteDTO : lista) {
            ingrediente = new Ingrediente();
            
            ingrediente.setNombre(ingredienteDTO.getNombre());
            ingrediente.setStock(ingredienteDTO.getStock());
            if (ingredienteDTO.getUnidad_medida() == UnidadMedidaDTO.PIEZA) {
                ingrediente.setUnidad_medida(UnidadMedida.PIEZA);
            }
            if (ingredienteDTO.getUnidad_medida() == UnidadMedidaDTO.GRAMOS) {
                ingrediente.setUnidad_medida(UnidadMedida.GRAMOS);
            }
            if (ingredienteDTO.getUnidad_medida() == UnidadMedidaDTO.MILILITROS) {
                ingrediente.setUnidad_medida(UnidadMedida.MILILITROS);
            }
            
            lista_ingredientes.add(ingrediente);
            
        }
        
        return lista_ingredientes;
        
    }
    
    
    public List<IngredienteDTOLista> listaIngredientesEntidadAListaIngredientesDTO(List<Ingrediente> lista){
        
        List<IngredienteDTOLista> lista_ingredientes = new ArrayList<>();
        
        IngredienteDTOLista ingredienteDTO;
        //Transformamos la lista de Entidad ingrediente a una lista de tipo DTO
        for (Ingrediente ingrediente : lista) {
            ingredienteDTO = new IngredienteDTOLista();
            
            ingredienteDTO.setNombre(ingrediente.getNombre());
            ingredienteDTO.setStock(ingrediente.getStock());
            if (ingrediente.getUnidad_medida() == UnidadMedida.PIEZA) {
                ingredienteDTO.setUnidad_medida(UnidadMedidaDTO.PIEZA);
            }
            if (ingrediente.getUnidad_medida() == UnidadMedida.GRAMOS) {
                ingredienteDTO.setUnidad_medida(UnidadMedidaDTO.GRAMOS);
            }
            if (ingrediente.getUnidad_medida() == UnidadMedida.MILILITROS) {
                ingredienteDTO.setUnidad_medida(UnidadMedidaDTO.MILILITROS);
            }
            
            lista_ingredientes.add(ingredienteDTO);
            
        }
        
        return lista_ingredientes;
    }
    
    public IngredienteDTOLista convertirADTO(Ingrediente entidad) {
        IngredienteDTOLista dto = new IngredienteDTOLista();
        dto.setId(entidad.getId());
        dto.setNombre(entidad.getNombre());
        dto.setStock(entidad.getStock());

        // Aquí es donde resuelves el problema del Enum:
        // Pasamos el Enum de la Entidad a String para el DTO
        if (entidad.getUnidad_medida() != null) {
        // Convertimos el nombre del Enum de la entidad a un Enum del DTO
        String nombreEnum = entidad.getUnidad_medida().name(); 
        dto.setUnidad_medida(UnidadMedidaDTO.valueOf(nombreEnum));
    }

        return dto;
    }
    
}
