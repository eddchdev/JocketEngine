package jocketengine.scene.scenes;

import jocketengine.collision.CollisionSystem;
import jocketengine.core.Engine;
import jocketengine.entities.Entity;
import jocketengine.entities.EntityManager;
import jocketengine.entities.PlayerEntity;
import jocketengine.events.EventManager;
import jocketengine.events.EventPriority;
import jocketengine.input.Input;
import jocketengine.scene.Scene;
import jocketengine.scene.SceneManager;
import jocketengine.ui.UIManager;
import jocketengine.ui.style.UIFonts;
import jocketengine.utils.Rectangle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Cena de gameplay: um pequeno platformer que demonstra, juntos, os principais
 * sistemas da engine — entidades e componentes, colisão AABB, eventos, entrada
 * e HUD. Junte as moedas e tecle {@code ESC} para pausar.
 *
 * @author Eddch
 */
public class GameScene extends Scene {

    private final EntityManager entityManager = new EntityManager();
    private final List<Rectangle> platforms = new ArrayList<>();
    private PlayerEntity player;

    private int score;
    private int totalCoins;

    @Override
    public void onLoad() {
        UIManager.clear(); // O HUD desta cena é desenhado em renderUI.

        buildPlatforms();

        player = new PlayerEntity(30, 224, 14, 20);
        player.setPlatforms(platforms);
        entityManager.addEntity(player);

        spawnCoins();

        EventManager.clearListeners(CoinCollectedEvent.class);
        EventManager.registerListener(CoinCollectedEvent.class, EventPriority.NORMAL, event -> {
            event.getCoin().destroy();
            score++;
        });
    }

    private void buildPlatforms() {
        platforms.clear();
        int w = Engine.getWidth();
        platforms.add(new Rectangle(0, 250, w, 20));   // chão
        platforms.add(new Rectangle(80, 205, 70, 12));
        platforms.add(new Rectangle(210, 165, 80, 12));
        platforms.add(new Rectangle(330, 120, 90, 12));
    }

    private void spawnCoins() {
        float[][] positions = {
                {45, 232}, {140, 232}, {110, 185}, {245, 145}, {370, 100}
        };
        totalCoins = positions.length;
        for (float[] p : positions) {
            entityManager.addEntity(new CoinEntity(p[0], p[1]));
        }
    }

    @Override
    public void update(float dt) {
        if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            SceneManager.pushScene(new PauseScene());
            return;
        }

        entityManager.update(dt);

        for (Entity entity : entityManager.getEntities()) {
            if (entity instanceof CoinEntity coin && CollisionSystem.collides(player, coin)) {
                EventManager.fireEvent(new CoinCollectedEvent(coin));
            }
        }
    }

    @Override
    public void render(Graphics g) {
        int w = Engine.getWidth();
        int h = Engine.getHeight();

        // Céu
        g.setColor(new Color(28, 32, 54));
        g.fillRect(0, 0, w, h);

        // Plataformas
        for (Rectangle p : platforms) {
            g.setColor(new Color(70, 90, 120));
            g.fillRect((int) p.x, (int) p.y, (int) p.width, (int) p.height);
            g.setColor(new Color(110, 140, 180));
            g.fillRect((int) p.x, (int) p.y, (int) p.width, 2);
        }

        entityManager.render(g);
    }

    @Override
    public void renderUI(Graphics2D g) {
        int w = Engine.getWidth();

        g.setFont(UIFonts.HUD);
        g.setColor(Color.WHITE);
        g.drawString("Moedas: " + score + "/" + totalCoins, 8, 15);

        g.setFont(UIFonts.SMALL);
        g.setColor(new Color(150, 150, 175));
        String hint = "ESC: pausar";
        g.drawString(hint, w - g.getFontMetrics().stringWidth(hint) - 8, 14);

        if (score >= totalCoins) {
            g.setFont(UIFonts.TITLE);
            g.setColor(new Color(255, 220, 90));
            String msg = "Você venceu!";
            g.drawString(msg, (w - g.getFontMetrics().stringWidth(msg)) / 2, 62);
        }
    }

    @Override
    public void onExit() {
        EventManager.clearListeners(CoinCollectedEvent.class);
        entityManager.clear();
    }
}
