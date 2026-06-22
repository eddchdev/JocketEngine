package jocketengine.scene;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.awt.Graphics;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertSame;

class SceneManagerTest {

    static class StubScene extends Scene {
        int loads;
        int exits;
        int updates;

        @Override
        public void onLoad() {
            loads++;
        }

        @Override
        public void update(float dt) {
            updates++;
        }

        @Override
        public void render(Graphics g) {
        }

        @Override
        public void onExit() {
            exits++;
        }
    }

    @BeforeEach
    void reset() {
        SceneManager.clear();
    }

    @Test
    void changeSceneReplacesAndRunsLifecycle() {
        StubScene a = new StubScene();
        SceneManager.changeScene(a);
        assertSame(a, SceneManager.getCurrent());
        assertEquals(1, a.loads);

        StubScene b = new StubScene();
        SceneManager.changeScene(b);
        assertSame(b, SceneManager.getCurrent());
        assertEquals(1, a.exits);
    }

    @Test
    void onlyTopSceneIsUpdated() {
        StubScene bottom = new StubScene();
        StubScene top = new StubScene();
        SceneManager.changeScene(bottom);
        SceneManager.pushScene(top);

        SceneManager.update(0.1f);

        assertEquals(1, top.updates);
        assertEquals(0, bottom.updates);
    }

    @Test
    void popReturnsToPreviousScene() {
        StubScene bottom = new StubScene();
        StubScene top = new StubScene();
        SceneManager.changeScene(bottom);
        SceneManager.pushScene(top);

        SceneManager.popScene();

        assertSame(bottom, SceneManager.getCurrent());
        assertEquals(1, top.exits);
    }

    @Test
    void clearEmptiesTheStack() {
        SceneManager.changeScene(new StubScene());
        SceneManager.clear();
        assertNull(SceneManager.getCurrent());
    }
}
