/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosDelRestaurante;

/**
 *Esta clase es para poder mostrar los productos a la hora de agregarlos a una comanda
 * @author josma
 */
public class ProductoComandaDTO {
    /**
     * id del producto.
     */
    private Long id;
    /**
     * Nombre del producto.
     */
    private String nombre;
    /**
     * Precio unitario del producto.
     */
    private Double precio;
    /**
     * Tipo del producto.
     */
    private String tipo; 
    
    /**
     * Constructor sin id
     * @param nombre
     * @param precio
     * @param tipo 
     */
    public ProductoComandaDTO(String nombre, Double precio, String tipo) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
    }
    
    /**
     * Constructor con id
     * @param id
     * @param nombre
     * @param precio
     * @param tipo 
     */
    public ProductoComandaDTO(Long id, String nombre, Double precio, String tipo) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo = tipo;
    }
    /**
     * Constructtor vacio.
     */
    public ProductoComandaDTO() {
    }
    
    
    
    /**
     * Regresa el valor del id del producto
     * @return 
     */

    public Long getId() {
        return id;
    }
    /**
     * Setea el valor del id del producto
     * @param id del producto
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Regresa el nombre del producto
     * @return 
     */
    public String getNombre() {
        return nombre;
    }
    /**
     * Setea el nombre del producto
     * @param nombre 
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
    /**
     * Regresa el precio unitario del producto
     * @return 
     */
    public Double getPrecio() {
        return precio;
    }
    /**
     * Setea el precio unitario del producto
     * @param precio 
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    /**
     * Regresa el tipo de producto que viene de un Enum
     * @return 
     */
    public String getTipo() {
        return tipo;
    }
    /**
     * Setea el tipo de producto
     * @param tipo 
     */
    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
    
    
    
}
