package jocketengine.entities;

import jocketengine.core.Engine;
import jocketengine.events.EventManager;
import jocketengine.events.PlayerMoveEvent;
import jocketengine.input.Input;
import jocketengine.utils.Rectangle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Jogador controlável: anda na horizontal, pula e sofre gravidade, colidindo
 * com uma lista de plataformas via AABB e resolução de colisão por eixo.
 * <p>
 * Dispara um {@link PlayerMoveEvent} cancelável sempre que se move, permitindo
 * que outros sistemas (scripts, gatilhos, anti-cheat) reajam ou bloqueiem.
 * </p>
 *
 * @author Eddch
 */
public class PlayerEntity extends Entity {

    private static final float MOVE_SPEED = 130f;
    private static final float JUMP_SPEED = 290f;
    private static final float GRAVITY = 900f;
    private static final float MAX_FALL_SPEED = 600f;

    private final float spawnX;
    private final float spawnY;

    private float velocityX;
    private float velocityY;
    private boolean onGround;

    private List<Rectangle> platforms = new ArrayList<>();

    public PlayerEntity(float x, float y, int width, int height) {
        super(x, y, width, height);
        this.spawnX = x;
        this.spawnY = y;
    }

    /** Define as plataformas sólidas com as quais o jogador colide. */
    public void setPlatforms(List<Rectangle> platforms) {
        this.platforms = platforms;
    }

    /** @return true se o jogador está apoiado em uma superfície. */
    public boolean isOnGround() {
        return onGround;
    }

    @Override
    public void update(float dt) {
        float oldX = x;
        float oldY = y;

        // Entrada horizontal
        velocityX = 0;
        if (Input.isKeyDown(KeyEvent.VK_LEFT) || Input.isKeyDown(KeyEvent.VK_A)) {
            velocityX -= MOVE_SPEED;
        }
        if (Input.isKeyDown(KeyEvent.VK_RIGHT) || Input.isKeyDown(KeyEvent.VK_D)) {
            velocityX += MOVE_SPEED;
        }

        // Pulo
        boolean jump = Input.isKeyPressed(KeyEvent.VK_SPACE)
                || Input.isKeyPressed(KeyEvent.VK_UP)
                || Input.isKeyPressed(KeyEvent.VK_W);
        if (jump && onGround) {
            velocityY = -JUMP_SPEED;
            onGround = false;
        }

        // Gravidade
        velocityY = Math.min(velocityY + GRAVITY * dt, MAX_FALL_SPEED);

        // Movimento + colisão resolvidos por eixo
        moveHorizontally(velocityX * dt);
        moveVertically(velocityY * dt);
        clampToWorld();

        // Notifica (cancelável) que o jogador se moveu
        if (x != oldX || y != oldY) {
            PlayerMoveEvent event = new PlayerMoveEvent(oldX, oldY, x, y);
            EventManager.fireEvent(event);
            if (event.isCancelled()) {
                x = oldX;
                y = oldY;
            } else {
                x = event.getNewX();
                y = event.getNewY();
            }
        }
    }

    private void moveHorizontally(float dx) {
        x += dx;
        Rectangle bounds = getBounds();
        for (Rectangle platform : platforms) {
            if (bounds.intersects(platform)) {
                if (dx > 0) {
                    x = platform.x - width;
                } else if (dx < 0) {
                    x = platform.x + platform.width;
                }
                velocityX = 0;
                bounds = getBounds();
            }
        }
    }

    private void moveVertically(float dy) {
        y += dy;
        onGround = false;
        Rectangle bounds = getBounds();
        for (Rectangle platform : platforms) {
            if (bounds.intersects(platform)) {
                if (dy > 0) {
                    y = platform.y - height;
                    onGround = true;
                } else if (dy < 0) {
                    y = platform.y + platform.height;
                }
                velocityY = 0;
                bounds = getBounds();
            }
        }
    }

    private void clampToWorld() {
        int worldWidth = Engine.getWidth();
        int worldHeight = Engine.getHeight();

        if (x < 0) {
            x = 0;
        }
        if (worldWidth > 0 && x + width > worldWidth) {
            x = worldWidth - width;
        }
        // Caiu para fora do mundo: reaparece no ponto inicial.
        if (worldHeight > 0 && y > worldHeight + height) {
            x = spawnX;
            y = spawnY;
            velocityX = 0;
            velocityY = 0;
        }
    }

    @Override
    public void render(Graphics g) {
        int ix = Math.round(x);
        int iy = Math.round(y);

        g.setColor(new Color(90, 150, 255));
        g.fillRect(ix, iy, width, height);
        g.setColor(new Color(200, 220, 255));
        g.drawRect(ix, iy, width - 1, height - 1);
    }
}
