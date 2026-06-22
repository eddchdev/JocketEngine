package jocketengine.scene.scenes;

import jocketengine.core.Engine;
import jocketengine.scene.Scene;
import jocketengine.scene.SceneManager;
import jocketengine.ui.UIManager;
import jocketengine.ui.elements.Button;
import jocketengine.ui.elements.Label;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Menu principal: título, botão de jogar e botão de sair.
 *
 * @author Eddch
 */
public class MainMenuScene extends Scene {

    @Override
    public void onLoad() {
        UIManager.clear();

        int centerX = Engine.getWidth() / 2;

        UIManager.add(new Label(centerX, 70, "JocketEngine",
                new Font("Monospaced", Font.BOLD, 32), new Color(120, 200, 255))
                .setAlign(Label.Align.CENTER));

        UIManager.add(new Label(centerX, 92, "engine 2D em Java",
                new Font("SansSerif", Font.PLAIN, 11), new Color(150, 150, 180))
                .setAlign(Label.Align.CENTER));

        int buttonWidth = 160;
        int buttonX = centerX - buttonWidth / 2;

        Button play = new Button(buttonX, 130, buttonWidth, 34, "Jogar");
        play.setOnClick(() -> SceneManager.changeScene(new GameScene()));
        UIManager.add(play);

        Button quit = new Button(buttonX, 175, buttonWidth, 34, "Sair");
        quit.setOnClick(Engine::exit);
        UIManager.add(quit);

        UIManager.add(new Label(centerX, 245, "Setas/WASD: mover   Espaco: pular",
                new Font("SansSerif", Font.PLAIN, 10), new Color(120, 120, 140))
                .setAlign(Label.Align.CENTER));
    }

    @Override
    public void update(float dt) {
        // Sem lógica adicional; a UI cuida da interação.
    }

    @Override
    public void render(Graphics g) {
        int w = Engine.getWidth();
        int h = Engine.getHeight();
        g.setColor(new Color(18, 18, 28));
        g.fillRect(0, 0, w, h);
    }

    @Override
    public void onExit() {
        UIManager.clear();
    }
}
