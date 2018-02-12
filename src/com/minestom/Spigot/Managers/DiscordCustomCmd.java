package com.minestom.Spigot.Managers;

import com.minestom.Spigot.BadBoy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.*;

public class DiscordCustomCmd {

    public Map<Object, Object> configStrings;

    private File file;
    private FileConfiguration fileConfiguration;


    public DiscordCustomCmd(BadBoy plugin) {
        configStrings = new HashMap<>();
        file = new File(plugin.getDataFolder(), "discordCommands.yml");
        fileConfiguration = new YamlConfiguration();
        if (!file.exists()) plugin.saveResource("discordCommands.yml", false);
        reloadFile();
    }

    private void loadConfigMap() {
        if (!configStrings.isEmpty()) configStrings.clear();
        fileConfiguration.getKeys(false).forEach(section -> configStrings.put(section, fileConfiguration.get(section)));
    }

    public void reloadFile() {
        try {
            fileConfiguration.load(file);
            loadConfigMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveConfig() {
        try {
            fileConfiguration.save(file);
            loadConfigMap();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getString(String path) {
        return (String) configStrings.get(path);
    }

    public List<String> getStringList(String path) {
        return (List<String>) configStrings.get(path);
    }

    public Set<String> toList() {
        Set<String> stringSet = new HashSet<>();
        configStrings.forEach((o, o2) -> stringSet.add("-" + o));
        return stringSet;
    }

}
