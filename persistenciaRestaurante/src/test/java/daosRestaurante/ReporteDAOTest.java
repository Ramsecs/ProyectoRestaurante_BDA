/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosRestaurante;

import entidadesRestaurante.Comanda;
import java.time.LocalDateTime;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 *Test para ReporteDAO
 * @author josma
 */
public class ReporteDAOTest {
    /**
     * Variable privada para el singleton.
     */
    private ReporteDAO reporteDAO;
    /**
     * Preparamos la instancia con la que se va a trabajar.
     */
    @BeforeEach
    public void setUp() {
        // Obtenemos la instancia del Singleton antes de cada test
        reporteDAO = ReporteDAO.getInstanceReporteDAO();
    }
    
    /**
     * Prueba para consultarComandasPorPeriodo.
     */
    @Test
    public void testConsultarComandasPorPeriodo() {
        // Rango de fechas: Ajusta según tus datos en la BD para que devuelva algo
        LocalDateTime inicio = LocalDateTime.of(2026, 4, 15, 0, 0, 0);
        LocalDateTime fin = LocalDateTime.now();

        List<Comanda> resultado = reporteDAO.consultarComandasPorPeriodo(inicio, fin);

        assertNotNull(resultado, "La lista de comandas no debería ser nula");
        
        // Si hay resultados, verificamos que el orden sea descendente por fecha
        if (resultado.size() > 1) {
            LocalDateTime fecha1 = resultado.get(0).getFecha_hora_creacion();
            LocalDateTime fecha2 = resultado.get(1).getFecha_hora_creacion();
            assertTrue(fecha1.isAfter(fecha2) || fecha1.isEqual(fecha2), 
                "Las comandas deben estar ordenadas por fecha de forma descendente");
        }
    }

    /**
     * Prueba para consultarReporteClientes.
     * Verifica que el filtro de nombre y el mínimo de visitas funcionen.
     */
    @Test
    public void testConsultarReporteClientes() {
        String filtroNombre = ""; // Filtro vacío para traer todos o "A" para específicos
        int minVisitas = 0;

        List<Object[]> reporte = reporteDAO.consultarReporteClientes(filtroNombre, minVisitas);

        assertNotNull(reporte, "El reporte de clientes no debería ser nulo");

        for (Object[] fila : reporte) {
            assertEquals(5, fila.length, "Cada fila debe tener 5 columnas");
            assertNotNull(fila[0], "El nombre no debe ser nulo");
            assertTrue((Long) fila[2] >= minVisitas, "Las visitas deben ser mayores o iguales al mínimo");
        }
    }

    /**
     * Caso de prueba con filtro específico que probablemente no exista.
     */
    @Test
    public void testConsultarReporteClientesSinResultados() {
        String nombreInexistente = "ZZZ_NONEXISTENT_NAME_XYZ";
        int minVisitas = 1000; // Un número de visitas imposible

        List<Object[]> reporte = reporteDAO.consultarReporteClientes(nombreInexistente, minVisitas);

        assertNotNull(reporte);
        assertTrue(reporte.isEmpty(), "La lista debería estar vacía para criterios irreales");
    }
}
