/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package objetosNegocioRestaurante;

import dtosDelRestaurante.ComandaDTO;
import dtosDelRestaurante.ComandaProductoDTO;
import excepcionesRestaurante.NegocioException;
import java.util.List;

/**
 *
 * @author josma
 */
public interface IComandaBO {
    
    public void guardarComanda(ComandaDTO comandaDTO, List<ComandaProductoDTO> detalles) throws NegocioException;
}
