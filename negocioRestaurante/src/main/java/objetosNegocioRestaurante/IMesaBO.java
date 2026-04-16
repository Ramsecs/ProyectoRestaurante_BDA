/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package objetosNegocioRestaurante;

import excepcionesRestaurante.NegocioException;
import java.util.List;

/**
 *
 * @author josma
 */
public interface IMesaBO {
    public List<Long> obtenerMesasOcupadas() throws NegocioException;
    public void cancelarComandaMesa(Long id_mesa) throws NegocioException;
}
