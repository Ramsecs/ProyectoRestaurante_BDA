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
 * Ventana de gestión de productos corregida con soporte de Enums en ComboBox.
 * @author josma / Adaptación Gemini
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
    // Cambio: El ComboBox ahora maneja TipoPlatillo directamente
    private ComboBoxPersonalizado<Object> combo_tipo; 
    private JLabel lbl_foto;

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
        gbc.fill = GridBagConstraints.HORIZONTAL; 
        gbc.weightx = 1.0; 
        gbc.insets = new Insets(2, 0, 2, 0); 

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
        JLabel lbl_buscar = new JLabel("Buscar producto");
        lbl_buscar.setFont(fuente_rabbits_mediana);
        cuadro_blanco.add(lbl_buscar, gbc);

        gbc.gridy = 2;
        txt_buscador = new TextFieldPersonalizado(20);
        txt_buscador.setPreferredSize(new Dimension(0, 35)); 
        cuadro_blanco.add(txt_buscador, gbc);

        //------------------------TABLA (FILA 3)--------------------------------
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
        txt_buscador.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String texto = txt_buscador.getText().trim();
                if (texto.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    // (?i) hace que ignore mayúsculas y minúsculas
                    // Buscamos en la columna 1 (Nombre) y columna 3 (Tipo)
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
                // Convertimos el índice por si hay filtros activos
                int filaModelo = tabla.convertRowIndexToModel(filaVista);

                // Obtenemos el ID del producto
                Long idProducto = (Long) modelo_tabla.getValueAt(filaModelo, 5);

                // Obtenemos el nombre para el título del diálogo
                String nombreProducto = modelo_tabla.getValueAt(filaModelo, 1).toString();

                // Llamamos al coordinador pasando el ID del producto clickeado
                // Este método en el coordinador debe abrir el JDialog que muestra ingredientes
                coordinador.mostrarDialogoIngredientesVista(this, idProducto, nombreProducto);
            }
        }));

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

        // CORRECCIÓN: Preparar opciones con "Seleccionar..." + los valores del Enum
        TipoPlatillo[] enums = TipoPlatillo.values();
        Object[] itemsCombo = new Object[enums.length + 1];
        itemsCombo[0] = "Seleccionar...";
        System.arraycopy(enums, 0, itemsCombo, 1, enums.length);

        combo_tipo = new ComboBoxPersonalizado<>(itemsCombo);
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
        GridBagConstraints gbcMedia = new GridBagConstraints();

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
                    archivo_imagen = selector.getSelectedFile();
                    lbl_foto.setIcon(ClaseImagen.escalarImagenDesdeFile(archivo_imagen, 120, 120));
                }
            }
        });
        
        //En esta parte usamos el action listener en el boton de agregar ingredientes para agregar 
        //los ingredientes que perteneceran a producto de esta manera podemos hacer que valide 
        //todos los demas campos que hay en la ventana
        btn_ingredientes.addActionListener(e -> {
            // 1. Validaciones
            if (txt_nombre.getText().trim().isEmpty() || txt_precio.getText().trim().isEmpty() || 
                combo_tipo.getSelectedIndex() <= 0 || archivo_imagen == null) {
                JOptionPane.showMessageDialog(null, "Complete todos los campos y la imagen.");
                return;
            }
            
            if (!coordinador.validarPrecio(txt_precio.getText())) {
                JOptionPane.showMessageDialog(null, "La cantidad del precio es invalida.");
                return;
            }

            // 2. Creamos el DTO
            ProductoDTO dto_para_registrar = new ProductoDTO();
            dto_para_registrar.setNombre(txt_nombre.getText().trim());
            dto_para_registrar.setPrecio(Double.parseDouble(txt_precio.getText().trim()));

            // CORRECCIÓN: Obtener el Enum seleccionado de forma segura
            TipoPlatillo seleccion = (TipoPlatillo) combo_tipo.getSelectedItem();
            // Mapeamos el nombre del Enum original al del DTO
            dto_para_registrar.setTipo_platilo(TipoPlatilloDTO.valueOf(seleccion.name()));

            dto_para_registrar.setRuta_imagen(archivo_imagen.getAbsolutePath());

            // 3. Abrir diálogo
            coordinador.mostrarDialogoIngredientes(this, dto_para_registrar);
        });
        
        btn_volver.addActionListener(e -> coordinador.ventanaMenuProductosAMenuAdmin());
        
        // --- EVENTO DE EDICIÓN EN TIEMPO REAL EN TABLA ---
        modelo_tabla.addTableModelListener(e -> {
                // Solo actua si el cambio fue una actualización de celda
            if (e.getType() == javax.swing.event.TableModelEvent.UPDATE) {
                int filaModelo = e.getFirstRow();
                int columna = e.getColumn();

                    // Evitamos procesar columnas que no sean Nombre (1) o Precio (2)
                if (columna == 1 || columna == 2) {

                        // Obtenemos el ID del producto (Columna 5)
                    Long idProducto = (Long) modelo_tabla.getValueAt(filaModelo, 5);

                        // Obtenemos el nuevo valor ingresado
                    Object nuevoValor = modelo_tabla.getValueAt(filaModelo, columna);

                    if (nuevoValor == null || nuevoValor.toString().trim().isEmpty()) {
                        JOptionPane.showMessageDialog(this, "El campo no puede estar vacío.");
                        llenarTabla(); 
                        return;
                    }

                    String valorString = nuevoValor.toString().trim();

                    if (columna == 1) { 
                        if (!coordinador.validarNombre(valorString)) {
                            JOptionPane.showMessageDialog(this, "Nombre inválido.");
                            llenarTabla(); 
                            return;
                        }
                            // Llamamos al coordinador para actualizar solo el nombre
                        coordinador.actualizarNombreProducto(idProducto, valorString);
                    } 
                    else if (columna == 2) { 
                        if (!coordinador.validarPrecio(valorString)) {
                            JOptionPane.showMessageDialog(this, "Precio inválido.");
                            llenarTabla();
                            return;
                        }
                            // Llamamos al coordinador para actualizar el precio
                        Double precioNuevo = Double.parseDouble(valorString);
                        coordinador.actualizarPrecioProducto(idProducto, precioNuevo);
                    }
                }
            }
        });

        //Este metodo actualiza la tabla con los registros que hay
        llenarTabla();
    }
    
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

    private void ocultarColumnaID() {
        tabla.getColumnModel().getColumn(5).setMinWidth(0);
        tabla.getColumnModel().getColumn(5).setMaxWidth(0);
        tabla.getColumnModel().getColumn(5).setPreferredWidth(0);
    }
    
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

        // Opcional: Devolver el foco al primer campo para agilidad
        txt_nombre.requestFocus();
    }
}
