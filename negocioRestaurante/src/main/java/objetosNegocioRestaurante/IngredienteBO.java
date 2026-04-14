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

    /** Instancia única de la clase (Patrón Singleton) */
    private static IngredienteBO ingredienteBO;

    /** Acceso a la capa de persistencia mediante interfaz para desacoplamiento */
    private IIngredienteDAO ingredienteDAO = IngredienteDAO.getInstanceIngredientesDAO();

    /** Utilidad para validaciones de formato y reglas de negocio */
    private Validaciones validar = new Validaciones();

    /** Constructor privado para evitar instanciación externa */
    private IngredienteBO() {
    }

    /**
     * Obtiene la instancia única de IngredienteBO.
     * @return Instancia de la lógica de negocio de ingredientes.
     */
    public static IngredienteBO getInstanceIngredienteBO() {
        if (ingredienteBO == null) {
            ingredienteBO = new IngredienteBO();
        }
        return ingredienteBO;
    }

    /**
     * Registra un nuevo ingrediente realizando validaciones previas de integridad y negocio.
     * 
     * @param ingrediente DTO con los datos del ingrediente a registrar.
     * @return true si el registro fue exitoso, false en caso contrario.
     * @throws NegocioException Si el nombre es inválido, el stock es incorrecto, 
     * el ingrediente ya existe o hay fallos en persistencia.
     */
    @Override
    public boolean registrarIngredientes(IngredientesDTO ingrediente) throws NegocioException {
        try {
            if (!validar.validarNombres(ingrediente.getNombre())) {
                throw new NegocioException("El nombre del ingrediente está mal escrito o es demasiado largo.");
            }

            if (!validar.validarStock(ingrediente.getStock())) {
                throw new NegocioException("El Stock no puede estar vacío o ser negativo.");
            }

            if (!validar.validarRepetido(ingrediente.getNombre(), ingrediente.getUnidad_Medida())) {
                throw new NegocioException("Ya existe un ingrediente registrado con ese nombre y esa unidad de medida.");
            }

            Ingrediente entidadIngrediente = new Ingrediente(
                    ingrediente.getNombre(),
                    ingrediente.getStock(),
                    ingrediente.getUnidad_Medida()
            );

            Ingrediente resultado = ingredienteDAO.registrarIngrediente(entidadIngrediente);

            return resultado != null && resultado.getId() != null;

        } catch (PersistenciaException ex) {
            throw new NegocioException("Error de persistencia: " + ex.getMessage());
        } catch (NegocioException ex) {
            throw ex; 
        } catch (Exception ex) {
            throw new NegocioException("Error inesperado al registrar el ingrediente: " + ex.getMessage());
        }
    }

    /**
     * Recupera una lista de ingredientes filtrada por nombre y la convierte a DTOs.
     * 
     * @param filtro Cadena de texto para filtrar la búsqueda.
     * @return Lista de DTOs con la información de los ingredientes encontrados.
     * @throws NegocioException Si ocurre un error en la comunicación con la base de datos.
     */
    @Override
    public List<IngredienteBusquedaDTO> buscarIngredientes(String filtro) throws NegocioException {
        try {

            List<Ingrediente> resultados = ingredienteDAO.buscarIngrediente(filtro);
            List<IngredienteBusquedaDTO> lista_DTO = new ArrayList<>();

            for (Ingrediente ingrediente : resultados) {

                IngredienteBusquedaDTO ingrediente_DTO = new IngredienteBusquedaDTO();
                ingrediente_DTO.setId(ingrediente.getId());
                ingrediente_DTO.setNombre(ingrediente.getNombre());
                ingrediente_DTO.setStock(ingrediente.getStock());
                ingrediente_DTO.setUnidad_medida(ingrediente.getUnidad_medida());

                lista_DTO.add(ingrediente_DTO);
            }
            return lista_DTO;

        } catch (PersistenciaException ex) {
            throw new NegocioException("Ocurrio un error en negocio al intentar devolver la lista de ingredientes: " + ex.getMessage());
        }
    }

    /**
     * Actualiza el stock de un ingrediente existente.
     * 
     * @param ingredienteDTO DTO que contiene el ID del ingrediente y el nuevo stock.
     * @throws NegocioException Si el ingrediente no existe o falla la persistencia.
     */
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