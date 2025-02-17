package org.team3082.chicken_planner.State.Events;

import org.team3082.chicken_planner.UI.Main.Page;

public class PageSwitchEvent {
    private final Page targetPage;

    public PageSwitchEvent(Page targetPage) {
        this.targetPage = targetPage;
    }

    public Page getTargetPage() {
        return targetPage;
    }
}

