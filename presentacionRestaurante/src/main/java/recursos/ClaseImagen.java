/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;

import java.awt.*;
import java.io.*;
import javax.swing.*;

/**
 *
 * @author josma
 */
public class ClaseImagen {
    //Metodo de ruta 
    public static ImageIcon escalarImagen(String ruta, int ancho, int alto) {
    try {
        Image img;
        // Si la ruta empieza con "/", es un recurso del proyecto (JAR/src)
        if (ruta.startsWith("/")) {
            java.net.URL imgURL = ClaseImagen.class.getResource(ruta);
            if (imgURL != null) {
                img = new ImageIcon(imgURL).getImage();
            } else {
                System.out.println("No se encontró el recurso en: " + ruta);
                return null;
            }
        } else {
            // Si no tiene "/", es una ruta absoluta del sistema (C:\Users...)
            img = new ImageIcon(ruta).getImage();
        }

        // Escalado suave para que no se vea pixelado
        Image nuevaImg = img.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(nuevaImg);
    } catch (Exception e) {
        System.err.println("Error al escalar imagen: " + e.getMessage());
        return null;
    }
}
    
    
    //Cuando ya tenemos el objeto File
    public static ImageIcon escalarImagenDesdeFile(File archivo, int ancho, int alto){
        if (archivo == null || !archivo.exists()) {
           return null;  
        }
        return escalarImagen(archivo.getAbsolutePath(), ancho, alto);       
    }
}
