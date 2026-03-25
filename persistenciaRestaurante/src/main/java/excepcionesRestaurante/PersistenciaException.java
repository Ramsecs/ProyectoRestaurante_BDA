package excepcionesRestaurante;

/**
 *  @author RAMSES
 */
public class PersistenciaException extends Exception {

     // Crea una excepción con un mensaje descriptivo sobre el error ocurrido.
    public PersistenciaException(String message) {
        super(message);
    }

    /**
     * Crea una excepción que, además del mensaje, conserva la causa original 
     * (el error técnico de JPA). Esto es vital para que el programador pueda 
     * rastrear el problema real en los logs.
     */
    public PersistenciaException(String message, Throwable cause) {
        super(message, cause);
    }
}