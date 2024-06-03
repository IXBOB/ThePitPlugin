package com.ixbob.thepit;

import com.ixbob.thepit.task.RandomGoldIngotSpawnRunnable;
import com.ixbob.thepit.task.handler.PlacedBlockMultipleTaskHandler;
import com.ixbob.thepit.task.handler.RandomGoldIngotSpawnSingleTaskHandler;

public class TaskManager {
    private static TaskManager instance;
    private final PlacedBlockMultipleTaskHandler placedBlockTaskHandler = new PlacedBlockMultipleTaskHandler();
    private final RandomGoldIngotSpawnSingleTaskHandler randomGoldIngotSpawnSingleTaskHandler = new RandomGoldIngotSpawnSingleTaskHandler(new RandomGoldIngotSpawnRunnable(), 20, 100);

    private TaskManager() {
    }

    public static TaskManager getInstance() {
        if (instance == null) {
            instance = new TaskManager();
        }
        return instance;
    }

    public PlacedBlockMultipleTaskHandler getPlacedBlockTaskHandler() {
        return placedBlockTaskHandler;
    }

    public RandomGoldIngotSpawnSingleTaskHandler getRandomGoldIngotSpawnTaskHandler() {
        return randomGoldIngotSpawnSingleTaskHandler;
    }

}
