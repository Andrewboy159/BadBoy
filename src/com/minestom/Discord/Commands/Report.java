package com.minestom.Discord.Commands;

import com.minestom.Discord.Utilities.MessageSender;
import com.minestom.Discord.Utilities.MessageType;
import com.minestom.Discord.Utilities.UsageMessage;
import com.minestom.Spigot.BadBoy;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;

public class Report {

    private MessageSender messageSender;
    public Report(MessageSender messageSender){
        this.messageSender = messageSender;
    }

    public void sendReport(MessageChannel channel, String[] args, Member reportedMember, Member member) {
        if (args.length == 2) UsageMessage.sendUsageMessage(channel, MessageType.REPORT);

        StringBuilder reason = new StringBuilder();
        for (int i = 2; i < args.length; i++) {
            reason.append(args[i]).append(" ");
        }

        messageSender.sendReportMessage(reportedMember.getAsMention(), member.getAsMention(), reason.toString().trim(), false);
    }
}
