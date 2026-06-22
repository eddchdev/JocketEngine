package jocketengine.graphics;

import org.junit.jupiter.api.Test;

import java.awt.image.BufferedImage;

import static org.junit.jupiter.api.Assertions.assertEquals;

class SpriteAnimationTest {

    private static BufferedImage[] threeFrames() {
        return new BufferedImage[]{
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB),
                new BufferedImage(1, 1, BufferedImage.TYPE_INT_ARGB)
        };
    }

    @Test
    void advancesToNextFrameAfterFrameTime() {
        SpriteAnimation animation = new SpriteAnimation(threeFrames(), 0.1f);
        assertEquals(0, animation.getCurrentFrameIndex());
        animation.update(0.1f);
        assertEquals(1, animation.getCurrentFrameIndex());
    }

    @Test
    void wrapsAroundToFirstFrame() {
        SpriteAnimation animation = new SpriteAnimation(threeFrames(), 0.1f);
        animation.update(0.1f);
        animation.update(0.1f);
        animation.update(0.1f);
        assertEquals(0, animation.getCurrentFrameIndex());
    }

    @Test
    void resetReturnsToFirstFrame() {
        SpriteAnimation animation = new SpriteAnimation(threeFrames(), 0.1f);
        animation.update(0.1f);
        animation.reset();
        assertEquals(0, animation.getCurrentFrameIndex());
    }

    @Test
    void frameCountMatchesInput() {
        assertEquals(3, new SpriteAnimation(threeFrames(), 0.1f).getFrameCount());
    }
}
