/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosDelRestaurante;

/**
 *
 * @author RAMSES
 */
public class ClienteBusquedaDTO {
    private Long id;
    private String nombre;
    private String apellido_paterno;
    private String apellido_materno;
    private String correo;
    private String telefono;
    private Integer puntos;
    private Long visitas;
    private Double total_acumulado;

    public ClienteBusquedaDTO() {
    }

    public ClienteBusquedaDTO(Long id, String nombre, String apellido_paterno, String apellido_materno, String correo, String telefono, Integer puntos, Long visitas, Double total_acumulado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.correo = correo;
        this.telefono = telefono;
        this.puntos = puntos;
        this.visitas = visitas;
        this.total_acumulado = total_acumulado;
    }

    

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido_paterno() {
        return apellido_paterno;
    }

    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    public String getApellido_materno() {
        return apellido_materno;
    }

    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }



    public Long getVisitas() {
        return visitas;
    }

    public void setVisitas(Long visitas) {
        this.visitas = visitas;
    }

    public Double getTotal_acumulado() {
        return total_acumulado;
    }

    public void setTotal_acumulado(Double total_acumulado) {
        this.total_acumulado = total_acumulado;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    @Override
    public String toString() {
        return "ClienteBusquedaDTO{" + "id=" + id + ", nombre=" + nombre + ", apellido_paterno=" + apellido_paterno + ", apellido_materno=" + apellido_materno + ", correo=" + correo + ", telefono=" + telefono +  ", puntos=" + puntos + ", visitas=" + visitas + ", total_acumulado=" + total_acumulado + '}';
    }

    
}
