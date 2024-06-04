package com.ixbob.thepit.util;

import com.ixbob.thepit.service.MongoDBService;
import org.bukkit.Bukkit;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.ServicesManager;

public class ServiceUtils {

    static ServicesManager servicesManager = Bukkit.getServer().getServicesManager();

    public static MongoDBService getMongoDBService() {
        RegisteredServiceProvider<MongoDBService> rsp = servicesManager.getRegistration(MongoDBService.class);
        return rsp.getProvider();
    }
}
