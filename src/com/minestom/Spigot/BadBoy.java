package com.minestom.Spigot;

import com.minestom.DiscordBot.BadBoyBot;
import com.minestom.Spigot.Commands.Report;
import com.minestom.Spigot.Integrations.AdvancedBan;
import com.minestom.Spigot.Integrations.CraftingStore;
import com.minestom.Spigot.Managers.DiscordConfig;
import com.minestom.Spigot.Managers.LanguageManager;
import com.minestom.Spigot.Managers.MessageManager;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

public class BadBoy extends JavaPlugin {

    private DiscordConfig discordConfig;
    private LanguageManager languageManager;
    private MessageManager messageManager;

    @Override
    public void onEnable() {

        languageManager = new LanguageManager(this);
        discordConfig = new DiscordConfig(this);
        messageManager = new MessageManager(this, languageManager);

        languageManager.setupLanguage();
        discordConfig.setupFileConfig();
        saveDefaultConfig();
        setupCommands();
        setupListeners();

        try {
            BadBoyBot.main(discordConfig.getString("token"));
            getLogger().info("The bot is online and its ready to use.");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("badboy")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                languageManager.reloadFile();
                discordConfig.reloadFile();
                messageManager.sendMessage(sender, languageManager.getMessage("reload_plugin"), PrefixType.NONE);
            }
        }
        return true;
    }

    private void setupCommands() {
        getCommand("report").setExecutor(new Report(this, messageManager, languageManager));
    }

    private void setupListeners() {
        PluginManager pluginManager = Bukkit.getPluginManager();
        if (getConfig().getBoolean("Integrations.CraftingStore"))
            pluginManager.registerEvents(new CraftingStore(this), this);
        if (getConfig().getBoolean("Integrations.AdvancedBans"))
            pluginManager.registerEvents(new AdvancedBan(this), this);

    }

    public String getDiscordConfigString(String string) {
        return discordConfig.getString(string);
    }
}
