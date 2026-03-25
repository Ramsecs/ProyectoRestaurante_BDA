/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package objetosNegocioRestaurante;

import dtosDelRestaurante.ClienteDTO;
import excepcionesRestaurante.NegocioException;

/**
 *
 * @author RAMSES
 */
public interface IClienteBO {
    
    public boolean registrarCliente(ClienteDTO cliente) throws NegocioException;
    
}
