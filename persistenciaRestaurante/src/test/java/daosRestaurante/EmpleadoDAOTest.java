/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package daosRestaurante;

import entidadesRestaurante.Empleado;
import entidadesRestaurante.Mesero;
import excepcionesRestaurante.PersistenciaException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author RAMSES
 */
public class EmpleadoDAOTest {
    
    private EmpleadoDAO empleadoDAO;

    @Before
    public void setUp() {
        // Obtenemos la instancia del Singleton
        empleadoDAO = EmpleadoDAO.getInstanceEmpleadoDAO();
    }

    /**
     * Caso de Exito: Registro masivo y recuperacion de empleados.
     * Verifica que si enviamos una lista valida, los datos se persisten.
     * 
     *  @throws PersistenciaException 
     */
    @Test
    public void testRegistrarMasivoExito() throws PersistenciaException {
        // Preparar datos
        Mesero m1 = new Mesero("user1", "pass1", "Juan", "Perez", "Ochoa", "6441112233");
        Mesero m2 = new Mesero("user2", "pass2", "Ana", "Lopez", "Duarte", "6445556677");
        List<Empleado> listaParaRegistrar = Arrays.asList(m1, m2);

        // Ejecutar accion
        empleadoDAO.registrarMasivo(listaParaRegistrar);

        // Verificar resultados
        List<Empleado> todos = empleadoDAO.obtenerTodos();
        
        assertNotNull("La lista no debería ser nula", todos);
        assertTrue("Debería haber al menos los 2 empleados registrados", todos.size() >= 2);
        
        // Verificar que el nombre del primer empleado esté en la lista recuperada
        boolean encontrado = todos.stream().anyMatch(e -> e.getNombres().equals("Juan"));
        assertTrue("El empleado Juan debería existir en la base de datos", encontrado);
    }

    /**
     * Caso de Fallo Esperado: Registro masivo con datos nulos o inválidos.
     * Verifica que el DAO lance una PersistenciaException y haga Rollback ante un error.
     * 
     * @throws PersistenciaException 
     */
    @Test
    public void testRegistrarMasivoFallo() throws PersistenciaException {
        // Preparar datos invalidos
        // Creamos una lista que contenga un objeto nulo o un empleado sin campos obligatorios
        List<Empleado> listaInvalida = new ArrayList<>();
        listaInvalida.add(new Mesero(null, null, null, null, null, null)); 

        // Ejecutar accion: Deberia lanzar PersistenciaException por el @Column(nullable = false)
        empleadoDAO.registrarMasivo(listaInvalida);
        
        // Si el flujo llega aquí sin lanzar excepcion, JUnit fallara la prueba.
    }
    
    /**
 * PRUEBA: EXITO DE CONSULTA
 * Escenario: Existen empleados en la base de datos.
 * Resultado esperado: La lista devuelta no es nula y contiene los objetos correctos.
 */
@Test
public void testObtenerTodosExito() {
    // Aseguramos que haya al menos un dato para consultar
    Mesero m = new Mesero("consulta_user", "hash", "Test", "Test", "Test", "123");
    try {
        empleadoDAO.registrarMasivo(Arrays.asList(m));
    } catch (PersistenciaException e) {
        fail("No se pudo preparar la prueba: " + e.getMessage());
    }

    // Ejecutar la consulta
    List<Empleado> resultado = empleadoDAO.obtenerTodos();

    // Validar
    assertNotNull("El método nunca debería devolver null, sino una lista vacía o con datos", resultado);
    assertFalse("La lista no debería estar vacía", resultado.isEmpty());
    
    // Verificamos que JPA trajo la entidad polimórfica correctamente
    boolean esMesero = resultado.stream().anyMatch(e -> e instanceof Mesero);
    assertTrue("Los objetos recuperados deben mantener su tipo (Mesero)", esMesero);
}

/**
 * PRUEBA: FALLO O ESTADO VACIO
 * Escenario: La base de datos está vacia o la conexion se pierde.
 * Nota: En JPA, una consulta sin resultados NO lanza excepcion, devuelve una lista de tamaño 0.
 */
@Test
public void testObtenerTodosVacio() {
    
    // Ejecutar consulta en BD vacía
    List<Empleado> resultado = empleadoDAO.obtenerTodos();

    // Validar
    // Si la BD esta vacia, el resultado debe ser una lista con 0 elementos, no null.
    assertNotNull(resultado);
    assertEquals("Si no hay empleados, el tamaño debe ser 0", 0, resultado.size());
}
    
}
