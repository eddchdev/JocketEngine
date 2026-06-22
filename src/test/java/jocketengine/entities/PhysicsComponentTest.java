package jocketengine.entities;

import org.junit.jupiter.api.Test;

import java.awt.Graphics;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PhysicsComponentTest {

    static class Body extends Entity {
        Body() {
            super(0, 0, 1, 1);
        }

        @Override
        public void update(float dt) {
        }

        @Override
        public void render(Graphics g) {
        }
    }

    @Test
    void velocityMovesTheEntity() {
        Body body = new Body();
        PhysicsComponent physics = body.addComponent(new PhysicsComponent(body));
        physics.velocity.x = 10;

        body.tick(1f);

        assertEquals(10, body.getX(), 1e-4);
    }

    @Test
    void gravityAcceleratesAndMovesDown() {
        Body body = new Body();
        PhysicsComponent physics = body.addComponent(new PhysicsComponent(body));
        physics.gravity = 100;

        body.tick(1f);

        assertEquals(100, physics.velocity.y, 1e-4);
        assertEquals(100, body.getY(), 1e-4);
    }

    @Test
    void accelerationChangesVelocity() {
        Body body = new Body();
        PhysicsComponent physics = body.addComponent(new PhysicsComponent(body));
        physics.acceleration.x = 10;

        body.tick(1f);

        assertEquals(10, physics.velocity.x, 1e-4);
    }
}
