/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosRestaurante;

import conexionRestaurante.ConexionBD;
import entidadesRestaurante.Mesa;
import excepcionesRestaurante.PersistenciaException;
import javax.persistence.EntityManager;

/**
 *
 * @author josma
 */
public class MesaDAO implements IMesaDAO{
   private static MesaDAO mesaDAO;

    private MesaDAO() {

    }

    public static MesaDAO getInstanceMesaDAO() {
        if (mesaDAO == null) {
            mesaDAO = new MesaDAO();
        }

        return mesaDAO;
    } 

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
}
