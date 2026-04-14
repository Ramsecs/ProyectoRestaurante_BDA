/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daosRestaurante;

import entidadesRestaurante.Comanda;
import java.time.LocalDateTime;
import java.util.List;

/**
 *
 * @author RAMSES
 */
public interface IReporteDAO {
    
    public List<Comanda> consultarComandasPorPeriodo(LocalDateTime inicio, LocalDateTime fin);
    
    public List<Object[]> consultarReporteClientes(String filtro_nombre, int minVisitas);
    
}
