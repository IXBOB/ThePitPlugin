package com.ixbob.thepit.task.onstart.delayed;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.Main;
import com.ixbob.thepit.config.PitConfig;
import com.ixbob.thepit.enums.HologramEnum;
import com.ixbob.thepit.task.ReloadPitLobbyRankingsListRunnable;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import org.bukkit.Bukkit;

import java.util.logging.Level;

public class ResetHologramsRunnable implements Runnable{

    @Override
    public void run() {
        //删除所有hologram
        for (HologramEnum hdEnum : HologramEnum.values()) {
            DHAPI.removeHologram(hdEnum.getName());
        }

        //添加hologram
        Hologram hd_tip_info = DHAPI.createHologram(HologramEnum.TIP_INFO.getName(), PitConfig.getInstance().getDhInfoTipLoc());
        for (String content : LangLoader.getStringList("hd_tip_info_contents")) {
            DHAPI.addHologramLine(hd_tip_info, content);
        }
        Hologram hd_tip_jump = DHAPI.createHologram(HologramEnum.TIP_JUMP.getName(), PitConfig.getInstance().getDhJumpTipLoc());
        for (String content : LangLoader.getStringList("hd_tip_jump_contents")) {
            DHAPI.addHologramLine(hd_tip_jump, content);
        }
        Hologram hd_lobby_rankings = DHAPI.createHologram(HologramEnum.LOBBY_RANKINGS.getName(), PitConfig.getInstance().getDhLobbyRankingsLoc());
        DHAPI.addHologramLine(hd_lobby_rankings, LangLoader.getString("hd_rankings_list_title"));
        for (int i = 1; i <= 10; i++) {
            DHAPI.addHologramLine(hd_lobby_rankings, String.format(LangLoader.getString("hd_rankings_list_init"), i, "§7-"));
        }

        Bukkit.getScheduler().runTask(Main.getInstance(), new ReloadPitLobbyRankingsListRunnable());

        Main.getInstance().getLogger().log(Level.INFO, LangLoader.getString("system_load_hds_finish_info"));
    }
}
