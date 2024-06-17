package LuisAxelCruzG;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

/**
 * Clase para capturar eventos del teclado y gestionar el estado de las teclas.
 * Implementa KeyListener para manejar eventos de teclado como pulsaciones y liberaciones de teclas.
 * 
 * @autor axeli
 */
public class KeyBoard implements KeyListener {
    
    private boolean[] keys = new boolean[256]; // Arreglo para almacenar el estado de las teclas
    
    public static boolean LEFT, RIGHT; // Variables estáticas para las teclas de movimiento
    
    /**
     * Constructor de la clase KeyBoard.
     * Inicializa las variables de control de las teclas.
     */
    public KeyBoard() {
        LEFT = false;
        RIGHT = false;
    }
    
    /**
     * Método para actualizar el estado de las teclas LEFT y RIGHT.
     * LEFT se establece true si la tecla A está presionada.
     * RIGHT se establece true si la tecla D está presionada.
     */
    public void update() {
        RIGHT = keys[KeyEvent.VK_D]; // TRUE si se presiona la tecla D
        LEFT = keys[KeyEvent.VK_A];  // TRUE si se presiona la tecla A
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
        keys[e.getKeyCode()] = true; // Marca la tecla presionada en el arreglo
    }

    @Override
    public void keyReleased(KeyEvent e) {
        keys[e.getKeyCode()] = false; // Marca la tecla liberada en el arreglo
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
        // Método no usado esta nada mas porque esta implementada la clase
    }
}