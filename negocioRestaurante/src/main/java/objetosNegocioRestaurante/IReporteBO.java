/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package objetosNegocioRestaurante;

import dtosDelRestaurante.ReporteClienteDTO;
import dtosDelRestaurante.ReporteComandaDTO;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author RAMSES
 */
public interface IReporteBO {
    
    public List<ReporteComandaDTO> generarReporteComandas(LocalDateTime inicio, LocalDateTime fin);
    
    public List<ReporteClienteDTO> obtenerReporteClientes(String filtroNombre, int minVisitas);
    
}
