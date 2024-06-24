package com.ixbob.thepit.gui;

import com.ixbob.thepit.Main;
import com.ixbob.thepit.enums.gui.GUIGridTypeEnum;
import com.ixbob.thepit.enums.gui.watchman.GUIWatchmanItemEnum;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.ClickType;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

public class GUIWatchman extends BasicGUIImpl{
    public GUIWatchman(Player player) {
        super(player, 27, 0, 0, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), new ArrayList<>());
    }

    @Override
    public void initContent() {
        int exitIndex = GUIWatchmanItemEnum.EXIT.getIndex();
        inventory.setItem(exitIndex, GUIWatchmanItemEnum.EXIT.getItemStack());
        leftButton.add(exitIndex);
    }

    @Override
    public void onClick(int index, ClickType clickType) {
        GUIGridTypeEnum type = getGridType(index, clickType);
        if (type == GUIGridTypeEnum.LEFT_BUTTON) {
            if (Objects.equals(inventory.getItem(index), GUIWatchmanItemEnum.EXIT.getItemStack())) {
                player.closeInventory();
                ByteArrayOutputStream stream = new ByteArrayOutputStream();
                DataOutputStream out = new DataOutputStream(stream);
                try {
                    out.writeUTF(player.getName());
                    player.sendPluginMessage(Main.getInstance(), Main.bungeecordChannelName, stream.toByteArray());
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
