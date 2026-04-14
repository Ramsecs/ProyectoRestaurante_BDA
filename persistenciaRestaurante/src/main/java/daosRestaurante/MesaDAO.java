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
 *Esta clase dao permite hacer las consultas directas a la BD
 * @author josma
 */
public class MesaDAO implements IMesaDAO{
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
     * @param id que pertenece a mesa
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public Mesa buscarPorId(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try{
            return em.find(Mesa.class, id);
            
        }catch(Exception e){
            throw new PersistenciaException("Error al buscar la mesa" + e.getMessage());
        }finally{
            em.close();
        }
    }
    /**
     * Obtiene una lista de las mesas que tienen una comand abierta
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public List<Long> obtenerMesasOcupadas() throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try{
            //Buscamos los IDs de las mesas que tengan una comanda abierta
            String comandoJPQL = "SELECT DISTINCT c.mesa.id FROM Comanda c WHERE c.estado_comanda = :estado";
            
            TypedQuery<Long> query = em.createQuery(comandoJPQL, Long.class);
            query.setParameter("estado", EstadoComanda.ABIERTA);
            
            return query.getResultList();
        }catch(Exception e){
            System.err.println("Error al obtener las mesas ocupadas: "+ e.getMessage());
            return new ArrayList<>();//Devolvemos una lista vacía para evitar errores tronadores         
        }finally{
            em.close();
            
        }
    }
}
