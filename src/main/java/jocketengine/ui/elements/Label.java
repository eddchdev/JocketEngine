package jocketengine.ui.elements;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * Texto estático para a interface (títulos, descrições, indicadores).
 * <p>
 * Suporta alinhamento horizontal: com {@link Align#CENTER}, a coordenada X passa
 * a ser o centro do texto, o que facilita centralizar elementos na tela.
 * </p>
 *
 * @author Eddch
 */
public class Label implements UIElement {

    /** Alinhamento horizontal do texto em relação à coordenada X. */
    public enum Align {
        LEFT, CENTER
    }

    private int x;
    private int y;
    private String text;
    private Font font;
    private Color color;
    private Align align = Align.LEFT;

    /**
     * Cria um rótulo alinhado à esquerda.
     *
     * @param x     posição X (canto esquerdo do texto)
     * @param y     linha de base do texto
     * @param text  texto exibido
     * @param font  fonte
     * @param color cor
     */
    public Label(int x, int y, String text, Font font, Color color) {
        this.x = x;
        this.y = y;
        this.text = text;
        this.font = font;
        this.color = color;
    }

    /** Define o alinhamento horizontal e retorna o próprio rótulo (encadeável). */
    public Label setAlign(Align align) {
        this.align = align;
        return this;
    }

    @Override
    public void update(float dt) {
        // Rótulo é estático.
    }

    @Override
    public void render(Graphics g) {
        g.setFont(font);
        g.setColor(color);

        int drawX = x;
        if (align == Align.CENTER) {
            FontMetrics fm = g.getFontMetrics();
            drawX = x - fm.stringWidth(text) / 2;
        }
        g.drawString(text, drawX, y);
    }

    public void setText(String text) {
        this.text = text;
    }

    public void setColor(Color color) {
        this.color = color;
    }

    public void setFont(Font font) {
        this.font = font;
    }
}
