package jocketengine.entities;

import jocketengine.utils.Vector2;

/**
 * Componente de física para aplicar velocidade, aceleração e gravidade.
 * 
 * @author Eddch
 */
public class PhysicsComponent implements Component {

    public Vector2 velocity = new Vector2(0, 0);
    public Vector2 acceleration = new Vector2(0, 0);
    public float gravity = 0f;
    public boolean onGround = false;

    private final Entity parent;

    public PhysicsComponent(Entity parent) {
        this.parent = parent;
    }

    @Override
    public void update(float dt) {
        velocity.add(acceleration.scale(dt));
        velocity.y += gravity * dt;

        parent.setX(parent.getX() + velocity.x * dt);
        parent.setY(parent.getY() + velocity.y * dt);
    }

    public void reset() {
        velocity.set(0, 0);
        acceleration.set(0, 0);
    }
}