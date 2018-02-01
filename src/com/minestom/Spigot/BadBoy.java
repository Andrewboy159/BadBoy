package com.minestom.Spigot;

import com.minestom.DiscordBot.BadBoyBot;
import com.minestom.Spigot.Commands.Report;
import com.minestom.Spigot.Integrations.AdvancedBan;
import com.minestom.Spigot.Integrations.CraftingStore;
import com.minestom.Spigot.Integrations.LiteBans;
import com.minestom.Spigot.Integrations.Votifier;
import com.minestom.Spigot.Managers.DiscordConfig;
import com.minestom.Spigot.Managers.LanguageManager;
import com.minestom.Spigot.Managers.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BadBoy extends JavaPlugin {

    public boolean log = false;
    private DiscordConfig discordConfig;
    private LanguageManager languageManager;
    private MessageManager messageManager;

    @Override
    public void onEnable() {

        languageManager = new LanguageManager(this);
        discordConfig = new DiscordConfig(this);
        messageManager = new MessageManager(this, languageManager);

        saveDefaultConfig();
        setupCommands();
        setupListeners();

        log = getConfig().getBoolean("LogDiscordActions");

        try {
            BadBoyBot.main(discordConfig.getString("token"), this);
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

    public void reloadPlugin(){
        reloadConfig();
        languageManager.reloadFile();
        discordConfig.reloadFile();
    }

    private void setupCommands() {
        getCommand("report").setExecutor(new Report(this, messageManager, languageManager));
    }

    private void setupListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        if (getConfig().getBoolean("Integrations.CraftingStore") && pluginManager.isPluginEnabled("CraftingStore")) {
            pluginManager.registerEvents(new CraftingStore(this), this);
            getLogger().info("CraftingStore integration enabled.");
        }
        if (getConfig().getBoolean("Integrations.AdvancedBan") && pluginManager.isPluginEnabled("AdvancedBan")) {
            pluginManager.registerEvents(new AdvancedBan(this), this);
            getLogger().info("AdvancedBan integration enabled.");
        }
        if (getConfig().getBoolean("Integrations.LiteBans") && pluginManager.isPluginEnabled("LiteBans")) {
            new LiteBans(this);
            getLogger().info("LiteBans integration enabled.");
        }
        if (getConfig().getBoolean("Integrations.Votifier") && pluginManager.isPluginEnabled("Votifier")) {
            pluginManager.registerEvents(new Votifier(this), this);
            getLogger().info("Votifier integration enabled.");
        }

    }

    public String getDiscordConfigString(String string) {
        return discordConfig.getString(string);
    }

    public String getLangString(String string) {
        return languageManager.getMessage(string);
    }
}
