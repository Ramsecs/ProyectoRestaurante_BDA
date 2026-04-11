/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocioRestaurante;

import adaptadores.ClienteAdapter;
import daosRestaurante.ClienteDAO;
import daosRestaurante.IClienteDAO;
import dtosDelRestaurante.ClienteBusquedaDTO;
import dtosDelRestaurante.ClienteDTO;
import entidadesRestaurante.Cliente;
import entidadesRestaurante.ClienteFrecuente;
import excepcionesRestaurante.NegocioException;
import excepcionesRestaurante.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import validadores.Validaciones;

/**
 * 
 * @author RAMSES
 */
public class ClienteBO implements IClienteBO {

    /**
     * Instancia unica de la clase para el patron Singleton.
     */
    private static ClienteBO clienteBO;

    /**
     * Referencia al DAO de clientes para operaciones de persistencia.
     */
    private IClienteDAO clienteDAO = ClienteDAO.getInstanceClienteDAO();

    /**
     * Utilidad de validaciones para verificar el formato de los datos de entrada.
     */
    private Validaciones validar = new Validaciones();

    /**
     * Constructor privado para evitar la instanciacion externa y mantener el patron Singleton.
     */
    private ClienteBO() {
    }

    /**
     * Obtiene la instancia unica de ClienteBO. Si no existe, la crea.
     * @return La instancia unica de ClienteBO.
     */
    public static ClienteBO getInstanceClienteBO() {
        if (clienteBO == null) {
            clienteBO = new ClienteBO();
        }
        return clienteBO;
    }

    /**
     * Registra un nuevo cliente en el sistema después de validar sus datos personales.
     * Se encarga de convertir el DTO recibido en una entidad de persistencia.
     * @param cliente Objeto DTO que contiene la informacion del cliente a registrar.
     * @return true si el registro fue exitoso, false en caso contrario.
     * @throws NegocioException Si los datos del cliente no pasan las validaciones de formato
     * o si ocurre un error en la capa de persistencia.
     */
    @Override
    public boolean registrarCliente(ClienteDTO cliente) throws NegocioException {
        ClienteAdapter clienteAdapter = new ClienteAdapter();

        if (!validar.validarNombres(cliente.getNombre())) {
            throw new NegocioException("El nombre del cliente esta mal escrito.");
        }
        if (!validar.validarApellidos(cliente.getApellido_paterno())) {
            throw new NegocioException("El apellido paterno del usuario esta mal escrito.");
        }
        if (!validar.validarApellidos(cliente.getApellido_materno())) {
            throw new NegocioException("El apellido materno del usuario esta mal escrito.");
        }
        if (cliente.getCorreo() != null) {
            if (!validar.validarCorreo(cliente.getCorreo())) {
                throw new NegocioException("El correo del cliente esta mal escrito.");
            }
        }

        if (!validar.validarTelefono(cliente.getTelefono())) {
            throw new NegocioException("El telefono del cliente esta mal escrito.");
        }

        ClienteFrecuente clienteIncribir = clienteAdapter.DTOAEntidadClienteFrecuente(cliente);

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

    /**
     * Busca clientes basados en un filtro de texto y calcula estadísticas adicionales 
     * como total de visitas, suma de ventas y puntos acumulados.
     * @param filtro Cadena de texto para filtrar la busqueda (nombre, telefono, etc.).
     * @return ClienteBusquedaDTO.
     * @throws NegocioException Si ocurre un error al consultar los datos en la base de datos.
     */
    @Override
    public List<ClienteBusquedaDTO> buscarClientes(String filtro) throws NegocioException {

        try {
            //1. Solicitar los datos al DAO
            List<Object[]> resultados = clienteDAO.buscarCliente(filtro);
            List<ClienteBusquedaDTO> listaDTO = new ArrayList<>();

            for (Object[] fila : resultados) {
                Cliente cliente = (Cliente) fila[0];
                Long total_visitas = (Long) fila[1];
                Double suma_ventas = (Double) fila[2];

                //2. crear el dto 
                ClienteBusquedaDTO clienteDTO = new ClienteBusquedaDTO();
                clienteDTO.setId(cliente.getId());
                clienteDTO.setNombre(cliente.getNombre());
                clienteDTO.setApellido_paterno(cliente.getApellido_paterno());
                clienteDTO.setApellido_materno(cliente.getApellido_materno());
                clienteDTO.setCorreo(cliente.getCorreo());
                clienteDTO.setTelefono(cliente.getTelefono());

                //4. Manejo de calculos
                //Visitas que viene como Long
                clienteDTO.setVisitas(total_visitas != null ? total_visitas : 0L);
                //Total acumulado 
                suma_ventas = (suma_ventas != null) ? suma_ventas : 0.0;
                clienteDTO.setTotal_acumulado(suma_ventas);
                //Puntos ( 1 punto por cada total/20)
                clienteDTO.setPuntos((int) (suma_ventas / 20));

                listaDTO.add(clienteDTO);
            }
            return listaDTO;
        } catch (PersistenciaException ex) {
            throw new NegocioException("Ocurrio un error en negocio al intentar devolver la lista de clientes" + ex.getMessage());
        }
    }

    /**
     * Actualiza la información personal de un cliente existente en la base de datos.
     * @param clienteDTO Objeto que contiene los datos actualizados del cliente y su ID identificador.
     * @throws NegocioException Si el cliente no existe o si ocurre un error durante la actualización.
     */
    @Override
    public void actualizarDatosCliente(ClienteBusquedaDTO clienteDTO) throws NegocioException {
        try {
            Cliente cliente = clienteDAO.buscarPorId(clienteDTO.getId());

            cliente.setNombre(clienteDTO.getNombre());
            cliente.setApellido_paterno(clienteDTO.getApellido_paterno());
            cliente.setApellido_materno(clienteDTO.getApellido_materno());
            cliente.setCorreo(clienteDTO.getCorreo());
            cliente.setTelefono(clienteDTO.getTelefono());

            clienteDAO.modificarCliente(cliente);

        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo actualizar el cliente: " + ex.getMessage());
        }
    }
}