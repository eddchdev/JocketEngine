package jocketengine.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class RectangleTest {

    @Test
    void overlappingRectanglesIntersect() {
        assertTrue(new Rectangle(0, 0, 10, 10).intersects(new Rectangle(5, 5, 10, 10)));
    }

    @Test
    void separatedRectanglesDoNotIntersect() {
        assertFalse(new Rectangle(0, 0, 10, 10).intersects(new Rectangle(20, 20, 5, 5)));
    }

    @Test
    void edgeTouchingIsNotConsideredOverlap() {
        assertFalse(new Rectangle(0, 0, 10, 10).intersects(new Rectangle(10, 0, 10, 10)));
    }

    @Test
    void fullyContainedRectangleIntersects() {
        assertTrue(new Rectangle(0, 0, 100, 100).intersects(new Rectangle(40, 40, 5, 5)));
    }
}
