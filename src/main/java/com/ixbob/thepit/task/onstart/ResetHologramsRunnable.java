package com.ixbob.thepit.task.onstart;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.Main;
import com.ixbob.thepit.config.PitConfig;
import com.ixbob.thepit.enums.HologramEnum;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;

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

        Main.getInstance().getLogger().log(Level.INFO, LangLoader.getString("system_load_hds_finish_info"));
    }
}
