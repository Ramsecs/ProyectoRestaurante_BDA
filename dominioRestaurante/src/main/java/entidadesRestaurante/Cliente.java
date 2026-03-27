/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesRestaurante;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.*;

/**
 *
 * @author RAMSES
 */
@Entity
@Table(name = "clientes")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_cliente")
public class Cliente implements Serializable{
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;
    
    @Column(name = "nombres", length = 100, nullable = false)
    protected String nombre;
    
    @Column(name = "apellido_paterno", length = 100, nullable = false)
    protected String apellido_paterno;
    
    @Column(name = "apellido_materno", length = 100, nullable = false)
    protected String apellido_materno;
    
    @Column(name = "correo", length = 100, nullable = false)
    protected String correo;
    
    @Column(name = "telefono", length = 10, nullable = true)
    protected String telefono;
    
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    protected LocalDate fecha_registro;
    
    @PrePersist
    protected void antesDePersistir() {
        if (this.fecha_registro == null) {
            this.fecha_registro = LocalDate.now();
        }
    }
    
    @OneToMany(mappedBy = "cliente", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Comanda> comandas;

    public Cliente() {
    }

    public Cliente(String nombre, String apellido_paterno, String apellido_materno, String correo, String telefono) {
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.correo = correo;
        this.telefono = telefono;
    }

    public Cliente(Long id, String nombre, String apellido_paterno, String apellido_materno, String correo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.correo = correo;
        this.telefono = telefono;
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

    public LocalDate getFecha_registro() {
        return fecha_registro;
    }

    public void setFecha_registro(LocalDate fecha_registro) {
        this.fecha_registro = fecha_registro;
    }

    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nombre=" + nombre + ", apellido_paterno=" + apellido_paterno + ", apellido_materno=" + apellido_materno + ", correo=" + correo + ", telefono=" + telefono + ", fecha_registro=" + fecha_registro + '}';
    }
    
    
    
}
