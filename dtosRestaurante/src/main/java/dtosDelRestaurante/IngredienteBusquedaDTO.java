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
    
    /** 
     * Identificador único del ingrediente en la base de datos. 
     */
    private Long id;
    
    /**
     * Nombre descriptivo del ingrediente. 
     */
    private String nombre;
    
    /**
     * Cantidad disponible actual en el inventario. 
     */
    private Integer stock;
    
    /**
     * Unidad de medida (Enum) asociada al ingrediente. 
     */
    private UnidadMedida unidad_medida;

    /**
     * Constructor por defecto.
     * Necesario para frameworks de mapeo y serialización.
     */
    public IngredienteBusquedaDTO() {
    }

    /**
     * Constructor completo para inicializar todos los campos.
     * Útil cuando se recuperan datos existentes con ID desde la base de datos.
     * 
     * @param id Identificador único.
     * @param stock Cantidad en inventario.
     * @param nombre Nombre del ingrediente.
     * @param unidad_medida Tipo de unidad de medida.
     */
    public IngredienteBusquedaDTO(Long id, Integer stock, String nombre, UnidadMedida unidad_medida) {
        this.id = id;
        this.stock = stock;
        this.nombre = nombre;
        this.unidad_medida = unidad_medida;
    }

    /**
     * Constructor sin ID.
     * Útil para búsquedas o transferencias donde el ID no es el foco principal
     * o para representar datos antes de ser persistidos.
     * 
     * @param nombre Nombre del ingrediente.
     * @param stock Cantidad inicial o filtrada.
     * @param Unidad_medida Tipo de unidad de medida.
     */
    public IngredienteBusquedaDTO(String nombre, Integer stock, UnidadMedida Unidad_medida) {
        this.nombre = nombre;
        this.stock = stock;
        this.unidad_medida = Unidad_medida;
    }

    /**
     * @return La cantidad disponible en stock. 
     */
    public Integer getStock() {
        return stock;
    }

    /**
     * @param stock Nueva cantidad disponible en stock.
     */
    public void setStock(Integer stock) {
        this.stock = stock;
    }

    /**
     * @return El identificador único del ingrediente. 
     */
    public Long getId() {
        return id;
    }

    /** 
     * @param id Nuevo identificador único. 
     */
    public void setId(Long id) {
        this.id = id;
    }

    /** 
     * @return El nombre del ingrediente. 
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre Nuevo nombre del ingrediente. 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /** 
     * @return La unidad de medida del ingrediente. 
     */
    public UnidadMedida getUnidad_medida() {
        return unidad_medida;
    }

    /** 
     * @param Unidad_medida Nueva unidad de medida. 
     */
    public void setUnidad_medida(UnidadMedida Unidad_medida) {
        this.unidad_medida = Unidad_medida;
    }

    /**
     * Devuelve una representación en cadena del objeto.
     * Principalmente utilizado para depuración (debugging).
     * * @return String con los valores de los atributos id, nombre y unidad_medida.
     */
    @Override
    public String toString() {
        return "IngredienteBusquedaDTO{" + "id=" + id + ", nombre=" + nombre + ", Unidad_medida=" + unidad_medida + '}';
    }
}