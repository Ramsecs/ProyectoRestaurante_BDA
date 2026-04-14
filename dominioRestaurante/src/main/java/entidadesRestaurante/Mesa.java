/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesRestaurante;

import enumEntidades.EstadoMesa;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import javax.persistence.*;

/**
 *
 * @author RAMSES
 */
@Entity
@Table(name = "mesas")
public class Mesa implements Serializable {

    /**
     * Identificador único de la mesa. Se genera automáticamente mediante una
     * estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "estado_mesa", nullable = false)
    private EstadoMesa mesa_estado;

    /**
     * Comandas que pertenecen a la mesa.
     */
    @OneToMany(mappedBy = "mesa", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Comanda> comandas;


    /**
     * Constructor por defecto requerido por JPA.
     */  
    public Mesa() {
        this.comandas = new ArrayList<>();
        this.mesa_estado = EstadoMesa.DISPONIBLE; //Estado por defecto
    }
  
    /**
     * Obtiene el identificador único de la mesa.
     *
     * @return El ID de la mesa.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la mesa.
     *
     * @param id El nuevo ID de la mesa.
     */
    public void setId(Long id) {
        this.id = id;
    }
    /**
     * Obtiene una lista de comandas.
     * @return Comanda regresa una lista de comandas
     */
    public List<Comanda> getComandas() {
        return comandas;
    }
    
    /**
     * Setea la lista de comandas, puede ser que se actulice 
     * @param comandas setea la lista de comandas
     */

    public void setComandas(List<Comanda> comandas) {
        this.comandas = comandas;
    }

    public EstadoMesa getMesa_estado() {
        return mesa_estado;
    }

    public void setMesa_estado(EstadoMesa mesa_estado) {
        this.mesa_estado = mesa_estado;
    }
    
    

    /**
     * Genera un código hash para la entidad basado en su identificador.
     *
     * @return Valor hash calculado.
     */
    @Override
    public int hashCode() {
        int hash = 7;
        hash = 89 * hash + Objects.hashCode(this.id);
        return hash;
    }

    /**
     * Compara esta mesa con otro objeto para verificar igualdad.
     *
     * @param obj Objeto a comparar.
     * @return {@code true} si los IDs son idénticos; {@code false} en caso
     * contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Mesa other = (Mesa) obj;
        return Objects.equals(this.id, other.id);
    }

    /**
     * Devuelve una representación textual de la mesa.
     *
     * @return Cadena con el ID de la mesa.
     */
    @Override
    public String toString() {
        return "Mesa{" + "id=" + id + "estado= "+ mesa_estado + '}';
    }
}
