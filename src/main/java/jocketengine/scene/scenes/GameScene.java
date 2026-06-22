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
import jocketengine.utils.Rectangle;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
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
        UIManager.clear(); // O HUD desta cena é desenhado manualmente.

        buildPlatforms();

        player = new PlayerEntity(30, 224, 14, 20);
        player.setPlatforms(platforms);
        entityManager.addEntity(player);

        spawnCoins();

        // Coletar moeda é tratado via evento: a colisão dispara, o listener pontua.
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

        // Colisão jogador x moedas → dispara CoinCollectedEvent.
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

        renderHud(g, w);
    }

    private void renderHud(Graphics g, int w) {
        g.setFont(new Font("Monospaced", Font.BOLD, 12));
        g.setColor(Color.WHITE);
        g.drawString("Moedas: " + score + "/" + totalCoins, 8, 16);

        g.setFont(new Font("SansSerif", Font.PLAIN, 10));
        g.setColor(new Color(160, 160, 180));
        g.drawString("ESC: pausar", w - 70, 16);

        if (score >= totalCoins) {
            g.setFont(new Font("Monospaced", Font.BOLD, 16));
            g.setColor(new Color(255, 220, 90));
            String msg = "Voce venceu!";
            int textWidth = g.getFontMetrics().stringWidth(msg);
            g.drawString(msg, (w - textWidth) / 2, 60);
        }
    }

    @Override
    public void onExit() {
        EventManager.clearListeners(CoinCollectedEvent.class);
        entityManager.clear();
    }
}
