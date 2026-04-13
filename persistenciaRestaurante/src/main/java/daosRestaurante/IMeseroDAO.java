/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package daosRestaurante;

import entidadesRestaurante.Mesero;
import excepcionesRestaurante.PersistenciaException;

/**
 *
 * @author josma
 */
public interface IMeseroDAO {
    public Mesero buscarPorId(Long id) throws PersistenciaException;
}
