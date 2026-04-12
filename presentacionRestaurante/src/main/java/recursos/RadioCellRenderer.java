/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

import java.awt.Component;
import javax.swing.*;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author josma
 */
public class RadioCellRenderer extends JRadioButton implements TableCellRenderer {

    public RadioCellRenderer() {
        setHorizontalAlignment(SwingConstants.CENTER);
        setOpaque(true); // Importante para que se pinte el fondo
    }

    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {

        // Convertimos el valor a boolean de forma segura
        boolean seleccionado = false;
        if (value instanceof Boolean) {
            seleccionado = (Boolean) value;
        } else if (value instanceof String) {
            seleccionado = Boolean.parseBoolean((String) value);
        }

        setSelected(seleccionado);

        // Colores para que combine con tu TablaEstilizada
        if (isSelected) {
            setBackground(table.getSelectionBackground());
            setForeground(table.getSelectionForeground());
        } else {
            setBackground(table.getBackground());
            setForeground(table.getForeground());
        }

        return this;
    }
}
