package com.minestom.Spigot.Integrations;

import com.minestom.Discord.Utilities.MessageSender;
import com.minestom.Spigot.BadBoy;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class MinecraftToDiscord implements Listener {

    private BadBoy plugin;
    private MessageSender messageSender;

    public MinecraftToDiscord(BadBoy plugin, MessageSender messageSender) {
        this.plugin = plugin;
        this.messageSender = messageSender;
    }

    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        if (event.isCancelled() || !(boolean) plugin.getConfigObjects("Integrations.Chat")) return;

        String message = plugin.getConfigObjects("ChatFormat.MinecraftDiscord").toString()
                .replace("{playerName}", event.getPlayer().getName())
                .replace("{message}", event.getMessage());
        messageSender.sendMessageToDiscord(message);
    }

}
