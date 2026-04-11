/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import controladorRestaurante.Coordinador;
import enumEntidades.EstadoComanda;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import observadorRestaurante.Observador;
import recursos.*;

public class VentanaMenuComanda extends JFrame {

    private final Coordinador coordinador;
    private Observador metiche;

    //Enchufe  o puerta para que nuestro observador pueda funcionar, es un setter de Intefaz
    // es como decir que la ventana es el radio y el observador la frecuencia
    //la ventana puede estar activa, pero si no esta vinculada con el observador nadie
    //escucha lo que grita. El this.metiche es la variable que guarda ese alguien 
    public void setConexionObservador(Observador metiche) {
        this.metiche = metiche;
    }

    private final Color naranja = new Color(255, 184, 77);
    private final Color verde = new Color(116, 155, 87);
    private final Color rojo = new Color(188, 55, 30);
    private DefaultTableModel modelo_tabla;
    private TablaEstilizada tabla;

    private TextFieldPersonalizado txt_nombre;
    private TextFieldPersonalizado txt_mesa;

    public VentanaMenuComanda(Coordinador coordinador) {
        this.coordinador = coordinador;
        setTitle("Gestión de Comandas - Sistema Restaurante");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 850);
        setLocationRelativeTo(null);
        initComponents();
    }

    public void initComponents() {

        //PANEL FONDO-----------------------------------------------------------
        PanelFondo panel_fondo = new PanelFondo();
        panel_fondo.setLayout(new GridBagLayout());
        setContentPane(panel_fondo);
        //PANEL BLANCO----------------------------------------------------------
        PanelRedondeado cuadro_blanco = new PanelRedondeado(50, Color.WHITE);
        cuadro_blanco.setLayout(new GridBagLayout());
        cuadro_blanco.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        cuadro_blanco.setPreferredSize(new Dimension(850, 780));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0);

        //FUENTES---------------------------------------------------------------
        Font fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 32f);
        Font fuente_botones = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 16f);
        Font fuente_rabbits_mediana = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 18f);
        Font fuente_rabbits_pequena = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 14f);

        //TITULO----------------------------------------------------------------
        JLabel lbl_titulo = new JLabel("Menú de Comandas", SwingConstants.CENTER);
        lbl_titulo.setFont(fuente_titulo);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        cuadro_blanco.add(lbl_titulo, gbc);

        //BUSCAR CLIENTE--------------------------------------------------------
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 0, 0);
        JLabel lbl_buscar = new JLabel("Buscar por Cliente");
        lbl_buscar.setFont(fuente_rabbits_mediana);
        cuadro_blanco.add(lbl_buscar, gbc);

        gbc.gridy = 2;
        txt_nombre = new TextFieldPersonalizado(20);
        txt_nombre.setPreferredSize(new Dimension(0, 35));
        cuadro_blanco.add(txt_nombre, gbc);

        //ESTADO BÚSQUEDA-------------------------------------------------------
        gbc.gridy = 3;
        JLabel lbl_estado = new JLabel("Estado de Comanda");
        lbl_estado.setFont(fuente_rabbits_mediana);
        cuadro_blanco.add(lbl_estado, gbc);

        gbc.gridy = 4;
        ComboBoxPersonalizado<EstadoComanda> cmb_busqueda = new ComboBoxPersonalizado<>(EstadoComanda.values());
        cmb_busqueda.setPreferredSize(new Dimension(0, 35));
        cmb_busqueda.setFont(fuente_rabbits_pequena);
        cmb_busqueda.setForeground(naranja);
        cuadro_blanco.add(cmb_busqueda, gbc);

        //MESA------------------------------------------------------------------
        gbc.gridy = 5;
        JLabel lbl_mesa = new JLabel("Número de Mesa");
        lbl_mesa.setFont(fuente_rabbits_mediana);
        cuadro_blanco.add(lbl_mesa, gbc);

        gbc.gridy = 6;
        txt_mesa = new TextFieldPersonalizado(20);
        txt_mesa.setPreferredSize(new Dimension(0, 35));
        cuadro_blanco.add(txt_mesa, gbc);

        //TABLA-----------------------------------------------------------------
        gbc.gridy = 7;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 0, 10, 0);

        String[] columnas = {"Mesa", "Cliente", "Estado", "Modificar", "Folio"};
        modelo_tabla = new ModeloTablaEditable(columnas, 0, 1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Columna 2 (Combo) y Columna 3 (Botón) deben ser editables
                return column == 2 || column == 3;
            }
        };

        tabla = new TablaEstilizada(modelo_tabla, fuente_rabbits_pequena);
        configurarRenderizadoUnidad();

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(naranja, 2));
        cuadro_blanco.add(scroll, gbc);

        //BOTONES INFERIORES----------------------------------------------------
        GridBagConstraints gbc_btns = new GridBagConstraints();
        gbc_btns.gridx = 0;
        gbc_btns.gridy = 8;
        gbc_btns.weightx = 1.0;
        gbc_btns.fill = GridBagConstraints.NONE;
        gbc_btns.insets = new Insets(15, 0, 15, 0);

        JPanel panel_botones = new JPanel(new GridLayout(1, 2, 30, 0));
        panel_botones.setOpaque(false);

        BotonMenuAdministrador btn_volver = new BotonMenuAdministrador("Volver", "/imagenes/volverPNG.png", rojo, 30, 30, fuente_botones);
        btn_volver.setPreferredSize(new Dimension(220, 55));
        BotonMenuAdministrador btn_agregar = new BotonMenuAdministrador("Agregar", "/imagenes/aceptarPNG.png", verde, 30, 30, fuente_botones);
        btn_agregar.setPreferredSize(new Dimension(220, 55));

        panel_botones.add(btn_volver);
        panel_botones.add(btn_agregar);
        cuadro_blanco.add(panel_botones, gbc_btns);

        GridBagConstraints gbc_fondo = new GridBagConstraints();
        gbc_fondo.fill = GridBagConstraints.BOTH;
        gbc_fondo.weightx = 1.0;
        gbc_fondo.weighty = 1.0;
        gbc_fondo.insets = new Insets(20, 40, 20, 40);
        panel_fondo.add(cuadro_blanco, gbc_fondo);

        //DATOS FAKE============================================================
        modelo_tabla.addRow(new Object[]{"1", "Juan Pérez", EstadoComanda.ABIERTA, "MODIFICAR", "CMD-001"});
        modelo_tabla.addRow(new Object[]{"5", "Maria Garcia", EstadoComanda.CANCELADA, "MODIFICAR", "CMD-002"});
//================ACTION LISTENER DE LOS BOTONES AGREGAR Y VOLVER===============
        btn_agregar.addActionListener(e -> coordinador.mostrarCrearComanda());
        btn_volver.addActionListener(e -> coordinador.volverComandaMesero());
    }
//=====================================OTRAS CONFIGURACIONES====================

    private void configurarRenderizadoUnidad() {
        // 1. CONFIGURAR COMBO EN TABLA
        JComboBox<EstadoComanda> cmb_tabla = new JComboBox<>(EstadoComanda.values());
        TableColumn colEstado = tabla.getColumnModel().getColumn(2);
        colEstado.setCellEditor(new DefaultCellEditor(cmb_tabla));

        // 2. CONFIGURAR BOTÓN EN TABLA
        TableColumn columna_boton = tabla.getColumnModel().getColumn(3);

        // Botones separados para evitar conflictos visuales
        BotonMenuAdministrador btn_render = new BotonMenuAdministrador("Modificar", null, naranja, 20, 20, tabla.getFont());
        BotonMenuAdministrador btn_editor = new BotonMenuAdministrador("Modificar", null, naranja, 20, 20, tabla.getFont());

        // RENDERER: Controla cómo se ve, este no tiene logica, lo tuve que separar porque de otra manera no funcionaba
        columna_boton.setCellRenderer(new BotonTablaRender(btn_render) {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
                Object estado = table.getValueAt(row, 2);
                boolean esAbierta = estado != null && estado.toString().equalsIgnoreCase("ABIERTA");

                btn_render.setEnabled(esAbierta);
                return btn_render;
            }
        });

        // EDITOR: Controla lo que pasa cuando se hace clic, aqui tendria que ir la logica del coordinador
        columna_boton.setCellEditor(new BotonTablaEditor(btn_editor, e -> {
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                //VALDACION DEL ESTADO
                Object estado = tabla.getValueAt(fila, 2);
                if (estado != null && estado.toString().equalsIgnoreCase("ABIERTA")) {

                    String folio = tabla.getValueAt(fila, 4).toString();
                    System.out.println("Ejecutando modificación para: " + folio);
                    JOptionPane.showMessageDialog(null, "Modificando comanda: " + folio);

                } else {
                    // Si no es abierta, detenemos la edición y no hacemos nada
                    System.out.println("Acción bloqueada: La comanda no está ABIERTA");
                }
            }

            // Forzamos a la tabla a dejar de editar para que el botón no se quede pegado
            if (tabla.isEditing()) {
                tabla.getCellEditor().stopCellEditing();
            }
        }));
    }
}
