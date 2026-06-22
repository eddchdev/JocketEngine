package jocketengine.input;

import java.awt.Component;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Sistema global de entrada da JocketEngine.
 * <p>
 * Captura o estado de teclado e mouse de forma centralizada e estática:
 * </p>
 * <pre>{@code
 * if (Input.isKeyDown(KeyEvent.VK_SPACE)) { ... }   // enquanto segurado
 * if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) { ... } // só no passo em que foi pressionado
 * }</pre>
 *
 * <p>
 * As coordenadas do mouse são convertidas para o <b>espaço lógico</b> do jogo
 * (dividindo pela escala da janela), então combinam diretamente com as posições
 * usadas para desenhar cenas e UI.
 * </p>
 *
 * <p>
 * Os estados "deste passo" ({@link #isKeyPressed(int)}, {@link #isMousePressed()},
 * {@link #getTypedText()}) são válidos até a chamada de {@link #update()}, que a
 * {@link jocketengine.core.Engine} faz ao final de cada atualização de lógica.
 * </p>
 *
 * @author Eddch
 */
public final class Input {

    private static final int MAX_KEYS = 256;
    private static final int MAX_MOUSE = 3;

    private static final boolean[] keys = new boolean[MAX_KEYS];
    private static final boolean[] keysPressed = new boolean[MAX_KEYS];
    private static final boolean[] keysReleased = new boolean[MAX_KEYS];

    private static final boolean[] mouseButtons = new boolean[MAX_MOUSE];
    private static boolean mousePressed = false;
    private static boolean mouseHeld = false;

    private static int mouseX = 0;
    private static int mouseY = 0;
    private static int scale = 1;

    private static final Queue<Character> typedCharacters = new LinkedList<>();

    private static boolean attached = false;

    private Input() {
    }

    private static final KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if (code >= 0 && code < MAX_KEYS) {
                if (!keys[code]) {
                    keysPressed[code] = true;
                }
                keys[code] = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int code = e.getKeyCode();
            if (code >= 0 && code < MAX_KEYS) {
                keys[code] = false;
                keysReleased[code] = true;
            }
        }

        @Override
        public void keyTyped(KeyEvent e) {
            char c = e.getKeyChar();
            if (!Character.isISOControl(c)) {
                typedCharacters.add(c);
            }
        }
    };

    private static final MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            int button = e.getButton();
            if (button >= 1 && button <= MAX_MOUSE) {
                mouseButtons[button - 1] = true;
                mouseHeld = true;
                mousePressed = true;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            int button = e.getButton();
            if (button >= 1 && button <= MAX_MOUSE) {
                mouseButtons[button - 1] = false;
                mouseHeld = false;
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX() / scale;
            mouseY = e.getY() / scale;
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseMoved(e);
        }
    };

    /**
     * Conecta o sistema de entrada a um componente (a {@code Canvas} da engine).
     * Idempotente: registrar listeners mais de uma vez não tem efeito.
     *
     * @param component componente que receberá os eventos
     * @param scale     escala da janela, usada para converter o mouse ao espaço lógico
     */
    public static void attach(Component component, int scale) {
        Input.scale = Math.max(1, scale);
        if (attached) {
            return;
        }
        component.addKeyListener(keyAdapter);
        component.addMouseListener(mouseAdapter);
        component.addMouseMotionListener(mouseAdapter);
        component.setFocusable(true);
        component.requestFocusInWindow();
        attached = true;
    }

    /**
     * Limpa os estados de "deste passo". Chamado pela engine ao final de cada
     * atualização de lógica, depois que cenas e UI já leram a entrada.
     */
    public static void update() {
        mousePressed = false;
        for (int i = 0; i < MAX_KEYS; i++) {
            keysPressed[i] = false;
            keysReleased[i] = false;
        }
        typedCharacters.clear();
    }

    /** @return true enquanto a tecla estiver sendo mantida pressionada. */
    public static boolean isKeyDown(int keyCode) {
        return keyCode >= 0 && keyCode < MAX_KEYS && keys[keyCode];
    }

    /** @return true apenas no passo em que a tecla foi pressionada. */
    public static boolean isKeyPressed(int keyCode) {
        return keyCode >= 0 && keyCode < MAX_KEYS && keysPressed[keyCode];
    }

    /** @return true apenas no passo em que a tecla foi solta. */
    public static boolean isKeyReleased(int keyCode) {
        return keyCode >= 0 && keyCode < MAX_KEYS && keysReleased[keyCode];
    }

    /** @return true enquanto o botão esquerdo do mouse estiver pressionado. */
    public static boolean isMouseDown() {
        return mouseHeld;
    }

    /** @return true apenas no passo em que o mouse foi clicado. */
    public static boolean isMousePressed() {
        return mousePressed;
    }

    /** @return posição X do cursor no espaço lógico do jogo. */
    public static int getMouseX() {
        return mouseX;
    }

    /** @return posição Y do cursor no espaço lógico do jogo. */
    public static int getMouseY() {
        return mouseY;
    }

    /**
     * Retorna (e consome) o texto digitado neste passo. Ideal para campos de texto.
     *
     * @return caracteres digitados desde o último {@link #update()}
     */
    public static String getTypedText() {
        StringBuilder sb = new StringBuilder();
        for (Character c : typedCharacters) {
            sb.append(c);
        }
        return sb.toString();
    }
}
