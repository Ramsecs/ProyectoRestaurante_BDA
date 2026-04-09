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
import java.awt.GridLayout;
import java.awt.Insets;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableModel;
import observadorRestaurante.Observador;
import recursos.BotonMenuAdministrador;
import recursos.ComboBoxPersonalizado;
import recursos.GestorFuentes;
import recursos.ModeloTablaEditable;
import recursos.PanelBordePunteado;
import recursos.PanelFondo;
import recursos.PanelRedondeado;
import recursos.TablaEstilizada;
import recursos.TextFieldPersonalizado;

/**
 *
 * @author josma
 */
public class VentanaMenuIngrediente extends JFrame {

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
    private TextFieldPersonalizado txt_cantidad;
    private TextFieldPersonalizado txt_medida;
    private TextFieldPersonalizado txt_buscador;

    public VentanaMenuIngrediente(Coordinador coordinador) {
        this.coordinador = coordinador;

        //CONFIGURACION BASE----------------------------------------------------
        setTitle("Gestión de Ingredientes - Sistema Restaurante");
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
        // Quitamos el setPreferredSize rígido para que el Layout pueda trabajar mejor
        cuadro_blanco.setPreferredSize(new Dimension(850, 780));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL; // Que ocupen todo el ancho
        gbc.weightx = 1.0; // Factor de expansión horizontal
        gbc.insets = new Insets(2, 0, 2, 0); // Espaciado vertical entre filas

        //----------------------------FUENTES-----------------------------------
        Font fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 32f);
        Font fuente_botones = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 16f);
        Font fuente_rabbits_mediana = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 18f);
        Font fuente_rabbits_pequena = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 14f);

        //--------------------------TITULO LABEL (FILA 0)-------------------------------
        JLabel lbl_titulo = new JLabel("Menú de Ingredientes", SwingConstants.CENTER);
        lbl_titulo.setFont(fuente_titulo);
        gbc.gridy = 0;
        gbc.weighty = 0.02;
        cuadro_blanco.add(lbl_titulo, gbc);

        //-------------------------BUSCADOR LABEL y TXTFIELD (FILA 1 Y 2)-------------------------------
        gbc.gridy = 1;
        gbc.weighty = 0;
        JLabel lbl_buscar = new JLabel("Buscar Ingrediente");
        lbl_buscar.setFont(fuente_rabbits_mediana);
        cuadro_blanco.add(lbl_buscar, gbc);

        gbc.gridy = 2;
        txt_buscador = new TextFieldPersonalizado(20);
        txt_buscador.setPreferredSize(new Dimension(0, 35)); // Altura fija
        cuadro_blanco.add(txt_buscador, gbc);

        //----------------------------TABLA (FILA 3)-------------------------------------
        gbc.gridy = 3;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        //1. Definimos las columnas que va a contener la tabla
        String[] columnas = {
            "Nombre", "Cantidad", "Unidad de Medida"
        };
        //----> OJO AQUI TIENES QUE CHECAR QUE SOLO SEA EDITABLE LA CANTIDAD PARA EL REESTOCK
        //EL EL CODIGO TIENE ALGUNAS COSITAS QUE YA NO ESTAS A TIEMPO DE CORREGIR JOS
        //TRABAJA CON LO QUE TIENES Y BUSCA LA MANERA DE RESOLVERLO
        //2. Ahora implementamos ModeloTablaEditable, para permitir que datos se van a editar
        modelo_tabla = new ModeloTablaEditable(columnas, 0, 1) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Bloqueamos la columna 0 (Nombre) 
                if (column == 0) {
                    return false;
                }
                // Bloqueamos la columna 2 (Unidad de Medida) 
                if (column == 2) {
                    return false;
                }
                // Solo permitimos la columna 1 (Cantidad)
                return column == 1;
            }
        };

        tabla = new TablaEstilizada(modelo_tabla, fuente_rabbits_pequena);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(0, 150));
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        cuadro_blanco.add(scroll, gbc);

        //======================================================================
        //DATOS FAKE
        modelo_tabla.addRow(new Object[]{"Harina de Trigo", "25.5", "Kilogramos"});
        modelo_tabla.addRow(new Object[]{"Tomate Saladette", "12.0", "Kilogramos"});
        modelo_tabla.addRow(new Object[]{"Aceite de Oliva", "5.0", "Litros"});
        modelo_tabla.addRow(new Object[]{"Sal de Mar", "2.0", "Kilogramos"});
        modelo_tabla.addRow(new Object[]{"Levadura Seca", "500", "Gramos"});
        modelo_tabla.addRow(new Object[]{"Pechuga de Pollo", "15.0", "Kilogramos"});
        modelo_tabla.addRow(new Object[]{"Leche Entera", "10.0", "Litros"});
        modelo_tabla.addRow(new Object[]{"Huevo Blanco", "180", "Piezas"});
        modelo_tabla.addRow(new Object[]{"Azúcar Estándar", "8.0", "Kilogramos"});
        modelo_tabla.addRow(new Object[]{"Pimiento Morrón", "4.5", "Kilogramos"});

        //======================================================================
        //----------------SECCIÓN PARA AGREGAR EL INGREDIENTE (panel)---------------
        PanelBordePunteado panel_agregar = new PanelBordePunteado();
        panel_agregar.setLayout(new GridLayout(0, 2, 10, 2));

        JLabel lbl_agregar = new JLabel("Agregar Ingrediente");
        lbl_agregar.setFont(fuente_rabbits_mediana);
        panel_agregar.add(lbl_agregar);
        panel_agregar.add(new JLabel(""));//ESTO ES UN ESPACIO VACIO

        //Agregamos los campos (etiqueta- campo) para que  esten un poco más compactos
        JLabel lbl_nombre = new JLabel("Nombre:");
        lbl_nombre.setFont(fuente_rabbits_pequena);
        panel_agregar.add(lbl_nombre);
        txt_nombre = new TextFieldPersonalizado(10);
        panel_agregar.add(txt_nombre);

        JLabel lbl_cantidad = new JLabel("Cantidad:");
        lbl_cantidad.setFont(fuente_rabbits_pequena);
        panel_agregar.add(lbl_cantidad);
        txt_cantidad = new TextFieldPersonalizado(10);
        panel_agregar.add(txt_cantidad);

        JLabel lbl_unidad = new JLabel("Unidad de Medida");
        lbl_unidad.setFont(fuente_rabbits_pequena);
        panel_agregar.add(lbl_unidad);
        String[] opcionesTipo = {"Seleccionar", "Galones", "Litros", "Gramos", "Kilogramos", "Mililitros"};
        ComboBoxPersonalizado<String> combo_tipo = new ComboBoxPersonalizado<>(opcionesTipo);
        combo_tipo.setPreferredSize(new Dimension(0, 35)); // Misma altura que tus textfields
        panel_agregar.add(combo_tipo);

        gbc.gridy = 4;
        gbc.weighty = 0.2;
        cuadro_blanco.add(panel_agregar, gbc);

        //-----------------SECCIÓN PARA LOS BOTONES-----------------------------
        gbc.gridy = 5;
        gbc.weighty = 0.1;
        JPanel panel_boton = new JPanel(new BorderLayout());
        panel_boton.setOpaque(false);

        BotonMenuAdministrador btn_volver = new BotonMenuAdministrador("Volver", "/imagenes/volverPNG.png", rojo, 30, 30, fuente_botones);
        BotonMenuAdministrador btn_agregar = new BotonMenuAdministrador("Agregar", "/imagenes/registrarPNG.png", verde, 30, 30, fuente_botones);

        panel_boton.add(btn_volver, BorderLayout.WEST);
        panel_boton.add(btn_agregar, BorderLayout.EAST);
        btn_volver.setPreferredSize(new Dimension(200, 60)); // 200 de ancho, 60 de alto
        btn_agregar.setPreferredSize(new Dimension(200, 60));

        cuadro_blanco.add(panel_boton, gbc);

        // CONFIGURACIÓN PARA EL CUADRO BLANCO DENTRO DEL FONDO
        GridBagConstraints gbc_fondo = new GridBagConstraints();
        gbc_fondo.gridx = 0;
        gbc_fondo.gridy = 0;
        gbc_fondo.weightx = 1.0; // Ocupa el ancho disponible
        gbc_fondo.weighty = 1.0; // Ocupa el alto disponible
        gbc_fondo.fill = GridBagConstraints.BOTH; // Se estira en ambas direcciones

        // Insets para que el cuadro blanco no toque los bordes de la ventana
        gbc_fondo.insets = new Insets(40, 60, 40, 60);

        panel_fondo.add(cuadro_blanco, gbc_fondo);
    }
}
