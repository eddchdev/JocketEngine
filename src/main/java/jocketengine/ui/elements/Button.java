package jocketengine.ui.elements;

import jocketengine.input.Input;
import jocketengine.ui.style.UIFonts;

import java.awt.Color;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;

/**
 * Botão clicável da interface: exibe texto, detecta o mouse (em coordenadas
 * lógicas) e executa uma ação ao ser clicado. Desenhado com cantos arredondados
 * e realce ao passar o mouse.
 *
 * @author Eddch
 */
public class Button implements UIElement {

    private final int x;
    private final int y;
    private final int width;
    private final int height;
    private final String text;

    private Runnable onClick;
    private boolean hovered = false;

    private Color backgroundColor = new Color(44, 48, 66);
    private Color hoverColor = new Color(64, 72, 100);
    private Color textColor = Color.WHITE;
    private Font font = UIFonts.BUTTON;

    /**
     * @param x      posição X
     * @param y      posição Y
     * @param width  largura
     * @param height altura
     * @param text   texto exibido
     */
    public Button(int x, int y, int width, int height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    /** Define a ação executada ao clicar. */
    public void setOnClick(Runnable onClick) {
        this.onClick = onClick;
    }

    @Override
    public void update(float dt) {
        int mouseX = Input.getMouseX();
        int mouseY = Input.getMouseY();

        hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;

        if (hovered && Input.isMousePressed() && onClick != null) {
            onClick.run();
        }
    }

    @Override
    public void render(Graphics g) {
        int arc = 8;

        g.setColor(hovered ? hoverColor : backgroundColor);
        g.fillRoundRect(x, y, width, height, arc, arc);

        g.setColor(hovered ? new Color(120, 200, 255) : new Color(90, 96, 120));
        g.drawRoundRect(x, y, width - 1, height - 1, arc, arc);

        g.setColor(textColor);
        g.setFont(font);
        FontMetrics fm = g.getFontMetrics();
        int textX = x + (width - fm.stringWidth(text)) / 2;
        int textY = y + (height - fm.getHeight()) / 2 + fm.getAscent();
        g.drawString(text, textX, textY);
    }

    public void setFont(Font font) {
        this.font = font;
    }

    public void setBackgroundColor(Color color) {
        this.backgroundColor = color;
    }

    public void setHoverColor(Color color) {
        this.hoverColor = color;
    }

    public void setTextColor(Color color) {
        this.textColor = color;
    }
}
