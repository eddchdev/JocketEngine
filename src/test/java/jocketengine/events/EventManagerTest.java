package jocketengine.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;

class EventManagerTest {

    static class SampleEvent extends Event {
    }

    @BeforeEach
    void clearListeners() {
        EventManager.clearAllListeners();
    }

    @Test
    void listenersRunFromHighestToLowestPriority() {
        List<String> calls = new ArrayList<>();
        EventManager.registerListener(SampleEvent.class, EventPriority.LOW, e -> calls.add("low"));
        EventManager.registerListener(SampleEvent.class, EventPriority.HIGH, e -> calls.add("high"));
        EventManager.registerListener(SampleEvent.class, EventPriority.NORMAL, e -> calls.add("normal"));

        EventManager.fireEvent(new SampleEvent());

        assertEquals(List.of("high", "normal", "low"), calls);
    }

    @Test
    void cancellingStopsPropagation() {
        List<String> calls = new ArrayList<>();
        EventManager.registerListener(SampleEvent.class, EventPriority.HIGH, e -> {
            calls.add("high");
            e.setCancelled(true);
        });
        EventManager.registerListener(SampleEvent.class, EventPriority.LOW, e -> calls.add("low"));

        EventManager.fireEvent(new SampleEvent());

        assertEquals(List.of("high"), calls);
    }

    @Test
    void firingWithoutListenersIsSafe() {
        assertDoesNotThrow(() -> EventManager.fireEvent(new SampleEvent()));
    }
}
