package jocketengine.physics;

import jocketengine.entities.Entity;
import jocketengine.entities.PhysicsComponent;

import java.util.List;
import java.util.Map;

/**
 * Sistema de física global da JocketEngine.
 * <p>
 * Aplica gravidade, aceleração e movimento às entidades com componente físico.
 * </p>
 * 
 * @author Eddch
 */
public class PhysicsSystem {

    private final Map<Entity, PhysicsComponent> physicsEntities;

    public PhysicsSystem(Map<Entity, PhysicsComponent> physicsEntities) {
        this.physicsEntities = physicsEntities;
    }

    /**
     * Atualiza todas as entidades com física ativa.
     * 
     * @param dt delta time (segundos desde o último frame)
     */
    public void update(float dt) {
        for (Map.Entry<Entity, PhysicsComponent> entry : physicsEntities.entrySet()) {
            Entity entity = entry.getKey();
            PhysicsComponent physics = entry.getValue();

            physics.update(dt); // aplica aceleração e gravidade

            // controle de colisão com o "chão" pode ser integrado aqui ou via CollisionSystem
            // Exemplo simples:
            if (entity.getY() + entity.getHeight() >= 720) { // chão fixo em y = 720
                entity.setY(720 - entity.getHeight());
                physics.velocity.y = 0;
                physics.onGround = true;
            }
        }
    }
}