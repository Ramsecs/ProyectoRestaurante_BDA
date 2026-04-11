/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import controladorRestaurante.Coordinador;
import java.awt.*;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import observadorRestaurante.Observador;
import recursos.BotonMenuAdministrador;
import recursos.ComboBoxPersonalizado;
import recursos.GestorFuentes;
import recursos.PanelFondo;
import recursos.PanelRedondeado;
import recursos.TextFieldPersonalizado;

/**
 *
 * @author josma
 */
public class VentanaCrearComanda extends JFrame{
private final Coordinador coordinador;
    private JPanel panel_cartas; 
    private CardLayout navegador;
    private final int TOTAL_MESAS = 20;

    // Colores corporativos
    private final Color verde = new Color(116, 155, 87);
    private final Color naranja = new Color(255, 184, 77);
    private final Color rojo = new Color(188, 55, 30);

    public VentanaCrearComanda(Coordinador coordinador) {
        this.coordinador = coordinador;
        setTitle("Gestión de Comandas - Sistema Restaurante");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 850);
        setLocationRelativeTo(null);
        initComponents();
    }

    public void initComponents() {
        //FONDO-----------------------------------------------------------------
        PanelFondo panel_principal = new PanelFondo();
        panel_principal.setLayout(new GridBagLayout());
        setContentPane(panel_principal);

        // CONFIGURACIÓN DE CARDLAYOUT (para poder redibujar)-------------------
        navegador = new CardLayout();
        panel_cartas = new JPanel(navegador);
        panel_cartas.setOpaque(false); // Transparente para ver el fondo de ingredientes

        //CREAR LAS DOS VISTAS--------------------------------------------------
        panel_cartas.add(crearVistaSeleccionMesa(), "SELECCION"); // --> mesas
        panel_cartas.add(crearVistaFormularioComanda(), "FORMULARIO"); //---> crear comanda

        // Añadir el contenedor de cartas al centro del fondo
        GridBagConstraints gbc_fondo = new GridBagConstraints();
        gbc_fondo.fill = GridBagConstraints.BOTH;
        gbc_fondo.weightx = 1.0;
        gbc_fondo.weighty = 1.0;
        gbc_fondo.insets = new Insets(20, 40, 20, 40); // Margen para que se vea el fondo
        panel_principal.add(panel_cartas, gbc_fondo);
    }

    // --- VISTA 1 -  DE MESAS ---
    private JPanel crearVistaSeleccionMesa() {
        PanelRedondeado panel = new PanelRedondeado(50, Color.WHITE);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        Font fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 40f);
        Font fuente_mesas = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 22f);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 40, 0);
        //TITULO
        JLabel lbl = new JLabel("Seleccione una Mesa", SwingConstants.CENTER);
        lbl.setFont(fuente_titulo);
        panel.add(lbl, gbc);

        // Panel de botones (4 filas, 5 columnas)
        JPanel grilla = new JPanel(new GridLayout(4, 5, 25, 25));
        grilla.setOpaque(false);

        for (int i = 1; i <= TOTAL_MESAS; i++) {
            BotonMenuAdministrador btn = new BotonMenuAdministrador("Mesa " + i, null, verde, 0, 0, fuente_mesas);
            final int numMesa = i;
            btn.addActionListener(e -> {
                System.out.println("Mesa " + numMesa + " seleccionada.");
                navegador.show(panel_cartas, "FORMULARIO");
            });
            grilla.add(btn);
        }

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(grilla, gbc);

        return panel;
    }

    // --- VISTA 2 - AGREGAR COMANDA (Formulario) ------------------------------
    private JPanel crearVistaFormularioComanda() {
        PanelRedondeado panel = new PanelRedondeado(50, Color.WHITE);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        Font fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 35f);
        Font fuente_rabbits = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 18f);
        Font fuente_botones = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 16f);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0);

        // TITULO---------------------------------------------------------------
        JLabel lbl_titulo = new JLabel("Agregar Comanda", SwingConstants.CENTER);
        lbl_titulo.setFont(fuente_titulo);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        panel.add(lbl_titulo, gbc);

        // SECCIÓN CLIENTE (Usando tus ComboBox y TextFields personalizados)
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridy = 1;
        JLabel lbl_tipo = new JLabel("Tipo de cliente");
        lbl_tipo.setFont(fuente_rabbits);
        panel.add(lbl_tipo, gbc);
        //COMBOBOX--------------------------------------------------------------
        gbc.gridy = 2;
        String[] tipos = {"Seleccionar...", "Frecuente", "Sin cliente"};
        ComboBoxPersonalizado<String> cb_cliente = new ComboBoxPersonalizado<>(tipos);
        cb_cliente.setPreferredSize(new Dimension(0, 35));
        panel.add(cb_cliente, gbc);
        //LABEL-----------------------------------------------------------------
        gbc.gridy = 3;
        JLabel lbl_nom = new JLabel("Nombre Cliente");
        lbl_nom.setFont(fuente_rabbits);
        panel.add(lbl_nom, gbc);

        gbc.gridy = 4;
        TextFieldPersonalizado txt_cliente = new TextFieldPersonalizado(20);
        txt_cliente.setPreferredSize(new Dimension(0, 35));
        panel.add(txt_cliente, gbc);

        
        
        // ESPACIO PARA LA TABLA (Simulada para este paso)
        gbc.gridy = 5;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        JPanel placeholderTabla = new JPanel(); 
        placeholderTabla.setBackground(Color.WHITE);
        placeholderTabla.setBorder(BorderFactory.createTitledBorder("Lista de Productos"));
        panel.add(placeholderTabla, gbc);

        // PANEL DE BOTONES INFERIORES
        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.setOpaque(false);
        
        BotonMenuAdministrador btnVolver = new BotonMenuAdministrador("Volver", "/imagenes/volverPNG.png", rojo, 25, 25, fuente_botones);
        btnVolver.setPreferredSize(new Dimension(180, 50));
        btnVolver.addActionListener(e -> navegador.show(panel_cartas, "SELECCION"));
        
        BotonMenuAdministrador btnAgregar = new BotonMenuAdministrador("Agregar", "/imagenes/registrarPNG.png", verde, 25, 25, fuente_botones);
        btnAgregar.setPreferredSize(new Dimension(180, 50));

        panelBotones.add(btnVolver, BorderLayout.WEST);
        panelBotones.add(btnAgregar, BorderLayout.EAST);

        gbc.gridy = 6;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(panelBotones, gbc);

        return panel;
    }
}
