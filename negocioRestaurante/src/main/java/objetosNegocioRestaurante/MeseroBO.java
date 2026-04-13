/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocioRestaurante;


import daosRestaurante.IMeseroDAO;
import daosRestaurante.MeseroDAO;
import validadores.Validaciones;

/**
 *
 * @author josma
 */
public class MeseroBO implements IMeseroBO {
    private static MeseroBO meseroBO;

    private IMeseroDAO meseroDAO = MeseroDAO.getInstanceMeseroDAO();
    private Validaciones validar = new Validaciones();
    
    private MeseroBO() {

    }

    //Metodo para el singleton
    public static MeseroBO getInstanceMeseroBO() {
        if (meseroBO == null) {
            meseroBO = new MeseroBO();
        }

        return meseroBO;
    }
}
