package com.ixbob.thepit.task.network;

import com.comphenix.protocol.PacketType;
import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import com.comphenix.protocol.events.ListenerPriority;
import com.comphenix.protocol.events.PacketAdapter;
import com.comphenix.protocol.events.PacketContainer;
import com.comphenix.protocol.events.PacketEvent;
import com.ixbob.thepit.Main;

public class SendScoreboardPacketAdapterRunnable implements Runnable{
    ProtocolManager manager = ProtocolLibrary.getProtocolManager();

    @Override
    public void run() {
        manager.addPacketListener(new PacketAdapter(Main.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Server.SCOREBOARD_SCORE) {
            @Override
            public void onPacketSending(PacketEvent event) {
                PacketContainer packet = event.getPacket();
                System.out.println(packet.getStructures().getValues());
            }
        });
    }
}
