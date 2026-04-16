/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import com.toedter.calendar.JDateChooser;
import controladorRestaurante.Coordinador;
import dtosDelRestaurante.ReporteClienteDTO;
import dtosDelRestaurante.ReporteComandaDTO;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import recursos.*;
import reportes.GeneradorReporte;

public class VentanaReportes extends JFrame {

    private final Coordinador coordinador;
    
    // Paleta de colores corporativa
    private final Color naranja_cabecera = new Color(255, 184, 77);
    private final Color rosa_volver = new Color(255, 102, 204);
    private final Color rojo_descargar = new Color(255, 82, 82);

    private JPanel cards;
    private CardLayout card_lay;
    private DefaultTableModel modelo_comandas, modelo_clientes;
    
    // Nuevos componentes visuales de fecha
    private JDateChooser jd_inicio, jd_fin; 
    
    private JLabel lbl_total_ventas;
    private TextFieldPersonalizado txt_nombre, txt_visitas;
    private Font fuente_titulo, fuente_botones, fuente_tabla;

    public VentanaReportes(Coordinador coordinador) {
        this.coordinador = coordinador;
        setTitle("Módulo de Reportes - Gestión de Restaurante");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 800);
        setLocationRelativeTo(null);

        // Carga de fuentes
        fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 45f);
        fuente_botones = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 20f);
        fuente_tabla = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 18f);

        initComponents();
        
        // Carga inicial
        SwingUtilities.invokeLater(() -> {
            actualizarTablaClientes("", 0);
            aplicarFiltroComandas();
        });
    }

    private void initComponents() {
        PanelFondo panel_fondo = new PanelFondo();
        panel_fondo.setLayout(new GridBagLayout());
        setContentPane(panel_fondo);

        PanelRedondeado contenedor_principal = new PanelRedondeado(50, Color.WHITE);
        contenedor_principal.setLayout(new BorderLayout());
        contenedor_principal.setBorder(new EmptyBorder(30, 50, 30, 50));

        card_lay = new CardLayout();
        cards = new JPanel(card_lay);
        cards.setOpaque(false);

        // Registro de vistas
        cards.add(crearPanelSeleccion(), "MENU");
        cards.add(crearPanelReporteComandas(), "COMANDAS");
        cards.add(crearPanelReporteClientes(), "CLIENTES");

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.BOTH;
        gbc.weightx = 1.0; gbc.weighty = 1.0;
        gbc.insets = new Insets(40, 40, 40, 40);

        panel_fondo.add(contenedor_principal, gbc);
        contenedor_principal.add(cards, BorderLayout.CENTER);
    }

    private JPanel crearPanelSeleccion() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0; gbc.insets = new Insets(20, 0, 20, 0); gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel label_titulo = new JLabel("Reportes", SwingConstants.CENTER);
        label_titulo.setFont(fuente_titulo);
        gbc.gridy = 0; panel.add(label_titulo, gbc);

        BotonMenuAdministrador btn_clientes = new BotonMenuAdministrador("Reporte Cliente", "/imagenes/clientePNG.png", rojo_descargar, 50, 50, fuente_botones);
        BotonMenuAdministrador btn_comandas = new BotonMenuAdministrador("Reporte Comandas", "/imagenes/comandaPNG.png", rojo_descargar, 50, 50, fuente_botones);
        BotonMenuAdministrador btn_volver = new BotonMenuAdministrador("Volver", "/imagenes/volverPNG.png", rosa_volver, 50, 50, fuente_botones);

        gbc.gridy = 1; panel.add(btn_clientes, gbc);
        gbc.gridy = 2; panel.add(btn_comandas, gbc);
        gbc.gridy = 3; panel.add(btn_volver, gbc);

        btn_clientes.addActionListener(e -> card_lay.show(cards, "CLIENTES"));
        btn_comandas.addActionListener(e -> card_lay.show(cards, "COMANDAS"));
        btn_volver.addActionListener(e -> coordinador.regresarMenuAdmin());

        return panel;
    }

    private JPanel crearPanelReporteComandas() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setOpaque(false);

        JLabel titulo = new JLabel("Reporte de Comandas", SwingConstants.CENTER);
        titulo.setFont(fuente_titulo);
        panel.add(titulo, BorderLayout.NORTH);

        // Panel de Filtros con JDateChooser
        JPanel panel_filtros = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panel_filtros.setOpaque(false);
        
        panel_filtros.add(crearEtiqueta("Desde:"));
        jd_inicio = configurarCalendario();
        panel_filtros.add(jd_inicio);

        panel_filtros.add(crearEtiqueta("Hasta:"));
        jd_fin = configurarCalendario();
        panel_filtros.add(jd_fin);

        // Actualizacion automatica al cambiar fecha
        jd_inicio.addPropertyChangeListener("date", evt -> aplicarFiltroComandas());
        jd_fin.addPropertyChangeListener("date", evt -> aplicarFiltroComandas());

        String[] col = {"ID", "Mesa", "Fecha", "Hora", "Estado", "Cliente", "Total"};
        modelo_comandas = new DefaultTableModel(col, 0) { 
            @Override public boolean isCellEditable(int r, int c) { return false; }
        };
        
        JTable tabla = crearTablaEstilizada(modelo_comandas);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(naranja_cabecera, 2));

        JPanel centro = new JPanel(new BorderLayout(0, 10));
        centro.setOpaque(false);
        centro.add(panel_filtros, BorderLayout.NORTH);
        centro.add(scroll, BorderLayout.CENTER);

        lbl_total_ventas = new JLabel("TOTAL VENTA PERIODO: $0.00", SwingConstants.CENTER);
        lbl_total_ventas.setFont(fuente_tabla);
        centro.add(lbl_total_ventas, BorderLayout.SOUTH);

        panel.add(centro, BorderLayout.CENTER);
        panel.add(crearPanelBotonesNavegacion("COMANDAS"), BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Configura el componentes de seleccion de fechas 
     * para la filtracion de registros en la tabla de reportes
     * de comandas.
     * 
     * @return JDateChooser
     */
    private JDateChooser configurarCalendario() {
        JDateChooser chooser = new JDateChooser();
        chooser.setDate(new Date()); 
        chooser.setDateFormatString("dd/MM/yyyy");
        chooser.setPreferredSize(new Dimension(180, 40));
        chooser.setFont(fuente_tabla);

        // Buscamos el boton de forma segura recorriendo los componentes internos
        for (Component comp : chooser.getComponents()) {
            if (comp instanceof JButton) {
                JButton btn = (JButton) comp;
                btn.setBackground(naranja_cabecera);
                btn.setCursor(new Cursor(Cursor.HAND_CURSOR));
                // Opcional: Quitar bordes molestos del boton
                btn.setBorder(BorderFactory.createEmptyBorder());
                btn.setContentAreaFilled(true);
            }
        }

        return chooser;
    }

    /**
     * Crea el panel completo de reporte de clientes frecuentes
     * cuando se selecciona el boton de cliente frecuente 
     * muestra este panel.
     * 
     * @return JPanel de reporte de cliente frecuente
     */
    private JPanel crearPanelReporteClientes() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setOpaque(false);

        JLabel titulo = new JLabel("Reporte de Clientes", SwingConstants.CENTER);
        titulo.setFont(fuente_titulo);
        panel.add(titulo, BorderLayout.NORTH);

        JPanel panel_filtros = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        panel_filtros.setOpaque(false);

        panel_filtros.add(crearEtiqueta("Nombre:"));
        txt_nombre = new TextFieldPersonalizado(15);
        txt_nombre.setBackground(new Color(255, 245, 230));
        panel_filtros.add(txt_nombre);

        panel_filtros.add(crearEtiqueta("Min. Visitas:"));
        txt_visitas = new TextFieldPersonalizado(5);
        txt_visitas.setBackground(new Color(255, 245, 230));
        panel_filtros.add(txt_visitas);

        DocumentListener dl = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { actualizar(); }
            public void removeUpdate(DocumentEvent e) { actualizar(); }
            public void changedUpdate(DocumentEvent e) { actualizar(); }
            private void actualizar() {
                String nom = txt_nombre.getText().trim();
                int vis = 0;
                try { 
                    if(!txt_visitas.getText().trim().isEmpty()) vis = Integer.parseInt(txt_visitas.getText().trim()); 
                } catch(Exception ex){
                }
                actualizarTablaClientes(nom, vis);
            }
        };
        txt_nombre.getDocument().addDocumentListener(dl);
        txt_visitas.getDocument().addDocumentListener(dl);

        String[] col = {"Nombre Cliente", "Visitas", "Total Gastado", "Última Compra"};
        modelo_clientes = new DefaultTableModel(col, 0) { 
            @Override public boolean isCellEditable(int r, int c) { 
                return false; 
            }};
        JTable tabla = crearTablaEstilizada(modelo_clientes);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(BorderFactory.createLineBorder(naranja_cabecera, 2));

        JPanel centro = new JPanel(new BorderLayout(0, 10));
        centro.setOpaque(false);
        centro.add(panel_filtros, BorderLayout.NORTH);
        centro.add(scroll, BorderLayout.CENTER);

        panel.add(centro, BorderLayout.CENTER);
        panel.add(crearPanelBotonesNavegacion("CLIENTES"), BorderLayout.SOUTH);

        return panel;
    }

    /**
     * Crea una tabla estilizada para las ventanas de reportes 
     * haciendo que tenga un diseño mucho mejor.
     * 
     * @param modelo
     * @return JTable tabla para la vetana de reportes
     */
    private JTable crearTablaEstilizada(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo);
        tabla.setFont(fuente_tabla);
        tabla.setRowHeight(35);
        tabla.setSelectionBackground(new Color(255, 230, 200));
        //Cabecera de color naranja
        tabla.setGridColor(naranja_cabecera);

        JTableHeader header = tabla.getTableHeader();
        header.setPreferredSize(new Dimension(0, 45));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable t, Object v, boolean s, boolean f, int r, int c) {
                super.getTableCellRendererComponent(t, v, s, f, r, c);
                setBackground(naranja_cabecera);
                setForeground(Color.WHITE);
                setFont(fuente_tabla);
                setHorizontalAlignment(JLabel.CENTER);
                return this;
            }
        });

        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
        return tabla;
    }

    /**
     * Crea el panel para los botones de generar pdf 
     * y volver en cada uno de los paneles de reportes.
     * 
     * @param tipo
     * @return JPanel Crea el panel para los botones de volver y pdf
     */
    private JPanel crearPanelBotonesNavegacion(String tipo) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        BotonMenuAdministrador btn_volver = new BotonMenuAdministrador("Volver", "/imagenes/volverPNG.png", rosa_volver, 35, 35, fuente_botones);
        BotonMenuAdministrador btn_pdf = new BotonMenuAdministrador("Descargar PDF", "/imagenes/aceptarPNG.png", rojo_descargar, 35, 35, fuente_botones);

        btn_volver.addActionListener(e -> card_lay.show(cards, "MENU"));
        
        btn_pdf.addActionListener(e -> {
            if (tipo.equals("COMANDAS")) {
                GeneradorReporte.generarPDFDesdeTabla(modelo_comandas, "REPORTE HISTÓRICO DE COMANDAS");
            } else {
                GeneradorReporte.generarPDFDesdeTabla(modelo_clientes, "REPORTE DE FIDELIDAD DE CLIENTES");
            }
        });

        panel.add(btn_volver, BorderLayout.WEST);
        panel.add(btn_pdf, BorderLayout.EAST);
        return panel;
    }

    /**
     * Regresa una etiqueta con el tipo de fuente 
     * que usa la ventana de la tabla.
     * 
     * @param texto
     * @return JLabel de la etiqueta estilizada 
     */
    private JLabel crearEtiqueta(String texto) {
        JLabel label = new JLabel(texto);
        label.setFont(fuente_tabla);
        return label;
    }

    /**
     * Aplica los filtros de periodos a la ventana de reportes de comandas, 
     * muestra con filtros: fecha, hora, mesa, total, 
     * estado, cliente y el total acumulado del periodo.
     */
    private void aplicarFiltroComandas() {
        if (jd_inicio.getDate() == null || jd_fin.getDate() == null) return;

        // Convertir Date a LocalDate para el filtrado
        LocalDate inicio = jd_inicio.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate fin = jd_fin.getDate().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();

        // Validación: Fecha inicio no puede ser posterior a fecha fin
        if (inicio.isAfter(fin)) {
            modelo_comandas.setRowCount(0);
            lbl_total_ventas.setText("ERROR: Rango de fechas inválido");
            lbl_total_ventas.setForeground(Color.RED);
            return;
        } else {
            lbl_total_ventas.setForeground(Color.BLACK);
        }

        // Obtener lista desde el coordinador (Capa Lógica/DAO)
        List<ReporteComandaDTO> lista = coordinador.generarReporteComandas(inicio.atStartOfDay(), fin.atTime(LocalTime.MAX));
        
        modelo_comandas.setRowCount(0);
        double totalAcumulado = 0;

        for (ReporteComandaDTO comanda : lista) {
            // Manejo del cliente "si aplica"
            String nombreCliente = (comanda.getCliente() == null || comanda.getCliente().isEmpty()) ? "Público General" : comanda.getCliente();

            modelo_comandas.addRow(new Object[]{ 
                "#" + comanda.getId(), 
                comanda.getMesa_id(), 
                comanda.getFecha(), 
                comanda.getHora(), 
                comanda.getEstado(), 
                nombreCliente, 
                "$" + String.format("%.2f", comanda.getTotal())
            });
            
            totalAcumulado += comanda.getTotal();
        }

        // Mostrar el total acumulado del periodo seleccionado (Requerimiento cumplido)
        lbl_total_ventas.setText("TOTAL VENTA PERIODO: $" + String.format("%.2f", totalAcumulado));
    }

    /**
     * Actualiza la tabla de clientes usando los filtros que 
     * aparecen en el panel de clientes frecuentes, y muestra
     * los registros que aplican para esos mismos filtros.
     * 
     * @param nombre
     * @param visitas 
     */
    private void actualizarTablaClientes(String nombre, int visitas) {
        List<ReporteClienteDTO> lista = coordinador.generarReporteClientes(nombre, visitas);
        modelo_clientes.setRowCount(0);
        for (ReporteClienteDTO reporte_cliente : lista) {
            modelo_clientes.addRow(new Object[]{ 
                reporte_cliente.getNombre_completo(), reporte_cliente.getVisitas(), 
                "$"+String.format("%.2f", reporte_cliente.getTotal_gastado()), reporte_cliente.getFecha_ultima_comanda() 
            });
        }
    }
}