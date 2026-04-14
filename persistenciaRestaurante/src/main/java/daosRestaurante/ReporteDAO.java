/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosRestaurante;

import conexionRestaurante.ConexionBD;
import entidadesRestaurante.Comanda;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author RAMSES
 */
public class ReporteDAO implements IReporteDAO{
    
    private static ReporteDAO reporteDAO;

    private ReporteDAO() {

    }

    /**
     * Obtener instancia de ReporteDAO.
     * 
     * @return ReporteDAO.
     */
    public static ReporteDAO getInstanceReporteDAO() {
        if (reporteDAO == null) {
            reporteDAO = new ReporteDAO();
        }

        return reporteDAO;
    }

    /**
     * Se obtiene la lista de comandas usando el periodo de fechas que el usuario otorga.
     * 
     * @param inicio
     * @param fin
     * @return Lista de comandas
     */
    @Override
    public List<Comanda> consultarComandasPorPeriodo(LocalDateTime inicio, LocalDateTime fin) {
        EntityManager em = ConexionBD.crearConexion();
        try {
            // Traemos la comanda, su mesa y su cliente (si tiene)
            String jpql = "SELECT c FROM Comanda c " +
                          "LEFT JOIN FETCH c.cliente " + 
                          "JOIN FETCH c.mesa " +
                          "WHERE c.fecha_hora_creacion BETWEEN :inicio AND :fin " +
                          "ORDER BY c.fecha_hora_creacion DESC";

            return em.createQuery(jpql, Comanda.class).setParameter("inicio", inicio).setParameter("fin", fin).getResultList();
        } finally {
            em.close();
        }
    }

    /**
     * Se obtiene la lista con los datos necesarios para la vista de reportes de clientes 
     * frecuentes, esta lista contiene diferentes datos referentes a los clientes que hay
     * registrados.
     * 
     * @return lista de datos de los clientes.
     */
    @Override
    public List<Object[]> consultarReporteClientes(String filtro_nombre, int minVisitas) {
        EntityManager em = ConexionBD.crearConexion();
        try {
            // Usamos COALESCE para que si no hay ventas, devuelva 0.0 en lugar de null
            String jpql = "SELECT c.nombre, c.apellido_paterno, COUNT(com), " +
                          "COALESCE(SUM(com.total_venta), 0.0), MAX(com.fecha_hora_creacion) " +
                          "FROM ClienteFrecuente c LEFT JOIN c.comandas com " + 
                          "WHERE (LOWER(c.nombre) LIKE LOWER(:nombre) OR LOWER(c.apellido_paterno) LIKE LOWER(:nombre)) " +
                          "GROUP BY c.id, c.nombre, c.apellido_paterno " +
                          "HAVING COUNT(com) >= :minVisitas";

            return em.createQuery(jpql).setParameter("nombre", "%" + filtro_nombre + "%")
                     .setParameter("minVisitas", (long) minVisitas)
                     .getResultList();
        } finally { 
            em.close(); 
        }
    }
    
    
    
}
