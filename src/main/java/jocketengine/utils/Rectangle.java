package jocketengine.utils;

/**
 * Representa um retângulo de colisão axis-aligned (AABB).
 * <p>
 * Pode ser usado para colisões simples entre entidades.
 * </p>
 * 
 * @author Eddch
 */
public class Rectangle {

    public float x, y, width, height;

    public Rectangle(float x, float y, float width, float height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    public boolean intersects(Rectangle other) {
        return this.x < other.x + other.width &&
               this.x + this.width > other.x &&
               this.y < other.y + other.height &&
               this.y + this.height > other.y;
    }
}