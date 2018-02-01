package com.minestom.Spigot.Managers;

import com.minestom.Spigot.BadBoy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscordConfig {

    private Map<Object, Object> configStrings;

    private File file;
    private FileConfiguration fileConfiguration;


    public DiscordConfig(BadBoy plugin) {
        configStrings = new HashMap<>();
        file = new File(plugin.getDataFolder(), "discordConfig.yml");
        fileConfiguration = new YamlConfiguration();
        if (!file.exists()) plugin.saveResource("discordConfig.yml", false);
        reloadFile();
    }

    private void loadConfigMap() {
        if (!configStrings.isEmpty()) configStrings.clear();
        configStrings.put("token", fileConfiguration.getString("token"));
        configStrings.put("server", fileConfiguration.getString("server"));
        configStrings.put("reports.guildId", fileConfiguration.getString("reports.guildId"));
        configStrings.put("reports.channelId", fileConfiguration.getString("reports.channelId"));
        configStrings.put("donation.guildId", fileConfiguration.getString("donation.guildId"));
        configStrings.put("donation.channelId", fileConfiguration.getString("donation.channelId"));
        configStrings.put("vote.guildId", fileConfiguration.getString("vote.guildId"));
        configStrings.put("vote.channelId", fileConfiguration.getString("vote.channelId"));
    }

    public void reloadFile() {
        try {
            fileConfiguration.load(file);
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

    public boolean getBoolean(String path) {
        return (boolean) configStrings.get(path);
    }

    public double getDouble(String path) {
        return (double) configStrings.get(path);
    }

}
