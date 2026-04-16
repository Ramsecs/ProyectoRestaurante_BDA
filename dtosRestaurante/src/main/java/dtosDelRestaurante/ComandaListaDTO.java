/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dtosDelRestaurante;

import entidadesEnumeradorDTO.EstadoComandaDTO;
import enumEntidades.EstadoComanda;

/**
 * Clase DTO de comanda que permite el nombre del cliente
 *
 * @author josma
 */
public class ComandaListaDTO {

    /**
     * Id de la comanda.
     */
    private Long id;
    /**
     * Folio de la comanda que se contruye, pero no se guarda.
     */
    private String folio;
    /**
     * Id de la mesa asociado a la comanda.
     */
    private Long id_mesa;
    /**
     * Id del cliente asociado a la comanda.
     */
    private Long id_cliente;
    /**
     * Nombre del cliente asociado a la comanda.
     */
    private String nombre_cliente;
    /**
     * Apellido paterno del cliente.
     */
    private String apellido_cliente_paterno;
    /**
     * Apellido materno del cliente.
     */
    private String apellido_cliente_materno;
    /**
     * Estado de la comanda.
     */
    private EstadoComandaDTO estado;

    /**
     * Constructor vacio.
     */
    public ComandaListaDTO() {
    }

    /**
     * Constructor sin id
     *
     * @param folio construido
     * @param id_mesa de la comanda
     * @param id_cliente de la comanda
     * @param nombre_cliente de la comanda
     * @param estado de la comanda
     */
    public ComandaListaDTO(String folio, Long id_mesa, Long id_cliente, String nombre_cliente, EstadoComandaDTO estado) {
        this.folio = folio;
        this.id_mesa = id_mesa;
        this.id_cliente = id_cliente;
        this.nombre_cliente = nombre_cliente;
        this.estado = estado;
    }

    /**
     * Consutructor con todo
     *
     * @param id de la ocmanda
     * @param folio construido de la comanda
     * @param id_mesa de la comanda
     * @param id_cliente de la comanda
     * @param nombre_cliente de la comanda
     * @param estado de la comanda
     */
    public ComandaListaDTO(Long id, String folio, Long id_mesa, Long id_cliente, String nombre_cliente, EstadoComandaDTO estado) {
        this.id = id;
        this.folio = folio;
        this.id_mesa = id_mesa;
        this.id_cliente = id_cliente;
        this.nombre_cliente = nombre_cliente;
        this.estado = estado;
    }

    /**
     * Obtener el id de la comanda
     *
     * @return
     */

    public Long getId() {
        return id;
    }

    /**
     * Setea el id de la comanda
     *
     * @param id de la comanda
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * Obtiene el folio de la comanda
     *
     * @return
     */
    public String getFolio() {
        return folio;
    }

    /**
     * Setea el folio de la comanda, esto es solo vista en la tabla
     *
     * @param folio construido de la comanda
     */
    public void setFolio(String folio) {
        this.folio = folio;
    }

    /**
     * Obtiene el id de la mesa
     *
     * @return
     */
    public Long getId_mesa() {
        return id_mesa;
    }

    /**
     * Setea el id de la mesa
     *
     * @param id_mesa de la mesa de la comanda
     */
    public void setId_mesa(Long id_mesa) {
        this.id_mesa = id_mesa;
    }

    /**
     * Obtiene el id del cliente
     *
     * @return
     */
    public Long getId_cliente() {
        return id_cliente;
    }

    /**
     * Setea el id del cliente
     *
     * @param id_cliente del cliente de la comanda
     */
    public void setId_cliente(Long id_cliente) {
        this.id_cliente = id_cliente;
    }

    /**
     * Obtiene el nombre del cliente
     *
     * @return
     */

    public String getNombre_cliente() {
        return nombre_cliente;
    }

    /**
     * Setea el nombre del cliente
     *
     * @param nombre_cliente asociado a la comanda
     */

    public void setNombre_cliente(String nombre_cliente) {
        this.nombre_cliente = nombre_cliente;
    }

    /**
     * Obtiene el estado de la comanda
     *
     * @return
     */
    public EstadoComandaDTO getEstado() {
        return estado;
    }

    /**
     * Setea el estado de la comanda
     *
     * @param estado de la comanda
     */
    public void setEstado(EstadoComandaDTO estado) {
        this.estado = estado;
    }

    /**
     * Obtiene el apellido paterno del cliente
     *
     * @return
     */

    public String getApellido_cliente_paterno() {
        return apellido_cliente_paterno;
    }

    /**
     * Setea el apellido paterno del cliente
     *
     * @param apellido_cliente_paterno del cliente
     */
    public void setApellido_cliente_paterno(String apellido_cliente_paterno) {
        this.apellido_cliente_paterno = apellido_cliente_paterno;
    }

    /**
     * Obtiene el apellido materno del cliente
     *
     * @return
     */

    public String getApellido_cliente_materno() {
        return apellido_cliente_materno;
    }

    /**
     * Setea el apellido paterno del cliente
     *
     * @param apellido_cliente_materno
     */
    public void setApellido_cliente_materno(String apellido_cliente_materno) {
        this.apellido_cliente_materno = apellido_cliente_materno;
    }

    /**
     * toStringo del DTO
     *
     * @return
     */
    @Override
    public String toString() {
        return (this.folio != null) ? this.folio : "";
    }

}
