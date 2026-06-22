package jocketengine.tilemap;

import jocketengine.utils.Rectangle;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TileMapTest {

    private TileMap sample() {
        int[][] tiles = {
                {0, 0, 0},
                {1, 2, 0},
                {1, 1, 1}
        };
        return new TileMap(tiles, 10, 10).setSolid(1, 2);
    }

    @Test
    void worldDimensions() {
        TileMap map = sample();
        assertEquals(30, map.getWorldWidth());
        assertEquals(30, map.getWorldHeight());
    }

    @Test
    void tileLookupRespectsRowColumnAndBounds() {
        TileMap map = sample();
        assertEquals(2, map.tileAt(1, 1));
        assertEquals(0, map.tileAt(2, 0));
        assertEquals(0, map.tileAt(-1, 0)); // fora dos limites
    }

    @Test
    void solidQuery() {
        TileMap map = sample();
        assertTrue(map.isSolidAt(0, 1));
        assertFalse(map.isSolidAt(2, 0));
        assertTrue(map.isSolidAtWorld(5, 25)); // col 0, linha 2 -> sólido
    }

    @Test
    void solidBoundsCollectsEverySolidTile() {
        assertEquals(5, sample().getAllSolidBounds().size());
    }

    @Test
    void solidBoundsCanBeRestrictedToArea() {
        // Apenas a última linha (3 tiles sólidos).
        assertEquals(3, sample().getSolidBounds(new Rectangle(0, 20, 30, 10)).size());
    }
}
