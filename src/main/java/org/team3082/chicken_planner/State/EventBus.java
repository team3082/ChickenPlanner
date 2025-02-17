package org.team3082.chicken_planner.State;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import org.team3082.chicken_planner.State.Events.Event;

public class EventBus {
    private static final List<Consumer<Event>> listeners = new ArrayList<>();

    public static void register(Consumer<Event> listener) {
        listeners.add(listener);
    }

    public static void unregister(Consumer<Event> listener) {
        listeners.remove(listener);
    }
    
    public static void fireEvent(Event event) {
        for (Consumer<Event> listener : new ArrayList<>(listeners)) {
            listener.accept(event);
        }
    }
}
