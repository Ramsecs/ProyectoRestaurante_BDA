/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosDelRestaurante;

import enumEntidades.EstadoComanda;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * DTO para poder transportar todos los datos necesarios entre capas
 *
 * @author josma
 */
public class ComandaDTO {

    /**
     * id de la comanda.
     *
     */
    private Long id;
    /**
     * id del cliente asociado a la comanda.
     *
     */
    private Long id_cliente;
    /**
     * id del mesero que atendio la comanda.
     *
     */
    private Long id_mesero;
    /**
     * id de la mesa donde se creo la comanda.
     *
     */
    private Long id_mesa;
    /**
     * Estado de la comanda.
     *
     */
    private EstadoComanda estado;
    /**
     * Fecha de creación de la comanda.
     *
     */
    private LocalDateTime fecha_hora;
    /**
     * Total de toda la comanda.
     *
     */
    private Double total;
    /**
     * Productos que contiene la comanda. 
     *
     */
    private List<ComandaProductoDTO> productos;
    /**
     * Notas generales de la comanda.
     */
    private String notas;

    /**
     * Constructor para el DTO.
     *
     */
    public ComandaDTO() {
        this.productos = new ArrayList<>();
        this.fecha_hora = LocalDateTime.now();
        this.total = 0.0;
    }
    /**
     * Constructor con todo
     * @param id
     * @param id_cliente
     * @param id_mesero
     * @param id_mesa
     * @param estado
     * @param fecha_hora
     * @param total
     * @param productos
     * @param notas 
     */
    public ComandaDTO(Long id, Long id_cliente, Long id_mesero, Long id_mesa, EstadoComanda estado, LocalDateTime fecha_hora, Double total, List<ComandaProductoDTO> productos, String notas) {
        this.id = id;
        this.id_cliente = id_cliente;
        this.id_mesero = id_mesero;
        this.id_mesa = id_mesa;
        this.estado = estado;
        this.fecha_hora = fecha_hora;
        this.total = total;
        this.productos = productos;
        this.notas = notas;
    }
    

    /**
     * Get para el id
     */
    public Long getId() {
        return id;
    }

    /**
     * set del id
     *
     * @param id de la coamnda
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * get el id del cliente
     *
     * @return
     */
    public Long getIdCliente() {
        return id_cliente;
    }

    /**
     * set del id del cliente
     *
     * @param idCliente que es el di del cliente asociado a la comanda
     */
    public void setIdCliente(Long idCliente) {
        this.id_cliente = idCliente;
    }

    /**
     * get del id del mesero
     *
     * @return
     */
    public Long getIdMesero() {
        return id_mesero;
    }

    /**
     * set del id del mesero
     *
     * @param idMesero mesero al que esta asociado la comanda
     */
    public void setIdMesero(Long idMesero) {
        this.id_mesero = idMesero;
    }

    /**
     * get del id de la mesa
     *
     * @return
     */
    public Long getIdMesa() {
        return id_mesa;
    }

    /**
     * set de la mesa
     *
     * @param idMesa mesa a la que esta asociada la comanda
     */
    public void setIdMesa(Long idMesa) {
        this.id_mesa = idMesa;
    }

    /**
     * get el estado de la comanda que es un enum
     *
     * @return
     */
    public EstadoComanda getEstado() {
        return estado;
    }

    /**
     * set del estado de la comanda
     *
     * @param estado que es un enum
     */
    public void setEstado(EstadoComanda estado) {
        this.estado = estado;
    }

    /**
     * get de la fecha y hora de creación de la comanda
     *
     * @return
     */
    public LocalDateTime getFechaHora() {
        return fecha_hora;
    }

    /**
     * set de la fecha y hora de la comanda
     *
     * @param fecha_hora que es el momento exacto en el que se crea la comanda
     */
    public void setFechaHora(LocalDateTime fecha_hora) {
        this.fecha_hora = fecha_hora;
    }

    /**
     * get del total de toda la comanda, osea la suma de los precios de todos
     * los productos
     *
     * @return
     */
    public Double getTotal() {
        return total;
    }

    /**
     * set del total de la comanda
     *
     * @param total la suma de los precios de los productos que componen a la
     * comanda
     */
    public void setTotal(Double total) {
        this.total = total;
    }

    /**
     * get de la lista de los productos que tiene la comanda
     *
     * @return
     */
    public List<ComandaProductoDTO> getProductos() {
        return productos;
    }

    /**
     * set de de la lista de productos que tiene la comanda
     *
     * @param productos que es una lista de productos que compone a la comanda
     */
    public void setProductos(List<ComandaProductoDTO> productos) {
        this.productos = productos;
    }
    /**
     * Metodo para agregar los productos a la comanda
     * @param producto de la comanda
     */
    public void agregarProducto(ComandaProductoDTO producto) {
        if (this.productos == null) {
            this.productos = new ArrayList<>();
        }
        this.productos.add(producto);
    }
    /**
     * Obtener las notas de la comanda
     * @return 
     */

    public String getNotas() {
        return notas;
    }
    /**
     * Setear las notas de la comanda
     * @param notas de la comanda
     */
    public void setNotas(String notas) {
        this.notas = notas;
    }
    
    

    /**
     * toString de todo el dto, imprime todos los datos
     *
     * @return
     */
    @Override
    public String toString() {
        return "ComandaDTO{" + "id=" + id + ", idCliente=" + id_cliente + ", idMesero=" + id_mesero + ", idMesa=" + id_mesa + ", estado=" + estado + ", fechaHora=" + fecha_hora + ", total=" + total + ", productos=" + productos + '}';
    }

}
