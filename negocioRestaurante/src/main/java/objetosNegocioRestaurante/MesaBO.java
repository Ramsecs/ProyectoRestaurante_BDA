/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocioRestaurante;

import daosRestaurante.IMesaDAO;
import daosRestaurante.MesaDAO;
import excepcionesRestaurante.NegocioException;
import java.util.ArrayList;
import java.util.List;
import validadores.Validaciones;

/**
 *Esta BO es  para mesa, donde podemos  establecer la conexión entre capas
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
     * @return 
     */
    public static MesaBO getInstanceComandaBO() {
        if (mesaBO == null) {
            mesaBO = new MesaBO();
        }

        return mesaBO;
    }
    /**
     * Metodo BO para obtener las mesas ocupadas para que el coordinador decida hacer algo con ellas
     * @return
     * @throws NegocioException 
     */
    @Override
    public List<Long> obtenerMesasOcupadas() throws NegocioException {
        try{
            List<Long> mesas_ocupadas = mesaDAO.obtenerMesasOcupadas();
            
            if (mesas_ocupadas == null) {
                return new ArrayList<>();
            }
            return mesas_ocupadas;
        }catch(Exception e){
            throw new NegocioException("Error al consultar el estado de las mesas " + e.getMessage());
        }
    }
}
