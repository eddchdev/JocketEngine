package jocketengine.graphics;

import java.awt.image.BufferedImage;

/**
 * Representa uma sequência de quadros de animação (sprite sheet).
 * <p>
 * Controla tempo entre quadros e avanço automático.
 * </p>
 * 
 * @author Eddch
 */
public class SpriteAnimation {

    private final BufferedImage[] frames;
    private final float frameTime; // tempo entre quadros
    private float timer = 0;
    private int currentFrame = 0;

    /**
     * Cria uma nova animação com os quadros fornecidos.
     * 
     * @param frames     array de imagens (quadros da animação)
     * @param frameTime  tempo (em segundos) entre cada quadro
     */
    public SpriteAnimation(BufferedImage[] frames, float frameTime) {
        this.frames = frames;
        this.frameTime = frameTime;
    }

    /**
     * Atualiza a animação com base no tempo decorrido.
     * 
     * @param deltaTime tempo desde o último frame (em segundos)
     */
    public void update(float deltaTime) {
        timer += deltaTime;
        if (timer >= frameTime) {
            timer -= frameTime;
            currentFrame = (currentFrame + 1) % frames.length;
        }
    }

    /**
     * Reinicia a animação para o primeiro quadro.
     */
    public void reset() {
        currentFrame = 0;
        timer = 0;
    }

    /**
     * Retorna o quadro atual da animação.
     * 
     * @return imagem atual
     */
    public BufferedImage getCurrentFrame() {
        return frames[currentFrame];
    }

    /**
     * Retorna o número total de quadros.
     * 
     * @return número de frames
     */
    public int getFrameCount() {
        return frames.length;
    }

    /**
     * Retorna o índice do quadro atual.
     * 
     * @return índice do frame atual
     */
    public int getCurrentFrameIndex() {
        return currentFrame;
    }
}