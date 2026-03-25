/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author josma
 */
public class PanelRedondeado extends JPanel {
    private int radio = 40; // lo curvo de las esquinas
    private Color color_fondo = Color.WHITE;

    public PanelRedondeado(int radioEsquina, Color color) {
        this.radio = radioEsquina;
        this.color_fondo = color;
        setOpaque(false); //Que no se vea lo gris de atras
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        // Activamos el suavizado de bordes (Antialiasing)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Pintamos el fondo redondeado
        g2.setColor(color_fondo);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radio, radio);
        
        g2.dispose();
        super.paintComponent(g);
    }
}
