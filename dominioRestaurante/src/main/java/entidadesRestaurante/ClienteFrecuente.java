/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesRestaurante;

import java.io.Serializable;
import java.time.LocalDate;
import javax.persistence.*;

/**
 *
 * @author RAMSES
 */
@Entity
@Table(name = "clientes_frecuentes")
@DiscriminatorValue("CLIENTE_FRECUENTE")
@PrimaryKeyJoinColumn(name = "id_cliente")
public class ClienteFrecuente extends Cliente implements Serializable{
    
    @Column(name = "puntos", nullable = false)
    private Integer puntos;

    public ClienteFrecuente() {
        super();
    }

    public ClienteFrecuente(Integer puntos, String nombre, String apellido_paterno, String apellido_materno, String correo, String telefono, LocalDate fecha_registro) {
        super(nombre, apellido_paterno, apellido_materno, correo, telefono, fecha_registro);
        this.puntos = puntos;
    }

    public ClienteFrecuente(Integer puntos, Long id, String nombre, String apellido_paterno, String apellido_materno, String correo, String telefono, LocalDate fecha_registro) {
        super(id, nombre, apellido_paterno, apellido_materno, correo, telefono, fecha_registro);
        this.puntos = puntos;
    }

    public Integer getPuntos() {
        return puntos;
    }

    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public String getNombre() {
        return nombre;
    }

    @Override
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    @Override
    public String getApellido_paterno() {
        return apellido_paterno;
    }

    @Override
    public void setApellido_paterno(String apellido_paterno) {
        this.apellido_paterno = apellido_paterno;
    }

    @Override
    public String getApellido_materno() {
        return apellido_materno;
    }

    @Override
    public void setApellido_materno(String apellido_materno) {
        this.apellido_materno = apellido_materno;
    }

    @Override
    public String getCorreo() {
        return correo;
    }

    @Override
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    @Override
    public String getTelefono() {
        return telefono;
    }

    @Override
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    @Override
    public LocalDate getFecha_registro() {
        return fecha_registro;
    }

    @Override
    public void setFecha_registro(LocalDate fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nombre=" + nombre + ", apellido_paterno=" + apellido_paterno + ", apellido_materno=" + apellido_materno + ", correo=" + correo + ", telefono=" + telefono + ", fecha_registro=" + fecha_registro  + "puntos=" + puntos + '}';
    }
    
    
    
}
