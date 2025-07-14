package jocketengine.collision;

import jocketengine.utils.Vector2;

/**
 * Define uma caixa de colisão (AABB) associada a uma entidade.
 * <p>
 * Usada para verificar interseção com outros objetos do jogo.
 * </p>
 * 
 * @author Eddch
 */
public class Collider {

    private Vector2 position;
    private float width, height;

    /**
     * Construtor.
     * 
     * @param position posição do canto superior esquerdo
     * @param width    largura da colisão
     * @param height   altura da colisão
     */
    public Collider(Vector2 position, float width, float height) {
        this.position = position;
        this.width = width;
        this.height = height;
    }

    /**
     * Verifica se este collider está colidindo com outro.
     * 
     * @param other outro collider
     * @return true se houver interseção
     */
    public boolean isColliding(Collider other) {
        return position.x < other.position.x + other.width &&
               position.x + width > other.position.x &&
               position.y < other.position.y + other.height &&
               position.y + height > other.position.y;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }
}