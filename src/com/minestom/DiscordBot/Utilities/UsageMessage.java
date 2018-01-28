package com.minestom.DiscordBot.Utilities;

import com.minestom.DiscordBot.BadBoyBot;
import com.minestom.Spigot.BadBoy;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import org.bukkit.plugin.Plugin;

import java.awt.*;

public class UsageMessage {

    public static void sendUsageMessage(MessageChannel channel, MessageType messageType) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.RED);

        switch (messageType) {
            case FORTUNE:
                embedBuilder.setDescription("Wrong Usage. The correct way to use this command is:\n" +
                        "**" + BadBoyBot.prefix + "8ball [Question with at least 4 chars]**");
                channel.sendMessage(embedBuilder.build()).queue();
                break;
            case CLEAR_CHAT:
                embedBuilder.setDescription("Wrong Usage. The correct way to use this command is:\n" +
                        "**" + BadBoyBot.prefix + "clear [amount of messages]**");
                channel.sendMessage(embedBuilder.build()).queue();
                break;
            case USER_NOT_FOUND:
                embedBuilder.setDescription("User could not be found. Try using *@userName*.");
                channel.sendMessage(embedBuilder.build()).queue();
                break;
        }
    }
    public static void sendReportMessage(String reportedUser, String user, String reason, BadBoy plugin) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        Guild guild = BadBoyBot.api.getGuildById(plugin.getConfigString("guildId"));
        MessageChannel channel = guild.getTextChannelById(plugin.getConfigString("channelId"));

        embedBuilder.setColor(Color.RED);
        embedBuilder.addField("Reported User", reportedUser, false);
        embedBuilder.addField("Reported By", user, false);
        embedBuilder.addField("Reason", reason, false);
        embedBuilder.addField("Server Issued", plugin.getConfigString("server"), false);

        channel.sendMessage(embedBuilder.build()).queue();
    }
}
