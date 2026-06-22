package jocketengine.collision;

import jocketengine.entities.Entity;
import jocketengine.events.EventManager;

import java.util.List;

/**
 * Detecção de colisões AABB entre entidades.
 * <p>
 * Compara as caixas delimitadoras ({@link Entity#getBounds()}) e dispara um
 * {@link CollisionEvent} para cada par sobreposto.
 * </p>
 *
 * @author Eddch
 */
public final class CollisionSystem {

    private CollisionSystem() {
    }

    /**
     * Verifica todos os pares de entidades e dispara {@link CollisionEvent}
     * para os que estiverem colidindo.
     *
     * @param entities entidades a testar
     */
    public static void checkCollisions(List<Entity> entities) {
        for (int i = 0; i < entities.size(); i++) {
            Entity a = entities.get(i);
            for (int j = i + 1; j < entities.size(); j++) {
                Entity b = entities.get(j);
                if (a.getBounds().intersects(b.getBounds())) {
                    EventManager.fireEvent(new CollisionEvent(a, b));
                }
            }
        }
    }

    /**
     * @param a primeira entidade
     * @param b segunda entidade
     * @return true se as caixas delimitadoras das duas entidades se sobrepõem
     */
    public static boolean collides(Entity a, Entity b) {
        return a.getBounds().intersects(b.getBounds());
    }
}
