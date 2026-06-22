package jocketengine.tilemap;

import jocketengine.assets.TextureAtlas;
import jocketengine.math.MathUtils;
import jocketengine.utils.Rectangle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

/**
 * Mapa de tiles (grade de inteiros). O valor {@code 0} é vazio; valores maiores
 * são tiles, que podem ser marcados como sólidos e desenhados via um
 * {@link TextureAtlas} ou como blocos coloridos (útil sem arte).
 * <p>
 * A colisão se integra direto ao resto da engine: {@link #getSolidBounds(Rectangle)}
 * devolve os retângulos sólidos numa região, prontos para alimentar a resolução
 * de colisão de uma entidade.
 * </p>
 *
 * @author Eddch
 */
public class TileMap {

    /** Índice de célula vazia. */
    public static final int EMPTY = 0;

    private final int[][] tiles; // [linha][coluna]
    private final int rows;
    private final int cols;
    private final int tileWidth;
    private final int tileHeight;

    private final Set<Integer> solid = new HashSet<>();
    private final Map<Integer, Color> colors = new HashMap<>();
    private TextureAtlas tileset;

    /**
     * @param tiles      grade {@code tiles[linha][coluna]}
     * @param tileWidth  largura de cada tile, em pixels
     * @param tileHeight altura de cada tile, em pixels
     */
    public TileMap(int[][] tiles, int tileWidth, int tileHeight) {
        this.tiles = tiles;
        this.rows = tiles.length;
        this.cols = tiles[0].length;
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    /** Define quais índices de tile são sólidos (colidem). */
    public TileMap setSolid(int... indices) {
        for (int i : indices) {
            solid.add(i);
        }
        return this;
    }

    /** Define a cor de um índice (usada quando não há tileset). */
    public TileMap setColor(int index, Color color) {
        colors.put(index, color);
        return this;
    }

    /** Define o atlas de imagens; o tile {@code n} usa o quadro {@code n - 1}. */
    public TileMap setTileset(TextureAtlas tileset) {
        this.tileset = tileset;
        return this;
    }

    public int getWorldWidth() {
        return cols * tileWidth;
    }

    public int getWorldHeight() {
        return rows * tileHeight;
    }

    public int tileAt(int col, int row) {
        if (col < 0 || row < 0 || col >= cols || row >= rows) {
            return EMPTY;
        }
        return tiles[row][col];
    }

    public boolean isSolidAt(int col, int row) {
        return solid.contains(tileAt(col, row));
    }

    /** @return true se a posição de mundo cai sobre um tile sólido. */
    public boolean isSolidAtWorld(float worldX, float worldY) {
        return isSolidAt((int) Math.floor(worldX / tileWidth), (int) Math.floor(worldY / tileHeight));
    }

    /**
     * Retorna os retângulos sólidos que tocam a região informada.
     *
     * @param area região de interesse, em coordenadas de mundo
     * @return lista de retângulos sólidos (pode estar vazia)
     */
    public List<Rectangle> getSolidBounds(Rectangle area) {
        List<Rectangle> result = new ArrayList<>();
        int startCol = MathUtils.clamp((int) Math.floor(area.x / tileWidth), 0, cols - 1);
        int endCol = MathUtils.clamp((int) Math.floor((area.x + area.width) / tileWidth), 0, cols - 1);
        int startRow = MathUtils.clamp((int) Math.floor(area.y / tileHeight), 0, rows - 1);
        int endRow = MathUtils.clamp((int) Math.floor((area.y + area.height) / tileHeight), 0, rows - 1);

        for (int row = startRow; row <= endRow; row++) {
            for (int col = startCol; col <= endCol; col++) {
                if (isSolidAt(col, row)) {
                    result.add(new Rectangle(col * tileWidth, row * tileHeight, tileWidth, tileHeight));
                }
            }
        }
        return result;
    }

    /** @return todos os retângulos sólidos do mapa (útil para colisão estática). */
    public List<Rectangle> getAllSolidBounds() {
        return getSolidBounds(new Rectangle(0, 0, getWorldWidth(), getWorldHeight()));
    }

    /** Desenha todos os tiles não vazios. */
    public void render(Graphics g) {
        for (int row = 0; row < rows; row++) {
            for (int col = 0; col < cols; col++) {
                int tile = tiles[row][col];
                if (tile == EMPTY) {
                    continue;
                }
                int x = col * tileWidth;
                int y = row * tileHeight;

                if (tileset != null && tile - 1 < tileset.getFrameCount()) {
                    BufferedImage frame = tileset.getFrame(tile - 1);
                    g.drawImage(frame, x, y, tileWidth, tileHeight, null);
                } else {
                    g.setColor(colors.getOrDefault(tile, new Color(70, 90, 120)));
                    g.fillRect(x, y, tileWidth, tileHeight);
                    g.setColor(new Color(110, 140, 180));
                    g.fillRect(x, y, tileWidth, 2);
                }
            }
        }
    }
}
