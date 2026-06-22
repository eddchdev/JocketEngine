package jocketengine.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Vector2Test {

    private static final float EPS = 1e-6f;

    @Test
    void addReturnsSum() {
        Vector2 r = new Vector2(1, 2).add(new Vector2(3, 4));
        assertEquals(4, r.x, EPS);
        assertEquals(6, r.y, EPS);
    }

    @Test
    void addDoesNotMutateOriginal() {
        Vector2 a = new Vector2(1, 1);
        a.add(new Vector2(5, 5));
        assertEquals(1, a.x, EPS);
        assertEquals(1, a.y, EPS);
    }

    @Test
    void scaleMultipliesComponents() {
        Vector2 r = new Vector2(2, 3).scale(2);
        assertEquals(4, r.x, EPS);
        assertEquals(6, r.y, EPS);
    }

    @Test
    void copyIsIndependent() {
        Vector2 a = new Vector2();
        a.set(7, 8);
        Vector2 c = a.copy();
        c.x = 0;
        assertEquals(7, a.x, EPS);
        assertEquals(8, c.y, EPS);
    }
}
