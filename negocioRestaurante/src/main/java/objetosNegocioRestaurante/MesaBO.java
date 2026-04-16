/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocioRestaurante;

import daosRestaurante.IMesaDAO;
import daosRestaurante.MesaDAO;
import entidadesRestaurante.Mesa;
import excepcionesRestaurante.NegocioException;
import excepcionesRestaurante.PersistenciaException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import validadores.Validaciones;

/**
 * Esta BO es para mesa, donde podemos establecer la conexión entre capas
 *
 * @author josma
 */
public class MesaBO implements IMesaBO {

    /**
     * Vriable privada de instancia mesaBO.
     */
    private static MesaBO mesaBO;
    /**
     * Instancia de la DAO de mesa.
     */
    private IMesaDAO mesaDAO = MesaDAO.getInstanceMesaDAO();
    /**
     * Instancia de Validaciones.
     */
    private Validaciones validar = new Validaciones();

    /**
     * Constructor privado y vacio.
     */
    private MesaBO() {

    }

    /**
     * Método Singleton
     *
     * @return
     */
    public static MesaBO getInstanceComandaBO() {
        if (mesaBO == null) {
            mesaBO = new MesaBO();
        }

        return mesaBO;
    }

    /**
     * Metodo BO para obtener las mesas ocupadas para que el coordinador decida
     * hacer algo con ellas
     *
     * @return
     * @throws NegocioException
     */
    @Override
    public List<Long> obtenerMesasOcupadas() throws NegocioException {
        try {
            List<Long> mesas_ocupadas = mesaDAO.obtenerMesasOcupadas();

            if (mesas_ocupadas == null) {
                return new ArrayList<>();
            }
            return mesas_ocupadas;
        } catch (Exception e) {
            throw new NegocioException("Error al consultar el estado de las mesas " + e.getMessage());
        }
    }

    @Override
    public void cancelarComandaMesa(Long id_mesa) throws NegocioException {
        //Validación por si a caso
        try {
            if (id_mesa == null || id_mesa < 0) {
                throw new NegocioException("ID de la mesa no válido");
            }

            mesaDAO.cancelarComandaPorMesa(id_mesa);
        } catch (Exception e) {
            throw new NegocioException("Error al cancelar la comanda" + e.getMessage());
        }
    }

    @Override
    public String registrarLoteMesas(int cantidad) throws NegocioException {
        // Regla de negocio: No permitir registros negativos o exagerados
        if (cantidad <= 0) {
            throw new NegocioException("La cantidad de mesas debe ser mayor a cero.");
        }

        if (cantidad > 100) {
            throw new NegocioException("No se pueden registrar más de 100 mesas a la vez.");
        }

        List<Mesa> listaMesas = new ArrayList<>();
        for (int i = 0; i < cantidad; i++) {
            listaMesas.add(new Mesa());
        }

        try {
            // Delegamos al DAO la persistencia
            mesaDAO.registrarMesasMasivas(listaMesas);
        } catch (PersistenciaException ex) {
            throw new NegocioException("Algo fallo al insertar las mesas "  + ex.getMessage());
        }

        return "Se registraron correctamente " + cantidad + " mesas.";
    }
}
