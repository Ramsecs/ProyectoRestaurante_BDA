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
public class ComandaProducto implements Serializable {

    /**
     * Identificador único del registro de detalle.
     * Generado automáticamente mediante una estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Notas o especificaciones adicionales sobre el producto solicitado.
     * Ejemplo: "Sin cebolla", "Término medio", "Coke con hielo".
     * Campo opcional con longitud máxima de 300 caracteres.
     */
    @Column(name = "detalles_producto", length = 300, nullable = true)
    private String detalles_producto;

    /**
     * Cantidad solicitada de este producto específico dentro de la comanda.
     */
    @Column(name = "cant_cada_producto", nullable = false)
    private Integer cant_cada_producto;

    /**
     * El producto específico que ha sido comprado.
     * Relación de muchos a uno con la entidad {@link Producto}.
     */
    @ManyToOne
    @JoinColumn(name = "id_productos", nullable = false)
    private Producto productos_comprados;

    /**
     * La comanda a la cual pertenece este detalle de producto.
     * Relación de muchos a uno con la entidad {@link Comanda}.
     */
    @ManyToOne
    @JoinColumn(name = "id_comandas", nullable = false)
    private Comanda comandas;

    /**
     * Constructor por defecto requerido por JPA para la gestión de entidades.
     */
    public ComandaProducto() {
    }

    /**
     * Constructor para crear un nuevo detalle de producto con información operativa.
     * 
     * @param detalles_producto Especificaciones adicionales del pedido.
     * @param cant_cada_producto Cantidad de unidades solicitadas.
     */
    public ComandaProducto(String detalles_producto, Integer cant_cada_producto) {
        this.detalles_producto = detalles_producto;
        this.cant_cada_producto = cant_cada_producto;
    }

    /**
     * Constructor para inicializar un detalle con un ID específico.
     *
     * @param id Identificador único.
     * @param detalles_producto Especificaciones adicionales del pedido.
     * @param cant_cada_producto Cantidad de unidades solicitadas.
     */
    public ComandaProducto(Long id, String detalles_producto, Integer cant_cada_producto) {
        this.id = id;
        this.detalles_producto = detalles_producto;
        this.cant_cada_producto = cant_cada_producto;
    }

    // --- Métodos de Acceso (Getters y Setters) ---

    /**
     * @return El identificador del registro de detalle. 
     */
    public Long getId() { return id; }

    /** 
     * @param id El nuevo ID a asignar. 
     */
    public void setId(Long id) { this.id = id; }

    /** 
     * @return Las especificaciones o detalles del producto solicitado. 
     */
    public String getDetalles_producto() { return detalles_producto; }

    /**
     * @param detalles_producto Los detalles a asignar. 
     */
    public void setDetalles_producto(String detalles_producto) { this.detalles_producto = detalles_producto; }

    /**
     * @return La cantidad de productos en este renglón de la comanda. 
     */
    public Integer getCant_cada_producto() { return cant_cada_producto; }

    /** 
     * @param cant_cada_producto La cantidad a establecer. 
     */
    public void setCant_cada_producto(Integer cant_cada_producto) { this.cant_cada_producto = cant_cada_producto; }

    /**
     * @return El objeto Producto asociado a este detalle. 
     */
    public Producto getProductos() { return productos_comprados; }

    /** 
     * @param productos El producto a asociar. 
     */
    public void setProductos(Producto productos) { this.productos_comprados = productos; }

    /** 
     * @return La Comanda asociada a este detalle. 
     */
    public Comanda getComandas() { return comandas; }

    /** 
     * @param comandas La comanda a la cual vincular este registro. 
     */
    public void setComandas(Comanda comandas) { this.comandas = comandas; }

    /**
     * Genera un código hash basado en el ID del registro.
     * @return Valor hash calculado.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * Compara este detalle con otro objeto basándose en su identificador único.
     * @param obj Objeto a comparar.
     * @return true si los IDs coinciden, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final ComandaProducto other = (ComandaProducto) obj;
        return Objects.equals(this.id, other.id);
    }

    /**
     * Devuelve una cadena con la información descriptiva del detalle de la comanda.
     * @return Representación textual de la entidad.
     */
    @Override
    public String toString() {
        return "ComandaProducto{" + "id=" + id + ", detalles_producto=" + detalles_producto + 
               ", cant_cada_producto=" + cant_cada_producto + ", productos=" + productos_comprados + 
               ", comandas=" + comandas + '}';
    }
}