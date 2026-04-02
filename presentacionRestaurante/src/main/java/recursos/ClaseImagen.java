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
    public static ImageIcon escalarImagen(String ruta, int ancho, int alto){
        
        if (ruta == null || ruta.isEmpty()) return null; 
        
        ImageIcon icono_original = new ImageIcon(ruta);
        if (icono_original.getImageLoadStatus() != MediaTracker.COMPLETE){
            return null; 
        }       
        Image imagen = icono_original.getImage();
        Image nueva_imagen = imagen.getScaledInstance(ancho, alto, Image.SCALE_SMOOTH);
        return new ImageIcon(nueva_imagen);
    }
    
    
    //Cuando ya tenemos el objeto File
    public static ImageIcon escalarImagenDesdeFile(File archivo, int ancho, int alto){
        if (archivo == null || !archivo.exists()) {
           return null;  
        }
        return escalarImagen(archivo.getAbsolutePath(), ancho, alto);       
    }
}
