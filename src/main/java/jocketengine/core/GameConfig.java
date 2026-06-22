package jocketengine.core;

/**
 * Configuração de inicialização da {@link Engine}.
 * <p>
 * Usa uma API fluente para facilitar a leitura:
 * </p>
 * <pre>{@code
 * GameConfig config = new GameConfig()
 *         .title("Meu Jogo")
 *         .logicalSize(480, 270)
 *         .scale(3)
 *         .targetFps(60);
 * }</pre>
 *
 * <p>
 * A resolução <b>lógica</b> é o espaço em que todo o jogo é desenhado.
 * A janela final tem tamanho {@code largura * scale} por {@code altura * scale},
 * e o conteúdo é ampliado com vizinho-mais-próximo, preservando o visual pixel art.
 * </p>
 *
 * @author Eddch
 */
public final class GameConfig {

    private String title = "JocketEngine";
    private int width = 480;
    private int height = 270;
    private int scale = 3;
    private int targetFps = 60;

    /** Define o título exibido na barra da janela. */
    public GameConfig title(String title) {
        this.title = title;
        return this;
    }

    /**
     * Define a resolução lógica (interna) do jogo, em pixels.
     *
     * @param width  largura lógica (&gt; 0)
     * @param height altura lógica (&gt; 0)
     */
    public GameConfig logicalSize(int width, int height) {
        if (width <= 0 || height <= 0) {
            throw new IllegalArgumentException("Resolução lógica deve ser positiva");
        }
        this.width = width;
        this.height = height;
        return this;
    }

    /**
     * Define o fator de ampliação da janela em relação à resolução lógica.
     *
     * @param scale fator de escala (&ge; 1)
     */
    public GameConfig scale(int scale) {
        if (scale < 1) {
            throw new IllegalArgumentException("Escala deve ser >= 1");
        }
        this.scale = scale;
        return this;
    }

    /**
     * Define a taxa de atualização lógica alvo (updates por segundo).
     *
     * @param targetFps quadros/atualizações por segundo (&ge; 1)
     */
    public GameConfig targetFps(int targetFps) {
        if (targetFps < 1) {
            throw new IllegalArgumentException("targetFps deve ser >= 1");
        }
        this.targetFps = targetFps;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public int getWidth() {
        return width;
    }

    public int getHeight() {
        return height;
    }

    public int getScale() {
        return scale;
    }

    public int getTargetFps() {
        return targetFps;
    }
}
