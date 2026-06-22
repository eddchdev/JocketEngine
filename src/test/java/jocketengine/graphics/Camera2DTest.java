package jocketengine.graphics;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class Camera2DTest {

    private static final float EPS = 1e-3f;

    @Test
    void offsetCentersOnPosition() {
        Camera2D cam = new Camera2D(100, 100);
        cam.setPosition(50, 50);
        assertEquals(0f, cam.getOffsetX(), EPS);
        assertEquals(0f, cam.getOffsetY(), EPS);
    }

    @Test
    void boundsClampTheView() {
        Camera2D cam = new Camera2D(100, 100);
        cam.setBounds(0, 0, 300, 300);
        cam.setPosition(1000, 1000); // muito além
        assertEquals(250f, cam.getX(), EPS); // 300 - meia-largura(50)
        assertEquals(250f, cam.getY(), EPS);
    }

    @Test
    void worldSmallerThanViewGetsCentered() {
        Camera2D cam = new Camera2D(200, 200);
        cam.setBounds(0, 0, 100, 100);
        cam.setPosition(10, 90);
        assertEquals(50f, cam.getX(), EPS);
        assertEquals(50f, cam.getY(), EPS);
    }

    @Test
    void followMovesFractionTowardTarget() {
        Camera2D cam = new Camera2D(100, 100);
        cam.setPosition(0, 0);
        cam.follow(100, 0, 0.5f);
        assertEquals(50f, cam.getX(), EPS);
    }
}
