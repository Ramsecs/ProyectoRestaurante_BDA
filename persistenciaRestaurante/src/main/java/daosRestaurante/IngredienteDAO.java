/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosRestaurante;

import conexionRestaurante.ConexionBD;
import entidadesRestaurante.Ingrediente;
import enumEntidades.UnidadMedida;
import excepcionesRestaurante.PersistenciaException;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

/**
 *
 * @author DANIEL
 */
public class IngredienteDAO implements IIngredienteDAO {

    private static IngredienteDAO ingredientesDAO;

    private IngredienteDAO() {

    }

    public static IngredienteDAO getInstanceIngredientesDAO() {
        if (ingredientesDAO == null) {
            ingredientesDAO = new IngredienteDAO();
        }

        return ingredientesDAO;
    }

    @Override
    public Ingrediente registrarIngrediente(Ingrediente ingrediente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {

            em.getTransaction().begin();
            em.persist(ingrediente);
            em.getTransaction().commit();
            return ingrediente;

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Ocurrio un error al querer registrar el ingrediente.");
        } finally {
            em.close();
        }
    }

    @Override
    public List<Ingrediente> buscarIngrediente(String filtro_busqueda) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        // Eliminamos el GROUP BY y corregimos los espacios
        String sentenciaJPQL = "SELECT i FROM Ingrediente i WHERE "
                + "LOWER(i.nombre) LIKE :filtro OR "
                + "LOWER(i.unidad_medida) LIKE :filtro";

        try {
            // Cambiamos Object[].class por Ingrediente.class
            TypedQuery<Ingrediente> query = em.createQuery(sentenciaJPQL, Ingrediente.class);

            query.setParameter("filtro", "%" + filtro_busqueda.toLowerCase() + "%");

            return query.getResultList();

        } catch (Exception ex) {
            throw new PersistenciaException("Ocurrio un error al querer aplicar los filtros de busqueda: " + ex.getMessage());
        } finally {
            em.close();
        }
    }

    @Override
    public Ingrediente modificarIngrediente(Ingrediente ingrediente) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();

        try {

            em.getTransaction().begin();
            em.merge(ingrediente);
            em.getTransaction().commit();
            return ingrediente;

        } catch (Exception ex) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            throw new PersistenciaException("Ocurrio un error al querer modificar el ingrediente.");
        } finally {
            em.close();
        }
    }

    @Override
    public boolean existeIngrediente(String nombre, UnidadMedida unidadMedida) {
        EntityManager em = ConexionBD.crearConexion();
        try {
            String jpql = "SELECT COUNT(i) FROM Ingrediente i WHERE i.nombre = :nombre AND i.unidad_medida = :unidad";
            Long conteo = em.createQuery(jpql, Long.class)
                    .setParameter("nombre", nombre)
                    .setParameter("unidad", unidadMedida)
                    .getSingleResult();
            return conteo > 0;
        } finally {
            em.close();
        }
    }
    
    @Override
    public Ingrediente buscarPorId(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            // .find(ClaseEntidad, ValorID)
            return em.find(Ingrediente.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar ingrediente por ID: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}