/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import controladorRestaurante.Coordinador;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import observadorRestaurante.Observador;
import recursos.*;

/**
 *
 * @author josma
 */
public class VentanaMenuProducto extends JFrame {

    private final Coordinador coordinador;

    private TextFieldPersonalizado txt_nombre;
    private TextFieldPersonalizado txt_precio;
    private TextFieldPersonalizado txt_buscador;

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

    public VentanaMenuProducto(Coordinador coordinador) {
        this.coordinador = coordinador;

        setTitle("Gestión de Productos - Sistema Restaurante");
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

        //--------------------------TITULO LABEL (FILA 0)-----------------------
        JLabel lbl_titulo = new JLabel("Menú de Productos", SwingConstants.CENTER);
        lbl_titulo.setFont(fuente_titulo);
        gbc.gridy = 0;
        gbc.weighty = 0.02;
        cuadro_blanco.add(lbl_titulo, gbc);

        //-------------------------BUSCADOR LABEL y TXTFIELD (FILA 1 Y 2)-------
        gbc.gridy = 1;
        gbc.weighty = 0;
        JLabel lbl_buscar = new JLabel("Buscar cliente");
        lbl_buscar.setFont(fuente_rabbits_mediana);
        cuadro_blanco.add(lbl_buscar, gbc);

        gbc.gridy = 2;
        txt_buscador = new TextFieldPersonalizado(20);
        txt_buscador.setPreferredSize(new Dimension(0, 35)); // Altura fija
        cuadro_blanco.add(txt_buscador, gbc);

        //------------------------TABLA (FILA 3)--------------------------------
        gbc.gridy = 3;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.BOTH;

        String[] columnas = {"Ingredientes", "Nombre", "Precio", "Tipo", "Imagen"};
        modelo_tabla = new ModeloTablaEditable(columnas, 0, 2);

        tabla = new TablaEstilizada(modelo_tabla, fuente_rabbits_pequena);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(0, 150));
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        tabla.setRowHeight(45); // Esto da espacio para que el botón se dibuje bien
        tabla.setCellSelectionEnabled(false); // Evita que la selección interfiera con el clic del botón

        cuadro_blanco.add(scroll, gbc);

        //APARTADO PARA EL COMPORTAMIENTO DE LA COLUMNA DE INGREDIENTES 
        BotonMenuAdministrador btn_detalles = new BotonMenuAdministrador("Ver detalles",
                null, //Sin icono
                new Color(255, 184, 77),
                0, 0,
                fuente_rabbits_pequena
        );

        //------------------------APARTADO PARA QUE SE MUESTRE LA IMAGEN--------
        tabla.getColumnModel().getColumn(4).setCellRenderer(new ImagenTablaRender());
        // Ajustamos el ancho de esa columna para que no sea tan grande
        tabla.getColumnModel().getColumn(4).setPreferredWidth(60);

        //----------------------------------------------------------------------
        //ESTO ES PARA ESPECIFICAR QUE HAY QUE DIBUJAR UN BOTON EN CADA FILA, OSEA
        //QUE SE DIBUJE QUE SE PONGA, con ayuda de las clases del paquete recursos:)
        tabla.getColumnModel().getColumn(0).setCellRenderer(new BotonTablaRender(btn_detalles));
        TableColumn columna_ingredientes = tabla.getColumnModel().getColumn(0);
        columna_ingredientes.setCellEditor(new BotonTablaEditor(btn_detalles, e -> {
            //AQUI TIENE QUE IR LA LOGICA PARA QUE SE ABRA LA VENTANA DE
            //INGREDIENTES PARA PODER VISUALIZARLOS 
            //DE MOMENTO SOLO CORROBORAMOS QUE FUNCIONE
            int fila = tabla.getSelectedRow();
            if (fila != -1) {
                String nombre = tabla.getValueAt(fila, 1).toString();
                System.out.println("Clic en ingredientes de la fila: " + fila);
            }

        }));

        //DATOS FAKE SOLO DE PRUEBA BORRAR DESPUES 
        // 1. Obtenemos el modelo de tu tabla
        DefaultTableModel modelo = (DefaultTableModel) tabla.getModel();

        // 2. Limpiamos por si acaso
        modelo.setRowCount(0);

        // 3. Agregamos filas de prueba
        // Formato: {Nombre, Precio, Tipo, Imagen (ID o Ruta), Botón}
        modelo.addRow(new Object[]{"Ver detalles", "Enchiladas Suizas", "150.00", "Platillo", "/imagenes/fondo.jpg"});
        modelo.addRow(new Object[]{"Ver detalles", "Tacos al Pastor", "120.00", "Platillo", "/imagenes/hola.jpg",});
        modelo.addRow(new Object[]{"Ver detalles", "Limonada Natural", "45.00", "Bebida", "/imagenes/fondo.jpg",});
        modelo.addRow(new Object[]{"Ver detalles", "Pastel de Chocolate", "85.00", "Postre", "/imagenes/hola.jpg",});
        modelo.addRow(new Object[]{"Ver detalles", "Hamburguesa Doble", "180.00", "Platillo", "/imagenes/fondo.jpg",});
        

        //----------------SECCION PARA REGISTRAR PRODUCTO (FILA 4)--------------

        gbc.gridy = 4;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Panel con borde punteado para agrupar el registro
        PanelBordePunteado panel_registro = new PanelBordePunteado();
        panel_registro.setLayout(new GridBagLayout());
        GridBagConstraints gbcReg = new GridBagConstraints();
        gbcReg.insets = new Insets(5, 10, 5, 10);
        gbcReg.fill = GridBagConstraints.HORIZONTAL;

        //---------------SUBPANEL IZQUIERDO (Campos de texto)-------------------
        JPanel panel_campos = new JPanel(new GridLayout(6, 1, 0, 5));
        panel_campos.setOpaque(false);
        panel_campos.setPreferredSize(new Dimension(300,200));

        JLabel lbl_nombre = new JLabel("Nombre:");
        lbl_nombre.setFont(fuente_rabbits_pequena);
        txt_nombre = new TextFieldPersonalizado(15);

        JLabel lbl_precio = new JLabel("Precio:");
        lbl_precio.setFont(fuente_rabbits_pequena);
        txt_precio = new TextFieldPersonalizado(15);

        JLabel lbl_tipo = new JLabel("Tipo:");
        lbl_tipo.setFont(fuente_rabbits_pequena);

        String[] opcionesTipo = {"Seleccionar...", "Platillo", "Bebida", "Postre"};
        ComboBoxPersonalizado<String> combo_tipo = new ComboBoxPersonalizado<>(opcionesTipo);
        combo_tipo.setPreferredSize(new Dimension(0, 35)); // Misma altura que tus textfields

        panel_campos.add(lbl_nombre);
        panel_campos.add(txt_nombre);
        panel_campos.add(lbl_precio);
        panel_campos.add(txt_precio);
        panel_campos.add(lbl_tipo);
        panel_campos.add(combo_tipo);
        
        gbcReg.gridx = 0;
        gbcReg.weightx = 0.6;
        panel_registro.add(panel_campos, gbcReg);

        //----------SUBPANEL DERECHO (apartado de imagen y boton ingredinetes)--
        JPanel panel_medio = new JPanel(new GridBagLayout());

        panel_medio.setOpaque(false);

        GridBagConstraints gbcMedia = new GridBagConstraints();

        //Recuadro para la imagen 
        JLabel lbl_foto = new JLabel();

        lbl_foto.setPreferredSize(new Dimension(120, 120));
        lbl_foto.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        lbl_foto.setHorizontalAlignment(JLabel.CENTER);
        lbl_foto.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //Cargamos la imagen por defecto del icono 
        lbl_foto.setIcon(new ImageIcon(getClass().getResource("/imagenes/imagenPNG.png")));

        //Boton para ingredientes 
        BotonMenuAdministrador btn_ingredientes = new BotonMenuAdministrador("Agregar ingredientes", null, naranja,
                0, 0, fuente_rabbits_pequena);

        gbcMedia.gridy = 0;
        panel_medio.add(new JLabel("Cargar imagen"), gbcMedia);
        gbcMedia.gridy = 1;
        panel_medio.add(lbl_foto, gbcMedia);
        gbcMedia.gridy = 2;
        gbcMedia.insets = new Insets(10, 0, 0, 0);
        panel_medio.add(btn_ingredientes, gbcMedia);

        gbcReg.gridx = 1;
        gbcReg.weightx = 0.4;
        panel_registro.add(panel_medio, gbcReg);

        cuadro_blanco.add(panel_registro, gbc);

        //-------------------------BOTON VOLVER (FILA 5)------------------------
        gbc.gridy = 5;
        gbc.weighty = 0.1;
        gbc.fill = GridBagConstraints.NONE;
        gbc.anchor = GridBagConstraints.CENTER;

        BotonMenuAdministrador btn_volver = new BotonMenuAdministrador(
                "Volver", "/imagenes/volverPNG.png", rojo, 30, 30, fuente_botones
        );
        btn_volver.setPreferredSize(new Dimension(200, 50));
        cuadro_blanco.add(btn_volver, gbc);

        //--- AGREGAR CUADRO BLANCO AL FONDO ---
        GridBagConstraints gbc_fondo = new GridBagConstraints();
        gbc_fondo.insets = new Insets(40, 60, 40, 60);
        gbc_fondo.fill = GridBagConstraints.BOTH;
        gbc_fondo.weightx = 1.0;
        gbc_fondo.weighty = 1.0;
        panel_fondo.add(cuadro_blanco, gbc_fondo);

        //EVENTO PARA PODER CAMBIAR LA IMAGEN Y GUARDARLA DEL PRODUCTO
        lbl_foto.addMouseListener(new MouseAdapter() {
            public void mouseClicked(MouseEvent e) {
                JFileChooser selector = new JFileChooser();
                //con showOpenDialog permite abrir la ventana para poder seleccionar
                //la imagen que queremos subir para el producto, con null
                //solo especificamos que se centre en nuestro monitor
                int estado = selector.showOpenDialog(null);
                //APPROVE_OPTION es para saber si el usuario selecciono un archivo
                if (estado == JFileChooser.APPROVE_OPTION) {
                    lbl_foto.setIcon(ClaseImagen.escalarImagenDesdeFile(selector.getSelectedFile(), 120, 120));
                    lbl_foto.setText("");
                }

            }
        });

        btn_volver.addActionListener(e -> coordinador.regresarMenuMesero());
    }
}
