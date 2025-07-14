package jocketengine.collision;

import jocketengine.entities.Entity;
import jocketengine.events.EventManager;

import java.util.List;

/**
 * Sistema responsável por verificar colisões entre entidades com Collider.
 * <p>
 * Dispara eventos de colisão quando necessário.
 * </p>
 * 
 * @author Eddch
 */
public class CollisionSystem {

    /**
     * Verifica colisões entre todas as entidades com collider.
     * 
     * @param entities lista de entidades do jogo
     */
    public static void checkCollisions(List<Entity> entities) {
        for (int i = 0; i < entities.size(); i++) {
            for (int j = i + 1; j < entities.size(); j++) {
                Entity a = entities.get(i);
                Entity b = entities.get(j);

                if (a instanceof Collidable && b instanceof Collidable) {
                    Collider colliderA = ((Collidable) a).getCollider();
                    Collider colliderB = ((Collidable) b).getCollider();

                    if (colliderA.isColliding(colliderB)) {
                        EventManager.fireEvent(new CollisionEvent(a, b));
                    }
                }
            }
        }
    }

    /**
     * Interface que define uma entidade que possui collider.
     */
    public interface Collidable {
        Collider getCollider();
    }
}