/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package daosRestaurante;

import entidadesRestaurante.Mesa;
import excepcionesRestaurante.PersistenciaException;
import java.util.List;

/**
 *
 * @author josma
 */
public interface IMesaDAO {
    public Mesa buscarPorId(Long id) throws PersistenciaException; 
    public List<Long> obtenerMesasOcupadas() throws PersistenciaException; 
    public void cancelarComandaPorMesa(Long id_mesa) throws PersistenciaException; 
    public void registrarMesasMasivas(List<Mesa> mesas) throws PersistenciaException; 
}
