package jocketengine.entities;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Armazena, atualiza e renderiza um conjunto de {@link Entity entidades}.
 * <p>
 * Entidades marcadas com {@link Entity#destroy()} são removidas automaticamente
 * no início do passo seguinte.
 * </p>
 *
 * @author Eddch
 */
public class EntityManager {

    private final List<Entity> entities = new ArrayList<>();
    private final List<Entity> pendingAdd = new ArrayList<>();

    /**
     * Adiciona uma entidade. A inclusão acontece no próximo {@link #update(float)},
     * o que torna seguro adicionar entidades durante a iteração.
     *
     * @param entity entidade a adicionar
     */
    public void addEntity(Entity entity) {
        pendingAdd.add(entity);
    }

    /**
     * Atualiza todas as entidades vivas e remove as destruídas.
     *
     * @param dt delta time em segundos
     */
    public void update(float dt) {
        if (!pendingAdd.isEmpty()) {
            entities.addAll(pendingAdd);
            pendingAdd.clear();
        }

        entities.removeIf(e -> !e.isAlive());

        // Cópia defensiva: uma entidade pode adicionar/remover outras durante o update.
        for (Entity entity : new ArrayList<>(entities)) {
            if (entity.isAlive()) {
                entity.tick(dt);
            }
        }
    }

    /**
     * Renderiza todas as entidades.
     *
     * @param g contexto gráfico
     */
    public void render(Graphics g) {
        for (Entity entity : entities) {
            entity.render(g);
        }
    }

    /** @return lista imutável das entidades atualmente ativas. */
    public List<Entity> getEntities() {
        return Collections.unmodifiableList(entities);
    }

    /** Remove todas as entidades imediatamente. */
    public void clear() {
        entities.clear();
        pendingAdd.clear();
    }
}
