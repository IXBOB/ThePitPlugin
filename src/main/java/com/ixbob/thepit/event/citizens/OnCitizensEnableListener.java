package com.ixbob.thepit.event.citizens;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.task.onstart.InitSpawnCitizensNPCRunnable;
import net.citizensnpcs.api.event.CitizensEnableEvent;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class OnCitizensEnableListener implements Listener {
    @EventHandler
    public void onCitizensEnable (CitizensEnableEvent event) {
        Bukkit.getScheduler().runTask(Main.getInstance(), new InitSpawnCitizensNPCRunnable());
    }
}
