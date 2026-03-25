/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesRestaurante;

import javax.persistence.*;

/**
 *
 * @author RAMSES
 */
@Entity
@Table(name = "ingredientes")
public class Ingrediente {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "")
    private Long id;
    
}
