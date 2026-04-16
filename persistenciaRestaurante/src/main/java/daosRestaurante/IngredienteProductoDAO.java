/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosRestaurante;

import conexionRestaurante.ConexionBD;
import dtosDelRestaurante.IngredienteDTOLista;
import entidadesRestaurante.Ingrediente;
import entidadesRestaurante.ProductoIngrediente;
import excepcionesRestaurante.PersistenciaException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author RAMSES
 */
public class IngredienteProductoDAO implements IIngredienteProductoDAO{
    
    private static IngredienteProductoDAO ingredienteProductoDAO;

    private IngredienteProductoDAO() {

    }

    /**
     * Obtener instancia de IngredienteProductoDAO.
     * 
     * @return IngredienteProductoDAO.
     */
    public static IngredienteProductoDAO getInstanceIngredienteProductoDAO() {
        if (ingredienteProductoDAO == null) {
            ingredienteProductoDAO = new IngredienteProductoDAO();
        }

        return ingredienteProductoDAO;
    }
    
    /**
     * Con este metodo se obtiene la lista completa de todos los 
     * registros de ingredientes que hay en la base de datos.
     * 
     * @return List.
     * @throws PersistenciaException.
     */
    @Override
    public List<Ingrediente> listarTodo() throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try {
            // Consultamos la entidad completa 'Ingrediente'
            TypedQuery<Ingrediente> query = em.createQuery("SELECT i FROM Ingrediente i", Ingrediente.class);
            return query.getResultList();
            
        } catch (Exception e) {
            
            throw new PersistenciaException("Hubo un error al recuperar la lista de ingredientes en persistencia."+e.getMessage());
            
        } finally {
            em.close();
        }
    }
    
    /**
     * Busca la lista de ingredientes que hay en el producto y 
     * la devuelve en una lista de entidad ProductoIngrediente.
     * 
     * @param id_producto.
     * @return List.
     * @throws PersistenciaException.
     */
    @Override
    public List<ProductoIngrediente> buscarPorProducto(Long id_producto) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            // Añadimos ORDER BY pi.ingredientes.nombre ASC
            return em.createQuery(
                "SELECT pi FROM ProductoIngrediente pi " +
                "WHERE pi.productos.id = :id " +
                "ORDER BY pi.ingredientes.nombre ASC", 
                ProductoIngrediente.class)
                .setParameter("id", id_producto)
                .getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al obtener detalles del producto: " + e.getMessage());
        } finally {
            em.close();
        }
    }
    
    /**
     * Actualiza el objeto de la relacion entre ingrediente
     * y producto, haciendo que la cantidad de
     * cierto ingrediente cambie en base a lo que recibe en 
     * los parametros.
     * 
     * @param id_relacion
     * @param nueva_cantidad
     * @throws PersistenciaException 
     */
    @Override
    public void actualizarCantidad(Long id_relacion, Integer nueva_cantidad) throws PersistenciaException{
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            
            // Buscamos la entidad ProductoIngrediente por su ID
            ProductoIngrediente producto_ingrediente = em.find(ProductoIngrediente.class, id_relacion);
            
            if (producto_ingrediente != null) {
                producto_ingrediente.setCantidad_ingrediente(nueva_cantidad);
                em.merge(producto_ingrediente); // Sincroniza los cambios con la BD
            } else {
                throw new Exception("No se encontró la relación producto-ingrediente.");
            }
            
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenciaException("");
        } finally {
            em.close();
        }
    }
    
}
