/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package observadorRestaurante;

import dtosDelRestaurante.ClienteBusquedaDTO;

/**
 *Se supone que con esta interface vamos a conseguir un descoplamiento con el coodinador y la
 * función de actualizar dentro de la ventana de clientes, practicamente este anda de metiche en la ventana
 * ve un cambio y grita que alguien se actualizo, se podria decir que es Observador = Contrato,
 * Ventana = Emisor y Coordinador = Receptor. 
 * @author josma
 */
public interface Observador {
    public void actualizar_empleado(ClienteBusquedaDTO clienteDTO);
    public void actualizar_ingrediente(); //<-- ESTE METODO ES PARA PODER ACTUALIZAR UNICAMENTE
    //LA CANTIDAD DEL INGREDIENTE CUANDO SE HAGA REESTOCK OCUPA EL DTO, PERO COMO NO ES MI RUBRO
    //NO LE MUEVO
}
