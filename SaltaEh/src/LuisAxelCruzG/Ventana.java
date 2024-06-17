package LuisAxelCruzG;

/**
 *
 * @author axeli
 */

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferStrategy;
import javax.swing.ImageIcon;
import javax.swing.JFrame;

public class Ventana extends JFrame implements Runnable {

    public static void main(String[] args) {
        new Ventana().start(); // Crear una nueva instancia de Ventana y comenzar el hilo
    }

    public static final int WIDTH = 800, HEIGHT = 700; // Ancho y alto de la ventana
    private Canvas canvas; // Canvas donde se dibujará el juego
    private Thread thread; // Hilo para la ejecución del juego
    private boolean running = false; // Indica si el juego está en ejecución
    private boolean gamePaused = false; // Variable para controlar el estado del juego

    private BufferStrategy bs; // Estrategia de buffering para el Canvas
    private Graphics g; // Objeto Graphics para dibujar en el Canvas

    private GameState gameState; // Estado actual del juego
    private KeyBoard keyBoard; // Teclado para manejar la entrada del usuario

    private final int FPS = 60; // Frames por segundo deseados
    private double TARGETTIME = 1000000000 / FPS; // Tiempo objetivo entre frames
    private double delta = 0; // Delta para el control de tiempo entre actualizaciones
    private int AVERAGEFPS = FPS; // FPS promedio

    public Ventana() {
        setTitle("Saltar Eh!"); // Título de la ventana
        setSize(WIDTH, HEIGHT); // Tamaño de la ventana
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); // Comportamiento al cerrar la ventana
        setResizable(false); // No permitir el redimensionamiento de la ventana
        setLocationRelativeTo(null); // Centrar la ventana en la pantalla

        // Establecer el icono de la ventana
        ImageIcon imagen = new ImageIcon(getClass().getResource("/res/Star.png"));
        setIconImage(imagen.getImage());

        canvas = new Canvas(); // Crear el Canvas
        keyBoard = new KeyBoard(); // Crear el teclado

        // Establecer dimensiones del Canvas
        canvas.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        canvas.setMaximumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setMinimumSize(new Dimension(WIDTH, HEIGHT));
        canvas.setFocusable(true); // Permitir que el Canvas reciba eventos de teclado

        add(canvas); // Añadir el Canvas a la ventana
        canvas.addKeyListener(keyBoard); // Añadir el listener de teclado al Canvas
        setVisible(true); // Hacer visible la ventana
    }

    public void update() {
        keyBoard.update(); // Actualizar el estado del teclado
        if (!gamePaused) { // Solo actualizar el estado del juego si no está pausado
            gameState.update(); // Actualizar el estado del juego
        }
    }

    private void draw() {
        bs = canvas.getBufferStrategy(); // Obtener la estrategia de buffering

        if (bs == null) {
            canvas.createBufferStrategy(2); // Crear una estrategia de doble buffering si no existe
            return;
        }

        g = bs.getDrawGraphics(); // Obtener el objeto Graphics para dibujar

        // Dibujar el fondo
        g.drawImage(Assets.background, 0, 0, WIDTH, HEIGHT, null);

        if (!gamePaused) {
            gameState.draw(g); // Dibujar el estado del juego
            // Dibujar la puntuación en la esquina superior izquierda
            g.setColor(Color.white);
            g.drawString("Score: " + gameState.getScore(), 10, 30);
        } else {
            // Dibujar la imagen de kirbySleep en el centro de la pantalla
            int kirbyX = (WIDTH - Assets.kirbySleep.getWidth()) / 2;
            int kirbyY = (HEIGHT - Assets.kirbySleep.getHeight()) / 2;
            g.drawImage(Assets.kirbySleep, kirbyX, kirbyY, null);

            // Establecer el color blanco para el texto
            g.setColor(Color.white);

            // Dibujar el texto "Game Over" debajo de la imagen
            g.drawString("Game Over", (WIDTH - g.getFontMetrics().stringWidth("Game Over")) / 2, kirbyY + Assets.kirbySleep.getHeight() + 20);

            // Dibujar la puntuación en la pantalla de Game Over
            g.drawString("Score: " + gameState.getScore(), (WIDTH - g.getFontMetrics().stringWidth("Score: " + gameState.getScore())) / 2, kirbyY + Assets.kirbySleep.getHeight() + 40);

            // Dibujar el texto "Presione A o D para reiniciar" debajo de "Game Over"
            g.drawString("Presione A o D para reiniciar", (WIDTH - g.getFontMetrics().stringWidth("Presione A o D para reiniciar")) / 2, kirbyY + Assets.kirbySleep.getHeight() + 60);
        }

        g.dispose(); // Liberar recursos del Graphics
        bs.show(); // Mostrar el buffer
    }

    private void init() {
        Assets.initia(); // Inicializar los recursos
        gameState = new GameState(); // Crear el estado del juego
    }

    @Override
    public void run() {
        long now = 0;
        long lastTime = System.nanoTime(); // Tiempo actual en nanosegundos
        int frames = 0; // Contador de frames
        long time = 0; // Acumulador de tiempo

        init(); // Inicializar recursos y estado del juego

        while (running) { // Bucle principal del juego
            now = System.nanoTime();
            delta += (now - lastTime) / TARGETTIME; // Actualizar delta
            time += (now - lastTime); // Acumular tiempo
            lastTime = now; // Actualizar lastTime

            if (delta >= 1) {
                update(); // Actualizar el juego
                draw(); // Dibujar el juego
                delta--; // Reducir delta
                frames++; // Incrementar contador de frames
            }
            if (time >= 1000000000) {
                AVERAGEFPS = frames; // Actualizar FPS promedio
                frames = 0; // Reiniciar contador de frames
                time = 0; // Reiniciar acumulador de tiempo
            }

            // Verificar si el jugador ha llegado al fondo de la pantalla
            if (gameState != null && gameState.getPlayer() != null && gameState.getPlayer().getPosition().getY() >= Ventana.HEIGHT) {
                gamePaused = true; // Pausar el juego
            }

            // Verificar si el jugador quiere reiniciar el juego
            if (gamePaused && (KeyBoard.LEFT || KeyBoard.RIGHT)) {
                gamePaused = false; // Reanudar el juego
                gameState = new GameState(); // Reiniciar el estado del juego
            }
        }

        stop(); // Detener el hilo
    }

    private void start() {
        thread = new Thread(this); // Crear un nuevo hilo
        thread.start(); // Iniciar el hilo
        running = true; // Marcar el juego como en ejecución
    }

    private void stop() {
        try {
            thread.join(); // Esperar a que el hilo termine
            running = false; // Marcar el juego como no en ejecución
        } catch (InterruptedException ex) {
            ex.printStackTrace(); // Imprimir traza de la excepción
        }
    }
}
