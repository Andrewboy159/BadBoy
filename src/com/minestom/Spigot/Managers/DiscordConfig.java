package com.minestom.Spigot.Managers;

import com.minestom.Spigot.BadBoy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class DiscordConfig {

    private Map<String, String> configStrings;

    private File file;
    private FileConfiguration fileConfiguration;

    private BadBoy plugin;

    public DiscordConfig(BadBoy plugin) {
        this.plugin = plugin;
        configStrings = new HashMap<>();
        file = new File(plugin.getDataFolder(), "discordConfig.yml");
        fileConfiguration = new YamlConfiguration();
    }

    public void setupFileConfig() {

        if (!file.exists()) plugin.saveResource("discordConfig.yml", false);

        try {
            fileConfiguration.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        loadConfigMap();
    }

    private void loadConfigMap() {
        if (!configStrings.isEmpty()) configStrings.clear();
        configStrings.put("token", fileConfiguration.getString("token"));
        configStrings.put("server", fileConfiguration.getString("server"));
        configStrings.put("reports.guildId", fileConfiguration.getString("reports.guildId"));
        configStrings.put("reports.channelId", fileConfiguration.getString("reports.channelId"));
        configStrings.put("donation.guildId", fileConfiguration.getString("donation.guildId"));
        configStrings.put("donation.channelId", fileConfiguration.getString("donation.channelId"));
    }

    public void reloadFile() {
        try {
            loadConfigMap();
            fileConfiguration.load(file);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public String getString(String path) {
        return configStrings.get(path);
    }

}
