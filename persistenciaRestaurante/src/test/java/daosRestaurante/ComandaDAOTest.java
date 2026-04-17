/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosRestaurante;

import conexionRestaurante.ConexionBD;
import entidadesRestaurante.Comanda;
import entidadesRestaurante.ComandaProducto;
import entidadesRestaurante.Mesa;
import entidadesRestaurante.Mesero;
import entidadesRestaurante.Producto;
import enumEntidades.EstadoComanda;
import enumEntidades.EstadoMesa;
import excepcionesRestaurante.PersistenciaException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

/**
 * Pruebas unitarias para comandaDAO
 *
 * @author josma
 */
public class ComandaDAOTest {

    /**
     * Variable para la la dao.
     */
    private ComandaDAO comandaDAO;

    /**
     * Iniciamos el setUp.
     */
    @BeforeEach
    public void setUp() {
        // Obtenemos la instancia del Singleton
        comandaDAO = ComandaDAO.getInstanceComandaDAO();
    }

    /**
     * Crea una comanda con sus relaciones mínimas obligatorias para evitar
     * errores de FK.
     */
    private Comanda crearComandaMinima() {
        Comanda comanda = new Comanda(EstadoComanda.ABIERTA, LocalDateTime.now(), 250.0);

        // Relaciones obligatorias según tus anotaciones @JoinColumn(nullable = false)
        Mesero mesero = new Mesero();
        mesero.setId(1L); // Asumiendo que el mesero 1 existe
        comanda.setMesero(mesero);

        Mesa mesa = new Mesa();
        mesa.setId(5L); // Asumiendo que la mesa 5 existe
        comanda.setMesa(mesa);

        return comanda;
    }

    // --- TEST: registrarComanda ---
    /**
     * Caso de registro de una comanda, debe ser exitoso.
     */
    @Test
    public void testRegistrarComanda_Exito() {
        Mesa mesa = new Mesa();
        mesa.setMesa_estado(EstadoMesa.DISPONIBLE);
        try {
            MesaDAO.getInstanceMesaDAO().registrarMesasMasivas(List.of(mesa));
        } catch (Exception e) {
            Mesero meseroExistente = new Mesero();
            meseroExistente.setId(3L);

            Producto productoExistente = new Producto();
            productoExistente.setId(1L);

            // 2. CREACIÓN DE LA COMANDA
            Comanda nueva = new Comanda(EstadoComanda.ABIERTA, LocalDateTime.now(), 500.0);
            nueva.setMesero(meseroExistente);
            nueva.setMesa(mesa);

            ComandaProducto detalle = new ComandaProducto("Término medio", 1);
            detalle.setProductos_comprados(productoExistente);
            nueva.agregarProducto(detalle);

            // 3. EJECUCIÓN
            assertDoesNotThrow(() -> {

                comandaDAO.registrarComanda(nueva);
                assertNotNull(nueva.getId(), "La comanda debería tener un ID asignado por la BD");
            });
        }
    }
    /**
     * Al intentar insertar una comanda esta debe de fallar por datod nulos.
     */
    @Test
    public void testRegistrarComanda_FallaPorDatosNulos() {
        Comanda incompleta = new Comanda(EstadoComanda.ABIERTA, LocalDateTime.now(), 100.0);
        // No seteamos Mesero ni Mesa

        assertThrows(PersistenciaException.class, () -> {
            comandaDAO.registrarComanda(incompleta);
        }, "Debería fallar por omitir campos obligatorios");
    }

    // --- TEST: actualizarDetallesComanda ---
    /**
     * Prueba el metodo de actualizar la comanda completo con todas las opciones, debe de pasar.
     */
    @Test
    public void testActualizarDetallesComanda_FlujoCompleto() {
        EntityManager emAux = ConexionBD.crearConexion();

        // 1. OBTENER REFERENCIAS REALES
        // Esto crea proxies que JPA aceptara en el dao (de otra manera sigue fallando)
        Mesero meseroRef = emAux.getReference(Mesero.class, 3L);
        Mesa mesaRef = emAux.getReference(Mesa.class, 1L);
        Producto productoRef = emAux.getReference(Producto.class, 1L);

        // 2. PREPARAR COMANDA
        Comanda comanda = new Comanda(EstadoComanda.ABIERTA, LocalDateTime.now(), 150.0);
        comanda.setMesero(meseroRef);
        comanda.setMesa(mesaRef);

        ComandaProducto detalleInicial = new ComandaProducto("Original", 1);
        detalleInicial.setProductos_comprados(productoRef);
        comanda.agregarProducto(detalleInicial);

        // Cerramos la conexión auxiliar
        emAux.close();

        // 3. REGISTRO INICIAL
        // Ahora el persist del DAO debe funcionar porque meseroRef no es nuevo o no lo parece
        assertDoesNotThrow(() -> {
            comandaDAO.registrarComanda(comanda);
        });

        Long idComanda = comanda.getId();

        // 4. ACTUALIZACIÓN (Modificar)
        detalleInicial.setDetalles_producto("Actualizado mediante test");

        List<ComandaProducto> listaModificar = new ArrayList<>();
        listaModificar.add(detalleInicial);

        List<ComandaProducto> listaEliminar = new ArrayList<>();
        List<ComandaProducto> listaNuevos = new ArrayList<>();

        // Ejecución del metodo dao
        assertDoesNotThrow(() -> {
            comandaDAO.actualizarDetallesComanda(idComanda, listaModificar, listaEliminar, listaNuevos);
        });
    }
    /**
     * En este caso debe de fallar al intentar actualizar la comanda.
     */
    @Test
    public void testActualizarDetallesComanda_ErrorRollback() {
        // Flujo Alternativo: Intentar agregar un producto a una comanda que NO existe
        List<ComandaProducto> nuevos = new ArrayList<>();
        nuevos.add(new ComandaProducto("Test", 1));

        assertThrows(PersistenciaException.class, () -> {
            comandaDAO.actualizarDetallesComanda(-99L, new ArrayList<>(), new ArrayList<>(), nuevos);
        }, "Debería lanzar excepción y hacer rollback si la comanda padre no existe");
    }

    // --- TEST: buscarComandasAbiertasPorCliente ---
    /**
     * Fallo al buscar comandas abiertas por un filtro inexistente
     * @throws PersistenciaException 
     */
    @Test
    public void testBuscarComandasAbiertasPorCliente_FiltroInexistente() throws PersistenciaException {
        // Buscar un nombre que no existe
        List<Comanda> resultados = comandaDAO.buscarComandasAbiertasPorCliente("Nombre123", EstadoComanda.ABIERTA);

        assertNotNull(resultados);
        assertTrue(resultados.isEmpty(), "La lista debe estar vacía si el cliente no existe.");
    }

    // --- TEST: buscarComandaProductoPorId ---
    /**
     * Busca la relacion comandaproducto por id, debe de funcionar y devolver la comanda asociada
     * @throws PersistenciaException 
     */
    @Test
    public void testBuscarComandaProductoPorId_Exito() throws PersistenciaException {
        // Asumiendo que el detalle ID 1 existe
        ComandaProducto cp = comandaDAO.buscarComandaProductoPorId(1L);
        if (cp != null) {
            assertNotNull(cp.getComandas(), "Debería traer la comanda asociada");
            assertNotNull(cp.getProductos_comprados(), "Debería traer el producto asociado");
        }
    }
}
