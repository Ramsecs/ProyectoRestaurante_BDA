/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosRestaurante;

import conexionRestaurante.ConexionBD;
import entidadesRestaurante.Comanda;
import entidadesRestaurante.ComandaProducto;
import excepcionesRestaurante.PersistenciaException;
import java.util.List;
import javax.persistence.EntityManager;

/**
 *
 * @author josma
 */
public class ComandaDAO implements IComandaDAO {

    private static ComandaDAO comandaDAO;

    private ComandaDAO() {

    }

    public static ComandaDAO getInstanceComandaDAO() {
        if (comandaDAO == null) {
            comandaDAO = new ComandaDAO();
        }

        return comandaDAO;
    }

    @Override
    public void registrarComanda(Comanda comanda) throws PersistenciaException {
        EntityManager em = ConexionBD.crearConexion();
        try {
            em.getTransaction().begin();
          
            em.persist(comanda); // Guardamos la comanda sol
            em.getTransaction().commit();
        } catch (Exception e) {
            throw new PersistenciaException("Error al registrar la comanda " + e.getMessage());
        } finally {
            em.close();
        }
    }
}
