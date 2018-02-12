package com.minestom.Discord.Commands;

import com.minestom.Discord.BadBoyBot;
import com.minestom.Spigot.BadBoy;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.exceptions.ErrorResponseException;
import net.dv8tion.jda.core.exceptions.PermissionException;

import java.awt.*;

public class Help {

    public static void sendHelpMessage(Member member, String[] args, MessageChannel channel, BadBoy plugin) {
        String prefix = BadBoyBot.prefix;
        EmbedBuilder normalUsers = new EmbedBuilder();
        EmbedBuilder staffUsers = new EmbedBuilder();
        EmbedBuilder customCommands = new EmbedBuilder();

        normalUsers.setColor(Color.ORANGE);
        staffUsers.setColor(Color.CYAN);
        customCommands.setColor(Color.RED);

        normalUsers.setDescription("Commands Available for Users");
        normalUsers.addField(prefix + "profile @user", "Shows User Profile.", false);
        normalUsers.addField(prefix + "info", "Shows the Bot's info.", false);
        normalUsers.addField(prefix + "help", "Get Help.", false);
        normalUsers.addField(prefix + "report [@user] [reason]", "Report a user that is breaking the rules", false);
        normalUsers.addField(prefix + "8ball [Question]", "Know things...", false);

        customCommands.setTitle("Other Commands");
        customCommands.setDescription(plugin.discordCustomCmd.toList().toString().replaceAll("[\\[\\]]", ""));

        if (member.hasPermission(Permission.valueOf(plugin.getDiscordConfigString("permissions.staff_help").toUpperCase()))) {
            staffUsers.setDescription("Commands Available for Staff Members");
            staffUsers.addField(prefix + "clear [number]", "Delete Messages", false);
            staffUsers.addField(prefix + "server <IP>", "Get an image with the server status", false);
            staffUsers.addField(prefix + "reload", "Reload the bot/plugin", false);
            staffUsers.addField(prefix + "broadcast", "Sends a message to the Minecraft server", false);
            staffUsers.addField(prefix + "getId", "Sends a message with the guild Id and channel Id", false);
        }

        try {
            if (args.length >= 2) {
                if (args[1].equalsIgnoreCase("staff")) {
                    if (member.hasPermission(Permission.valueOf(plugin.getDiscordConfigString("permissions.staff_help").toUpperCase())))
                        member.getUser().openPrivateChannel().complete().sendMessage(staffUsers.build()).complete();
                    return;
                }
                if (args[1].equalsIgnoreCase("other")) {
                    member.getUser().openPrivateChannel().complete().sendMessage(customCommands.build()).complete();
                    return;
                }
            }
            member.getUser().openPrivateChannel().complete().sendMessage(normalUsers.build()).complete();
            member.getUser().openPrivateChannel().complete().sendMessage(customCommands.build()).complete();
            if (member.hasPermission(Permission.valueOf(plugin.getDiscordConfigString("permissions.staff_help").toUpperCase())))
                member.getUser().openPrivateChannel().complete().sendMessage(staffUsers.build()).complete();

        } catch (ErrorResponseException | PermissionException e) {
            channel.sendMessage(normalUsers.setDescription("Your DM is closed please open it to get the message").build()).complete();
        }
    }
}
