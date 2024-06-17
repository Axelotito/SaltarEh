package LuisAxelCruzG;

/**
 *
 * @author axeli
 */

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public abstract class GameObject {
    protected BufferedImage texture; // Imagen que representa el objeto
    protected Vector2D position; // Posición del objeto en el espacio 2D

    // Constructor de la clase GameObject
    public GameObject(Vector2D position, BufferedImage texture) {
        this.position = position; // Inicializar posición
        this.texture = texture; // Inicializar textura (imagen)
    }

    // Método abstracto para actualizar el estado del objeto (debe ser implementado por las subclases)
    public abstract void update();

    // Método abstracto para dibujar el objeto (debe ser implementado por las subclases)
    public abstract void draw(Graphics g);

    // Obtener la posición del objeto
    public Vector2D getPosition() {
        return position;
    }

    // Establecer la posición del objeto
    public void setPosition(Vector2D position) {
        this.position = position;
    }

    // Obtener los límites del objeto como un rectángulo (utilizado para detección de colisiones)
    public Rectangle getBounds() {
        return new Rectangle((int) position.getX(), (int) position.getY(), texture.getWidth(), texture.getHeight());
    }
}
