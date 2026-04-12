/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosRestaurante;

import conexionRestaurante.ConexionBD;
import dtosDelRestaurante.ProductoDTO;
import entidadesRestaurante.Ingrediente;
import entidadesRestaurante.Producto;
import entidadesRestaurante.ProductoIngrediente;
import enumEntidades.TipoPlatillo;
import excepcionesRestaurante.PersistenciaException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author RAMSES
 */
public class ProductoDAO implements IProductoDAO{
    
    private static ProductoDAO productoDAO;

    private ProductoDAO() {

    }

    public static ProductoDAO getInstanceProductoDAO() {
        if (productoDAO == null) {
            productoDAO = new ProductoDAO();
        }

        return productoDAO;
    }
    
    /**
     * Este metodo regresa la lista de productos que hay en la base de datos.
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public List<Producto> listarTodo() throws PersistenciaException{
        
        EntityManager ma = ConexionBD.crearConexion();
        try {
            // Consultamos la entidad completa
            return ma.createQuery("SELECT p FROM Producto p", Producto.class).getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Error al consultar productos: " + ex.getMessage());
        } finally {
            ma.close();
        }
        
        
    }
    
    /**
     * Este metodo registra el producto con su lista de ingredientes.
     * @param nuevoProducto
     * @param detalles
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public boolean registrarProductoConIngredientes(Producto nuevoProducto, List<ProductoIngrediente> detalles) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();

            // Buscamos si ya existe un producto con el mismo nombre
            Long conteo = em.createQuery(
                "SELECT COUNT(p) FROM Producto p WHERE LOWER(p.nombre) = LOWER(:nombre)", Long.class).setParameter("nombre", nuevoProducto.getNombre())
                .getSingleResult();

            if (conteo > 0) {
                // Si el nombre ya existe, lanzamos una excepción para parar
                throw new PersistenciaException("Ya existe un producto registrado con el nombre: " + nuevoProducto.getNombre());
            }

            //Asociamos cada detalle al producto y buscamos el ingrediente real
            for (ProductoIngrediente detalle : detalles) {
                Ingrediente ingredienteDB = em.find(Ingrediente.class, detalle.getIngredientes().getId());

                if (ingredienteDB == null) {
                    throw new PersistenciaException("El ingrediente con ID " + detalle.getIngredientes().getId() + " ya no existe.");
                }

                detalle.setProductos(nuevoProducto);
                detalle.setIngredientes(ingredienteDB);
            }

            //Asignamos la lista de detalles al producto
            nuevoProducto.setLista_ingredientes(detalles);

            //Persistimos el producto. 
            em.persist(nuevoProducto);

            em.getTransaction().commit();
            return true;

        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenciaException("Error al registrar producto: " + e.getMessage());
        } finally {
            em.close();
        }
    }
    
    @Override
    public void actualizarPrecio(Long id, Double nuevoPrecio) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            em.createQuery("UPDATE Producto p SET p.precio = :precio WHERE p.id = :id").setParameter("precio", nuevoPrecio)
              .setParameter("id", id)
              .executeUpdate();
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            throw new PersistenciaException("Error al actualizar precio");
        } finally {
            em.close();
        }
    }
    
    @Override
    public void actualizarNombre(Long id, String nuevoNombre) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();

            // Ejecutamos un UPDATE directo sobre el atributo 'nombre'
            int filasActualizadas = em.createQuery(
                "UPDATE Producto p SET p.nombre = :nuevoNombre WHERE p.id = :id").setParameter("nuevoNombre", nuevoNombre)
                .setParameter("id", id)
                .executeUpdate();

            em.getTransaction().commit();

            if (filasActualizadas == 0) {
                throw new PersistenciaException("No se encontró el producto con ID: " + id);
            }

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al actualizar el nombre del producto: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public List<Producto> consultarPorCategoria(TipoPlatillo tipo) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try{
            //1.Creamos la consulta con la entidad Producto, usaremos TypedQuery
            String comandoJPQL = "SELECT p FROM Producto p WHERE p.tipo_platilo = :platillo_tipo";
            
            TypedQuery<Producto> query = em.createQuery(comandoJPQL, Producto.class);
            query.setParameter("platillo_tipo", tipo);
            
            return query.getResultList();
        }catch(Exception e){
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error en ProductoDAO.consultarPorCategotia" + e.getMessage());
        }finally{
            em.close();
        }
    }
    
}
