/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

import javax.swing.table.DefaultTableModel;

/**
 *Esta clase es para poder definir las columnas que son editables, es importante ponerlas corridas
 * ya que estan dentro de un rango.
 * @author josma
 */
public class ModeloTablaEditable extends DefaultTableModel{
    private int columna_maxima_editable;
    
    /**
     * @param columnas Array con los nombres de las columnas
     * @param filas Cantidad de filas iniciales
     * @param hasta_columna Índice de la última columna que se puede editar 
     */
    
    public ModeloTablaEditable(Object[] columnas, int filas, int hasta_columna){
        super(columnas,filas);
        this.columna_maxima_editable = hasta_columna;
    }
    
    @Override 
    public boolean isCellEditable(int row, int column){
        return column <= columna_maxima_editable;
    }
}
