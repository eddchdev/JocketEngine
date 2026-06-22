package jocketengine.input;

import org.junit.jupiter.api.Test;

import java.awt.event.KeyEvent;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class InputMapTest {

    @Test
    void storesBindings() {
        InputMap map = new InputMap().bind("jump", KeyEvent.VK_SPACE, KeyEvent.VK_W);
        assertTrue(map.has("jump"));
        assertArrayEquals(new int[]{KeyEvent.VK_SPACE, KeyEvent.VK_W}, map.getKeys("jump"));
    }

    @Test
    void unboundActionsAreInactive() {
        InputMap map = new InputMap();
        assertFalse(map.has("run"));
        assertFalse(map.isDown("run"));
        assertFalse(map.isPressed("run"));
        assertArrayEquals(new int[0], map.getKeys("run"));
    }

    @Test
    void boundKeysAreNotPressedWithoutInput() {
        InputMap map = new InputMap().bind("jump", KeyEvent.VK_SPACE);
        // Sem eventos de teclado, nenhuma ação está ativa.
        assertFalse(map.isDown("jump"));
    }
}
