/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package objetosNegocioRestaurante;

import dtosDelRestaurante.EmpleadoRegistroDTO;
import entidadesRestaurante.Empleado;
import excepcionesRestaurante.NegocioException;
import java.util.List;

/**
 *
 * @author RAMSES
 */
public interface IEmpleadoBO {
    
    public void registrarEmpleados(List<Empleado> lista) throws NegocioException;
    
    public EmpleadoRegistroDTO loginPorCodigo(String codigoIngresado) throws NegocioException;
}
