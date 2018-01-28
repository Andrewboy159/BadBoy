package com.minestom.DiscordBot.Commands;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.awt.*;

public class Profile {

    public static void sendProfileMessage(MessageChannel channel, Member member) {

        EmbedBuilder embedBuilder = new EmbedBuilder();
        String game = member.getGame() != null ? member.getGame().getName() : "Nothing";

        if (member.getUser().isBot()){
            embedBuilder.setDescription("Why do you wanna see the profile if this bot...?");
            embedBuilder.setColor(Color.RED);
            channel.sendMessage(embedBuilder.build()).queue();
            return;
        }

        embedBuilder.addField("Real UserName", member.getUser().getName(), true);
        embedBuilder.addField("Playing", game, true);
        embedBuilder.addField("Highest Role", member.getRoles().get(0).getName(), true);
        embedBuilder.addField("Join Date", member.getJoinDate().toLocalDate().toString(), true);
        embedBuilder.addField("Current Level", "In development", true);
        embedBuilder.setColor(member.getRoles().get(0).getColor());
        embedBuilder.setThumbnail(member.getUser().getAvatarUrl());

        channel.sendMessage(embedBuilder.build()).queue();
    }

}
