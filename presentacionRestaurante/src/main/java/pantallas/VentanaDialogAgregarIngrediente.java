/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import controladorRestaurante.Coordinador;
import dtosDelRestaurante.IngredienteDTOLista;
import dtosDelRestaurante.ProductoIngredienteDTO;
import enumEntidades.UnidadMedida;
import java.awt.*;
import java.util.ArrayList;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import observadorRestaurante.Observador;
import recursos.*;
import java.util.List;
import javax.swing.table.TableRowSorter;

/**
 * @author josma
 */
public class VentanaDialogAgregarIngrediente extends JDialog {

    private final Coordinador coordinador;
    private TablaEstilizada tabla;
    private DefaultTableModel modelo_tabla;
    private final Color naranja = new Color(255, 184, 77);
    private final Color verde = new Color(116, 155, 87);
    private final Color rojo = new Color(188, 55, 30);
    
    private Observador metiche; 

    public void setConexionObservador(Observador metiche) {
        this.metiche = metiche;
    }

    public VentanaDialogAgregarIngrediente(Coordinador coordinador, Frame padre) {
        super(padre, true);
        this.coordinador = coordinador;
        setSize(900, 700);
        setLocationRelativeTo(padre);
        initComponents();
    }

    private void initComponents() {
        PanelFondo panel_fondo = new PanelFondo();
        panel_fondo.setLayout(new GridBagLayout());
        setContentPane(panel_fondo);

        PanelRedondeado cuadro_blanco = new PanelRedondeado(50, Color.WHITE);
        cuadro_blanco.setLayout(new GridBagLayout());
        cuadro_blanco.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));
        cuadro_blanco.setPreferredSize(new Dimension(850, 500));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 0, 5, 0);
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;

        Font fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 32f);
        Font fuente_rabbits_mediana = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 18f);
        Font fuente_rabbits_pequena = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 14f);
        Font fuente_botones = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 18f);

        JLabel lbl_titulo = new JLabel("Agregar Ingrediente a producto", SwingConstants.CENTER);
        lbl_titulo.setFont(fuente_titulo);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 20, 0);
        cuadro_blanco.add(lbl_titulo, gbc);

        JLabel lbl_buscar = new JLabel("Buscar ingrediente");
        lbl_buscar.setFont(fuente_rabbits_mediana);
        gbc.gridy = 1;
        gbc.insets = new Insets(0, 0, 0, 0);
        cuadro_blanco.add(lbl_buscar, gbc);

        TextFieldPersonalizado txt_buscador = new TextFieldPersonalizado(20);
        txt_buscador.setPreferredSize(new Dimension(0, 40));
        gbc.gridy = 2;
        gbc.insets = new Insets(5, 0, 20, 0);
        cuadro_blanco.add(txt_buscador, gbc);

        gbc.gridy = 3;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;

        // Estructura: 0:ID, 1:Nombre, 2:Cantidad, 3:Unidad, 4:Seleccionado
        String[] columnas = {"ID", "Nombre", "Cantidad", "Unidad", "Seleccionado"};
        modelo_tabla = new ModeloTablaEditable(columnas, 0, 4) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Solo ID (0) y Nombre (1) son NO editables
                return column != 0 && column != 1 && super.isCellEditable(row, column);
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) return Boolean.class;
                if (columnIndex == 0) return Long.class;
                return String.class;
            }
        };
        
        tabla = new TablaEstilizada(modelo_tabla, fuente_rabbits_pequena);

        // Configurar Checkbox en columna 4
        tabla.getColumnModel().getColumn(4).setCellRenderer(tabla.getDefaultRenderer(Boolean.class));
        tabla.getColumnModel().getColumn(4).setCellEditor(tabla.getDefaultEditor(Boolean.class));

        configurarRenderizadoUnidad();

        JScrollPane scroll = new JScrollPane(tabla);
        scroll.getViewport().setBackground(Color.WHITE);
        scroll.setBorder(BorderFactory.createLineBorder(naranja, 2));
        cuadro_blanco.add(scroll, gbc);
        
        // --- CONFIGURACIÓN DEL FILTRO ---
        TableRowSorter<DefaultTableModel> sorter = new TableRowSorter<>(modelo_tabla);
        tabla.setRowSorter(sorter);

        txt_buscador.addKeyListener(new java.awt.event.KeyAdapter() {
            @Override
            public void keyReleased(java.awt.event.KeyEvent e) {
                String texto = txt_buscador.getText().trim();
                if (texto.isEmpty()) {
                    sorter.setRowFilter(null);
                } else {
                    // Filtra por columna 1 (Nombre) y columna 3 (Unidad) ignorando mayúsculas/minúsculas
                    sorter.setRowFilter(RowFilter.regexFilter("(?i)" + texto, 1, 3));
                }
            }
        });

        gbc.gridy = 4;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(20, 0, 0, 0);

        JPanel panel_botones = new JPanel(new BorderLayout());
        panel_botones.setOpaque(false);

        BotonMenuAdministrador btn_volver = new BotonMenuAdministrador("Volver", "/imagenes/volverPNG.png", rojo, 25, 25, fuente_botones);
        BotonMenuAdministrador btn_agregar = new BotonMenuAdministrador("Agregar", "/imagenes/aceptarPNG.png", verde, 25, 25, fuente_botones);

        btn_volver.setPreferredSize(new Dimension(180, 50));
        btn_agregar.setPreferredSize(new Dimension(180, 50));

        panel_botones.add(btn_volver, BorderLayout.WEST);
        panel_botones.add(btn_agregar, BorderLayout.EAST);

        cuadro_blanco.add(panel_botones, gbc);

        gbc.gridy = 0;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.CENTER;
        panel_fondo.add(cuadro_blanco, gbc);

        // EVENTOS
        btn_volver.addActionListener(e -> this.dispose());
        
        btn_agregar.addActionListener(e -> {
            List<ProductoIngredienteDTO> seleccionados = new ArrayList<>();

            // 1. Recorremos las filas que el usuario ve actualmente
            for (int i = 0; i < tabla.getRowCount(); i++) {

                // 2. Traducimos el índice de la vista al índice real del modelo de datos
                int model_row = tabla.convertRowIndexToModel(i);

                // 3. Obtenemos el estado del Checkbox usando el índice del modelo
                Boolean esta_seleccionado = (Boolean) modelo_tabla.getValueAt(model_row, 4); 

                if (esta_seleccionado != null && esta_seleccionado) {
                    try {
                        // Extraemos los datos de la fila real
                        Long id_ing = (Long) modelo_tabla.getValueAt(model_row, 0); 
                        String nombre_ing = (String) modelo_tabla.getValueAt(model_row, 1); 
                        Object val_cantidad = modelo_tabla.getValueAt(model_row, 2);

                        // --- VALIDACIONES ---
                        if (val_cantidad == null || val_cantidad.toString().trim().isEmpty()) {
                            JOptionPane.showMessageDialog(this, "Ingresa una cantidad para: " + nombre_ing);
                            return;
                        }

                        String cant_texto = val_cantidad.toString().trim();

                        // Usamos la validación del coordinador que creamos al inicio (Regex \\d+)
                        if (!coordinador.validarCantidad(cant_texto)) { 
                            JOptionPane.showMessageDialog(this, "La cantidad de '" + nombre_ing + "' debe ser un número entero positivo.");
                            return;
                        }

                        Integer cantidad = Integer.parseInt(cant_texto);

                        // Validación lógica de negocio (no puede ser cero)
                        if (cantidad <= 0) {
                            JOptionPane.showMessageDialog(this, "La cantidad de '" + nombre_ing + "' debe ser mayor a cero.");
                            return;
                        }

                        // 4. Si todo está bien, lo agregamos a la lista de seleccionados
                        seleccionados.add(new ProductoIngredienteDTO(id_ing, nombre_ing, cantidad));

                    } catch (NumberFormatException ex) {
                        JOptionPane.showMessageDialog(this, "Error de formato en la cantidad de: " + modelo_tabla.getValueAt(model_row, 1));
                        return;
                    }
                }
            }

            // 5. Verificamos si al menos seleccionó un ingrediente
            if (seleccionados.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No has seleccionado ningún ingrediente para agregar.");
                return;
            }

            // 6. Notificamos al observador y cerramos
            if (metiche != null) {
                metiche.enviarProductoConListaARegistro(seleccionados); 
            }
            dispose();
        });
        
        llenarTabla();
    }

    private void configurarRenderizadoUnidad() {
        JComboBox<UnidadMedida> comboUnidad = new JComboBox<>(UnidadMedida.values());
        // La Unidad está en la columna 3
        TableColumn colUnidad = tabla.getColumnModel().getColumn(3);
        colUnidad.setCellEditor(new DefaultCellEditor(comboUnidad));
    }
    
    public void llenarTabla() {
        modelo_tabla.setRowCount(0);
        List<IngredienteDTOLista> lista = coordinador.obtenerListaIngredientes();

        for (IngredienteDTOLista ing : lista) {
            Object[] fila = {
                ing.getId(),            // Col 0
                ing.getNombre(),        // Col 1
                "",                     // Col 2: Cantidad (Editable)
                ing.getUnidad_medida(), // Col 3: Unidad (Editable)
                false                   // Col 4: Seleccionado
            };
            modelo_tabla.addRow(fila);
        }

        // Ocultar ID
        tabla.getColumnModel().getColumn(0).setMinWidth(0);
        tabla.getColumnModel().getColumn(0).setMaxWidth(0);
        tabla.getColumnModel().getColumn(0).setPreferredWidth(0);
    }
}
