/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

import java.awt.Component;
import java.awt.event.ActionListener;
import javax.swing.AbstractCellEditor;
import javax.swing.JTable;
import javax.swing.table.TableCellEditor;

/**
 *
 * @author josma
 */
public class BotonTablaEditor extends AbstractCellEditor implements TableCellEditor {

    private final BotonMenuAdministrador boton;    
    
    public BotonTablaEditor(BotonMenuAdministrador boton, ActionListener accion) {
        this.boton = boton;

        //Ahora removemos actionListeners previos para que no se acumulen
        for (ActionListener acl : boton.getActionListeners()) {
            boton.removeActionListener(acl);
        }
        
        this.boton.addActionListener(e -> {
            stopCellEditing(); //Avisa a la tabla que ya terminamos de hacer modificaciones o editar
            accion.actionPerformed(e); //Ejecuta lo que deseemos (como abrir una ventana etc)
        });
    }

    @Override
    public Object getCellEditorValue() {
        return boton.getText();
    }
    
    @Override
    public Component getTableCellEditorComponent(JTable table, Object value, boolean isSelected, int row, int column) {
        return boton; 
    }
    
}
