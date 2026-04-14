/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package pantallas;

import controladorRestaurante.Coordinador;
import dtosDelRestaurante.ClienteBusquedaDTO;
import dtosDelRestaurante.ClienteDTO;
import java.awt.*;
import java.io.*;
import java.util.List;
import java.time.LocalDate;
import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.TableModelEvent;
import javax.swing.table.*;
import observadorRestaurante.Observador;
import recursos.*;

/**
 *
 * @author josma
 */
public class VentanaMenuCliente extends JFrame {

    private final Coordinador coordinador;
    //Observador 
    private Observador metiche;

    //Enchufe  o puerta para que nuestro observador pueda funcionar, es un setter de Intefaz
    // es como decir que la ventana es el radio y el observador la frecuencia
    //la ventana puede estar activa, pero si no esta vinculada con el observador nadie
    //escucha lo que grita. El this.metiche es la variable que guarda ese alguien 
    public void setConexionObservador(Observador metiche) {
        this.metiche = metiche;
    }
    
    private List<ClienteBusquedaDTO> lista_clientes_actual;
    private ClienteDTO clienteDTO;
    private final Color naranja = new Color(255, 184, 77);
    private final Color verde = new Color(116, 155, 87);
    private final Color rojo = new Color(188, 55, 30);
    private DefaultTableModel modelo_tabla;
    private TablaEstilizada tabla;

    private TextFieldPersonalizado txt_nombre;
    private TextFieldPersonalizado txt_apellido_paterno;
    private TextFieldPersonalizado txt_apellido_materno;
    private TextFieldPersonalizado txt_correo;
    private TextFieldPersonalizado txt_telefono;
    private TextFieldPersonalizado txt_buscador;

    public VentanaMenuCliente(Coordinador coordinador) {
        this.coordinador = coordinador;
        this.clienteDTO = new ClienteDTO();

        //CONFIGURACION BASE----------------------------------------------------
        setTitle("Gestion de Clientes - Sistema Restaurante");
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
        gbc.fill = GridBagConstraints.HORIZONTAL; // Que ocupen todo el ancho
        gbc.weightx = 1.0; // Factor de expansión horizontal
        gbc.insets = new Insets(2, 0, 2, 0); // Espaciado vertical entre filas

        //----------------------------FUENTES-----------------------------------
        Font fuente_titulo = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 32f);
        Font fuente_botones = GestorFuentes.obtenerFuente("Agbalumo-Regular.ttf", 16f);
        Font fuente_rabbits_mediana = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 18f);
        Font fuente_rabbits_pequena = GestorFuentes.obtenerFuente("Rabbits-Bro.ttf", 14f);

        //--------------------------TITULO LABEL-------------------------------
        JLabel lbl_titulo = new JLabel("Menu de Cliente", SwingConstants.CENTER);
        lbl_titulo.setFont(fuente_titulo);
        gbc.gridy = 0;
        gbc.weighty = 0.02;
        cuadro_blanco.add(lbl_titulo, gbc);

        //-------------------------BUSCADOR LABEL y TXTFIELD-------------------------------
        gbc.gridy = 1;
        gbc.weighty = 0;
        JLabel lbl_buscar = new JLabel("Buscar cliente");
        lbl_buscar.setFont(fuente_rabbits_mediana);
        cuadro_blanco.add(lbl_buscar, gbc);

        gbc.gridy = 2;
        txt_buscador = new TextFieldPersonalizado(20);
        txt_buscador.setPreferredSize(new Dimension(0, 35)); // Altura fija
        cuadro_blanco.add(txt_buscador, gbc);

        //----------------------------TABLA-------------------------------------
        gbc.gridy = 3;
        gbc.weighty = 0.3;
        gbc.fill = GridBagConstraints.BOTH;
        // Definimos las columnas que va a contener la tabla
        String[] columnas = {
            "Nombre", "Apellido Paterno", "Apellido Materno", "Correo",
            "Teléfono", "Visitas", "Puntos", "Total Acumulado"
        };

        // Ahora implementamos ModeloTablaEditable, para permitir que datos se van a editar
        modelo_tabla = new ModeloTablaEditable(columnas, 0, 4);

        tabla = new TablaEstilizada(modelo_tabla, fuente_rabbits_pequena);
        JScrollPane scroll = new JScrollPane(tabla);
        scroll.setPreferredSize(new Dimension(0, 150));
        scroll.setBorder(BorderFactory.createEmptyBorder());
        scroll.getViewport().setBackground(Color.WHITE);

        cuadro_blanco.add(scroll, gbc);

        //======================================================================
        //----------------SECCION PARA AGREGAR AL CLIENTE (panel)---------------
        PanelBordePunteado panel_agregar = new PanelBordePunteado();
        panel_agregar.setLayout(new GridLayout(0, 2, 10, 2));

        JLabel lbl_agregar = new JLabel("Registrar Cliente");
        lbl_agregar.setFont(fuente_rabbits_mediana);
        panel_agregar.add(lbl_agregar);
        panel_agregar.add(new JLabel(""));//ESTO ES UN ESPACIO VACIO

        //Agregamos los campos (etiqueta- campo) para que  esten un poco más compactos
        JLabel lbl_nombre = new JLabel("Nombre:");
        lbl_nombre.setFont(fuente_rabbits_pequena);
        panel_agregar.add(lbl_nombre);
        txt_nombre = new TextFieldPersonalizado(10);
        panel_agregar.add(txt_nombre);

        JLabel lbl_apelldio_paterno = new JLabel("Apellido Paterno:");
        lbl_apelldio_paterno.setFont(fuente_rabbits_pequena);
        panel_agregar.add(lbl_apelldio_paterno);
        txt_apellido_paterno = new TextFieldPersonalizado(10);
        panel_agregar.add(txt_apellido_paterno);

        JLabel lbl_apellido_materno = new JLabel("Apellido Materno:");
        lbl_apellido_materno.setFont(fuente_rabbits_pequena);
        panel_agregar.add(lbl_apellido_materno);
        txt_apellido_materno = new TextFieldPersonalizado(10);
        panel_agregar.add(txt_apellido_materno);

        JLabel lbl_correo = new JLabel("Correo (Opcional):");
        lbl_correo.setFont(fuente_rabbits_pequena);
        panel_agregar.add(lbl_correo);
        txt_correo = new TextFieldPersonalizado(10);
        panel_agregar.add(txt_correo);

        JLabel lbl_telefono = new JLabel("Telefono:");
        lbl_telefono.setFont(fuente_rabbits_pequena);
        panel_agregar.add(lbl_telefono);
        txt_telefono = new TextFieldPersonalizado(10);
        panel_agregar.add(txt_telefono);

        gbc.gridy = 4;
        gbc.weighty = 0.2;
        cuadro_blanco.add(panel_agregar, gbc);

        //-----------------SECCION PARA LOS BOTONES-----------------------------
        gbc.gridy = 5;
        gbc.weighty = 0.1;
        JPanel panel_boton = new JPanel(new BorderLayout());
        panel_boton.setOpaque(false);

        BotonMenuAdministrador btn_volver = new BotonMenuAdministrador("Volver", "/imagenes/volverPNG.png", rojo, 30, 30, fuente_botones);
        BotonMenuAdministrador btn_agregar = new BotonMenuAdministrador("Agregar", "/imagenes/registrarPNG.png", verde, 30, 30, fuente_botones);

        panel_boton.add(btn_volver, BorderLayout.WEST);
        panel_boton.add(btn_agregar, BorderLayout.EAST);
        btn_volver.setPreferredSize(new Dimension(200, 60)); // 200 de ancho, 60 de alto
        btn_agregar.setPreferredSize(new Dimension(200, 60));

        cuadro_blanco.add(panel_boton, gbc);

        // CONFIGURACION PARA EL CUADRO BLANCO DENTRO DEL FONDO
        GridBagConstraints gbc_fondo = new GridBagConstraints();
        gbc_fondo.gridx = 0;
        gbc_fondo.gridy = 0;
        gbc_fondo.weightx = 1.0; // Ocupa el ancho disponible
        gbc_fondo.weighty = 1.0; // Ocupa el alto disponible
        gbc_fondo.fill = GridBagConstraints.BOTH; // Se estira en ambas direcciones

// Insets para que el cuadro blanco no toque los bordes de la ventana
        gbc_fondo.insets = new Insets(40, 60, 40, 60);

        panel_fondo.add(cuadro_blanco, gbc_fondo);

//==============================================ACTIONS LISTENER================        
        btn_volver.addActionListener(a -> {
            coordinador.regresarMenuMesero();
        });

        btn_agregar.addActionListener(a -> {
            if (txt_nombre.getText().trim().isEmpty() || txt_apellido_paterno.getText().trim().isEmpty()
                    || txt_apellido_materno.getText().trim().isEmpty()
                    || txt_telefono.getText().trim().isEmpty()) {

                JOptionPane.showMessageDialog(null, "Los campos no pueden ser nulos");
                return;
            }

            if (txt_correo.getText().trim().isEmpty()) {
                clienteDTO.setCorreo(null);
            } else {
                clienteDTO.setCorreo(txt_correo.getText());
            }
            clienteDTO.setNombre(txt_nombre.getText());
            clienteDTO.setApellido_paterno(txt_apellido_paterno.getText());
            clienteDTO.setApellido_materno(txt_apellido_materno.getText());
            clienteDTO.setTelefono(txt_telefono.getText());
            clienteDTO.setFecha_registro(LocalDate.now());

            coordinador.agregarClienteFrecuente(clienteDTO);
            limpiarCampos();
            //Refrescamos la tabla para ver al nuevo cliente
            coordinador.buscarClientes("");
        });

//============================Busqueda tiempo real==============================
        txt_buscador.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                buscar();
            }

            @Override 
            public void removeUpdate(DocumentEvent e) {
                buscar();
            }
 
            @Override 
            public void changedUpdate(DocumentEvent e) {
                buscar();
            }
            
            /**
             * Usando este metodo se busca a un cliente mediante el uso del filtro que 
             * se obtiene del campo de texto que hay en la pantalla
             */
            private void buscar() {
                String filtro = txt_buscador.getText().trim();
                coordinador.buscarClientes(filtro);
            }
        });

//==================MODIFICACION================================================
// Escucha cambios en las celdas de la tabla
        modelo_tabla.addTableModelListener(e -> {
            // Solo actuamos si el evento es una ACTUALIZACION
            if (e.getType() == TableModelEvent.UPDATE) {
                int fila = e.getFirstRow();
                int columna = e.getColumn();

                //SOLO SE EDITA SI ESTA DENTRO DE LAS PERMITIDAS
                if (columna >= 0 && columna <= 4) {
                    ejecutarActualizacionDesdeTabla(fila);
                }
            }
        });
    }

    /**
     * Mediante este metodo se actualiza la tabla de clientes con la 
     * lista de cleintes que obtiene en sus parametros.
     * @param lista 
     */
    public void actualizarTabla(List<ClienteBusquedaDTO> lista) {
        this.lista_clientes_actual = lista;
        // Limpiamos la tabla
        modelo_tabla.setRowCount(0);
        // Recorremos la lista de DTOs que nos mando el BO por medio del cordi

        if (lista == null) {
            return;
        }

        // 2. Recorremos la lista de DTOs
        for (ClienteBusquedaDTO dto : lista) {

            // --- FILTRO PARA OCULTAR CLIENTE GENERAL ---
            // Usamos equalsIgnoreCase para evitar problemas con mayúsculas/minúsculas
            boolean esGeneral = dto.getNombre().equalsIgnoreCase("Cliente")
                    && dto.getApellido_paterno().equalsIgnoreCase("Frecuente");

            if (esGeneral) {
                continue; // Si es el general, se salta el addRow y va al siguiente
            }

            // Si no es el general, se agrega a la tabla con todas sus columnas
            Object[] fila = {
                dto.getNombre(),
                dto.getApellido_paterno(),
                dto.getApellido_materno(),
                dto.getCorreo(),
                dto.getTelefono(),
                dto.getVisitas(), // Long
                dto.getPuntos(), // Integer
                dto.getTotal_acumulado(), // Double
            };
            modelo_tabla.addRow(fila);
        }
    }

    /**
     * Mediante este metodo se limpian los campos 
     * en la tabla que se muestra en la pantalla de clientes.
     */
    public void limpiarCampos() {
        txt_nombre.setText("");
        txt_apellido_paterno.setText("");
        txt_apellido_materno.setText("");
        txt_correo.setText("");
        txt_telefono.setText("");
        txt_nombre.requestFocus();
    }

    /**
     * Mediante este metodo se actualiza la tabla y la informacion 
     * cuando el usuario modifica la informacion dentro.
     * 
     * @param fila 
     */
    private void ejecutarActualizacionDesdeTabla(int fila) {

        //PRIMERO VALIDACIONES =============================
        // Extraer los datos de la fila editada
        String nombre = modelo_tabla.getValueAt(fila, 0).toString().trim();
        String paterno_apellido = modelo_tabla.getValueAt(fila, 1).toString().trim();
        String materno_apellido = modelo_tabla.getValueAt(fila, 2).toString().trim();
        String correo = modelo_tabla.getValueAt(fila, 3).toString().trim();
        String telefono = modelo_tabla.getValueAt(fila, 4).toString().trim();

        // --- SECCIÓN DE VALIDACIONES ---
        if (nombre.isEmpty() || paterno_apellido.isEmpty() || materno_apellido.isEmpty() || telefono.isEmpty()) {
            JOptionPane.showMessageDialog(this, "No puede haber campos obligatorios vacíos", "Validación", JOptionPane.WARNING_MESSAGE);
            notificarError(); // Refresca la tabla para revertir el cambio visual
            return;
        }
        
        //VALIDACION DEL NOMBRE DEL CLIENTE
        if (!coordinador.validarNombre(nombre)) {
            JOptionPane.showMessageDialog(this, "El nombre esta mal escrito.");
            notificarError();
            return;
        }
        
        //VALIDACION DEL APELLIDO PATERNO
        if (!coordinador.validarApellidos(paterno_apellido)) {
            JOptionPane.showMessageDialog(this, "El apellido paterno esta mal escrito.");
            notificarError();
            return;
        }
        
        //VALIDACION DEL APELLIDO MATERNO
        if (!coordinador.validarApellidos(materno_apellido)) {
            JOptionPane.showMessageDialog(this, "El apellido materno esta mal escrito.");
            notificarError();
            return;
        }
        
        //VALIDACION DEL CORREO PARA QUE LO ACEPTE NULL
        String correo_final = null; 
        if (!correo.isEmpty()) {
            if (!coordinador.validarCorreo(correo)) {
                JOptionPane.showMessageDialog(this, "El formato del correo no es válido");
                notificarError();
                return;
            }
            correo_final = correo;
        }

        //VALIDACION DEL TELEFONO
        if (!coordinador.validarTelefono(telefono)) {
            JOptionPane.showMessageDialog(this, "El teléfono debe contener exactamente 10 dígitos numéricos");
            notificarError();
            return;
        }

        //======================================================================
        // Buscamos el DTO original en la lista que creamos para usarla como tipo espejo
        //fue como la manera más segura que encontre
        ClienteBusquedaDTO cliente_original = lista_clientes_actual.get(fila);
        Long id_real = cliente_original.getId();

        // Creamos el DTO con los nuevos datos de la tabla
        ClienteBusquedaDTO cliente_editado = new ClienteBusquedaDTO();
        cliente_editado.setId(id_real); // Le pasamos el ID que rescatamos de la lista

            cliente_editado.setNombre(nombre);
            cliente_editado.setApellido_paterno(paterno_apellido);
            cliente_editado.setApellido_materno(materno_apellido);
            cliente_editado.setCorreo(correo_final);
            cliente_editado.setTelefono(telefono);

            // Notificamos al observador para persistir en la base de datos
            if (this.metiche != null) {
                this.metiche.actualizar_empleado(cliente_editado);
            }

            System.out.println("Actualización exitosa para el cliente ID: " + id_real);
        } else {
            JOptionPane.showMessageDialog(this, "Error de sincronización: No se encontró el registro original.");
            notificarError();
        }
    }
    
    
    /**
     * Metodo para reevertir cambios visuales dentro de la tabla en clientes.
     */
    private void notificarError(){
        if (coordinador != null) {
            coordinador.buscarClientes(txt_buscador.getText().trim());
        }
    }
}
