package jocketengine.events;

/**
 * API de conveniência para registro e disparo de eventos.
 * <p>
 * Inspirado por APIs como Bukkit e PocketMine.
 * </p>
 * 
 * @author Eddch
 */
public class Events {

    /**
     * Registra um listener com prioridade NORMAL.
     * 
     * @param <T>       tipo do evento
     * @param eventType classe do evento
     * @param listener  ação a ser executada quando o evento ocorrer
     */
    public static <T extends Event> void on(Class<T> eventType, EventListener<T> listener) {
        EventManager.registerListener(eventType, EventPriority.NORMAL, listener::onEvent);
    }

    /**
     * Registra um listener com prioridade personalizada.
     * 
     * @param <T>       tipo do evento
     * @param eventType classe do evento
     * @param priority  prioridade de execução
     * @param listener  ação a ser executada
     */
    public static <T extends Event> void on(Class<T> eventType, EventPriority priority, EventListener<T> listener) {
        EventManager.registerListener(eventType, priority, listener::onEvent);
    }

    /**
     * Dispara um evento.
     * 
     * @param <T>   tipo do evento
     * @param event instância do evento
     */
    public static <T extends Event> void trigger(T event) {
        EventManager.fireEvent(event);
    }
}