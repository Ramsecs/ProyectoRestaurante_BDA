/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daosRestaurante;

import dtosDelRestaurante.ComandaDTO;
import dtosDelRestaurante.DetalleComandaDTO;
import entidadesRestaurante.Comanda;
import entidadesRestaurante.ComandaProducto;
import enumEntidades.EstadoComanda;
import excepcionesRestaurante.PersistenciaException;
import java.util.List;

/**
 *
 * @author josma
 */
public interface IComandaDAO {
    public void registrarComanda(Comanda comanda) throws PersistenciaException; 
    public  List<Comanda> obtenerComandasAbiertas(EstadoComanda Estado) throws PersistenciaException; 
    public void actualizarEstadoComanda(Long id, EstadoComanda estado) throws PersistenciaException;
    public List<Comanda> buscarComandasAbiertasPorCliente(String filtro, EstadoComanda estado) throws PersistenciaException;
    public void actualizarDetallesComanda(Long id_comanda, List<ComandaProducto> modificar,List<ComandaProducto> eliminar, List<ComandaProducto> nuevos) throws PersistenciaException;
    public List<ComandaProducto> consultarPorComanda(Long id) throws PersistenciaException;
    public ComandaProducto buscarComandaProductoPorId(Long id) throws PersistenciaException;
    public void descontarStockPorProducto(Long idProducto, int cantidadPedida) throws PersistenciaException;
    
}
