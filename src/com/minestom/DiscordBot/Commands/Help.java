package com.minestom.DiscordBot.Commands;

import com.minestom.DiscordBot.BadBoyBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.impl.UserImpl;

import java.awt.*;

public class Help {

    public static void sendHelpMessage(Member member, MessageChannel channel) {
        String prefix = BadBoyBot.prefix;
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(Color.RED);

        try {
            if (!member.getUser().hasPrivateChannel()) {
                member.getUser().openPrivateChannel().complete();
            }
        } catch (Exception e) {
            channel.sendMessage(embedBuilder.setDescription("You DM is closed please open it to get the message").build()).queue();
        }

        embedBuilder.addField(prefix + "profile @user", "Shows User Profile.", true);
        embedBuilder.addField(prefix + "help", "Get Help.", true);
        embedBuilder.addField(prefix + "8ball [Question]", "Know things...", true);
        embedBuilder.addField(prefix + "clear [number]", "Delete Messages", true);

        ((UserImpl) member.getUser()).getPrivateChannel().sendMessage(embedBuilder.build()).queue();
    }

}
