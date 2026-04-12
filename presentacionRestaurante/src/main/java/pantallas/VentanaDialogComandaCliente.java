/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import controladorRestaurante.Coordinador;
import dtosDelRestaurante.ClienteBusquedaDTO;
import dtosDelRestaurante.ClienteDTO;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.util.List;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import recursos.GestorFuentes;
import recursos.ModeloTablaEditable;
import recursos.PanelFondo;
import recursos.PanelRedondeado;
import recursos.*;
import recursos.TablaEstilizada;
import recursos.TextFieldPersonalizado;

/**
 *
 * @author josma
 */
public class VentanaDialogComandaCliente extends JDialog {

    private final Coordinador coordinador;

    private List<ClienteBusquedaDTO> listaClientesActual;
    private ClienteDTO clienteDTO;
    private final Color naranja = new Color(255, 184, 77);
    private final Color verde = new Color(116, 155, 87);
    private final Color rojo = new Color(188, 55, 30);
    private DefaultTableModel modelo_tabla;
    private TablaEstilizada tabla;

    private TextFieldPersonalizado txt_buscador;

    public VentanaDialogComandaCliente(Coordinador coordinador, JFrame padre) {
        super(padre, true);
        this.coordinador = coordinador;
        this.clienteDTO = new ClienteDTO();
        //CONFIGURACION BASE----------------------------------------------------
        setSize(900, 700);
        setLocationRelativeTo(padre);
        initComponents();
    }

    private void initComponents() {
        //-------------SECCIÓN DEL FONDO----------------------------------------
        PanelFondo panel_fondo = new PanelFondo();
        panel_fondo.setLayout(new GridBagLayout());
        setContentPane(panel_fondo);

        //-------------SECCIÓN DEL PANEL BLANCO---------------------------------
        PanelRedondeado cuadro_blanco = new PanelRedondeado(50, Color.WHITE);
        cuadro_blanco.setLayout(new GridBagLayout());
        cuadro_blanco.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        // Ajustamos el tamaño para que quepa bien en los 900x700 del JDialog
        cuadro_blanco.setPreferredSize(new Dimension(800, 600));

        GridBagConstraints gbc = new GridBagConstraints();

        // --- IMPORTANTE: AGREGAR EL CUADRO BLANCO AL FONDO ---
        GridBagConstraints gbc_cuadro = new GridBagConstraints();
        gbc_cuadro.gridx = 0;
        gbc_cuadro.gridy = 0;
        gbc_cuadro.fill = GridBagConstraints.BOTH;
        gbc_cuadro.insets = new Insets(20, 20, 20, 20); // Margen para ver el fondo
        gbc_cuadro.weightx = 1.0;
        gbc_cuadro.weighty = 1.0;
        panel_fondo.add(cuadro_blanco, gbc_cuadro); // <--- ESTO ES LO QUE FALTABA

        //----------------------------FUENTES-----------------------------------
        Font fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 32f);
        Font fuente_rabbits_mediana = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 18f);
        Font fuente_rabbits_pequena = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 14f);

        // Reutilizamos gbc para los componentes INTERNOS del cuadro blanco
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        //--------------------------TITULO (FILA 0)-------------------------------
        JLabel lbl_titulo = new JLabel("Seleccione un Cliente", SwingConstants.CENTER);
        lbl_titulo.setFont(fuente_titulo);
        gbc.gridy = 0;
        gbc.weighty = 0.0; // Los labels no necesitan expandirse verticalmente
        gbc.insets = new Insets(0, 0, 15, 0);
        cuadro_blanco.add(lbl_titulo, gbc);

        //-------------------------BUSCADOR (FILA 1 Y 2)-------------------------------
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 5, 0);
        JLabel lbl_buscar = new JLabel("Buscar cliente");
        lbl_buscar.setFont(fuente_rabbits_mediana);
        cuadro_blanco.add(lbl_buscar, gbc);

        gbc.gridy = 2;
        txt_buscador = new TextFieldPersonalizado(20);
        txt_buscador.setPreferredSize(new Dimension(0, 35));
        cuadro_blanco.add(txt_buscador, gbc);

        //----------------------------TABLA (FILA 3)-------------------------------------
        gbc.gridy = 3;
        gbc.weighty = 1.0; // La tabla sí debe absorber el espacio sobrante
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(15, 0, 10, 0);

        String[] columnas = {"Nombre", "Apellido Paterno", "Apellido Materno", "Correo", "Teléfono", "Seleccionar"};
        //APARTADO DEL RADIOBUTTON==============================================
        modelo_tabla = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo la columna 5 (Seleccionar) es editable
                return column == 5;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                // Decimos que la última columna es Boolean para que use componentes de selección
                return (columnIndex == 5) ? Boolean.class : Object.class;
            }
        };

        tabla = new TablaEstilizada(modelo_tabla, fuente_rabbits_pequena);

//CONFIGURACIÓN DEL RADIOBUTON==================================================
        tabla.setDefaultRenderer(Boolean.class, new RadioCellRenderer());
        tabla.setDefaultEditor(Boolean.class, new RadioCellEditor());

        // Asignación directa a la columna 5 para evitar que Swing use el default
        tabla.getColumnModel().getColumn(5).setCellRenderer(new RadioCellRenderer());
        tabla.getColumnModel().getColumn(5).setCellEditor(new RadioCellEditor());

        //Parte logica para que solo sea uno a la vez
        modelo_tabla.addTableModelListener(e -> {
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                int row = e.getFirstRow();
                int col = e.getColumn();

                // Si el usuario marcó un radio button como true
                if (col == 5 && (Boolean) modelo_tabla.getValueAt(row, col)) {
                    for (int i = 0; i < modelo_tabla.getRowCount(); i++) {
                        if (i != row) {
                            modelo_tabla.setValueAt(false, i, 5); // Desmarca los demás
                        }
                    }
                }
            }
        });
//==============================================================================

        // Agregamos un par de filas fake para probar visualmente
        modelo_tabla.addRow(new Object[]{"Juan", "Perez", "García", "juan@mail.com", "123456", true});
        modelo_tabla.addRow(new Object[]{"Juan", "Perez", "García", "juan@mail.com", "123456", false});
        modelo_tabla.addRow(new Object[]{"Juan", "Perez", "García", "juan@mail.com", "123456", false});
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(naranja, 2));

        cuadro_blanco.add(scroll, gbc);
    }
}
