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
            // Consulta JPQL para obtener los ingredientes de un producto específico
            return em.createQuery(
                "SELECT pi FROM ProductoIngrediente pi WHERE pi.productos.id = :id", 
                ProductoIngrediente.class)
                .setParameter("id", id_producto)
                .getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al obtener detalles del producto: " + e.getMessage());
        } finally {
            em.close();
        }
    }
    
}
