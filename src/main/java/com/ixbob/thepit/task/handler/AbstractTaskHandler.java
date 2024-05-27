package com.ixbob.thepit.task.handler;

import java.util.HashMap;

public abstract class AbstractTaskHandler {
    private final HashMap<Integer, Runnable> tasks = new HashMap<>();

    public void add(int taskId, Runnable runnable) {
        tasks.put(taskId, runnable);
    }

    public void remove(int taskId) {
        tasks.remove(taskId);
    }

    public void removeAll() {
        HashMap<Integer, Runnable> processTasks = new HashMap<>(tasks);
        for (Runnable runnable : processTasks.values()) {
            runnable.run();
        }
        tasks.clear();
    }
}
