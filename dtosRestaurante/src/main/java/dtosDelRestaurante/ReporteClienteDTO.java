/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosDelRestaurante;

/**
 *
 * @author RAMSES
 */
public class ReporteClienteDTO {
    
    private String nombre_completo; 
    private Long visitas; 
    private Double total_gastado;
    private String fecha_ultima_comanda;

    public ReporteClienteDTO() {
    }

    public ReporteClienteDTO(String nombre_completo, Long visitas, Double total_gastado, String fecha_ultima_comanda) {
        this.nombre_completo = nombre_completo;
        this.visitas = visitas;
        this.total_gastado = total_gastado;
        this.fecha_ultima_comanda = fecha_ultima_comanda;
    }

    public String getNombre_completo() {
        return nombre_completo;
    }

    public void setNombre_completo(String nombre_completo) {
        this.nombre_completo = nombre_completo;
    }

    public Long getVisitas() {
        return visitas;
    }

    public void setVisitas(Long visitas) {
        this.visitas = visitas;
    }

    public Double getTotal_gastado() {
        return total_gastado;
    }

    public void setTotal_gastado(Double total_gastado) {
        this.total_gastado = total_gastado;
    }

    public String getFecha_ultima_comanda() {
        return fecha_ultima_comanda;
    }

    public void setFecha_ultima_comanda(String fecha_ultima_comanda) {
        this.fecha_ultima_comanda = fecha_ultima_comanda;
    }

    @Override
    public String toString() {
        return "ReporteClienteDTO{" + "nombre_completo=" + nombre_completo + ", visitas=" + visitas + ", total_gastado=" + total_gastado + ", fecha_ultima_comanda=" + fecha_ultima_comanda + '}';
    }
    
    
}
