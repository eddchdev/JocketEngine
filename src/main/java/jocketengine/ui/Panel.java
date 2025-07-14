package jocketengine.ui.elements;

import jocketengine.ui.style.UIColorPalette;

import java.awt.*;

/**
 * Painel de UI que funciona como container visual.
 * <p>
 * Pode ser usado como fundo para menus, HUDs ou janelas.
 * </p>
 * 
 * @author Eddch
 */
public class Panel implements UIElement {

    private int x, y, width, height;
    private Color background = UIColorPalette.BACKGROUND;
    private Color border = UIColorPalette.BORDER;

    public Panel(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void update(float dt) {
        // Painel é estático
    }

    @Override
    public void render(Graphics g) {
        g.setColor(background);
        g.fillRect(x, y, width, height);

        g.setColor(border);
        g.drawRect(x, y, width, height);
    }

    // Getters e setters
    public void setBackground(Color bg) {
        this.background = bg;
    }

    public void setBorder(Color border) {
        this.border = border;
    }
}