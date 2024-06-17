package LuisAxelCruzG;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;

/**
 * Clase Loader que proporciona métodos estáticos para cargar imágenes desde archivos.
 * 
 * @author axeli
 */
public class Loader {
    
    /**
     * Método estático para cargar una imagen desde un archivo en la ruta especificada.
     * 
     * Param path Ruta del archivo de imagen a cargar
     * Devuelve BufferedImage cargado desde el archivo, o null si ocurre un error
     */
    public static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(new File(path)); // Intenta cargar la imagen desde la ruta especificada
        } catch (IOException e) {
            e.printStackTrace();
            return null; // En caso de error, devuelve null
        }
    }
//Esto se va a modificar jasjas
}
