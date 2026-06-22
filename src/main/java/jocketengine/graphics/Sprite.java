package jocketengine.graphics;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.Map;

/**
 * Sprite em pixel art construído a partir de um "mapa" de caracteres.
 * <p>
 * Cada linha é uma fileira de pixels; cada caractere é mapeado para uma cor pela
 * paleta. Caracteres ausentes na paleta (ex.: {@code '.'}) viram pixels
 * transparentes. Isso permite desenhar personagens e itens diretamente no
 * código, sem arquivos externos:
 * </p>
 * <pre>{@code
 * Sprite s = Sprite.fromRows(new String[]{
 *     ".oo.",
 *     "owwo",
 *     ".oo."
 * }, Map.of('o', Color.BLACK, 'w', Color.WHITE));
 * }</pre>
 *
 * @author Eddch
 */
public final class Sprite {

    private final BufferedImage image;

    public Sprite(BufferedImage image) {
        this.image = image;
    }

    /**
     * Constrói um sprite a partir das linhas e da paleta.
     *
     * @param rows    linhas do desenho (todas com a mesma largura)
     * @param palette mapa de caractere para cor (omissos = transparente)
     * @return o sprite gerado
     */
    public static Sprite fromRows(String[] rows, Map<Character, Color> palette) {
        int height = rows.length;
        int width = rows[0].length();
        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

        for (int y = 0; y < height; y++) {
            String row = rows[y];
            if (row.length() != width) {
                throw new IllegalArgumentException(
                        "Linha " + y + " tem largura " + row.length() + ", esperado " + width);
            }
            for (int x = 0; x < width; x++) {
                Color color = palette.get(row.charAt(x));
                if (color != null) {
                    img.setRGB(x, y, color.getRGB());
                }
            }
        }
        return new Sprite(img);
    }

    public int getWidth() {
        return image.getWidth();
    }

    public int getHeight() {
        return image.getHeight();
    }

    public BufferedImage getImage() {
        return image;
    }

    /** Desenha o sprite com o canto superior esquerdo em (x, y). */
    public void draw(Graphics g, int x, int y) {
        draw(g, x, y, false);
    }

    /**
     * Desenha o sprite, opcionalmente espelhado na horizontal.
     *
     * @param flipX se {@code true}, espelha (útil para virar o personagem de lado)
     */
    public void draw(Graphics g, int x, int y, boolean flipX) {
        int w = getWidth();
        int h = getHeight();
        if (flipX) {
            g.drawImage(image, x + w, y, x, y + h, 0, 0, w, h, null);
        } else {
            g.drawImage(image, x, y, x + w, y + h, 0, 0, w, h, null);
        }
    }
}
