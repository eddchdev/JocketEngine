package jocketengine.events;

import java.util.*;
import java.util.function.Consumer;

/**
 * Gerenciador global de eventos da JocketEngine.
 * <p>
 * Permite registrar listeners para eventos específicos com prioridade,
 * e dispara os eventos para os listeners registrados respeitando a ordem de prioridade.
 * </p>
 * <p>
 * Prioridades mais altas são executadas primeiro.
 *
 * @author Eddch
 */
public class EventManager {

    /**
     * Mapa de eventos para seus listeners organizados por prioridade.
     * <p>
     * A estrutura é:
     * - Chave: classe do evento
     * - Valor: mapa onde
     * - chave: prioridade do listener
     * - valor: lista de listeners registrados naquela prioridade
     * </p>
     */
    private static final Map<Class<? extends Event>, EnumMap<EventPriority, List<Consumer<? extends Event>>>> listeners = new HashMap<>();

    /**
     * Registra um listener para um tipo específico de evento com prioridade.
     *
     * @param <T>       tipo do evento
     * @param eventType classe do evento que o listener irá escutar
     * @param priority  prioridade do listener (listeners com prioridade mais alta são executados primeiro)
     * @param listener  função que será executada quando o evento ocorrer
     */
    public static <T extends Event> void registerListener(Class<T> eventType, EventPriority priority, Consumer<T> listener) {
        listeners.computeIfAbsent(eventType, k -> new EnumMap<>(EventPriority.class)).computeIfAbsent(priority, k -> new ArrayList<>()).add(listener);
    }

    /**
     * Dispara um evento para todos os listeners registrados daquele tipo, na ordem de prioridade.
     *
     * @param <T>   tipo do evento
     * @param event instância do evento a ser disparado
     */
    @SuppressWarnings("unchecked")
    public static <T extends Event> void fireEvent(T event) {
        EnumMap<EventPriority, List<Consumer<? extends Event>>> priorityMap = listeners.get(event.getClass());
        if (priorityMap != null) {
            // Prioridades em ordem decrescente (de HIGH a LOW)
            EventPriority[] priorities = EventPriority.values();
            Arrays.sort(priorities, Comparator.reverseOrder());

            for (EventPriority priority : priorities) {
                List<Consumer<? extends Event>> eventListeners = priorityMap.get(priority);
                if (eventListeners != null) {
                    for (Consumer<? extends Event> listener : eventListeners) {
                        ((Consumer<T>) listener).accept(event);
                        if (event.isCancelled()) {
                            return; // interrompe se o evento for cancelado
                        }
                    }
                }
            }
        }
    }

    /**
     * Remove todos os listeners registrados para um tipo de evento.
     *
     * @param eventType classe do evento a limpar listeners
     */
    public static void clearListeners(Class<? extends Event> eventType) {
        listeners.remove(eventType);
    }

    /**
     * Remove todos os listeners de todos os eventos.
     */
    public static void clearAllListeners() {
        listeners.clear();
    }
}
