package jocketengine.ui.elements;

import jocketengine.input.Input;
import jocketengine.ui.style.UIColorPalette;

import java.awt.*;

/**
 * Componente de UI deslizante (slider horizontal).
 * <p>
 * Útil para configurações como volume, brilho, etc.
 * </p>
 * 
 * @author Eddch
 */
public class Slider implements UIElement {

    private int x, y, width;
    private float value; // entre 0 e 1
    private boolean dragging = false;

    public Slider(int x, int y, int width, float initialValue) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.value = Math.max(0, Math.min(1, initialValue));
    }

    @Override
    public void update(float dt) {
        int mouseX = Input.getMouseX();
        int mouseY = Input.getMouseY();

        if (Input.isMousePressed()) {
            if (mouseX >= x && mouseX <= x + width && mouseY >= y - 5 && mouseY <= y + 15) {
                dragging = true;
            }
        }

        if (dragging) {
            value = Math.max(0, Math.min(1, (float) (mouseX - x) / width));
            if (!Input.isMouseDown()) {
                dragging = false;
            }
        }
    }

    @Override
    public void render(Graphics g) {
        int knobX = x + (int) (value * width);

        g.setColor(UIColorPalette.HOVER);
        g.fillRect(x, y, width, 4);

        g.setColor(UIColorPalette.ACCENT);
        g.fillRect(knobX - 4, y - 4, 8, 12);
    }

    public float getValue() {
        return value;
    }

    public void setValue(float value) {
        this.value = Math.max(0, Math.min(1, value));
    }
}