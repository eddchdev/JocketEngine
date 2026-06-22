package jocketengine.scene.scenes;

import jocketengine.entities.BobComponent;
import jocketengine.entities.Entity;

import java.awt.Color;
import java.awt.Graphics;

/**
 * Moeda colecionável do demo. Demonstra o sistema de componentes: usa um
 * {@link BobComponent} para flutuar suavemente, sem nenhuma lógica própria.
 *
 * @author Eddch
 */
public class CoinEntity extends Entity {

    private static final int SIZE = 10;

    public CoinEntity(float x, float y) {
        super(x, y, SIZE, SIZE);
        addComponent(new BobComponent(this, 3f, 4f));
    }

    @Override
    public void update(float dt) {
        // Todo o movimento vem do BobComponent.
    }

    @Override
    public void render(Graphics g) {
        int ix = Math.round(x);
        int iy = Math.round(y);
        g.setColor(new Color(255, 210, 70));
        g.fillOval(ix, iy, SIZE, SIZE);
        g.setColor(new Color(180, 130, 20));
        g.drawOval(ix, iy, SIZE - 1, SIZE - 1);
    }
}
