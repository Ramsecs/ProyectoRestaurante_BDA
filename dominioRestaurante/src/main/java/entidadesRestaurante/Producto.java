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
public class Producto implements Serializable {

    /**
     * Identificador único del producto.
     * Se genera automáticamente mediante una estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre comercial del producto.
     */
    @Column(name = "nombre", length = 100, nullable = false)
    private String nombre;

    /**
     * Costo de venta del producto.
     */
    @Column(name = "precio", nullable = false)
    private Double precio;

    /**
     * Clasificación del producto basada en el enumerador {@link TipoPlatillo}.
     * Se almacena en la base de datos como una cadena de texto.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "tipo_platillo", nullable = false)
    private TipoPlatillo tipo_platilo;

    /**
     * Ruta de acceso local o remota a la imagen del producto.
     */
    @Column(name = "ruta_imagen", length = 100, nullable = false)
    private String ruta_imagen;

    /**
     * Relación uno a muchos con los ingredientes del producto.
     * Incluye persistencia, eliminación y actualización en cascada, además de 
     * gestionar la eliminación de huérfanos para mantener la integridad referencial.
     */
    @OneToMany(mappedBy = "productos", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    private List<ProductoIngrediente> lista_ingredientes;

    /**
     * Relación uno a muchos con las comandas en las que este producto ha sido incluido.
     */
    @OneToMany(mappedBy = "productos_comprados", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<ComandaProducto> lista_comandas;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Producto() {
    }

    /**
     * Constructor para inicializar un producto con sus atributos básicos.
     *
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     * @param tipo_platilo Categoría del producto.
     * @param ruta_imagen Ruta del archivo de imagen.
     */
    public Producto(String nombre, Double precio, TipoPlatillo tipo_platilo, String ruta_imagen) {
        this.nombre = nombre;
        this.precio = precio;
        this.tipo_platilo = tipo_platilo;
        this.ruta_imagen = ruta_imagen;
    }

    /**
     * Constructor completo para inicializar un producto incluyendo su ID.
     * 
     * @param id Identificador único.
     * @param nombre Nombre del producto.
     * @param precio Precio del producto.
     * @param tipo_platilo Categoría del producto.
     * @param ruta_imagen Ruta del archivo de imagen.
     */
    public Producto(Long id, String nombre, Double precio, TipoPlatillo tipo_platilo, String ruta_imagen) {
        this.id = id;
        this.nombre = nombre;
        this.precio = precio;
        this.tipo_platilo = tipo_platilo;
        this.ruta_imagen = ruta_imagen;
    }

    /**
     * Obtiene el identificador único del producto.
     * @return El ID del producto.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador del producto.
     * @param id El nuevo ID del producto.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el nombre del producto.
     * @return Nombre del producto.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * Establece el nombre del producto.
     * @param nombre El nuevo nombre.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * Obtiene el precio del producto.
     * @return El precio actual.
     */
    public Double getPrecio() {
        return precio;
    }

    /**
     * Establece el precio del producto.
     * @param precio El nuevo precio.
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }

    /**
     * Obtiene el tipo o categoría del platillo.
     * @return El TipoPlatillo asignado.
     */
    public TipoPlatillo getTipo_platilo() {
        return tipo_platilo;
    }

    /**
     * Establece el tipo o categoría del platillo.
     * @param tipo_platilo El nuevo tipo de platillo.
     */
    public void setTipo_platilo(TipoPlatillo tipo_platilo) {
        this.tipo_platilo = tipo_platilo;
    }

    /**
     * Obtiene la ruta de la imagen asociada.
     * @return La cadena con la ruta de la imagen.
     */
    public String getRuta_imagen() {
        return ruta_imagen;
    }

    /**
     * Establece la ruta de la imagen asociada.
     * @param ruta_imagen La nueva ruta de imagen.
     */
    public void setRuta_imagen(String ruta_imagen) {
        this.ruta_imagen = ruta_imagen;
    }

    /**
     * Obtiene la lista de ingredientes asociados al producto.
     * @return Lista de ProductoIngrediente.
     */
    public List<ProductoIngrediente> getLista_ingredientes() {
        return lista_ingredientes;
    }

    /**
     * Establece la lista de ingredientes del producto.
     * @param lista_ingredientes Nueva lista de ingredientes.
     */
    public void setLista_ingredientes(List<ProductoIngrediente> lista_ingredientes) {
        this.lista_ingredientes = lista_ingredientes;
    }

    /**
     * Obtiene la lista de comandas que contienen este producto.
     * @return Lista de ComandaProducto.
     */
    public List<ComandaProducto> getLista_comandas() {
        return lista_comandas;
    }

    /**
     * Establece la lista de comandas asociadas.
     * @param lista_comandas Nueva lista de comandas.
     */
    public void setLista_comandas(List<ComandaProducto> lista_comandas) {
        this.lista_comandas = lista_comandas;
    }

    /**
     * Genera un código hash para la entidad basado en su identificador.
     * @return Valor hash calculado.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * Compara este producto con otro objeto para verificar igualdad.
     * @param obj Objeto a comparar.
     * @return true si los IDs son idénticos;  false en caso contrario.
     */
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

    /**
     * Devuelve una representación textual de los atributos principales del producto.
     * @return Cadena con el estado del objeto.
     */
    @Override
    public String toString() {
        return "Producto{" + "id=" + id + ", nombre=" + nombre + ", precio=" + precio + ", tipo_platilo=" + tipo_platilo + ", ruta_imagen=" + ruta_imagen + '}';
    }
}