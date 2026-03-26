/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;
import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
/**
 *
 * @author josma
 */


public class TextFieldPersonalizado extends JTextField {
    private Color color_fondo = new Color(255, 184, 77);

    public TextFieldPersonalizado(int columnas) {
        super(columnas);
        setOpaque(false);
        setBackground(color_fondo);
        setForeground(Color.BLACK); // Color de la letra al escribir
        // El margen de 15 a la izquierda evita que el texto toque la curva
        setBorder(new EmptyBorder(8, 15, 8, 15)); 
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Dibujar fondo redondeado
        g2.setColor(getBackground());
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), 25, 25);
        
        g2.dispose();
        super.paintComponent(g); // Dibuja el texto encima
    }
}
