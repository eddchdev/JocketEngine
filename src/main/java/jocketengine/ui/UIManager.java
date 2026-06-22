package jocketengine.ui;

import jocketengine.ui.elements.UIElement;

import java.awt.Graphics;
import java.util.ArrayList;
import java.util.List;

/**
 * Gerenciador global da interface gráfica.
 * <p>
 * Armazena, atualiza e renderiza os elementos de UI. A atualização percorre uma
 * cópia da lista, então é seguro um elemento adicionar, remover ou trocar de cena
 * (limpando a UI) durante o seu próprio {@code update} — como faz um botão ao ser
 * clicado.
 * </p>
 *
 * @author Eddch
 */
public final class UIManager {

    private static final List<UIElement> elements = new ArrayList<>();

    private UIManager() {
    }

    /** Adiciona um elemento à interface. */
    public static void add(UIElement element) {
        elements.add(element);
    }

    /** Remove um elemento da interface. */
    public static void remove(UIElement element) {
        elements.remove(element);
    }

    /**
     * Atualiza todos os elementos (sobre uma cópia, para permitir mutação durante a iteração).
     *
     * @param dt delta time em segundos
     */
    public static void update(float dt) {
        for (UIElement element : new ArrayList<>(elements)) {
            element.update(dt);
        }
    }

    /**
     * Renderiza todos os elementos.
     *
     * @param g contexto gráfico
     */
    public static void render(Graphics g) {
        for (UIElement element : elements) {
            element.render(g);
        }
    }

    /** Remove todos os elementos. */
    public static void clear() {
        elements.clear();
    }

    /** @return cópia da lista atual de elementos. */
    public static List<UIElement> getElements() {
        return new ArrayList<>(elements);
    }
}
