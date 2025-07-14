package jocketengine.events;

/**
 * Classe base abstrata para eventos da JocketEngine.
 * <p>
 * Todos os eventos personalizados da engine devem estender esta classe.
 * Contém controle básico para cancelamento de eventos.
 * </p>
 * 
 * @author Eddch
 */
public abstract class Event {

    private boolean cancelled = false;

    /**
     * Verifica se o evento foi cancelado.
     * 
     * @return true se o evento está cancelado
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Define o estado de cancelamento do evento.
     * 
     * @param cancel true para cancelar o evento
     */
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }
}