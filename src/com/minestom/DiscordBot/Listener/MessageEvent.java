package com.minestom.DiscordBot.Listener;

import com.minestom.DiscordBot.BadBoyBot;
import com.minestom.DiscordBot.Commands.ClearChat;
import com.minestom.DiscordBot.Commands.FortuneBall;
import com.minestom.DiscordBot.Commands.Help;
import com.minestom.DiscordBot.Commands.Profile;
import com.minestom.DiscordBot.Utilities.MessageType;
import com.minestom.DiscordBot.Utilities.UsageMessage;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class MessageEvent extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (!event.getMessage().getContentRaw().startsWith(BadBoyBot.prefix)) return;
        Message message = event.getMessage();
        Member member = event.getMember();
        MessageChannel channel = event.getChannel();
        String[] args = event.getMessage().getContentRaw().toLowerCase().split(" ");
        String cmd = args[0].substring(1);

        if (cmd.equals("profile")) {
            if (args.length == 2) {
                if (message.getMentionedMembers().size() == 0) {
                    UsageMessage.sendUsageMessage(channel, MessageType.USER_NOT_FOUND);
                    return;
                }
                Member mentioned = message.getMentionedMembers().get(0);
                if (args[1].equalsIgnoreCase(mentioned.getAsMention()))
                    Profile.sendProfileMessage(channel, message.getMentionedMembers().get(0));
                return;
            }
            Profile.sendProfileMessage(channel, member);
        }

        if (cmd.equals("help")) {
            Help.sendHelpMessage(member);
        }

        if (cmd.equals("clear")) {
            if (args.length == 2) ClearChat.clearMessages(channel, Integer.parseInt(args[1]));
            else UsageMessage.sendUsageMessage(channel, MessageType.CLEAR_CHAT);
        }

        if (cmd.equals("8ball")) {
            if (args.length >= 4) FortuneBall.sendFortuneMessage(channel);
            else UsageMessage.sendUsageMessage(channel, MessageType.FORTUNE);
        }

        if (cmd.equals("mcsetup")) {
            if (!member.isOwner()) return;
            EmbedBuilder embedBuilder = new EmbedBuilder();

            embedBuilder.setDescription("Guild ID: " + message.getGuild().getId() +
                    "\nChannel ID: " + message.getChannel().getId());
            embedBuilder.setColor(Color.RED);

            Message setup = channel.sendMessage(embedBuilder.build()).complete();
            setup.delete().queueAfter(30, TimeUnit.SECONDS);
        }
    }
}
