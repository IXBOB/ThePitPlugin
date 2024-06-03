package com.ixbob.thepit;

import com.mongodb.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;

import java.net.UnknownHostException;
import java.util.UUID;
import java.util.logging.Level;

public class MongoDB {
    private DBCollection collection;
    private static DB mcserverdb;
    private static MongoClient client;
    private static MongoDB instance;

    private MongoDB() {
    }

    public static MongoDB getInstance() {
        if (instance == null) {
            instance = new MongoDB();
            instance.connect("127.0.0.1", 27017, Main.getInstance());
            instance.setCollection("ThePit_IXBOB");
        }
        return instance;
    }

    public void setCollection(String collectionName) {
        collection = mcserverdb.getCollection(collectionName);
    }
    public void connect(String ip, int port, Plugin plugin) {
        try {
            client = new MongoClient(ip, port);
            plugin.getLogger().log(Level.INFO, "database connected!");
            mcserverdb = client.getDB("mcserver");
        } catch (UnknownHostException e) {
            plugin.getLogger().log(Level.SEVERE, "Could not connect to database!", e);
        }
    }
    public DBCollection getCollection() {
        return collection;
    }
    public long getCollectionSize() {
        return collection.getCount();
    }
    public String getCollectionName() {
        return collection.getName();
    }
    public void insert(DBObject object) {
        collection.insert(object);
    }

    public void updateSys(DBObject object) {
        BasicDBObject query = new BasicDBObject("id", 0);
        collection.update(query, object);
    }

    public void updateDataByUUID(DBObject object, UUID uuid) {
        BasicDBObject query = new BasicDBObject("UUID", uuid.toString());
        collection.update(query, object);
    }
//    public void insertTest() {
//        DBObject obj = new BasicDBObject("test_key", "123456");
//        obj.put("test_key2", "555555");
//        collection.insert(obj);
//    }

    public DBObject findByUUID (UUID uuid) {
        DBObject r = new BasicDBObject("UUID", uuid.toString());
        DBObject found = collection.findOne(r);
        if (found == null) {
            Bukkit.getLogger().log(Level.SEVERE, "found nothing. Error uuid parameter.");
            new NullPointerException().printStackTrace();
        }
        return found;
    }

    public DBObject findByID (int id) {
        DBObject r = new BasicDBObject("id", id);
        DBObject found = collection.findOne(r);
        if (found == null) {
            Bukkit.getLogger().log(Level.SEVERE, "found nothing. Error id parameter.");
            new NullPointerException().printStackTrace();
        }
        return found;
    }

    public boolean isFindByUUIDExist (UUID uuid) {
        DBObject r = new BasicDBObject("UUID", uuid.toString());
        DBObject found = collection.findOne(r);
        return found != null;
    }

    public boolean isFindByIDExist (int id) {
        DBObject r = new BasicDBObject("id", id);
        DBObject found = collection.findOne(r);
        return found != null;
    }
}
