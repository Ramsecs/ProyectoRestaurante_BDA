/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocioRestaurante;

import adaptadores.ClienteAdapter;
import daosRestaurante.ClienteDAO;
import daosRestaurante.IClienteDAO;
import dtosDelRestaurante.ClienteDTO;
import entidadesRestaurante.Cliente;
import entidadesRestaurante.ClienteFrecuente;
import excepcionesRestaurante.NegocioException;
import excepcionesRestaurante.PersistenciaException;
import java.time.LocalDate;
import java.util.logging.Level;
import java.util.logging.Logger;
import validadores.Validaciones;

/**
 *
 * @author RAMSES
 */
public class ClienteBO implements IClienteBO{
    
    private IClienteDAO clienteDAO = new ClienteDAO();
    private Validaciones validar = new Validaciones();
    
    @Override
    public Cliente registrarCliente(ClienteDTO cliente) throws NegocioException {
            ClienteAdapter clienteAdap = new ClienteAdapter();
        
        if (!validar.validarNombres(cliente.getNombre())) {
            throw new NegocioException("El nombre del cliente esta mal escrito.");
        }
        if (validar.validarTelefono(cliente.getTelefono())) {
            throw new NegocioException("El telefono del cliente esta mal escrito.");
        }
        if (validar.validarApellidos(cliente.getApellido_paterno())) {
            throw new NegocioException("El apellido materno del usuario esta mal escrito.");
        }
        if (validar.validarApellidos(cliente.getApellido_materno())) {
            throw new NegocioException("El apellido paterno del usuario esta mal escrito.");
        }
        if (validar.validarCorreo(cliente.getCorreo())) {
            throw new NegocioException("El correo del cliente esta mal escrito.");
        }
        
        ClienteFrecuente clienteIncribir = clienteAdap.DTOAEntidad(cliente);
        
        try {
            return clienteDAO.registrarCliente(clienteIncribir);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Ocurrio un error en negocio al intentar registrar el cliente.");
        }
        
    }
    
}
