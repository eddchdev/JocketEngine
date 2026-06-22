package jocketengine.entities;

/**
 * Componente que faz a entidade "flutuar", oscilando verticalmente em torno
 * da posição inicial com um movimento senoidal suave.
 * <p>
 * Útil para itens colecionáveis, power-ups e elementos decorativos.
 * </p>
 *
 * @author Eddch
 */
public class BobComponent implements Component {

    private final Entity entity;
    private final float baseY;
    private final float amplitude;
    private final float speed;
    private float time;

    /**
     * @param entity    entidade alvo
     * @param amplitude deslocamento máximo, em pixels, para cima e para baixo
     * @param speed     velocidade da oscilação, em radianos por segundo
     */
    public BobComponent(Entity entity, float amplitude, float speed) {
        this.entity = entity;
        this.baseY = entity.getY();
        this.amplitude = amplitude;
        this.speed = speed;
    }

    @Override
    public void update(float dt) {
        time += dt;
        entity.setY(baseY + (float) Math.sin(time * speed) * amplitude);
    }
}
