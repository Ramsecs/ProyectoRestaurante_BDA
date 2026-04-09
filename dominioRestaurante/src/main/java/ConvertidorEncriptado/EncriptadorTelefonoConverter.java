/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package ConvertidorEncriptado;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

/**
 *
 * @author RAMSES
 */
@Converter
public class EncriptadorTelefonoConverter implements AttributeConverter<String, String> {

    private static final String ALGORITMO = "AES/ECB/PKCS5Padding";
    private static final byte[] LLAVE = "MiLlaveSecreta12".getBytes(); // Debe ser de 16 caracteres
    
    
    /**
     * Mediante este metodo convertimos el atributo a una columna de la base de datos,
     * se le pasa por atributos el dato que queremos que entre y lo lleva protegido para que no sea legible 
     * en la base de datos
     * @param telefono
     * @return 
     */
    @Override
    public String convertToDatabaseColumn(String telefono) {
        if (telefono == null) return null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(LLAVE, "AES"));
            return Base64.getEncoder().encodeToString(cipher.doFinal(telefono.getBytes()));
        } catch (Exception e) {
            throw new RuntimeException("Error al encriptar", e);
        }
    }
    
    /**
     * Mediante este metodo convertimos el atributo a un atributo de entidad para que pueda ser 
     * usado de manera que ya este desencriptado
     * @param telefono_db
     * @return 
     */
    @Override
    public String convertToEntityAttribute(String telefono_db) {
        if (telefono_db == null) return null;
        try {
            Cipher cipher = Cipher.getInstance(ALGORITMO);
            cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(LLAVE, "AES"));
            return new String(cipher.doFinal(Base64.getDecoder().decode(telefono_db)));
        } catch (Exception e) {
            throw new RuntimeException("Error al desencriptar", e);
        }
    }
}
