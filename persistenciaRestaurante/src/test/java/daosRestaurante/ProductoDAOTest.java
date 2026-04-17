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

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import static org.junit.jupiter.api.Assertions.*;

/**
 *
 * @author RAMSES
 */
public class ProductoDAOTest {

    private ProductoDAO productoDAO;

    @BeforeEach
    public void setUp() {
        productoDAO = ProductoDAO.getInstanceProductoDAO();
    }

    // ============================================================
    // 1. registrarProductoConIngredientes
    // ============================================================
    
    @Test
    @DisplayName("EXITO: Registrar producto nuevo con ingredientes")
    public void testRegistrarProductoExito() throws PersistenciaException {
        Producto producto = new Producto();
        producto.setNombre("Tacos de Pastor " + System.currentTimeMillis());
        producto.setPrecio(95.0);
        producto.setTipo_platilo(TipoPlatillo.PLATILLO);
        producto.setRuta_imagen("img/tacos.png");

        // Detalle de ingrediente (Asumiendo que ID 1 existe en DB)
        Ingrediente ing = new Ingrediente();
        ing.setId(1L); 

        ProductoIngrediente detalle = new ProductoIngrediente();
        detalle.setCantidad_ingrediente(3);
        detalle.setIngredientes(ing);

        List<ProductoIngrediente> detalles = new ArrayList<>();
        detalles.add(detalle);

        boolean result = productoDAO.registrarProductoConIngredientes(producto, detalles);
        assertTrue(result, "El producto deberia registrarse con exito");
    }

    @Test
    @DisplayName("FALLO: Registrar producto con nombre ya existente")
    public void testRegistrarProductoFalloDuplicado() throws PersistenciaException {
        String nombreFijo = "Producto Duplicado Test";
        Producto p1 = new Producto();
        p1.setNombre(nombreFijo);
        p1.setPrecio(10.0);
        p1.setTipo_platilo(TipoPlatillo.BEBIDA);
        p1.setRuta_imagen("test.png");

        // Primer registro exitoso o ignorado si ya existe
        try { productoDAO.registrarProductoConIngredientes(p1, new ArrayList<>()); } catch (Exception e) {}

        // Segundo registro debe fallar por nombre duplicado
        assertThrows(PersistenciaException.class, () -> {
            productoDAO.registrarProductoConIngredientes(p1, new ArrayList<>());
        }, "Deberia lanzar PersistenciaException por nombre duplicado");
    }

    // ============================================================
    // 2. actualizarPrecio
    // ============================================================

    @Test
    @DisplayName("EXITO: Actualizar precio de producto existente")
    public void testActualizarPrecioExito() throws PersistenciaException {
        // Obtenemos el primero de la lista para asegurar que el ID existe
        List<Producto> lista = productoDAO.listarTodo();
        if(!lista.isEmpty()){
            Long id = lista.get(0).getId();
            productoDAO.actualizarPrecio(id, 150.0);
            
            Producto actualizado = productoDAO.buscarPorId(id);
            assertEquals(150.0, actualizado.getPrecio(), "El precio deberia haber cambiado a 150.0");
        }
    }

    @Test
    @DisplayName("FALLO: Actualizar precio de ID que no existe")
    public void testActualizarPrecioFalloIdInexistente() {
        assertThrows(PersistenciaException.class, () -> {
            productoDAO.actualizarPrecio(-99L, 50.0);
        }, "Deberia fallar al no encontrar el ID");
    }

    // ============================================================
    // 3. actualizarNombre
    // ============================================================

    @Test
    @DisplayName("EXITO: Actualizar nombre de producto")
    public void testActualizarNombreExito() throws PersistenciaException {
        List<Producto> lista = productoDAO.listarTodo();
        if(!lista.isEmpty()){
            Long id = lista.get(0).getId();
            String nuevoNombre = "Nombre Actualizado " + System.currentTimeMillis();
            productoDAO.actualizarNombre(id, nuevoNombre);
            
            Producto actualizado = productoDAO.buscarPorId(id);
            assertEquals(nuevoNombre, actualizado.getNombre());
        }
    }

    @Test
    @DisplayName("FALLO: Actualizar nombre de ID inexistente")
    public void testActualizarNombreFalloInexistente() {
        assertThrows(PersistenciaException.class, () -> {
            productoDAO.actualizarNombre(-1L, "Error");
        });
    }

    // ============================================================
    // 4. consultarPorCategoria
    // ============================================================

    @Test
    @DisplayName("EXITO: Consultar productos por categoria BEBIDA")
    public void testConsultarPorCategoriaExito() throws PersistenciaException {
        List<Producto> resultados = productoDAO.consultarPorCategoria(TipoPlatillo.BEBIDA);
        assertNotNull(resultados);
        // Validamos que todos los devueltos sean BEBIDA
        assertTrue(resultados.stream().allMatch(p -> p.getTipo_platilo() == TipoPlatillo.BEBIDA));
    }

    @Test
    @DisplayName("FALLO/VACIO: Consultar categoria sin productos")
    public void testConsultarPorCategoriaVacia() throws PersistenciaException {
        // Si no tienes POSTREs en la base, debería devolver una lista vacía, no null ni error
        List<Producto> resultados = productoDAO.consultarPorCategoria(TipoPlatillo.POSTRE);
        assertNotNull(resultados, "JPA siempre debe devolver una lista (aunque sea vacia)");
    }

    // ============================================================
    // 5. buscarPorId
    // ============================================================

    @Test
    @DisplayName("EXITO: Buscar producto por ID valido")
    public void testBuscarPorIdExito() throws PersistenciaException {
        List<Producto> lista = productoDAO.listarTodo();
        if(!lista.isEmpty()){
            Long id = lista.get(0).getId();
            Producto producto = productoDAO.buscarPorId(id);
            assertNotNull(producto);
            assertEquals(id, producto.getId());
        }
    }

    @Test
    @DisplayName("FALLO: Buscar producto por ID inexistente")
    public void testBuscarPorIdFallo() throws PersistenciaException {
        Producto p = productoDAO.buscarPorId(-500L);
        assertNull(p, "em.find debe devolver null si el objeto no existe");
    }
    
    // ============================================================
    // 6. listarTodo
    // ============================================================

    @Test
    @DisplayName("EXITO: Listar todos los productos")
    public void testListarTodoExito() throws PersistenciaException {
        // Primero nos aseguramos de que haya algo, si no, el éxito es que no truene
        List<Producto> productos = productoDAO.listarTodo();
        
        assertNotNull(productos, "La lista devuelta por el DAO nunca debe ser nula");
        // Nota: El test es exitoso si la consulta se ejecuta correctamente, 
        // independientemente de si la lista tiene 0 o 100 elementos.
        System.out.println("Productos encontrados: " + productos.size());
    }
}