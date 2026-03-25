/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesRestaurante;

import enumEntidades.UnidadMedida;
import javax.persistence.*;

/**
 *
 * @author RAMSES
 */
@Entity
@Table(name = "ingredientes")
public class Ingrediente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre_ingrediente", length = 100, nullable = false)
    private String nombre;
    
    @Column(name = "stock", nullable = false)
    private Integer stock;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_medida", nullable = false)
    private UnidadMedida unidad_medida;

    public Ingrediente() {
    }

    public Ingrediente(String nombre, Integer stock, UnidadMedida unidad_medida) {
        this.nombre = nombre;
        this.stock = stock;
        this.unidad_medida = unidad_medida;
    }

    public Ingrediente(Long id, String nombre, Integer stock, UnidadMedida unidad_medida) {
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

    public UnidadMedida getUnidad_medida() {
        return unidad_medida;
    }

    public void setUnidad_medida(UnidadMedida unidad_medida) {
        this.unidad_medida = unidad_medida;
    }

    @Override
    public String toString() {
        return "Ingrediente{" + "id=" + id + ", nombre=" + nombre + ", stock=" + stock + ", unidad_medida=" + unidad_medida + '}';
    }
    
    
    
    
}
