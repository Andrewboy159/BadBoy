package com.minestom.Spigot.Managers;

import com.minestom.Spigot.BadBoy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class LanguageManager {

    private Map<String, String> language;

    private File file;
    private FileConfiguration fileConfiguration;

    private BadBoy plugin;

    public LanguageManager(BadBoy plugin) {
        this.plugin = plugin;
        language = new HashMap<>();
        file = new File(plugin.getDataFolder(), "language.yml");
        fileConfiguration = new YamlConfiguration();
    }

    public void setupLanguage() {

        if (!file.exists()) plugin.saveResource("language.yml", false);

        try {
            fileConfiguration.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadLanguageMap();
    }

    private void loadLanguageMap() {
        if (!language.isEmpty()) language.clear();
        language.put("reports_prefix", fileConfiguration.getString("reports_prefix"));
        language.put("playerNotOnline", fileConfiguration.getString("playerNotOnline"));
        language.put("reports_wrongUsage", fileConfiguration.getString("reports_wrongUsage"));
        language.put("reload_plugin", fileConfiguration.getString("reload_plugin"));
    }

    public void reloadFile() {
        try {
            loadLanguageMap();
            fileConfiguration.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMessage(String path) {
        return language.get(path);
    }
}
