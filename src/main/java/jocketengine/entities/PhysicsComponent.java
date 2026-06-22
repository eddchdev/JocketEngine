package jocketengine.entities;

import jocketengine.utils.Vector2;

/**
 * Componente de física simples: integra aceleração, gravidade e velocidade,
 * movendo a entidade dona a cada passo.
 *
 * <pre>{@code
 * PhysicsComponent physics = entidade.addComponent(new PhysicsComponent(entidade));
 * physics.gravity = 800f;
 * physics.velocity.x = 120f;
 * }</pre>
 *
 * @author Eddch
 */
public class PhysicsComponent implements Component {

    /** Velocidade atual, em pixels por segundo. */
    public final Vector2 velocity = new Vector2();

    /** Aceleração aplicada continuamente, em pixels por segundo². */
    public final Vector2 acceleration = new Vector2();

    /** Gravidade aplicada no eixo Y, em pixels por segundo². */
    public float gravity = 0f;

    private final Entity parent;

    public PhysicsComponent(Entity parent) {
        this.parent = parent;
    }

    @Override
    public void update(float dt) {
        velocity.x += acceleration.x * dt;
        velocity.y += (acceleration.y + gravity) * dt;

        parent.setX(parent.getX() + velocity.x * dt);
        parent.setY(parent.getY() + velocity.y * dt);
    }

    /** Zera velocidade e aceleração. */
    public void reset() {
        velocity.set(0, 0);
        acceleration.set(0, 0);
    }
}
