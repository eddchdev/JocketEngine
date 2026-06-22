package jocketengine.graphics;

import org.junit.jupiter.api.Test;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class SpriteTest {

    @Test
    void dimensionsMatchRows() {
        Sprite sprite = Sprite.fromRows(new String[]{"oo", "oo", "oo"}, Map.of('o', Color.WHITE));
        assertEquals(2, sprite.getWidth());
        assertEquals(3, sprite.getHeight());
    }

    @Test
    void mappedPixelsGetColorUnmappedAreTransparent() {
        Sprite sprite = Sprite.fromRows(new String[]{"o.", ".."}, Map.of('o', new Color(255, 0, 0)));
        BufferedImage img = sprite.getImage();

        assertEquals(255, (img.getRGB(0, 0) >> 16) & 0xFF, "canal vermelho do pixel 'o'");
        assertEquals(0, img.getRGB(1, 0) >>> 24, "alpha do pixel transparente '.'");
    }

    @Test
    void rowsOfDifferentWidthsThrow() {
        assertThrows(IllegalArgumentException.class,
                () -> Sprite.fromRows(new String[]{"oo", "o"}, Map.of('o', Color.WHITE)));
    }
}
