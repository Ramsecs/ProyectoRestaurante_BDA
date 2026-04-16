/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daosRestaurante;

import entidadesRestaurante.Comanda;
import entidadesRestaurante.Ingrediente;
import enumEntidades.UnidadMedida;
import excepcionesRestaurante.PersistenciaException;
import java.util.List;

/**
 *
 * @author DANIEL
 */
public interface IIngredienteDAO {
    
    public Ingrediente registrarIngrediente(Ingrediente ingrediente) throws PersistenciaException;
    public List<Ingrediente> buscarIngrediente(String filtro_busqueda) throws PersistenciaException;
    public Ingrediente modificarIngrediente(Ingrediente ingrediente) throws PersistenciaException;
    public boolean existeIngrediente(String nombre, UnidadMedida unidadMedida);
    public Ingrediente buscarPorId(Long id) throws PersistenciaException;
    public boolean procesarStockDeComanda(Comanda comanda) throws PersistenciaException;
}
