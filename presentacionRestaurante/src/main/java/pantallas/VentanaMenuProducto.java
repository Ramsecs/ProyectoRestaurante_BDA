/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import controladorRestaurante.Coordinador;
import dtosDelRestaurante.ProductoDTO;
import entidadesEnumeradorDTO.TipoPlatilloDTO;
import enumEntidades.TipoPlatillo;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import observadorRestaurante.Observador;
import recursos.*;
import java.util.List;
import javax.swing.table.TableRowSorter;

/**
 * 
 * @author josma / Ramses
 */
public class VentanaMenuProducto extends JFrame {

    private final Coordinador coordinador;
    
    private File archivo_imagen = null;
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
    private ComboBoxPersonalizado<Object> combo_tipo; 
    private JLabel lbl_foto;

    public VentanaMenuProducto(Coordinador coordinador) {
        this.coordinador = coordinador;

        setTitle("Gestion de Productos - Sistema Restaurante");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1000, 850);
        setLocationRelativeTo(null);
        initComponents();
    }

    private void initComponents() {
        //-------------SECCION DEL FONDO----------------------------------------
        PanelFondo panel_fondo = new PanelFondo();
        panel_fondo.setLayout(new GridBagLayout());
        setContentPane(panel_fondo);

        //-------------SECCION DEL PANEL BLANCO---------------------------------
        PanelRedondeado cuadro_blanco = new PanelRedondeado(50, Color.WHITE);
        cuadro_blanco.setLayout(new GridBagLayout());
        cuadro_blanco.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        // Quitamos el setPreferredSize rígido para que el Layout pueda trabajar mejor
        cuadro_blanco.setPreferredSize(new Dimension(850, 780));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 1.0; 
        gbc.insets = new Insets(2, 0, 2, 0); 

        //----------------------------FUENTES-----------------------------------
        Font fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 32f);
        Font fuente_botones = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 16f);
        Font fuente_rabbits_mediana = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 18f);
        Font fuente_rabbits_pequena = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 14f);

        //--------------------------TITULO LABEL-----------------------
        JLabel lbl_titulo = new JLabel("Menú de Productos", SwingConstants.CENTER);
        lbl_titulo.setFont(fuente_titulo);
        gbc.gridy = 0;
        gbc.weighty = 0.02;
        cuadro_blanco.add(lbl_titulo, gbc);

        //-------------------------BUSCADOR LABEL y TXTFIELD-------
        gbc.gridy = 1;
        gbc.weighty = 0;
        JLabel lbl_buscar = new JLabel("Buscar producto");
        lbl_buscar.setFont(fuente_rabbits_mediana);
        cuadro_blanco.add(lbl_buscar, gbc);

        gbc.gridy = 2;
        txt_buscador = new TextFieldPersonalizado(20);
        txt_buscador.setPreferredSize(new Dimension(0, 35)); 
        cuadro_blanco.add(txt_buscador, gbc);

        //------------------------TABLA--------------------------------
        gbc.gridy = 3;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.BOTH;

        String[] columnas = {"Ingredientes", "Nombre", "Precio", "Tipo", "Imagen", "ID"};
        modelo_tabla = new ModeloTablaEditable(columnas, 0, 2);

        tabla = new TablaEstilizada(modelo_tabla, fuente_rabbits_pequena);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(0, 150));
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);
        tabla.setRowHeight(45); 
        tabla.setCellSelectionEnabled(false); 

        cuadro_blanco.add(scroll, gbc);
        
        // Creamos el sorter vinculado al modelo de la tabla
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo_tabla);
        tabla.setRowSorter(sorter);

        // Agregamos el evento de escucha al campo de texto buscador
        txt_buscador.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String texto = txt_buscador.getText().trim();
                if (texto.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    // (?i) hace que ignore mayusculas y minusculas
                    // Buscamos en la columna 1 el cual es el Nombre y columna 3  que es el Tipo.
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 1, 3));
                }
            }
        });

        //APARTADO PARA EL COMPORTAMIENTO DE LA COLUMNA DE INGREDIENTES 
        BotonMenuAdministrador btn_detalles = new BotonMenuAdministrador("Ver detalles",
                null, naranja, 0, 0, fuente_rabbits_pequena
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
            int filaVista = tabla.getSelectedRow();
            if (filaVista != -1) {
                // Convertimos el indice por si hay filtros activos
                int fila_modelo = tabla.convertRowIndexToModel(filaVista);

                // Obtenemos el ID del producto
                Long id_producto = (Long) modelo_tabla.getValueAt(fila_modelo, 5);

                // Obtenemos el nombre para el titulo del dialogo
                String nombre_producto = modelo_tabla.getValueAt(fila_modelo, 1).toString();

                // Llamamos al coordinador pasando el ID del producto clickeado
                // Este metodo en el coordinador debe abrir el JDialog que muestra ingredientes
                coordinador.mostrarDialogoIngredientesVista(this, id_producto, nombre_producto);
            }
        }));

        //----------------SECCION PARA REGISTRAR PRODUCTO--------------
        gbc.gridy = 4;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Panel con borde punteado para agrupar el registro
        PanelBordePunteado panel_registro = new PanelBordePunteado();
        panel_registro.setLayout(new GridBagLayout());
        GridBagConstraints gbcReg = new GridBagConstraints();
        gbcReg.insets = new Insets(5, 10, 5, 10);
        gbcReg.fill = GridBagConstraints.HORIZONTAL;

        //---------------SUBPANEL IZQUIERDO-------------------
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

        // Preparar opciones con "Seleccionar..." + los valores del Enum
        TipoPlatillo[] enums = TipoPlatillo.values();
        Object[] items_combo = new Object[enums.length + 1];
        items_combo[0] = "Seleccionar...";
        System.arraycopy(enums, 0, items_combo, 1, enums.length);

        combo_tipo = new ComboBoxPersonalizado<>(items_combo);
        combo_tipo.setPreferredSize(new Dimension(0, 35)); 

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
        GridBagConstraints gbc_media = new GridBagConstraints();

        //Recuadro para la imagen 
        lbl_foto = new JLabel();
        lbl_foto.setPreferredSize(new Dimension(120, 120));
        lbl_foto.setBorder(BorderFactory.createLineBorder(Color.LIGHT_GRAY, 2));
        lbl_foto.setHorizontalAlignment(JLabel.CENTER);
        lbl_foto.setCursor(new Cursor(Cursor.HAND_CURSOR));

        //Cargamos la imagen por defecto del icono 
        lbl_foto.setIcon(new ImageIcon(getClass().getResource("/imagenes/imagenPNG.png")));

        //Boton para ingredientes 
        BotonMenuAdministrador btn_ingredientes = new BotonMenuAdministrador("Agregar ingredientes", null, naranja,
                0, 0, fuente_rabbits_pequena);

        gbc_media.gridy = 0;
        panel_medio.add(new JLabel("Cargar imagen"), gbc_media);
        gbc_media.gridy = 1;
        panel_medio.add(lbl_foto, gbc_media);
        gbc_media.gridy = 2;
        gbc_media.insets = new Insets(10, 0, 0, 0);
        panel_medio.add(btn_ingredientes, gbc_media);

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
                    archivo_imagen = selector.getSelectedFile();
                    lbl_foto.setIcon(ClaseImagen.escalarImagenDesdeFile(archivo_imagen, 120, 120));
                }
            }
        });
        
        //En esta parte usamos el action listener en el boton de agregar ingredientes para agregar 
        //los ingredientes que perteneceran a producto de esta manera podemos hacer que valide 
        //todos los demas campos que hay en la ventana
        btn_ingredientes.addActionListener(e -> {
            // Validaciones
            if (txt_nombre.getText().trim().isEmpty() || txt_precio.getText().trim().isEmpty() || 
                combo_tipo.getSelectedIndex() <= 0 || archivo_imagen == null) {
                JOptionPane.showMessageDialog(null, "Complete todos los campos y la imagen.");
                return;
            }
            
            if (!coordinador.validarPrecio(txt_precio.getText())) {
                JOptionPane.showMessageDialog(null, "La cantidad del precio es invalida.");
                return;
            }

            // Creamos el DTO
            ProductoDTO dto_para_registrar = new ProductoDTO();
            dto_para_registrar.setNombre(txt_nombre.getText().trim());
            dto_para_registrar.setPrecio(Double.parseDouble(txt_precio.getText().trim()));

            // Obtener el Enum seleccionado de forma segura
            TipoPlatillo seleccion = (TipoPlatillo) combo_tipo.getSelectedItem();
            // Mapeamos el nombre del Enum original al del DTO
            dto_para_registrar.setTipo_platilo(TipoPlatilloDTO.valueOf(seleccion.name()));

            dto_para_registrar.setRuta_imagen(archivo_imagen.getAbsolutePath());

            // Abrir dialogo
            coordinador.mostrarDialogoIngredientes(this, dto_para_registrar);
        });
        
        btn_volver.addActionListener(e -> coordinador.ventanaMenuProductosAMenuAdmin());
        
        // --- EVENTO DE EDICIÓN EN TIEMPO REAL EN TABLA ---
        modelo_tabla.addTableModelListener(e -> {
                // Solo actua si el cambio fue una actualización de celda
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                int fila_modelo = e.getFirstRow();
                int columna = e.getColumn();

                    // Evitamos procesar columnas que no sean Nombre (1) o Precio (2)
                if (columna == 1 || columna == 2) {

                        // Obtenemos el ID del producto (Columna 5)
                    Long id_producto = (Long) modelo_tabla.getValueAt(fila_modelo, 5);

                        // Obtenemos el nuevo valor ingresado
                    Object nuevo_valor = modelo_tabla.getValueAt(fila_modelo, columna);

                    if (nuevo_valor == null || nuevo_valor.toString().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "El campo no puede estar vacío.");
                        llenarTabla(); 
                        return;
                    }

                    String valor_string = nuevo_valor.toString().trim();

                    if (columna == 1) { 
                        if (!coordinador.validarNombre(valor_string)) {
                            JOptionPane.showMessageDialog(this, "Nombre inválido.");
                            llenarTabla(); 
                            return;
                        }
                            // Llamamos al coordinador para actualizar solo el nombre
                        coordinador.actualizarNombreProducto(id_producto, valor_string);
                    } 
                    else if (columna == 2) { 
                        if (!coordinador.validarPrecio(valor_string)) {
                            JOptionPane.showMessageDialog(this, "Precio inválido.");
                            llenarTabla();
                            return;
                        }
                            // Llamamos al coordinador para actualizar el precio
                        Double precio_nuevo = Double.parseDouble(valor_string);
                        coordinador.actualizarPrecioProducto(id_producto, precio_nuevo);
                    }
                }
            }
        });

        //Este metodo actualiza la tabla con los registros que hay
        llenarTabla();
    }
    
    /**
     * Metodo para llenar la tabla de la ventana con los registros
     * de los productos que hay en la base de datos.
     */
    public void llenarTabla() {
        List<ProductoDTO> lista = coordinador.obtenerListaProductos();
        modelo_tabla.setRowCount(0);
        if (lista == null || lista.isEmpty()) return;

        for (ProductoDTO p : lista) {
            Object[] fila = new Object[]{
                "Ver detalles", p.getNombre(), p.getPrecio(), 
                p.getTipo_platilo(), p.getRuta_imagen(), p.getId()
            };
            modelo_tabla.addRow(fila);
        }
        ocultarColumnaID();
    }
    
    /**
     * Metodo para acultar la columna del ID en la tabla
     * de manera que no sea visible cuando se muestren los datos.
     */
    private void ocultarColumnaID() {
        tabla.getColumnModel().getColumn(5).setMinWidth(0);
        tabla.getColumnModel().getColumn(5).setMaxWidth(0);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(0);
    }
    
    /**
     * Metodo para limpiar los campos que hay 
     * en la tabla de productos.
     */
    public void limpiarCampos() {
        // Limpiar campos de texto
        txt_nombre.setText("");
        txt_precio.setText("");
        if (txt_buscador != null) txt_buscador.setText("");
        combo_tipo.setSelectedIndex(0);

        // Restablecer la imagen por defecto
        lbl_foto.setIcon(new ImageIcon(getClass().getResource("/imagenes/imagenPNG.png")));

        // Limpiar la variable que guarda el archivo seleccionado
        archivo_imagen = null;

        // Devolver el foco al primer campo para agilidad
        txt_nombre.requestFocus();
    }
}
