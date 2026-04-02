/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

import java.awt.Component;
import javax.swing.JButton;
import javax.swing.JTable;
import javax.swing.table.TableCellRenderer;

/**
 *
 * @author josma
 */
public class BotonTablaRender implements TableCellRenderer{
    
    private final BotonMenuAdministrador boton;    
    public BotonTablaRender(BotonMenuAdministrador boton){
        this.boton = boton;
    }
    @Override
    public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
        //Si tenemos la intención de querer cambiar el texto dependiendo de la tabla    
        //boton.setText(value != null ? value.toString() : "");
        return boton; 
    }
        
    
}

