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


public class PanelBordePunteado extends JPanel {
    private Color color_borde = new Color(255, 184, 77); //El color del borde
    //lo deje ya establecido porque no lo pienso cambiar en un futuro
    private int redondez_esquinas = 20;

    public PanelBordePunteado() {
        setOpaque(false);
        // Espacio interno para que los componentes no toquen el borde
        setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        
        // Configuración del punteado: {largo de línea, espacio}
        float[] dash = {10.0f, 5.0f};
        g2.setStroke(new BasicStroke(2, BasicStroke.CAP_BUTT, BasicStroke.JOIN_MITER, 10.0f, dash, 0.0f));
        g2.setColor(color_borde);
        
        // Dibujamos el contorno redondeado
        g2.drawRoundRect(2, 2, getWidth() - 5, getHeight() - 5, redondez_esquinas, redondez_esquinas);
        g2.dispose();
    }
}