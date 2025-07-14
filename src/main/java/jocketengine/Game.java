package jocketengine;

import jocketengine.input.Input;
import jocketengine.scene.SceneManager;
import jocketengine.scene.scenes.MainMenuScene;
import jocketengine.ui.UIManager;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferStrategy;

/**
 * Classe principal da JocketEngine, responsável por criar a janela,
 * iniciar o loop principal do jogo (update e render) e gerenciar sistemas básicos.
 * <p>
 * Extende {@link Canvas} para renderização acelerada, e implementa {@link Runnable} para executar em thread própria.
 * </p>
 * <p>
 * A janela é criada com {@link JFrame} com tamanho fixo e escala configurável,
 * e utiliza buffer strategy para melhor performance gráfica.
 * </p>
 *
 * <p>Exemplo de execução:</p>
 * <pre>{@code
 * public static void main(String[] args) {
 *     Game game = new Game();
 *     game.start();
 * }
 * }</pre>
 *
 * @author Eddch
 */
public class Game extends Canvas implements Runnable {

    /**
     * Largura base da tela em pixels.
     */
    private static final int WIDTH = 480;

    /**
     * Altura base da tela em pixels.
     */
    private static final int HEIGHT = 270;

    /**
     * Fator de escala aplicado à tela para ampliar a imagem.
     */
    private static final int SCALE = 3;

    /**
     * Título da janela do jogo.
     */
    private static final String TITLE = "JocketEngine Demo";

    /**
     * Janela principal do jogo.
     */
    private static JFrame frame;

    /**
     * Instância estática única do jogo.
     */
    private static Game instance;

    /**
     * Controla o estado do loop do jogo (rodando ou não).
     */
    private boolean running = false;

    /**
     * Thread principal que executa o loop do jogo.
     */
    private Thread gameThread;

    /**
     * Último instante de tempo registrado para cálculo do delta time.
     */
    private long lastTime;

    /**
     * Método principal para iniciar a aplicação.
     * Cria uma instância de {@code Game} e inicia o loop.
     *
     * @param args argumentos de linha de comando (não usados)
     */
    public static void main(String[] args) {
        instance = new Game();
        instance.start();
    }

    /**
     * Construtor que inicializa a janela, configura a entrada de dados e prepara o canvas.
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

        // Configura os listeners de input para teclado e mouse
        Input.setup(this);
        this.addKeyListener(Input.getKeyListener());
        this.addMouseListener(Input.getMouseListener());
        this.addMouseMotionListener(Input.getMouseMotionListener());
        this.setFocusable(true);
    }

    /**
     * Inicia o loop principal do jogo em uma thread separada.
     */
    public synchronized void start() {
        if (running) return;
        running = true;
        gameThread = new Thread(this, "GameThread");
        gameThread.start();
    }

    /**
     * Para o loop do jogo e aguarda término da thread.
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
     * Método principal executado na thread do jogo.
     * Responsável por atualizar e renderizar o jogo em loop constante,
     * mantendo taxa aproximada de 60 frames por segundo.
     */
    @Override
    public void run() {
        this.createBufferStrategy(3);
        BufferStrategy bs = this.getBufferStrategy();
        Graphics g;

        // Carrega a primeira cena do jogo
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
                Thread.sleep(16); // Aproximadamente 60 FPS
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Atualiza os sistemas do jogo, como entrada, cena e interface.
     *
     * @param dt delta time (tempo em segundos desde o último frame)
     */
    private void update(float dt) {
        Input.update();
        SceneManager.update(dt);
        UIManager.update(dt);
    }

    /**
     * Renderiza a cena atual e os elementos da interface gráfica.
     *
     * @param g objeto {@link Graphics} utilizado para desenho na tela
     */
    private void render(Graphics g) {
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        SceneManager.render(g);
        UIManager.render(g);
    }

    /**
     * Retorna a instância única do jogo.
     *
     * @return instância de {@code Game}
     */
    public static Game getInstance() {
        return instance;
    }

    /**
     * Retorna a largura base do jogo (sem escala).
     *
     * @return largura em pixels
     */
    public static int getGameWidth() {
        return WIDTH;
    }

    /**
     * Retorna a altura base do jogo (sem escala).
     *
     * @return altura em pixels
     */
    public static int getGameHeight() {
        return HEIGHT;
    }

    /**
     * Encerra a aplicação de forma segura.
     */
    public static void exit() {
        instance.stop();
        System.exit(0);
    }
}