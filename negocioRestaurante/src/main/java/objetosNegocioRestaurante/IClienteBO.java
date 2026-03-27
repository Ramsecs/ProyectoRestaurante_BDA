/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package objetosNegocioRestaurante;

import dtosDelRestaurante.ClienteBusquedaDTO;
import dtosDelRestaurante.ClienteDTO;
import excepcionesRestaurante.NegocioException;
import java.util.List;

/**
 *
 * @author RAMSES
 */
public interface IClienteBO {
    
    public boolean registrarCliente(ClienteDTO cliente) throws NegocioException;
    public List<ClienteBusquedaDTO> buscarClientes(String filtro) throws NegocioException;
    public void actualizarDatosCliente(ClienteBusquedaDTO clienteDTO) throws NegocioException;
}
