/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit4TestClass.java to edit this template
 */
package daosRestaurante;

import entidadesRestaurante.Ingrediente;
import entidadesRestaurante.Producto;
import entidadesRestaurante.ProductoIngrediente;
import enumEntidades.TipoPlatillo;
import excepcionesRestaurante.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import org.junit.Test;
import static org.junit.Assert.*;
import org.junit.Before;

/**
 *
 * @author RAMSES
 */
public class ProductoDAOTest {
    
    private ProductoDAO productoDAO;

    @Before
    public void setUp() {
        productoDAO = ProductoDAO.getInstanceProductoDAO();
    }

    // ============================================================
    // 1. PRUEBAS PARA listarTodo()
    // ============================================================
    
    @Test
    public void testListarTodoExito() throws PersistenciaException {
        List<Producto> productos = productoDAO.listarTodo();
        assertNotNull("La lista no debe ser nula", productos);
        // Exito si la consulta corre sin excepciones, incluso si la lista esta vacia
    }

    // ============================================================
    // 2. PRUEBAS PARA registrarProductoConIngredientes()
    // ============================================================
    
    @Test
    public void testRegistrarProductoExito() throws PersistenciaException {
        // Preparar Producto
        Producto producto = new Producto();
        producto.setNombre("Tacos al Pastor " + System.currentTimeMillis()); // Nombre unico
        producto.setPrecio(85.0);
        producto.setTipo_platilo(TipoPlatillo.PLATILLO);

        // Preparar Ingrediente (Asumimos que ID 1 existe en BD)
        Ingrediente ing = new Ingrediente();
        ing.setId(1L); 

        ProductoIngrediente detalle = new ProductoIngrediente();
        detalle.setCantidad_ingrediente(3);
        detalle.setIngredientes(ing);

        List<ProductoIngrediente> detalles = new ArrayList<>();
        detalles.add(detalle);

        boolean result = productoDAO.registrarProductoConIngredientes(producto, detalles);
        assertTrue("Deberia registrarse correctamente", result);
    }

    @Test(expected = PersistenciaException.class)
    public void testRegistrarProductoFalloDuplicado() throws PersistenciaException {
        Producto producto = new Producto();
        producto.setNombre("Soda Fija"); // Usar un nombre que ya sepas que existe
        producto.setPrecio(20.0);
        producto.setTipo_platilo(TipoPlatillo.BEBIDA);

        // Intentar registrar dos veces el mismo nombre
        productoDAO.registrarProductoConIngredientes(producto, new ArrayList<>());
        productoDAO.registrarProductoConIngredientes(producto, new ArrayList<>());
    }

    // ============================================================
    // 3. PRUEBAS PARA actualizarPrecio()
    // ============================================================
    
    @Test
    public void testActualizarPrecioExito() throws PersistenciaException {
        // Asumimos que el producto ID 1 existe
        productoDAO.actualizarPrecio(1L, 99.99);
        
        // Verificacion logica
        Producto p = productoDAO.listarTodo().stream().filter(x -> x.getId().equals(1L)).findFirst().orElse(null);
        if(p != null) assertEquals(99.99, p.getPrecio(), 0.001);
    }

    @Test(expected = PersistenciaException.class)
    public void testActualizarPrecioFalloIdInexistente() throws PersistenciaException {
        // Intentar actualizar un precio con ID que no existe
        productoDAO.actualizarPrecio(-1L, 50.0);
    }

    // ============================================================
    // 4. PRUEBAS PARA actualizarNombre()
    // ============================================================
    
    @Test
    public void testActualizarNombreExito() throws PersistenciaException {
        // Cambiar nombre al ID 1
        String nuevoNombre = "Nombre Editado";
        productoDAO.actualizarNombre(1L, nuevoNombre);
        
        boolean existe = productoDAO.listarTodo().stream().anyMatch(p -> p.getNombre().equals(nuevoNombre));
        assertTrue("El nombre deberia haber cambiado", existe);
    }

    @Test(expected = PersistenciaException.class)
    public void testActualizarNombreFalloInexistente() throws PersistenciaException {
        // Este fallara porque el DAO lanza PersistenciaException si filasActualizadas == 0
        productoDAO.actualizarNombre(9999L, "NoExiste");
    }

    // ============================================================
    // 5. PRUEBAS PARA consultarPorCategoria()
    // ============================================================
    
    @Test
    public void testConsultarPorCategoriaExito() throws PersistenciaException {
        List<Producto> bebidas = productoDAO.consultarPorCategoria(TipoPlatillo.BEBIDA);
        assertNotNull(bebidas);
        for(Producto p : bebidas) {
            assertEquals(TipoPlatillo.BEBIDA, p.getTipo_platilo());
        }
    }

    @Test
    public void testConsultarPorCategoriaVacia() throws PersistenciaException {
        // Si no hay postres, debe devolver lista vacía, no lanzar error
        List<Producto> resultados = productoDAO.consultarPorCategoria(TipoPlatillo.POSTRE);
        assertNotNull(resultados);
        assertEquals(0, resultados.size());
    }
    
}
