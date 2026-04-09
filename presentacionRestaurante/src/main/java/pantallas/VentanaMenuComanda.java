/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import controladorRestaurante.Coordinador;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.DefaultCellEditor;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import observadorRestaurante.Observador;
import recursos.BotonMenuAdministrador;
import recursos.ComboBoxPersonalizado;
import recursos.GestorFuentes;
import recursos.ModeloTablaEditable;
import recursos.PanelFondo;
import recursos.PanelRedondeado;
import recursos.TablaEstilizada;
import recursos.TextFieldPersonalizado;

/**
 *
 * @author josma
 */
public class VentanaMenuComanda extends JFrame {

    private final Coordinador coordinador;
    //Observador 
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

        //CONFIGURACION BASE----------------------------------------------------
        setTitle("Gestión de Comandas - Sistema Restaurante");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 850);
        setLocationRelativeTo(null);
        initComponents();
    }

    public void initComponents() {
        //-------------SECCIÓN DEL FONDO----------------------------------------
        PanelFondo panel_fondo = new PanelFondo();
        panel_fondo.setLayout(new GridBagLayout());
        setContentPane(panel_fondo);

        //-------------SECCIÓN DEL PANEL BLANCO---------------------------------
        PanelRedondeado cuadro_blanco = new PanelRedondeado(50, Color.WHITE);
        cuadro_blanco.setLayout(new GridBagLayout());
        cuadro_blanco.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        cuadro_blanco.setPreferredSize(new Dimension(850, 780));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0); // Espacio entre elementos

        //----------------------------FUENTES-----------------------------------
        Font fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 32f);
        Font fuente_botones = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 16f);
        Font fuente_rabbits_mediana = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 18f);
        Font fuente_rabbits_pequena = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 14f);

        //TITULO
        JLabel lbl_titulo = new JLabel("Menú de Comandas", SwingConstants.CENTER);
        lbl_titulo.setFont(fuente_titulo);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0); // Espacio extra bajo el título
        cuadro_blanco.add(lbl_titulo, gbc);

        //BUSCAR CLIENTE (Label)
        gbc.gridy = 1;
        gbc.insets = new Insets(5, 0, 0, 0);
        JLabel lbl_buscar = new JLabel("Buscar por Cliente");
        lbl_buscar.setFont(fuente_rabbits_mediana);
        cuadro_blanco.add(lbl_buscar, gbc);

        //BUSCAR CLIENTE (TextField)
        gbc.gridy = 2;
        txt_nombre = new TextFieldPersonalizado(20);
        txt_nombre.setPreferredSize(new Dimension(0, 35));
        cuadro_blanco.add(txt_nombre, gbc);

        //ESTADO (Label)
        gbc.gridy = 3;
        JLabel lbl_estado = new JLabel("Estado de Comanda");
        lbl_estado.setFont(fuente_rabbits_mediana);
        cuadro_blanco.add(lbl_estado, gbc);

        //ESTADO (ComboBox)
        gbc.gridy = 4;
        String[] opcionesTipo = {"Seleccionar", "Abierta", "Preparación", "Cancelada", "Terminada"};
        ComboBoxPersonalizado<String> combo_tipo = new ComboBoxPersonalizado<>(opcionesTipo);
        combo_tipo.setPreferredSize(new Dimension(0, 35));
        cuadro_blanco.add(combo_tipo, gbc);

        //NÚMERO DE MESA (Label)
        gbc.gridy = 5;
        JLabel lbl_mesa = new JLabel("Número de Mesa");
        lbl_mesa.setFont(fuente_rabbits_mediana);
        cuadro_blanco.add(lbl_mesa, gbc);

        //NÚMERO DE MESA (TextField)
        gbc.gridy = 6;
        txt_mesa = new TextFieldPersonalizado(20);
        txt_mesa.setPreferredSize(new Dimension(0, 35));
        cuadro_blanco.add(txt_mesa, gbc);

        //TABLA
        gbc.gridy = 7;
        gbc.weighty = 1.0; // ESTO hace que la tabla ocupe el espacio sobrante
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(20, 0, 10, 0); // Margen arriba y abajo de la tabla

        String[] columnas = {"Nombre Cliente", "Estado", "Numero Comanda"};
        modelo_tabla = new ModeloTablaEditable(columnas, 0, 1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return column == 1; // Solo el Estado es editable
            }
        };

        tabla = new TablaEstilizada(modelo_tabla, fuente_rabbits_pequena);
        configurarRenderizadoUnidad();

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(naranja, 2));
        cuadro_blanco.add(scroll, gbc);

        //BOTÓN AGREGAR (Al final)
        gbc.gridy = 8;
        gbc.weighty = 0; // El botón no debe crecer
        gbc.fill = GridBagConstraints.NONE; // Para que no ocupe todo el ancho
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.insets = new Insets(10, 0, 10, 0);

        BotonMenuAdministrador btn_agregar = new BotonMenuAdministrador("Agregar Comanda", "/imagenes/registrarPNG.png", verde, 30, 30, fuente_botones);
        btn_agregar.setPreferredSize(new Dimension(250, 60));
        cuadro_blanco.add(btn_agregar, gbc);

        // AGREGAR CUADRO BLANCO AL FONDO
        GridBagConstraints gbc_fondo = new GridBagConstraints();
        gbc_fondo.insets = new Insets(20, 40, 20, 40);
        gbc_fondo.fill = GridBagConstraints.BOTH;
        gbc_fondo.weightx = 1.0;
        gbc_fondo.weighty = 1.0;
        panel_fondo.add(cuadro_blanco, gbc_fondo);

        // --- DATOS FAKE ---
        modelo_tabla.addRow(new Object[]{"Juan Pérez", "Abierta", "CMD-001"});
        modelo_tabla.addRow(new Object[]{"Maria Garcia", "Preparación", "CMD-002"});
    }

    private void configurarRenderizadoUnidad() {
        // Aqui SE DEBE CONECTAR LA CLASE ENUM al ComboBox de la tabla
        // ComboBox<EnumMedida> combo = new JComboBox<>(EnumMedida.values());
        String[] estadosFake = {"Abierta", "Preparación", "Termianda", "Cancelada"};
        JComboBox<String> comboUnidad = new JComboBox<>(estadosFake);

        TableColumn colUnidad = tabla.getColumnModel().getColumn(1);
        colUnidad.setCellEditor(new DefaultCellEditor(comboUnidad));
    }
}
