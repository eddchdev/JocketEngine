package jocketengine.particles;

import org.junit.jupiter.api.Test;

import java.awt.Color;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ParticleSystemTest {

    @Test
    void burstAddsParticles() {
        ParticleSystem system = new ParticleSystem();
        system.burst(0, 0, 12, Color.RED);
        assertEquals(12, system.count());
    }

    @Test
    void particlesDieAfterTheirLifetime() {
        ParticleSystem system = new ParticleSystem();
        system.burst(0, 0, 10, Color.RED);

        // A vida máxima de uma partícula é < 1s; após 2s todas devem ter morrido.
        for (int i = 0; i < 20; i++) {
            system.update(0.1f);
        }
        assertEquals(0, system.count());
    }

    @Test
    void clearRemovesAll() {
        ParticleSystem system = new ParticleSystem();
        system.burst(0, 0, 5, Color.RED);
        system.clear();
        assertEquals(0, system.count());
    }

    @Test
    void lifeRatioStaysNormalized() {
        Particle p = new Particle();
        p.maxLife = 1f;
        p.life = 0.5f;
        assertTrue(p.lifeRatio() >= 0 && p.lifeRatio() <= 1);
        assertEquals(0.5f, p.lifeRatio(), 1e-4);
    }
}
