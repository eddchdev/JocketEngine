package jocketengine.events;

/**
 * Evento que oferece um atalho semântico para cancelamento.
 * <p>
 * Reaproveita o estado de cancelamento de {@link Event} (sem duplicá-lo),
 * apenas expondo um {@link #cancel()} mais expressivo.
 * </p>
 *
 * @author Eddch
 */
public abstract class CancellableEvent extends Event {

    /** Cancela este evento (equivale a {@code setCancelled(true)}). */
    public void cancel() {
        setCancelled(true);
    }
}
