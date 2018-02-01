package com.minestom.DiscordBot.Utilities;

import com.minestom.DiscordBot.BadBoyBot;
import com.minestom.Spigot.BadBoy;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.awt.*;

public class MessageSender {

    public static void sendReportMessage(String reportedUser, String user, String reason, BadBoy plugin, boolean server) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        Guild guild = BadBoyBot.api.getGuildById(plugin.getDiscordConfigString("reports.guildId"));
        MessageChannel channel = guild.getTextChannelById(plugin.getDiscordConfigString("reports.channelId"));

        embedBuilder.setColor(Color.GREEN);
        embedBuilder.addField("Reported User", reportedUser, false);
        embedBuilder.addField("Reported By", user, false);
        embedBuilder.addField("Reason", reason, false);
        if (server) embedBuilder.addField("Server Issued", plugin.getDiscordConfigString("server"), false);

        channel.sendMessage(embedBuilder.build()).queue();
    }

    public static void sendPurchaseMessage(String userName, String packageName, int price, BadBoy plugin) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        Guild guild = BadBoyBot.api.getGuildById(plugin.getDiscordConfigString("donation.guildId"));
        MessageChannel channel = guild.getTextChannelById(plugin.getDiscordConfigString("donation.channelId"));

        embedBuilder.setColor(Color.getHSBColor(5, 193, 240));
        embedBuilder.setDescription("New donation made by **" + userName + "**. Thank You.");
        embedBuilder.addField("Package", packageName, true);
        embedBuilder.addField("Price", "$" + price, true);
        embedBuilder.setFooter("Visit our store: store.my-server.net", null);

        channel.sendMessage(embedBuilder.build()).queue();
    }

    public static void sendPunishmentMessage(String userName, String operator, String type, String reason, BadBoy plugin) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        Guild guild = BadBoyBot.api.getGuildById(plugin.getDiscordConfigString("donation.guildId"));
        MessageChannel channel = guild.getTextChannelById(plugin.getDiscordConfigString("donation.channelId"));

        embedBuilder.setColor(Color.getHSBColor(5, 193, 240));
        embedBuilder.setDescription(userName + " has been punished.");
        embedBuilder.addField("Staff Member", operator, true);
        embedBuilder.addField("Type", type, true);
        embedBuilder.addField("Reason", reason, false);
        embedBuilder.setFooter("Remember to follow the rules.", null);

        channel.sendMessage(embedBuilder.build()).queue();
    }

    public static void sendVoteMessage(String userName, String service, BadBoy plugin) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        Guild guild = BadBoyBot.api.getGuildById(plugin.getDiscordConfigString("vote.guildId"));
        MessageChannel channel = guild.getTextChannelById(plugin.getDiscordConfigString("vote.channelId"));

        embedBuilder.setColor(Color.getHSBColor(5, 193, 240));
        embedBuilder.setDescription(plugin.getLangString("d-on_vote").replace("{userName}", userName).replace("{serviceName}", service));

        channel.sendMessage(embedBuilder.build()).queue();
    }

}
