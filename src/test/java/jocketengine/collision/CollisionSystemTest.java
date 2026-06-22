package jocketengine.collision;

import jocketengine.entities.Entity;
import jocketengine.events.EventManager;
import jocketengine.events.EventPriority;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class CollisionSystemTest {

    static class Box extends Entity {
        Box(float x, float y) {
            super(x, y, 10, 10);
        }

        @Override
        public void update(float dt) {
        }

        @Override
        public void render(Graphics g) {
        }
    }

    @BeforeEach
    void clearListeners() {
        EventManager.clearAllListeners();
    }

    @Test
    void overlappingEntitiesFireCollisionEvent() {
        List<CollisionEvent> fired = new ArrayList<>();
        EventManager.registerListener(CollisionEvent.class, EventPriority.NORMAL, fired::add);

        CollisionSystem.checkCollisions(List.of(new Box(0, 0), new Box(5, 5)));

        assertEquals(1, fired.size());
    }

    @Test
    void separatedEntitiesFireNothing() {
        List<CollisionEvent> fired = new ArrayList<>();
        EventManager.registerListener(CollisionEvent.class, EventPriority.NORMAL, fired::add);

        CollisionSystem.checkCollisions(List.of(new Box(0, 0), new Box(50, 50)));

        assertTrue(fired.isEmpty());
    }

    @Test
    void collidesHelperMatchesOverlap() {
        assertTrue(CollisionSystem.collides(new Box(0, 0), new Box(5, 5)));
        assertFalse(CollisionSystem.collides(new Box(0, 0), new Box(20, 20)));
    }
}
