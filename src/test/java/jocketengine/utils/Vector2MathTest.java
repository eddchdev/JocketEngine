package jocketengine.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Vector2MathTest {

    private static final float EPS = 1e-4f;

    @Test
    void lengthAndLengthSquared() {
        Vector2 v = new Vector2(3, 4);
        assertEquals(5f, v.length(), EPS);
        assertEquals(25f, v.lengthSquared(), EPS);
    }

    @Test
    void dotProduct() {
        assertEquals(11f, new Vector2(1, 2).dot(new Vector2(3, 4)), EPS);
    }

    @Test
    void distanceBetweenPoints() {
        assertEquals(5f, new Vector2(0, 0).distance(new Vector2(3, 4)), EPS);
    }

    @Test
    void normalizedHasUnitLength() {
        Vector2 n = new Vector2(0, 5).normalized();
        assertEquals(0f, n.x, EPS);
        assertEquals(1f, n.y, EPS);
    }

    @Test
    void normalizingZeroVectorIsSafe() {
        assertEquals(0f, new Vector2().normalized().length(), EPS);
    }

    @Test
    void subtractionAndLerp() {
        Vector2 s = new Vector2(5, 5).sub(new Vector2(2, 1));
        assertEquals(3f, s.x, EPS);
        assertEquals(4f, s.y, EPS);

        Vector2 l = new Vector2(0, 0).lerp(new Vector2(10, 0), 0.5f);
        assertEquals(5f, l.x, EPS);
    }
}
