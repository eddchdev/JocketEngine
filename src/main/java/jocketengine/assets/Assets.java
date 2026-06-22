package jocketengine.assets;

import jocketengine.utils.Logger;

import javax.sound.sampled.Clip;
import java.awt.Font;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * Ponto de acesso unificado aos recursos do jogo, com cache (delegando aos
 * carregadores especializados). Simplifica o uso comum a uma só chamada:
 * <pre>{@code
 * BufferedImage hero = Assets.texture("/sprites/hero.png");
 * Clip jump = Assets.sound("/sfx/jump.wav");
 * Font ui = Assets.font("/fonts/pixel.ttf", 16f);
 * }</pre>
 *
 * @author Eddch
 */
public final class Assets {

    private Assets() {
    }

    /**
     * Carrega (com cache) uma textura do classpath.
     *
     * @param path caminho do recurso (ex.: {@code "/sprites/hero.png"})
     * @return a imagem carregada
     * @throws IllegalStateException se o recurso não puder ser carregado
     */
    public static BufferedImage texture(String path) {
        try {
            return AssetLoader.loadImage(path);
        } catch (IOException | RuntimeException e) {
            throw new IllegalStateException("Falha ao carregar textura: " + path, e);
        }
    }

    /**
     * Carrega (com cache) um som do classpath. Retorna {@code null} (com aviso no
     * log) se não for possível — útil para o jogo seguir sem áudio.
     *
     * @param path caminho do recurso (ex.: {@code "/sfx/jump.wav"})
     * @return o clipe, ou {@code null}
     */
    public static Clip sound(String path) {
        try {
            return SoundLoader.loadSound(path);
        } catch (Exception e) {
            Logger.warn("Assets", "Não foi possível carregar o som " + path + ": " + e.getMessage());
            return null;
        }
    }

    /**
     * Carrega (com cache) uma fonte TrueType do classpath.
     *
     * @param path caminho do recurso (ex.: {@code "/fonts/pixel.ttf"})
     * @param size tamanho em pontos
     * @return a fonte carregada
     * @throws IllegalStateException se o recurso não puder ser carregado
     */
    public static Font font(String path, float size) {
        try {
            return FontLoader.loadFont(path, size);
        } catch (Exception e) {
            throw new IllegalStateException("Falha ao carregar fonte: " + path, e);
        }
    }
}
