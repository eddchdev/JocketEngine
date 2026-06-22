package jocketengine.ui.elements;

import jocketengine.input.Input;
import jocketengine.ui.style.UIColorPalette;
import jocketengine.ui.style.UIFonts;

import java.awt.*;
import java.awt.event.KeyEvent;

/**
 * Campo de entrada de texto da interface.
 * <p>
 * Permite ao jogador digitar nomes, comandos ou interagir com menus.
 * </p>
 * 
 * @author Eddch
 */
public class TextField implements UIElement {

    private int x, y, width, height;
    private StringBuilder text = new StringBuilder();
    private boolean focused = false;

    private Color background = UIColorPalette.BACKGROUND;
    private Color border = UIColorPalette.ACCENT;
    private Color textColor = UIColorPalette.FOREGROUND;
    private Font font = UIFonts.DEFAULT;

    public TextField(int x, int y, int width, int height) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
    }

    @Override
    public void update(float dt) {
        if (focused) {
            String typed = Input.getTypedText(); // método fictício para pegar texto digitado
            if (typed != null) {
                text.append(typed);
            }

            if (Input.isKeyPressed(KeyEvent.VK_BACK_SPACE) && text.length() > 0) {
                text.deleteCharAt(text.length() - 1);
            }
        }

        if (Input.isMousePressed()) {
            focused = Input.getMouseX() >= x && Input.getMouseX() <= x + width &&
                      Input.getMouseY() >= y && Input.getMouseY() <= y + height;
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(background);
        g.fillRect(x, y, width, height);

        g.setColor(border);
        g.drawRect(x, y, width, height);

        g.setColor(textColor);
        g.setFont(font);
        g.drawString(text.toString(), x + 5, y + height / 2 + font.getSize() / 2 - 2);
    }

    public String getText() {
        return text.toString();
    }

    public void setText(String value) {
        text = new StringBuilder(value);
    }

    public boolean isFocused() {
        return focused;
    }

    public void setFocused(boolean focused) {
        this.focused = focused;
    }
}