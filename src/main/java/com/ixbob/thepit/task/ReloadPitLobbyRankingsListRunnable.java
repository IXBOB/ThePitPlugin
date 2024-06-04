package com.ixbob.thepit.task;

import com.ixbob.thepit.service.MongoDBService;
import com.ixbob.thepit.util.ServiceUtils;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.DBObject;

import java.util.List;

public class ReloadPitLobbyRankingsListRunnable implements Runnable{

    @Override
    public void run() {
        MongoDBService mongoDB = ServiceUtils.getMongoDBService();
        DBCursor cursor = mongoDB.getCollectionFindings().limit(10).sort(new BasicDBObject("xp_amount",-1));
        List<DBObject> list = cursor.toArray();
        for (DBObject dbObject : list) {
            System.out.println(dbObject.toString());
        }
        cursor.close();
    }
}
