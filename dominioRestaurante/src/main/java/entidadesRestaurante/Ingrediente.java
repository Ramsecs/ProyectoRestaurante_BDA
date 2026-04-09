/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesRestaurante;

import enumEntidades.UnidadMedida;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author RAMSES
 */
@Entity
@Table(name = "ingredientes")
public class Ingrediente implements Serializable {

    /**
     * Identificador unico del ingrediente en la base de datos.
     * Se genera automaticamente mediante una estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre descriptivo del ingrediente.
     * Campo obligatorio con una longitud maxima de 100 caracteres.
     */
    @Column(name = "nombre_ingrediente", length = 100, nullable = false)
    private String nombre;

    /**
     * Cantidad actual disponible en el inventario.
     */
    @Column(name = "stock", nullable = false)
    private Integer stock;

    /**
     * Unidad de medida utilizada para cuantificar el stock (MILILITROS, GRAMOS, PIEZA).
     * Se almacena en la base de datos como una cadena de texto (String).
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "unidad_medida", nullable = false)
    private UnidadMedida unidad_medida;

    /**
     * Lista de relaciones entre este ingrediente y los productos que lo utilizan.
     * Incluye persistencia en cascada y eliminacion de huerfanos para mantener la integridad.
     */
    @OneToMany(mappedBy = "productos", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE}, orphanRemoval = true)
    private List<ProductoIngrediente> lista_productos;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Ingrediente() {
    }

    /**
     * Constructor para crear un ingrediente nuevo con sus atributos básicos.
     * @param nombre Nombre del ingrediente.
     * @param stock Cantidad inicial en inventario.
     * @param unidad_medida Tipo de unidad de medida.
     */
    public Ingrediente(String nombre, Integer stock, UnidadMedida unidad_medida) {
        this.nombre = nombre;
        this.stock = stock;
        this.unidad_medida = unidad_medida;
    }

    /**
     * Constructor para inicializar un ingrediente con un ID especifico.
     * @param id Identificador único.
     * @param nombre Nombre del ingrediente.
     * @param stock Cantidad en inventario.
     * @param unidad_medida Tipo de unidad de medida.
     */
    public Ingrediente(Long id, String nombre, Integer stock, UnidadMedida unidad_medida) {
        this.id = id;
        this.nombre = nombre;
        this.stock = stock;
        this.unidad_medida = unidad_medida;
    }

    // --- Metodos de Acceso (Getters y Setters) ---

    /** 
     * @return El ID del ingrediente. 
     */
    public Long getId() { return id; }

    /**
     * @param id El nuevo ID a asignar. 
     */
    public void setId(Long id) { this.id = id; }

    /** 
     * @return El nombre del ingrediente. 
     */
    public String getNombre() { return nombre; }

    /** 
     * @param nombre El nombre a asignar.
     */
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** 
     * @return La cantidad en stock. 
     */
    public Integer getStock() { return stock; }

    /** 
     * @param stock El nuevo valor de stock. 
     */
    public void setStock(Integer stock) { this.stock = stock; }

    /** 
     * @return La unidad de medida asociada. 
     */
    public UnidadMedida getUnidad_medida() { return unidad_medida; }

    /** 
     * @param unidad_medida La unidad de medida a asignar. 
     */
    public void setUnidad_medida(UnidadMedida unidad_medida) { this.unidad_medida = unidad_medida; }

    /**
     * Genera un codigo hash basado en el ID del ingrediente.
     * @return Valor hash del objeto.
     */
    @Override
    public int hashCode() {
        int hash = 5;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * Compara este ingrediente con otro objeto basandose en el identificador único.
     * @param obj Objeto a comparar.
     * @return true si los IDs coinciden, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final Ingrediente other = (Ingrediente) obj;
        return Objects.equals(this.id, other.id);
    }

    /**
     * Devuelve una cadena con la informacion detallada del ingrediente.
     * @return Representación textual del objeto.
     */
    @Override
    public String toString() {
        return "Ingrediente{" + "id=" + id + ", nombre=" + nombre + ", stock=" + stock + ", unidad_medida=" + unidad_medida + '}';
    }
}