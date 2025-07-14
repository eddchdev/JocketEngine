package jocketengine.events;

/**
 * Interface funcional que representa um listener de evento.
 * <p>
 * Pode ser usada para registrar ações que escutam eventos.
 * </p>
 * 
 * @param <T> tipo do evento
 * 
 * @author Eddch
 */
@FunctionalInterface
public interface EventListener<T extends Event> {
    void onEvent(T event);
}