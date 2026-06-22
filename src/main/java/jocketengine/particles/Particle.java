package jocketengine.particles;

import java.awt.Color;

/**
 * Uma única partícula: posição, velocidade, tempo de vida e aparência.
 * Gerenciada pelo {@link ParticleSystem}.
 *
 * @author Eddch
 */
public class Particle {

    public float x;
    public float y;
    public float velocityX;
    public float velocityY;
    public float life;
    public float maxLife;
    public float size;
    public Color color;

    /** @return progresso restante de vida, de 1 (recém-criada) a 0 (morta). */
    public float lifeRatio() {
        return maxLife <= 0 ? 0 : Math.max(0, life / maxLife);
    }

    public boolean isAlive() {
        return life > 0;
    }
}
