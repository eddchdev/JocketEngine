package jocketengine.entities;

import java.awt.Graphics;

/**
 * Classe base abstrata para todas as entidades do jogo na JocketEngine.
 * <p>
 * Representa um objeto com posição, dimensões e comportamento.
 * Deve ser estendida para criar entidades específicas.
 * </p>
 * 
 * @author Eddch
 */
public abstract class Entity {

    protected float x, y;
    protected int width, height;

    /**
     * Construtor padrão.
     * 
     * @param x      posição horizontal
     * @param y      posição vertical
     * @param width  largura da entidade
     * @param height altura da entidade
     */
    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Atualiza a lógica da entidade.
     * Chamado a cada frame.
     * 
     * @param dt delta time — tempo em segundos desde o último frame
     */
    public abstract void update(float dt);

    /**
     * Renderiza a entidade na tela.
     * 
     * @param g objeto Graphics para desenhar
     */
    public abstract void render(Graphics g);

    // Getters e setters básicos

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }
}