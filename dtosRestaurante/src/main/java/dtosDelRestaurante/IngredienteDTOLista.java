/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosDelRestaurante;

import entidadesEnumeradorDTO.UnidadMedidaDTO;

/**
 *
 * @author RAMSES
 */
public class IngredienteDTOLista {
    
    private Long id;

    private String nombre;
    
    private Integer stock;
    
    private UnidadMedidaDTO unidad_medida;

    public IngredienteDTOLista() {
    }

    public IngredienteDTOLista(String nombre, Integer stock, UnidadMedidaDTO unidad_medida) {
        this.nombre = nombre;
        this.stock = stock;
        this.unidad_medida = unidad_medida;
    }

    public IngredienteDTOLista(Long id, String nombre, Integer stock, UnidadMedidaDTO unidad_medida) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.unidad_medida = unidad_medida;
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

    public Integer getStock() {
        return stock;
    }

    public void setStock(Integer stock) {
        this.stock = stock;
    }

    public UnidadMedidaDTO getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(UnidadMedidaDTO unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    @Override
    public String toString() {
        return "IngredienteDTOLista{" + "id=" + id + ", nombre=" + nombre + ", stock=" + stock + ", unidad_medida=" + unidad_medida + '}';
    }

    
    
    
    
}
