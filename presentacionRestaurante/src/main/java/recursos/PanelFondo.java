/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

import java.awt.*;
import java.awt.geom.*;
import java.awt.image.*;
import java.net.*;
import javax.imageio.*;
import javax.swing.*;

/**
 *
 * @author josma
 */
public class PanelFondo  extends JPanel{
    private BufferedImage imagen_patron;
    private TexturePaint textura_fondo;

    public PanelFondo() {
        try {
            imagen_patron = ImageIO.read(getClass().getResource("/imagenes/fondo.jpg"));
            
            //Esto hace que se repita por si la ventana es mas grande que la imaen
            Rectangle2D anchor = new Rectangle2D.Double(0, 0, imagen_patron.getWidth(), imagen_patron.getHeight());
            textura_fondo = new TexturePaint(imagen_patron, anchor);
            
        } catch (Exception e) {
            System.err.println("Error: No se pudo cargar la imagen de fondo en /recursos/");
            e.printStackTrace();
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        if (imagen_patron != null) {
            // Esto hace que la imagen se estire al tamaño total del panel
            g.drawImage(imagen_patron, 0, 0, getWidth(), getHeight(), this);
        }
    }
}
