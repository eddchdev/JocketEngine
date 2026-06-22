package jocketengine.particles;

import jocketengine.math.MathUtils;

import java.awt.Color;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Emite e atualiza partículas. Leve e sem alocação por quadro no caminho comum.
 * <pre>{@code
 * particles.burst(x, y, 14, new Color(255, 210, 70)); // explosão de moeda
 * // no update/render da cena:
 * particles.update(dt);
 * particles.render(g);
 * }</pre>
 *
 * @author Eddch
 */
public class ParticleSystem {

    private final List<Particle> particles = new ArrayList<>();

    /** Gravidade aplicada às partículas (px/s²). Padrão: leve, para cima e some. */
    private float gravity = 200f;

    /** Atrito por segundo (0..1 do quanto a velocidade é mantida). */
    private float damping = 0.9f;

    public ParticleSystem setGravity(float gravity) {
        this.gravity = gravity;
        return this;
    }

    public ParticleSystem setDamping(float damping) {
        this.damping = damping;
        return this;
    }

    /**
     * Cria uma explosão de partículas saindo de um ponto em direções aleatórias.
     *
     * @param x     origem X (mundo)
     * @param y     origem Y (mundo)
     * @param count quantidade de partículas
     * @param color cor base
     */
    public void burst(float x, float y, int count, Color color) {
        for (int i = 0; i < count; i++) {
            Particle p = new Particle();
            p.x = x;
            p.y = y;
            float angle = MathUtils.random(0, MathUtils.TAU);
            float speed = MathUtils.random(40, 140);
            p.velocityX = (float) Math.cos(angle) * speed;
            p.velocityY = (float) Math.sin(angle) * speed;
            p.maxLife = MathUtils.random(0.3f, 0.7f);
            p.life = p.maxLife;
            p.size = MathUtils.random(1.5f, 3.5f);
            p.color = color;
            particles.add(p);
        }
    }

    /**
     * Atualiza posições e tempos de vida; remove partículas mortas.
     *
     * @param dt delta time em segundos
     */
    public void update(float dt) {
        float keep = (float) Math.pow(damping, dt);
        Iterator<Particle> it = particles.iterator();
        while (it.hasNext()) {
            Particle p = it.next();
            p.velocityY += gravity * dt;
            p.velocityX *= keep;
            p.velocityY *= keep;
            p.x += p.velocityX * dt;
            p.y += p.velocityY * dt;
            p.life -= dt;
            if (!p.isAlive()) {
                it.remove();
            }
        }
    }

    /** Desenha as partículas, desbotando conforme morrem. */
    public void render(Graphics g) {
        for (Particle p : particles) {
            float ratio = p.lifeRatio();
            int alpha = (int) MathUtils.clamp(ratio * 255, 0, 255);
            Color base = p.color;
            g.setColor(new Color(base.getRed(), base.getGreen(), base.getBlue(), alpha));
            int s = Math.max(1, Math.round(p.size));
            g.fillRect(Math.round(p.x), Math.round(p.y), s, s);
        }
    }

    public int count() {
        return particles.size();
    }

    public void clear() {
        particles.clear();
    }
}
