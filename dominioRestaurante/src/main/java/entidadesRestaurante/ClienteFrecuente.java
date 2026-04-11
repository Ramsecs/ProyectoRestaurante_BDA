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
public class ClienteFrecuente extends Cliente implements Serializable {

    /**
     * Puntos de lealtad acumulados por el cliente basándose en sus compras 
     * o visitas anteriores.
     */
    @Column(name = "puntos", nullable = false)
    private Integer puntos;

    /**
     * Constructor por defecto requerido por JPA. 
     * Invoca al constructor de la clase superior {@link Cliente}.
     */
    public ClienteFrecuente() {
        super();
    }

    /**
     * Constructor para crear un cliente frecuente nuevo con puntos iniciales.
     *
     * @param puntos Cantidad de puntos asignados.
     * @param nombre Nombres del cliente.
     * @param apellido_paterno Apellido paterno.
     * @param apellido_materno Apellido materno.
     * @param correo Dirección de correo electrónico.
     * @param telefono Número telefónico de contacto.
     */
    public ClienteFrecuente(Integer puntos, String nombre, String apellido_paterno, String apellido_materno, String correo, String telefono) {
        super(nombre, apellido_paterno, apellido_materno, correo, telefono);
        this.puntos = puntos;
    }

    /**
     * Constructor para inicializar un cliente frecuente con un ID existente y puntos.
     *
     * @param puntos Cantidad de puntos acumulados.
     * @param id Identificador único del cliente.
     * @param nombre Nombres del cliente.
     * @param apellido_paterno Apellido paterno.
     * @param apellido_materno Apellido materno.
     * @param correo Dirección de correo electrónico.
     * @param telefono Número telefónico de contacto.
     */
    public ClienteFrecuente(Integer puntos, Long id, String nombre, String apellido_paterno, String apellido_materno, String correo, String telefono) {
        super(id, nombre, apellido_paterno, apellido_materno, correo, telefono);
        this.puntos = puntos;
    }

    /**
     * Obtiene los puntos de lealtad del cliente.
     * @return {@code Integer} con la cantidad de puntos.
     */
    public Integer getPuntos() {
        return puntos;
    }

    /**
     * Asigna o actualiza los puntos de lealtad del cliente.
     * @param puntos Cantidad de puntos a establecer.
     */
    public void setPuntos(Integer puntos) {
        this.puntos = puntos;
    }

    // --- Métodos Sobrescritos de la clase Cliente ---

    /**
     * @return El ID del cliente (llave primaria compartida).
     */
    @Override
    public Long getId() { return id; }

    /**
     * @param id El nuevo ID del cliente. 
     */
    @Override
    public void setId(Long id) { this.id = id; }

    /** 
     * @return El nombre o nombres del cliente frecuente. 
     */
    @Override
    public String getNombre() { return nombre; }

    /** 
     * @param nombre El nombre a asignar. 
     */
    @Override
    public void setNombre(String nombre) { this.nombre = nombre; }

    /** 
     * @return El apellido paterno. 
     */
    @Override
    public String getApellido_paterno() { return apellido_paterno; }

    /** 
     * @param apellido_paterno El apellido paterno a asignar. 
     */
    @Override
    public void setApellido_paterno(String apellido_paterno) { this.apellido_paterno = apellido_paterno; }

    /** 
     * @return El apellido materno. 
     */
    @Override
    public String getApellido_materno() { return apellido_materno; }

    /** 
     * @param apellido_materno El apellido materno a asignar. 
     */
    @Override
    public void setApellido_materno(String apellido_materno) { this.apellido_materno = apellido_materno; }

    /** 
     * @return El correo electrónico. 
     */
    @Override
    public String getCorreo() { return correo; }

    /** 
     * @param correo El correo a asignar. 
     */
    @Override
    public void setCorreo(String correo) { this.correo = correo; }

    /** 
     * @return El teléfono de contacto.
     */
    @Override
    public String getTelefono() { return telefono; }

    /** 
     * @param telefono El teléfono a asignar. 
     */
    @Override
    public void setTelefono(String telefono) { this.telefono = telefono; }

    /** 
     * @return La fecha en que el cliente fue registrado inicialmente. 
     */
    @Override
    public LocalDate getFecha_registro() { return fecha_registro; }

    /** 
     * @param fecha_registro La fecha de registro a asignar. 
     */
    @Override
    public void setFecha_registro(LocalDate fecha_registro) { this.fecha_registro = fecha_registro; }

    /**
     * Devuelve una representación en cadena de texto de los datos del cliente frecuente,
     * incluyendo la información heredada y los puntos acumulados.
     * @return String con la información detallada.
     */
    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nombre=" + nombre + ", apellido_paterno=" + apellido_paterno + 
               ", apellido_materno=" + apellido_materno + ", correo=" + correo + ", telefono=" + telefono + 
               ", fecha_registro=" + fecha_registro  + ", puntos=" + puntos + '}';
    }
}