package jocketengine.tween;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TweenTest {

    private static final float EPS = 1e-3f;

    @Test
    void allEasingsKeepEndpoints() {
        Easing[] easings = {
                Easing.LINEAR, Easing.QUAD_IN, Easing.QUAD_OUT, Easing.QUAD_IN_OUT,
                Easing.SINE_IN_OUT, Easing.BACK_OUT, Easing.BOUNCE_OUT
        };
        for (Easing e : easings) {
            assertEquals(0f, e.ease(0f), EPS, "ease(0)");
            assertEquals(1f, e.ease(1f), EPS, "ease(1)");
        }
    }

    @Test
    void tweenReachesTargetValue() {
        float[] value = {0};
        Tween tween = new Tween(0, 10, 1, Easing.LINEAR, v -> value[0] = v);

        tween.update(0.5f);
        assertEquals(5f, value[0], EPS);
        assertFalse(tween.isDone());

        tween.update(0.5f);
        assertEquals(10f, value[0], EPS);
        assertTrue(tween.isDone());
    }

    @Test
    void onCompleteFires() {
        boolean[] done = {false};
        Tween tween = new Tween(0, 1, 0.5f, Easing.LINEAR, v -> {
        }).onComplete(() -> done[0] = true);

        tween.update(1f);
        assertTrue(done[0]);
    }

    @Test
    void managerDropsFinishedTweens() {
        TweenManager manager = new TweenManager();
        manager.add(new Tween(0, 1, 0.1f, Easing.LINEAR, v -> {
        }));
        assertEquals(1, manager.size());

        manager.update(0.2f);
        assertEquals(0, manager.size());
    }
}
