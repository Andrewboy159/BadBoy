package com.minestom.Discord.Commands;

import com.minestom.Spigot.BadBoy;
import net.dv8tion.jda.core.entities.MessageChannel;

import java.util.Map;

public class CustomCommands {

    public static void sendCustomCommandMessages(MessageChannel channel, String cmd, String user, BadBoy plugin) {

        for (Map.Entry entry : plugin.discordCustomCmd.configStrings.entrySet()) {
            String command = entry.getKey().toString();
            String message = entry.getValue().toString().replace("{user}", user);
            if (cmd.equals(command)) channel.sendMessage(message).queue();

        }

    }

}
