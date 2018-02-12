package com.minestom.Spigot;

import com.minestom.Discord.BadBoyBot;
import com.minestom.Discord.Utilities.MessageSender;
import com.minestom.Spigot.Commands.Report;
import com.minestom.Spigot.Integrations.*;
import com.minestom.Spigot.Managers.DiscordConfig;
import com.minestom.Spigot.Managers.DiscordCustomCmd;
import com.minestom.Spigot.Managers.LanguageManager;
import com.minestom.Spigot.Managers.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class BadBoy extends JavaPlugin {

    public boolean log = false;
    public DiscordCustomCmd discordCustomCmd;
    private DiscordConfig discordConfig;
    private LanguageManager languageManager;
    private MessageManager messageManager;
    private Map<String, Object> config;
    private MessageSender messageSender;

    @Override
    public void onEnable() {

        languageManager = new LanguageManager(this);
        discordConfig = new DiscordConfig(this);
        discordCustomCmd = new DiscordCustomCmd(this);
        messageManager = new MessageManager(this, languageManager);
        messageSender = new MessageSender(this);
        config = new HashMap<>();

        saveDefaultConfig();
        setupCommands();
        setupListeners();
        setupConfigMap();

        log = getConfig().getBoolean("LogDiscordActions");

        try {
            BadBoyBot.main(discordConfig.getString("token"), this, messageSender);
            getLogger().info("The bot is online and its ready to use.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("badboy")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                reloadPlugin();
                messageManager.sendMessage(sender, languageManager.getMessage("mc-reload_plugin"), PrefixType.NONE);
            }
        }
        return true;
    }

    public void reloadPlugin() {
        reloadConfiguration();
        languageManager.reloadFile();
        discordConfig.reloadFile();
        discordCustomCmd.reloadFile();
    }

    private void setupCommands() {
        getCommand("report").setExecutor(new Report(messageManager, languageManager, messageSender));
    }

    private void setupConfigMap() {
        FileConfiguration configuration = getConfig();
        config.put("Integrations.Chat", configuration.getBoolean("Integrations.Chat"));
        config.put("ChatFormat.MinecraftDiscord", configuration.getString("ChatFormat.MinecraftDiscord"));
        config.put("ChatFormat.DiscordMinecraft", configuration.getString("ChatFormat.DiscordMinecraft"));
    }

    private void reloadConfiguration() {
        config.clear();
        reloadConfig();
        setupConfigMap();
    }

    private void setupListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        if (getConfig().getBoolean("Integrations.Chat")) {
            pluginManager.registerEvents(new MinecraftToDiscord(this, messageSender), this);
            getLogger().info("Chat integration enabled.");
        }
        if (getConfig().getBoolean("Integrations.CraftingStore") && pluginManager.isPluginEnabled("CraftingStore")) {
            pluginManager.registerEvents(new CraftingStore(messageSender), this);
            getLogger().info("CraftingStore integration enabled.");
        }
        if (getConfig().getBoolean("Integrations.AdvancedBan") && pluginManager.isPluginEnabled("AdvancedBan")) {
            pluginManager.registerEvents(new AdvancedBan(messageSender), this);
            getLogger().info("AdvancedBan integration enabled.");
        }
        if (getConfig().getBoolean("Integrations.LiteBans") && pluginManager.isPluginEnabled("LiteBans")) {
            new LiteBans(this);
            getLogger().info("LiteBans integration enabled.");
        }
        if (getConfig().getBoolean("Integrations.Votifier") && pluginManager.isPluginEnabled("Votifier")) {
            pluginManager.registerEvents(new Votifier(messageSender), this);
            getLogger().info("Votifier integration enabled.");
        }

    }

    public String getDiscordConfigString(String string) {
        return discordConfig.getString(string);
    }

    public String getLangString(String string) {
        return languageManager.getMessage(string);
    }

    public Object getConfigObjects(String string) {
        return config.get(string);
    }
}
