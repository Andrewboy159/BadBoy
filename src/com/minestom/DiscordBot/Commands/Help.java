package com.minestom.DiscordBot.Commands;

import com.minestom.DiscordBot.BadBoyBot;
import com.minestom.Spigot.BadBoy;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.impl.UserImpl;

import java.awt.*;

public class Help {

    public static void sendHelpMessage(Member member, MessageChannel channel, BadBoy plugin) {
        String prefix = BadBoyBot.prefix;
        EmbedBuilder normalUsers = new EmbedBuilder();
        EmbedBuilder staffUsers = new EmbedBuilder();

        normalUsers.setColor(Color.ORANGE);
        staffUsers.setColor(Color.CYAN);

        if (!member.getUser().hasPrivateChannel()) {
            try {
                member.getUser().openPrivateChannel().complete();
            } catch (Exception e) {
                channel.sendMessage(normalUsers.setDescription("You DM is closed please open it to get the message").build()).queue();
            }
        }

        normalUsers.setDescription("Commands Available for Users");
        normalUsers.addField(prefix + "profile @user", "Shows User Profile.", false);
        normalUsers.addField(prefix + "help", "Get Help.", false);
        normalUsers.addField(prefix + "report [@user] [reason]", "Report a user that is breaking the rules", false);
        normalUsers.addField(prefix + "8ball [Question]", "Know things...", false);

        if (member.hasPermission(Permission.valueOf(plugin.getDiscordConfigString("permissions.staff_help").toUpperCase()))) {
            staffUsers.setDescription("Commands Available for Staff Members");
            staffUsers.addField(prefix + "clear [number]", "Delete Messages", false);
            staffUsers.addField(prefix + "server <IP>", "Get an image with the server status", false);
            staffUsers.addField(prefix + "reload", "Reload the bot/plugin", false);
            staffUsers.addField(prefix + "broadcast", "Sends a message to the Minecraft server", false);
            staffUsers.addField(prefix + "info", "Sends a message with the guildId and channel Id", false);
        }

        ((UserImpl) member.getUser()).getPrivateChannel().sendMessage(normalUsers.build()).queue();
        if (member.hasPermission(Permission.valueOf(plugin.getDiscordConfigString("permissions.staff_help").toUpperCase())))
            ((UserImpl) member.getUser()).getPrivateChannel().sendMessage(staffUsers.build()).queue();
    }

}
