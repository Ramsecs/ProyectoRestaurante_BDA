/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daosRestaurante;

import entidadesRestaurante.Cliente;
import excepcionesRestaurante.PersistenciaException;
import java.util.List;

/**
 *
 * @author RAMSES
 */
public interface IClienteDAO {
    
    public Cliente registrarCliente(Cliente cliente) throws PersistenciaException;
    
    public Cliente buscarCliente(Cliente cliente) throws PersistenciaException;
    
    
}
