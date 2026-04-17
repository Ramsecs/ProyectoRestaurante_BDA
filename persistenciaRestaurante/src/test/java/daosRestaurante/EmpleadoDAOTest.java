/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package daosRestaurante;

import entidadesRestaurante.Administrador;
import entidadesRestaurante.Empleado;
import entidadesRestaurante.Mesero;
import excepcionesRestaurante.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author RAMSES
 */
public class EmpleadoDAOTest {
    
    private final EmpleadoDAO empleadoDAO = EmpleadoDAO.getInstanceEmpleadoDAO();

    @Test
    @DisplayName("ÉXITO: Registro masivo de una lista mixta (Administradores y Meseros)")
    public void testRegistrarMasivoExito() {
        List<Empleado> lista = new ArrayList<>();
        
        lista.add(new Administrador("admin_test", "hash123", "Carlos", "Perez", "Diaz", "6444001122"));
        lista.add(new Mesero("mesero_test", "hash456", "Ana", "Gomez", null, "6444998877"));

        try {
            empleadoDAO.registrarMasivo(lista);
            
            List<Empleado> todos = empleadoDAO.obtenerTodos();
            
            boolean adminEncontrado = todos.stream().anyMatch(e -> "admin_test".equals(e.getNombre_usuario()));
            boolean meseroEncontrado = todos.stream().anyMatch(e -> "mesero_test".equals(e.getNombre_usuario()));

            assertTrue(adminEncontrado, "El administrador debió persistirse");
            assertTrue(meseroEncontrado, "El mesero debió persistirse");
            
        } catch (PersistenciaException e) {
            fail("No debería lanzar excepción: " + e.getMessage());
        }
    }

    @Test
    @DisplayName("FALLO: El registro masivo hace rollback si un elemento es inválido")
    public void testRegistrarMasivoFallo() {
        List<Empleado> lista = new ArrayList<>();
        lista.add(new Mesero("valido", "pass", "Juan", "Cano", "Ruiz", "123"));
        
        // Empleado INVÁLIDO (nombre_usuario nulo viola la restricción nullable=false)
        Empleado invalido = new Administrador();
        invalido.setNombre_usuario(null); 
        invalido.setContrasena("pass");
        invalido.setNombres("Error");
        invalido.setApellido_paterno("Test");
        
        lista.add(invalido);

        assertThrows(PersistenciaException.class, () -> {
            empleadoDAO.registrarMasivo(lista);
        }, "Debería lanzar PersistenciaException debido a la restricción de integridad");
    }

    @Test
    @DisplayName("ÉXITO: Obtener lista completa de empleados")
    public void testObtenerTodosExito() throws PersistenciaException {
        List<Empleado> precarga = new ArrayList<>();
        precarga.add(new Mesero("lista_user_" + System.currentTimeMillis(), "pass", "Test", "List", null, "000"));
        empleadoDAO.registrarMasivo(precarga);

        List<Empleado> resultado = empleadoDAO.obtenerTodos();

        assertNotNull(resultado, "La lista no debe ser nula");
        assertFalse(resultado.isEmpty(), "La lista debe contener al menos un empleado");
    }

    @Test
    @DisplayName("ÉXITO: Comportamiento ante lista vacía")
    public void testObtenerTodosVacio() {
        try {
            List<Empleado> resultado = empleadoDAO.obtenerTodos();
            assertNotNull(resultado, "JPA debe devolver una lista vacía, nunca null");
        } catch (Exception e) {
            fail("No debería lanzar excepción en una consulta simple: " + e.getMessage());
        }
    }
}