/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package adaptadores;

import dtosDelRestaurante.ClienteDTO;
import entidadesRestaurante.ClienteFrecuente;
import java.time.LocalDate;

/**
 *
 * @author RAMSES
 */
public class ClienteAdapter {
    
    /**
     * 
     * @param cliente
     * @return ClienteFrecuente
     */
    public ClienteFrecuente DTOAEntidadClienteFrecuente(ClienteDTO cliente){
        
        if (cliente == null) {
            return null;
        }
        
        ClienteFrecuente clienteEntidad = new ClienteFrecuente();
        clienteEntidad.setNombre(cliente.getNombre());
        clienteEntidad.setApellido_paterno(cliente.getApellido_paterno());
        clienteEntidad.setApellido_materno(cliente.getApellido_materno());
        clienteEntidad.setCorreo(cliente.getCorreo());
        clienteEntidad.setTelefono(cliente.getTelefono());
        clienteEntidad.setFecha_registro(LocalDate.now());
        clienteEntidad.setPuntos(0);
        
        return clienteEntidad;
        
    }
    
    
    public ClienteDTO EntidadADTOCliente(ClienteFrecuente cliente){
        
        if (cliente == null) {
            return null;
        }
        
        ClienteDTO clienteDTO = new ClienteDTO();
        clienteDTO.setNombre(cliente.getNombre());
        clienteDTO.setApellido_paterno(cliente.getApellido_paterno());
        clienteDTO.setApellido_materno(cliente.getApellido_materno());
        clienteDTO.setCorreo(cliente.getCorreo());
        clienteDTO.setTelefono(cliente.getTelefono());
        clienteDTO.setFecha_registro(cliente.getFecha_registro());
        
        return clienteDTO;
        
    }
}
