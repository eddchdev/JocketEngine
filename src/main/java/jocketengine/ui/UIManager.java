package jocketengine.ui;

import jocketengine.ui.elements.UIElement;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Gerenciador global de interface gráfica da JocketEngine.
 * <p>
 * Responsável por armazenar, atualizar e renderizar todos os elementos
 * de UI em tempo real, como botões, rótulos, painéis e campos de texto.
 * </p>
 *
 * <p>Uso típico:</p>
 * <pre>{@code
 * UIManager.add(new Button(...));
 * UIManager.update(dt);
 * UIManager.render(g);
 * }</pre>
 *
 * @author Eddch
 */
public class UIManager {

    /** Lista de elementos ativos da interface. */
    private static final List<UIElement> elements = new ArrayList<>();

    /**
     * Adiciona um novo elemento visual à interface.
     *
     * @param element o elemento de UI a ser adicionado
     */
    public static void add(UIElement element) {
        elements.add(element);
    }

    /**
     * Atualiza todos os elementos da interface.
     *
     * @param dt delta time (tempo em segundos desde o último frame)
     */
    public static void update(float dt) {
        for (UIElement element : elements) {
            element.update(dt);
        }
    }

    /**
     * Renderiza todos os elementos da interface gráfica.
     *
     * @param g objeto {@link Graphics} usado para desenhar
     */
    public static void render(Graphics g) {
        for (UIElement element : elements) {
            element.render(g);
        }
    }

    /**
     * Remove todos os elementos da interface atual.
     * Útil ao trocar de cena ou limpar a tela.
     */
    public static void clear() {
        elements.clear();
    }

    /**
     * Remove um elemento específico da interface.
     *
     * @param element o elemento a ser removido
     */
    public static void remove(UIElement element) {
        elements.remove(element);
    }

    /**
     * Retorna uma cópia da lista atual de elementos de UI.
     *
     * @return lista de elementos visuais
     */
    public static List<UIElement> getElements() {
        return new ArrayList<>(elements);
    }
}