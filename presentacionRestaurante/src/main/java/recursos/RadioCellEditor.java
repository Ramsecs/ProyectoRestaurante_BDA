/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

import java.awt.*;
import javax.swing.*;

/**
 *Esta clase es para poder darle formato al radiobutton que puede haber en la tabla
 * @author josma
 */
public class RadioCellEditor extends DefaultCellEditor {

    private JRadioButton button;

    public RadioCellEditor() {
        super(new JCheckBox()); // Constructor base
        button = new JRadioButton();
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.setOpaque(true);
    }

    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        button.setSelected(value != null && (Boolean) value);
        button.setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
        return button;
    }

    @Override
    public Object getCellEditorValue() {
        return button.isSelected();
    }
}
