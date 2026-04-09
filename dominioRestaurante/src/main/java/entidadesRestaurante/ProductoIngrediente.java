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
public class ProductoIngrediente implements Serializable {

    /**
     * Identificador único del registro de la relación producto-ingrediente.
     * Generado automáticamente mediante una estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Cantidad del ingrediente necesaria para el producto asociado.
     * El valor debe interpretarse según la unidad de medida definida en {@link Ingrediente}.
     */
    @Column(name = "cantidad_ingrediente")
    private Integer cantidad_ingrediente;

    /**
     * El producto al que pertenece esta parte de la receta.
     * Relación de muchos a uno con la entidad {@link Producto}.
     */
    @ManyToOne
    @JoinColumn(name = "id_producto", nullable = false)
    private Producto productos;

    /**
     * El ingrediente requerido para el producto.
     * Relación de muchos a uno con la entidad {@link Ingrediente}.
     */
    @ManyToOne
    @JoinColumn(name = "id_ingrediente", nullable = false)
    private Ingrediente ingredientes;

    /**
     * Constructor por defecto requerido por la especificación JPA.
     */
    public ProductoIngrediente() {
    }

    /**
     * Constructor para crear una nueva asociación definiendo solo la cantidad.
     * 
     * @param cantidad_ingrediente Cantidad necesaria del insumo.
     */
    public ProductoIngrediente(Integer cantidad_ingrediente) {
        this.cantidad_ingrediente = cantidad_ingrediente;
    }

    /**
     * Constructor para inicializar la asociación con un ID específico y su cantidad.
     * 
     * @param id Identificador único de la relación.
     * @param cantidad_ingrediente Cantidad necesaria del insumo.
     */
    public ProductoIngrediente(Long id, Integer cantidad_ingrediente) {
        this.id = id;
        this.cantidad_ingrediente = cantidad_ingrediente;
    }

    // --- Métodos de Acceso (Getters y Setters) ---

    /** 
     * @return El ID de la relación producto-ingrediente. 
     */
    public Long getId() { return id; }

    /** 
     * @param id El nuevo ID a asignar. 
     */
    public void setId(Long id) { this.id = id; }

    /** 
     * @return La cantidad de ingrediente configurada para esta receta. 
     */
    public Integer getCantidad_ingrediente() { return cantidad_ingrediente; }

    /** 
     * @param cantidad_ingrediente La cantidad a establecer. 
     */
    public void setCantidad_ingrediente(Integer cantidad_ingrediente) { this.cantidad_ingrediente = cantidad_ingrediente; }

    /**
     * @return El objeto Producto asociado.
     */
    public Producto getProductos() { return productos; }

    /**
     * @param productos El producto a asociar. 
     */
    public void setProductos(Producto productos) { this.productos = productos; }

    /**
     * @return El objeto Ingrediente asociado. 
     */
    public Ingrediente getIngredientes() { return ingredientes; }

    /**
     * @param ingredientes El ingrediente a asociar. 
     */
    public void setIngredientes(Ingrediente ingredientes) { this.ingredientes = ingredientes; }

    /**
     * Genera un código hash basado en el ID de la entidad.
     * @return Valor hash calculado.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 17 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * Compara la igualdad entre esta asociación y otro objeto basándose en el ID.
     * @param obj Objeto a comparar.
     * @return true si los IDs coinciden, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final ProductoIngrediente other = (ProductoIngrediente) obj;
        return Objects.equals(this.id, other.id);
    }

    /**
     * Devuelve una representación textual de la relación, incluyendo el producto e ingrediente.
     * @return Cadena descriptiva de la receta.
     */
    @Override
    public String toString() {
        return "ProductoIngrediente{" + "id=" + id + ", cantidad_ingrediente=" + 
               cantidad_ingrediente + ", productos=" + productos + 
               ", ingredientes=" + ingredientes + '}';
    }
}
