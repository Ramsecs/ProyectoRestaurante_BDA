/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entidadesRestaurante;

import java.io.Serializable;
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
     * Identificador único de la mesa.
     * Se genera automáticamente mediante una estrategia de identidad.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * Comanda asociada actualmente a la mesa.
     * Relación uno a uno que incluye persistencia, actualización y eliminación 
     * en cascada. El uso de {@code orphanRemoval = true} asegura que si se 
     * desvincula una comanda, esta sea gestionada correctamente por el proveedor de persistencia.
     */
    @OneToOne(cascade = {CascadeType.PERSIST, CascadeType.REMOVE, CascadeType.MERGE}, orphanRemoval = true)
    @JoinColumn(name = "id")
    private Comanda comanda;

    /**
     * Constructor por defecto requerido por JPA.
     */
    public Mesa() {
    }

    /**
     * Obtiene el identificador único de la mesa.
     * @return El ID de la mesa.
     */
    public Long getId() {
        return id;
    }

    /**
     * Establece el identificador de la mesa.
     * @param id El nuevo ID de la mesa.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Genera un código hash para la entidad basado en su identificador.
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
     * @param obj Objeto a comparar.
     * @return {@code true} si los IDs son idénticos; {@code false} en caso contrario.
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
     * @return Cadena con el ID de la mesa.
     */
    @Override
    public String toString() {
        return "Mesa{" + "id=" + id + '}';
    }
}