package com.ixbob.thepit.task.network;

//R.I.P.
//public class RegisterPlayerNamePacketAdapterRunnable implements Runnable{
//    ProtocolManager manager = ProtocolLibrary.getProtocolManager();
//
//    @Override
//    public void run() {
//        manager.addPacketListener(new PacketAdapter(Main.getInstance(), ListenerPriority.NORMAL, PacketType.Play.Server.PLAYER_INFO) {
//            @Override
//            public void onPacketSending(PacketEvent event) {
//                Player player = event.getPlayer();
//                PacketContainer packet = event.getPacket();
//
//                if (packet.getPlayerInfoAction().read(0) == EnumWrappers.PlayerInfoAction.ADD_PLAYER) {
//                    List<PlayerInfoData> dataList = packet.getPlayerInfoDataLists().read(0);
//
//                    for (int i = 0; i < dataList.size(); i++) {
//                        PlayerInfoData data = dataList.get(i);
//
//                        if (data == null) continue;
//
//                        UUID uniqueId = data.getProfile().getUUID();
//
//                       for (Player onlinePl : Bukkit.getOnlinePlayers()) {
//                           ServerPlayer onlinePlHandle = ((CraftPlayer) onlinePl).getHandle();
//                           if (onlinePlHandle.getUUID() == uniqueId) {
//                               dataList.set(i, new PlayerInfoData(
//                                       new WrappedGameProfile(uniqueId, PlayerUtils.getPitDisplayName(onlinePl)),
//                                       data.getLatency(),
//                                       data.getGameMode(),
//                                       WrappedChatComponent.fromLegacyText(PlayerUtils.getPitDisplayName(onlinePl))
//                               ));
//                           }
//                       }
//                    }
//                    packet.getPlayerInfoDataLists().write(0, dataList);
//                }
//            }
//        });
//    }
//}
