package jocketengine.entities;

import jocketengine.audio.Audio;
import jocketengine.core.Engine;
import jocketengine.events.EventManager;
import jocketengine.events.PlayerMoveEvent;
import jocketengine.graphics.Sprite;
import jocketengine.graphics.SpriteAnimation;
import jocketengine.input.InputMap;
import jocketengine.utils.Rectangle;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sound.sampled.Clip;

/**
 * Jogador controlável, desenhado como um pequeno cavaleiro de máscara branca e
 * chifres (inspirado em Hollow Knight), em pixel art e com ciclo de caminhada.
 * <p>
 * Usa um {@link InputMap} (ações "left", "right", "jump"), sofre gravidade e
 * colide com plataformas via AABB. Dispara um {@link PlayerMoveEvent} cancelável.
 * </p>
 *
 * @author Eddch
 */
public class PlayerEntity extends Entity {

    private static final float MOVE_SPEED = 130f;
    private static final float JUMP_SPEED = 290f;
    private static final float GRAVITY = 900f;
    private static final float MAX_FALL_SPEED = 600f;

    // Corpo do cavaleiro (linhas 0-17), comum aos dois quadros de animação.
    private static final String[] KNIGHT_BODY = {
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
            "..obbbbbbbbo.."
    };

    // Pernas: quadro de contato (juntas) e quadro de passada (afastadas).
    private static final String[] LEGS_CONTACT = {
            "...obo..obo...",
            "...obo..obo...",
            "...obo..obo...",
            "...ooo..ooo..."
    };
    private static final String[] LEGS_STRIDE = {
            "...obo..obo...",
            "...obo..obo...",
            "..obo....obo..",
            "..ooo....ooo.."
    };

    private static final Sprite FRAME_A = buildSprite(LEGS_CONTACT);
    private static final Sprite FRAME_B = buildSprite(LEGS_STRIDE);

    private static Sprite buildSprite(String[] legs) {
        String[] rows = new String[KNIGHT_BODY.length + legs.length];
        System.arraycopy(KNIGHT_BODY, 0, rows, 0, KNIGHT_BODY.length);
        System.arraycopy(legs, 0, rows, KNIGHT_BODY.length, legs.length);

        Map<Character, Color> palette = new HashMap<>();
        palette.put('o', new Color(20, 22, 30));
        palette.put('w', new Color(238, 240, 232));
        palette.put('e', new Color(12, 12, 16));
        palette.put('b', new Color(40, 44, 62));
        return Sprite.fromRows(rows, palette);
    }

    private static InputMap defaultControls() {
        return new InputMap()
                .bind("left", KeyEvent.VK_LEFT, KeyEvent.VK_A)
                .bind("right", KeyEvent.VK_RIGHT, KeyEvent.VK_D)
                .bind("jump", KeyEvent.VK_SPACE, KeyEvent.VK_UP, KeyEvent.VK_W);
    }

    private final float spawnX;
    private final float spawnY;
    private final SpriteAnimation walk =
            new SpriteAnimation(new java.awt.image.BufferedImage[]{FRAME_A.getImage(), FRAME_B.getImage()}, 0.13f);

    private float velocityX;
    private float velocityY;
    private boolean onGround;
    private boolean facingLeft;
    private boolean moving;

    private InputMap controls = defaultControls();
    private Clip jumpSound;
    private List<Rectangle> platforms = new ArrayList<>();

    public PlayerEntity(float x, float y, int width, int height) {
        super(x, y, width, height);
        this.spawnX = x;
        this.spawnY = y;
    }

    public void setPlatforms(List<Rectangle> platforms) {
        this.platforms = platforms;
    }

    /** Define o mapeamento de teclas (rebind). */
    public void setControls(InputMap controls) {
        this.controls = controls;
    }

    /** Define o som tocado ao pular (pode ser {@code null}). */
    public void setJumpSound(Clip jumpSound) {
        this.jumpSound = jumpSound;
    }

    public boolean isOnGround() {
        return onGround;
    }

    @Override
    public void update(float dt) {
        float oldX = x;
        float oldY = y;

        velocityX = 0;
        if (controls.isDown("left")) {
            velocityX -= MOVE_SPEED;
        }
        if (controls.isDown("right")) {
            velocityX += MOVE_SPEED;
        }
        if (velocityX < 0) {
            facingLeft = true;
        } else if (velocityX > 0) {
            facingLeft = false;
        }

        if (controls.isPressed("jump") && onGround) {
            velocityY = -JUMP_SPEED;
            onGround = false;
            Audio.play(jumpSound);
        }

        velocityY = Math.min(velocityY + GRAVITY * dt, MAX_FALL_SPEED);

        moveHorizontally(velocityX * dt);
        moveVertically(velocityY * dt);
        clampToWorld();

        moving = onGround && velocityX != 0;
        if (moving) {
            walk.update(dt);
        } else {
            walk.reset();
        }

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
        int worldHeight = Engine.getHeight();
        if (x < 0) {
            x = 0;
        }
        if (worldHeight > 0 && y > worldHeight + height + 200) {
            x = spawnX;
            y = spawnY;
            velocityX = 0;
            velocityY = 0;
        }
    }

    @Override
    public void render(Graphics g) {
        Sprite frame = (moving && walk.getCurrentFrameIndex() == 1) ? FRAME_B : FRAME_A;
        int drawX = Math.round(x + width / 2f - frame.getWidth() / 2f);
        int drawY = Math.round(y + height - frame.getHeight());
        frame.draw(g, drawX, drawY, facingLeft);
    }
}
