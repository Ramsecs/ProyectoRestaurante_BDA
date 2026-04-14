/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosDelRestaurante;

/**
 *
 * @author RAMSES
 */
public class ReporteComandaDTO {
    
    private Long id;
    private Long mesa_id;
    private String fecha; 
    private String hora; 
    private String estado; 
    private String cliente;
    private Double total;

    public ReporteComandaDTO() {
    }

    public ReporteComandaDTO(Long id, Long mesa_id, String fecha, String hora, String estado, String cliente, Double total) {
        this.id = id;
        this.mesa_id = mesa_id;
        this.fecha = fecha;
        this.hora = hora;
        this.estado = estado;
        this.cliente = cliente;
        this.total = total;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getMesa_id() {
        return mesa_id;
    }

    public void setMesa_id(Long mesa_id) {
        this.mesa_id = mesa_id;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    @Override
    public String toString() {
        return "ReporteComandaDTO{" + "id=" + id + ", mesa_id=" + mesa_id + ", fecha=" + fecha + ", hora=" + hora + ", estado=" + estado + ", cliente=" + cliente + ", total=" + total + '}';
    }
    
    
    
}
