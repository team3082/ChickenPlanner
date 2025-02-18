package org.team3082.chicken_planner.State;

import java.util.ArrayList;
import java.util.function.Consumer;

import org.team3082.chicken_planner.Constants;
import org.team3082.chicken_planner.State.Events.Event;

public class EventBus {
    private static final ArrayList<Consumer<Event>> listeners = new ArrayList<>();
    private static final ArrayList<Event> eventLog = new ArrayList<>();

    public static void register(Consumer<Event> listener) {
        listeners.add(listener);
    }

    public static void unregister(Consumer<Event> listener) {
        listeners.remove(listener);
    }
    
    public static void fireEvent(Event event) {
        if(Constants.Debug.LOG_EVENTS) eventLog.add(event);
        System.out.println(event.getClass().getSimpleName());
        for (Consumer<Event> listener : new ArrayList<>(listeners)) {
            listener.accept(event);
        }
    }
}
