/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesRestaurante;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author RAMSES
 */
@Entity
public class ProductoIngrediente implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "cantidad_ingrediente")
    private Integer cantidad_ingrediente;
    
    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto productos;
    
    @ManyToOne
    @JoinColumn(name = "id_ingrediente", nullable = false)
    private Ingrediente ingredientes;

    public ProductoIngrediente() {
    }

    public ProductoIngrediente(Integer cantidad_ingrediente) {
        this.cantidad_ingrediente = cantidad_ingrediente;
    }

    public ProductoIngrediente(Long id, Integer cantidad_ingrediente) {
        this.id = id;
        this.cantidad_ingrediente = cantidad_ingrediente;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Integer getCantidad_ingrediente() {
        return cantidad_ingrediente;
    }

    public void setCantidad_ingrediente(Integer cantidad_ingrediente) {
        this.cantidad_ingrediente = cantidad_ingrediente;
    }

    public Producto getProductos() {
        return productos;
    }

    public void setProductos(Producto productos) {
        this.productos = productos;
    }

    public Ingrediente getIngredientes() {
        return ingredientes;
    }

    public void setIngredientes(Ingrediente ingredientes) {
        this.ingredientes = ingredientes;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final ProductoIngrediente other = (ProductoIngrediente) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "ProductoIngrediente{" + "id=" + id + ", cantidad_ingrediente=" + cantidad_ingrediente + ", productos=" + productos + ", ingredientes=" + ingredientes + '}';
    }

    
    
    
    
}
