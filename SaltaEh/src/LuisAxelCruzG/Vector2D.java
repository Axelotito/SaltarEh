package LuisAxelCruzG;

/**
 * Clase que representa un vector 2D con componentes x e y.
 * Da los componentes para que funcionen vectores
 * una idea que implemente al ver que lo recomendaban en un video
 * me arrepenti pero segui :( 
 * 
 * @autor axeli
 */
public class Vector2D {
//aca pondre algo
    private double x, y; // Componentes x e y del vector

    /**
     * Constructor que inicializa el vector con componentes específicas.
     * 
     * @param x Componente x del vector
     * @param y Componente y del vector
     */
    public Vector2D(double x, double y) {
        this.x = x;
        this.y = y;
    }

    /**
     * Suma el vector actual con otro vector dado.
     * 
     * @param v Vector a sumar
     * @return Un nuevo vector que es la suma del vector actual y el vector dado
     */
    public Vector2D add(Vector2D v) {
        return new Vector2D(x + v.getX(), y + v.getY());
    }

    /**
     * Obtiene la componente x del vector.
     * 
     * @return Componente x del vector
     */
    public double getX() {
        return x;
    }

    /**
     * Establece la componente x del vector.
     * 
     * @param x Nueva componente x del vector
     */
    public void setX(double x) {
        this.x = x;
    }

    /**
     * Obtiene la componente y del vector.
     * 
     * @return Componente y del vector
     */
    public double getY() {
        return y;
    }

    /**
     * Establece la componente y del vector.
     * 
     * @param y Nueva componente y del vector
     */
    public void setY(double y) {
        this.y = y;
    }

    /**
     * Constructor por defecto que inicializa el vector en (0,0).
     */
    public Vector2D() {
        x = 0;
        y = 0;
    }
}

