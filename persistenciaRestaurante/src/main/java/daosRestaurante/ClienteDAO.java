/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosRestaurante;

import conexionRestaurante.ConexionBD;
import entidadesRestaurante.Cliente;
import excepcionesRestaurante.PersistenciaException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author RAMSES y JOSMARA
 */
public class ClienteDAO implements IClienteDAO {

    private static ClienteDAO clienteDAO;

    private ClienteDAO() {

    }

    public static ClienteDAO getInstanceClienteDAO() {
        if (clienteDAO == null) {
            clienteDAO = new ClienteDAO();
        }

        return clienteDAO;
    }

    /**
     * Mediante este metodo registramos el cliente dentro de la base de datos,
     * incluyendo los clientes frecuentes ya que siendo el cliente la clase
     * padre es posible registrar tambien el cliente frecuente
     *
     * @param cliente
     * @return Cliente
     * @throws PersistenciaException
     */
    @Override
    public Cliente registrarCliente(Cliente cliente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {

            em.getTransaction().begin();
            em.persist(cliente);
            em.getTransaction().commit();
            return cliente;

        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new PersistenciaException("Ocurrio un error al querer registrar el cliente.");
        } finally {
            em.close();
        }
    }

    /**
     * Este metodo permite hacer la busqueda de los clientes que coincidan con
     * el string de busqueda, que busca por nombre, apellidos (implementación
     * extra), correo y teléfono.
     *
     * @param filtro_busqueda
     * @return List
     * @throws PersistenciaException
     */
    @Override
    public List<Object[]> buscarCliente(String filtro_busqueda) throws PersistenciaException {

        EntityManager em = ConexionBD.crearConexion();
        //LOWER nos sirve para que NO importe si se escriben mayusculas o minusculas
        String sentenciaJPQL = "SELECT c, COUNT(com), SUM(com.total_venta) "
                + "FROM Cliente c LEFT JOIN c.comandas com "
                + "WHERE (LOWER(c.nombre) LIKE :filtro OR "
                + "LOWER(c.apellido_paterno) LIKE :filtro OR "
                + "LOWER(c.apellido_materno) LIKE :filtro OR "
                + "LOWER(c.correo) LIKE :filtro OR "
                + "c.telefono LIKE :filtro) "
                + "GROUP BY c";

        try {

            TypedQuery<Object[]> query = em.createQuery(sentenciaJPQL, Object[].class);
            //El % nos ayuda a que se permita buscar por asi decir lo que sea antes y despues 
            //del texto
            query.setParameter("filtro", "%" + filtro_busqueda.toLowerCase() + "%");
            //Devolvemos la lista con los resultados del filtro de busqueda
            return query.getResultList();

        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new PersistenciaException("Ocurrio un error al querer aplicar los filtros de busqueda");
        } finally {
            em.close();
        }

    }

    /**
     * Mediante este metodo se permite modifcar al cliente con EntityManager
     *
     * @param cliente
     * @return Cliente
     * @throws PersistenciaException
     */
    @Override
    public Cliente modificarCliente(Cliente cliente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {

            em.getTransaction().begin();
            em.merge(cliente);
            em.getTransaction().commit();
            return cliente;

        } catch (Exception ex) {
            em.getTransaction().rollback();
            throw new PersistenciaException("Ocurrio un error al querer registrar el cliente.");
        } finally {
            em.close();
        }
    }

    @Override
    public Cliente buscarPorId(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            // .find(ClaseEntidad, ValorID)
            return em.find(Cliente.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar cliente por ID: " + e.getMessage());
        } finally {
            em.close();
        }
    }

}
