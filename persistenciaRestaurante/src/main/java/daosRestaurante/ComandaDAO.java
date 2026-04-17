/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosRestaurante;

import conexionRestaurante.ConexionBD;
import dtosDelRestaurante.ComandaDTO;
import dtosDelRestaurante.DetalleComandaDTO;
import entidadesRestaurante.Comanda;
import entidadesRestaurante.ComandaProducto;
import entidadesRestaurante.Ingrediente;
import entidadesRestaurante.ProductoIngrediente;
import enumEntidades.EstadoComanda;
import excepcionesRestaurante.PersistenciaException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Clase DAO que permite la administración de la BD con metodos especificos
 *
 * @author josma
 */
public class ComandaDAO implements IComandaDAO {

    /**
     * Variable del DAO.
     */
    private static ComandaDAO comandaDAO;

    /**
     * Constructor privado vacio.
     */
    private ComandaDAO() {

    }

    /**
     * Metodo para singleton
     *
     * @return
     */
    public static ComandaDAO getInstanceComandaDAO() {
        if (comandaDAO == null) {
            comandaDAO = new ComandaDAO();
        }

        return comandaDAO;
    }

    /**
     * Registra la comanda
     *
     * @param comanda del cliente
     * @throws PersistenciaException
     */
    @Override
    public void registrarComanda(Comanda comanda) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();

            em.persist(comanda); // Guardamos la comanda sol
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new PersistenciaException("Error al registrar la comanda " + e.getMessage());
        } finally {
            em.close();
        }
    }
    /**
     * Obtiene las comandas que estan exclusivamente abiertas
     * @param estado pars la comanda
     * @return
     * @throws PersistenciaException 
     */

    /**
     * Obtiene las comandas abiertas que tenemos registradas.
     * 
     * @param estado
     * @return lista de comandas
     * @throws PersistenciaException 
     */
    @Override
    public List<Comanda> obtenerComandasAbiertas(EstadoComanda estado) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            //Aqui usamos el JOIN FETCH hacemos que se traigan tambien las relaciones con comanda y no solo la comanda 
            //Podria ser con left join u otro, probablemente si, pero Fetch me parecio más conveniente
            String comandoJPQL = "SELECT c FROM Comanda c JOIN FETCH c.mesa JOIN FETCH c.cliente WHERE c.estado_comanda = :estado";
            TypedQuery<Comanda> query = em.createQuery(comandoJPQL, Comanda.class);
            query.setParameter("estado", estado);

            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar comandas: " + e.getMessage());
        } finally {
            em.close();
        }
    }
    /**
     * Actualiza el estado de la comanda
     * @param id de la comanda al que se le va a cambiar el estado
     * @param estado al que se va a cambiar el estado
     * @throws PersistenciaException 
     */

    /**
     * Actualiza el estado de la comanda que 
     * tiene el mismo id al estado que 
     * nosotros le damos.
     * 
     * @param id
     * @param estado
     * @throws PersistenciaException 
     */
    @Override
    public void actualizarEstadoComanda(Long id, EstadoComanda estado) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
            Comanda comanda = em.find(Comanda.class, id);
            if (comanda != null) {
                comanda.setEstado_comanda(estado);
                em.merge(comanda);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new PersistenciaException("Error al cambiar el estado en la bd " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Busca las comandas abiertas por 
     * medio de un filtro del nombre del cliente.
     * 
     * @param filtro
     * @param estado_filtro
     * @return lista de comandas
     * @throws PersistenciaException 
     */
    @Override
    public List<Comanda> buscarComandasAbiertasPorCliente(String filtro, EstadoComanda estado_filtro) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String comandoJPQL = "SELECT c FROM Comanda c WHERE c.estado_comanda = :estado "
                    + "AND (LOWER(c.cliente.nombre) LIKE LOWER(:fil) "
                    + "OR LOWER(c.cliente.apellido_paterno) LIKE LOWER(:fil) "
                    + "OR LOWER(c.cliente.apellido_materno) LIKE LOWER(:fil))";

            return em.createQuery(comandoJPQL, Comanda.class)
                    .setParameter("estado", estado_filtro)
                    .setParameter("fil", "%" + filtro + "%")
                    .getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al filtrar comandas en BD: " + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Se actualizan los cambios de la comanda
     * cuando se actualiza, con la lista nueva de productos
     * y si es que se eliminaron productos tambien.
     * 
     * @param id_comanda
     * @param modificar
     * @param eliminar
     * @param nuevos
     * @throws PersistenciaException 
     */
    @Override
    public void actualizarDetallesComanda(Long id_comanda, List<ComandaProducto> modificar, List<ComandaProducto> eliminar, List<ComandaProducto> nuevos) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();

            //1. Eliminar
            for (ComandaProducto comanda_producto : eliminar) {
                ComandaProducto por_borrar = em.find(ComandaProducto.class, comanda_producto.getId());
                if (por_borrar != null) {
                    em.remove(por_borrar);
                }
            }

            // 2. Modificar - merge para actualizar cantidad y detalles_producto
            for (ComandaProducto cp : modificar) {
                em.merge(cp);
            }

            // 3. Insertar Nuevos
            Comanda comandaPadre = em.find(Comanda.class, id_comanda);
            for (ComandaProducto cp : nuevos) {
                cp.setComandas(comandaPadre); // id_comandas
                em.persist(cp);
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al actualizar productos de la comanda: " + e.getMessage());
        } finally {
            em.close();
        }
    }
    /**
     * Regresa todas las comandas
     * @param id_comanda de la comanda
     * @return
     * @throws PersistenciaException 
     */

    
    /**
     * Consulta comanda por medio del id 
     * de la misma comanda, y regresa una 
     * lista de comanda producto.
     * 
     * @param id_comanda
     * @return lista de comanda producto
     * @throws PersistenciaException 
     */
    @Override
    public List<ComandaProducto> consultarPorComanda(Long id_comanda) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String jpql = "SELECT cp FROM ComandaProducto cp JOIN FETCH cp.productos_comprados WHERE cp.comandas.id = :id";
            TypedQuery<ComandaProducto> query = em.createQuery(jpql, ComandaProducto.class);
            query.setParameter("id", id_comanda);
            return query.getResultList();
        } catch (Exception e) {
            throw new PersistenciaException("Error al consultar detalles de la comanda: " + e.getMessage());
        } finally {
            em.close();
        }
    }
    /**
     * Este metodo regresa la relación entre la comanda y el producto
     * @param id de la comandaProducto
     * @return
     * @throws PersistenciaException 
     */

    /**
     * Buscamos la relacion que hay entre
     * comanda y producto mediante el id.
     * 
     * @param id
     * @return ComandaProducto
     * @throws PersistenciaException 
     */
    @Override
    public ComandaProducto buscarComandaProductoPorId(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            return em.find(ComandaProducto.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar el detalle: " + e.getMessage());
        } finally {
            em.close();
        }
    }
    
    /**
     * Actualiza los ingredientes que se van a gastar 
     * para los nuevos productos cuando se hace
     * una modificacion en comanda.
     * 
     * @param id_producto
     * @param cantidad_pedida
     * @throws PersistenciaException 
     */
    @Override
    public void descontarStockPorProducto(Long id_producto, int cantidad_pedida) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();

            // Buscamos la "receta" en tu tabla intermedia ProductoIngrediente
            List<ProductoIngrediente> vinculos = em.createQuery("SELECT pi FROM ProductoIngrediente pi WHERE pi.productos.id = :idProd", 
                ProductoIngrediente.class)
                .setParameter("idProd", id_producto)
                .getResultList();

            if (vinculos.isEmpty()) {
                throw new PersistenciaException("El producto ID " + id_producto + " no tiene ingredientes asociados.");
            }

            // Procesamos cada ingrediente vinculado
            for (ProductoIngrediente pi : vinculos) {
                Ingrediente ing = pi.getIngredientes();
                // cantidad necesaria = (cantidad en ProductoIngrediente) * (cuatos platos se pidieron)
                double cantidadARestar = pi.getCantidad_ingrediente()* cantidad_pedida;

                // Validacion de Stock (Evita que el sistema truene por valores negativos)
                if (ing.getStock() < cantidadARestar) {
                    throw new PersistenciaException("Stock insuficiente de: " + ing.getNombre() + 
                                                    ". Requerido: " + cantidadARestar + ", Disponible: " + ing.getStock());
                }

                // Actualizacion manual del stock
                ing.setStock((int)(ing.getStock()- cantidadARestar));
                em.merge(ing); // Aseguramos que el cambio se guarde
            }

            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            // Lanzamos el error real para que la interfaz sepa que paso
            throw new PersistenciaException("Error de Inventario: " + e.getMessage());
        } finally {
            em.close();
        }
    }

}
