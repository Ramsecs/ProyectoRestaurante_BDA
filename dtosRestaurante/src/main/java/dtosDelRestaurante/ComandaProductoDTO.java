/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosDelRestaurante;

/**
 *
 * @author josma
 */
public class ComandaProductoDTO {
    /**
     * id del registro de la relación entre comanda y producto.
     */
    private Long id;
    /**
     * id del producto de la entidad de Producto.
     */
    private Long id_producto;
    /**
     * Nombre del producto, asi no habrá necesidad de buscar en la BD.
     */
    private String nombre_producto;
    /**
     * Cantidad de producto que se le andjunta a la comanda de manera individual.
     */
    private Integer cantidad;
    /**
     * Detalles que tenga la comanda, como apuntes u otros. 
     */
    private String detalles;
    /**
     * Precio del producto unitario.
     */
    private Double precio; 
    /**
     * Constructor vacio.
     */
    public ComandaProductoDTO() {
    }
    /**
     * Constructor que inicia con parametros ya establecidos
     * @param id_producto que es el id del producto de la comanda
     * @param cantidad la cantidad del producto que se desea agregar
     */
    public ComandaProductoDTO(Long id_producto, Integer cantidad) {
        this.id_producto = id_producto;
        this.cantidad = cantidad;
    }
    /**
     * get del id
     * @return 
     */
    public Long getId() {
        return id;
    }
    /**
     * set del id
     * @param id que corresponde a la relacion ComandProducto
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * get del id del producto
     * @return 
     */

    public Long getId_producto() {
        return id_producto;
    }
    /**
     * set del id del producto
     * @param id_producto que corresponde al producto que compone a la comanda
     */
    public void setId_producto(Long id_producto) {
        this.id_producto = id_producto;
    }
    /**
     * get del nombre del producto
     * @return 
     */
    public String getNombre_producto() {
        return nombre_producto;
    }
    /**
     * set del nombre del producto
     * @param nombre_producto que es el nombre del producto que compone la comanda 
     */
    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }
    /**
     * get de la cantidad del producto que compone la comanda
     * @return 
     */

    public Integer getCantidad() {
        return cantidad;
    }
    /**
     * set de la cantidad del producto
     * @param cantidad corresponde a la cantidad individual del producto
     */
    public void setCantidad(Integer cantidad) {
        this.cantidad = cantidad;
    }
    /**
     * get de detalles de la comanda
     * @return 
     */
    public String getDetalles() {
        return detalles;
    }
    /**
     * set de detalles de la comanda
     * @param detalles corresponde a los detalles de la comanda, es decir
     * si se desea algo en especifico dentro del producto
     */
    public void setDetalles(String detalles) {
        this.detalles = detalles;
    }
    /**
     * Obtener el precio del producto
     * @return 
     */
    public Double getPrecio() {
        return precio;
    }
    /**
     * setear el precio del producto
     * @param precio del producto
     */
    public void setPrecio(Double precio) {
        this.precio = precio;
    }
    
    /**
     * toString con todos los datos del DTO 
     * @return 
     */
    @Override
    public String toString() {
        return "ComandaProductoDTO{" + "id=" + id + ", id_producto=" + id_producto + ", nombre_producto=" + nombre_producto + ", cantidad=" + cantidad + ", detalles=" + detalles + ", precio=" + precio + '}';
    }
    
    
    
    
}
