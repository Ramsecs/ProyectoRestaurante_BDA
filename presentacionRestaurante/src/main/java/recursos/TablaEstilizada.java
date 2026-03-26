/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;
import java.awt.*;
import javax.swing.*;
import javax.swing.table.*;
/**
 *
 * @author josma
 */


public class TablaEstilizada extends JTable {

    public TablaEstilizada(DefaultTableModel modelo, Font fuenteCeldas) {
        super(modelo);
        
        // 1. Configuracion General
        setRowHeight(35);
        setShowVerticalLines(true);
        setGridColor(new Color(230, 230, 230));
        setSelectionBackground(new Color(255, 220, 150));
        setSelectionForeground(Color.BLACK);
        
        // 2. Personalizar Cabecera 
        JTableHeader header = getTableHeader();
        header.setPreferredSize(new Dimension(0, 40));
        header.setBackground(new Color(255, 184, 77));
        header.setForeground(Color.WHITE);
        header.setFont(fuenteCeldas.deriveFont(Font.BOLD, 14f));

        // 3. Renderizador para el centrado  y aplicar fuente a las celdas
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer() {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
                Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                setHorizontalAlignment(JLabel.CENTER);
                setFont(fuenteCeldas);
                return c;
            }
        };

        // Aplicar a todas las columnas
        for (int i = 0; i < getColumnCount(); i++) {
            getColumnModel().getColumn(i).setCellRenderer(centerRenderer);
        }
    }
}
