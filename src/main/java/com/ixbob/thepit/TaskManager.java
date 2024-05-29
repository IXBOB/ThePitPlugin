package com.ixbob.thepit;

import com.ixbob.thepit.task.RandomGoldIngotSpawnRunnable;
import com.ixbob.thepit.task.handler.PlacedBlockMultipleTaskHandler;
import com.ixbob.thepit.task.handler.RandomGoldIngotSpawnSingleTaskHandler;

public class TaskManager {
    private final PlacedBlockMultipleTaskHandler placedBlockTaskHandler = new PlacedBlockMultipleTaskHandler();
    private final RandomGoldIngotSpawnSingleTaskHandler randomGoldIngotSpawnSingleTaskHandler = new RandomGoldIngotSpawnSingleTaskHandler(new RandomGoldIngotSpawnRunnable(), 20, 100);

    public PlacedBlockMultipleTaskHandler getPlacedBlockTaskHandler() {
        return placedBlockTaskHandler;
    }

    public RandomGoldIngotSpawnSingleTaskHandler getRandomGoldIngotSpawnTaskHandler() {
        return randomGoldIngotSpawnSingleTaskHandler;
    }

}
