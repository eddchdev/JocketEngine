package jocketengine.tween;

import java.util.ArrayList;
import java.util.List;

/**
 * Atualiza um conjunto de {@link Tween}s e descarta os concluídos.
 *
 * @author Eddch
 */
public class TweenManager {

    private final List<Tween> tweens = new ArrayList<>();

    /** Adiciona (e retorna) um tween para ser gerenciado. */
    public Tween add(Tween tween) {
        tweens.add(tween);
        return tween;
    }

    /**
     * Atualiza todos os tweens e remove os finalizados.
     *
     * @param dt delta time em segundos
     */
    public void update(float dt) {
        for (Tween tween : new ArrayList<>(tweens)) {
            tween.update(dt);
        }
        tweens.removeIf(Tween::isDone);
    }

    /** @return quantidade de tweens ativos. */
    public int size() {
        return tweens.size();
    }

    public void clear() {
        tweens.clear();
    }
}
