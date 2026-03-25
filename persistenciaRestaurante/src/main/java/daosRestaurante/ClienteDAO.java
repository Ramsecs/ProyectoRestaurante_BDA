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
 * @author RAMSES
 */
public class ClienteDAO implements IClienteDAO{
    
    /**
     * Mediante este metodo registramos el cliente dentro de la base de datos, 
     * incluyendo los clientes frecuentes ya que siendo el cliente la clase padre 
     * es posible registrar tambien el cliente frecuente 
     * @param cliente
     * @return Cliente
     * @throws PersistenciaException 
     */
    @Override
    public Cliente registrarCliente(Cliente cliente) throws PersistenciaException{
        EntityManager mana = ConexionBD.crearConexion();
        
        try{
            
            mana.getTransaction().begin();
            mana.persist(cliente);
            mana.getTransaction().commit();
            return cliente;
            
        }catch(Exception ex){
            mana.getTransaction().rollback();
            throw new PersistenciaException("Ocurrio un error al querer registrar el cliente.");
        }finally{
            mana.close();
        }
    }
    
    /**
     * Este metodo queda pendiente de hacer para la busqueda de cliente con sus demas filtros
     * @param cliente
     * @return List 
     * @throws PersistenciaException 
     */
    @Override
    public Cliente buscarCliente(Cliente cliente) throws PersistenciaException{
        
        EntityManager mana = ConexionBD.crearConexion();
        
        String sentencia = "SELECT c FROM Cliente c WHERE c.id = :id_cliente";
        
        try{
            
            TypedQuery<Cliente> query = mana.createQuery(sentencia, Cliente.class);
            query.setParameter("id_cliente", cliente.getId());
            Cliente lista = query.getSingleResult();
            return lista;
            
        }catch(Exception ex){
            mana.getTransaction().rollback();
            throw new PersistenciaException("Ocurrio un error al querer registrar el cliente.");
        }finally{
            mana.close();
        }
        
    }
    
}
