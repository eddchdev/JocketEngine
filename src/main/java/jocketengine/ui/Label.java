package jocketengine.ui.elements;

import java.awt.*;

/**
 * Elemento visual que exibe texto simples na interface da JocketEngine.
 * <p>
 * Pode ser usado para títulos, descrições, informações ou indicadores.
 * </p>
 *
 * @author Eddch
 */
public class Label implements UIElement {

    private int x, y;
    private String text;
    private Font font;
    private Color color;

    /**
     * Cria um novo rótulo de texto.
     *
     * @param x     posição X (em pixels)
     * @param y     posição Y (em pixels)
     * @param text  texto a ser exibido
     * @param font  fonte usada para renderizar o texto
     * @param color cor do texto
     */
    public Label(int x, int y, String text, Font font, Color color) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.font = font;
        this.color = color;
    }

    @Override
    public void update(float dt) {
        // Label é estático — nenhuma atualização lógica por padrão
    }

    @Override
    public void render(Graphics g) {
        g.setFont(font);
        g.setColor(color);
        g.drawString(text, x, y);
    }

    // Getters e setters (opcionais para futuras animações ou efeitos)

    public void setText(String text) {
        this.text = text;
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setColor(Color color) {
        this.color = color;
    }
}