package jocketengine.events;

/**
 * Evento disparado quando o jogador se move.
 * <p>
 * Contém as posições antigas e novas,
 * permitindo modificar ou cancelar o movimento.
 * </p>
 * 
 * @author Eddch
 */
public class PlayerMoveEvent extends Event {

    private final float oldX, oldY;
    private float newX, newY;

    public PlayerMoveEvent(float oldX, float oldY, float newX, float newY) {
        this.oldX = oldX;
        this.oldY = oldY;
        this.newX = newX;
        this.newY = newY;
    }

    public float getOldX() {
        return oldX;
    }

    public float getOldY() {
        return oldY;
    }

    public float getNewX() {
        return newX;
    }

    public float getNewY() {
        return newY;
    }

    public void setNewX(float newX) {
        this.newX = newX;
    }

    public void setNewY(float newY) {
        this.newY = newY;
    }
}