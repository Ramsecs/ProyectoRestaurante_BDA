/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daosRestaurante;

import entidadesRestaurante.Empleado;
import excepcionesRestaurante.PersistenciaException;
import java.util.List;

/**
 *
 * @author RAMSES
 */
public interface IEmpleadoDAO {
    
    public void registrarMasivo(List<Empleado> empleados) throws PersistenciaException;
    
    public List<Empleado> obtenerTodos();
    
}
