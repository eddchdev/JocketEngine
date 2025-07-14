package jocketengine.collision;

import jocketengine.entities.Entity;
import jocketengine.events.Event;

/**
 * Evento disparado quando ocorre uma colisão entre duas entidades.
 * <p>
 * Pode ser escutado para detectar e reagir a colisões no jogo.
 * </p>
 * 
 * @author Eddch
 */
public class CollisionEvent extends Event {

    private final Entity entityA;
    private final Entity entityB;

    public CollisionEvent(Entity entityA, Entity entityB) {
        this.entityA = entityA;
        this.entityB = entityB;
    }

    public Entity getEntityA() {
        return entityA;
    }

    public Entity getEntityB() {
        return entityB;
    }
}