package com.minestom.Discord.Utilities;

import com.minestom.Discord.BadBoyBot;
import com.minestom.Spigot.BadBoy;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.awt.*;

public class MessageSender {

    private BadBoy plugin;

    public MessageSender(BadBoy plugin) {
        this.plugin = plugin;
    }

    public void sendReportMessage(String reportedUser, String user, String reason, boolean server) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        Guild guild = BadBoyBot.api.getGuildById(plugin.getDiscordConfigString("reports.guildId"));
        MessageChannel channel = guild.getTextChannelById(plugin.getDiscordConfigString("reports.channelId"));

        embedBuilder.setColor(Color.GREEN);
        embedBuilder.addField(plugin.getLangString("d-reports_reported_user"), reportedUser, false);
        embedBuilder.addField(plugin.getLangString("d-reports_reported_by"), user, false);
        embedBuilder.addField(plugin.getLangString("d-reports_reason"), reason, false);
        if (server) embedBuilder.addField(plugin.getLangString("d-reports_server"), plugin.getDiscordConfigString("server"), false);

        channel.sendMessage(embedBuilder.build()).queue();
    }

    public void sendPurchaseMessage(String userName, String packageName, int price) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        Guild guild = BadBoyBot.api.getGuildById(plugin.getDiscordConfigString("donation.guildId"));
        MessageChannel channel = guild.getTextChannelById(plugin.getDiscordConfigString("donation.channelId"));

        embedBuilder.setColor(Color.getHSBColor(5, 193, 240));
        embedBuilder.addField(plugin.getLangString("d-donations_user"), userName, true);
        embedBuilder.addField(plugin.getLangString("d-donations_package"), packageName, true);
        embedBuilder.addField(plugin.getLangString("d-donations_price"), "$" + price, true);
        embedBuilder.setFooter(plugin.getLangString("d-donations_footer"), null);

        channel.sendMessage(embedBuilder.build()).queue();
    }

    public void sendPunishmentMessage(String userName, String operator, String type, String reason) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        Guild guild = BadBoyBot.api.getGuildById(plugin.getDiscordConfigString("punishments.guildId"));
        MessageChannel channel = guild.getTextChannelById(plugin.getDiscordConfigString("punishments.channelId"));

        embedBuilder.setColor(Color.getHSBColor(5, 193, 240));
        embedBuilder.addField(plugin.getLangString("d-punishments_user"), userName, true);
        embedBuilder.addField(plugin.getLangString("d-punishments_operator"), operator, true);
        embedBuilder.addField(plugin.getLangString("d-punishments_type"), type, true);
        embedBuilder.addField(plugin.getLangString("d-punishments_reason"), reason, false);
        embedBuilder.setFooter(plugin.getLangString("d-punishments_footer"), null);

        channel.sendMessage(embedBuilder.build()).queue();
    }

    public void sendVoteMessage(String userName, String service) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        Guild guild = BadBoyBot.api.getGuildById(plugin.getDiscordConfigString("vote.guildId"));
        MessageChannel channel = guild.getTextChannelById(plugin.getDiscordConfigString("vote.channelId"));

        embedBuilder.setColor(Color.getHSBColor(5, 193, 240));
        embedBuilder.setDescription(plugin.getLangString("d-on_vote").replace("{userName}", userName).replace("{serviceName}", service));

        channel.sendMessage(embedBuilder.build()).queue();
    }

    public void sendMessageToDiscord(String message){
        Guild guild = BadBoyBot.api.getGuildById(plugin.getDiscordConfigString("minecraft-chat.guildId"));
        MessageChannel channel = guild.getTextChannelById(plugin.getDiscordConfigString("minecraft-chat.channelId"));

        channel.sendMessage(message).complete();
    }
}
