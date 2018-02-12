package com.minestom.Discord.Commands;

import com.minestom.Discord.BadBoyBot;
import com.minestom.Discord.Utilities.Cooldown;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.MessageChannel;
import org.apache.commons.lang.time.DurationFormatUtils;
import org.bukkit.Bukkit;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Info extends Cooldown {

    public static void sendStatsMessage(MessageChannel channel, String owner, int users, String region) {

        EmbedBuilder embedBuilder = new EmbedBuilder();
        long uptime = System.currentTimeMillis() - BadBoyBot.uptime;
        JDA bot = BadBoyBot.getApi();

        if (!onCooldown("info")) {
            startCooldown("info", 30);


            embedBuilder.setColor(Color.GREEN);
            embedBuilder.setFooter("Bot Creator: By_Jack", null);
            embedBuilder.addField("Guild Owner", owner, true);
            embedBuilder.addField("Guild Members", users + "", true);
            embedBuilder.addField("Guild Region", region, true);
            embedBuilder.addField("Players in Server", Bukkit.getOnlinePlayers().size() + "", true);
            embedBuilder.addField("Bot Ping", bot.getPing() + "", true);
            embedBuilder.addField("Bot Uptime", DurationFormatUtils.formatDurationWords(uptime,
                    true, true), true);

            channel.sendMessage(embedBuilder.build()).queue();

        } else {
            embedBuilder.setColor(Color.BLUE);
            embedBuilder.setDescription("You can't use this command right now. Please wait "
                    + getSecondsLeft("info") + " seconds...");
            channel.sendMessage(embedBuilder.build()).complete().delete().queueAfter(3, TimeUnit.SECONDS);
        }
    }

}
