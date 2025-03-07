package org.team3082.chicken_planner.State.Events;

import java.io.File;

public class ProjectLoadRequest implements Event {
    private final File file;

    public ProjectLoadRequest(File file) {
        this.file = file;
    }

    public File getFile() {
        return file;
    }
}
