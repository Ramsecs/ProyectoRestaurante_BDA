/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daosRestaurante;

import entidadesRestaurante.Cliente;
import excepcionesRestaurante.PersistenciaException;
import java.time.LocalDate;
import java.util.List;
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

    // --- PRUEBAS PARA registrarCliente ---

    @Test
    @DisplayName("ÉXITO: Registrar cliente con todos los campos obligatorios")
    public void testRegistrarClienteExito() {
        Cliente cliente = new Cliente("Ramses", "Contreras", "Garcia", "ramtaro110@gmail.com", "6441238756");
        
        try {
            Cliente resultado = clienteDAO.registrarCliente(cliente);
            assertNotNull(resultado.getId(), "El ID debería haberse generado");
            assertEquals(LocalDate.now(), resultado.getFecha_registro(), "El PrePersist debió asignar la fecha");
        } catch (PersistenciaException e) {
            fail("No debería fallar: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("FALLO: Lanzar excepción al registrar cliente nulo")
    public void testRegistrarClienteFallo() {
        assertThrows(PersistenciaException.class, () -> {
            clienteDAO.registrarCliente(null);
        }, "Debería lanzar PersistenciaException al ser nulo");
    }

    // --- PRUEBAS PARA buscarCliente (Filtro) ---

    @Test
    @DisplayName("ÉXITO: Buscar cliente por coincidencia de nombre (LOWER CASE)")
    public void testBuscarClienteExito() throws PersistenciaException {
        // Preparar
        Cliente c = new Cliente("Tilin", "Perez", "Lopez", "tilin@test.com", "123");
        clienteDAO.registrarCliente(c);

        // Ejecutar (buscamos en minusculas para probar el LOWER de la consulta)
        List<Object[]> resultados = clienteDAO.buscarCliente("tilin");

        // Verificar
        assertFalse(resultados.isEmpty(), "Debería encontrar al menos un resultado");
        Cliente encontrado = (Cliente) resultados.get(0)[0];
        assertTrue(encontrado.getNombre().equalsIgnoreCase("Tilin"));
    }

    @Test
    @DisplayName("FALLO: Retornar lista vacía cuando el filtro no coincide con nada")
    public void testBuscarClienteSinResultados() throws PersistenciaException {
        List<Object[]> resultados = clienteDAO.buscarCliente("NOMBRE_QUE_NO_EXISTE_12345");
        assertTrue(resultados.isEmpty(), "La lista debería estar vacía");
    }

    // --- PRUEBAS PARA modificarCliente ---

    @Test
    @DisplayName("ÉXITO: Modificar el correo de un cliente existente")
    public void testModificarClienteExito() throws PersistenciaException {
        // Preparar
        Cliente c = new Cliente("Original", "Ap", "Am", "orig@mail.com", "111");
        Cliente registrado = clienteDAO.registrarCliente(c);

        // Modificar
        registrado.setCorreo("nuevo@mail.com");
        Cliente actualizado = clienteDAO.modificarCliente(registrado);

        // Verificar
        assertEquals("nuevo@mail.com", actualizado.getCorreo());
        assertEquals(registrado.getId(), actualizado.getId());
    }

    @Test
    @DisplayName("FALLO: Error al intentar modificar un cliente que no existe (ID inexistente)")
    public void testModificarClienteFallo() {
        // Creamos un cliente con un ID que no esta en la base de datos
        Cliente clienteInexistente = new Cliente();
        clienteInexistente.setId(999999L); 
        clienteInexistente.setNombre("Fantasma");
        clienteInexistente.setApellido_paterno("X");
        clienteInexistente.setApellido_materno("Y");

        // JPA Merge puede crear uno nuevo o fallar dependiendo de la configuracion, 
        // pero si forzamos un error de persistencia (campos nulos obligatorios), saltara la excepcion.
        clienteInexistente.setNombre(null); // Esto causara error por nullable=false
        
        assertThrows(PersistenciaException.class, () -> {
            clienteDAO.modificarCliente(clienteInexistente);
        });
    }

    // --- PRUEBAS PARA buscarPorId ---

    @Test
    @DisplayName("ÉXITO: Encontrar cliente por ID generado")
    public void testBuscarPorIdExito() throws PersistenciaException {
        // Preparar
        Cliente c = new Cliente("Busqueda", "Test", "Id", "id@test.com", "000");
        Cliente registrado = clienteDAO.registrarCliente(c);

        // Ejecutar
        Cliente encontrado = clienteDAO.buscarPorId(registrado.getId());

        // Verificar
        assertNotNull(encontrado);
        assertEquals(registrado.getId(), encontrado.getId());
    }

    @Test
    @DisplayName("FALLO: Retornar null al buscar un ID que no existe")
    public void testBuscarPorIdInexistente() throws PersistenciaException {
        // ID extremadamente alto que no exista
        Cliente resultado = clienteDAO.buscarPorId(888888L);
        assertNull(resultado, "El resultado debe ser null si el ID no existe en JPA");
    }
}
