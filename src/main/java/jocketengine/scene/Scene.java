package jocketengine.scene;

import java.awt.Graphics;

/**
 * Classe abstrata base que representa uma cena dentro do jogo.
 * <p>
 * Uma cena pode ser qualquer estado visual ou lógico do jogo:
 * menu principal, gameplay, tela de pausa, etc.
 * </p>
 *
 * <p>
 * Cada cena deve implementar os métodos de ciclo de vida:
 * {@link #onLoad()}, {@link #update(float)}, {@link #render(Graphics)}, e {@link #onExit()}.
 * </p>
 *
 * <p>Exemplo de extensão:</p>
 * <pre>{@code
 * public class GameScene extends Scene {
 *     public void onLoad() { ... }
 *     public void update(float dt) { ... }
 *     public void render(Graphics g) { ... }
 *     public void onExit() { ... }
 * }
 * }</pre>
 *
 * @author Eddch
 */
public abstract class Scene {

    /**
     * Método chamado uma única vez quando a cena é carregada pelo {@link SceneManager}.
     * Ideal para inicializar entidades, carregar recursos e configurar o estado inicial.
     */
    public abstract void onLoad();

    /**
     * Método chamado a cada frame para atualizar a lógica da cena.
     *
     * @param dt delta time — tempo em segundos desde o último frame
     */
    public abstract void update(float dt);

    /**
     * Método chamado a cada frame para desenhar os elementos visuais da cena.
     *
     * @param g objeto {@link Graphics} usado para renderização 2D
     */
    public abstract void render(Graphics g);

    /**
     * Método chamado uma vez ao sair da cena atual.
     * Ideal para limpar recursos, pausar música ou salvar progresso.
     */
    public abstract void onExit();
}