package jocketengine.events;

/**
 * Evento que pode ser cancelado independentemente de outras propriedades.
 * <p>
 * Usado quando se quer distinguir eventos canceláveis de não canceláveis.
 * </p>
 * 
 * @author Eddch
 */
public abstract class CancellableEvent extends Event {

    private boolean cancelled = false;

    /**
     * Cancela este evento.
     */
    public void cancel() {
        this.cancelled = true;
    }

    /**
     * Verifica se o evento foi cancelado.
     * 
     * @return true se foi cancelado
     */
    public boolean isCancelled() {
        return cancelled;
    }
}