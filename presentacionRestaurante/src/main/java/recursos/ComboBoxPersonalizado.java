/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

import java.awt.*;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.plaf.basic.*;

/**
 *
 * @author josma
 */
//Aplicamos de un tipo generico para poder recibir cualquier objeto
public class ComboBoxPersonalizado<E> extends JComboBox<E> {

    private Color color_fondo = Color.WHITE;
    private Color color_borde = new Color(255, 184, 77); 
    private int radio = 20;

    public ComboBoxPersonalizado(E[] items) {
        super(items);
        setOpaque(false);
        setBackground(color_fondo);
        setFont(new Font("SansSerif", Font.PLAIN, 14));
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        // Quitamos el diseño por defecto 
        setUI(new BasicComboBoxUI() {
            @Override
            protected JButton createArrowButton() {
                // Personalizamos la flechita
                JButton boton = new JButton("▼");
                boton.setBorderPainted(false);
                boton.setContentAreaFilled(false);
                boton.setFocusPainted(false);
                boton.setForeground(color_borde);
                return boton;
            }

            @Override
            public void paintCurrentValueBackground(Graphics g, Rectangle bounds, boolean hasFocus) {
                // Cambiamos para que no lo pinte azul
            }

            @Override
            protected ComboPopup createPopup() {
                BasicComboPopup popup = new BasicComboPopup(comboBox);
                popup.setBorder(BorderFactory.createLineBorder(color_borde, 1));
                return popup;
            }
        });

        // Margen interno para el texto
        setBorder(new EmptyBorder(5, 15, 5, 10));
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Dibujar el fondo redondeado
        g2.setColor(color_fondo);
        g2.fillRoundRect(0, 0, getWidth() - 1, getHeight() - 1, radio, radio);

        // Dibujar el borde naranja
        g2.setColor(color_borde);
        g2.setStroke(new BasicStroke(2));
        g2.drawRoundRect(1, 1, getWidth() - 3, getHeight() - 3, radio, radio);

        g2.dispose();
        super.paintComponent(g);
    }
}
