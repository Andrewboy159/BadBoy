package com.minestom.Spigot.Managers;

import com.minestom.Spigot.BadBoy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LanguageManager {

    private Map<Object, Object> language;

    private File file;
    private FileConfiguration fileConfiguration;

    public LanguageManager(BadBoy plugin) {
        language = new HashMap<>();
        file = new File(plugin.getDataFolder(), "language.yml");
        fileConfiguration = new YamlConfiguration();
        if (!file.exists()) plugin.saveResource("language.yml", false);
        reloadFile();
    }

    private void loadLanguageMap() {
        if (!language.isEmpty()) language.clear();
        fileConfiguration.getKeys(false).forEach(section -> language.put(section, fileConfiguration.getString(section)));
    }

    public void reloadFile() {
        try {
            fileConfiguration.load(file);
            loadLanguageMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getMessage(String path) {
        return (String) language.get(path);
    }

    public List<String> getStringList(String path) {
        return (List<String>) language.get(path);
    }
}
