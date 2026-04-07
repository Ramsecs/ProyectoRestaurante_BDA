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
@Table(name = "administradores")
@DiscriminatorValue("ADMIN")
@PrimaryKeyJoinColumn(name = "id_admin")
public class Administrador extends Empleado implements Serializable{
    
    
    
}
