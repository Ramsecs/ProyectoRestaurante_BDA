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
import validadores.Validaciones;

/**
 *
 * @author RAMSES
 */
public class ClienteBO implements IClienteBO{
    
    //Singleton
    private static ClienteBO clienteBO;
    
    private IClienteDAO clienteDAO = ClienteDAO.getInstanceClienteDAO();
    private Validaciones validar = new Validaciones();
    
    //Constructor privado
    private ClienteBO(){
        
    }
    //Metodo para el singleton
    public static ClienteBO getInstanceClienteBO(){
        if (clienteBO == null) {
            clienteBO = new ClienteBO();
        }
        
        return clienteBO;
    }
    
    @Override
    public boolean registrarCliente(ClienteDTO cliente) throws NegocioException {
            ClienteAdapter clienteAdap = new ClienteAdapter();
        
        if (!validar.validarNombres(cliente.getNombre())) {
            throw new NegocioException("El nombre del cliente esta mal escrito.");
        }
        if (!validar.validarApellidos(cliente.getApellido_paterno())) {
            throw new NegocioException("El apellido materno del usuario esta mal escrito.");
        }
        if (!validar.validarApellidos(cliente.getApellido_materno())) {
            throw new NegocioException("El apellido paterno del usuario esta mal escrito.");
        }
        if (!validar.validarCorreo(cliente.getCorreo())) {
            throw new NegocioException("El correo del cliente esta mal escrito.");
        }
        
        ClienteFrecuente clienteIncribir = clienteAdap.DTOAEntidadClienteFrecuente(cliente);
        
        try {
            Cliente cliente_recibido = clienteDAO.registrarCliente(clienteIncribir);
            
            if (cliente_recibido == null) {
                return false;
            }
            return true;
            
        } catch (PersistenciaException ex) {
            throw new NegocioException("Ocurrio un error en negocio al intentar registrar el cliente.");
        }
        
    }
    
}
