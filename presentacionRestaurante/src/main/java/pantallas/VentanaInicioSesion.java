/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

/**
 *
 * @author DANIEL
 */
import controladorRestaurante.Coordinador;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.util.Objects;
import observadorRestaurante.Observador;
import recursos.BotonMenuAdministrador;
import recursos.GestorFuentes;
import recursos.PanelFondo;

public class VentanaInicioSesion extends JFrame {

    private Observador metiche;
    private final Coordinador coordinador;
    
    // Colores y constantes
    private static final Color COLOR_PANEL_BLANCO = Color.WHITE;
    private static final Color COLOR_TEXTFIELDS = new Color(255, 189, 68); // Naranja
    private final Color verde = new Color(116, 155, 87);

    private JTextField txtUsuario;
    private JPasswordField txtContrasena;

    public void setConexionObservador(Observador metiche) {
        this.metiche = metiche;
    }

    public VentanaInicioSesion(Coordinador coordinador) {
        this.coordinador = coordinador;
        
        setTitle("Inicia Sesión - Sistema Restaurante");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 750); // Tamaño similar a tu ventana de ingredientes
        setLocationRelativeTo(null);
        setResizable(false);

        initComponents();
    }

    private void initComponents() {
        // --- 1. SECCIÓN DEL FONDO (Usando tu clase PanelFondo) ---
        PanelFondo panel_fondo = new PanelFondo();
        panel_fondo.setLayout(new GridBagLayout());
        setContentPane(panel_fondo);

        // --- 2. SECCIÓN DEL CUADRO BLANCO REDONDEADO ---
        JPanel cuadro_blanco = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(COLOR_PANEL_BLANCO);
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 50, 50));
                g2d.dispose();
            }
        };
        cuadro_blanco.setOpaque(false);
        cuadro_blanco.setLayout(new GridBagLayout());
        cuadro_blanco.setPreferredSize(new Dimension(500, 550)); // Tamaño del cuadro interno
        cuadro_blanco.setBorder(new EmptyBorder(40, 50, 40, 50));

        // --- FUENTES ---
        Font fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 36f);
        Font fuente_botones = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 18f);
        Font fuente_rabbits_mediana = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 20f);

        // --- CONFIGURACIÓN DE COMPONENTES INTERNOS ---
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // Título
        JLabel lbl_titulo = new JLabel("Inicia Sesión", SwingConstants.CENTER);
        lbl_titulo.setFont(fuente_titulo);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 40, 0);
        cuadro_blanco.add(lbl_titulo, gbc);

        // Usuario
        JLabel lblUsuario = new JLabel("Usuario");
        lblUsuario.setFont(fuente_rabbits_mediana);
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 5, 0);
        cuadro_blanco.add(lblUsuario, gbc);

        txtUsuario = crearTextFieldRedondeado();
        gbc.gridy = 2;
        cuadro_blanco.add(txtUsuario, gbc);

        // Contraseña
        JLabel lblContrasena = new JLabel("Contraseña");
        lblContrasena.setFont(fuente_rabbits_mediana);
        gbc.gridy = 3;
        gbc.insets = new Insets(20, 0, 5, 0);
        cuadro_blanco.add(lblContrasena, gbc);

        txtContrasena = crearPasswordFieldRedondeado();
        gbc.gridy = 4;
        gbc.insets = new Insets(5, 0, 40, 0);
        cuadro_blanco.add(txtContrasena, gbc);

        // Botón Entrar (Mismo ancho que los campos)
        BotonMenuAdministrador btnEntrar = new BotonMenuAdministrador("Entrar", "/imagenes/registrarPNG.png", verde, 30, 30, fuente_botones);
        btnEntrar.setPreferredSize(new Dimension(0, 60));
        gbc.gridy = 5;
        gbc.insets = new Insets(0, 0, 0, 0);
        cuadro_blanco.add(btnEntrar, gbc);

        // --- 3. AGREGAR EL CUADRO BLANCO AL PANEL DE FONDO ---
        // Esto hace que el cuadro blanco flote centrado sobre la imagen de fondo
        GridBagConstraints gbc_centro = new GridBagConstraints();
        gbc_centro.gridx = 0;
        gbc_centro.gridy = 0;
        panel_fondo.add(cuadro_blanco, gbc_centro);

        // Evento del botón
        btnEntrar.addActionListener(e -> {
            // Tu lógica aquí
            System.out.println("Usuario: " + txtUsuario.getText());
        });
    }

    // Métodos auxiliares para mantener el estilo redondeado de los campos
    private JTextField crearTextFieldRedondeado() {
        JTextField tf = new JTextField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 25, 25));
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        tf.setOpaque(false);
        tf.setBackground(COLOR_TEXTFIELDS);
        tf.setBorder(new EmptyBorder(10, 15, 10, 15));
        tf.setPreferredSize(new Dimension(0, 45));
        return tf;
    }

    private JPasswordField crearPasswordFieldRedondeado() {
        JPasswordField pf = new JPasswordField(20) {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 25, 25));
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        pf.setOpaque(false);
        pf.setBackground(COLOR_TEXTFIELDS);
        pf.setBorder(new EmptyBorder(10, 15, 10, 15));
        pf.setPreferredSize(new Dimension(0, 45));
        return pf;
    }
}