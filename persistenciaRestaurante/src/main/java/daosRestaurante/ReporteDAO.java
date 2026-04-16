/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosRestaurante;

import conexionRestaurante.ConexionBD;
import entidadesRestaurante.ClienteFrecuente;
import entidadesRestaurante.Comanda;
import java.time.LocalDateTime;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Join;
import javax.persistence.criteria.JoinType;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

/**
 *
 * @author RAMSES
 */
public class ReporteDAO implements IReporteDAO {

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
     * Se obtiene la lista de comandas usando el periodo de fechas que el
     * usuario otorga.
     *
     * @param inicio
     * @param fin
     * @return Lista de comandas
     */
    @Override
    public List<Comanda> consultarComandasPorPeriodo(LocalDateTime inicio, LocalDateTime fin) {
        EntityManager em = ConexionBD.crearConexion();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Comanda> cq = cb.createQuery(Comanda.class);
            Root<Comanda> c = cq.from(Comanda.class);

            c.fetch("cliente", JoinType.LEFT);

            c.fetch("mesa", JoinType.INNER);

            cq.where(cb.between(c.get("fecha_hora_creacion"), inicio, fin));

            cq.orderBy(cb.desc(c.get("fecha_hora_creacion")));

            return em.createQuery(cq).getResultList();

        } finally {
            em.close();
        }
    }

    /**
     * Se obtiene la lista con los datos necesarios para la vista de reportes de
     * clientes frecuentes, esta lista contiene diferentes datos referentes a
     * los clientes que hay registrados.
     *
     * @param filtro_nombre
     * @param minVisitas
     * @return lista de datos de los clientes.
     */
    @Override
    public List<Object[]> consultarReporteClientes(String filtro_nombre, int minVisitas) {
        EntityManager em = ConexionBD.crearConexion();
        try {
            CriteriaBuilder cb = em.getCriteriaBuilder();
            CriteriaQuery<Object[]> cq = cb.createQuery(Object[].class);

            Root<ClienteFrecuente> c = cq.from(ClienteFrecuente.class);

            Join<ClienteFrecuente, Comanda> com = c.join("comandas", JoinType.LEFT);

            cq.multiselect(
                    c.get("nombre"),
                    c.get("apellido_paterno"),
                    cb.count(com),
                    cb.coalesce(cb.sum(com.get("total_venta")), 0.0),
                    cb.max(com.get("fecha_hora_creacion"))
            );

            String pattern = "%" + filtro_nombre.toLowerCase() + "%";
            Predicate nombrePredicate = cb.like(cb.lower(c.get("nombre")), pattern);
            Predicate apellidoPredicate = cb.like(cb.lower(c.get("apellido_paterno")), pattern);
            cq.where(cb.or(nombrePredicate, apellidoPredicate));

            cq.groupBy(c.get("id"), c.get("nombre"), c.get("apellido_paterno"));

            cq.having(cb.greaterThanOrEqualTo(cb.count(com), (long) minVisitas));

            return em.createQuery(cq).getResultList();

        } finally {
            em.close();
        }
    }

}
