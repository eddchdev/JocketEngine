package jocketengine.entities;

import jocketengine.utils.Rectangle;
import org.junit.jupiter.api.Test;

import java.awt.Graphics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertSame;
import static org.junit.jupiter.api.Assertions.assertTrue;

class EntityTest {

    static class Dummy extends Entity {
        int updates;

        Dummy() {
            super(5, 10, 20, 30);
        }

        @Override
        public void update(float dt) {
            updates++;
        }

        @Override
        public void render(Graphics g) {
        }
    }

    static class CountingComponent implements Component {
        int updates;

        @Override
        public void update(float dt) {
            updates++;
        }
    }

    @Test
    void tickUpdatesComponentsAndEntity() {
        Dummy d = new Dummy();
        CountingComponent c = d.addComponent(new CountingComponent());

        d.tick(0.1f);

        assertEquals(1, c.updates);
        assertEquals(1, d.updates);
    }

    @Test
    void getComponentReturnsAttachedInstance() {
        Dummy d = new Dummy();
        CountingComponent c = d.addComponent(new CountingComponent());

        assertSame(c, d.getComponent(CountingComponent.class));
        assertTrue(d.hasComponent(CountingComponent.class));
    }

    @Test
    void boundsReflectPositionAndSize() {
        Rectangle b = new Dummy().getBounds();
        assertEquals(5, b.x, 1e-6);
        assertEquals(10, b.y, 1e-6);
        assertEquals(20, b.width, 1e-6);
        assertEquals(30, b.height, 1e-6);
    }

    @Test
    void destroyMarksEntityNotAlive() {
        Dummy d = new Dummy();
        assertTrue(d.isAlive());
        d.destroy();
        assertFalse(d.isAlive());
    }
}
