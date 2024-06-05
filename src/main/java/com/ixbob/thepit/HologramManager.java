package com.ixbob.thepit;

import eu.decentsoftware.holograms.api.holograms.Hologram;

import java.util.ArrayList;

public class HologramManager {
    private static HologramManager instance;
    private final ArrayList<Hologram> holograms = new ArrayList<>();
    private boolean isInitialized = false;

    private HologramManager() {
    }

    public static HologramManager getInstance() {
        if (instance == null) {
            instance = new HologramManager();
        }
        return instance;
    }

    public void add(Hologram hologram) {
        if (!isInitialized) {
            holograms.add(hologram);
            return;
        }
        throw new IllegalStateException("You have already initialized the hologram manager!");
    }

    public boolean isExist(Hologram hologram) {
        return holograms.contains(hologram);
    }

    public void remove(Hologram hologram) {
        if (!isInitialized) {
            throw new IllegalStateException("You haven't initialized the hologram manager!");
        }
        if (isExist(hologram)) {
            holograms.remove(hologram);
        }
        throw new IllegalStateException("the manager list not contains the hologram!");
    }

    public void setInitialized() {
        isInitialized = true;
    }

    public boolean isInitialized() {
        return isInitialized;
    }
}
