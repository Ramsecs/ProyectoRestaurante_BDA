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
@Test
    @DisplayName("Debe buscar clientes por filtro")
    public void testBuscarCliente() {
        String filtro = "Ramses";

        try {
            List<Object[]> resultados = clienteDAO.buscarCliente(filtro);

            assertNotNull(resultados, "La lista de resultados no deberia ser nula");
            if (!resultados.isEmpty()) {
                Object[] fila = resultados.get(0);
    
                assertTrue(fila[0] instanceof Cliente, "El primer elemento debe ser un objeto Cliente");
                Cliente c = (Cliente) fila[0];
                assertTrue(c.getNombre().toLowerCase().contains(filtro) || 
                           c.getCorreo().toLowerCase().contains(filtro));
                
                System.out.println("Cliente encontrado: " + c.getNombre() + " Comandas: " + fila[1]);
            }
        } catch (PersistenciaException e) {
            fail("Error al buscar cliente: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Debe modificar los datos de un cliente existente")
    public void testModificarCliente() {
        try {
            Long idExistente = 1L; 
            Cliente cliente = clienteDAO.buscarPorId(idExistente);
            
            assertNotNull(cliente, "Para probar modificacion debe existir el ID 1 en la BD");

            String nuevoNombre = "Tilin";
            cliente.setNombre(nuevoNombre);

            Cliente actualizado = clienteDAO.modificarCliente(cliente);

            assertEquals(nuevoNombre, actualizado.getNombre(), "El nombre no se actualizo correctamente");
            
        } catch (PersistenciaException e) {
            fail("Error al modificar cliente: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("Debe encontrar un cliente por su ID")
    public void testBuscarPorId() {
        try {
            Long id = 1L;
            Cliente encontrado = clienteDAO.buscarPorId(id);

            assertNotNull(encontrado, "El cliente con ID " + id + " debe existir");
            assertEquals(id, encontrado.getId(), "El ID retornado no coincide con el buscado");
            
        } catch (PersistenciaException e) {
            fail("Error al buscar por ID: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("retorna null a un id que no existe")
    public void testBuscarPorIdInexistente() {
        try {
            Cliente resultado = clienteDAO.buscarPorId(873102973L);
            assertNull(resultado, "Debe retornar null para el id inexistente");
        } catch (PersistenciaException e) {
            fail("debe retornar null");
        }
    }
}
