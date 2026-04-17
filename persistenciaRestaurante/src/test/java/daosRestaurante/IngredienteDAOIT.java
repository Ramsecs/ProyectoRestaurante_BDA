/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/UnitTests/JUnit5TestClass.java to edit this template
 */
package daosRestaurante;

import entidadesRestaurante.Ingrediente;
import entidadesRestaurante.Producto;
import enumEntidades.UnidadMedida;
import excepcionesRestaurante.PersistenciaException;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.DisplayName;

/**
 *
 * @author DANIEL
 */
public class IngredienteDAOIT {

    /** Interfaz del DAO a testear, inicializada mediante el Singleton de la implementación. */
    private static IIngredienteDAO ingredienteDAO;

    /**
     * Constructor por defecto de la clase de prueba.
     */
    public IngredienteDAOIT() {
    }

    /**
     * Configuración inicial antes de ejecutar cualquier prueba de la clase.
     * Inicializa la instancia del DAO para ser reutilizada en todos los métodos de test.
     */
    @BeforeAll
    static void setUp() {
        ingredienteDAO = IngredienteDAO.getInstanceIngredientesDAO();
    }

    /**
     * Prueba el método registrarIngrediente.
     * Verifica que el objeto sea persistido correctamente y que la base de datos 
     * retorne un ID generado automáticamente.
     * 
     * @throws PersistenciaException Si ocurre un error inesperado en la comunicación.
     */
    @Test
    @DisplayName("Debería registrar un ingrediente exitosamente")
    void testRegistrarIngrediente() throws PersistenciaException {
        Ingrediente nuevo = new Ingrediente("Tomate", 10, UnidadMedida.KILOS);
        
        Ingrediente resultado = ingredienteDAO.registrarIngrediente(nuevo);
        
        assertNotNull(resultado.getId(), "El ID no debería ser nulo tras registrar");
        assertEquals("Tomate", resultado.getNombre());
    }

    /**
     * Prueba el método buscarIngrediente con filtros.
     * Valida que la consulta JPQL con LIKE e ignorescase funcione correctamente 
     * al buscar por coincidencias parciales.
     * 
     * @throws PersistenciaException Si la sentencia JPQL tiene errores de sintaxis.
     */
    @Test
    @DisplayName("Debería buscar ingredientes por filtro de nombre")
    void testBuscarIngrediente() throws PersistenciaException {
        ingredienteDAO.registrarIngrediente(new Ingrediente("Cebolla Morada", 5, UnidadMedida.KILOS));
        
        List<Ingrediente> lista = ingredienteDAO.buscarIngrediente("cebolla");
        
        assertFalse(lista.isEmpty(), "La lista no debería estar vacía");
        assertTrue(lista.stream().anyMatch(i -> i.getNombre().contains("Cebolla")));
    }

    /**
     * Prueba la recuperación de un registro específico mediante su llave primaria.
     * Verifica que el método em.find() devuelva el objeto exacto solicitado.
     * 
     * @throws PersistenciaException Si el ID no es procesable.
     */
    @Test
    @DisplayName("Debería buscar un ingrediente por su ID")
    void testBuscarPorId() throws PersistenciaException {
        Ingrediente aux = ingredienteDAO.registrarIngrediente(new Ingrediente("Lechuga", 2, UnidadMedida.PIEZA));
        
        Ingrediente encontrado = ingredienteDAO.buscarPorId(aux.getId());
        
        assertNotNull(encontrado);
        assertEquals(aux.getId(), encontrado.getId());
        assertEquals("Lechuga", encontrado.getNombre());
    }

    /**
     * Prueba la actualización de datos (merge) de un ingrediente.
     * Modifica el stock de un objeto persistido y valida que el cambio se mantenga 
     * después de la transacción.
     * 
     * @throws PersistenciaException Si falla la sincronización con la base de datos.
     */
    @Test
    @DisplayName("Debería modificar el stock de un ingrediente")
    void testModificarIngrediente() throws PersistenciaException {
        Ingrediente original = ingredienteDAO.registrarIngrediente(new Ingrediente("Papas", 50, UnidadMedida.KILOS));
        
        original.setStock(100);
        Ingrediente modificado = ingredienteDAO.modificarIngrediente(original);
        
        assertEquals(100, modificado.getStock(), "El stock debería haberse actualizado a 100");
    }

    /**
     * Prueba la lógica de verificación de duplicados.
     * Valida que el sistema identifique correctamente cuando una combinación de 
     * nombre y unidad ya existe en los registros.
     * 
     * @throws PersistenciaException Si la consulta de conteo falla.
     */
    @Test
    @DisplayName("Debería verificar si un ingrediente existe por nombre y unidad")
    void testExisteIngrediente() throws PersistenciaException {
        String nombre = "Sal de Mar";
        UnidadMedida unidad = UnidadMedida.GRAMOS;
        ingredienteDAO.registrarIngrediente(new Ingrediente(nombre, 1000, unidad));
        
        boolean existe = ingredienteDAO.existeIngrediente(nombre, unidad);
        boolean noExiste = ingredienteDAO.existeIngrediente("Producto Inexistente", unidad);
        
        assertTrue(existe, "Debería retornar true para un ingrediente existente");
        assertFalse(noExiste, "Debería retornar false para un ingrediente que no existe");
    }

    /**
     * Prueba la robustez del DAO ante datos erróneos.
     * Verifica que el bloque catch del DAO atrape excepciones de JPA y las 
     * relance como PersistenciaException.
     */
    @Test
    @DisplayName("Debería lanzar PersistenciaException ante un error (ejemplo: objeto nulo)")
    void testRegistrarFalla() {
        assertThrows(PersistenciaException.class, () -> {
            ingredienteDAO.registrarIngrediente(null);
        }, "Debería lanzar una excepción al intentar registrar un nulo");
    }
    
    /**
     * Prueba el descuento de stock basado en una comanda.
     * Crea un producto con una receta (ProductoIngrediente) y descuenta el stock
     * multiplicando la cantidad de la receta por la cantidad de platos pedidos.
     */
    @Test
    @DisplayName("Debería descontar stock correctamente al procesar una comanda")
    void testProcesarStockDeComanda() throws PersistenciaException {
        Ingrediente carne = ingredienteDAO.registrarIngrediente(new Ingrediente("Carne", 5000, UnidadMedida.GRAMOS));
        
        Producto hamburguesa = new Producto();
        hamburguesa.setNombre("Hamburguesa Pro");
        
        entidadesRestaurante.ProductoIngrediente receta = new entidadesRestaurante.ProductoIngrediente();
        receta.setIngredientes(carne);
        receta.setCantidad_ingrediente(250);
        hamburguesa.setLista_ingredientes(java.util.List.of(receta));
        
        entidadesRestaurante.Comanda comanda = new entidadesRestaurante.Comanda();
        entidadesRestaurante.ComandaProducto detalle = new entidadesRestaurante.ComandaProducto();
        detalle.setProductos_comprados(hamburguesa);
        detalle.setCant_cada_producto(2);
        comanda.setLista_productos(java.util.List.of(detalle));
        
        boolean exito = ingredienteDAO.procesarStockDeComanda(comanda);
        
        Ingrediente carneActualizada = ingredienteDAO.buscarPorId(carne.getId());
        assertTrue(exito, "El procesamiento debería ser exitoso");
        assertEquals(4500, carneActualizada.getStock(), "El stock debería ser 4500 (5000 - (250*2))");
    }

    /**
     * Prueba que el sistema no descuente nada y devuelva false si no hay stock suficiente.
     */
    @Test
    @DisplayName("Debería fallar el procesamiento si el stock es insuficiente")
    void testProcesarStockInsuficiente() throws PersistenciaException {
        Ingrediente caviar = ingredienteDAO.registrarIngrediente(new Ingrediente("Caviar", 10, UnidadMedida.GRAMOS));
        
        Producto platoLujo = new Producto();
        entidadesRestaurante.ProductoIngrediente receta = new entidadesRestaurante.ProductoIngrediente();
        receta.setIngredientes(caviar);
        receta.setCantidad_ingrediente(100);
        platoLujo.setLista_ingredientes(java.util.List.of(receta));
        
        entidadesRestaurante.Comanda comanda = new entidadesRestaurante.Comanda();
        entidadesRestaurante.ComandaProducto detalle = new entidadesRestaurante.ComandaProducto();
        detalle.setProductos_comprados(platoLujo);
        detalle.setCant_cada_producto(1);
        comanda.setLista_productos(java.util.List.of(detalle));
        
        boolean exito = ingredienteDAO.procesarStockDeComanda(comanda);
        
        assertFalse(exito, "Debería retornar false por falta de stock");
        assertEquals(10, ingredienteDAO.buscarPorId(caviar.getId()).getStock(), "El stock no debería haber cambiado");
    }

    /**
     * Prueba la reposición de stock cuando se cancelan productos.
     */
    @Test
    @DisplayName("Debería reponer stock correctamente tras una cancelación")
    void testReponerStockPorCancelacion() throws PersistenciaException {
        Ingrediente pan = ingredienteDAO.registrarIngrediente(new Ingrediente("Pan", 10, UnidadMedida.PIEZA));
        
        Producto sandwich = new Producto();
        entidadesRestaurante.ProductoIngrediente receta = new entidadesRestaurante.ProductoIngrediente();
        receta.setIngredientes(pan);
        receta.setCantidad_ingrediente(2); // 2 panes por sandwich
        sandwich.setLista_ingredientes(java.util.List.of(receta));
        
        entidadesRestaurante.ComandaProducto detalleCancelado = new entidadesRestaurante.ComandaProducto();
        detalleCancelado.setProductos_comprados(sandwich);
        detalleCancelado.setCant_cada_producto(3); // Se cancelaron 3 sandwiches (Total 6 panes)

        boolean exito = ingredienteDAO.reponerStockPorCancelacion(java.util.List.of(detalleCancelado));
        
        Ingrediente panRepuesto = ingredienteDAO.buscarPorId(pan.getId());
        assertTrue(exito);
        assertEquals(16, panRepuesto.getStock(), "El stock debería ser 16 (10 iniciales + 6 devueltos)");
    }
}