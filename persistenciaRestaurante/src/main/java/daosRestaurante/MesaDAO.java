/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosRestaurante;

import conexionRestaurante.ConexionBD;
import entidadesRestaurante.Mesa;
import enumEntidades.EstadoComanda;
import excepcionesRestaurante.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 * Esta clase dao permite hacer las consultas directas a la BD
 *
 * @author josma
 */
public class MesaDAO implements IMesaDAO {

    /**
     * Variable statica para la la DAO de mesero.
     */
    private static MesaDAO mesaDAO;

    /**
     * Constructor privado.
     */
    private MesaDAO() {

    }

    /**
     * Obtiene la instancia de la DAO, si no existe la crea
     *
     * @return
     */
    public static MesaDAO getInstanceMesaDAO() {
        if (mesaDAO == null) {
            mesaDAO = new MesaDAO();
        }

        return mesaDAO;
    }

    /**
     * Busca las mesas por id
     *
     * @param id que pertenece a mesa
     * @return
     * @throws PersistenciaException
     */
    @Override
    public Mesa buscarPorId(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            return em.find(Mesa.class, id);

        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar la mesa" + e.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Obtiene una lista de las mesas que tienen una comand abierta
     *
     * @return
     * @throws PersistenciaException
     */
    @Override
    public List<Long> obtenerMesasOcupadas() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            //Buscamos los IDs de las mesas que tengan una comanda abierta
            String comandoJPQL = "SELECT DISTINCT c.mesa.id FROM Comanda c WHERE c.estado_comanda = :estado";

            TypedQuery<Long> query = em.createQuery(comandoJPQL, Long.class);
            query.setParameter("estado", EstadoComanda.ABIERTA);

            return query.getResultList();
        } catch (Exception e) {
            System.err.println("Error al obtener las mesas ocupadas: " + e.getMessage());
            return new ArrayList<>();//Devolvemos una lista vacía para evitar errores tronadores         
        } finally {
            em.close();

        }
    }
    /**
     * Actualiza el estado de la mesa para poder agregarle ptra comanda
     * @param id_mesa de la mesa que tiene la comanda
     * @throws PersistenciaException 
     */

    @Override
    public void cancelarComandaPorMesa(Long id_mesa) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            String comandoJPQL = "UPDATE Comanda c SET c.estado_comanda = :nuevo_estado WHERE c.mesa.id = :mesa_id AND c.estado_comanda = :comanda_abierta";

            int comanda_cancelada = em.createQuery(comandoJPQL)
                    .setParameter("nuevo_estado", EstadoComanda.CANCELADA)
                    .setParameter("mesa_id", id_mesa)
                    .setParameter("comanda_abierta", EstadoComanda.ABIERTA)
                    .executeUpdate();

            em.getTransaction().commit();
            System.out.println("Se cancelo la comanda de la mesa");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al cancelar la comanda " + e.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public void registrarMesasMasivas(List<Mesa> mesas) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();
            for (Mesa mesa : mesas) {
                em.persist(mesa);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Error al registrar el lote de mesas: " + e.getMessage());
        } finally {
            em.close();
        }
    }

}
