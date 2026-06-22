package jocketengine.ui.elements;

import jocketengine.input.Input;

import java.awt.*;

/**
 * Representa um botão clicável na interface gráfica da JocketEngine.
 * <p>
 * Pode exibir texto, detectar interações com o mouse e executar ações
 * definidas pelo usuário ao ser clicado.
 * </p>
 *
 * @author Eddch
 */
public class Button implements UIElement {

    private int x, y, width, height;
    private String text;
    private Runnable onClick;

    private boolean hovered = false;

    private Color backgroundColor = new Color(60, 60, 80);
    private Color hoverColor = new Color(80, 80, 100);
    private Color textColor = Color.WHITE;
    private Font font = new Font("Arial", Font.PLAIN, 16);

    /**
     * Cria um novo botão com posição, tamanho e texto.
     *
     * @param x     posição X
     * @param y     posição Y
     * @param width largura do botão
     * @param height altura do botão
     * @param text  texto exibido no botão
     */
    public Button(int x, int y, int width, int height, String text) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.text = text;
    }

    /**
     * Define a ação que será executada ao clicar no botão.
     *
     * @param onClick ação a ser executada (Runnable)
     */
    public void setOnClick(Runnable onClick) {
        this.onClick = onClick;
    }

    @Override
    public void update(float dt) {
        int mouseX = Input.getMouseX();
        int mouseY = Input.getMouseY();

        hovered = mouseX >= x && mouseX <= x + width && mouseY >= y && mouseY <= y + height;

        if (hovered && Input.isMousePressed()) {
            if (onClick != null) onClick.run();
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(hovered ? hoverColor : backgroundColor);
        g.fillRect(x, y, width, height);

        g.setColor(Color.BLACK);
        g.drawRect(x, y, width, height);

        g.setColor(textColor);
        g.setFont(font);

        // Centraliza o texto no botão
        FontMetrics fm = g.getFontMetrics();
        int textX = x + (width - fm.stringWidth(text)) / 2;
        int textY = y + (height + fm.getAscent() - fm.getDescent()) / 2;

        g.drawString(text, textX, textY);
    }

    // Getters e setters opcionais (para personalização futura)

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