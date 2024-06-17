package LuisAxelCruzG;

/**
 *
 * @author axeli
 */

import java.awt.image.BufferedImage;
import java.io.IOException;
import javax.imageio.ImageIO;

public class Assets {

    // Declaración de variables estáticas para las imágenes
    public static BufferedImage player;
    public static BufferedImage platform;
    public static BufferedImage background;
    public static BufferedImage movingPlatform;
    public static BufferedImage fakePlatform;
    public static BufferedImage kirbySleep;
    public static BufferedImage enemy;

    // Método para inicializar y cargar todas las imágenes
    public static void initia() {
        player = loadImage("/res/player.png"); // Cargar imagen del jugador
        platform = loadImage("/res/cloud.png"); // Cargar imagen de la plataforma estática
        background = loadImage("/res/background.png"); // Cargar imagen de fondo
        movingPlatform = loadImage("/res/movingPlatform.png"); // Cargar imagen de la plataforma móvil
        fakePlatform = loadImage("/res/fakePlatform.png"); // Cargar imagen de la plataforma falsa
        kirbySleep = loadImage("/res/kirbySleep.png"); // Cargar imagen de Kirby durmiendo
        enemy = loadImage("/res/enemy.png"); // Al final no implementado :( no salia
    }

    // Método para cargar una imagen desde una ruta especificada
    private static BufferedImage loadImage(String path) {
        try {
            return ImageIO.read(Assets.class.getResourceAsStream(path)); // Leer la imagen desde el recurso
        } catch (IOException e) {
            return null; // Retornar null si ocurre un error al cargar la imagen
        }
    }
}
