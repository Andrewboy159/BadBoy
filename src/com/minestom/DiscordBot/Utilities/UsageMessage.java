package com.minestom.DiscordBot.Utilities;

import com.minestom.DiscordBot.BadBoyBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class UsageMessage {

    public static void sendUsageMessage(MessageChannel channel, MessageType messageType) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setColor(Color.RED);

        switch (messageType) {
            case FORTUNE:
                embedBuilder.setDescription("Wrong Usage. The correct way to use this command is:\n" +
                        "**" + BadBoyBot.prefix + "8ball [Question with at least 4 chars]**");
                channel.sendMessage(embedBuilder.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
                break;
            case CLEAR_CHAT:
                embedBuilder.setDescription("Wrong Usage. The correct way to use this command is:\n" +
                        "**" + BadBoyBot.prefix + "clear [amount of messages]**");
                channel.sendMessage(embedBuilder.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
                break;
            case USER_NOT_FOUND:
                embedBuilder.setDescription("User could not be found. Try using *@userName*.");
                channel.sendMessage(embedBuilder.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
                break;
            case REPORT:
                embedBuilder.setDescription("Wrong Usage. The correct way to use this command is:\n" +
                        "**" + BadBoyBot.prefix + "report [@user] [reason]**");
                channel.sendMessage(embedBuilder.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
                break;
            case BROADCAST:
                embedBuilder.setDescription("Wrong Usage. The correct way to use this command is:\n" +
                        "**" + BadBoyBot.prefix + "broadcast [message]**");
                channel.sendMessage(embedBuilder.build()).complete().delete().queueAfter(5, TimeUnit.SECONDS);
                break;
        }
    }
}
