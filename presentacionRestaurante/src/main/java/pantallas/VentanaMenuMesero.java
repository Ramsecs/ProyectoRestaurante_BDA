/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import controladorRestaurante.Coordinador;
import java.awt.*;
import java.io.InputStream;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import recursos.*;

/**
 *
 * @author josma
 */
public class VentanaMenuMesero extends JFrame {
    private final Coordinador coordinador;
    
    //Estos son los colores de nuestra palata de colores
    private final Color verde = new Color(116, 155, 87);
    private final Color rojo = new Color(188, 55, 30);
    private final Color blanco = Color.WHITE;

    public VentanaMenuMesero(Coordinador coordinador) {
        this.coordinador = coordinador;
        //CONFIGURACION BASE----------------------------------------------------
        setTitle("Menú de Administrador - Sistema Restaurante");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 650);
        setLocationRelativeTo(null); //Para que este siempre centrado

        initComponents();

    }

    private void initComponents() {
        //Primero usamos la clase PanelFondo
        //necesarias para tener de fondo una imagen
        PanelFondo panel_con_fondo = new PanelFondo();
        panel_con_fondo.setLayout(new GridBagLayout()); // Para centrar el cuadro blanco
        setContentPane(panel_con_fondo);

        //Segundo, creamos el panel blanco que es el contenedor de nuestra label
        //y botones
        PanelRedondeado cuadro_blanco = new PanelRedondeado(50, Color.WHITE);
        cuadro_blanco.setLayout(new BorderLayout(0, 30));
        cuadro_blanco.setBorder(new javax.swing.border.EmptyBorder(40, 50, 40, 50));
        cuadro_blanco.setPreferredSize(new Dimension(600, 600));

        //Tercero que es el titulo de la etiqueta que aparece en el menu 
        Font fuenteTitulo = cargarFuenteAgbalumo(35f);
        JLabel lbl_titulo = new JLabel("Menú de Administrador");
        lbl_titulo.setFont(fuenteTitulo);
        lbl_titulo.setForeground(Color.BLACK);
        cuadro_blanco.add(lbl_titulo, BorderLayout.NORTH);
        lbl_titulo.setHorizontalAlignment(SwingConstants.CENTER);
        // También es buena idea asegurar que el texto ocupe todo el ancho disponible
        lbl_titulo.setAlignmentX(Component.CENTER_ALIGNMENT);

        //Cuarto, ACOMODO DE LOS BOTONES (NO MOVER)       
        JPanel panel_botones = new JPanel(new GridLayout(3, 2, 25, 25)); // 25px de espacio entre botones
        panel_botones.setBackground(blanco);

        //Quinto, creación de los botones 
        //Si agregan botones este es el formato 
        // Formato: BotonMenuAdministrador(texto, ruta, color, ancho_icono, alto_icono)
        Font fuente_botones = cargarFuenteAgbalumo(20f);
        BotonMenuAdministrador btn_comanda = new BotonMenuAdministrador("Comandas", "/imagenes/comandaPNG.png", verde, 80, 80, fuente_botones);
        BotonMenuAdministrador btn_cliente = new BotonMenuAdministrador("Clientes", "/imagenes/clientePNG.png", verde, 80, 80, fuente_botones);
        BotonMenuAdministrador btn_volver = new BotonMenuAdministrador("Volver", "/imagenes/volverPNG.png", rojo, 80, 80, fuente_botones);

        //Agregar botones al panel 
        panel_botones.add(btn_comanda);
        panel_botones.add(btn_cliente);
        panel_botones.add(btn_volver);

        cuadro_blanco.add(panel_botones, BorderLayout.CENTER);

        //Sexto, centramos todo en la pantalla 
        // Añadimos el cuadro blanco al panel con patrón usando GridBagConstraints
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        panel_con_fondo.add(cuadro_blanco, gbc);

        // --- ACCIONES DE LOS BOTONES ---
        btn_volver.addActionListener(e -> {
            JOptionPane.showMessageDialog(this, "Regresando al inicio...");
        });
        
        
        btn_cliente.addActionListener(e -> {
            coordinador.mostrarMenuCliente();
        });
    }

    //METODO PARA LA FUENTE BONITA------------------------------------------
    //La descargue del google fonts ouyeah
    public Font cargarFuenteAgbalumo(float tamano) {
        try {
            InputStream input_stream = getClass().getResourceAsStream("/fonts/Agbalumo-Regular.ttf");
            if (input_stream == null) {
                throw new Exception("No se encontro el archivo de fuente");
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, input_stream);
            return font.deriveFont(tamano);
        } catch (Exception e) {
            System.err.println("Error al cargar Agbalumo: " + e.getMessage());
            // Si falla, devuelve una fuente de respaldo decente
            return new Font("Segoe UI", Font.BOLD, (int) tamano);
        }
    }

}
