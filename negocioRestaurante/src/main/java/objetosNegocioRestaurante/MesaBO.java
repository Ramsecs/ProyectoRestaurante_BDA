/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocioRestaurante;

import daosRestaurante.IMesaDAO;
import daosRestaurante.MesaDAO;
import validadores.Validaciones;

/**
 *
 * @author josma
 */
public class MesaBO implements IMesaBO {
    private static MesaBO mesaBO;

    private IMesaDAO mesaDAO = MesaDAO.getInstanceMesaDAO();
    private Validaciones validar = new Validaciones();

    //Constructor privado
    private MesaBO() {

    }

    //Metodo para el singleton
    public static MesaBO getInstanceComandaBO() {
        if (mesaBO == null) {
            mesaBO = new MesaBO();
        }

        return mesaBO;
    }
}
