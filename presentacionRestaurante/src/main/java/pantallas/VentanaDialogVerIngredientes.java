/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import controladorRestaurante.Coordinador;
import dtosDelRestaurante.IngredienteDTOLista;
import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import recursos.*;
import java.util.List;
import javax.swing.event.TableModelEvent;
import javax.swing.table.TableRowSorter;
/**
 *
 * @author josma / RAMSES
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

        // -----------------------TITULO-----------------------------
        // Puedes dinamizar esto pasando el nombre del producto al constructor
        JLabel lbl_titulo = new JLabel("Ingredientes del Producto", SwingConstants.CENTER);
        lbl_titulo.setFont(fuente_titulo);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        cuadro_blanco.add(lbl_titulo, gbc);

        // ---------------LABEL BUSCAR-------------------------------
        JLabel lbl_buscar = new JLabel("Buscar Ingrediente");
        lbl_buscar.setFont(fuente_rabbits_mediana);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        cuadro_blanco.add(lbl_buscar, gbc);

        // ---------------------TXT BUSCADOR-------------------------
        TextFieldPersonalizado txt_buscador = new TextFieldPersonalizado(20);
        txt_buscador.setPreferredSize(new Dimension(0, 40));
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 20, 0);
        cuadro_blanco.add(txt_buscador, gbc);

        // --------------------------TABLA---------------------------
        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        String[] columnas = {"Nombre", "Cantidad", "Medida"};
        
        // Usamos 0 filas iniciales y -1 en hasta_columna para que NADA sea editable
        modelo_tabla = new ModeloTablaEditable(columnas, 0, 1);
        
        tabla = new TablaEstilizada(modelo_tabla, fuente_rabbits_pequena);

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(naranja, 2));
        cuadro_blanco.add(scroll, gbc);

        // ----------------------BOTON VOLVER------------------------
        gbc.gridy = 4;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.NONE; // Para que el boton no se estire a lo ancho
        gbc.insets = new Insets(20, 0, 0, 0);

        BotonMenuAdministrador btn_volver = new BotonMenuAdministrador("Volver", "/imagenes/volverPNG.png", rojo, 25, 25, fuente_botones);
        btn_volver.setPreferredSize(new Dimension(200, 55));
        
        cuadro_blanco.add(btn_volver, gbc);

        // ---------------------AGREGACION AL FONDO---------------------------
        gbc.gridy = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.CENTER;
        panel_fondo.add(cuadro_blanco, gbc);

        //---------------------------EVENTOS----------------------------------
        btn_volver.addActionListener(e -> this.dispose());
        
        // Cargar los datos en la tabla
        llenarTabla();
        
        modelo_tabla.addTableModelListener(e -> {
            // Solo actuamos si el cambio fue una actualizacion en una celda
            if (e.getType() == TableModelEvent.UPDATE) {
                int fila = e.getFirstRow();
                int columna = e.getColumn();

                // Si el cambio fue en la columna "Cantidad"
                if (columna == 1) {
                    try {
                        // Obtener el nuevo valor de la tabla
                        Object nuevoValor = modelo_tabla.getValueAt(fila, columna);
                        Integer nuevaCantidad = Integer.parseInt(nuevoValor.toString());

                        // Obtener el DTO correspondiente de la lista original
                        int filaModelo = tabla.convertRowIndexToModel(fila);
                        IngredienteDTOLista dto_modificado = lista_ingredientes.get(filaModelo);

                        // Actualizar el DTO localmente
                        dto_modificado.setStock(nuevaCantidad); 

                        // Llamar al coordinador para persistir en la Base de datos
                        coordinador.actualizarCantidadIngredienteProducto(dto_modificado.getId(), nuevaCantidad);

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Por favor ingrese un número válido.", "Error de formato", JOptionPane.ERROR_MESSAGE);
                        llenarTabla(); // Recargamos para revertir el texto erroneo
                    }
                }
            }
        });

        // Configurar el Filtro de busqueda
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo_tabla);
        tabla.setRowSorter(sorter);

        txt_buscador.addKeyListener(new KeyAdapter() {
            @Override
            public void keyReleased(KeyEvent e) {
                String texto = txt_buscador.getText().trim();
                if (texto.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    // (?i) hace que ignore mayusculas y minusculas
                    // El 0 es la columna "Nombre" y el 2 es la columna "Medida"
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 0, 2));
                }
            }
        });
        
    }
    
    /**
     * Llenan la tabla de la ver ingredientes mediante 
     * el uso de la lista de la lista de ingredientes tipo DTO.
     */
    public void llenarTabla() {
        // Limpiamos por seguridad
        modelo_tabla.setRowCount(0);

        // Validamos que la lista no sea nula
        if (lista_ingredientes != null) {
            for (IngredienteDTOLista ing : lista_ingredientes) {
                Object[] fila = {
                    ing.getNombre(), // Columna 0: Nombre
                    ing.getStock(),// Columna 1: Cantidad
                    ing.getUnidad_medida()// Columna 2: Medida
                };
                modelo_tabla.addRow(fila);
            }
        }
    }
    
}
