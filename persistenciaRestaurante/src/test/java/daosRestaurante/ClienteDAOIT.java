/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daosRestaurante;

import entidadesRestaurante.Cliente;
import excepcionesRestaurante.PersistenciaException;
import java.time.LocalDate;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author DANIEL
 */
public class ClienteDAOIT {
    
    public ClienteDAOIT() {
    }

    private final ClienteDAO clienteDAO = ClienteDAO.getInstanceClienteDAO();

    @Test
    @DisplayName("Deberia persistir un cliente correctamente y generar un ID")
    public void testAgregarClienteExitoso() {
        Cliente nuevoCliente = new Cliente();
        nuevoCliente.setNombre("Ramses");
        nuevoCliente.setApellido_paterno("Contreras");
        nuevoCliente.setApellido_materno("Garcia");
        nuevoCliente.setCorreo("ramtaro110@gmail.com");
        nuevoCliente.setTelefono("6441238756");

        try {
            Cliente resultado = clienteDAO.registrarCliente(nuevoCliente);
            assertNotNull(resultado, "El objeto retornado no debe ser nulo");
            assertNotNull(resultado.getId(), "La base de datos debe asignar un ID al cliente");
            assertEquals("Ramses", resultado.getNombre(), "El nombre debe coincidir");
            assertEquals("ramtaro110@gmail.com", resultado.getCorreo(), "El correo debe persistirse correctamente");

            assertNotNull(resultado.getFecha_registro(), "El PrePersist no funciono");
            assertEquals(LocalDate.now(), resultado.getFecha_registro());
            
        } catch (PersistenciaException e) {
            fail("No deberia lanzar PersistenciaException: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Deberia lanzar excepcion al intentar registrar un cliente nulo")
    public void testAgregarClienteNulo() {
        assertThrows(PersistenciaException.class, () -> {
            clienteDAO.registrarCliente(null);
        }, "Se esperaba PersistenciaException al enviar un objeto nulo");

    }
}
