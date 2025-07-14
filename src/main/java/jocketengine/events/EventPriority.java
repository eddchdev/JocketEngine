package jocketengine.events;

/**
 * Define a ordem de execução dos listeners registrados em eventos.
 * 
 * Listeners com prioridade mais alta são executados antes.
 * 
 * @author Eddch
 */
public enum EventPriority {
    HIGHEST,
    HIGH,
    NORMAL,
    LOW,
    LOWEST
}