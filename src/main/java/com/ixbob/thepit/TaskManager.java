package com.ixbob.thepit;

import com.ixbob.thepit.task.handler.ObsidianTaskHandler;

public class TaskManager {
    private final ObsidianTaskHandler obsidianTaskHandler = new ObsidianTaskHandler();

    public ObsidianTaskHandler getObsidianTaskHandler() {
        return obsidianTaskHandler;
    }
}
