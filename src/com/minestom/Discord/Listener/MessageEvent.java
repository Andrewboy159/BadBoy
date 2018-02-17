package com.minestom.Discord.Listener;

import com.minestom.Discord.BadBoyBot;
import com.minestom.Discord.Commands.*;
import com.minestom.Discord.Utilities.MessageSender;
import com.minestom.Discord.Utilities.MessageType;
import com.minestom.Discord.Data.ShopData;
import com.minestom.Discord.Utilities.UsageMessage;
import com.minestom.Spigot.BadBoy;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Guild;
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
    private BadBoyBot badBoyBot;
    private Report report;
    private Shop shop;

    public MessageEvent(BadBoy badBoy, BadBoyBot badBoyBot, MessageSender messageSender, Shop shop){
        this.badBoy = badBoy;
        this.badBoyBot = badBoyBot;
        report = new Report(messageSender);
        this.shop = shop;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        if (!event.getMessage().getContentRaw().startsWith(BadBoyBot.prefix)) return;
        Message message = event.getMessage();
        Member member = event.getMember();
        MessageChannel channel = event.getChannel();
        Guild guild = message.getGuild();
        String[] args = event.getMessage().getContentRaw().toLowerCase().split(" ");
        String cmd = args[0].substring(BadBoyBot.prefix.length());

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
            return;
        }

        if (cmd.equals("help")) {
            message.delete().queue();
            Help.sendHelpMessage(member, args, channel, badBoy);
            return;
        }

        if (cmd.equals("clear")) {
            if (member.hasPermission(Permission.MESSAGE_MANAGE))
                if (args.length == 2)
                    if (StringUtils.isNumeric(args[1]))
                        ClearChat.clearMessages(channel, Integer.parseInt(args[1]));
                    else UsageMessage.sendUsageMessage(channel, MessageType.CLEAR_CHAT);
                else UsageMessage.sendUsageMessage(channel, MessageType.CLEAR_CHAT);
            else message.delete().queue();
            return;
        }

        if (cmd.equals("8ball")) {
            if (args.length >= 4) FortuneBall.sendFortuneMessage(channel);
            else UsageMessage.sendUsageMessage(channel, MessageType.FORTUNE);
            return;
        }

        if (cmd.equals("server")) {
            message.delete().queue();
            Server.sendServerBanner(channel, args);
            return;
        }

        if (cmd.equals("shop")) {
            message.delete().queue();
            badBoyBot.getDataMap().put(member.getUser(), new ShopData());
            shop.sendMainShopDm(member.getUser());
            return;
        }

        if (cmd.equals("report")) {
            Member reportedUser = message.getMentionedMembers().get(0);
            report.sendReport(channel, args, reportedUser, member);
            return;
        }

        if (cmd.equals("reload")) {
            message.delete().queue();
            if (member.hasPermission(Permission.ADMINISTRATOR))
                Reload.reloadPlugin(badBoy, channel);
            return;
        }

        if (cmd.equals("broadcast") || cmd.equals("bc")) {
            message.delete().queue();
            if (member.hasPermission(Permission.ADMINISTRATOR))
                if (args.length < 2) {
                    UsageMessage.sendUsageMessage(channel, MessageType.BROADCAST);
                    return;
                }
            BroadCast.sendMessage(args, badBoy, channel);
            return;
        }

        if (cmd.equals("info")) {
            message.delete().queue();
            Info.sendStatsMessage(channel, guild.getOwner().getUser().getName(), guild.getMembers().size(), guild.getRegionRaw());
            return;
        }

        if (cmd.equals("getid")) {
            message.delete().queue();
            if (!member.isOwner()) return;
            EmbedBuilder embedBuilder = new EmbedBuilder();

            embedBuilder.setDescription("Guild ID: " + message.getGuild().getId() +
                    "\nChannel ID: " + message.getChannel().getId());
            embedBuilder.setColor(Color.RED);

            channel.sendMessage(embedBuilder.build()).complete().delete().queueAfter(30, TimeUnit.SECONDS);
            return;
        }
        CustomCommands.sendCustomCommandMessages(channel, cmd, member.getAsMention(), badBoy);
    }
}
