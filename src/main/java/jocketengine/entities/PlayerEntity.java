package jocketengine.entities;

import jocketengine.events.EventManager;
import jocketengine.events.PlayerMoveEvent;
import jocketengine.input.Input;

import java.awt.*;

/**
 * Entidade que representa o jogador controlável na JocketEngine.
 * <p>
 * Contém lógica de movimento, física básica e renderização.
 * </p>
 * 
 * @author Eddch
 */
public class PlayerEntity extends Entity {

    private float velocityX, velocityY;
    private final float gravity = 800f;
    private final float moveSpeed = 200f;
    private final float jumpSpeed = 450f;

    private final float groundY;

    private boolean onGround;

    public PlayerEntity(float x, float y, int width, int height, float groundY) {
        super(x, y, width, height);
        this.groundY = groundY;
        this.velocityX = 0;
        this.velocityY = 0;
        this.onGround = true;
    }

    @Override
    public void update(float dt) {
        float oldX = x;
        float oldY = y;

        velocityX = 0;
        if (Input.isKeyDown(java.awt.event.KeyEvent.VK_LEFT) || Input.isKeyDown(java.awt.event.KeyEvent.VK_A)) {
            velocityX = -moveSpeed;
        }
        if (Input.isKeyDown(java.awt.event.KeyEvent.VK_RIGHT) || Input.isKeyDown(java.awt.event.KeyEvent.VK_D)) {
            velocityX = moveSpeed;
        }

        if ((Input.isKeyDown(java.awt.event.KeyEvent.VK_UP) || Input.isKeyDown(java.awt.event.KeyEvent.VK_W) || Input.isKeyDown(java.awt.event.KeyEvent.VK_SPACE)) && onGround) {
            velocityY = -jumpSpeed;
            onGround = false;
        }

        velocityY += gravity * dt;

        float tentativeX = x + velocityX * dt;
        float tentativeY = y + velocityY * dt;

        // Dispara evento
        PlayerMoveEvent moveEvent = new PlayerMoveEvent(oldX, oldY, tentativeX, tentativeY);
        EventManager.fireEvent(moveEvent);

        if (!moveEvent.isCancelled()) {
            x = moveEvent.getNewX();
            y = moveEvent.getNewY();
        }

        // Colisão com chão
        if (y + height >= groundY) {
            y = groundY - height;
            velocityY = 0;
            onGround = true;
        }

        // Limites laterais (ajustar conforme necessidade)
        int screenWidth = 960; // Exemplo fixo (3x320)
        if (x < 0) x = 0;
        if (x + width > screenWidth) x = screenWidth - width;
    }

    @Override
    public void render(Graphics g) {
        g.setColor(new Color(100, 150, 255));
        g.fillRect((int) x, (int) y, width, height);
    }
}