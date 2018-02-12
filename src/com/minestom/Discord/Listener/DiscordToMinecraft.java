package com.minestom.Discord.Listener;

import com.minestom.Discord.BadBoyBot;
import com.minestom.Spigot.BadBoy;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;

public class DiscordToMinecraft extends ListenerAdapter {

    private BadBoy plugin;

    public DiscordToMinecraft(BadBoy plugin) {
        this.plugin = plugin;
    }

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if (event.getAuthor().isBot() || event.getMessage().getContentRaw().startsWith(BadBoyBot.prefix)) return;
        Guild guild = BadBoyBot.api.getGuildById(plugin.getDiscordConfigString("discord-minecraft-chat.guildId"));
        MessageChannel channel = guild.getTextChannelById(plugin.getDiscordConfigString("discord-minecraft-chat.channelId"));
        if (event.getChannel() != channel) return;

        String message = plugin.getConfigObjects("ChatFormat.DiscordMinecraft").toString()
                .replace("{userName}", event.getAuthor().getName())
                .replace("{message}", event.getMessage().getContentStripped());

        Bukkit.broadcastMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

}
