package jocketengine.math;

import org.junit.jupiter.api.RepeatedTest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class MathUtilsTest {

    private static final float EPS = 1e-4f;

    @Test
    void clampBoundsValues() {
        assertEquals(5f, MathUtils.clamp(10f, 0f, 5f), EPS);
        assertEquals(0f, MathUtils.clamp(-3f, 0f, 5f), EPS);
        assertEquals(3, MathUtils.clamp(3, 0, 5));
    }

    @Test
    void lerpInterpolates() {
        assertEquals(5f, MathUtils.lerp(0f, 10f, 0.5f), EPS);
        assertEquals(0f, MathUtils.lerp(0f, 10f, 0f), EPS);
        assertEquals(10f, MathUtils.lerp(0f, 10f, 1f), EPS);
    }

    @Test
    void mapRemapsRanges() {
        assertEquals(50f, MathUtils.map(5f, 0f, 10f, 0f, 100f), EPS);
        assertEquals(0f, MathUtils.map(5f, 5f, 5f, 0f, 100f), EPS); // intervalo degenerado
    }

    @Test
    void approachMovesByAtMostDelta() {
        assertEquals(5f, MathUtils.approach(0f, 10f, 5f), EPS);
        assertEquals(10f, MathUtils.approach(8f, 10f, 5f), EPS);
        assertEquals(5f, MathUtils.approach(10f, 0f, 5f), EPS);
    }

    @Test
    void distanceIsEuclidean() {
        assertEquals(5f, MathUtils.distance(0, 0, 3, 4), EPS);
    }

    @RepeatedTest(20)
    void randomIntStaysInRange() {
        int v = MathUtils.randomInt(1, 6);
        assertTrue(v >= 1 && v <= 6, "valor fora do intervalo: " + v);
    }
}
