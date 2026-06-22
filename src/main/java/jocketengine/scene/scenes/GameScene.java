package jocketengine.scene.scenes;

import jocketengine.audio.Audio;
import jocketengine.audio.Sfx;
import jocketengine.collision.CollisionSystem;
import jocketengine.core.Engine;
import jocketengine.entities.Entity;
import jocketengine.entities.EntityManager;
import jocketengine.entities.PlayerEntity;
import jocketengine.events.EventManager;
import jocketengine.events.EventPriority;
import jocketengine.graphics.Camera2D;
import jocketengine.input.Input;
import jocketengine.input.InputMap;
import jocketengine.particles.ParticleSystem;
import jocketengine.scene.Scene;
import jocketengine.scene.SceneManager;
import jocketengine.tilemap.TileMap;
import jocketengine.tween.Easing;
import jocketengine.tween.Tween;
import jocketengine.tween.TweenManager;
import jocketengine.ui.UIManager;
import jocketengine.ui.style.UIFonts;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;

import javax.sound.sampled.Clip;

/**
 * Cena de gameplay que demonstra a engine completa: mundo em <b>tilemap</b> com
 * <b>câmera</b> que segue o jogador, <b>partículas</b> e <b>SFX procedural</b> ao
 * coletar moedas, <b>tween</b> na mensagem de vitória e <b>input por ações</b>.
 *
 * @author Eddch
 */
public class GameScene extends Scene {

    private static final int TILE = 30;

    private final EntityManager entityManager = new EntityManager();
    private final ParticleSystem particles = new ParticleSystem();
    private final TweenManager tweens = new TweenManager();

    private TileMap tileMap;
    private Camera2D camera;
    private PlayerEntity player;

    private Clip coinSound;

    private int score;
    private int totalCoins;
    private boolean winStarted;
    private float winScale;

    @Override
    public void onLoad() {
        UIManager.clear();

        tileMap = buildLevel();
        camera = new Camera2D(Engine.getWidth(), Engine.getHeight());
        camera.setBounds(0, 0, tileMap.getWorldWidth(), tileMap.getWorldHeight());

        InputMap controls = new InputMap()
                .bind("left", KeyEvent.VK_LEFT, KeyEvent.VK_A)
                .bind("right", KeyEvent.VK_RIGHT, KeyEvent.VK_D)
                .bind("jump", KeyEvent.VK_SPACE, KeyEvent.VK_UP, KeyEvent.VK_W);

        player = new PlayerEntity(40, 180, 14, 20);
        player.setPlatforms(tileMap.getAllSolidBounds());
        player.setControls(controls);
        player.setJumpSound(Sfx.jump());
        entityManager.addEntity(player);

        spawnCoins();
        coinSound = Sfx.coin();

        EventManager.clearListeners(CoinCollectedEvent.class);
        EventManager.registerListener(CoinCollectedEvent.class, EventPriority.NORMAL, this::onCoinCollected);
    }

    private TileMap buildLevel() {
        int cols = 40;
        int rows = 9;
        int[][] tiles = new int[rows][cols];

        // Chão ao longo de todo o mundo.
        for (int c = 0; c < cols; c++) {
            tiles[8][c] = 1;
        }
        // Plataformas flutuantes (tile 2).
        platform(tiles, 6, 9, 5);
        platform(tiles, 13, 16, 4);
        platform(tiles, 22, 26, 3);
        platform(tiles, 31, 34, 5);

        return new TileMap(tiles, TILE, TILE)
                .setSolid(1, 2)
                .setColor(1, new Color(56, 62, 84))
                .setColor(2, new Color(74, 96, 128));
    }

    private static void platform(int[][] tiles, int colStart, int colEnd, int row) {
        for (int c = colStart; c <= colEnd; c++) {
            tiles[row][c] = 2;
        }
    }

    private void spawnCoins() {
        float[][] positions = {
                {120, 224}, {235, 136}, {445, 106}, {730, 76}, {985, 136}
        };
        totalCoins = positions.length;
        for (float[] p : positions) {
            entityManager.addEntity(new CoinEntity(p[0], p[1]));
        }
    }

    private void onCoinCollected(CoinCollectedEvent event) {
        CoinEntity coin = event.getCoin();
        particles.burst(coin.getX() + coin.getWidth() / 2f, coin.getY() + coin.getHeight() / 2f,
                16, new Color(255, 210, 70));
        Audio.play(coinSound);
        coin.destroy();
        score++;

        if (score >= totalCoins && !winStarted) {
            winStarted = true;
            tweens.add(new Tween(0, 1, 0.6f, Easing.BACK_OUT, value -> winScale = value));
        }
    }

    @Override
    public void update(float dt) {
        if (Input.isKeyPressed(KeyEvent.VK_ESCAPE)) {
            SceneManager.pushScene(new PauseScene());
            return;
        }

        entityManager.update(dt);
        particles.update(dt);
        tweens.update(dt);

        float maxX = tileMap.getWorldWidth() - player.getWidth();
        if (player.getX() > maxX) {
            player.setX(maxX);
        }

        camera.follow(player.getX() + player.getWidth() / 2f,
                player.getY() + player.getHeight() / 2f, 0.12f);

        for (Entity entity : entityManager.getEntities()) {
            if (entity instanceof CoinEntity coin && CollisionSystem.collides(player, coin)) {
                EventManager.fireEvent(new CoinCollectedEvent(coin));
            }
        }
    }

    @Override
    public void render(Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g.setColor(new Color(26, 30, 50));
        g.fillRect(0, 0, Engine.getWidth(), Engine.getHeight());

        camera.begin(g2);
        tileMap.render(g);
        entityManager.render(g);
        particles.render(g);
        camera.end(g2);
    }

    @Override
    public void renderUI(Graphics2D g) {
        g.setFont(UIFonts.HUD);
        g.setColor(Color.WHITE);
        g.drawString("Moedas: " + score + "/" + totalCoins, 8, 15);

        g.setFont(UIFonts.SMALL);
        g.setColor(new Color(150, 150, 175));
        String hint = "ESC: pausar";
        g.drawString(hint, Engine.getWidth() - g.getFontMetrics().stringWidth(hint) - 8, 14);

        if (winStarted) {
            Font font = UIFonts.TITLE.deriveFont(28f * Math.max(0.01f, winScale));
            g.setFont(font);
            g.setColor(new Color(255, 220, 90));
            String msg = "Você venceu!";
            int textWidth = g.getFontMetrics().stringWidth(msg);
            g.drawString(msg, (Engine.getWidth() - textWidth) / 2, 70);
        }
    }

    @Override
    public void onExit() {
        EventManager.clearListeners(CoinCollectedEvent.class);
        entityManager.clear();
        particles.clear();
        tweens.clear();
    }
}
