package jocketengine.ui.elements;

import java.awt.Graphics;

/**
 * Interface base para todos os elementos da interface gráfica (UI) da JocketEngine.
 * <p>
 * Define os métodos obrigatórios que cada componente visual precisa implementar
 * para funcionar corretamente com o {@link jocketengine.ui.UIManager}.
 * </p>
 *
 * @author Eddch
 */
public interface UIElement {

    /**
     * Atualiza o estado do elemento.
     * Chamado a cada frame pelo {@link jocketengine.ui.UIManager}.
     *
     * @param dt delta time — tempo em segundos desde o último frame
     */
    void update(float dt);

    /**
     * Renderiza visualmente o elemento na tela.
     *
     * @param g objeto {@link Graphics} usado para desenhar o elemento
     */
    void render(Graphics g);
}