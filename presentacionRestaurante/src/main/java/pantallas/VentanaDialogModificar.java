package pantallas;

import controladorRestaurante.Coordinador;
import dtosDelRestaurante.ComandaListaDTO;
import dtosDelRestaurante.DetalleComandaDTO;
import dtosDelRestaurante.ProductoDTO;
import entidadesEnumeradorDTO.TipoPlatilloDTO;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import recursos.*;
import java.util.List;
import javax.swing.table.TableCellRenderer;

/**
 * Ventana para modificar una comanda existente. Corregido: Renderizado forzado
 * de CheckBoxes.
 *
 * * @author josma
 */
public class VentanaDialogModificar extends JDialog {

    private final Coordinador coordinador;
    private final Color naranja = new Color(255, 184, 77);
    private final Color verde = new Color(116, 155, 87);
    private final Color rojo = new Color(188, 55, 30);

    private DefaultTableModel modelo_actuales;
    private DefaultTableModel modelo_catalogo;
    private TablaEstilizada tabla_actuales;
    private TablaEstilizada tabla_catalogo;
    private Long id_comanda_seleccionada;
    private ComboBoxPersonalizado<TipoPlatilloDTO> cb_filtro;

    private Map<Integer, DetalleComandaDTO> mapa_actuales = new HashMap<>();
    private Map<Integer, ProductoDTO> mapa_catalogo = new HashMap<>();
    private List<ProductoDTO> listaCatalogoMemoria;
    private Map<Long, DetalleComandaDTO> seleccion_temporal_nuevos = new HashMap<>();

    public VentanaDialogModificar(Coordinador coordinador, JFrame padre) {
        super(padre, true);
        this.coordinador = coordinador;
        setTitle("Modificar Comanda");
        setSize(900, 750);
        setLocationRelativeTo(padre);
        initComponents();
    }

    private void initComponents() {
        //------------- SECCIÓN DEL FONDO --------------------------------------
        PanelFondo panel_fondo = new PanelFondo();
        panel_fondo.setLayout(new GridBagLayout());
        setContentPane(panel_fondo);

        //------------- SECCIÓN DEL PANEL BLANCO -------------------------------
        PanelRedondeado cuadro_blanco = new PanelRedondeado(50, Color.WHITE);
        cuadro_blanco.setLayout(new GridBagLayout());
        cuadro_blanco.setBorder(BorderFactory.createEmptyBorder(15, 25, 15, 25));

        GridBagConstraints gbc_principal = new GridBagConstraints();
        gbc_principal.insets = new Insets(10, 10, 10, 10);
        gbc_principal.fill = GridBagConstraints.BOTH;
        gbc_principal.weightx = 1.0;
        gbc_principal.weighty = 1.0;
        panel_fondo.add(cuadro_blanco, gbc_principal);

        //----------------- FUENTES --------------------------------------------
        Font fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 30f);
        Font fuente_rabbits = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 16f);
        Font fuente_tabla = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 14f);
        Font fuente_botones = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 15f);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0);

        // 1. TÍTULO------------------------------------------------------------
        JLabel lbl_titulo = new JLabel("Modificar Comanda", SwingConstants.CENTER);
        lbl_titulo.setFont(fuente_titulo);
        gbc.gridy = 0;
        cuadro_blanco.add(lbl_titulo, gbc);

        // 2. TABLA PRODUCTOS ACTUALES------------------------------------------
        gbc.gridy = 1;
        JLabel lbl_actuales = new JLabel("Productos actuales (Edite cantidad o notas):");
        lbl_actuales.setFont(fuente_rabbits);
        cuadro_blanco.add(lbl_actuales, gbc);

        gbc.gridy = 2;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.BOTH;
        String[] col_actuales = {"Producto", "Cantidad", "Notas", "Eliminar"};
        modelo_actuales = new DefaultTableModel(col_actuales, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return c > 0;
            }

            @Override
            public Class<?> getColumnClass(int c) {
                return (c == 3) ? Boolean.class : Object.class;
            }
        };
        tabla_actuales = new TablaEstilizada(modelo_actuales, fuente_tabla);

        // --- INICIO CORRECCIÓN CHECKBOX ACTUALES ---
        TableCellRenderer checkRendererAct = tabla_actuales.getDefaultRenderer(Boolean.class);
        DefaultCellEditor checkEditorAct = (DefaultCellEditor) tabla_actuales.getDefaultEditor(Boolean.class);
        tabla_actuales.getColumnModel().getColumn(3).setCellRenderer(checkRendererAct);
        tabla_actuales.getColumnModel().getColumn(3).setCellEditor(checkEditorAct);
        if (checkRendererAct instanceof JComponent) {
            ((JComponent) checkRendererAct).setOpaque(false);
        }
        // --- FIN CORRECCIÓN ---

        JScrollPane scroll_actuales = new JScrollPane(tabla_actuales);
        scroll_actuales.getViewport().setBackground(Color.WHITE);
        cuadro_blanco.add(scroll_actuales, gbc);

        // 3. BUSCADOR / FILTRO-------------------------------------------------
        gbc.gridy = 3;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        JLabel lbl_filtro = new JLabel("Agregar nuevos productos:");
        lbl_filtro.setFont(fuente_rabbits);
        cuadro_blanco.add(lbl_filtro, gbc);

        gbc.gridy = 4;
        cb_filtro = new ComboBoxPersonalizado<>(TipoPlatilloDTO.values());
        cb_filtro.setPreferredSize(new Dimension(0, 35));
        cuadro_blanco.add(cb_filtro, gbc);

        // 4. TABLA CATÁLOGO----------------------------------------------------
        gbc.gridy = 5;
        gbc.weighty = 0.4;
        gbc.fill = GridBagConstraints.BOTH;
        String[] col_catalogo = {"Producto", "Precio", "Cantidad", "Notas", "Agregar"};
        modelo_catalogo = new DefaultTableModel(col_catalogo, 0) {
            @Override
            public boolean isCellEditable(int r, int c) {
                return c >= 2;
            }

            @Override
            public Class<?> getColumnClass(int c) {
                return (c == 4) ? Boolean.class : Object.class;
            }
        };
        tabla_catalogo = new TablaEstilizada(modelo_catalogo, fuente_tabla);

        TableCellRenderer checkRendererCat = tabla_catalogo.getDefaultRenderer(Boolean.class);
        DefaultCellEditor checkEditorCat = (DefaultCellEditor) tabla_catalogo.getDefaultEditor(Boolean.class);
        tabla_catalogo.getColumnModel().getColumn(4).setCellRenderer(checkRendererCat);
        tabla_catalogo.getColumnModel().getColumn(4).setCellEditor(checkEditorCat);
        if (checkRendererCat instanceof JComponent) {
            ((JComponent) checkRendererCat).setOpaque(false);
        }

        JScrollPane scroll_catalogo = new JScrollPane(tabla_catalogo);
        scroll_catalogo.getViewport().setBackground(Color.WHITE);
        cuadro_blanco.add(scroll_catalogo, gbc);

        // 6. BOTONES-----------------------------------------------------------
        gbc.gridy = 6;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(15, 0, 5, 0);

        JPanel panel_botones = new JPanel(new BorderLayout());
        panel_botones.setOpaque(false);

        BotonMenuAdministrador btn_volver = new BotonMenuAdministrador("Volver", "/imagenes/volverPNG.png", rojo, 20, 20, fuente_botones);
        btn_volver.setPreferredSize(new Dimension(180, 45));
        btn_volver.addActionListener(e -> dispose());

        BotonMenuAdministrador btn_aplicar = new BotonMenuAdministrador("Aplicar Cambios", "/imagenes/aceptarPNG.png", verde, 20, 20, fuente_botones);
        btn_aplicar.setPreferredSize(new Dimension(200, 45));

        btn_aplicar.addActionListener(e -> {
            if (tabla_actuales.isEditing()) {
                tabla_actuales.getCellEditor().stopCellEditing();
            }
            if (tabla_catalogo.isEditing()) {
                tabla_catalogo.getCellEditor().stopCellEditing();
            }

            coordinador.guardarCambiosComanda(
                    id_comanda_seleccionada,
                    obtenerProductosAModificar(),
                    obtenerProductosAEliminar(),
                    obtenerProductosNuevos()
            );
            dispose();
        });

        panel_botones.add(btn_volver, BorderLayout.WEST);
        panel_botones.add(btn_aplicar, BorderLayout.EAST);
        cuadro_blanco.add(panel_botones, gbc);

        cb_filtro.addActionListener(e -> {
            guardarEstadoTablaCatalogo();
            TipoPlatilloDTO seleccionado = (TipoPlatilloDTO) cb_filtro.getSelectedItem();
            List<ProductoDTO> filtrados = new ArrayList<>();
            if (listaCatalogoMemoria != null) {
                for (ProductoDTO p : listaCatalogoMemoria) {
                    if (p.getTipo_platilo().equals(seleccionado)) {
                        filtrados.add(p);
                    }
                }
            }
            refrescarTablaCatalogo(filtrados);
        });
    }

    public void cargarDatosEdicion(ComandaListaDTO resumen, List<DetalleComandaDTO> detalles, List<ProductoDTO> catalogo) {
        this.id_comanda_seleccionada = resumen.getId();
        this.listaCatalogoMemoria = catalogo;
        this.seleccion_temporal_nuevos.clear();

        modelo_actuales.setRowCount(0);
        mapa_actuales.clear();
        for (int i = 0; i < detalles.size(); i++) {
            DetalleComandaDTO d = detalles.get(i);
            mapa_actuales.put(i, d);
            modelo_actuales.addRow(new Object[]{d.getNombre_producto(), d.getCantidad(), d.getNotas(), false});
        }

        TipoPlatilloDTO seleccionado = (TipoPlatilloDTO) cb_filtro.getSelectedItem();
        List<ProductoDTO> inicial = new ArrayList<>();
        if (catalogo != null) {
            for (ProductoDTO p : catalogo) {
                if (p.getTipo_platilo().equals(seleccionado)) {
                    inicial.add(p);
                }
            }
        }
        refrescarTablaCatalogo(inicial);
    }

    private List<DetalleComandaDTO> obtenerProductosAModificar() {
        List<DetalleComandaDTO> lista = new ArrayList<>();
        for (int i = 0; i < tabla_actuales.getRowCount(); i++) {
            int modelRow = tabla_actuales.convertRowIndexToModel(i);
            boolean eliminar = (boolean) modelo_actuales.getValueAt(modelRow, 3);
            if (!eliminar) {
                DetalleComandaDTO dto = mapa_actuales.get(modelRow);
                Object notaObj = modelo_actuales.getValueAt(modelRow, 2);
                String nota = (notaObj == null) ? "" : notaObj.toString();
                dto.setCantidad(Integer.parseInt(modelo_actuales.getValueAt(modelRow, 1).toString()));
                dto.setNotas(nota);
                lista.add(dto);
            }
        }
        return lista;
    }

    private List<DetalleComandaDTO> obtenerProductosAEliminar() {
        List<DetalleComandaDTO> lista = new ArrayList<>();
        for (int i = 0; i < tabla_actuales.getRowCount(); i++) {
            int modelRow = tabla_actuales.convertRowIndexToModel(i);
            boolean eliminar = (boolean) modelo_actuales.getValueAt(modelRow, 3);
            if (eliminar) {
                lista.add(mapa_actuales.get(modelRow));
            }
        }
        return lista;
    }

    private List<DetalleComandaDTO> obtenerProductosNuevos() {
        guardarEstadoTablaCatalogo();
        return new ArrayList<>(seleccion_temporal_nuevos.values());
    }

    private void refrescarTablaCatalogo(List<ProductoDTO> productos) {
        modelo_catalogo.setRowCount(0);
        mapa_catalogo.clear();
        for (int i = 0; i < productos.size(); i++) {
            ProductoDTO p = productos.get(i);
            mapa_catalogo.put(i, p);

            if (seleccion_temporal_nuevos.containsKey(p.getId())) {
                DetalleComandaDTO prev = seleccion_temporal_nuevos.get(p.getId());
                modelo_catalogo.addRow(new Object[]{p.getNombre(), p.getPrecio(), prev.getCantidad(), prev.getNotas(), true});
            } else {
                modelo_catalogo.addRow(new Object[]{p.getNombre(), p.getPrecio(), 1, "", false});
            }
        }
    }

    private void guardarEstadoTablaCatalogo() {
        for (int i = 0; i < modelo_catalogo.getRowCount(); i++) {
            int modelRow = tabla_catalogo.convertRowIndexToModel(i);
            boolean agregar = (boolean) modelo_catalogo.getValueAt(modelRow, 4);
            ProductoDTO p = mapa_catalogo.get(modelRow);

            if (agregar) {
                DetalleComandaDTO temp = new DetalleComandaDTO();
                temp.setId_producto(p.getId());
                temp.setNombre_producto(p.getNombre());
                temp.setPrecio_unitario(p.getPrecio());
                temp.setCantidad(Integer.parseInt(modelo_catalogo.getValueAt(modelRow, 2).toString()));
                temp.setNotas(modelo_catalogo.getValueAt(modelRow, 3).toString());
                seleccion_temporal_nuevos.put(p.getId(), temp);
            } else {
                seleccion_temporal_nuevos.remove(p.getId());
            }
        }
    }
}
