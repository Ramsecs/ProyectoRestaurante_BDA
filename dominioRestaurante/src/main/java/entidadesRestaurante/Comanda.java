/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesRestaurante;

import enumEntidades.EstadoComanda;
import java.time.LocalDateTime;
import javax.persistence.*;

/**
 *
 * @author RAMSES
 */
@Entity
@Table(name = "comanda")
public class Comanda {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_comanda", nullable = false)
    private EstadoComanda estado_comanda;
    
    @Column(name = "fecha_hora_creacion", nullable = false)
    private LocalDateTime fecha_hora_creacion;
    
    @Column(name = "total_venta", nullable = false)
    private Double total_venta;
    
    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private Cliente cliente;

    public Comanda() {
    }

    public Comanda(EstadoComanda estado_comanda, LocalDateTime fecha_hora_creacion, Double total_venta) {
        this.estado_comanda = estado_comanda;
        this.fecha_hora_creacion = fecha_hora_creacion;
        this.total_venta = total_venta;
    }

    public Comanda(Long id, EstadoComanda estado_comanda, LocalDateTime fecha_hora_creacion, Double total_venta) {
        this.id = id;
        this.estado_comanda = estado_comanda;
        this.fecha_hora_creacion = fecha_hora_creacion;
        this.total_venta = total_venta;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public EstadoComanda getEstado_comanda() {
        return estado_comanda;
    }

    public void setEstado_comanda(EstadoComanda estado_comanda) {
        this.estado_comanda = estado_comanda;
    }

    public LocalDateTime getFecha_hora_creacion() {
        return fecha_hora_creacion;
    }

    public void setFecha_hora_creacion(LocalDateTime fecha_hora_creacion) {
        this.fecha_hora_creacion = fecha_hora_creacion;
    }

    public Double getTotal_venta() {
        return total_venta;
    }

    public void setTotal_venta(Double total_venta) {
        this.total_venta = total_venta;
    }

    @Override
    public String toString() {
        return "Comanda{" + "id=" + id + ", estado_comanda=" + estado_comanda + ", fecha_hora_creacion=" + fecha_hora_creacion + ", total_venta=" + total_venta + '}';
    }

}
