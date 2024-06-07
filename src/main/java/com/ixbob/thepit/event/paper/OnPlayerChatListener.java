package com.ixbob.thepit.event.paper;

import com.ixbob.thepit.util.PlayerUtils;
import io.papermc.paper.chat.ChatRenderer;
import io.papermc.paper.event.player.AsyncChatEvent;
import net.kyori.adventure.audience.Audience;
import net.kyori.adventure.text.Component;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.jetbrains.annotations.NotNull;

public class OnPlayerChatListener implements Listener, ChatRenderer {
    @EventHandler
    public void onPlayerChat(AsyncChatEvent event) {
        event.renderer(this);
    }

    @Override
    public @NotNull Component render(@NotNull Player player, @NotNull Component sourceDisplayName, @NotNull Component message, @NotNull Audience audience) {
        return Component
                .text(PlayerUtils.getPitDisplayName(player))
                .append(Component.text(": "))
                .append(message);
    }
}
