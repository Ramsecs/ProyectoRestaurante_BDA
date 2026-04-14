/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

import java.awt.*;
import java.awt.image.BufferedImage;
import javax.swing.*;

/**
 *
 * @author josma
 */
public class BotonMenuAdministrador extends JButton {

    private int radio_esquinas = 25; // Redondez de las esquinas de los botones
    private Color color_fondo;
    private Color color_original;
    private int ancho_icono, alto_icono;
    private Font fuente_especial;

    /**
     * Constructor del botón con auto-escalado de imagen.
     *
     * * @param texto El texto a mostrar (puede ser null).
     * @param ruta_icono La ruta de la imagen (ej: "/recursos/icono.png"). Puede
     * ser null.
     * @param color El color de fondo del botón.
     * @param ancho_icono_deseado El ancho máximo que quieres para el icono (ej:
     * 50).
     * @param alto_icono_deseado El alto máximo que quieres para el icono (ej:
     * 50).
     */
    public BotonMenuAdministrador(String texto, String ruta_icono, Color color, int ancho_icono_deseado, int alto_icono_deseado, Font fuente_especial) {
        this.color_fondo = color;
        this.color_original = color;
        this.ancho_icono = ancho_icono_deseado;
        this.alto_icono = alto_icono_deseado;
        this.fuente_especial = fuente_especial;

        //Aqui estamos haciendo el escalado de las imagenes ya que algunas son muy grandes
        if (ruta_icono != null && !ruta_icono.isEmpty()) {
            ImageIcon iconoOriginal = new ImageIcon(getClass().getResource(ruta_icono));

            // Creamos un nuevo Icono escalado
            ImageIcon iconoEscalado = escalarImagen(iconoOriginal, ancho_icono_deseado, alto_icono_deseado);

            // Asignamos el icono escalado al boton
            setIcon(iconoEscalado);
        }

        // ---CONFIGURACION DE TEXTO--------------------------------------------
        if (texto != null && !texto.isEmpty()) {
            setText(texto);
            setForeground(Color.WHITE); // Texto siempre blanco
            setFont(new Font("SansSerif", Font.BOLD, 14));
            if (fuente_especial != null) {
                setFont(fuente_especial);
            } else {
                // Fuente de respaldo por si acaso
                setFont(new Font("SansSerif", Font.BOLD, 14));
            }
            // Posicionar texto debajo del icono
            setHorizontalTextPosition(SwingConstants.CENTER);
            setVerticalTextPosition(SwingConstants.BOTTOM);
        }

        // --- ESTETICA LIMPIA DE SWING ----------------------------------------
        setContentAreaFilled(false);
        setBorderPainted(false);
        setFocusPainted(false);
        setCursor(new Cursor(Cursor.HAND_CURSOR));

        //EFECTOS PARA LOS BOTONES
        this.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                // Se oscurece un poco al entrar
                color_fondo = color_fondo.darker();
                repaint();
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) {
                // Vuelve al color original (necesitas guardar el color original en una variable)
                color_fondo = color_original;
                repaint();
            }
            //----------------------------------------------------------------------

            @Override
            public void mousePressed(java.awt.event.MouseEvent evt) {
                // Efecto de encogerse
                setLocation(getX() + 2, getY() + 2);
            }

            @Override
            public void mouseReleased(java.awt.event.MouseEvent evt) {
                setLocation(getX() - 2, getY() - 2);
            }
        });
    }

    /**
     * Método de utilidad para redimensionar una imagen de forma suave.
     */
    private ImageIcon escalarImagen(ImageIcon icono_original, int ancho, int alto) {
        // 1. Obtenemos la imagen base
        Image imgOriginal = icono_original.getImage();

        // 2. Escalamos la imagen
        // Este metodo es sincrono y se encarga de todo el suavizado.
        Image img_escalada = imgOriginal.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);

        // 3. Devolvemos la nueva imagen escalada como ImageIcon
        return new ImageIcon(img_escalada);
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();

        // Activar suavizado para TODO lo que se dibuje en este botón (incluyendo el icono)
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        // Dibuja el fondo redondeado
        g2.setColor(color_fondo);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), radio_esquinas, radio_esquinas);

        // Importante: No cerramos g2 todavía si queremos que afecte al super
        g2.dispose();

        // Para que el icono también se beneficie del suavizado:
        Graphics2D g2_super = (Graphics2D) g;
        g2_super.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2_super.setRenderingHint(RenderingHints.KEY_INTERPOLATION, RenderingHints.VALUE_INTERPOLATION_BICUBIC);

        super.paintComponent(g);
    }

    @Override
    public void setBackground(Color bg) {
        super.setBackground(bg);
        this.color_fondo = bg;
        this.color_original = bg; // Actualizamos el original para que el MouseExited no lo regrese al verde
        repaint();
    }
}
