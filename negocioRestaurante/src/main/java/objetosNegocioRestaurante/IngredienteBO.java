/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocioRestaurante;

import daosRestaurante.IngredienteDAO;
import validadores.Validaciones;
import daosRestaurante.IIngredienteDAO;
import dtosDelRestaurante.IngredienteBusquedaDTO;
import dtosDelRestaurante.IngredientesDTO;
import entidadesRestaurante.Ingrediente;
import enumEntidades.UnidadMedida;
import excepcionesRestaurante.NegocioException;
import excepcionesRestaurante.PersistenciaException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author DANIEL
 */
public class IngredienteBO implements IIngredienteBO {

    private static IngredienteBO ingredienteBO;

    private IIngredienteDAO ingredienteDAO = IngredienteDAO.getInstanceIngredientesDAO();

    private Validaciones validar = new Validaciones();

    private IngredienteBO() {

    }

    public static IngredienteBO getInstanceIngredienteBO() {
        if (ingredienteBO == null) {
            ingredienteBO = new IngredienteBO();
        }
        return ingredienteBO;
    }

    @Override
    public boolean registrarIngredientes(IngredientesDTO ingrediente) throws NegocioException {
        try {
            // 1. Validación de formato de nombre
            if (!validar.validarNombres(ingrediente.getNombre())) {
                throw new NegocioException("El nombre del ingrediente está mal escrito o es demasiado largo.");
            }

            // 2. Validación de stock (no nulo, no negativo)
            if (!validar.validarStock(ingrediente.getStock())) {
                throw new NegocioException("El Stock no puede estar vacío o ser negativo.");
            }

            // 3. Validación de duplicados (Nombre + Unidad de Medida)
            if (!validar.validarRepetido(ingrediente.getNombre(), ingrediente.getUnidad_Medida())) {
                throw new NegocioException("Ya existe un ingrediente registrado con ese nombre y esa unidad de medida.");
            }

            // 4. Mapeo de DTO a Entidad
            Ingrediente entidadIngrediente = new Ingrediente(
                    ingrediente.getNombre(),
                    ingrediente.getStock(),
                    ingrediente.getUnidad_Medida()
            );

            // 5. Persistencia
            Ingrediente resultado = ingredienteDAO.registrarIngrediente(entidadIngrediente);

            // 6. Verificación de éxito
            return resultado != null && resultado.getId() != null;

        } catch (PersistenciaException ex) {
            // Captura errores específicos de la base de datos
            throw new NegocioException("Error de persistencia: " + ex.getMessage());
        } catch (NegocioException ex) {
            // Relanza las excepciones de validación que nosotros mismos tiramos arriba
            throw ex;
        } catch (Exception ex) {
            // Captura cualquier otro error inesperado (NullPointer, etc.)
            throw new NegocioException("Error inesperado al registrar el ingrediente: " + ex.getMessage());
        }
    }

    @Override
    public List<IngredienteBusquedaDTO> buscarIngredientes(String filtro) throws NegocioException {
        try {
            // 1. Solicitar los datos al DAO (Ahora devuelve una lista de objetos Ingrediente directamente)
            List<Ingrediente> resultados = ingredienteDAO.buscarIngrediente(filtro);
            List<IngredienteBusquedaDTO> listaDTO = new ArrayList<>();

            for (Ingrediente ingrediente : resultados) {
                // 2. Crear y mapear el DTO
                IngredienteBusquedaDTO ingredienteDTO = new IngredienteBusquedaDTO();
                ingredienteDTO.setId(ingrediente.getId());
                ingredienteDTO.setNombre(ingrediente.getNombre());
                ingredienteDTO.setStock(ingrediente.getStock());
                ingredienteDTO.setUnidad_medida(ingrediente.getUnidad_medida());

                listaDTO.add(ingredienteDTO);
            }

            return listaDTO;

        } catch (PersistenciaException ex) {
            // Corregido el mensaje de "clientes" a "ingredientes"
            throw new NegocioException("Ocurrió un error en negocio al intentar devolver la lista de ingredientes: " + ex.getMessage());
        }
    }

    @Override
    public void actualizarStock(IngredienteBusquedaDTO ingredienteDTO) throws NegocioException {
        try {
            Ingrediente ingrediente = ingredienteDAO.buscarPorId(ingredienteDTO.getId());

            if (ingrediente == null) {
            throw new NegocioException("El ingrediente no existe en la base de datos.");
        }
            
            ingrediente.setStock(ingredienteDTO.getStock());
            
            ingredienteDAO.modificarIngrediente(ingrediente);
            
        } catch (PersistenciaException ex) {
            throw new NegocioException("No se pudo actualizar el ingrediente: " + ex.getMessage());
        }
    }
}
