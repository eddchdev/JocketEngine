package jocketengine.entities;

import jocketengine.utils.Rectangle;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Classe base para todas as entidades do jogo.
 * <p>
 * Uma entidade tem posição, tamanho e uma lista de {@link Component componentes}
 * reutilizáveis (física, animação, comportamentos...). Os componentes são
 * atualizados automaticamente antes da lógica da própria entidade.
 * </p>
 *
 * <pre>{@code
 * class Caixa extends Entity {
 *     Caixa(float x, float y) {
 *         super(x, y, 16, 16);
 *         addComponent(new PhysicsComponent(this)).gravity = 600f;
 *     }
 *     public void update(float dt) { }            // componentes cuidam do movimento
 *     public void render(Graphics g) { ... }
 * }
 * }</pre>
 *
 * @author Eddch
 */
public abstract class Entity {

    protected float x;
    protected float y;
    protected int width;
    protected int height;

    private final List<Component> components = new ArrayList<>();
    private boolean alive = true;

    /**
     * @param x      posição horizontal
     * @param y      posição vertical
     * @param width  largura
     * @param height altura
     */
    public Entity(float x, float y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    /**
     * Atualiza a lógica específica da entidade (chamada a cada passo de lógica).
     *
     * @param dt tempo, em segundos, desde o passo anterior
     */
    public abstract void update(float dt);

    /**
     * Desenha a entidade.
     *
     * @param g contexto gráfico
     */
    public abstract void render(Graphics g);

    /**
     * Atualiza os componentes e depois a entidade. Chamado pelo {@link EntityManager}.
     *
     * @param dt delta time em segundos
     */
    public final void tick(float dt) {
        for (Component component : components) {
            component.update(dt);
        }
        update(dt);
    }

    /**
     * Anexa um componente a esta entidade.
     *
     * @param component componente a anexar
     * @param <T>       tipo do componente
     * @return o próprio componente, para encadear configuração
     */
    public <T extends Component> T addComponent(T component) {
        components.add(component);
        return component;
    }

    /**
     * Busca o primeiro componente do tipo informado.
     *
     * @param type classe do componente
     * @param <T>  tipo do componente
     * @return o componente, ou {@code null} se não houver
     */
    public <T extends Component> T getComponent(Class<T> type) {
        for (Component component : components) {
            if (type.isInstance(component)) {
                return type.cast(component);
            }
        }
        return null;
    }

    /** @return true se a entidade possui um componente do tipo informado. */
    public boolean hasComponent(Class<? extends Component> type) {
        return getComponent(type) != null;
    }

    /** @return a caixa delimitadora (AABB) atual da entidade. */
    public Rectangle getBounds() {
        return new Rectangle(x, y, width, height);
    }

    /** @return true se a entidade está viva (deve permanecer no jogo). */
    public boolean isAlive() {
        return alive;
    }

    /** Marca a entidade para remoção pelo {@link EntityManager} no próximo passo. */
    public void destroy() {
        this.alive = false;
    }

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
