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
public class IngredienteBusquedaDTO {
    private Long id;
    private String nombre;
    private Integer stock;
    private UnidadMedida unidad_medida;

    public IngredienteBusquedaDTO() {
    }

    public IngredienteBusquedaDTO(Long id,Integer stock, String nombre, UnidadMedida unidad_medida) {
        this.id = id;
        this.stock = stock;
        this.nombre = nombre;
        this.unidad_medida = unidad_medida;
    }

    public IngredienteBusquedaDTO(String nombre, Integer stock, UnidadMedida Unidad_medida) {
        this.nombre = nombre;
        this.stock = stock;
        this.unidad_medida = Unidad_medida;
    }

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    
    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public UnidadMedida getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(UnidadMedida Unidad_medida) {
        this.unidad_medida = Unidad_medida;
    }

    @Override
    public String toString() {
        return "IngredienteBusquedaDTO{" + "id=" + id + ", nombre=" + nombre + ", Unidad_medida=" + unidad_medida + '}';
    }

    
}
