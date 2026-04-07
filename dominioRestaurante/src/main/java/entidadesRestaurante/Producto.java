/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesRestaurante;

import enumEntidades.TipoPlatillo;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author RAMSES
 */
@Entity
@Table(name = "productos")
public class Producto implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;
    
    @Column(name = "precio", nullable = false)
    private Double precio;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_platillo", nullable = false)
    private TipoPlatillo tipo_platilo;
    
    @Column(name = "ruta_imagen", length = 100, nullable = false)
    private String ruta_imagen;
    
    @OneToMany(mappedBy = "productos", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private List<ProductoIngrediente> lista_ingredientes;
    
    @OneToMany(mappedBy = "productos_comprados", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<ComandaProducto> lista_comandas;

    public Producto() {
    }

    public Producto(String nombre, Double precio, TipoPlatillo tipo_platilo, String ruta_imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipo_platilo = tipo_platilo;
        this.ruta_imagen = ruta_imagen;
    }

    public Producto(Long id, String nombre, Double precio, TipoPlatillo tipo_platilo, String ruta_imagen) {
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

    public TipoPlatillo getTipo_platilo() {
        return tipo_platilo;
    }

    public void setTipo_platilo(TipoPlatillo tipo_platilo) {
        this.tipo_platilo = tipo_platilo;
    }

    public String getRuta_imagen() {
        return ruta_imagen;
    }

    public void setRuta_imagen(String ruta_imagen) {
        this.ruta_imagen = ruta_imagen;
    }

    public List<ProductoIngrediente> getLista_ingredientes() {
        return lista_ingredientes;
    }

    public void setLista_ingredientes(List<ProductoIngrediente> lista_ingredientes) {
        this.lista_ingredientes = lista_ingredientes;
    }

    public List<ComandaProducto> getLista_comandas() {
        return lista_comandas;
    }

    public void setLista_comandas(List<ComandaProducto> lista_comandas) {
        this.lista_comandas = lista_comandas;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
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
        final Producto other = (Producto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", tipo_platilo=" + tipo_platilo + ", ruta_imagen=" + ruta_imagen + '}';
    }
    
    
    
    
    
}
