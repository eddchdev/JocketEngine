package jocketengine.input;

import java.awt.Component;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Sistema global de entrada da JocketEngine.
 * <p>
 * Captura estados de teclado e mouse de forma centralizada,
 * incluindo suporte a teclas pressionadas, mouse, e caracteres digitados.
 * </p>
 *
 * <p>
 * Este sistema é projetado para ser usado de forma estática:
 * {@code if (Input.isKeyDown(KeyEvent.VK_SPACE)) {...}}
 * </p>
 *
 * @author Eddch
 */
public class Input {

    private static final int MAX_KEYS = 256;
    private static final int MAX_MOUSE = 3;

    /** Estado atual de teclas pressionadas. */
    private static final boolean[] keys = new boolean[MAX_KEYS];

    /** Marca teclas pressionadas neste frame. */
    private static final boolean[] keysPressed = new boolean[MAX_KEYS];

    /** Estado atual dos botões do mouse. */
    private static final boolean[] mouseButtons = new boolean[MAX_MOUSE];

    /** Marca se o botão do mouse foi pressionado neste frame. */
    private static boolean mousePressed = false;

    /** Marca se o botão do mouse está sendo segurado. */
    private static boolean mouseHeld = false;

    /** Coordenadas do mouse na tela. */
    private static int mouseX = 0, mouseY = 0;

    /** Fila de caracteres digitados no frame atual. */
    private static final Queue<Character> typedCharacters = new LinkedList<>();

    /** Listener para eventos de teclado. */
    private static final KeyAdapter keyAdapter = new KeyAdapter() {
        @Override
        public void keyPressed(KeyEvent e) {
            int code = e.getKeyCode();
            if (code < MAX_KEYS) {
                if (!keys[code]) keysPressed[code] = true;
                keys[code] = true;
            }
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int code = e.getKeyCode();
            if (code < MAX_KEYS) {
                keys[code] = false;
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

    /** Listener para eventos de mouse. */
    private static final MouseAdapter mouseAdapter = new MouseAdapter() {
        @Override
        public void mousePressed(MouseEvent e) {
            if (e.getButton() <= MAX_MOUSE) {
                mouseButtons[e.getButton() - 1] = true;
                mouseHeld = true;
                mousePressed = true;
            }
        }

        @Override
        public void mouseReleased(MouseEvent e) {
            if (e.getButton() <= MAX_MOUSE) {
                mouseButtons[e.getButton() - 1] = false;
                mouseHeld = false;
            }
        }

        @Override
        public void mouseMoved(MouseEvent e) {
            mouseX = e.getX();
            mouseY = e.getY();
        }

        @Override
        public void mouseDragged(MouseEvent e) {
            mouseMoved(e);
        }
    };

    /**
     * Inicializa o sistema de entrada num componente Swing (Canvas, JPanel...).
     *
     * @param c componente onde os eventos de entrada serão escutados
     */
    public static void setup(Component c) {
        c.addKeyListener(keyAdapter);
        c.addMouseListener(mouseAdapter);
        c.addMouseMotionListener(mouseAdapter);
        c.setFocusable(true);
        c.requestFocusInWindow();
    }

    /**
     * Atualiza o estado interno do sistema de entrada.
     * Deve ser chamado ao final de cada frame.
     */
    public static void update() {
        mousePressed = false;

        for (int i = 0; i < MAX_KEYS; i++) {
            keysPressed[i] = false;
        }

        typedCharacters.clear();
    }

    /**
     * Verifica se uma tecla está atualmente pressionada.
     *
     * @param keyCode código da tecla (KeyEvent.VK_*)
     * @return true se a tecla está sendo mantida pressionada
     */
    public static boolean isKeyDown(int keyCode) {
        return keyCode < MAX_KEYS && keys[keyCode];
    }

    /**
     * Verifica se uma tecla foi pressionada neste frame.
     *
     * @param keyCode código da tecla (KeyEvent.VK_*)
     * @return true se a tecla foi pressionada agora
     */
    public static boolean isKeyPressed(int keyCode) {
        return keyCode < MAX_KEYS && keysPressed[keyCode];
    }

    /**
     * Verifica se o botão esquerdo do mouse está sendo segurado.
     *
     * @return true se o mouse está pressionado
     */
    public static boolean isMouseDown() {
        return mouseHeld;
    }

    /**
     * Verifica se o botão esquerdo do mouse foi clicado neste frame.
     *
     * @return true se foi clicado agora
     */
    public static boolean isMousePressed() {
        return mousePressed;
    }

    /**
     * Retorna a posição X atual do cursor do mouse.
     *
     * @return coordenada X do mouse
     */
    public static int getMouseX() {
        return mouseX;
    }

    /**
     * Retorna a posição Y atual do cursor do mouse.
     *
     * @return coordenada Y do mouse
     */
    public static int getMouseY() {
        return mouseY;
    }

    /**
     * Retorna o texto digitado pelo usuário neste frame.
     * Ideal para campos de texto ou chat.
     *
     * @return string com os caracteres digitados
     */
    public static String getTypedText() {
        StringBuilder sb = new StringBuilder();
        while (!typedCharacters.isEmpty()) {
            sb.append(typedCharacters.poll());
        }
        return sb.toString();
    }

    /**
     * Retorna o listener de teclado utilizado internamente.
     *
     * @return KeyListener
     */
    public static KeyListener getKeyListener() {
        return keyAdapter;
    }

    /**
     * Retorna o listener de mouse utilizado internamente.
     *
     * @return MouseListener
     */
    public static MouseListener getMouseListener() {
        return mouseAdapter;
    }

    /**
     * Retorna o listener de movimento do mouse utilizado internamente.
     *
     * @return MouseMotionListener
     */
    public static MouseMotionListener getMouseMotionListener() {
        return mouseAdapter;
    }
}
