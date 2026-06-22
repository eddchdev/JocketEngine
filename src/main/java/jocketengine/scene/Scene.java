package jocketengine.scene;

import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 * Base para uma cena do jogo (menu, gameplay, pausa...).
 * <p>
 * Cada cena tem um ciclo de vida: {@link #onLoad()}, {@link #update(float)},
 * {@link #render(Graphics)} e {@link #onExit()}. Opcionalmente, pode desenhar
 * HUD/texto em {@link #renderUI(Graphics2D)}.
 * </p>
 *
 * <p>
 * O {@link #render(Graphics)} desenha o <b>mundo</b> no espaço lógico (pixel art,
 * ampliado por vizinho-mais-próximo). Já o {@link #renderUI(Graphics2D)} desenha
 * em <b>resolução nativa com antialiasing</b> — ideal para texto nítido — usando
 * ainda as mesmas coordenadas lógicas.
 * </p>
 *
 * @author Eddch
 */
public abstract class Scene {

    /** Chamado uma vez ao carregar a cena. */
    public abstract void onLoad();

    /**
     * Atualiza a lógica da cena.
     *
     * @param dt tempo em segundos desde o último passo
     */
    public abstract void update(float dt);

    /**
     * Desenha o mundo da cena (pixel art, espaço lógico).
     *
     * @param g contexto gráfico do back buffer lógico
     */
    public abstract void render(Graphics g);

    /**
     * Desenha HUD/texto em resolução nativa com antialiasing (tipografia nítida).
     * As coordenadas continuam sendo as lógicas. Padrão: não desenha nada.
     *
     * @param g contexto já escalado e com antialiasing ativo
     */
    public void renderUI(Graphics2D g) {
        // Opcional.
    }

    /** Chamado uma vez ao sair da cena. */
    public abstract void onExit();
}
