package jocketengine.scene.scenes;

import jocketengine.core.Engine;
import jocketengine.input.Input;
import jocketengine.scene.Scene;
import jocketengine.scene.SceneManager;
import jocketengine.ui.UIManager;
import jocketengine.ui.elements.Button;
import jocketengine.ui.elements.Label;
import jocketengine.ui.style.UIFonts;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;

/**
 * Menu de pausa, exibido como sobreposição translúcida por cima da
 * {@link GameScene} congelada.
 *
 * @author Eddch
 */
public class PauseScene extends Scene {

    @Override
    public void onLoad() {
        UIManager.clear();

        int centerX = Engine.getWidth() / 2;

        UIManager.add(new Label(centerX, 92, "Pausado", UIFonts.TITLE, Color.WHITE)
                .setAlign(Label.Align.CENTER));

        int buttonWidth = 170;
        int buttonX = centerX - buttonWidth / 2;

        Button resume = new Button(buttonX, 130, buttonWidth, 32, "Continuar");
        resume.setOnClick(SceneManager::popScene);
        UIManager.add(resume);

        Button menu = new Button(buttonX, 172, buttonWidth, 32, "Menu Principal");
        menu.setOnClick(() -> SceneManager.changeScene(new MainMenuScene()));
        UIManager.add(menu);
    }

    @Override
    public void update(float dt) {
        if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            SceneManager.popScene();
        }
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(0, 0, 0, 170));
        g.fillRect(0, 0, Engine.getWidth(), Engine.getHeight());
    }

    @Override
    public void onExit() {
        UIManager.clear();
    }
}
