/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package objetosNegocioRestaurante;

import dtosDelRestaurante.ClienteDTO;
import entidadesRestaurante.Cliente;
import excepcionesRestaurante.NegocioException;
import java.time.LocalDate;

/**
 *
 * @author RAMSES
 */
public interface IClienteBO {
    
    public Cliente registrarCliente(ClienteDTO cliente) throws NegocioException;
    
}
