package jocketengine.assets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

/**
 * Classe responsável por carregar animações a partir de spritesheets ou atlas.
 * <p>
 * Suporta carregar uma sequência de quadros (frames) para animação frame-by-frame.
 * </p>
 * 
 * @author Eddch
 */
public class AnimationLoader {

    /**
     * Carrega uma animação a partir de um spritesheet (imagem única contendo múltiplos frames em linha).
     * 
     * @param path       caminho do spritesheet
     * @param frameWidth largura de cada frame
     * @param frameHeight altura de cada frame
     * @return lista de BufferedImage com os frames da animação
     * @throws IOException se a imagem não puder ser carregada
     */
    public static List<BufferedImage> loadAnimationFrames(String path, int frameWidth, int frameHeight) throws IOException {
        BufferedImage sheet = ImageIO.read(AnimationLoader.class.getResourceAsStream(path));
        int framesCount = sheet.getWidth() / frameWidth;
        List<BufferedImage> frames = new ArrayList<>();

        for (int i = 0; i < framesCount; i++) {
            BufferedImage frame = sheet.getSubimage(i * frameWidth, 0, frameWidth, frameHeight);
            frames.add(frame);
        }

        return frames;
    }
}