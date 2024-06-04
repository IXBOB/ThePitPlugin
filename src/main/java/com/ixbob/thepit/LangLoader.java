package com.ixbob.thepit;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class LangLoader {

    public static HashMap<String, Object> translationMap = new HashMap<>();
    public static Plugin plugin = Main.getInstance();

    public static void init () {
        plugin.saveResource("languages/zh_CN.yml", true);
        File languageFile = new File(plugin.getDataFolder(), "languages/zh_CN.yml");
        FileConfiguration translations = YamlConfiguration.loadConfiguration(languageFile.getAbsoluteFile());
        for (String translation : translations.getKeys(false)) {
            translationMap.put(translation, translations.get(translation));
        }
    }

    public static String getString(String key) {
        return (String) translationMap.get(key);
    }

    @SuppressWarnings("unchecked")
    public static ArrayList<String> getStringList(String key) {
        if (!(translationMap.get(key) instanceof ArrayList<?>)) {
            throw new IllegalCallerException();
        }
        return (ArrayList<String>) translationMap.get(key);
    }
}
