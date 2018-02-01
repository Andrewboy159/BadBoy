package com.minestom.DiscordBot.Commands;

import com.minestom.Spigot.BadBoy;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Reload {

    public static void reloadPlugin(BadBoy plugin, MessageChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.GREEN);
        embedBuilder.setDescription(plugin.getLangString("d-reload_plugin"));

        channel.sendMessage(embedBuilder.build()).complete().delete().queueAfter(10, TimeUnit.SECONDS);
        plugin.getLogger().info("Discord: " + plugin.getLangString("d-reload_plugin"));
        plugin.reloadPlugin();
    }

}
