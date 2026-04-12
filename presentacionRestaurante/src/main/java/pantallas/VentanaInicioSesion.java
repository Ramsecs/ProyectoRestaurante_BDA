/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import controladorRestaurante.Coordinador;
import dtosDelRestaurante.EmpleadoRegistroDTO;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import recursos.BotonMenuAdministrador;
import recursos.GestorFuentes;
import recursos.PanelFondo;

/**
 * Ventana de Inicio de Sesion con dimensiones fijas y diseño unificado.
 * @author DANIEL / RAMSES
 */
public class VentanaInicioSesion extends JFrame {

    private final Coordinador coordinador;
    
    // Colores
    private static final Color COLOR_PANEL_BLANCO = Color.WHITE;
    private static final Color COLOR_NARANJA = new Color(255, 189, 68);
    private static final Color COLOR_BOTONES_ACCION = new Color(255, 89, 89); // Salmón

    // Dimensiones Estrictas
    private final Dimension DIM_BOTON_PRINCIPAL = new Dimension(350, 75);
    private final Dimension DIM_CAMPO_TEXTO = new Dimension(350, 65);
    private final Dimension DIM_BOTON_LOGIN = new Dimension(200, 60);
    private final Dimension DIM_CUADRO_BLANCO = new Dimension(700, 580);

    private JTextField txtCodigo;
    private JPanel cuadro_blanco;
    
    private Font fuente_titulo, fuente_botones, fuente_etiquetas;

    public VentanaInicioSesion(Coordinador coordinador) {
        this.coordinador = coordinador;
        
        setTitle("Sistema Restaurante - Acceso");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(850, 750); 
        setLocationRelativeTo(null);
        setResizable(false);

        cargarFuentes();
        initComponents();
        mostrarSeleccionRol();
    }

    private void cargarFuentes() {
        fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 48f);
        fuente_botones = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 20f);
        fuente_etiquetas = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 26f);
    }

    private void initComponents() {
        PanelFondo panel_fondo = new PanelFondo();
        panel_fondo.setLayout(new GridBagLayout());
        setContentPane(panel_fondo);

        cuadro_blanco = new JPanel() {
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
        cuadro_blanco.setPreferredSize(DIM_CUADRO_BLANCO);
        cuadro_blanco.setMinimumSize(DIM_CUADRO_BLANCO);

        panel_fondo.add(cuadro_blanco);
    }

    /**
     * Menu principal con botones.
     */
    private void mostrarSeleccionRol() {
        cuadro_blanco.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE; // Importante: No estirar

        // Titulo
        JLabel lbl_titulo = new JLabel("Bienvenido a el sistema", SwingConstants.CENTER);
        lbl_titulo.setFont(fuente_titulo);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 60, 0);
        cuadro_blanco.add(lbl_titulo, gbc);

        // Botones Naranjas
        BotonMenuAdministrador btnAdmin = new BotonMenuAdministrador("Soy administrador", null, COLOR_NARANJA, 0, 0, fuente_botones);
        configurarBoton(btnAdmin, DIM_BOTON_PRINCIPAL);
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 10, 0);
        cuadro_blanco.add(btnAdmin, gbc);

        BotonMenuAdministrador btnMesero = new BotonMenuAdministrador("Soy mesero", null, COLOR_NARANJA, 0, 0, fuente_botones);
        configurarBoton(btnMesero, DIM_BOTON_PRINCIPAL);
        gbc.gridy = 2;
        cuadro_blanco.add(btnMesero, gbc);

        btnAdmin.addActionListener(e -> mostrarLogin("Administrador"));
        btnMesero.addActionListener(e -> mostrarLogin("Mesero"));

        actualizarContenedor();
    }

    /**
     * Inicio de sesión identico para ambos roles con validación cruzada.
     * @param rolEsperado "Administrador" o "Mesero"
     */
    private void mostrarLogin(String rolEsperado) {
        cuadro_blanco.removeAll();
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        gbc.fill = GridBagConstraints.NONE;

        // 1. Titulo
        JLabel lbl_titulo = new JLabel("Inicio Sesión " + rolEsperado, SwingConstants.CENTER);
        lbl_titulo.setFont(fuente_titulo);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 40, 0);
        cuadro_blanco.add(lbl_titulo, gbc);

        // 2. Etiqueta
        JLabel lblCodigo = new JLabel("Ingrese su Código de Acceso", SwingConstants.CENTER);
        lblCodigo.setFont(fuente_etiquetas);
        gbc.gridy = 1;
        gbc.insets = new Insets(10, 0, 20, 0);
        cuadro_blanco.add(lblCodigo, gbc);

        // 3. Campo Unico
        txtCodigo = crearTextFieldRedondeado(DIM_CAMPO_TEXTO);
        gbc.gridy = 2;
        gbc.insets = new Insets(0, 0, 60, 0);
        cuadro_blanco.add(txtCodigo, gbc);

        // 4. Panel de botones inferiores
        JPanel panelInferior = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 0));
        panelInferior.setOpaque(false);

        BotonMenuAdministrador btnVolver = new BotonMenuAdministrador("Volver", null, COLOR_BOTONES_ACCION, 0, 0, fuente_botones);
        configurarBoton(btnVolver, DIM_BOTON_LOGIN);
        
        BotonMenuAdministrador btnEntrar = new BotonMenuAdministrador("Iniciar Sesión", null, COLOR_BOTONES_ACCION, 0, 0, fuente_botones);
        configurarBoton(btnEntrar, DIM_BOTON_LOGIN);

        panelInferior.add(btnVolver);
        panelInferior.add(btnEntrar);

        gbc.gridy = 3;
        gbc.insets = new Insets(0, 0, 0, 0);
        cuadro_blanco.add(panelInferior, gbc);

        // --- EVENTOS ---
        
        btnVolver.addActionListener(e -> mostrarSeleccionRol());
        
        btnEntrar.addActionListener(e -> {
            String codigoIngresado = txtCodigo.getText().trim();

            if (codigoIngresado.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, ingrese su código de acceso.");
                return;
            }

            try {
                // Buscamos al empleado por código
                EmpleadoRegistroDTO emp = coordinador.validarIngresoPorCodigo(codigoIngresado);

                // VALIDACION DE ROL:
                // Comparamos el rol del DTO con el esperado en esta pantalla
                if (emp.getRol().equalsIgnoreCase(rolEsperado)) {
                    
                    JOptionPane.showMessageDialog(this, "¡Bienvenido " + emp.getNombres() + "!");
                    
                    // Abrir la ventana correspondiente
                    if (emp.getRol().equalsIgnoreCase("ADMINISTRADOR")) {
                        txtCodigo.setText("");
                        coordinador.abrirVentanaMenuAdmin();
                    } else {
                        txtCodigo.setText("");
                        coordinador.setMeseroEnSesion(emp);
                        coordinador.abrirVentanaMenuMesero();
                    }
                    this.dispose();

                } else {
                    // Si el codigo existe pero no es para este acceso
                    JOptionPane.showMessageDialog(this, 
                        "Acceso denegado: Este código no pertenece a un " + rolEsperado, 
                        "Error de Rol", 
                        JOptionPane.WARNING_MESSAGE);
                    txtCodigo.setText("");
                }

            } catch (Exception ex) {
                // Si el código no existe en la BD o hay error de conexión
                JOptionPane.showMessageDialog(this, "Código de acceso incorrecto", "Error", JOptionPane.ERROR_MESSAGE);
                txtCodigo.setText("");
            }
        });

        actualizarContenedor();
    }

    private void configurarBoton(JButton btn, Dimension dim) {
        btn.setPreferredSize(dim);
        btn.setMinimumSize(dim);
        btn.setMaximumSize(dim);
    }

    private JTextField crearTextFieldRedondeado(Dimension dim) {
        JTextField tf = new JTextField() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g.create();
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2d.setColor(getBackground());
                g2d.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));
                g2d.dispose();
                super.paintComponent(g);
            }
        };
        tf.setOpaque(false);
        tf.setBackground(COLOR_NARANJA);
        tf.setHorizontalAlignment(JTextField.CENTER);
        tf.setFont(new Font("Arial", Font.BOLD, 24));
        tf.setBorder(new EmptyBorder(0, 20, 0, 20));
        tf.setPreferredSize(dim);
        tf.setMinimumSize(dim);
        tf.setMaximumSize(dim);
        return tf;
    }

    private void actualizarContenedor() {
        cuadro_blanco.revalidate();
        cuadro_blanco.repaint();
    }
}