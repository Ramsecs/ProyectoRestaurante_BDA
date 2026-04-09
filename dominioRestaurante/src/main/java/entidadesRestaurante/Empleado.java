/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesRestaurante;

import ConvertidorEncriptado.EncriptadorTelefonoConverter;
import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author RAMSES
 */
@Entity
@Table(name = "meseros")
@Inheritance(strategy = InheritanceType.JOINED)
@DiscriminatorColumn(name = "tipo_empledo")
public class Empleado implements Serializable {

    /**
     * Identificador único del empleado.
     * Generado automáticamente mediante una estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Nombre de usuario único para el acceso al sistema.
     */
    @Column(name = "nombre_usuario", length = 100, nullable = false)
    private String nombre_usuario;

    /**
     * Contraseña del usuario. Se recomienda que el valor almacenado 
     * sea un hash de seguridad.
     */
    @Column(name = "contrasena", length = 255, nullable = false)
    private String contrasena;

    /**
     * Nombre o nombres de pila del empleado.
     */
    @Column(name = "nombres", length = 100, nullable = false)
    private String nombres;

    /**
     * Apellido paterno del empleado.
     */
    @Column(name = "apellido_paterno", length = 100, nullable = false)
    private String apellido_paterno;

    /**
     * Apellido materno del empleado. Campo opcional.
     */
    @Column(name = "apellido_materno", length = 100, nullable = true)
    private String apellido_materno;

    /**
     * Número telefónico de contacto. 
     * Se aplica el conversor {@link EncriptadorTelefonoConverter} para asegurar 
     * la privacidad del dato en la base de datos.
     */
    @Convert(converter = EncriptadorTelefonoConverter.class)
    protected String telefono;

    /**
     * Constructor por defecto requerido por JPA para procesos de persistencia.
     */
    public Empleado() {
    }

    /**
     * Constructor para inicializar un empleado con todos sus atributos personales y de cuenta.
     * 
     * @param nombre_usuario Alias de acceso al sistema.
     * @param contrasena Clave de acceso.
     * @param nombres Nombre(s) del empleado.
     * @param apellido_paterno Primer apellido.
     * @param apellido_materno Segundo apellido.
     * @param telefono Teléfono de contacto.
     */
    public Empleado(String nombre_usuario, String contrasena, String nombres, String apellido_paterno, String apellido_materno, String telefono) {
        this.nombre_usuario = nombre_usuario;
        this.contrasena = contrasena;
        this.nombres = nombres;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.telefono = telefono;
    }

    /**
     * Constructor para inicializar un empleado con un identificador específico.
     * 
     * @param id Identificador único en la base de datos.
     * @param nombre_usuario Alias de acceso al sistema.
     * @param contrasena Clave de acceso.
     * @param nombres Nombre(s) del empleado.
     * @param apellido_paterno Primer apellido.
     * @param apellido_materno Segundo apellido.
     * @param telefono Teléfono de contacto.
     */
    public Empleado(Long id, String nombre_usuario, String contrasena, String nombres, String apellido_paterno, String apellido_materno, String telefono) {
        this.id = id;
        this.nombre_usuario = nombre_usuario;
        this.contrasena = contrasena;
        this.nombres = nombres;
        this.apellido_paterno = apellido_paterno;
        this.apellido_materno = apellido_materno;
        this.telefono = telefono;
    }

    // --- Getters y Setters ---

    /**
     * @return El identificador del empleado. 
     */
    public Long getId() { return id; }

    /**
     * @param id El nuevo ID a asignar. 
     */
    public void setId(Long id) { this.id = id; }

    /**
     * @return El nombre de usuario. 
     */
    public String getNombre_usuario() { return nombre_usuario; }

    /**
     * @param nombre_usuario El nombre de usuario a establecer. 
     */
    public void setNombre_usuario(String nombre_usuario) { this.nombre_usuario = nombre_usuario; }

    /**
     * @return La contraseña (hash). 
     */
    public String getContrasena() { return contrasena; }

    /**
     * @param contrasena La contraseña a establecer. 
     */
    public void setContrasena(String contrasena) { this.contrasena = contrasena; }

    /**
     * @return Los nombres del empleado. 
     */
    public String getNombres() { return nombres; }

    /**
     * @param nombres Los nombres a establecer. 
     */
    public void setNombres(String nombres) { this.nombres = nombres; }

    /**
     * @return El apellido paterno. 
     */
    public String getApellido_paterno() { return apellido_paterno; }

    /**
     * @param apellido_paterno El apellido paterno a asignar. 
     */
    public void setApellido_paterno(String apellido_paterno) { this.apellido_paterno = apellido_paterno; }

    /**
     * @return El apellido materno. 
     */
    public String getApellido_materno() { return apellido_materno; }

    /**
     * @param apellido_materno El apellido materno a asignar. 
     */
    public void setApellido_materno(String apellido_materno) { this.apellido_materno = apellido_materno; }

    /**
     * @return El teléfono de contacto. 
     */
    public String getTelefono() { return telefono; }

    /** 
     * @param telefono El teléfono a establecer. 
     */
    public void setTelefono(String telefono) { this.telefono = telefono; }

    /**
     * Genera un código hash basado en el identificador único.
     * @return Valor hash del objeto.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 71 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * Compara la igualdad de dos empleados basándose en su ID.
     * @param obj Objeto a comparar.
     * @return true si los IDs son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        final Empleado other = (Empleado) obj;
        return Objects.equals(this.id, other.id);
    }

    /**
     * Devuelve una representación textual de los datos del empleado.
     * @return Cadena con los atributos principales.
     */
    @Override
    public String toString() {
        return "Empleado{" + "id=" + id + ", nombre_usuario=" + nombre_usuario + 
               ", contrasena=" + contrasena + ", nombres=" + nombres + 
               ", apellido_paterno=" + apellido_paterno + 
               ", apellido_materno=" + apellido_materno + ", telefono=" + telefono + '}';
    }
}