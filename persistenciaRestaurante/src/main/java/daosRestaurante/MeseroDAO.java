/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosRestaurante;

import conexionRestaurante.ConexionBD;
import entidadesRestaurante.Mesero;
import excepcionesRestaurante.PersistenciaException;
import javax.persistence.EntityManager;

/**
 *DAO de mesero donde podremos hacer consultas a la bd
 * @author josma
 */
public class MeseroDAO implements IMeseroDAO {
    /**
     * Variable privada para el singleton.
     */
    private static MeseroDAO meseroDAO;
    /**
     * Constructor vacio.
     */
    private MeseroDAO() {

    }
    /**
     * Metodo singleton
     * @return 
     */
    public static MeseroDAO getInstanceMeseroDAO() {
        if (meseroDAO == null) {
            meseroDAO = new MeseroDAO();
        }

        return meseroDAO;
    }
    /**
     * Busca el mesero por id, esto para poder asignarlo a una comanda.
     * @param id del mesero
     * @return
     * @throws PersistenciaException 
     */
    @Override
    public Mesero buscarPorId(Long id) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        
        try{
            return em.find(Mesero.class, id);
        }catch(Exception e){
            throw new PersistenciaException("Error al buscar el mesero: " + e.getMessage());
        }finally{
            em.close();
        }
    }
    
}
