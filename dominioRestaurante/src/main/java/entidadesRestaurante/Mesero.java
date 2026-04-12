/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesRestaurante;

import java.io.Serializable;
import javax.persistence.*;

/**
 *
 * @author RAMSES
 */
@Entity
@Table(name = "meseros")
@DiscriminatorValue("MESERO")
@PrimaryKeyJoinColumn(name = "id_empleado")
public class Mesero extends Empleado implements Serializable{

    public Mesero() {
    }

    public Mesero(String nombre_usuario, String contrasena, String nombres, String apellido_paterno, String apellido_materno, String telefono) {
        super(nombre_usuario, contrasena, nombres, apellido_paterno, apellido_materno, telefono);
    }

    public Mesero(Long id, String nombre_usuario, String contrasena, String nombres, String apellido_paterno, String apellido_materno, String telefono) {
        super(id, nombre_usuario, contrasena, nombres, apellido_paterno, apellido_materno, telefono);
    }
    
    
    
}
