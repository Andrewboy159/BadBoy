package com.minestom.DiscordBot.Commands;

import com.minestom.DiscordBot.Utilities.Cooldown;
import com.minestom.Spigot.BadBoy;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import org.bukkit.Bukkit;
import org.bukkit.Statistic;
import org.bukkit.entity.Player;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Stats extends Cooldown {

    public static void sendStatsMessage(MessageChannel channel, String[] args, BadBoy plugin) {

        Player player = Bukkit.getPlayer(args[1]);
        EmbedBuilder embedBuilder = new EmbedBuilder();

        if (!onCooldown("stats")) {
            startCooldown("stats", 5);

            embedBuilder.setColor(Color.GREEN);
            embedBuilder.addField("Deaths", player.getStatistic(Statistic.DEATHS) + "", true);
            embedBuilder.addField("Player Kills", player.getStatistic(Statistic.PLAYER_KILLS) + "", true);
            embedBuilder.addField("Mobs Kills", player.getStatistic(Statistic.MOB_KILLS) + "", true);
            if (plugin.getServer().getPluginManager().isPluginEnabled("Vault")) return;

            channel.sendMessage(embedBuilder.build()).queue();

        } else {
            embedBuilder.setColor(Color.BLUE);
            embedBuilder.setDescription("You can't use this command right now. Please wait "
                    + getSecondsLeft("server") + " seconds...");
            channel.sendMessage(embedBuilder.build()).complete().delete().queueAfter(3, TimeUnit.SECONDS);
        }
    }
}
