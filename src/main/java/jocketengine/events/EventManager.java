package jocketengine.events;

import java.util.ArrayList;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Gerenciador global de eventos da JocketEngine.
 * <p>
 * Permite registrar ouvintes para tipos de evento com uma {@link EventPriority},
 * e disparar eventos respeitando a ordem de prioridade — listeners de prioridade
 * <b>mais alta são executados primeiro</b>. Um listener pode cancelar o evento
 * (quando aplicável), interrompendo a propagação para os demais.
 * </p>
 *
 * @author Eddch
 */
public final class EventManager {

    private static final Map<Class<? extends Event>, EnumMap<EventPriority, List<Consumer<? extends Event>>>> listeners =
            new HashMap<>();

    private EventManager() {
    }

    /**
     * Registra um listener para um tipo de evento.
     *
     * @param eventType classe do evento escutado
     * @param priority  prioridade de execução
     * @param listener  ação executada quando o evento ocorrer
     * @param <T>       tipo do evento
     */
    public static <T extends Event> void registerListener(Class<T> eventType, EventPriority priority, Consumer<T> listener) {
        listeners
                .computeIfAbsent(eventType, k -> new EnumMap<>(EventPriority.class))
                .computeIfAbsent(priority, k -> new ArrayList<>())
                .add(listener);
    }

    /**
     * Dispara um evento para todos os listeners do seu tipo, em ordem de prioridade
     * (HIGHEST → LOWEST). Para imediatamente se o evento for cancelado.
     *
     * @param event evento a disparar
     * @param <T>   tipo do evento
     */
    @SuppressWarnings("unchecked")
    public static <T extends Event> void fireEvent(T event) {
        EnumMap<EventPriority, List<Consumer<? extends Event>>> priorityMap = listeners.get(event.getClass());
        if (priorityMap == null) {
            return;
        }

        // EventPriority é declarado de HIGHEST a LOWEST, então values() já está na ordem certa.
        for (EventPriority priority : EventPriority.values()) {
            List<Consumer<? extends Event>> eventListeners = priorityMap.get(priority);
            if (eventListeners == null) {
                continue;
            }
            for (Consumer<? extends Event> listener : eventListeners) {
                ((Consumer<T>) listener).accept(event);
                if (event.isCancelled()) {
                    return;
                }
            }
        }
    }

    /**
     * Remove todos os listeners de um tipo de evento.
     *
     * @param eventType classe do evento
     */
    public static void clearListeners(Class<? extends Event> eventType) {
        listeners.remove(eventType);
    }

    /** Remove todos os listeners de todos os eventos. */
    public static void clearAllListeners() {
        listeners.clear();
    }
}
