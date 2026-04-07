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
public class ComandaProducto implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "detalles_producto", length = 300, nullable = true)
    private String detalles_producto;
    
    @Column(name = "cant_cada_producto", nullable = false)
    private Integer cant_cada_producto;
    
    @ManyToOne
    @JoinColumn(name = "id_productos", nullable = false)
    private Producto productos_comprados;
    
    @ManyToOne
    @JoinColumn(name = "id_comandas", nullable = false)
    private Comanda comandas;

    public ComandaProducto() {
    }

    public ComandaProducto(String detalles_producto, Integer cant_cada_producto) {
        this.detalles_producto = detalles_producto;
        this.cant_cada_producto = cant_cada_producto;
    }

    public ComandaProducto(Long id, String detalles_producto, Integer cant_cada_producto) {
        this.id = id;
        this.detalles_producto = detalles_producto;
        this.cant_cada_producto = cant_cada_producto;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDetalles_producto() {
        return detalles_producto;
    }

    public void setDetalles_producto(String detalles_producto) {
        this.detalles_producto = detalles_producto;
    }

    public Integer getCant_cada_producto() {
        return cant_cada_producto;
    }

    public void setCant_cada_producto(Integer cant_cada_producto) {
        this.cant_cada_producto = cant_cada_producto;
    }

    public Producto getProductos() {
        return productos_comprados;
    }

    public void setProductos(Producto productos) {
        this.productos_comprados = productos;
    }

    public Comanda getComandas() {
        return comandas;
    }

    public void setComandas(Comanda comandas) {
        this.comandas = comandas;
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
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
        final ComandaProducto other = (ComandaProducto) obj;
        return Objects.equals(this.id, other.id);
    }

    @Override
    public String toString() {
        return "ComandaProducto{" + "id=" + id + ", detalles_producto=" + detalles_producto + ", cant_cada_producto=" + cant_cada_producto + ", productos=" + productos_comprados + ", comandas=" + comandas + '}';
    }
    
    
    
}
