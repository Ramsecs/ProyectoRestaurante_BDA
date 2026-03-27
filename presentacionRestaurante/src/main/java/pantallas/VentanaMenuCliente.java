/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import controladorRestaurante.Coordinador;
import java.awt.*;
import java.io.*;
import javax.swing.*;
import javax.swing.table.*;
import recursos.*;

/**
 *
 * @author josma
 */
public class VentanaMenuCliente extends JFrame {
    private final Coordinador coordinador;

    private final Color naranja = new Color(255, 184, 77);
    private final Color verde = new Color(116, 155, 87);
    private final Color rojo = new Color(188, 55, 30);
    private DefaultTableModel modelo_tabla;
    private TablaEstilizada tabla;

    public VentanaMenuCliente(Coordinador coordinador) {
        this.coordinador = coordinador;
        //CONFIGURACION BASE----------------------------------------------------
        setTitle("Gestión de Clientes - Sistema Restaurante");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 850);
        setLocationRelativeTo(null);
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
        JLabel lbl_titulo = new JLabel("Menú de Cliente", SwingConstants.CENTER);
        lbl_titulo.setFont(fuente_titulo);
        gbc.gridy = 0;
        gbc.weighty = 0.02;
        cuadro_blanco.add(lbl_titulo, gbc);

        //-------------------------BUSCADOR LABEL y TXTFIELD (FILA 1 Y 2)-------------------------------
        gbc.gridy = 1;
        gbc.weighty = 0;
        JLabel lbl_buscar = new JLabel("Buscar cliente");
        lbl_buscar.setFont(fuente_rabbits_mediana);
        cuadro_blanco.add(lbl_buscar, gbc);

        gbc.gridy = 2;
        TextFieldPersonalizado txt_buscador = new TextFieldPersonalizado(20);
        txt_buscador.setPreferredSize(new Dimension(0, 35)); // Altura fija
        cuadro_blanco.add(txt_buscador, gbc);

        //----------------------------TABLA (FILA 3)-------------------------------------
        gbc.gridy = 3;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        //1. Definimos las columnas que va a contener la tabla
        String[] columnas = {
            "Nombre", "Apellido Paterno", "Apellido Materno", "Correo",
            "Teléfono", "Visitas", "Puntos", "Total Acumulado"
        };

        //2. Ahora implementamos ModeloTablaEditable, para permitir que datos se van a editar
        modelo_tabla = new ModeloTablaEditable(columnas, 0, 4);

        tabla = new TablaEstilizada(modelo_tabla, fuente_rabbits_pequena);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(0, 150));
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        cuadro_blanco.add(scroll, gbc);

        //MODELO DE PRUEBA PARA LA TABLA========================================
        modelo_tabla.addRow(new Object[]{"Julia", "Rivera", "Salazar", "juliancitopro@", "6441025765", "30", "15000", "10235.67"});
        modelo_tabla.addRow(new Object[]{"Julia", "Rivera", "Salazar", "juliancitopro@", "6441025765", "30", "15000", "10235.67"});
        modelo_tabla.addRow(new Object[]{"Julia", "Rivera", "Salazar", "juliancitopro@", "6441025765", "30", "15000", "10235.67"});
        modelo_tabla.addRow(new Object[]{"Julia", "Rivera", "Salazar", "juliancitopro@", "6441025765", "30", "15000", "10235.67"});
        modelo_tabla.addRow(new Object[]{"Julia", "Rivera", "Salazar", "juliancitopro@", "6441025765", "30", "15000", "10235.67"});
        modelo_tabla.addRow(new Object[]{"Julia", "Rivera", "Salazar", "juliancitopro@", "6441025765", "30", "15000", "10235.67"});
        modelo_tabla.addRow(new Object[]{"Julia", "Rivera", "Salazar", "juliancitopro@", "6441025765", "30", "15000", "10235.67"});
        modelo_tabla.addRow(new Object[]{"Julia", "Rivera", "Salazar", "juliancitopro@", "6441025765", "30", "15000", "10235.67"});
        //======================================================================

        //----------------SECCIÓN PARA AGREGAR AL CLIENTE (panel)---------------
        PanelBordePunteado panel_agregar = new PanelBordePunteado();
        panel_agregar.setLayout(new GridLayout(0, 2, 10, 2));

        JLabel lbl_agregar = new JLabel("Registrar Cliente");
        lbl_agregar.setFont(fuente_rabbits_mediana);
        panel_agregar.add(lbl_agregar);
        panel_agregar.add(new JLabel(""));//ESTO ES UN ESPACIO VACIO

        //Agregamos los campos (etiqueta- campo) para que  esten un poco más compactos
        JLabel lbl_nombre = new JLabel("Nombre:");
        lbl_nombre.setFont(fuente_rabbits_pequena);
        panel_agregar.add(lbl_nombre);
        TextFieldPersonalizado txt_nombre = new TextFieldPersonalizado(10);
        panel_agregar.add(txt_nombre);

        JLabel lbl_apelldio_paterno = new JLabel("Apellido Paterno:");
        lbl_apelldio_paterno.setFont(fuente_rabbits_pequena);
        panel_agregar.add(lbl_apelldio_paterno);
        TextFieldPersonalizado txt_apellido_patenro = new TextFieldPersonalizado(10);
        panel_agregar.add(txt_apellido_patenro);

        JLabel lbl_apellido_materno = new JLabel("Apellido Materno:");
        lbl_apellido_materno.setFont(fuente_rabbits_pequena);
        panel_agregar.add(lbl_apellido_materno);
        TextFieldPersonalizado txt_apellido_materno = new TextFieldPersonalizado(10);
        panel_agregar.add(txt_apellido_materno);

        JLabel lbl_correo = new JLabel("Correo (Opcional):");
        lbl_correo.setFont(fuente_rabbits_pequena);
        panel_agregar.add(lbl_correo);
        TextFieldPersonalizado txt_correo = new TextFieldPersonalizado(10);
        panel_agregar.add(txt_correo);

        JLabel lbl_telefono = new JLabel("Teléfono:");
        lbl_telefono.setFont(fuente_rabbits_pequena);
        panel_agregar.add(lbl_telefono);
        TextFieldPersonalizado txt_telefono = new TextFieldPersonalizado(10);
        panel_agregar.add(txt_telefono);

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
        
        btn_volver.addActionListener(a -> {
            coordinador.regresarMenuAdmin();
        });

    }
}
