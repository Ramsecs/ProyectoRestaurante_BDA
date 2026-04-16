/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosRestaurante;

import conexionRestaurante.ConexionBD;
import entidadesRestaurante.Comanda;
import entidadesRestaurante.ComandaProducto;
import entidadesRestaurante.Ingrediente;
import entidadesRestaurante.Producto;
import entidadesRestaurante.ProductoIngrediente;
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

    /**
     * Instancia única de la clase para el patrón Singleton.
     */
    private static IngredienteDAO ingredientesDAO;

    /**
     * Constructor privado para evitar la creación de múltiples instancias.
     */
    private IngredienteDAO() {
    }

    /**
     * Obtiene la instancia única de IngredienteDAO. Si no existe, la crea; de
     * lo contrario, devuelve la existente.
     *
     * * @return Instancia única de IngredienteDAO.
     * @return
     */
    public static IngredienteDAO getInstanceIngredientesDAO() {
        if (ingredientesDAO == null) {
            ingredientesDAO = new IngredienteDAO();
        }
        return ingredientesDAO;
    }

    /**
     * Registra un nuevo ingrediente en la base de datos.
     *
     * @param ingrediente El objeto Ingrediente a persistir.
     * @return El ingrediente registrado con su ID generado.
     * @throws PersistenciaException Si ocurre un error durante la transacción.
     */
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
            throw new PersistenciaException("Ocurrió un error al querer registrar el ingrediente.");
        } finally {
            em.close();
        }
    }

    /**
     * Busca ingredientes cuyo nombre o unidad de medida coincidan con el filtro
     * proporcionado. Utiliza lenguaje JPQL para realizar una busqueda
     * insensible a mayusculas/minusculas.
     *
     * @param filtro_busqueda Texto a buscar entre los registros de
     * ingredientes.
     * @return Una lista de objetos Ingrediente que coinciden con el criterio.
     * @throws PersistenciaException Si ocurre un error al ejecutar la consulta.
     */
    @Override
    public List<Ingrediente> buscarIngrediente(String filtro_busqueda) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        String sentenciaJPQL = "SELECT i FROM Ingrediente i WHERE "
                + "LOWER(i.nombre) LIKE :filtro OR "
                + "LOWER(i.unidad_medida) LIKE :filtro";
        try {
            TypedQuery<Ingrediente> query = em.createQuery(sentenciaJPQL, Ingrediente.class);
            query.setParameter("filtro", "%" + filtro_busqueda.toLowerCase() + "%");
            return query.getResultList();
        } catch (Exception ex) {
            throw new PersistenciaException("Ocurrió un error al querer aplicar los filtros de búsqueda: " + ex.getMessage());
        } finally {
            em.close();
        }
    }

    /**
     * Modifica un ingrediente existente en la base de datos. Utiliza el metodo
     * merge para actualizar el estado del objeto en el contexto de
     * persistencia.
     *
     * @param ingrediente Objeto ingrediente con los datos actualizados.
     * @return El objeto ingrediente actualizado.
     * @throws PersistenciaException Si ocurre un error al intentar modificar el
     * registro.
     */
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
            throw new PersistenciaException("Ocurrió un error al querer modificar el ingrediente.");
        } finally {
            em.close();
        }
    }

    /**
     * Verifica si ya existe un ingrediente con el mismo nombre y unidad de
     * medida.
     *
     * @param nombre Nombre del ingrediente a verificar.
     * @param unidadMedida Enum de la unidad de medida a verificar.
     * @return true si existe al menos una coincidencia, false en caso
     * contrario.
     */
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

    /**
     * Descuenta del inventario los ingredientes necesarios para cumplir con una
     * comanda.
     *
     * 
     * @param comanda La comanda que contiene la lista de productos y sus
     * cantidades.
     * @return true si el descuento se realizo con exito; false si hubo stock
     * insuficiente o error en la persistencia.
     */
    @Override
    public boolean procesarStockDeComanda(Comanda comanda) {
        EntityManager em = ConexionBD.crearConexion();

        try {
            em.getTransaction().begin();

            for (ComandaProducto detalle : comanda.getLista_productos()) {

                int cantidad_Platos_Pedida = detalle.getCant_cada_producto();
                Producto producto = detalle.getProductos_comprados();

                for (ProductoIngrediente receta : producto.getLista_ingredientes()) {

                    Ingrediente ingrediente = receta.getIngredientes();

                    int cantidad_A_Restar = receta.getCantidad_ingrediente() * cantidad_Platos_Pedida;
                    int stock_Disponible = ingrediente.getStock();

                    if (stock_Disponible >= cantidad_A_Restar) {
                        ingrediente.setStock(stock_Disponible - cantidad_A_Restar);

                        em.merge(ingrediente);
                    } else {
                        System.err.println("STOCK INSUFICIENTE: " + ingrediente.getNombre());
                        em.getTransaction().rollback();
                        return false;
                    }
                }
            } 
            em.getTransaction().commit();
            return true;

        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }

    /**
     * Busca un ingrediente específico utilizando su identificador único (ID).
     *
     * @param id Identificador del ingrediente.
     * @return El objeto Ingrediente encontrado o null si no existe.
     * @throws PersistenciaException Si ocurre un error en la busqueda.
     */
    @Override
    public Ingrediente buscarPorId(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            return em.find(Ingrediente.class, id);
        } catch (Exception e) {
            throw new PersistenciaException("Error al buscar ingrediente por ID: " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
