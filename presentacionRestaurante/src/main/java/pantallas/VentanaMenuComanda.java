/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import controladorRestaurante.Coordinador;
import dtosDelRestaurante.ClienteBusquedaDTO;
import dtosDelRestaurante.ComandaListaDTO;
import entidadesEnumeradorDTO.EstadoComandaDTO;
import enumEntidades.EstadoComanda;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import observadorRestaurante.Observador;
import recursos.*;
import java.util.List;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

/**
 * Ventana que tiene las vista de las comandas ABIERTAS (son las que importan) y
 * la parte para modificar (solo el estado y los productos)
 *
 * @author josma
 */
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

    private JComboBox<EstadoComandaDTO> cmb_tabla;

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

        
                //TABLA-----------------------------------------------------------------
        gbc.gridy = 7;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 0, 10, 0);

        String[] columnas = {"Mesa", "Cliente", "Estado", "Modificar", "Folio"};
        modelo_tabla = new ModeloTablaEditable(columnas, 0, 1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Columna 2 (Combo) y Columna 3 (Boton) deben ser editables
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

//================ACTION LISTENER DE LOS BOTONES AGREGAR Y VOLVER===============
        btn_agregar.addActionListener(e -> coordinador.mostrarCrearComanda());
        btn_volver.addActionListener(e -> coordinador.volverComandaMesero());

        cmb_tabla.addActionListener(e -> {
            if (!cmb_tabla.isShowing()) {
                return;
            }

            int fila = tabla.getSelectedRow();

            // 2. Validar que realmente haya una fila seleccionada 
            if (fila != -1 && fila < tabla.getRowCount()) {

                // Recuperamos el DTO
                ComandaListaDTO dto = (ComandaListaDTO) tabla.getValueAt(fila, 4);
                EstadoComandaDTO estado = (EstadoComandaDTO) cmb_tabla.getSelectedItem();

                // Si el estado es el mismo que ya tiene, no hacemos nada para evitar bucles
                if (dto.getEstado() == estado) {
                    return;
                }

                int resp = JOptionPane.showConfirmDialog(this, "¿Cambiar estado?");
                if (resp == JOptionPane.YES_OPTION) {
                    // 3. ANTES de llamar al coordinador, detenemos la edición
                    if (tabla.isEditing()) {
                        tabla.getCellEditor().stopCellEditing();
                    }
                    coordinador.actualizarEstadoComanda(dto.getId(), estado);
                } else {
                    // Si cancela, refrescamos para revertir el combo visualmente
                    coordinador.cargarComandasAbiertas();
                }
            }
        });

        txt_nombre.getDocument().addDocumentListener((new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                filtrar();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                filtrar();
            }

            private void filtrar() {
                String texto = txt_nombre.getText().trim();
                if (texto.isEmpty()) {
                    coordinador.cargarComandasAbiertas();
                } else {
                    coordinador.buscarClientesComanda(texto);
                }
            }

        }));

    }
//=====================================OTRAS CONFIGURACIONES====================

    private void configurarRenderizadoUnidad() {
        // 1. CONFIGURAR COMBO EN TABLA
        cmb_tabla = new JComboBox<>(EstadoComandaDTO.values());
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
                int filaModelo = tabla.convertRowIndexToModel(fila);
                ComandaListaDTO dto = (ComandaListaDTO) tabla.getModel().getValueAt(filaModelo, 4);

                // 2. Validamos el estado
                if (dto.getEstado().toString().equals("ABIERTA")) {
                    // Pasamos el DTO (los datos) y el Frame (this)
                    coordinador.prepararVentanaModificacion(dto, this);
                } else {
                    JOptionPane.showMessageDialog(this, "Solo se pueden modificar comandas ABIERTAS.");
                }
            }

            // Forzamos a la tabla a dejar de editar para que el botón no se quede pegado
            if (tabla.isEditing()) {
                tabla.getCellEditor().stopCellEditing();
            }
        }));
    }

    public void llenarTabla(List<ComandaListaDTO> lista) {
        modelo_tabla.setRowCount(0); //limpiamos la tabla

        for (ComandaListaDTO dto : lista) {
            //Formateamos el nombre completo para la tabla 
            String nombre_completo = dto.getNombre_cliente();
            if (dto.getApellido_cliente_paterno() != null || dto.getApellido_cliente_materno() != null) {
                nombre_completo += " " + dto.getApellido_cliente_paterno() + " " + dto.getApellido_cliente_materno();
            }

            Object[] fila = {
                dto.getId_mesa(),
                nombre_completo,
                dto.getEstado(),
                "MODIFICAR",
                dto

            };
            modelo_tabla.addRow(fila);
        }
    }

    public void actualizarTabla(List<ComandaListaDTO> lista) {
        modelo_tabla.setRowCount(0);

        for (ComandaListaDTO dto : lista) {
            String nombre_completo = dto.getNombre_cliente() + (dto.getApellido_cliente_paterno() != null ? " " + dto.getApellido_cliente_paterno() : "");

            Object[] fila = {
                dto.getId_mesa(),
                nombre_completo,
                dto.getEstado(),
                "MODIFICAR",
                dto // Guardamos el objeto completo en la columna del Folio como acordamos
            };
            modelo_tabla.addRow(fila);

        }
    }

}
