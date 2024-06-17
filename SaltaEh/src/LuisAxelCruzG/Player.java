package LuisAxelCruzG;

/**
 *
 * @author axeli
 */

import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;

public class Player extends GameObject {

    private boolean isJumping = false; // Indica si el jugador está saltando
    private boolean isAscending = false; // Indica si el jugador está ascendiendo en el salto
    private int jumpTime = 0; // Contador de tiempo de salto
    public static final int JUMP_SPEED = 8; // Velocidad de ascenso del salto
    private static final double ASCEND_DURATION = 0.4 * 60; // Duración del ascenso (0.4 segundos a 60 frames por segundo)
    private static final double DESCEND_DURATION = 4 * 60; // Duración del descenso (4 segundos a 60 frames por segundo)
    private static final int DESCEND_SPEED = 10; // Velocidad de descenso
    private static final int GROUND_LEVEL = Ventana.HEIGHT - 50; // Nivel del suelo

    private GameState gameState; // Referencia al estado del juego

    /**
     * Constructor del jugador.
     * 
     * @param position Posición inicial del jugador
     * @param texture Imagen del jugador
     * @param gameState Estado del juego
     */
    public Player(Vector2D position, BufferedImage texture, GameState gameState) {
        super(position, texture);
        this.gameState = gameState; // Inicializar la referencia a GameState
    }

    /**
     * Establece el estado del juego.
     * 
     * @param gameState Nuevo estado del juego
     */
    public void setGameState(GameState gameState) {
        this.gameState = gameState;
    }

    @Override
    public void update() {
        // Movimiento a la derecha
        if (KeyBoard.RIGHT) {
            position.setX(position.getX() + 15);
            if (position.getX() + texture.getWidth() > Ventana.WIDTH + 80) {
                position.setX(-50); //tp para la izquierda
            }
        }
        // Movimiento a la izquierda
        if (KeyBoard.LEFT) {
            position.setX(position.getX() - 15);
            if (position.getX() < -100) {
                position.setX(Ventana.WIDTH + 80); // tp para la derecha
            }
        }

        // empieza salto si no está saltando
        if (!isJumping) {
            jump();
        }

        // con esto salta el player
        if (isJumping) {
            if (isAscending) {
                // Ascendiendo
                if (jumpTime < ASCEND_DURATION) {
                    position.setY(position.getY() - JUMP_SPEED);
                    jumpTime++;
                } else {
                    isAscending = false;
                    jumpTime = 0;
                }
            } else {
                // Descendiendo
                if (jumpTime < DESCEND_DURATION) {
                    position.setY(position.getY() + DESCEND_SPEED);
                    jumpTime++;
                } else {
                    position.setY(GROUND_LEVEL);
                    isJumping = false;
                }
            }
        }
    }

    /**
     * Inicia el salto del jugador.
     */
    private void jump() {
        if (!isJumping) {
            isJumping = true;
            isAscending = true;
            jumpTime = 0;
            gameState.increaseScore(); // Incrementar puntuación al saltar
        }
    }

    /**
     * Maneja la colisión del jugador con una plataforma.
     * 
     * @param platform Plataforma con la que colisiona el jugador
     */
    public void onPlatformCollision(Platform platform) {
        if (isJumping && !isAscending) {
            isJumping = false;
            position.setY(platform.getPosition().getY() - texture.getHeight());
        }
    }

    /**
     * Verifica si el jugador está moviéndose hacia arriba.
     * 
     * @return true si el jugador está ascendiendo, false en caso contrario
     */
    public boolean isMovingUp() {
        return isJumping && isAscending;
    }

    /**
     * Verifica si el jugador está moviéndose hacia abajo.
     * 
     * @return true si el jugador está descendiendo, false en caso contrario
     */
    public boolean isMovingDown() {
        return isJumping && !isAscending;
    }

    @Override
    public void draw(Graphics g) {
        g.drawImage(texture, (int) position.getX(), (int) position.getY(), null); // Dibujar el jugador
    }

    @Override
    public Rectangle getBounds() {
        return new Rectangle((int) position.getX(), (int) position.getY(), texture.getWidth(), texture.getHeight()); // Obtener límites del jugador
    }

    /**
     * Inicializa o carga los recursos como imagenes y el estado del juego.
     */
    private void init() {
        Assets.initia();
        gameState = new GameState();
        gameState.getPlayer().setGameState(gameState); // Inicializar referencia de GameState en Player
    }
}
