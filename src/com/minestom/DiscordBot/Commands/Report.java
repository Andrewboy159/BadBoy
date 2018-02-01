package com.minestom.DiscordBot.Commands;

import com.minestom.DiscordBot.Utilities.MessageSender;
import com.minestom.DiscordBot.Utilities.MessageType;
import com.minestom.DiscordBot.Utilities.UsageMessage;
import com.minestom.Spigot.BadBoy;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

public class Report {

    public static void sendReport(MessageChannel channel, String[] args, Member reportedMember, Member member, BadBoy plugin) {
        if (args.length == 2) UsageMessage.sendUsageMessage(channel, MessageType.REPORT);

        StringBuilder reason = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }

        MessageSender.sendReportMessage(reportedMember.getAsMention(), member.getAsMention(), reason.toString().trim(), plugin, false);
    }
}
