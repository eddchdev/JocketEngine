package jocketengine.core;

import jocketengine.input.Input;
import jocketengine.scene.Scene;
import jocketengine.scene.SceneManager;
import jocketengine.ui.UIManager;

import javax.swing.JFrame;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.image.BufferStrategy;
import java.awt.image.BufferedImage;

/**
 * Núcleo da JocketEngine: cria a janela, executa o laço principal do jogo
 * e coordena os subsistemas globais ({@link Input}, {@link SceneManager},
 * {@link UIManager}).
 *
 * <p>
 * Todo o jogo é renderizado em um <i>back buffer</i> com a resolução lógica
 * definida em {@link GameConfig} e, em seguida, ampliado para a janela usando
 * interpolação por vizinho-mais-próximo — o que mantém o visual pixel art nítido.
 * </p>
 *
 * <p>
 * O laço usa <b>passo de tempo fixo</b> (fixed timestep): a lógica é atualizada
 * sempre com o mesmo {@code dt}, independentemente da taxa de quadros, deixando
 * a física e o movimento determinísticos e estáveis.
 * </p>
 *
 * <p>Uso típico:</p>
 * <pre>{@code
 * Engine.start(new GameConfig().title("Meu Jogo"), new MainMenuScene());
 * }</pre>
 *
 * @author Eddch
 */
public final class Engine implements Runnable {

    /** Limite de atualizações encadeadas por quadro, para evitar a "espiral da morte". */
    private static final int MAX_UPDATES_PER_FRAME = 5;

    private static Engine instance;

    private final GameConfig config;
    private final Canvas canvas;
    private final BufferedImage backBuffer;
    private final Graphics2D bufferGraphics;

    private JFrame frame;
    private Thread thread;
    private volatile boolean running;

    private Engine(GameConfig config) {
        this.config = config;
        this.backBuffer = new BufferedImage(config.getWidth(), config.getHeight(), BufferedImage.TYPE_INT_RGB);
        this.bufferGraphics = backBuffer.createGraphics();

        int windowWidth = config.getWidth() * config.getScale();
        int windowHeight = config.getHeight() * config.getScale();

        this.canvas = new Canvas();
        Dimension size = new Dimension(windowWidth, windowHeight);
        canvas.setPreferredSize(size);
        canvas.setMinimumSize(size);
        canvas.setMaximumSize(size);
        canvas.setFocusable(true);
    }

    /**
     * Inicia a engine com a configuração dada e carrega a cena inicial.
     *
     * @param config       configuração da janela e do laço
     * @param initialScene primeira cena a ser exibida
     * @throws IllegalStateException se a engine já estiver em execução
     */
    public static void start(GameConfig config, Scene initialScene) {
        if (instance != null) {
            throw new IllegalStateException("A engine já foi iniciada");
        }
        instance = new Engine(config);
        instance.init(initialScene);
        instance.launchThread();
    }

    private void init(Scene initialScene) {
        frame = new JFrame(config.getTitle());
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setResizable(false);
        frame.add(canvas);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);

        Input.attach(canvas, config.getScale());
        canvas.requestFocus();

        SceneManager.changeScene(initialScene);
    }

    private synchronized void launchThread() {
        if (running) {
            return;
        }
        running = true;
        thread = new Thread(this, "JocketEngine-Main");
        thread.start();
    }

    @Override
    public void run() {
        canvas.createBufferStrategy(3);
        BufferStrategy bs = canvas.getBufferStrategy();

        final double nsPerUpdate = 1_000_000_000.0 / config.getTargetFps();
        final float dt = 1f / config.getTargetFps();

        long previous = System.nanoTime();
        double accumulator = 0;

        while (running) {
            long now = System.nanoTime();
            accumulator += now - previous;
            previous = now;

            int updates = 0;
            while (accumulator >= nsPerUpdate && updates < MAX_UPDATES_PER_FRAME) {
                tick(dt);
                accumulator -= nsPerUpdate;
                updates++;
            }

            render(bs);

            // Cede a CPU para não ocupar 100% de um núcleo desnecessariamente.
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
    }

    private void tick(float dt) {
        SceneManager.update(dt);
        UIManager.update(dt);
        // Limpa os estados de "pressionado neste passo" só depois que tudo já leu o input.
        Input.update();
    }

    private void render(BufferStrategy bs) {
        bufferGraphics.setColor(Color.BLACK);
        bufferGraphics.fillRect(0, 0, config.getWidth(), config.getHeight());

        SceneManager.render(bufferGraphics);
        UIManager.render(bufferGraphics);

        Graphics g = bs.getDrawGraphics();
        Graphics2D g2 = (Graphics2D) g;
        g2.setRenderingHint(RenderingHints.KEY_INTERPOLATION,
                RenderingHints.VALUE_INTERPOLATION_NEAREST_NEIGHBOR);
        g2.drawImage(backBuffer, 0, 0,
                config.getWidth() * config.getScale(),
                config.getHeight() * config.getScale(), null);
        g.dispose();
        bs.show();
    }

    /** @return largura lógica do jogo (em pixels), ou 0 se a engine não foi iniciada. */
    public static int getWidth() {
        return instance != null ? instance.config.getWidth() : 0;
    }

    /** @return altura lógica do jogo (em pixels), ou 0 se a engine não foi iniciada. */
    public static int getHeight() {
        return instance != null ? instance.config.getHeight() : 0;
    }

    /** @return a configuração ativa, ou {@code null} se a engine não foi iniciada. */
    public static GameConfig getConfig() {
        return instance != null ? instance.config : null;
    }

    /** Para o laço principal (a janela continua aberta). */
    public static void stop() {
        if (instance != null) {
            instance.running = false;
        }
    }

    /** Encerra o laço e finaliza o processo. */
    public static void exit() {
        stop();
        System.exit(0);
    }
}
