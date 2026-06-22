package jocketengine.math;

import java.util.Random;

/**
 * Funções matemáticas utilitárias comuns em jogos: interpolação, limites,
 * remapeamento e aleatoriedade.
 *
 * @author Eddch
 */
public final class MathUtils {

    /** Valor de PI como {@code float}. */
    public static final float PI = (float) Math.PI;

    /** PI x 2 (uma volta completa, em radianos). */
    public static final float TAU = (float) (Math.PI * 2);

    private static final Random RNG = new Random();

    private MathUtils() {
    }

    /** Restringe {@code value} ao intervalo [min, max]. */
    public static float clamp(float value, float min, float max) {
        return value < min ? min : Math.min(value, max);
    }

    /** Restringe {@code value} ao intervalo [min, max]. */
    public static int clamp(int value, int min, int max) {
        return value < min ? min : Math.min(value, max);
    }

    /** Interpolação linear: retorna {@code a} em t=0 e {@code b} em t=1. */
    public static float lerp(float a, float b, float t) {
        return a + (b - a) * t;
    }

    /** Remapeia {@code value} do intervalo [inMin, inMax] para [outMin, outMax]. */
    public static float map(float value, float inMin, float inMax, float outMin, float outMax) {
        if (inMax == inMin) {
            return outMin;
        }
        return outMin + (value - inMin) * (outMax - outMin) / (inMax - inMin);
    }

    /** Move {@code current} em direção a {@code target} no máximo {@code maxDelta}. */
    public static float approach(float current, float target, float maxDelta) {
        if (current < target) {
            return Math.min(current + maxDelta, target);
        }
        return Math.max(current - maxDelta, target);
    }

    /** Distância euclidiana entre dois pontos. */
    public static float distance(float x1, float y1, float x2, float y2) {
        float dx = x2 - x1;
        float dy = y2 - y1;
        return (float) Math.sqrt(dx * dx + dy * dy);
    }

    /** {@code float} aleatório no intervalo [min, max). */
    public static float random(float min, float max) {
        return min + RNG.nextFloat() * (max - min);
    }

    /** Inteiro aleatório no intervalo [minInclusive, maxInclusive]. */
    public static int randomInt(int minInclusive, int maxInclusive) {
        return minInclusive + RNG.nextInt(maxInclusive - minInclusive + 1);
    }

    /** Retorna {@code true} com probabilidade {@code probability} (0..1). */
    public static boolean chance(float probability) {
        return RNG.nextFloat() < probability;
    }
}
