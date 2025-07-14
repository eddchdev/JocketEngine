package jocketengine.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Gerenciador global de entidades da JocketEngine.
 * <p>
 * Responsável por armazenar, atualizar e renderizar todas as entidades.
 * </p>
 * 
 * @author Eddch
 */
public class EntityManager {

    private final List<Entity> entities = new ArrayList<>();

    /**
     * Adiciona uma entidade à lista.
     * 
     * @param entity entidade a ser adicionada
     */
    public void addEntity(Entity entity) {
        entities.add(entity);
    }

    /**
     * Remove uma entidade da lista.
     * 
     * @param entity entidade a ser removida
     */
    public void removeEntity(Entity entity) {
        entities.remove(entity);
    }

    /**
     * Atualiza todas as entidades.
     * 
     * @param dt delta time (segundos desde o último frame)
     */
    public void update(float dt) {
        for (Entity entity : entities) {
            entity.update(dt);
        }
    }

    /**
     * Renderiza todas as entidades.
     * 
     * @param g objeto Graphics para desenhar
     */
    public void render(Graphics g) {
        for (Entity entity : entities) {
            entity.render(g);
        }
    }

    /**
     * Remove todas as entidades.
     */
    public void clear() {
        entities.clear();
    }

    /**
     * Retorna a lista de entidades (não modificável).
     * 
     * @return lista de entidades
     */
    public List<Entity> getEntities() {
        return List.copyOf(entities);
    }
}