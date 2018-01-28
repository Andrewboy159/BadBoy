package com.minestom.Spigot;

import com.minestom.DiscordBot.BadBoyBot;
import com.minestom.DiscordBot.Utilities.UsageMessage;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.HashMap;
import java.util.Map;

public class BadBoy extends JavaPlugin {

    private Map<String, String> config = new HashMap<>();

    public String getConfigString(String string) {
        return config.get(string);
    }

    @Override
    public void onEnable() {
        saveDefaultConfig();
        try {
            BadBoyBot.main(getConfig().getString("discord.token"));
        } catch (Exception e) {
            e.printStackTrace();
        }
        config.put("server", getConfig().getString("discord.server"));
        config.put("guildId", getConfig().getString("discord.guildId"));
        config.put("channelId", getConfig().getString("discord.channelId"));
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getName().equalsIgnoreCase("report")) {
            if (args.length <= 1) return true;

            StringBuilder reason = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                reason.append(args[i]).append(" ");
            }
            UsageMessage.sendReportMessage(args[0], sender.getName(), reason.toString().trim(), this);
        }
        if (command.getName().equalsIgnoreCase("badboy")) {
            if (args.length == 1 && args[0].equalsIgnoreCase("debug")) {
                sender.sendMessage(config.toString());
            }
            if (args.length == 1 && args[0].equalsIgnoreCase("reload")) {
                reloadConfig();
                config.clear();
                config.put("server", getConfig().getString("discord.server"));
                config.put("guildId", getConfig().getString("discord.guildId"));
                config.put("channelId", getConfig().getString("discord.channelId"));
            }
        }
        return true;
    }


}
