package com.minestom.Spigot.Managers;

import com.minestom.Spigot.BadBoy;
import com.minestom.Spigot.PrefixType;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class MessageManager {

    private BadBoy plugin;
    private LanguageManager languageManager;

    public MessageManager(BadBoy plugin, LanguageManager languageManager) {
        this.plugin = plugin;
        this.languageManager = languageManager;
    }

    public String color(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    private String getPrefix(PrefixType prefixType) {

        if (prefixType == PrefixType.REPORTS) {
            return languageManager.getMessage("reports_prefix");
        } else if (prefixType == PrefixType.NONE) return "";

        return null;
    }

    public void sendMessage(CommandSender sender, String string, PrefixType prefixType) {
        sender.sendMessage(color(getPrefix(prefixType) + string));
    }

    public void sendMessage(Player player, String string, PrefixType prefixType) {
        player.sendMessage(color(getPrefix(prefixType) + string));
    }

}
