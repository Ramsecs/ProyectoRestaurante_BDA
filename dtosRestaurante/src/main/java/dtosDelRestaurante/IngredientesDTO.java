/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosDelRestaurante;

import enumEntidades.UnidadMedida;

/**
 *
 * @author DANIEL
 */
public class IngredientesDTO {
    
    /**
     * Nombre del ingrediente que se desea registrar. 
     */
    private String nombre;
    
    /**
     * Cantidad inicial disponible que se ingresará al inventario. 
     */
    private Integer stock;
    
    /**
     * Unidad de medida (Enum) bajo la cual se gestionará el ingrediente. 
     */
    private UnidadMedida unidad_Medida;

    /**
     * Constructor por defecto.
     * Permite la creación de un objeto vacío para ser llenado mediante métodos setter.
     */
    public IngredientesDTO() {
    }

    /**
     * Constructor parametrizado para inicializar un nuevo ingrediente.
     * 
     * @param nombre Nombre del nuevo ingrediente.
     * @param stock Cantidad inicial de existencias.
     * @param unidad_Medida Unidad de medida seleccionada (ej. GRAMOS, PIEZA).
     */
    public IngredientesDTO(String nombre, Integer stock, UnidadMedida unidad_Medida) {
        this.nombre = nombre;
        this.stock = stock;
        this.unidad_Medida = unidad_Medida;
    }

    /**
     * @return El nombre del ingrediente. 
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre Define el nombre del ingrediente. 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return La cantidad de stock actual. 
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * @param stock Define la cantidad de stock inicial. 
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * @return La unidad de medida asociada.
     */
    public UnidadMedida getUnidad_Medida() {
        return unidad_Medida;
    }

    /**
     * @param unidad_Medida Define la unidad de medida para este ingrediente. 
     */
    public void setUnidad_Medida(UnidadMedida unidad_Medida) {
        this.unidad_Medida = unidad_Medida;
    }

    /**
     * Genera una representación textual del DTO.
     * Útil para logs de auditoría o depuración durante el proceso de registro.
     * 
     * @return Cadena con los valores actuales de nombre, stock y unidad de medida.
     */
    @Override
    public String toString() {
        return "IngredientesDTO{" + "nombre=" + nombre + ", stock=" + stock + ", unidad_Medida=" + unidad_Medida + '}';
    }
}