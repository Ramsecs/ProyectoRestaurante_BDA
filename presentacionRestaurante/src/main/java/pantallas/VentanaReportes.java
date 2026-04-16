/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import controladorRestaurante.Coordinador;
import dtosDelRestaurante.ReporteClienteDTO;
import dtosDelRestaurante.ReporteComandaDTO;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.ZoneId;
import static java.time.ZoneId.systemDefault;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.swing.*;
import static javax.swing.BorderFactory.createLineBorder;
import javax.swing.JSpinner.DateEditor;
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
    // Colores basados en tus imágenes
    private final Color naranja_cabecera = new Color(255, 184, 77);
    private final Color rosa_volver = new Color(255, 102, 204);
    private final Color rojo_descargar = new Color(255, 82, 82);
    private final Color gris_fondo_tabla = new Color(240, 240, 240);

    private JPanel cards;
    private CardLayout card_lay;
    private DefaultTableModel modelo_comandas, modelo_clientes;
    private JSpinner spinner_fecha_inicio, spinner_fecha_fin;
    private JLabel lbl_total_ventas;
    private TextFieldPersonalizado txt_nombre, txt_visitas;
    private Font fuente_titulo, fuente_botones, fuente_tabla;

    public VentanaReportes(Coordinador coordinador) {
        this.coordinador = coordinador;
        setTitle("Módulo de Reportes");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1100, 800);
        setLocationRelativeTo(null);

        // Carga de fuentes personalizadas
        fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 45f);
        fuente_botones = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 20f);
        fuente_tabla = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 18f);

        initComponents();
        
        // Carga inicial de datos
        SwingUtilities.invokeLater(() -> {
            actualizarTablaClientes("", 0);
            aplicarFiltroComandas();
        });
    }

    private void initComponents() {
        PanelFondo panel_fondo = new PanelFondo(); // Usa fondo.jpg
        panel_fondo.setLayout(new GridBagLayout());
        setContentPane(panel_fondo);

        PanelRedondeado contenedor_principal = new PanelRedondeado(50, Color.WHITE);
        contenedor_principal.setLayout(new BorderLayout());
        contenedor_principal.setBorder(new EmptyBorder(30, 50, 30, 50));

        card_lay = new CardLayout();
        cards = new JPanel(card_lay);
        cards.setOpaque(false);

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
        btn_volver.addActionListener(e -> coordinador.regresarMenuMesero());

        return panel;
    }

    private JPanel crearPanelReporteComandas() {
        JPanel panel = new JPanel(new BorderLayout(0, 20));
        panel.setOpaque(false);

        JLabel titulo = new JLabel("Reporte de Comandas", SwingConstants.CENTER);
        titulo.setFont(fuente_titulo);
        panel.add(titulo, BorderLayout.NORTH);

        // Panel de Filtros superior
        JPanel panel_filtros = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 10));
        panel_filtros.setOpaque(false);
        
        panel_filtros.add(crearEtiqueta("Desde:"));
        spinner_fecha_inicio = crearSelectorFecha();
        panel_filtros.add(spinner_fecha_inicio);

        panel_filtros.add(crearEtiqueta("Hasta:"));
        spinner_fecha_fin = crearSelectorFecha();
        panel_filtros.add(spinner_fecha_fin);

        spinner_fecha_inicio.addChangeListener(e -> aplicarFiltroComandas());
        spinner_fecha_fin.addChangeListener(e -> aplicarFiltroComandas());

        // Configuración de Tabla
        String[] col = {"ID", "Mesa", "Fecha", "Hora", "Estado", "Cliente", "Total"};
        modelo_comandas = new DefaultTableModel(col, 0) { 
            @Override public boolean isCellEditable(int r, int c) { 
                return false; 
            }
        };
        JTable tabla = crearTablaEstilizada(modelo_comandas);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(naranja_cabecera, 2));

        // Panel Central para tabla y total
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
        txt_nombre.setBackground(naranja_cabecera);
        panel_filtros.add(txt_nombre);

        panel_filtros.add(crearEtiqueta("Min. Visitas:"));
        txt_visitas = new TextFieldPersonalizado(5);
        txt_visitas.setBackground(naranja_cabecera);
        panel_filtros.add(txt_visitas);

        // Listener para tiempo real
        DocumentListener dl = new DocumentListener() {
            public void insertUpdate(DocumentEvent e) { 
                actualizar(); 
            }
            public void removeUpdate(DocumentEvent e) {
                actualizar(); 
            }
            public void changedUpdate(DocumentEvent e) { 
                actualizar(); 
            }
            private void actualizar() {
                String nom = txt_nombre.getText().trim();
                int vis = 0;
                try { if(!txt_visitas.getText().trim().isEmpty()) vis = Integer.parseInt(txt_visitas.getText().trim()); } catch(Exception ex){}
                actualizarTablaClientes(nom, vis);
            }
        };
        txt_nombre.getDocument().addDocumentListener(dl);
        txt_visitas.getDocument().addDocumentListener(dl);

        String[] col = {"Nombre Cliente", "Visitas", "Total Gastado", "Última Compra"};
        modelo_clientes = new DefaultTableModel(col, 0) { @Override public boolean isCellEditable(int r, int c) { return false; }};
        JTable tabla = crearTablaEstilizada(modelo_clientes);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setBorder(createLineBorder(naranja_cabecera, 2));

        JPanel centro = new JPanel(new BorderLayout(0, 10));
        centro.setOpaque(false);
        centro.add(panel_filtros, BorderLayout.NORTH);
        centro.add(scroll, BorderLayout.CENTER);

        panel.add(centro, BorderLayout.CENTER);
        panel.add(crearPanelBotonesNavegacion("CLIENTES"), BorderLayout.SOUTH);

        return panel;
    }

    // --- METODOS DE ESTILO DE TABLA ---
    private JTable crearTablaEstilizada(DefaultTableModel modelo) {
        JTable tabla = new JTable(modelo);
        tabla.setFont(fuente_tabla);
        tabla.setRowHeight(35);
        tabla.setSelectionBackground(new Color(255, 230, 200));
        tabla.setShowVerticalLines(true);
        tabla.setGridColor(naranja_cabecera);

        // Cabecera naranja como en la imagen
        JTableHeader header = tabla.getTableHeader();
        header.setPreferredSize(new Dimension(0, 45));
        header.setDefaultRenderer(new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable tabla, Object valor, boolean seleccionado, boolean has_focus, int fila, int columna) {
                super.getTableCellRendererComponent(tabla, valor, seleccionado, has_focus, fila, columna);
                setBackground(naranja_cabecera);
                setForeground(Color.WHITE);
                setFont(fuente_tabla);
                setHorizontalAlignment(JLabel.CENTER);
                setBorder(BorderFactory.createMatteBorder(0, 0, 0, 1, Color.WHITE));
                return this;
            }
        });

        // Alineación de celdas
        DefaultTableCellRenderer center_renderer = new DefaultTableCellRenderer();
        center_renderer.setHorizontalAlignment(JLabel.CENTER);
        for (int i = 0; i < tabla.getColumnCount(); i++) {
            tabla.getColumnModel().getColumn(i).setCellRenderer(center_renderer);
        }

        return tabla;
    }

    private JPanel crearPanelBotonesNavegacion(String tipo_reporte) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setOpaque(false);

        BotonMenuAdministrador btn_volver = new BotonMenuAdministrador("Volver", "/imagenes/volverPNG.png", rosa_volver, 35, 35, fuente_botones);
        BotonMenuAdministrador btn_pdf = new BotonMenuAdministrador("Descargar PDF", "/imagenes/aceptarPNG.png", rojo_descargar, 35, 35, fuente_botones);

        btn_volver.addActionListener(e -> card_lay.show(cards, "MENU"));
        btn_pdf.addActionListener(e -> {
            if (tipo_reporte.equals("COMANDAS")) {
                GeneradorReporte.generarPDFDesdeTabla(modelo_comandas, "REPORTE DE COMANDAS");
            } else {
                GeneradorReporte.generarPDFDesdeTabla(modelo_clientes, "REPORTE DE CLIENTES");
            }
        });

        panel.add(btn_volver, BorderLayout.WEST);
        panel.add(btn_pdf, BorderLayout.EAST);
        return panel;
    }

    private JLabel crearEtiqueta(String texto) {
        JLabel l = new JLabel(texto);
        l.setFont(fuente_tabla);
        return l;
    }

    private JSpinner crearSelectorFecha() {
        SpinnerDateModel model = new SpinnerDateModel(new Date(), null, null, Calendar.DAY_OF_MONTH);
        JSpinner spinner = new JSpinner(model);
        DateEditor editor = new DateEditor(spinner, "dd/MM/yyyy");
        spinner.setEditor(editor);
        spinner.setPreferredSize(new Dimension(160, 40));
        return spinner;
    }

    // --- LOGICA DE DATOS ---
    private void aplicarFiltroComandas() {
        LocalDate inicio = obtenerFechaDeSpinner(spinner_fecha_inicio);
        LocalDate fin = obtenerFechaDeSpinner(spinner_fecha_fin);
        
        List<ReporteComandaDTO> lista = coordinador.generarReporteComandas(inicio.atStartOfDay(), fin.atTime(LocalTime.MAX));
        modelo_comandas.setRowCount(0);
        double total = 0;
        for (ReporteComandaDTO r : lista) {
            modelo_comandas.addRow(new Object[]{ "#"+r.getId(), r.getMesa_id(), r.getFecha(), r.getHora(), r.getEstado(), r.getCliente(), "$"+String.format("%.2f", r.getTotal())});
            total += r.getTotal();
        }
        lbl_total_ventas.setText("TOTAL VENTA PERIODO: $" + String.format("%.2f", total));
    }

    private void actualizarTablaClientes(String nombre, int visitas) {
        List<ReporteClienteDTO> lista = coordinador.generarReporteClientes(nombre, visitas);
        modelo_clientes.setRowCount(0);
        for (ReporteClienteDTO r : lista) {
            modelo_clientes.addRow(new Object[]{ r.getNombre_completo(), r.getVisitas(), "$"+String.format("%.2f", r.getTotal_gastado()), r.getFecha_ultima_comanda() });
        }
    }

    private LocalDate obtenerFechaDeSpinner(JSpinner spinner) {
        Date date = (Date) spinner.getValue();
        return date.toInstant().atZone(systemDefault()).toLocalDate();
    }
    
    
}