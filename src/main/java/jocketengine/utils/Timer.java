package jocketengine.utils;

/**
 * Temporizador usado para delays, animações, cooldowns, etc.
 * <p>
 * Funciona como contador regressivo de tempo.
 * </p>
 * 
 * @author Eddch
 */
public class Timer {

    private float time;
    private float remaining;
    private boolean active;

    public Timer(float timeInSeconds) {
        this.time = timeInSeconds;
        this.remaining = timeInSeconds;
        this.active = false;
    }

    public void start() {
        active = true;
        remaining = time;
    }

    public void update(float dt) {
        if (!active) return;
        remaining -= dt;
        if (remaining <= 0) {
            active = false;
            remaining = 0;
        }
    }

    public boolean isFinished() {
        return !active;
    }

    public void reset() {
        this.remaining = time;
        this.active = true;
    }

    public float getRemaining() {
        return remaining;
    }
}