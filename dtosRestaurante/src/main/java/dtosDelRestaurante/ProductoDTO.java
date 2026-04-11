/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosDelRestaurante;

import entidadesEnumeradorDTO.TipoPlatilloDTO;

/**
 *
 * @author RAMSES
 */
public class ProductoDTO {
    
    private Long id;
    
    private String nombre;
    
    private Double precio;
    
    private TipoPlatilloDTO tipo_platilo;
    
    private String ruta_imagen;

    public ProductoDTO() {
    }

    public ProductoDTO(String nombre, Double precio, TipoPlatilloDTO tipo_platilo, String ruta_imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipo_platilo = tipo_platilo;
        this.ruta_imagen = ruta_imagen;
    }

    public ProductoDTO(Long id, String nombre, Double precio, TipoPlatilloDTO tipo_platilo, String ruta_imagen) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo_platilo = tipo_platilo;
        this.ruta_imagen = ruta_imagen;
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

    public Double getPrecio() {
        return precio;
    }

    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    public TipoPlatilloDTO getTipo_platilo() {
        return tipo_platilo;
    }

    public void setTipo_platilo(TipoPlatilloDTO tipo_platilo) {
        this.tipo_platilo = tipo_platilo;
    }

    public String getRuta_imagen() {
        return ruta_imagen;
    }

    public void setRuta_imagen(String ruta_imagen) {
        this.ruta_imagen = ruta_imagen;
    }

    @Override
    public String toString() {
        return "ProductoDTO{" + "id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", tipo_platilo=" + tipo_platilo + ", ruta_imagen=" + ruta_imagen + '}';
    }

    
    
    
    
}
