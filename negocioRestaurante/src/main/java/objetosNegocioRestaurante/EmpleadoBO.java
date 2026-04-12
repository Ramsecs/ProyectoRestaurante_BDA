/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocioRestaurante;

import daosRestaurante.EmpleadoDAO;
import daosRestaurante.IEmpleadoDAO;
import daosRestaurante.IIngredienteDAO;
import daosRestaurante.IngredienteDAO;
import dtosDelRestaurante.EmpleadoRegistroDTO;
import entidadesRestaurante.Empleado;
import entidadesRestaurante.Mesero;
import excepcionesRestaurante.NegocioException;
import excepcionesRestaurante.PersistenciaException;
import java.util.List;
import seguridad.PasswordHasher;
import validadores.Validaciones;

/**
 *
 * @author RAMSES
 */
public class EmpleadoBO implements IEmpleadoBO{
    
    private static EmpleadoBO empleadoBO;

    private IEmpleadoDAO empleadoDAO = EmpleadoDAO.getInstanceEmpleadoDAO();

    private Validaciones validar = new Validaciones();

    private EmpleadoBO() {

    }

    public static EmpleadoBO getInstanceEmpleadoBO() {
        if (empleadoBO == null) {
            empleadoBO = new EmpleadoBO();
        }
        return empleadoBO;
    }
    
    /**
     * Mediante este metodo obtenemos la lista de empleados para poder encriptar sus cotraseñas.
     * @param lista
     * @throws NegocioException 
     */
    @Override
    public void registrarEmpleados(List<Empleado> lista) throws NegocioException {
        if (lista == null || lista.isEmpty()){
            throw new NegocioException("La lista está vacía.");
        }
        
        for (Empleado e : lista) {
            // El código ingresado se guarda como hash en el campo 'contrasena'
            String hash = PasswordHasher.hash(e.getContrasena());
            e.setContrasena(hash);
        }
        try{
            empleadoDAO.registrarMasivo(lista);
        }catch(PersistenciaException ex){
            throw new NegocioException("Hubo un error al registrar el empleado.");
        }
        
    }

    /**
     * Mediante este metodo iniciamos sesion validando la contraseña que obtenemos por el usuario.
     * @param codigo_ingresado
     * @return
     * @throws NegocioException 
     */
    @Override
    public EmpleadoRegistroDTO loginPorCodigo(String codigo_ingresado) throws NegocioException {
        List<Empleado> empleados = empleadoDAO.obtenerTodos();

        for (Empleado e : empleados) {
            // Verificamos el código (que es la contraseña encriptada)
            if (PasswordHasher.verificar(codigo_ingresado, e.getContrasena())) {
                
                // Determinamos el rol por la instancia de la clase
                String rol = "DESCONOCIDO";
                
                if (e instanceof Mesero){
                    rol = "ADMINISTRADOR";
                }else{
                    rol = "MESERO";
                }

                // Retornamos el DTO solicitado
                return new EmpleadoRegistroDTO(
                    e.getId(),
                    e.getNombres(),
                    e.getApellido_paterno(),
                    e.getApellido_materno(),
                    rol
                );
            }
        }
        throw new NegocioException("Código de acceso no válido.");
    }
    
    
    
}
