package com.minestom.Discord.Commands;

import com.minestom.Discord.BadBoyBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.awt.*;
import java.util.Random;

public class FortuneBall {

    public static void sendFortuneMessage(MessageChannel channel) {

        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setDescription(BadBoyBot.fortune.get(new Random().nextInt(BadBoyBot.fortune.size())));
        embedBuilder.setColor(Color.RED);

        channel.sendMessage(embedBuilder.build()).queue();
    }

}
