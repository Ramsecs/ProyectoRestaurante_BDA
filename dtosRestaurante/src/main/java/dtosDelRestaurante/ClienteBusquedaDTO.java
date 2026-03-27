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
    private String tipo_cliente;
    private Integer puntos;
    private Integer visitas;
    private double total_acumulado;

    public ClienteBusquedaDTO() {
    }

    public ClienteBusquedaDTO(Long id, String nombre, String apellido_paterno, String apellido_materno, String correo, String telefono, String tipo_cliente, Integer puntos, Integer visitas, double total_acumulado) {
        this.id = id;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.correo = correo;
        this.telefono = telefono;
        this.tipo_cliente = tipo_cliente;
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

    public String getTipo_cliente() {
        return tipo_cliente;
    }

    public void setTipo_cliente(String tipo_cliente) {
        this.tipo_cliente = tipo_cliente;
    }

    public Integer getVisitas() {
        return visitas;
    }

    public void setVisitas(Integer visitas) {
        this.visitas = visitas;
    }

    public double getTotal_acumulado() {
        return total_acumulado;
    }

    public void setTotal_acumulado(double total_acumulado) {
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
        return "ClienteBusquedaDTO{" + "id=" + id + ", nombre=" + nombre + ", apellido_paterno=" + apellido_paterno + ", apellido_materno=" + apellido_materno + ", correo=" + correo + ", telefono=" + telefono + ", tipo_cliente=" + tipo_cliente + ", puntos=" + puntos + ", visitas=" + visitas + ", total_acumulado=" + total_acumulado + '}';
    }

    
}
