/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

import java.awt.Component;
import javax.swing.JLabel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;

/**
 *
 * @author josma
 */
public class ImagenTablaRender extends DefaultTableCellRenderer {

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value,
            boolean isSelected, boolean hasFocus, int row, int column) {

        JLabel label = new JLabel();
        label.setHorizontalAlignment(SwingConstants.CENTER);

        if (value != null) {
            String ruta = value.toString();
            // Usamos la ClaseImagen para que la miniatura sea de 40x40 o bien, el alto de la fila
            try {
                label.setIcon(ClaseImagen.escalarImagen(ruta, 40, 40));
            } catch (Exception e) {
                label.setText("Error"); // Por si la ruta esta mal
            }
        }

        // Mantener el color de selección de la tabla
        if (isSelected) {
            label.setOpaque(true);
            label.setBackground(table.getSelectionBackground());
        }

        return label;
    }
}
