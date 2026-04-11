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
    private String nombre;
    private Integer stock;
    private UnidadMedida unidad_Medida;

    public IngredientesDTO() {
    }

    public IngredientesDTO(String nombre, Integer stock, UnidadMedida unidad_Medida) {
        this.nombre = nombre;
        this.stock = stock;
        this.unidad_Medida = unidad_Medida;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public UnidadMedida getUnidad_Medida() {
        return unidad_Medida;
    }

    public void setUnidad_Medida(UnidadMedida unidad_Medida) {
        this.unidad_Medida = unidad_Medida;
    }

    @Override
    public String toString() {
        return "IngredientesDTO{" + "nombre=" + nombre + ", stock=" + stock + ", unidad_Medida=" + unidad_Medida + '}';
    }
    
    
}
