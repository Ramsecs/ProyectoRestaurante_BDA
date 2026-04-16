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

}
