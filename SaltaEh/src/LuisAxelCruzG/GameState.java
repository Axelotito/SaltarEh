package LuisAxelCruzG;

/**
 *
 * @author axeli
 */

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class GameState {

    private Player player;
    private List<Platform> platforms;
    private Random random;
    private static final int INITIAL_PLATFORMS = 15; // Número inicial de plataformas
    private static final int MIN_PLATFORMS = 11; // Número mínimo de plataformas presentes
    private int score = 0; // Variable de puntuación

    public GameState() {
        player = new Player(new Vector2D(375, 300), Assets.player, this); // Inicializar jugador
        platforms = new ArrayList<>(); // Inicializar lista de plataformas
        random = new Random(); // Inicializar generador de números aleatorios
        score = 0; // Inicializar puntuación

        // Generar plataformas iniciales
        for (int i = 0; i < INITIAL_PLATFORMS; i++) {
            addPlatform(); // Añadir plataforma estática
        }

        // Añadir algunas plataformas móviles
        for (int i = 0; i < 1; i++) { // Añadir una plataforma móvil
            addMovingPlatform(); // Añadir plataforma móvil
        }

        // Añadir algunas plataformas falsas
        for (int i = 0; i < 3; i++) { // Añadir tres plataformas falsas
            addFakePlatform(); // Añadir plataforma falsa
        }
    }

    public void increaseScore() {
        score += 10; // Incrementar puntuación en 10 puntos
    }

    public int getScore() {
        return score; // Devolver la puntuación actual
    }

    private void addPlatform() {
        Platform newPlatform;
        boolean overlaps;
        do {
            // Generar coordenadas aleatorias para la nueva plataforma
            double x = random.nextInt(Ventana.WIDTH - Platform.WIDTH);
            double y = random.nextInt(Ventana.HEIGHT - Platform.HEIGHT);
            newPlatform = new Platform(new Vector2D(x, y), Assets.platform);

            overlaps = false;
            // Verificar superposición con plataformas existentes
            for (Platform platform : platforms) {
                if (newPlatform.getBounds().intersects(platform.getBounds())) {
                    overlaps = true;
                    break;
                }
            }
        } while (overlaps); // Repetir si hay superposición
        platforms.add(newPlatform); // Añadir la nueva plataforma a la lista
    }

    private void addMovingPlatform() {
        MovingPlatform newPlatform;
        boolean overlaps;
        do {
            // Generar coordenadas aleatorias para la nueva plataforma móvil
            double x = random.nextInt(Ventana.WIDTH - Platform.WIDTH);
            double y = random.nextInt(Ventana.HEIGHT - Platform.HEIGHT);
            newPlatform = new MovingPlatform(new Vector2D(x, y), Assets.movingPlatform, 2); // Velocidad de movimiento

            overlaps = false;
            // Verificar superposición con plataformas existentes
            for (Platform platform : platforms) {
                if (newPlatform.getBounds().intersects(platform.getBounds())) {
                    overlaps = true;
                    break;
                }
            }
        } while (overlaps); // Repetir si hay superposición
        platforms.add(newPlatform); // Añadir la nueva plataforma móvil a la lista
    }

    private void addFakePlatform() {
        FakePlatform newPlatform;
        boolean overlaps;
        do {
            // Generar coordenadas aleatorias para la nueva plataforma falsa
            double x = random.nextInt(Ventana.WIDTH - Platform.WIDTH);
            double y = random.nextInt(Ventana.HEIGHT - Platform.HEIGHT);
            newPlatform = new FakePlatform(new Vector2D(x, y), Assets.fakePlatform);

            overlaps = false;
            // Verificar superposición con plataformas existentes
            for (Platform platform : platforms) {
                if (newPlatform.getBounds().intersects(platform.getBounds())) {
                    overlaps = true;
                    break;
                }
            }
        } while (overlaps); // Repetir si hay superposición
        platforms.add(newPlatform); // Añadir la nueva plataforma falsa a la lista
    }

    public void update() {
        player.update(); // Actualizar estado del jugador

        // Mover las plataformas hacia abajo si el jugador está subiendo
        if (player.isMovingUp()) {
            for (Platform platform : platforms) {
                platform.getPosition().setY(platform.getPosition().getY() + Player.JUMP_SPEED);
            }
        }

        // Actualizar plataformas
        for (Platform platform : platforms) {
            platform.update(); // Llamar al método update de cada plataforma
        }

        // Listas temporales para las plataformas a eliminar y añadir
        List<Platform> toRemove = new ArrayList<>();
        List<Platform> toAdd = new ArrayList<>();

        // Verificar si las plataformas salen de la pantalla y preparar nuevas
        for (Platform platform : platforms) {
            if (platform.getPosition().getY() > Ventana.HEIGHT) {
                toRemove.add(platform); // Marcar plataforma para eliminar
                // Añadir una nueva plataforma con probabilidad del 30%
                if (random.nextInt(100) < 30) {
                    toAdd.add(createNewPlatform()); // Crear y añadir nueva plataforma
                }
            } else if (platform instanceof FakePlatform) {
                if (((FakePlatform) platform).isTouchedBy(player)) {
                    toRemove.add(platform); // Marcar plataforma falsa para eliminar si ha sido tocada por el jugador
                }
            }
        }

        // Eliminar y añadir plataformas fuera del bucle de iteración
        platforms.removeAll(toRemove); // Eliminar plataformas
        platforms.addAll(toAdd); // Añadir nuevas plataformas

        // Añadir nuevas plataformas si es necesario para mantener el mínimo
        while (platforms.size() < MIN_PLATFORMS) {
            platforms.add(createNewPlatform()); // Crear y añadir nuevas plataformas hasta alcanzar el mínimo
        }

        // Verificar colisiones entre el jugador y las plataformas
        for (Platform platform : platforms) {
            if (!(platform instanceof FakePlatform) && player.getBounds().intersects(platform.getBounds())) {
                player.onPlatformCollision(platform); // Manejar la colisión del jugador con la plataforma
            }
        }
    }

    private Platform createNewPlatform() {
        Platform newPlatform;
        boolean overlaps;
        do {
            // Generar coordenadas aleatorias para la nueva plataforma
            double x = random.nextInt(Ventana.WIDTH - Platform.WIDTH);
            double y = -Platform.HEIGHT; // Posicionar la nueva plataforma fuera de la pantalla, en la parte superior
            int platformType = random.nextInt(10); // Generar un tipo de plataforma aleatorio (0-9)

            if (platformType < 1) { // 10% de probabilidad para FakePlatform
                newPlatform = new FakePlatform(new Vector2D(x, y), Assets.fakePlatform);
            } else if (platformType < 4) { // 30% de probabilidad para MovingPlatform
                newPlatform = new MovingPlatform(new Vector2D(x, y), Assets.movingPlatform, 2); // Plataforma móvil
            } else { // 60% de probabilidad para la plataforma estática
                newPlatform = new Platform(new Vector2D(x, y), Assets.platform);
            }

            overlaps = false;
            // Verificar superposición con plataformas existentes
            for (Platform platform : platforms) {
                if (newPlatform.getBounds().intersects(platform.getBounds())) {
                    overlaps = true;
                    break;
                }
            }
        } while (overlaps); // Repetir si hay superposición
        return newPlatform; // Devolver la nueva plataforma creada
    }

    public void draw(Graphics g) {
        player.draw(g); // Dibujar jugador
        for (Platform platform : platforms) {
            platform.draw(g); // Dibujar cada plataforma
        }
    }

    public Player getPlayer() {
        return player; // Devolver la instancia del jugador
    }
}
