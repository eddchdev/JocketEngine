package jocketengine.scene.scenes;

import jocketengine.Game;
import jocketengine.entities.EntityManager;
import jocketengine.entities.PlayerEntity;
import jocketengine.scene.Scene;

import java.awt.*;

/**
 * Cena principal do jogo com gameplay simples.
 * <p>
 * Exibe fundo cinza escuro, jogador controlável via Entity,
 * física básica e chão fixo.
 * </p>
 * 
 * @author Eddch
 */
public class GameScene extends Scene {

    private static final int PLAYER_WIDTH = 40;
    private static final int PLAYER_HEIGHT = 60;

    private final float groundY;
    private EntityManager entityManager;
    private PlayerEntity player;

    public GameScene() {
        int screenHeight = Game.getGameHeight() * 3;
        groundY = screenHeight - 80;
        entityManager = new EntityManager();
    }

    @Override
    public void onLoad() {
        player = new PlayerEntity(Game.getGameWidth() * 3 / 2f, groundY - PLAYER_HEIGHT, PLAYER_WIDTH, PLAYER_HEIGHT, groundY);
        entityManager.addEntity(player);
    }

    @Override
    public void update(float dt) {
        entityManager.update(dt);
    }

    @Override
    public void render(Graphics g) {
        // Fundo cinza escuro
        g.setColor(new Color(50, 50, 50));
        g.fillRect(0, 0, Game.getGameWidth() * 3, Game.getGameHeight() * 3);

        // Chão preto
        g.setColor(Color.BLACK);
        g.fillRect(0, (int) groundY, Game.getGameWidth() * 3, 80);

        // Renderiza entidades (incluindo jogador)
        entityManager.render(g);
    }

    @Override
    public void onExit() {
        entityManager.clear();
    }
}