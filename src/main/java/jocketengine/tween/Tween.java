package jocketengine.tween;

import jocketengine.math.MathUtils;

import java.util.function.Consumer;

/**
 * Anima um valor {@code float} de {@code from} até {@code to} ao longo de uma
 * duração, aplicando uma {@link Easing} e entregando o valor a cada passo.
 * <pre>{@code
 * Tween fade = new Tween(0, 1, 0.5f, Easing.QUAD_OUT, alpha -> sprite.alpha = alpha)
 *         .onComplete(() -> Logger.info("UI", "fade pronto"));
 * }</pre>
 *
 * @author Eddch
 */
public class Tween {

    private final float from;
    private final float to;
    private final float duration;
    private final Easing easing;
    private final Consumer<Float> setter;

    private float time;
    private boolean done;
    private Runnable onComplete;

    /**
     * @param from     valor inicial
     * @param to       valor final
     * @param duration duração em segundos (&gt; 0)
     * @param easing   função de suavização
     * @param setter   recebe o valor interpolado a cada atualização
     */
    public Tween(float from, float to, float duration, Easing easing, Consumer<Float> setter) {
        this.from = from;
        this.to = to;
        this.duration = Math.max(0.0001f, duration);
        this.easing = easing;
        this.setter = setter;
    }

    /** Define uma ação para quando a animação terminar. Retorna {@code this}. */
    public Tween onComplete(Runnable action) {
        this.onComplete = action;
        return this;
    }

    /**
     * Avança a animação.
     *
     * @param dt tempo em segundos desde o último passo
     */
    public void update(float dt) {
        if (done) {
            return;
        }
        time += dt;
        float t = MathUtils.clamp(time / duration, 0f, 1f);
        setter.accept(from + (to - from) * easing.ease(t));
        if (t >= 1f) {
            done = true;
            if (onComplete != null) {
                onComplete.run();
            }
        }
    }

    public boolean isDone() {
        return done;
    }
}
