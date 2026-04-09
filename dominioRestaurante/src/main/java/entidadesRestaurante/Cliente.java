/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesRestaurante;

import ConvertidorEncriptado.EncriptadorTelefonoConverter;
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
public class Cliente implements Serializable {

    /**
     * Identificador unico del cliente. Se autogenera mediante una estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    protected Long id;

    /**
     * Nombres del cliente. Campo obligatorio con una longitud maxima de 100 caracteres.
     */
    @Column(name = "nombres", length = 100, nullable = false)
    protected String nombre;

    /**
     * Apellido paterno del cliente. Campo obligatorio.
     */
    @Column(name = "apellido_paterno", length = 100, nullable = false)
    protected String apellido_paterno;

    /**
     * Apellido materno del cliente. Campo obligatorio.
     */
    @Column(name = "apellido_materno", length = 100, nullable = false)
    protected String apellido_materno;

    /**
     * Direccion de correo electronico del cliente. Campo opcional.
     */
    @Column(name = "correo", length = 100, nullable = true)
    protected String correo;

    /**
     * Numero telefonico del cliente. 
     * Se aplica el conversor EncriptadorTelefonoConverter para cifrar el dato antes de persistirlo.
     */
    @Convert(converter = EncriptadorTelefonoConverter.class)
    protected String telefono;

    /**
     * Fecha en la que el cliente fue registrado en el sistema. 
     * No es actualizable una vez que se ha insertado el registro.
     */
    @Column(name = "fecha_registro", nullable = false, updatable = false)
    protected LocalDate fecha_registro;

    /**
     * Relacion de uno a muchos con la entidad Comanda.
     * Gestiona el ciclo de vida de las comandas asociadas mediante operaciones en cascada.
     */
    @OneToMany(mappedBy = "cliente", cascade = {CascadeType.PERSIST, CascadeType.MERGE, CascadeType.REMOVE})
    private List<Comanda> comandas;

    /**
     * Metodo de ciclo de vida de JPA que se ejecuta antes de persistir la entidad.
     * Asigna automáticamente la fecha actual si fecha_registro es nula.
     */
    @PrePersist
    protected void antesDePersistir() {
        if (this.fecha_registro == null) {
            this.fecha_registro = LocalDate.now();
        }
    }

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Cliente() {
    }

    /**
     * Constructor para crear un cliente sin ID (util para registros nuevos).
     * @param nombre Nombres del cliente.
     * @param apellido_paterno Apellido paterno.
     * @param apellido_materno Apellido materno.
     * @param correo Correo electrónico.
     * @param telefono Teléfono de contacto.
     */
    public Cliente(String nombre, String apellido_paterno, String apellido_materno, String correo, String telefono) {
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.correo = correo;
        this.telefono = telefono;
    }

    /**
     * Constructor para crear o referenciar un cliente con un ID existente.
     * @param id Identificador único.
     * @param nombre Nombres del cliente.
     * @param apellido_paterno Apellido paterno.
     * @param apellido_materno Apellido materno.
     * @param correo Correo electrónico.
     * @param telefono Teléfono de contacto.
     */
    public Cliente(Long id, String nombre, String apellido_paterno, String apellido_materno, String correo, String telefono) {
        this.id = id;
        this.nombre = nombre;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.correo = correo;
        this.telefono = telefono;
    }

    // --- Getters y Setters ---

    /** 
    * @return El ID del cliente. 
    */
    public Long getId() { 
        return id; 
    }

    /**
    * @param id El nuevo ID del cliente. 
    */
    public void setId(Long id) { 
        this.id = id; 
    }

    /**
    * @return El nombre o nombres del cliente. 
    */
    public String getNombre() { 
        return nombre; 
    }

    /**
    * @param nombre El nombre a asignar. 
    */
    public void setNombre(String nombre) { 
        this.nombre = nombre; 
    }

    /** 
    * @return El apellido paterno. 
    */
    public String getApellido_paterno() { 
        return apellido_paterno; 
    }

    /** 
    * @param apellido_paterno El apellido paterno a asignar. 
    */
    public void setApellido_paterno(String apellido_paterno) { 
        this.apellido_paterno = apellido_paterno; 
    }

    /** 
    * @return El apellido materno. 
    */
    public String getApellido_materno() { 
        return apellido_materno; 
    }

    /** 
    * @param apellido_materno El apellido materno a asignar. 
    */
    public void setApellido_materno(String apellido_materno) { 
        this.apellido_materno = apellido_materno; 
    }

    /** 
    * @return El correo electronico. 
    */
    public String getCorreo() { 
        return correo; 
    }

    /** 
    * @param correo El correo a asignar. 
    */
    public void setCorreo(String correo) { 
        this.correo = correo; 
    }

    /**
    * @return El telefono (desencriptado por el convertidor). 
    */
    public String getTelefono() { 
        return telefono; 
    }

    /**
    * @param telefono El telefono a asignar.
    */
    public void setTelefono(String telefono) { 
        this.telefono = telefono; 
    }

    /**
    * @return La fecha de registro. 
    */
    public LocalDate getFecha_registro() { 
        return fecha_registro; 
    }

    /** 
    * @param fecha_registro La fecha de registro a asignar.
    */
    public void setFecha_registro(LocalDate fecha_registro) { 
        this.fecha_registro = fecha_registro; 
    }

    /**
     * Devuelve una representacion en cadena de texto del objeto Cliente.
     * @return String con los datos del cliente.
     */
    @Override
    public String toString() {
        return "Cliente{" + "id=" + id + ", nombre=" + nombre + ", apellido_paterno=" + apellido_paterno + 
               ", apellido_materno=" + apellido_materno + ", correo=" + correo + ", telefono=" + telefono + 
               ", fecha_registro=" + fecha_registro + '}';
    }
}