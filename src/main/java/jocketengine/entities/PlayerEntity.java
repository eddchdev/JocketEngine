package jocketengine.entities;

import jocketengine.core.Engine;
import jocketengine.events.EventManager;
import jocketengine.events.PlayerMoveEvent;
import jocketengine.graphics.Sprite;
import jocketengine.input.Input;
import jocketengine.utils.Rectangle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Jogador controlável, desenhado como um pequeno cavaleiro de máscara branca e
 * chifres (inspirado em Hollow Knight), em pixel art. Anda na horizontal, pula e
 * sofre gravidade, colidindo com plataformas via AABB e resolução por eixo.
 * <p>
 * Dispara um {@link PlayerMoveEvent} cancelável sempre que se move.
 * </p>
 *
 * @author Eddch
 */
public class PlayerEntity extends Entity {

    private static final float MOVE_SPEED = 130f;
    private static final float JUMP_SPEED = 290f;
    private static final float GRAVITY = 900f;
    private static final float MAX_FALL_SPEED = 600f;

    // Mapa de pixels do cavaleiro (14 x 22). '.' = transparente.
    private static final String[] KNIGHT = {
            "..o........o..",
            "..ow......wo..",
            ".oww......wwo.",
            ".owwo....owwo.",
            ".owwwo..owwwo.",
            ".oowwwwwwwwoo.",
            ".owwwwwwwwwwo.",
            ".owweewweewwo.",
            ".owweewweewwo.",
            ".owwwwwwwwwwo.",
            "..owwwwwwwwo..",
            "..obbbbbbbbo..",
            ".obbbbbbbbbbo.",
            ".obbbbbbbbbbo.",
            ".obbbbbbbbbbo.",
            ".obbbbbbbbbbo.",
            "..obbbbbbbbo..",
            "..obbbbbbbbo..",
            "...obo..obo...",
            "...obo..obo...",
            "...obo..obo...",
            "...ooo..ooo..."
    };

    private static final Sprite SPRITE = buildSprite();

    private static Sprite buildSprite() {
        Map<Character, Color> palette = new HashMap<>();
        palette.put('o', new Color(20, 22, 30));    // contorno
        palette.put('w', new Color(238, 240, 232)); // máscara clara
        palette.put('e', new Color(12, 12, 16));    // olhos vazios
        palette.put('b', new Color(40, 44, 62));    // corpo/manto
        return Sprite.fromRows(KNIGHT, palette);
    }

    private final float spawnX;
    private final float spawnY;

    private float velocityX;
    private float velocityY;
    private boolean onGround;
    private boolean facingLeft;

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

        velocityX = 0;
        if (Input.isKeyDown(KeyEvent.VK_LEFT) || Input.isKeyDown(KeyEvent.VK_A)) {
            velocityX -= MOVE_SPEED;
        }
        if (Input.isKeyDown(KeyEvent.VK_RIGHT) || Input.isKeyDown(KeyEvent.VK_D)) {
            velocityX += MOVE_SPEED;
        }

        if (velocityX < 0) {
            facingLeft = true;
        } else if (velocityX > 0) {
            facingLeft = false;
        }

        boolean jump = Input.isKeyPressed(KeyEvent.VK_SPACE)
                || Input.isKeyPressed(KeyEvent.VK_UP)
                || Input.isKeyPressed(KeyEvent.VK_W);
        if (jump && onGround) {
            velocityY = -JUMP_SPEED;
            onGround = false;
        }

        velocityY = Math.min(velocityY + GRAVITY * dt, MAX_FALL_SPEED);

        moveHorizontally(velocityX * dt);
        moveVertically(velocityY * dt);
        clampToWorld();

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
        if (worldHeight > 0 && y > worldHeight + height) {
            x = spawnX;
            y = spawnY;
            velocityX = 0;
            velocityY = 0;
        }
    }

    @Override
    public void render(Graphics g) {
        // Centraliza o sprite na horizontal e alinha os pés à base da hitbox,
        // permitindo que os chifres ultrapassem o topo da caixa de colisão.
        int drawX = Math.round(x + width / 2f - SPRITE.getWidth() / 2f);
        int drawY = Math.round(y + height - SPRITE.getHeight());
        SPRITE.draw(g, drawX, drawY, facingLeft);
    }
}
