/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosDelRestaurante;

/**
 * DTO para poder editar las comandas
 *
 * @author josma
 */
public class DetalleComandaDTO {

    /**
     * Id de la comanda.
     */
    private Long id;
    /**
     * Id del producto.
     */
    private Long id_producto;
    /**
     * Nombre del producto.
     */
    private String nombre_producto;
    /**
     * Cantidad del producto.
     */
    private int cantidad;
    /**
     * Precio unitario del producto.
     */
    private Double precio_unitario;
    /**
     * notas del producto.
     */
    private String notas;

    /**
     * Constructor vacio.
     */
    public DetalleComandaDTO() {
    }

    /**
     * Constructor con todo
     *
     * @param id de la comanda
     * @param id_producto del producto de la comanda
     * @param nombre_producto del producto de la comanda
     * @param cantidad de producto de la comanda
     * @param precio_unitario del producto de la comanda
     * @param notas del producto
     */
    public DetalleComandaDTO(Long id, Long id_producto, String nombre_producto, int cantidad, Double precio_unitario, String notas) {
        this.id = id;
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.notas = notas;
    }

    /**
     * Constructor sin id
     *
     * @param id_producto del producto de la comanda
     * @param nombre_producto del producto de la comanda
     * @param cantidad de producto de la comanda
     * @param precio_unitario del producto de la comanda
     * @param notas del producto
     */
    public DetalleComandaDTO(Long id_producto, String nombre_producto, int cantidad, Double precio_unitario, String notas) {
        this.id_producto = id_producto;
        this.nombre_producto = nombre_producto;
        this.cantidad = cantidad;
        this.precio_unitario = precio_unitario;
        this.notas = notas;
    }
    /**
     * Obtener el id de la comanda
     * @return 
     */
    public Long getId() {
        return id;
    }
    /**
     * Setear el id de la comanda
     * @param id 
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Obtener el id del producto
     * @return 
     */
    public Long getId_producto() {
        return id_producto;
    }
    /**
     * setear el id del producto
     * @param id_producto 
     */
    public void setId_producto(Long id_producto) {
        this.id_producto = id_producto;
    }
    /**
     * obtener el nombre del producto
     * @return 
     */
    public String getNombre_producto() {
        return nombre_producto;
    }
    /**
     * setear el nombre del producto
     * @param nombre_producto 
     */
    public void setNombre_producto(String nombre_producto) {
        this.nombre_producto = nombre_producto;
    }
    /**
     * Obtener la cantidad del producto
     * @return 
     */
    public int getCantidad() {
        return cantidad;
    }
    /**
     * setear la cantidad del producto
     * @param cantidad 
     */
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    /**
     * Obtener el precio unitario del producto
     * @return 
     */
    public Double getPrecio_unitario() {
        return precio_unitario;
    }
    /**
     * Setear el precio unitario del producto
     * @param precio_unitario 
     */
    public void setPrecio_unitario(Double precio_unitario) {
        this.precio_unitario = precio_unitario;
    }
    /**
     * Obtener las notas del producto
     * @return 
     */
    public String getNotas() {
        return notas;
    }
    /**
     * setear las notas del producto
     * @param notas 
     */
    public void setNotas(String notas) {
        this.notas = notas;
    }
    /**
     * toString del DTO
     * @return 
     */
    @Override
    public String toString() {
        return "DetalleComandaDTO{" + "id=" + id + ", id_producto=" + id_producto + ", nombre_producto=" + nombre_producto + ", cantidad=" + cantidad + ", precio_unitario=" + precio_unitario + ", notas=" + notas + '}';
    }
    
    
}
