/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controladorRestaurante;

import daosRestaurante.ClienteDAO;
import daosRestaurante.IClienteDAO;
import objetosNegocioRestaurante.ClienteBO;
import objetosNegocioRestaurante.IClienteBO;

/**
 *
 * @author RAMSES
 */
public class Coordinador {
    
    private final IClienteDAO clienteDAO;
    private final IClienteBO clienteBO;
    
    public Coordinador(){
        this.clienteBO = new ClienteBO();
        this.clienteDAO = new ClienteDAO();
    }
    
    
    
}
