package com.minestom.Spigot.Managers;

import com.minestom.Spigot.BadBoy;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DiscordCustomCmd {

    public Map<Object, Object> configStrings;

    private File file;
    private FileConfiguration fileConfiguration;


    public DiscordCustomCmd(BadBoy plugin) {
        configStrings = new HashMap<>();
        file = new File(plugin.getDataFolder(), "discordCommands.yml");
        fileConfiguration = new YamlConfiguration();
        fileConfiguration.options().header(" This are custom commands for the bot.\n" +
                " First is going the command name without the prefix then a message.\n " +
                " \n  store: 'The store is store.my-server.net {user}.'\n \n" +
                " Remember the commands are case sensitive.\n" +
                " Available place holders are: {user}, more coming soon...");
        fileConfiguration.addDefault("store", "The store is store.my-server.net {user}.");
        fileConfiguration.options().copyDefaults(true);
        saveConfig();
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

    private void saveConfig(){
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

}
