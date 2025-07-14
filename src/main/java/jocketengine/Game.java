package jocketengine;

import jocketengine.input.Input;
import jocketengine.graphics.OpenGLWindow;
import jocketengine.scene.SceneManager;
import jocketengine.scene.scenes.MainMenuScene;
import jocketengine.ui.UIManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Classe principal da JocketEngine.
 * Responsável por iniciar o loop principal do jogo (update e render),
 * criar a janela Java2D ou OpenGL e inicializar sistemas.
 *
 * <p>
 * Usa Java2D com {@link Canvas} para renderização tradicional,
 * e possui suporte alternativo a OpenGL via LWJGL.
 * </p>
 *
 * @author Eddch
 */
public class Game extends Canvas implements Runnable {

    private static final int WIDTH = 480;
    private static final int HEIGHT = 270;
    private static final int SCALE = 3;
    private static final String TITLE = "JocketEngine Demo";

    private static JFrame frame;
    private static Game instance;

    private boolean running = false;
    private Thread gameThread;
    private long lastTime;

    /**
     * Ponto de entrada da aplicação.
     * Inicia o jogo em modo Java2D ou OpenGL.
     *
     * @param args argumentos de linha de comando
     */
    public static void main(String[] args) {
        boolean enableOpenGL = true; // Troque para true para iniciar com OpenGL

        if (enableOpenGL) {
            new OpenGLWindow().run();
        } else {
            instance = new Game();
            instance.start();
        }
    }

    /**
     * Construtor que cria a janela Java2D e configura input.
     */
    public Game() {
        setPreferredSize(new Dimension(WIDTH * SCALE, HEIGHT * SCALE));
        frame = new JFrame(TITLE);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(this);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Input.setup(this);
        this.addKeyListener(Input.getKeyListener());
        this.addMouseListener(Input.getMouseListener());
        this.addMouseMotionListener(Input.getMouseMotionListener());
        this.setFocusable(true);
    }

    /**
     * Inicia o loop do jogo em uma thread separada.
     */
    public synchronized void start() {
        if (running) return;
        running = true;
        gameThread = new Thread(this, "GameThread");
        gameThread.start();
    }

    /**
     * Para o jogo com segurança.
     */
    public synchronized void stop() {
        if (!running) return;
        running = false;
        try {
            gameThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    /**
     * Loop principal do jogo.
     * Atualiza e renderiza a cada ciclo.
     */
    @Override
    public void run() {
        this.createBufferStrategy(3);
        BufferStrategy bs = this.getBufferStrategy();
        Graphics g;

        SceneManager.changeScene(new MainMenuScene());
        lastTime = System.nanoTime();

        while (running) {
            long now = System.nanoTime();
            float deltaTime = (now - lastTime) / 1_000_000_000f;
            lastTime = now;

            update(deltaTime);

            g = bs.getDrawGraphics();
            render(g);
            g.dispose();
            bs.show();

            try {
                Thread.sleep(16); // ~60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Atualiza entrada, cena e interface.
     *
     * @param dt delta time (em segundos)
     */
    private void update(float dt) {
        Input.update();
        SceneManager.update(dt);
        UIManager.update(dt);
    }

    /**
     * Renderiza cena e interface gráfica.
     *
     * @param g contexto gráfico
     */
    private void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        SceneManager.render(g);
        UIManager.render(g);
    }

    /**
     * Retorna instância do jogo.
     */
    public static Game getInstance() {
        return instance;
    }

    /**
     * Largura lógica do jogo (sem escala).
     */
    public static int getGameWidth() {
        return WIDTH;
    }

    /**
     * Altura lógica do jogo (sem escala).
     */
    public static int getGameHeight() {
        return HEIGHT;
    }

    /**
     * Encerra o jogo com segurança.
     */
    public static void exit() {
        instance.stop();
        System.exit(0);
    }
}