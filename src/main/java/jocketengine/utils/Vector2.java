package jocketengine.utils;

/**
 * Representa um vetor 2D usado em posições, direções ou velocidades.
 * <p>
 * Contém operações básicas como soma, multiplicação escalar, etc.
 * </p>
 * 
 * @author Eddch
 */
public class Vector2 {

    public float x;
    public float y;

    public Vector2() {
        this(0, 0);
    }

    public Vector2(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 add(Vector2 other) {
        return new Vector2(this.x + other.x, this.y + other.y);
    }

    public Vector2 scale(float scalar) {
        return new Vector2(this.x * scalar, this.y * scalar);
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 copy() {
        return new Vector2(x, y);
    }
}