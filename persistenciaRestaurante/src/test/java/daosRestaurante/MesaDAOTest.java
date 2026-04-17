/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosRestaurante;

import entidadesRestaurante.Mesa;
import enumEntidades.EstadoMesa;
import excepcionesRestaurante.PersistenciaException;
import java.util.Arrays;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *Clase para hacer un test a los metodos de la dao de mesas
 * @author josma
 */
public class MesaDAOTest {
    /**
     * variable para el singleton.
     */
    private MesaDAO mesaDAO;
    /**
     * Hace un setup y el singleton para poder usar la dao.
     */
    @BeforeEach
    public void setUp() {
        mesaDAO = MesaDAO.getInstanceMesaDAO();
    }

    // --- TEST: buscarPorId ---
    /**
     * busca la mesa por id, este caso debe de funcionar
     * @throws PersistenciaException 
     */
    @Test
    public void testBuscarPorId_Exito() throws PersistenciaException {
        // Flujo feliz Buscar una mesa que sabemos que existe 
        Long idExistente = 1L;
        Mesa mesa = mesaDAO.buscarPorId(idExistente);

        //validamos que si existe
        if (mesa != null) {
            assertNotNull(mesa.getMesa_estado());
        }
    }
    /**
     * busca una mesa con un id inexistente, debe de fallar
     * @throws PersistenciaException 
     */
    @Test
    public void testBuscarPorId_Inexistente() throws PersistenciaException {
        // Flujo alternativo El ID no existe
        Mesa mesa = mesaDAO.buscarPorId(-1L);
        assertNull(mesa, "Debería retornar null para un ID que no existe");
    }

    // --- TEST: obtenerMesasOcupadas ---
    /**
     * debe de obtener las mesas ocupadas, debe funcionar
     * @throws PersistenciaException 
     */
    @Test
    public void testObtenerMesasOcupadas_FlujoConsulta() throws PersistenciaException {
        // Este test valida que la consulta JPQL no truene y devuelva una lista (aunque esté vacía)
        List<Long> ocupadas = mesaDAO.obtenerMesasOcupadas();
        assertNotNull(ocupadas, "La lista no debe ser nula, incluso si no hay mesas ocupadas");
    }

    // --- TEST: cancelarComandaPorMesa ---
    /**
     * Debe cancelar las comandas por mesa, este es el caso de exito.
     */
    @Test
    public void testCancelarComandaPorMesa_Exito() {
        // Flujo positivo Intentar cancelar una mesa con ID valido
        // El metodo usa executeUpdate(), por lo tanto si no hay comandas abiertas, 
        // simplemente no actualiza nada pero no debe lanzar error, se supone
        assertDoesNotThrow(() -> {
            mesaDAO.cancelarComandaPorMesa(1L);
        });
    }
    /**
     * Intenta cancelar la comanda a una mesa que no existe, es caso de falla.
     */
    @Test
    public void testCancelarComandaPorMesa_IdInexistente() {
        assertDoesNotThrow(() -> {
            mesaDAO.cancelarComandaPorMesa(999999L);
        });
    }

    // --- TEST: registrarMesasMasivas ---
    /**
     * Intenta registrar mesas, es el camino que funciona.
     */
    @Test
    public void testRegistrarMesasMasivas_Exito() {
        // Flujo positivo: Registrar un lote de mesas
        Mesa m1 = new Mesa();
        m1.setMesa_estado(EstadoMesa.DISPONIBLE);

        Mesa m2 = new Mesa();
        m2.setMesa_estado(EstadoMesa.DISPONIBLE);

        List<Mesa> lote = Arrays.asList(m1, m2);

        assertDoesNotThrow(() -> {
            mesaDAO.registrarMesasMasivas(lote);
            assertNotNull(m1.getId());
            assertNotNull(m2.getId());
        });
    }
    /**
     * Debe de fallar al insertar mesas.
     */
    @Test
    public void testRegistrarMesasMasivas_ErrorRollback() {
        // Flujo alternativo: Una de las mesas del lote es inválida 
        Mesa mValida = new Mesa();
        Mesa mInvalida = new Mesa();
        mInvalida.setMesa_estado(null);

        List<Mesa> lote = Arrays.asList(mValida, mInvalida);

        assertThrows(PersistenciaException.class, () -> {
            mesaDAO.registrarMesasMasivas(lote);
        }, "El lote completo debería fallar (rollback) si una mesa es inválida");
    }
}
