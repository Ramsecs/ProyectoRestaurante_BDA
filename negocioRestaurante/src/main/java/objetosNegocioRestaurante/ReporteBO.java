/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package objetosNegocioRestaurante;

import daosRestaurante.ClienteDAO;
import daosRestaurante.IClienteDAO;
import daosRestaurante.IReporteDAO;
import daosRestaurante.ReporteDAO;
import dtosDelRestaurante.ReporteClienteDTO;
import dtosDelRestaurante.ReporteComandaDTO;
import entidadesRestaurante.Comanda;
import java.time.LocalDateTime;
import java.util.List;
import validadores.Validaciones;

/**
 *
 * @author RAMSES
 */
public class ReporteBO implements IReporteBO{
    
    private static ReporteBO reporteBO;

    private IReporteDAO reporteDAO;

    private Validaciones validar = new Validaciones();

    /**
     * Constructor privado para evitar la instanciacion externa y mantener el
     * patron Singleton.
     */
    private ReporteBO() {
        reporteDAO = ReporteDAO.getInstanceReporteDAO();
        
    }

    /**
     * Obtiene la instancia unica de ClienteBO, si no existe, la crea.
     *
     * @return ClienteBO.
     */
    public static ReporteBO getInstanceReporteBO() {
        if (reporteBO == null) {
            reporteBO = new ReporteBO();
        }
        return reporteBO;
    }

    /**
     * Se obtiene una lista de comandas tipo DTO de una fecha de inicio
     * a una fecha fin, para la vista de reportes de comandas.
     * 
     * @param inicio
     * @param fin
     * @return Lista de reporte comanda DTO
     */
    @Override
    public List<ReporteComandaDTO> generarReporteComandas(LocalDateTime inicio, LocalDateTime fin) {
        List<Comanda> comandas = reporteDAO.consultarComandasPorPeriodo(inicio, fin);

        return comandas.stream().map(c -> {
            // Formateo de fecha y hora
            String fecha = c.getFecha_hora_creacion().toLocalDate().toString();
            String hora = c.getFecha_hora_creacion().toLocalTime().withNano(0).toString();

            // Manejo de cliente nulo
            String nombreCliente = (c.getCliente() != null) 
                ? c.getCliente().getNombre() + " " + c.getCliente().getApellido_paterno() 
                : "Público General";

            return new ReporteComandaDTO(
                c.getId(),
                c.getMesa().getId(),
                fecha,
                hora,
                c.getEstado_comanda().toString(),
                nombreCliente,
                c.getTotal_venta()
            );
        }).toList();
    }

    /**
     * Obtiene una lista de clientes tipo DTO para la vista de reportes
     * de clientes frecuentes, usando como filtro el minimo de visitas 
     * y el nombre del cliente.
     * 
     * @param filtro_nombre
     * @param minVisitas
     * @return lista de reporte de cliente DTO
     */
    @Override
    public List<ReporteClienteDTO> obtenerReporteClientes(String filtro_nombre, int minVisitas) {
        List<Object[]> resultados = reporteDAO.consultarReporteClientes(filtro_nombre, minVisitas);

        return resultados.stream()
            .map(r -> {
                String nombreCompleto = r[0] + " " + r[1];
                Long visitas = (Long) r[2];
                Double totalGasto = (Double) r[3];
                // Si no hay fecha (cliente sin compras), ponemos un texto descriptivo
                String ultimaFecha = (r[4] != null) ? r[4].toString() : "Sin registros";

                return new ReporteClienteDTO(nombreCompleto, visitas, totalGasto, ultimaFecha);
            })
            .toList();
    }
    
}
