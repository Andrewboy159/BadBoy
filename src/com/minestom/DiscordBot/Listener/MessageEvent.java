package com.minestom.DiscordBot.Listener;

import com.minestom.DiscordBot.BadBoyBot;
import com.minestom.DiscordBot.Commands.*;
import com.minestom.DiscordBot.Utilities.MessageType;
import com.minestom.DiscordBot.Utilities.UsageMessage;
import com.minestom.Spigot.BadBoy;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class MessageEvent extends ListenerAdapter {

    private BadBoy badBoy;

    public MessageEvent(BadBoy badBoy) {
        this.badBoy = badBoy;
    }

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
            Help.sendHelpMessage(member, channel);
        }

        if (cmd.equals("clear")) {
            if (member.hasPermission(Permission.MESSAGE_MANAGE))
                if (args.length == 2)
                    if (StringUtils.isNumeric(args[1]))
                        ClearChat.clearMessages(channel, Integer.parseInt(args[1]));
                    else UsageMessage.sendUsageMessage(channel, MessageType.CLEAR_CHAT);
                else UsageMessage.sendUsageMessage(channel, MessageType.CLEAR_CHAT);
            else message.delete().queue();
        }

        if (cmd.equals("8ball")) {
            if (args.length >= 4) FortuneBall.sendFortuneMessage(channel);
            else UsageMessage.sendUsageMessage(channel, MessageType.FORTUNE);
        }

        if (cmd.equals("server")) {
            Server.sendServerBanner(channel, args);
        }

        if (cmd.equals("report")) {
            Member reportedUser = message.getMentionedMembers().get(0);
            Report.sendReport(channel, args, reportedUser, member, badBoy);
        }

        if (cmd.equals("reload")) {
            message.delete().queue();
            if (member.hasPermission(Permission.ADMINISTRATOR))
                Reload.reloadPlugin(badBoy, channel);
        }

        if (cmd.equals("broadcast") || cmd.equals("bc")) {
            message.delete().queue();
            if (member.hasPermission(Permission.ADMINISTRATOR))
                if (args.length < 2) {
                    UsageMessage.sendUsageMessage(channel, MessageType.BROADCAST);
                    return;
                }
            BroadCast.sendMessage(args, badBoy, channel);
        }

        if (cmd.equals("stats")) {
            message.delete().queue();
            if (args.length < 2) {
                UsageMessage.sendUsageMessage(channel, MessageType.BROADCAST);
                return;
            }
            Stats.sendStatsMessage(channel, args, badBoy);
        }

        if (cmd.equals("info")) {
            message.delete().queue();
            if (!member.isOwner()) return;
            EmbedBuilder embedBuilder = new EmbedBuilder();

            embedBuilder.setDescription("Guild ID: " + message.getGuild().getId() +
                    "\nChannel ID: " + message.getChannel().getId());
            embedBuilder.setColor(Color.RED);

            channel.sendMessage(embedBuilder.build()).complete().delete().queueAfter(30, TimeUnit.SECONDS);
        }
    }
}
