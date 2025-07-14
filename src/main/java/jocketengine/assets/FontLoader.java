package jocketengine.assets;

import java.awt.Font;
import java.awt.FontFormatException;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

/**
 * Gerenciador para carregar e armazenar fontes para a JocketEngine.
 * <p>
 * Suporta fontes .ttf e outras, para uso em UI e HUD.
 * </p>
 * 
 * @author Eddch
 */
public class FontLoader {

    private static final Map<String, Font> fonts = new HashMap<>();

    /**
     * Carrega uma fonte do recurso especificado.
     * 
     * @param path caminho do arquivo de fonte (ex: "/fonts/pixel.ttf")
     * @param size tamanho da fonte
     * @return instância da fonte carregada
     * @throws IOException          se a fonte não puder ser lida
     * @throws FontFormatException  se o formato da fonte for inválido
     */
    public static Font loadFont(String path, float size) throws IOException, FontFormatException {
        String key = path + "@" + size;
        if (fonts.containsKey(key)) {
            return fonts.get(key);
        }

        try (InputStream is = FontLoader.class.getResourceAsStream(path)) {
            if (is == null) {
                throw new IOException("Fonte não encontrada: " + path);
            }
            Font font = Font.createFont(Font.TRUETYPE_FONT, is).deriveFont(size);
            fonts.put(key, font);
            return font;
        }
    }

    /**
     * Remove todas as fontes carregadas.
     */
    public static void clear() {
        fonts.clear();
    }
}