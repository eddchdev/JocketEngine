package jocketengine.assets;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;

/**
 * Gerencia o carregamento e armazenamento dos assets da engine.
 * <p>
 * Permite carregar e recuperar imagens, sons e outras mídias.
 * </p>
 * 
 * @author Eddch
 */
public class AssetLoader {

    private static final Map<String, BufferedImage> images = new HashMap<>();

    /**
     * Carrega uma imagem a partir do caminho informado e armazena em cache.
     * 
     * @param path caminho do arquivo de imagem (ex: "/sprites/player.png")
     * @return BufferedImage carregada
     * @throws IOException caso o arquivo não seja encontrado ou não possa ser lido
     */
    public static BufferedImage loadImage(String path) throws IOException {
        if (images.containsKey(path)) {
            return images.get(path);
        }
        BufferedImage img = ImageIO.read(AssetLoader.class.getResourceAsStream(path));
        images.put(path, img);
        return img;
    }

    /**
     * Retorna a imagem já carregada pelo caminho.
     * 
     * @param path caminho do arquivo de imagem
     * @return BufferedImage armazenada ou null se não carregada
     */
    public static BufferedImage getImage(String path) {
        return images.get(path);
    }

    /**
     * Limpa o cache de imagens carregadas.
     */
    public static void clear() {
        images.clear();
    }
}