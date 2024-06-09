package com.ixbob.thepit.task;

import com.ixbob.thepit.LangLoader;
import com.ixbob.thepit.Mth;
import com.ixbob.thepit.enums.HologramEnum;
import com.ixbob.thepit.service.MongoDBService;
import com.ixbob.thepit.util.ServiceUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;
import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;

import java.util.List;

public class ReloadPitLobbyRankingsListRunnable implements Runnable{

    @Override
    public void run() {
        MongoDBService mongoDB = ServiceUtils.getMongoDBService();
        DBCursor cursor = mongoDB.getCollectionFindings().limit(10).sort(new BasicDBObject("xp_amount",-1));
        List<DBObject> list = cursor.toArray();
        cursor.close();
        Hologram hologram = DHAPI.getHologram(HologramEnum.LOBBY_RANKINGS.getName());

        assert hologram != null;
        DHAPI.setHologramLine(hologram.getPage(0).getLine(0), LangLoader.getString("hd_rankings_list_title"));
        for (int i = 1; i <= list.size(); i++) {
            DHAPI.setHologramLine(hologram.getPage(0).getLine(i), String.format(LangLoader.getString("hd_rankings_list_set"), i, list.get(i-1).get("player_name"), Mth.formatDecimalWithFloor((Double) list.get(i-1).get("xp_amount"), 2)));
        }
    }
}
