package jocketengine.utils;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TimerTest {

    @Test
    void runningTimerIsNotFinishedUntilTimeElapses() {
        Timer timer = new Timer(1f);
        timer.start();
        assertFalse(timer.isFinished());
        timer.update(0.5f);
        assertFalse(timer.isFinished());
    }

    @Test
    void timerFinishesAfterDurationElapses() {
        Timer timer = new Timer(1f);
        timer.start();
        timer.update(0.6f);
        timer.update(0.6f);
        assertTrue(timer.isFinished());
    }

    @Test
    void resetMakesTimerActiveAgain() {
        Timer timer = new Timer(1f);
        timer.start();
        timer.update(2f);
        assertTrue(timer.isFinished());
        timer.reset();
        assertFalse(timer.isFinished());
    }
}
