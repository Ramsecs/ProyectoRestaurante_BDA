/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosRestaurante;

import entidadesRestaurante.Mesero;
import excepcionesRestaurante.PersistenciaException;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias para MeseroDAO, enfocado en la recuperación de datos por id
 *
 * * @author josma
 */
public class MeseroDAOTest {

    private MeseroDAO meseroDAO;

    @BeforeEach
    public void setUp() {
        meseroDAO = MeseroDAO.getInstanceMeseroDAO();
    }

    /**
     * Prueba el éxito al buscar un mesero que sí existe en la base de datos.
     */
    @Test
    public void testBuscarPorId_Exito() {
        Long idExistente = 3L; // ID que debe existir en la bd

        assertDoesNotThrow(() -> {
            Mesero resultado = meseroDAO.buscarPorId(idExistente);

            assertNotNull(resultado, "El mesero no debería ser nulo si el ID existe");
            assertEquals(idExistente, resultado.getId(), "El ID retornado debe coincidir con el buscado");
            System.out.println("Mesero encontrado: " + resultado.getNombres());
        });
    }

    /**
     * Prueba el comportamiento cuando se busca un ID que no existe.
     */
    @Test
    public void testBuscarPorId_NoExistente() {
        Long idInexistente = 999999L;

        assertDoesNotThrow(() -> {
            Mesero resultado = meseroDAO.buscarPorId(idInexistente);
            assertNull(resultado, "Debe retornar null cuando el ID no existe en la base de datos");
        });
    }

    /**
     * Prueba el manejo de parámetros nulos.
     */
    @Test
    public void testBuscarPorId_IdNulo() {
        try {
            Mesero resultado = meseroDAO.buscarPorId(null);
            assertNull(resultado, "Si no lanza excepción, al menos debe retornar null");
        } catch (PersistenciaException e) {
            assertTrue(e.getMessage().contains("Error al buscar"), "La excepción debe tener el mensaje esperado");
        }
    }
}
