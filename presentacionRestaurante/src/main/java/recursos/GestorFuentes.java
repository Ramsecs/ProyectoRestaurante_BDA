/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package recursos;
import java.awt.Font;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
/**
 *
 * @author josma
 */
public class GestorFuentes {

    // Almacenamos las fuentes cargadas para no leer el archivo cada vez
    //Aqui usamos Map que nos ayuda a definir una especie de estructura
    //clave-valor, donde la clave es un String y el valor en este caso el objeto Font
    //el HashMap lo que hace es buscarlo por su llave que es igual clave(llave)-valor
    private static final Map<String, Font> fuentes = new HashMap<>();

    /**
     * @param nombreArchivo que es el nombre del archivo que vamos a recibir
     * @param tamano Tamaño de la fuente (float)
     */
    public static Font obtenerFuente(String nombreArchivo, float tamano) {
        Font fuente_base = fuentes.get(nombreArchivo);

        if (fuente_base == null) {
            try {
                //Aqui se ajusta la ruta donde 
                String ruta = "/fonts/" + nombreArchivo;
                InputStream input_stream = GestorFuentes.class.getResourceAsStream(ruta);
                
                if (input_stream == null) {
                    System.err.println("No se encontró el archivo: " + ruta);
                    return new Font("Arial", Font.PLAIN, (int) tamano);
                }

                fuente_base = Font.createFont(Font.TRUETYPE_FONT, input_stream);
                fuentes.put(nombreArchivo, fuente_base); // Guardar en el cache
                
            } catch (Exception e) {
                System.err.println("Error al cargar la fuente: " + e.getMessage());
                return new Font("Arial", Font.PLAIN, (int) tamano);
            }
        }

        return fuente_base.deriveFont(tamano);
    }
}
