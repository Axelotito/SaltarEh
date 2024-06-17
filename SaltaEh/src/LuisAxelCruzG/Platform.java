package LuisAxelCruzG;

/**
 * Clase que representa una plataforma en el juego.
 * Esta clase hereda de GameObject y define las características básicas de una plataforma.
 * 
 * @autor axeli
 */

import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class Platform extends GameObject {
    public static final int WIDTH = 100; // Ancho de la plataforma
    public static final int HEIGHT = 15; // Alto de la plataforma

    /**
     * Constructor de la clase Platform.
     * 
     * @param position Posición de la plataforma
     * @param texture Imagen de la plataforma
     */
    public Platform(Vector2D position, BufferedImage texture) {
        super(position, texture);
    }

    @Override
    public void update() {
        // Las plataformas no necesitan lógica de actualización por ahora
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(texture, (int) position.getX(), (int) position.getY(), WIDTH, HEIGHT, null); // Dibujar la plataforma
    }
}

/**
 * Clase que representa una plataforma móvil en el juego.
 * Hereda de Platform y añade funcionalidad para moverse de lado a lado.
 */
class MovingPlatform extends Platform {
    private double speed; // Velocidad de movimiento de la plataforma

    /**
     * Constructor de la clase MovingPlatform.
     * 
     * @param position Posición de la plataforma
     * @param texture Imagen de la plataforma
     * @param speed Velocidad de la plataforma
     */
    public MovingPlatform(Vector2D position, BufferedImage texture, double speed) {
        super(position, texture);
        this.speed = speed;
    }

    @Override
    public void update() {
        position.setX(position.getX() + speed); // Actualizar posición según la velocidad
        
        // Cambiar dirección cuando alcanza los bordes
        if (position.getX() <= 0 || position.getX() + WIDTH >= Ventana.WIDTH) {
            speed = -speed;
        }
    }
}

/**
 * Clase que representa una plataforma falsa en el juego.
 * Hereda de Platform y se oculta después de ser tocada por el jugador.
 */
class FakePlatform extends Platform {
    private boolean touched = false; // Indica si la plataforma ha sido tocada

    /**
     * Constructor de la clase FakePlatform.
     * 
     * @param position Posición de la plataforma
     * @param texture Imagen de la plataforma
     */
    public FakePlatform(Vector2D position, BufferedImage texture) {
        super(position, texture);
    }

    @Override
    public void update() {
        // No se necesita lógica de actualización adicional
    }

    @Override
    public void draw(Graphics g) {
        if (!touched) {
            super.draw(g); // Dibujar la plataforma si no ha sido tocada
        }
    }

    /**
     * Verifica si la plataforma ha sido tocada por el jugador.
     * 
     * @param player El jugador que puede tocar la plataforma
     * @return true si la plataforma es tocada por el jugador, false en caso contrario
     */
    public boolean isTouchedBy(Player player) {
        if (!touched && player.getBounds().intersects(getBounds())) { // Verificar colisión
            if (player.isMovingDown()) {
                touched = true; // Marcar como tocada
                return true;
            }
        }
        return false;
    }
}
