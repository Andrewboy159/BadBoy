package com.minestom.Discord.Commands;

import com.minestom.Spigot.BadBoy;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class BroadCast {

    public static void sendMessage(String[] args, BadBoy plugin, MessageChannel channel) {

        StringBuilder message = new StringBuilder();
        EmbedBuilder embedBuilder = new EmbedBuilder();
        String prefix = plugin.getLangString("mc-broadcast_prefix");

        for (int i = 1; i < args.length; i++) {
            message.append(args[i]).append(" ");
        }

        Bukkit.getOnlinePlayers().forEach(player ->
                player.sendMessage(ChatColor.translateAlternateColorCodes('&',
                        prefix + message.toString().trim())));

        plugin.getLogger().info(prefix + message.toString().trim());

        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setDescription("The message has been broadcast to all players");

        channel.sendMessage(embedBuilder.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
    }

}
