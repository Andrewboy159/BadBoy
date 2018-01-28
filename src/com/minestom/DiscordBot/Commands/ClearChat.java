package com.minestom.DiscordBot.Commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.MessageHistory;

import java.awt.*;
import java.util.List;

public class ClearChat {

    public static void clearMessages(MessageChannel channel, int amount) {

        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setDescription("You've successfully cleared " + amount + " messages.");
        embedBuilder.setColor(Color.RED);

        MessageHistory history = new MessageHistory(channel);
        List<Message> messages = history.retrievePast(amount + 1).complete();

        messages.forEach(message -> channel.deleteMessageById(message.getId()).queue());

        channel.sendMessage(embedBuilder.build()).queue();
    }

}
