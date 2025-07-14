package jocketengine.scene.scenes;

import jocketengine.Game;
import jocketengine.scene.Scene;
import jocketengine.scene.SceneManager;
import jocketengine.ui.UIManager;
import jocketengine.ui.elements.Button;
import jocketengine.ui.elements.Label;

import java.awt.*;

/**
 * Cena de pausa do jogo.
 * <p>
 * Sobrepõe a GameScene com um menu simples de pausa,
 * permitindo continuar ou voltar ao menu principal.
 * </p>
 *
 * @author Eddch
 */
public class PauseScene extends Scene {

    private Font titleFont;

    @Override
    public void onLoad() {
        UIManager.clear();

        int centerX = Game.getGameWidth() * 3 / 2;

        titleFont = new Font("Arial", Font.BOLD, 32);

        // Título "Pausado"
        UIManager.add(new Label(centerX - 60, 100, "Pausado", titleFont, Color.WHITE));

        // Botão: Continuar (volta para GameScene)
        Button resumeButton = new Button(centerX - 100, 180, 200, 40, "Continuar");
        resumeButton.setOnClick(() -> SceneManager.popScene()); // volta para GameScene
        UIManager.add(resumeButton);

        // Botão: Menu Principal
        Button menuButton = new Button(centerX - 100, 240, 200, 40, "Menu Principal");
        menuButton.setOnClick(() -> SceneManager.changeScene(new MainMenuScene()));
        UIManager.add(menuButton);
    }

    @Override
    public void update(float dt) {
        // Pode adicionar efeitos visuais ou animações futuras
    }

    @Override
    public void render(Graphics g) {
        // Fundo transparente escurecido sobre o jogo
        g.setColor(new Color(0, 0, 0, 180));
        g.fillRect(0, 0, Game.getGameWidth() * 3, Game.getGameHeight() * 3);
    }

    @Override
    public void onExit() {
        UIManager.clear();
    }
}