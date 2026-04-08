/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import controladorRestaurante.Coordinador;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import recursos.*;

/**
 *
 * @author josma
 */
public class VentanaDialogAgregarIngrediente extends JDialog {

    private final Coordinador coordinador;

    private TablaEstilizada tabla;
    private DefaultTableModel modelo_tabla;
    private final Color naranja = new Color(255, 184, 77);
    private final Color verde = new Color(116, 155, 87);
    private final Color rojo = new Color(188, 55, 30);

    public VentanaDialogAgregarIngrediente(Coordinador coordinador, Frame padre) {
        super(padre, true); //Activamos el modal para que no podamos editar el frame anterior
        this.coordinador = coordinador;
        setSize(900, 700);
        setLocationRelativeTo(padre);
        initComponents();
    }

    private void initComponents() {
        //-------------SECCIÓN DEL FONDO----------------------------------------

        PanelFondo panel_fondo = new PanelFondo();
        panel_fondo.setLayout(new GridBagLayout());
        setContentPane(panel_fondo);

        //----------------------PANEL BLANCO------------------------------------
        PanelRedondeado cuadro_blanco = new PanelRedondeado(50, Color.WHITE);
        cuadro_blanco.setLayout(new GridBagLayout());
        cuadro_blanco.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        cuadro_blanco.setPreferredSize(new Dimension(850, 500));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0); // Espaciado general pequeño entre filas
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // -----------------------------------------FUENTES---------------------
        Font fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 32f);
        Font fuente_rabbits_mediana = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 18f);
        Font fuente_rabbits_pequena = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 14f);
        Font fuente_botones = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 18f);

        // ---------------------------TITULO (Fila 0)---------------------------
        JLabel lbl_titulo = new JLabel("Agregar Ingrediente a producto", SwingConstants.CENTER);
        lbl_titulo.setFont(fuente_titulo);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        cuadro_blanco.add(lbl_titulo, gbc);

        // ---------------LABEL BUSCAR (Fila 1)---------------------------------
        JLabel lbl_buscar = new JLabel("Buscar Producto");
        lbl_buscar.setFont(fuente_rabbits_mediana);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        cuadro_blanco.add(lbl_buscar, gbc);

        // ---------------------TXT BUSCADOR (Fila 2)---------------------------
        TextFieldPersonalizado txt_buscador = new TextFieldPersonalizado(20);
        txt_buscador.setPreferredSize(new Dimension(0, 40));
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 20, 0); // Espacio entre buscador y tabla
        cuadro_blanco.add(txt_buscador, gbc);

        // ----------------------------------TABLA (Fila 3)---------------------
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        String[] columnas = {"Nombre", "Cantidad", "Unidad", "Seleccionado"};
        modelo_tabla = new ModeloTablaEditable(columnas, 0,3) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Bloqueamos la columna 0 PARA QUE NO SE EDITE
                if (column == 0) {
                    return false;
                }
                return super.isCellEditable(row, column);
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 3) {
                    return Boolean.class;
                }
                return String.class;
            }
        };
        tabla = new TablaEstilizada(modelo_tabla, fuente_rabbits_pequena);

        //Le quitamos un poco de poder a nuestra clase personalizada para poder dibujar
        //el checkbox
        tabla.getColumnModel().getColumn(3).setCellRenderer(tabla.getDefaultRenderer(Boolean.class));
        tabla.getColumnModel().getColumn(3).setCellEditor(tabla.getDefaultEditor(Boolean.class));

        configurarRenderizadoUnidad();

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(naranja, 2));
        cuadro_blanco.add(scroll, gbc);

        // --------------------------PANEL DE BOTONES (Fila 4)------------------
        gbc.gridy = 4;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 0, 0); // Margen superior para separarlo de la tabla

        // Usamos un JPanel con BorderLayout para poner uno a la izq y otro a la der
        JPanel panel_botones = new JPanel(new BorderLayout());
        panel_botones.setOpaque(false);

        BotonMenuAdministrador btn_volver = new BotonMenuAdministrador("Volver", "/imagenes/volverPNG.png", rojo, 25, 25, fuente_botones);
        BotonMenuAdministrador btn_agregar = new BotonMenuAdministrador("Agregar", "/imagenes/aceptarPNG.png", verde, 25, 25, fuente_botones);

        btn_volver.setPreferredSize(new Dimension(180, 50));
        btn_agregar.setPreferredSize(new Dimension(180, 50));

        panel_botones.add(btn_volver, BorderLayout.WEST);  // btn Volver a la izquierda
        panel_botones.add(btn_agregar, BorderLayout.EAST); // btn Agregar a la derecha

        cuadro_blanco.add(panel_botones, gbc);

        // -------------------------ULTIMA AGRECACION---------------------------
        gbc.gridy = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.CENTER;
        panel_fondo.add(cuadro_blanco, gbc);

        //------------------------EVENTOS---------------------------------------
        btn_volver.addActionListener(e -> dispose());

        btn_agregar.addActionListener(e -> {//<-- LLAMAR AL COORDINADOR

            System.out.println("Guardando ingredientes...");
            dispose();
        });

        // Datos de prueba (Fake Data)
        modelo_tabla.addRow(new Object[]{"Ajo", "40", "Lista de unidades", false});
        modelo_tabla.addRow(new Object[]{"Cebolla", "", "", false});
        modelo_tabla.addRow(new Object[]{"Pimienta", "", "", false});
    }

    private void configurarRenderizadoUnidad() {
        // Aqui SE DEBE CONECTAR LA CLASE ENUM al ComboBox de la tabla
        // ComboBox<EnumMedida> combo = new JComboBox<>(EnumMedida.values());
        String[] unidadesFake = {"Gramos", "Kilogramos", "Litros", "Piezas"};
        JComboBox<String> comboUnidad = new JComboBox<>(unidadesFake);

        TableColumn colUnidad = tabla.getColumnModel().getColumn(2);
        colUnidad.setCellEditor(new DefaultCellEditor(comboUnidad));
    }
}
