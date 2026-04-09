/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Enum.java to edit this template
 */
package enumEntidades;

/**
 *
 * @author RAMSES
 */
public enum EstadoComanda {

    /**
     * Indica que la comanda esta activa y en proceso. 
     * El cliente aún puede solicitar productos adicionales y la cuenta no se ha cerrado.
     */
    ABIERTA,

    /**
     * Indica que la comanda ha sido anulada por el personal.
     * Este estado se utiliza cuando el pedido no se concreta por errores, 
     * cambios de decision o incidentes, invalidando su cobro.
     */
    CANCELADA,

    /**
     * Indica que el servicio ha finalizado con exito.
     * Los productos han sido servidos al cliente y el proceso de venta se considera completado.
     */
    ENTREGADA
}
