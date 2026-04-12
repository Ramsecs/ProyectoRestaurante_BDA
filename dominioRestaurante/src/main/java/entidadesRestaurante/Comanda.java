/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesRestaurante;

import enumEntidades.EstadoComanda;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author RAMSES
 */
@Entity
@Table(name = "comanda")
public class Comanda implements Serializable {

    /**
     * Identificador único de la comanda. Generado automáticamente por la base
     * de datos.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Estado actual del pedido (ABIERTA, CANCELADA, ENTREGADA). Se almacena en
     * la base de datos como una cadena de texto.
     */
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_comanda", nullable = false)
    private EstadoComanda estado_comanda;

    /**
     * Fecha y hora exacta en la que se generó la comanda.
     */
    @Column(name = "fecha_hora_creacion", nullable = false)
    private LocalDateTime fecha_hora_creacion;

    /**
     * Monto total de la venta calculado para esta comanda.
     */
    @Column(name = "total_venta", nullable = false)
    private Double total_venta;

    /**
     * Mesero al que se le asigno la comanda. Relación de muchos a uno: Varias comandas
     * pueden pertenecer a un mismo mesero.
     */
    @ManyToOne
    @JoinColumn(name = "fk_mesero")
    private Mesero mesero;
    
    /**
     * Cliente que realizó el pedido. Relación de muchos a uno: Varias comandas
     * pueden pertenecer a un mismo cliente.
     */
    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private Cliente cliente;

    /**
     * Documentar de nuevo !!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!
     */
    @ManyToOne
    @JoinColumn(name = "fk_mesa", nullable = false) // Esta será la columna en la tabla comanda
    private Mesa mesa;

    /**
     * Lista de productos incluidos en la comanda a través de la entidad
     * intermedia. Incluye persistencia y eliminación en cascada para mantener
     * la integridad del pedido.
     */
    @OneToMany(mappedBy = "comandas", cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE})
    private List<ComandaProducto> lista_productos;

    /**
     * Constructor por defecto para JPA.
     */
    public Comanda() {
    }

    /**
     * Constructor para inicializar una comanda nueva con sus datos operativos
     * básicos.
     *
     * @param estado_comanda Estado inicial de la orden.
     * @param fecha_hora_creacion Momento de apertura del pedido.
     * @param total_venta Importe total inicial.
     */
    public Comanda(EstadoComanda estado_comanda, LocalDateTime fecha_hora_creacion, Double total_venta) {
        this.estado_comanda = estado_comanda;
        this.fecha_hora_creacion = fecha_hora_creacion;
        this.total_venta = total_venta;
    }

    /**
     * Constructor para inicializar una comanda con un identificador específico.
     *
     * @param id Identificador único.
     * @param estado_comanda Estado de la orden.
     * @param fecha_hora_creacion Fecha de creación.
     * @param total_venta Importe total.
     */
    public Comanda(Long id, EstadoComanda estado_comanda, LocalDateTime fecha_hora_creacion, Double total_venta) {
        this.id = id;
        this.estado_comanda = estado_comanda;
        this.fecha_hora_creacion = fecha_hora_creacion;
        this.total_venta = total_venta;
    }

    // --- Métodos de Acceso (Getters y Setters) ---
    /**
     * @return El ID de la comanda.
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id El nuevo ID de la comanda.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return El estado actual de la comanda.
     */
    public EstadoComanda getEstado_comanda() {
        return estado_comanda;
    }

    /**
     * @param estado_comanda El nuevo estado a asignar.
     */
    public void setEstado_comanda(EstadoComanda estado_comanda) {
        this.estado_comanda = estado_comanda;
    }

    /**
     * @return La fecha y hora de creación.
     */
    public LocalDateTime getFecha_hora_creacion() {
        return fecha_hora_creacion;
    }

    /**
     * @param fecha_hora_creacion La nueva fecha y hora a asignar.
     */
    public void setFecha_hora_creacion(LocalDateTime fecha_hora_creacion) {
        this.fecha_hora_creacion = fecha_hora_creacion;
    }

    /**
     * @return El total de la venta.
     */
    public Double getTotal_venta() {
        return total_venta;
    }

    /**
     * @param total_venta El nuevo total a asignar.
     */
    public void setTotal_venta(Double total_venta) {
        this.total_venta = total_venta;
    }

    /**
     * @return La lista de productos asociados a esta comanda.
     */
    public List<ComandaProducto> getLista_productos() {
        return lista_productos;
    }

    /**
     * @param lista_productos La lista de productos a asociar.
     */
    public void setLista_productos(List<ComandaProducto> lista_productos) {
        this.lista_productos = lista_productos;
    }

    /**
     * Devuelve una representación en texto de la comanda con sus atributos
     * principales.
     *
     * @return Cadena con el estado de la entidad.
     */
    @Override
    public String toString() {
        return "Comanda{" + "id=" + id + ", estado_comanda=" + estado_comanda
                + ", fecha_hora_creacion=" + fecha_hora_creacion + ", total_venta=" + total_venta + '}';
    }
}
