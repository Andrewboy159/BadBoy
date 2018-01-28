package com.minestom.DiscordBot.Commands;

import com.minestom.DiscordBot.BadBoyBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.impl.UserImpl;

import java.awt.*;

public class Help {

    public static void sendHelpMessage(Member member) {
        String prefix = BadBoyBot.prefix;
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.addField(prefix + "profile @user", "Shows User Profile.", true);
        embedBuilder.addField(prefix + "help", "Get Help.", true);
        embedBuilder.addField(prefix + "8ball [Question]", "Know things...", true);
        embedBuilder.addField(prefix + "clear [number]","Delete Messages", true);
        embedBuilder.setColor(Color.RED);

        if (!member.getUser().hasPrivateChannel()) member.getUser().openPrivateChannel().complete();

        ((UserImpl) member.getUser()).getPrivateChannel().sendMessage(embedBuilder.build()).queue();
    }

}
