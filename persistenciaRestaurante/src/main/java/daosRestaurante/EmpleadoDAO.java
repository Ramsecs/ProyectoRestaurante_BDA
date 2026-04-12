/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosRestaurante;

import conexionRestaurante.ConexionBD;
import entidadesRestaurante.Empleado;
import excepcionesRestaurante.PersistenciaException;
import java.util.List;
import javax.persistence.EntityManager;
import seguridad.PasswordHasher;

/**
 *
 * @author RAMSES
 */
public class EmpleadoDAO implements IEmpleadoDAO{
    
    private static EmpleadoDAO empleadoDAO;

    private EmpleadoDAO() {

    }

    public static EmpleadoDAO getInstanceEmpleadoDAO() {
        if (empleadoDAO == null) {
            empleadoDAO = new EmpleadoDAO();
        }

        return empleadoDAO;
    }
    
    /**
     * Este metodo nos ayuda a registrar a los empleados en la base de datos.
     * @param empleados
     * @throws PersistenciaException 
     */
    @Override
    public void registrarMasivo(List<Empleado> empleados) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion(); // Asegúrate que este método existe
        try {
            em.getTransaction().begin();
            for (Empleado empleado : empleados) {
                // JPA se encarga de enviarlo a la tabla correcta (Administrador o Mesero)
                em.persist(empleado);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            em.getTransaction().rollback();
            throw new PersistenciaException("Error al realizar el registro masivo");
        } finally {
            em.close();
        }
    }

    /**
     * Mediante este metodo obtenemos la lista completa de todos los empleados en la base de datos
     * @return 
     */
    @Override
    public List<Empleado> obtenerTodos() {
        EntityManager em = ConexionBD.crearConexion();
        try {
            // Esta consulta trae tanto Meseros como Administradores gracias a @Inheritance
            return em.createQuery("SELECT e FROM Empleado e", Empleado.class).getResultList();
        } finally {
            em.close();
        }
    }
    
    
}
