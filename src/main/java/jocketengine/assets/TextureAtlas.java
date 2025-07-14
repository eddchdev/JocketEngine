package jocketengine.assets;

import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Divide spritesheets em quadros individuais para animações.
 * <p>
 * Facilita acessar frames específicos a partir de uma imagem maior.
 * </p>
 * 
 * @author Eddch
 */
public class TextureAtlas {

    private final BufferedImage sheet;
    private final int frameWidth;
    private final int frameHeight;
    private final List<BufferedImage> frames = new ArrayList<>();

    /**
     * Construtor que quebra a imagem em frames.
     * 
     * @param sheet       spritesheet completo
     * @param frameWidth  largura do frame
     * @param frameHeight altura do frame
     */
    public TextureAtlas(BufferedImage sheet, int frameWidth, int frameHeight) {
        this.sheet = sheet;
        this.frameWidth = frameWidth;
        this.frameHeight = frameHeight;
        sliceFrames();
    }

    private void sliceFrames() {
        int cols = sheet.getWidth() / frameWidth;
        int rows = sheet.getHeight() / frameHeight;

        for (int y = 0; y < rows; y++) {
            for (int x = 0; x < cols; x++) {
                BufferedImage frame = sheet.getSubimage(x * frameWidth, y * frameHeight, frameWidth, frameHeight);
                frames.add(frame);
            }
        }
    }

    /**
     * Retorna o frame na posição especificada.
     * 
     * @param index índice do frame (0-based)
     * @return frame BufferedImage
     */
    public BufferedImage getFrame(int index) {
        if (index < 0 || index >= frames.size()) {
            throw new IndexOutOfBoundsException("Frame index out of bounds: " + index);
        }
        return frames.get(index);
    }

    /**
     * Retorna a quantidade total de frames no atlas.
     * 
     * @return número de frames
     */
    public int getFrameCount() {
        return frames.size();
    }
}