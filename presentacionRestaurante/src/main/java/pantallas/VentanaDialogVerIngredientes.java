/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import controladorRestaurante.Coordinador;
import dtosDelRestaurante.IngredienteDTOLista;
import dtosDelRestaurante.ProductoIngredienteDTO;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import recursos.*;
import java.util.List;
import javax.swing.table.TableRowSorter;
/**
 *
 * @author josma
 */
public class VentanaDialogVerIngredientes extends JDialog {
    private final Coordinador coordinador;
    private TablaEstilizada tabla;
    private DefaultTableModel modelo_tabla;
    private final Color naranja = new Color(255, 184, 77);
    private final Color rojo = new Color(188, 55, 30);
    
    private List<IngredienteDTOLista> lista_ingredientes;
        
    public VentanaDialogVerIngredientes(Coordinador coordinador, JFrame padre, boolean valor, List<IngredienteDTOLista> detalles) {
        super(padre, valor);
        this.coordinador = coordinador;
        this.lista_ingredientes = detalles;
        setSize(900, 700);
        setLocationRelativeTo(padre);
        initComponents();
    }
    
    private void initComponents() {
        //-------------SECCION DEL FONDO--------------------------------------
        PanelFondo panel_fondo = new PanelFondo();
        panel_fondo.setLayout(new GridBagLayout());
        setContentPane(panel_fondo);

        //----------------------PANEL BLANCO----------------------------------
        PanelRedondeado cuadro_blanco = new PanelRedondeado(50, Color.WHITE);
        cuadro_blanco.setLayout(new GridBagLayout());
        cuadro_blanco.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        cuadro_blanco.setPreferredSize(new Dimension(850, 550)); 

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        // -------------------------FUENTES-----------------------------------
        Font fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 32f);
        Font fuente_rabbits_mediana = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 18f);
        Font fuente_rabbits_pequena = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 14f);
        Font fuente_botones = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 18f);

        // -----------------------TITULO (Fila 0)-----------------------------
        // Puedes dinamizar esto pasando el nombre del producto al constructor
        JLabel lbl_titulo = new JLabel("Ingredientes del Producto", SwingConstants.CENTER);
        lbl_titulo.setFont(fuente_titulo);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        cuadro_blanco.add(lbl_titulo, gbc);

        // ---------------LABEL BUSCAR (Fila 1)-------------------------------
        JLabel lbl_buscar = new JLabel("Buscar Ingrediente");
        lbl_buscar.setFont(fuente_rabbits_mediana);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        cuadro_blanco.add(lbl_buscar, gbc);

        // ---------------------TXT BUSCADOR (Fila 2)-------------------------
        TextFieldPersonalizado txt_buscador = new TextFieldPersonalizado(20);
        txt_buscador.setPreferredSize(new Dimension(0, 40));
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 20, 0);
        cuadro_blanco.add(txt_buscador, gbc);

        // --------------------------TABLA (Fila 3)---------------------------
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        String[] columnas = {"Nombre", "Cantidad", "Medida"};
        
        // Usamos 0 filas iniciales y -1 en hasta_columna para que NADA sea editable
        modelo_tabla = new ModeloTablaEditable(columnas, 0, -1);
        
        tabla = new TablaEstilizada(modelo_tabla, fuente_rabbits_pequena);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(naranja, 2));
        cuadro_blanco.add(scroll, gbc);

        // ----------------------BOTON VOLVER (Fila 4)------------------------
        gbc.gridy = 4;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE; // Para que el botón no se estire a lo ancho
        gbc.insets = new Insets(20, 0, 0, 0);

        BotonMenuAdministrador btn_volver = new BotonMenuAdministrador("Volver", "/imagenes/volverPNG.png", rojo, 25, 25, fuente_botones);
        btn_volver.setPreferredSize(new Dimension(200, 55));
        
        cuadro_blanco.add(btn_volver, gbc);

        // ---------------------AGREGACIÓN AL FONDO---------------------------
        gbc.gridy = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.CENTER;
        panel_fondo.add(cuadro_blanco, gbc);

        //---------------------------EVENTOS----------------------------------
        btn_volver.addActionListener(e -> this.dispose());
        
        // 1. Cargar los datos en la tabla
        llenarTabla();

        // 2. Configurar el Filtro de búsqueda
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo_tabla);
        tabla.setRowSorter(sorter);

        txt_buscador.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String texto = txt_buscador.getText().trim();
                if (texto.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    // (?i) hace que ignore mayúsculas y minúsculas
                    // El 0 es la columna "Nombre" y el 2 es la columna "Medida"
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 0, 2));
                }
            }
        });
        
    }
    
    public void llenarTabla() {
        // 1. Limpiamos por seguridad
        modelo_tabla.setRowCount(0);

        // 2. Validamos que la lista no sea nula
        if (lista_ingredientes != null) {
            for (IngredienteDTOLista ing : lista_ingredientes) {
                Object[] fila = {
                    ing.getNombre(), // Columna 0: Nombre
                    ing.getStock(),// Columna 1: Cantidad
                    ing.getUnidad_medida()// Columna 2: Medida (puedes traerlo del DTO si lo tienes)
                };
                modelo_tabla.addRow(fila);
            }
        }
    }
    
}
