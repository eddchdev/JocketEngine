package jocketengine.utils;

/**
 * Vetor 2D usado em posições, direções e velocidades, com as operações mais
 * comuns em jogos.
 * <p>
 * Os métodos aritméticos ({@link #add}, {@link #sub}, {@link #scale},
 * {@link #normalized}, {@link #lerp}) <b>não modificam</b> o vetor original,
 * retornando um novo vetor — o que evita efeitos colaterais difíceis de rastrear.
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
        return new Vector2(x + other.x, y + other.y);
    }

    public Vector2 sub(Vector2 other) {
        return new Vector2(x - other.x, y - other.y);
    }

    public Vector2 scale(float scalar) {
        return new Vector2(x * scalar, y * scalar);
    }

    /** @return comprimento (magnitude) do vetor. */
    public float length() {
        return (float) Math.sqrt(x * x + y * y);
    }

    /** @return comprimento ao quadrado (mais barato que {@link #length()}). */
    public float lengthSquared() {
        return x * x + y * y;
    }

    /** @return produto escalar com {@code other}. */
    public float dot(Vector2 other) {
        return x * other.x + y * other.y;
    }

    /** @return distância até {@code other}. */
    public float distance(Vector2 other) {
        float dx = x - other.x;
        float dy = y - other.y;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /** @return uma cópia normalizada (comprimento 1), ou (0,0) se este vetor for nulo. */
    public Vector2 normalized() {
        float len = length();
        return len == 0 ? new Vector2() : new Vector2(x / len, y / len);
    }

    /** Interpola linearmente em direção a {@code other} (t em 0..1). */
    public Vector2 lerp(Vector2 other, float t) {
        return new Vector2(x + (other.x - x) * t, y + (other.y - y) * t);
    }

    public void set(float x, float y) {
        this.x = x;
        this.y = y;
    }

    public Vector2 copy() {
        return new Vector2(x, y);
    }

    @Override
    public String toString() {
        return "Vector2(" + x + ", " + y + ")";
    }
}
