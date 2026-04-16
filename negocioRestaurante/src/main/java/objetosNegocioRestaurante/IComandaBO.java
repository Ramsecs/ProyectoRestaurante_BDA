/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package objetosNegocioRestaurante;

import dtosDelRestaurante.ComandaDTO;
import dtosDelRestaurante.ComandaListaDTO;
import dtosDelRestaurante.ComandaProductoDTO;
import dtosDelRestaurante.DetalleComandaDTO;
import entidadesEnumeradorDTO.EstadoComandaDTO;
import excepcionesRestaurante.NegocioException;
import java.util.List;

/**
 *
 * @author josma
 */
public interface IComandaBO {
    
    public void guardarComanda(ComandaDTO comandaDTO, List<ComandaProductoDTO> detalles) throws NegocioException;
    public List<ComandaListaDTO> mostrarComandasAbiertas() throws NegocioException;
    public void actualizarEstadoComanda(Long id, EstadoComandaDTO estado) throws NegocioException;
    public List<ComandaListaDTO> filtrarComandasAbiertas(String filtro) throws NegocioException;
    public void guardarCambiosComanda(Long idComanda, List<DetalleComandaDTO> modificarDTO, List<DetalleComandaDTO> eliminarDTO, List<DetalleComandaDTO> nuevosDTO) throws NegocioException;
    public List<DetalleComandaDTO> consultarDetallesPorComanda(Long id_comanda) throws NegocioException; 
}
