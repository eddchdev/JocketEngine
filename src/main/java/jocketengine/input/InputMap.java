package jocketengine.input;

import java.util.HashMap;
import java.util.Map;

/**
 * Mapeia <b>ações</b> nomeadas para teclas, desacoplando a lógica do jogo das
 * teclas físicas e permitindo rebind fácil.
 * <pre>{@code
 * InputMap controls = new InputMap()
 *         .bind("left",  KeyEvent.VK_LEFT, KeyEvent.VK_A)
 *         .bind("right", KeyEvent.VK_RIGHT, KeyEvent.VK_D)
 *         .bind("jump",  KeyEvent.VK_SPACE, KeyEvent.VK_W, KeyEvent.VK_UP);
 *
 * if (controls.isDown("left"))  player.moveLeft();
 * if (controls.isPressed("jump")) player.jump();
 * }</pre>
 *
 * @author Eddch
 */
public class InputMap {

    private final Map<String, int[]> bindings = new HashMap<>();

    /** Associa uma ação a uma ou mais teclas (KeyEvent.VK_*). Retorna {@code this}. */
    public InputMap bind(String action, int... keys) {
        bindings.put(action, keys.clone());
        return this;
    }

    /** @return true enquanto qualquer tecla da ação estiver pressionada. */
    public boolean isDown(String action) {
        int[] keys = bindings.get(action);
        if (keys == null) {
            return false;
        }
        for (int key : keys) {
            if (Input.isKeyDown(key)) {
                return true;
            }
        }
        return false;
    }

    /** @return true apenas no passo em que alguma tecla da ação foi pressionada. */
    public boolean isPressed(String action) {
        int[] keys = bindings.get(action);
        if (keys == null) {
            return false;
        }
        for (int key : keys) {
            if (Input.isKeyPressed(key)) {
                return true;
            }
        }
        return false;
    }

    /** @return teclas associadas à ação (vazio se não houver). */
    public int[] getKeys(String action) {
        int[] keys = bindings.get(action);
        return keys == null ? new int[0] : keys.clone();
    }

    public boolean has(String action) {
        return bindings.containsKey(action);
    }
}
