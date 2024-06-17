package LuisAxelCruzG;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * Clase abstracta que representa un objeto en movimiento en el juego.
 * Hereda de GameObject y añade propiedades y métodos específicos para objetos en movimiento.
 * 
 * @author axeli
 */
public abstract class MovingObject extends GameObject {

    protected Vector2D velocity; // Velocidad del objeto
    protected AffineTransform at; // Transformación afín para la rotación y el movimiento

    /**
     * Constructor de la clase MovingObject.
     * 
     * Param position Posición inicial del objeto
     * Param velocity Velocidad inicial del objeto
     * Param texture Imagen del objeto
     */
    public MovingObject(Vector2D position, Vector2D velocity, BufferedImage texture) {
        super(position, texture);
        this.velocity = velocity;
    }

    /**
     * Método abstracto para actualizar el estado del objeto.
     * Debe ser implementado por las subclases para definir la lógica específica de actualización.
     */
    @Override
    public abstract void update();

    /**
     * Método abstracto para dibujar el objeto en la pantalla.
     * Debe ser implementado por las subclases para definir cómo se dibuja el objeto.
     * 
     * Param g Objeto Graphics para dibujar en la pantalla
     */
    @Override
    public abstract void draw(Graphics g);

}
