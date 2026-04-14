/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import controladorRestaurante.Coordinador;
import dtosDelRestaurante.ComandaDTO;
import dtosDelRestaurante.ComandaProductoDTO;
import dtosDelRestaurante.ProductoComandaDTO;
import entidadesEnumeradorDTO.TipoPlatilloDTO;
import enumEntidades.EstadoComanda;
import java.awt.*;
import java.util.HashMap;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import recursos.*;
import java.util.List;
import java.util.Map;

/**
 *
 * @author josma
 */
public class VentanaCrearComanda extends JFrame {

    private final Coordinador coordinador;

    private Long id_cliente_seleccionado;
    private Long id_mesa_seleccionada;
    private DefaultTableModel modelo;
    private TablaEstilizada tabla;
    private JTextArea txt_notas; // Para recolectar las notas generales
    private BotonMenuAdministrador btn_buscar_cliente;
    private ComboBoxPersonalizado<String> cmb_tipo_cliente;
    private ComboBoxPersonalizado<TipoPlatilloDTO> cb_tipo_producto;

    private JPanel panel_cartas;
    private CardLayout navegador;
    private final int TOTAL_MESAS = 20;

    private List<ProductoComandaDTO> productos_tabla; //Esto nos servira para poder traer el id de los productos
    private final Map<Long, ComandaProductoDTO> carro_productos = new HashMap<>();
    //Este hash map nos va a ayudar para poder crear la lista de productos
    //Lo hago asi porque como actualizo la tabla se queda solo el ultimo producto seleccionado

    BotonMenuAdministrador btn_volver;
    BotonMenuAdministrador btn_agregar;

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

        //CONFIGURACIÓN DE CARDLAYOUT-------------------------------------------
        navegador = new CardLayout();
        panel_cartas = new JPanel(navegador);
        panel_cartas.setOpaque(false);

        //CREAR LAS DOS VISTAS--------------------------------------------------
        panel_cartas.add(crearVistaSeleccionMesa(), "SELECCION");
        panel_cartas.add(crearVistaFormularioComanda(), "FORMULARIO");

        //EL BOTON INICIA DESEACTIVADO------------------------------------------
        btn_buscar_cliente.setEnabled(false);
        //----------------------------------------------------------------------

        GridBagConstraints gbc_fondo = new GridBagConstraints();
        gbc_fondo.fill = GridBagConstraints.BOTH;
        gbc_fondo.weightx = 1.0;
        gbc_fondo.weighty = 1.0;
        gbc_fondo.insets = new Insets(20, 40, 20, 40);
        panel_principal.add(panel_cartas, gbc_fondo);
    }
//==============================================================================

    private JPanel crearVistaSeleccionMesa() {
        PanelRedondeado panel = new PanelRedondeado(50, Color.WHITE);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(40, 60, 40, 60));

        Font fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 40f);
        Font fuente_mesas = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 22f);
//===================APARTADO DE LAS MESAS AQUI SE CREAN========================
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 40, 0);
        JLabel lbl = new JLabel("Seleccione una Mesa", SwingConstants.CENTER);
        lbl.setFont(fuente_titulo);
        panel.add(lbl, gbc);

        JPanel grilla = new JPanel(new GridLayout(4, 5, 25, 25));
        grilla.setOpaque(false);

        for (int i = 1; i <= TOTAL_MESAS; i++) {
            BotonMenuAdministrador btn_mesa = new BotonMenuAdministrador("Mesa " + i, null, verde, 0, 0, fuente_mesas);
            final long numero_mesa = i;
            btn_mesa.addActionListener(e -> {
                this.id_mesa_seleccionada = numero_mesa;
                navegador.show(panel_cartas, "FORMULARIO");
            });
            grilla.add(btn_mesa);
        }

        gbc.gridy = 1;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        panel.add(grilla, gbc);

        return panel;

    }
//==============================COMANDAS========================================

    private JPanel crearVistaFormularioComanda() {
        PanelRedondeado panel = new PanelRedondeado(50, Color.WHITE);
        panel.setLayout(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 40, 20, 40));

        Font fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 35f);
        Font fuente_rabbits = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 18f);
        Font fuente_botones = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 16f);
        Font fuente_tabla = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 14f);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.weightx = 1.0;
        gbc.insets = new Insets(5, 0, 5, 0);

        // TITULO
        JLabel lbl_titulo = new JLabel("Agregar Comanda", SwingConstants.CENTER);
        lbl_titulo.setFont(fuente_titulo);
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 0, 15, 0);
        panel.add(lbl_titulo, gbc);

        // SECCIÓN CLIENTE
        gbc.gridy = 1;
        JLabel lbl_tipo = new JLabel("Tipo de cliente");
        lbl_tipo.setFont(fuente_rabbits);
        panel.add(lbl_tipo, gbc);

        gbc.gridy = 2;
        String[] tipos = {"Seleccionar...", "Cliente Frecuente", "Cliente General"};
        cmb_tipo_cliente = new ComboBoxPersonalizado<>(tipos);
        cmb_tipo_cliente.setPreferredSize(new Dimension(0, 35));
        panel.add(cmb_tipo_cliente, gbc);

        // Lógica para habilitar/deshabilitar botón de búsqueda según el combo
        gbc.gridy = 3;
        JLabel lbl_nom = new JLabel("Nombre Cliente");
        lbl_nom.setFont(fuente_rabbits);
        panel.add(lbl_nom, gbc);

        gbc.gridy = 4;
        //AQUI ESTA EL BOTON PARA DESPLEGAR EL JDIALOG JOS
        btn_buscar_cliente = new BotonMenuAdministrador("Seleccione un cliente", null, naranja, 0, 0, fuente_rabbits);
        btn_buscar_cliente.setPreferredSize(new Dimension(0, 35));
        cmb_tipo_cliente.addActionListener(e -> {
            boolean frecuente = cmb_tipo_cliente.getSelectedItem().toString().equals("Cliente Frecuente");
            btn_buscar_cliente.setEnabled(frecuente);

            if (frecuente) {
                btn_buscar_cliente.setText("Seleccione un cliente");
                btn_buscar_cliente.setBackground(naranja); // Color original
            } else {
                btn_buscar_cliente.setText("Cliente General");
                btn_buscar_cliente.setBackground(Color.LIGHT_GRAY); // Visualmente desactivado
                this.id_cliente_seleccionado = null; // Limpiamos el ID por seguridad
            }
        });
        panel.add(btn_buscar_cliente, gbc);

        // SECCIÓN PRODUCTO (Filtro)
        gbc.gridy = 5;
        JLabel lbl_prod = new JLabel("Filtrar por categoría");
        lbl_prod.setFont(fuente_rabbits);
        panel.add(lbl_prod, gbc);

        gbc.gridy = 6;
        cb_tipo_producto = new ComboBoxPersonalizado<>(TipoPlatilloDTO.values());
        cb_tipo_producto.setPreferredSize(new Dimension(0, 35));
        panel.add(cb_tipo_producto, gbc);

        // --- CONTENEDOR DE TABLA Y NOTAS (FILA 7) ---
        gbc.gridy = 7;
        gbc.weighty = 1.0;
        gbc.fill = GridBagConstraints.BOTH;
        gbc.insets = new Insets(15, 0, 10, 0);

        JPanel panelMedio = new JPanel(new GridBagLayout());
        panelMedio.setOpaque(false);
        GridBagConstraints gbcMedio = new GridBagConstraints();

        // 1. LA TABLA
        // 1. Definir columnas
        String[] columnas = {"Nombre", "Costo", "Cantidad", "Notas", "Agregar"};

        // 2. Crear el modelo
        modelo = new DefaultTableModel(columnas, 0) {
            @Override
            public boolean isCellEditable(int row, int column) {
                // Permitir editar Cantidad (2) y Agregar (3)
                return column == 2 || column == 3 || column == 4;
            }

            @Override
            public Class<?> getColumnClass(int columnIndex) {
                if (columnIndex == 4) {
                    return Boolean.class;
                }
                return super.getColumnClass(columnIndex);
            }
        };
        /**
         * Aqui estamos creando la parte del checkbox, hay que hacer algunas
         * modificaciones para que la tabla no lo tome como string y si lo
         * dibuje
         */
        tabla = new TablaEstilizada(modelo, fuente_tabla);

        TableCellRenderer booleanRenderer = tabla.getDefaultRenderer(Boolean.class);
        DefaultCellEditor booleanEditor = (DefaultCellEditor) tabla.getDefaultEditor(Boolean.class);
        tabla.getColumnModel().getColumn(4).setCellRenderer(booleanRenderer);
        tabla.getColumnModel().getColumn(4).setCellEditor(booleanEditor);
        if (booleanRenderer instanceof JComponent) {
            ((JComponent) booleanRenderer).setOpaque(false);
        }

        //======================================================================
        JScrollPane scrollTabla = new JScrollPane(tabla);
        scrollTabla.getViewport().setBackground(Color.WHITE);
        scrollTabla.setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(naranja), "Menú de Productos"));

        gbcMedio.gridx = 0;
        gbcMedio.weightx = 0.7;
        gbcMedio.fill = GridBagConstraints.BOTH;
        gbcMedio.weighty = 1.0;
        panelMedio.add(scrollTabla, gbcMedio);

        panel.add(panelMedio, gbc);

        // PANEL DE BOTONES INFERIORES
        JPanel panelBotones = new JPanel(new BorderLayout());
        panelBotones.setOpaque(false);

        btn_volver = new BotonMenuAdministrador("Volver", "/imagenes/volverPNG.png", rojo, 25, 25, fuente_botones);
        btn_volver.setPreferredSize(new Dimension(180, 50));
        

        btn_agregar = new BotonMenuAdministrador("Agregar", "/imagenes/aceptarPNG.png", verde, 25, 25, fuente_botones);
        btn_agregar.setPreferredSize(new Dimension(180, 50));

        panelBotones.add(btn_volver, BorderLayout.WEST);
        panelBotones.add(btn_agregar, BorderLayout.EAST);

        gbc.gridy = 8;
        gbc.weighty = 0;
        gbc.fill = GridBagConstraints.HORIZONTAL;
        panel.add(panelBotones, gbc);
//===============ACTION LISTENER================================================
        btn_buscar_cliente.addActionListener(a -> {
            coordinador.mostrarSelectorCliente(this);
        });

        cb_tipo_producto.addActionListener(e -> {
            TipoPlatilloDTO categoria_seleccionada = (TipoPlatilloDTO) cb_tipo_producto.getSelectedItem();

            System.out.println("Filtrado por" + categoria_seleccionada);
            coordinador.cargarProductosPorCategoria(categoria_seleccionada);
        });

        btn_agregar.addActionListener(e -> {
            //1. Recogemos el DTO con la información de la pantalla
            ComandaDTO nueva_comanda = recolectarDatosComanda();

            int respuesta = JOptionPane.showConfirmDialog(this,
                    "¿Desea registrar la comanda por un total de $" + nueva_comanda.getTotal() + "?",
                    "Confirmar Registro", JOptionPane.YES_NO_OPTION);

            if (respuesta == JOptionPane.YES_OPTION) {
                try {
                    // 3. El coordinador guarda
                    coordinador.guardarComanda(nueva_comanda);
                    coordinador.volverMenuComanda();
                    // 4. Caso de exito
                    JOptionPane.showMessageDialog(this, "Comanda registrada con éxito", "Éxito", JOptionPane.INFORMATION_MESSAGE);

                    limpiarFormulario();

                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(this, "Algo fallo: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
                    ex.printStackTrace();
                }

            }

        });
        modelo.addTableModelListener(e -> {
            int fila = e.getFirstRow();
            int columna = e.getColumn();

            //Si el cambio fue en la columna checkbox (lo hago solo aqui porque al final esta es la que se agrega
            if (fila >= 0 && (columna == 4)) {
                Boolean seleccionado = (Boolean) modelo.getValueAt(fila, 4);
                Long id_producto = productos_tabla.get(fila).getId();

                if (seleccionado != null && seleccionado) {
                    ComandaProductoDTO detalle = new ComandaProductoDTO();
                    detalle.setId_producto(id_producto);
                    detalle.setCantidad(Integer.parseInt(modelo.getValueAt(fila, 2).toString()));
                    detalle.setDetalles(modelo.getValueAt(fila, 3).toString());

                    carro_productos.put(id_producto, detalle);
                } else {
                    carro_productos.remove(id_producto);
                }
            }
        });

        cb_tipo_producto.addActionListener(e -> {
            //1. Guardamos lo que se haya hecho en la categoria anterior
            actualizarCarritoDesdeTabla();
            TipoPlatilloDTO categoria = (TipoPlatilloDTO) cb_tipo_producto.getSelectedItem();
            coordinador.cargarProductosPorCategoria(categoria);
        });
        
        btn_volver.addActionListener(e -> {
            coordinador.volverMenuComanda();
        });
        //TODOS LOS ACTIONS LISTENER TIENEN QUE IR ANTES DE ESTE RETURN JOS=====
        return panel;

    }

    public void cargarTablaProductos(List<ProductoComandaDTO> lista) {
        this.productos_tabla = lista;

        modelo.setRowCount(0);

        for (ProductoComandaDTO producto : lista) {
            Object[] fila = {
                producto.getNombre(),
                producto.getPrecio(),
                1, //Cantidad por defecto
                "", //Vacio aqui van las notas
                false //checkbox desmarcado
            };
            modelo.addRow(fila);
        }
    }

    private ComandaDTO recolectarDatosComanda() {
        // 1. Sincronizamos lo que hay en la pantalla actual al carrito 
        actualizarCarritoDesdeTabla();

        if (this.carro_productos.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Debe seleccionar al menos un producto.");
            return null;
        }

        ComandaDTO comandaDTO = new ComandaDTO();

        // --- Logica de Cliente y Mesero ---
        String tipo_cliente = cmb_tipo_cliente.getSelectedItem().toString();
        if (tipo_cliente.equals("Cliente General")) {
            comandaDTO.setIdCliente(coordinador.obtenerIdClienteGeneral());
        } else if (tipo_cliente.equals("Cliente Frecuente")) {
            if (this.id_cliente_seleccionado == null) {
                JOptionPane.showMessageDialog(this, "Por favor, seleccione un cliente frecuente.");
                return null;
            }
            comandaDTO.setIdCliente(this.id_cliente_seleccionado);
        }

        comandaDTO.setIdMesa(this.id_mesa_seleccionada);
        comandaDTO.setIdMesero(coordinador.geMeseroEnSesion().getId());
        comandaDTO.setEstado(EstadoComanda.ABIERTA);

        // 2. Procesar el Carrito con lo acumulado
        double total_venta = 0;
        for (ComandaProductoDTO detalle : carro_productos.values()) {
            comandaDTO.agregarProducto(detalle);
            // Ahora el precio ya vive dentro del detalle
            total_venta += (detalle.getPrecio() * detalle.getCantidad());
        }

        comandaDTO.setTotal(total_venta);
        return comandaDTO;
    }

    /**
     * Este método extrae lo que está marcado en la tabla actual y lo guarda en
     * el carrito sin borrar lo que ya estaba de categorías anteriores.
     */
    private void actualizarCarritoDesdeTabla() {
        for (int i = 0; i < modelo.getRowCount(); i++) {
            Boolean seleccionado = (Boolean) modelo.getValueAt(i, 4);
            Long idProd = productos_tabla.get(i).getId();

            if (seleccionado != null && seleccionado) {
                ComandaProductoDTO detalle = new ComandaProductoDTO();
                detalle.setId_producto(idProd);
                detalle.setCantidad(Integer.parseInt(modelo.getValueAt(i, 2).toString()));
                detalle.setDetalles(modelo.getValueAt(i, 3).toString());

                // Obtenemos el precio de la columna 1 y lo guardamos en el DTO
                double precio = Double.parseDouble(modelo.getValueAt(i, 1).toString());
                detalle.setPrecio(precio);

                carro_productos.put(idProd, detalle);
            } else {
                // Si el producto está en la tabla pero NO está seleccionado, 
                // nos aseguramos de que no esté en el carrito (por si lo desmarcaron).
                carro_productos.remove(idProd);
            }
        }
    }

    public void setClienteSeleccionado(Long id, String nombre_completo) {
        // Guardamos el ID para cuando se ejecute el boton
        this.id_cliente_seleccionado = id;

        // Seteamos el texto del boton para que el mesero peuda ver el nombre seleccionado
        this.btn_buscar_cliente.setText(nombre_completo);
        this.btn_buscar_cliente.setBackground(verde);

        //checamos que si se este haciendo el proceso (borrar cuando ya no sea necesario)
        System.out.println("Cliente recibido en Ventana Comanda: " + id + " - " + nombre_completo);
    }

    private void limpiarFormulario() {
        // 1. Resetear IDs de selección
        this.id_cliente_seleccionado = null;
        this.id_mesa_seleccionada = null;

        // 2. Limpiar componentes  de la seccion Cliente
        cmb_tipo_cliente.setSelectedIndex(0);
        btn_buscar_cliente.setText("Seleccione un cliente");
        btn_buscar_cliente.setEnabled(false);
        btn_buscar_cliente.setBackground(naranja);

        // 3. Limpiar sección de Productos
        cb_tipo_producto.setSelectedIndex(0);
        modelo.setRowCount(0); // Borra todas las filas de la tabla
        if (productos_tabla != null) {
            productos_tabla.clear(); // Limpia la memoria de IDs
        }

        this.carro_productos.clear();

        // 4. Regresar a la vista de Selección de Mesa
        navegador.show(panel_cartas, "SELECCION");
    }

}
