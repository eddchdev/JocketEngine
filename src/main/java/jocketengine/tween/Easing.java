package jocketengine.tween;

/**
 * Funções de suavização (easing) para animação. Todas mapeiam um progresso
 * {@code t} em [0, 1] para um valor de saída, normalmente também em [0, 1],
 * com {@code ease(0) == 0} e {@code ease(1) == 1}.
 *
 * @author Eddch
 */
@FunctionalInterface
public interface Easing {

    float ease(float t);

    /** Linear (sem suavização). */
    Easing LINEAR = t -> t;

    /** Acelera a partir do zero. */
    Easing QUAD_IN = t -> t * t;

    /** Desacelera até o fim. */
    Easing QUAD_OUT = t -> 1 - (1 - t) * (1 - t);

    /** Acelera e depois desacelera. */
    Easing QUAD_IN_OUT = t -> t < 0.5f ? 2 * t * t : 1 - (float) Math.pow(-2 * t + 2, 2) / 2;

    /** Suavização senoidal nas duas pontas. */
    Easing SINE_IN_OUT = t -> (float) (-(Math.cos(Math.PI * t) - 1) / 2);

    /** Passa do alvo e volta (efeito "elástico" sutil) no fim. */
    Easing BACK_OUT = t -> {
        float c1 = 1.70158f;
        float c3 = c1 + 1;
        return 1 + c3 * (float) Math.pow(t - 1, 3) + c1 * (float) Math.pow(t - 1, 2);
    };

    /** Quica ao chegar no fim. */
    Easing BOUNCE_OUT = t -> {
        float n1 = 7.5625f;
        float d1 = 2.75f;
        if (t < 1 / d1) {
            return n1 * t * t;
        } else if (t < 2 / d1) {
            t -= 1.5f / d1;
            return n1 * t * t + 0.75f;
        } else if (t < 2.5f / d1) {
            t -= 2.25f / d1;
            return n1 * t * t + 0.9375f;
        } else {
            t -= 2.625f / d1;
            return n1 * t * t + 0.984375f;
        }
    };
}
