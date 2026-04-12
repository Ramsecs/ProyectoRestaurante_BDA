/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import controladorRestaurante.Coordinador;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import recursos.*;

/**
 * Ventana para modificar una comanda existente.
 * Permite editar cantidades, eliminar productos, agregar nuevos y actualizar notas.
 * * @author josma
 */
public class VentanaDialogModificar extends JDialog {

    private final Coordinador coordinador;
    private final Color naranja = new Color(255, 184, 77);
    private final Color verde = new Color(116, 155, 87);
    private final Color rojo = new Color(188, 55, 30);

    private DefaultTableModel modelo_actuales;
    private DefaultTableModel modelo_catalogo;
    private TablaEstilizada tabla_actuales;
    private TablaEstilizada tabla_catalogo;
    private JTextArea txt_notas;

    public VentanaDialogModificar(Coordinador coordinador, JFrame padre) {
        super(padre, true);
        this.coordinador = coordinador;
        setTitle("Modificar Comanda");
        setSize(1000, 900);
        setLocationRelativeTo(padre);
        initComponents();
    }

    private void initComponents() {
        //------------- SECCIÓN DEL FONDO -------------
        PanelFondo panel_fondo = new PanelFondo();
        panel_fondo.setLayout(new GridBagLayout());
        setContentPane(panel_fondo);

        //------------- SECCIÓN DEL PANEL BLANCO -------------
        PanelRedondeado cuadro_blanco = new PanelRedondeado(50, Color.WHITE);
        cuadro_blanco.setLayout(new GridBagLayout());
        cuadro_blanco.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        
        GridBagConstraints gbc_principal = new GridBagConstraints();
        gbc_principal.insets = new Insets(20, 20, 20, 20);
        gbc_principal.fill = GridBagConstraints.BOTH;
        gbc_principal.weightx = 1.0;
        gbc_principal.weighty = 1.0;
        panel_fondo.add(cuadro_blanco, gbc_principal);

        //----------------- FUENTES -----------------
        Font fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 35f);
        Font fuente_rabbits = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 18f);
        Font fuente_tabla = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 14f);
        Font fuente_botones = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 16f);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0);

        // 1. TÍTULO (FILA 0)
        JLabel lbl_titulo = new JLabel("Modificar Comanda", SwingConstants.CENTER);
        lbl_titulo.setFont(fuente_titulo);
        gbc.gridy = 0;
        cuadro_blanco.add(lbl_titulo, gbc);

        // 2. TABLA PRODUCTOS ACTUALES (FILA 1 Y 2)
        gbc.gridy = 1;
        JLabel lbl_actuales = new JLabel("Productos en la Comanda:");
        lbl_actuales.setFont(fuente_rabbits);
        cuadro_blanco.add(lbl_actuales, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        String[] col_actuales = {"Producto", "Cantidad", "Eliminar"};
        modelo_actuales = new DefaultTableModel(col_actuales, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return c > 0; }
            @Override
            public Class<?> getColumnClass(int c) { return (c == 2) ? Boolean.class : Object.class; }
        };
        tabla_actuales = new TablaEstilizada(modelo_actuales, fuente_tabla);
        // Aplicar el Checkbox centrado y correcto
        tabla_actuales.getColumnModel().getColumn(2).setCellRenderer(tabla_actuales.getDefaultRenderer(Boolean.class));
        tabla_actuales.getColumnModel().getColumn(2).setCellEditor(tabla_actuales.getDefaultEditor(Boolean.class));
        
        JScrollPane scroll_actuales = new JScrollPane(tabla_actuales);
        scroll_actuales.getViewport().setBackground(Color.WHITE);
        cuadro_blanco.add(scroll_actuales, gbc);

        // 3. BUSCADOR / FILTRO (FILA 3 Y 4)
        gbc.gridy = 3;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel lbl_filtro = new JLabel("Agregar más productos (Filtrar por categoría):");
        lbl_filtro.setFont(fuente_rabbits);
        cuadro_blanco.add(lbl_filtro, gbc);

        gbc.gridy = 4;
        String[] categorias = {"Todos", "Bebidas", "Postres", "Platillos", "Entradas"};
        ComboBoxPersonalizado<String> cb_filtro = new ComboBoxPersonalizado<>(categorias);
        cb_filtro.setPreferredSize(new Dimension(0, 35));
        cuadro_blanco.add(cb_filtro, gbc);

        // 4. TABLA CATÁLOGO (FILA 5)
        gbc.gridy = 5;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        String[] col_catalogo = {"Producto", "Precio", "Cantidad", "Agregar"};
        modelo_catalogo = new DefaultTableModel(col_catalogo, 0) {
            @Override
            public boolean isCellEditable(int r, int c) { return c >= 2; }
            @Override
            public Class<?> getColumnClass(int c) { return (c == 3) ? Boolean.class : Object.class; }
        };
        tabla_catalogo = new TablaEstilizada(modelo_catalogo, fuente_tabla);
        // Aplicar Checkbox
        tabla_catalogo.getColumnModel().getColumn(3).setCellRenderer(tabla_catalogo.getDefaultRenderer(Boolean.class));
        tabla_catalogo.getColumnModel().getColumn(3).setCellEditor(tabla_catalogo.getDefaultEditor(Boolean.class));
        
        JScrollPane scroll_catalogo = new JScrollPane(tabla_catalogo);
        scroll_catalogo.getViewport().setBackground(Color.WHITE);
        cuadro_blanco.add(scroll_catalogo, gbc);

        // 5. NOTAS (FILA 6)
        gbc.gridy = 6;
        gbc.weighty = 0.15;
        JPanel panel_notas = new JPanel(new BorderLayout());
        panel_notas.setOpaque(false);
        JLabel lbl_notas = new JLabel("Notas de la comanda:");
        lbl_notas.setFont(fuente_rabbits);
        txt_notas = new JTextArea();
        txt_notas.setLineWrap(true);
        txt_notas.setWrapStyleWord(true);
        JScrollPane scroll_notas = new JScrollPane(txt_notas);
        panel_notas.add(lbl_notas, BorderLayout.NORTH);
        panel_notas.add(scroll_notas, BorderLayout.CENTER);
        cuadro_blanco.add(panel_notas, gbc);

        // 6. BOTONES (FILA 7)
        gbc.gridy = 7;
        gbc.weighty = 0;
        gbc.insets = new Insets(15, 0, 0, 0);
        JPanel panel_botones = new JPanel(new BorderLayout());
        panel_botones.setOpaque(false);

        BotonMenuAdministrador btn_volver = new BotonMenuAdministrador("Volver", "/imagenes/volverPNG.png", rojo, 25, 25, fuente_botones);
        btn_volver.setPreferredSize(new Dimension(200, 50));
        btn_volver.addActionListener(e -> dispose());

        BotonMenuAdministrador btn_aplicar = new BotonMenuAdministrador("Aplicar Cambios", "/imagenes/aceptarPNG.png", verde, 25, 25, fuente_botones);
        btn_aplicar.setPreferredSize(new Dimension(220, 50));
        // Aquí iría la lógica para recolectar datos de ambas tablas y enviarlos al coordinador

        panel_botones.add(btn_volver, BorderLayout.WEST);
        panel_botones.add(btn_aplicar, BorderLayout.EAST);
        cuadro_blanco.add(panel_botones, gbc);

        // --- Datos Fake para Pruebas ---
        modelo_actuales.addRow(new Object[]{"Hamburguesa Doble", 1, false});
        modelo_actuales.addRow(new Object[]{"Coca Cola 600ml", 2, false});
        
        modelo_catalogo.addRow(new Object[]{"Papas Fritas", 45.00, 1, false});
        modelo_catalogo.addRow(new Object[]{"Nuggets", 60.00, 1, false});
    }
}