/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

import java.awt.Component;
import java.awt.MediaTracker;
import java.util.HashMap;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author josma
 */
public class ImagenTablaRender extends DefaultTableCellRenderer {

    // Aquí guardaremos las imágenes ya escaladas para no repetir el trabajo
    //Con esto estamos evitando que la ventana vaya lentisima
    private final Map<String, ImageIcon> cacheImagenes = new HashMap<>();

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);
        label.setOpaque(true);

        if (value != null) {
            String ruta = value.toString();

            //Si YA tenemos esta imagen en la memoria
            if (cacheImagenes.containsKey(ruta)) {
                label.setIcon(cacheImagenes.get(ruta));
            } else {
                //Si NO está, la escalamos UNA SOLA VEZ y la guardamos
                ImageIcon icono = ClaseImagen.escalarImagen(ruta, 40, 40);
                if (icono != null) {
                    cacheImagenes.put(ruta, icono);
                    label.setIcon(icono);
                }
            }
        }

        // Colores de selección
        if (isSelected) {
            label.setBackground(table.getSelectionBackground());
        } else {
            label.setBackground(table.getBackground());
        }

        return label;
    }
}
