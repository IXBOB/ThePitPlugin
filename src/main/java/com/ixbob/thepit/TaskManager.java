package com.ixbob.thepit;

import com.ixbob.thepit.task.handler.PlacedBlockTaskHandler;

public class TaskManager {
    private final PlacedBlockTaskHandler placedBlockTaskHandler = new PlacedBlockTaskHandler();

    public PlacedBlockTaskHandler getPlacedBlockTaskHandler() {
        return placedBlockTaskHandler;
    }
}
