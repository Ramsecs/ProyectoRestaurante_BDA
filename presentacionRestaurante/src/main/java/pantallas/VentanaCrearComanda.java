/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import controladorRestaurante.Coordinador;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import recursos.*;

/**
 *
 * @author josma
 */
public class VentanaCrearComanda extends JFrame {

    private final Coordinador coordinador;
    private JPanel panel_cartas;
    private CardLayout navegador;
    private final int TOTAL_MESAS = 20;

    // Colores corporativos
    private final Color verde = new Color(116, 155, 87);
    private final Color naranja = new Color(255, 184, 77);
    private final Color rojo = new Color(188, 55, 30);

    public VentanaCrearComanda(Coordinador coordinador) {
        this.coordinador = coordinador;
        setTitle("Gestión de Comandas - Sistema Restaurante");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 850);
        setLocationRelativeTo(null);
        initComponents();
    }

    public void initComponents() {
        //FONDO-----------------------------------------------------------------
        PanelFondo panel_principal = new PanelFondo();
        panel_principal.setLayout(new GridBagLayout());
        setContentPane(panel_principal);

        //CONFIGURACIÓN DE CARDLAYOUT-------------------------------------------
        navegador = new CardLayout();
        panel_cartas = new JPanel(navegador);
        panel_cartas.setOpaque(false);

        //CREAR LAS DOS VISTAS--------------------------------------------------
        panel_cartas.add(crearVistaSeleccionMesa(), "SELECCION");
        panel_cartas.add(crearVistaFormularioComanda(), "FORMULARIO");

        GridBagConstraints gbc_fondo = new GridBagConstraints();
        gbc_fondo.fill = GridBagConstraints.BOTH;
        gbc_fondo.weightx = 1.0;
        gbc_fondo.weighty = 1.0;
        gbc_fondo.insets = new Insets(20, 40, 20, 40);
        panel_principal.add(panel_cartas, gbc_fondo);
    }
//==============================================================================

    private JPanel crearVistaSeleccionMesa() {
        PanelRedondeado panel = new PanelRedondeado(50, Color.WHITE);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        Font fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 40f);
        Font fuente_mesas = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 22f);
//===================APARTADO DE LAS MESAS AQUI SE CREAN========================
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 40, 0);
        JLabel lbl = new JLabel("Seleccione una Mesa", SwingConstants.CENTER);
        lbl.setFont(fuente_titulo);
        panel.add(lbl, gbc);

        JPanel grilla = new JPanel(new GridLayout(4, 5, 25, 25));
        grilla.setOpaque(false);

        for (int i = 1; i <= TOTAL_MESAS; i++) {
            BotonMenuAdministrador btn = new BotonMenuAdministrador("Mesa " + i, null, verde, 0, 0, fuente_mesas);
            final int numMesa = i;
            btn.addActionListener(e -> {
                System.out.println("Mesa " + numMesa + " seleccionada.");
                navegador.show(panel_cartas, "FORMULARIO");
            });
            grilla.add(btn);
        }

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(grilla, gbc);

        return panel;
        
    }
//==============================COMANDAS========================================

    private JPanel crearVistaFormularioComanda() {
        PanelRedondeado panel = new PanelRedondeado(50, Color.WHITE);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        Font fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 35f);
        Font fuente_rabbits = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 18f);
        Font fuente_botones = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 16f);
        Font fuente_tabla = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 14f);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0);

        // TITULO
        JLabel lbl_titulo = new JLabel("Agregar Comanda", SwingConstants.CENTER);
        lbl_titulo.setFont(fuente_titulo);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 0);
        panel.add(lbl_titulo, gbc);

        // SECCIÓN CLIENTE
        gbc.gridy = 1;
        JLabel lbl_tipo = new JLabel("Tipo de cliente");
        lbl_tipo.setFont(fuente_rabbits);
        panel.add(lbl_tipo, gbc);

        gbc.gridy = 2;
        String[] tipos = {"Seleccionar...", "Frecuente", "Sin cliente"};
        ComboBoxPersonalizado<String> cb_tipo_cliente = new ComboBoxPersonalizado<>(tipos);
        cb_tipo_cliente.setPreferredSize(new Dimension(0, 35));
        panel.add(cb_tipo_cliente, gbc);

        // Lógica para habilitar/deshabilitar botón de búsqueda según el combo
        gbc.gridy = 3;
        JLabel lbl_nom = new JLabel("Nombre Cliente");
        lbl_nom.setFont(fuente_rabbits);
        panel.add(lbl_nom, gbc);

        gbc.gridy = 4;
        //AQUI ESTA EL BOTON PARA DESPLEGAR EL JDIALOG JOS
        BotonMenuAdministrador btn_buscar_cliente = new BotonMenuAdministrador("Seleccione un cliente", null, naranja, 0, 0, fuente_rabbits);
        btn_buscar_cliente.setPreferredSize(new Dimension(0, 35));
        cb_tipo_cliente.addActionListener(e -> {
            boolean esFrecuente = cb_tipo_cliente.getSelectedItem().toString().equals("Frecuente");
            btn_buscar_cliente.setEnabled(esFrecuente);
            btn_buscar_cliente.setText(esFrecuente ? "Seleccione un cliente" : "No aplica");
        });
        panel.add(btn_buscar_cliente, gbc);

        // SECCIÓN PRODUCTO (Filtro)
        gbc.gridy = 5;
        JLabel lbl_prod = new JLabel("Filtrar por categoría");
        lbl_prod.setFont(fuente_rabbits);
        panel.add(lbl_prod, gbc);

        gbc.gridy = 6;
        String[] productos = {"Todos", "Bebidas", "Postres", "Platillos", "Entradas"};
        ComboBoxPersonalizado<String> cb_tipo_producto = new ComboBoxPersonalizado<>(productos);
        cb_tipo_producto.setPreferredSize(new Dimension(0, 35));
        panel.add(cb_tipo_producto, gbc);

        // --- CONTENEDOR DE TABLA Y NOTAS (FILA 7) ---
        gbc.gridy = 7;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(15, 0, 10, 0);

        JPanel panelMedio = new JPanel(new GridBagLayout());
        panelMedio.setOpaque(false);
        GridBagConstraints gbcMedio = new GridBagConstraints();

        // 1. LA TABLA
        // 1. Definir columnas
        String[] columnas = {"Nombre", "Costo", "Cantidad", "Agregar"};

// 2. Crear el modelo
        DefaultTableModel modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Permitir editar Cantidad (2) y Agregar (3)
                return column == 2 || column == 3;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // ¡ESTO ES LO MÁS IMPORTANTE! 
                // Si no regresas Boolean.class, Swing siempre dibujará texto.
                if (columnIndex == 3) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        /**
         * Aqui estamos creando la parte del checkbox, hay que hacer algunas
         * modificaciones para que la tabla no lo tome como string y si lo
         * dibuje
         */
        TablaEstilizada tabla = new TablaEstilizada(modelo, fuente_tabla);
        
        TableCellRenderer booleanRenderer = tabla.getDefaultRenderer(Boolean.class);
        DefaultCellEditor booleanEditor = (DefaultCellEditor) tabla.getDefaultEditor(Boolean.class);
        tabla.getColumnModel().getColumn(3).setCellRenderer(booleanRenderer);
        tabla.getColumnModel().getColumn(3).setCellEditor(booleanEditor);
        if (booleanRenderer instanceof JComponent) {
            ((JComponent) booleanRenderer).setOpaque(false);
        }
        
        //======================================================================
        
        
        // Datos Fake
        modelo.addRow(new Object[]{"Pastel de Chocolate", 200.00, 1, false});
        modelo.addRow(new Object[]{"Refresco 600ml", 35.00, 1, false});
        modelo.addRow(new Object[]{"Pasta Alfredo", 180.00, 1, false});

        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        scrollTabla.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(naranja), "Menú de Productos"));

        gbcMedio.gridx = 0;
        gbcMedio.weightx = 0.7;
        gbcMedio.fill = GridBagConstraints.BOTH;
        gbcMedio.weighty = 1.0;
        panelMedio.add(scrollTabla, gbcMedio);

        // 2. EL TEXT AREA DE NOTAS
        JPanel panelNotas = new JPanel(new BorderLayout());
        panelNotas.setOpaque(false);
        panelNotas.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));

        JLabel lbl_notas = new JLabel("Notas de la comanda:");
        lbl_notas.setFont(fuente_rabbits);

        JTextArea txt_notas = new JTextArea();
        txt_notas.setFont(new Font("SansSerif", Font.PLAIN, 14));
        txt_notas.setLineWrap(true);
        txt_notas.setWrapStyleWord(true);

        JScrollPane scrollNotas = new JScrollPane(txt_notas);
        scrollNotas.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY));

        panelNotas.add(lbl_notas, BorderLayout.NORTH);
        panelNotas.add(scrollNotas, BorderLayout.CENTER);

        gbcMedio.gridx = 1;
        gbcMedio.weightx = 0.3; // 30% del ancho para notas
        panelMedio.add(panelNotas, gbcMedio);

        panel.add(panelMedio, gbc);

        // PANEL DE BOTONES INFERIORES
        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.setOpaque(false);

        BotonMenuAdministrador btnVolver = new BotonMenuAdministrador("Volver", "/imagenes/volverPNG.png", rojo, 25, 25, fuente_botones);
        btnVolver.setPreferredSize(new Dimension(180, 50));
        btnVolver.addActionListener(e -> navegador.show(panel_cartas, "SELECCION"));

        BotonMenuAdministrador btnAgregar = new BotonMenuAdministrador("Agregar", "/imagenes/aceptarPNG.png", verde, 25, 25, fuente_botones);
        btnAgregar.setPreferredSize(new Dimension(180, 50));

        panelBotones.add(btnVolver, BorderLayout.WEST);
        panelBotones.add(btnAgregar, BorderLayout.EAST);

        gbc.gridy = 8;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(panelBotones, gbc);
        
        btn_buscar_cliente.addActionListener(a -> {coordinador.mostrarDialogClienteComanda(this);});
        
        //TODOS LOS ACTIONS LISTENER TIENEN QUE IR ANTES DE ESTE RETURN JOS=====
        return panel;
        
        
        
    }
    
}
