package jocketengine;

import jocketengine.core.Engine;
import jocketengine.core.GameConfig;
import jocketengine.scene.scenes.MainMenuScene;

/**
 * Ponto de entrada do jogo de demonstração da JocketEngine.
 * <p>
 * Mostra como, na prática, se usa a engine: cria uma {@link GameConfig},
 * escolhe a cena inicial e entrega tudo para a {@link Engine}.
 * </p>
 *
 * @author Eddch
 */
public final class Game {

    private Game() {
    }

    /**
     * Inicia o demo da JocketEngine.
     *
     * @param args ignorado
     */
    public static void main(String[] args) {
        GameConfig config = new GameConfig()
                .title("JocketEngine Demo")
                .logicalSize(480, 270)
                .scale(3)
                .targetFps(60);

        Engine.start(config, new MainMenuScene());
    }
}
