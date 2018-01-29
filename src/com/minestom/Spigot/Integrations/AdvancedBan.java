package com.minestom.Spigot.Integrations;

import com.minestom.DiscordBot.Utilities.MessageSender;
import com.minestom.Spigot.BadBoy;
import me.leoko.advancedban.bukkit.event.PunishmentEvent;
import me.leoko.advancedban.utils.Punishment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AdvancedBan implements Listener {

    private BadBoy plugin;

    public AdvancedBan(BadBoy plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPunish(PunishmentEvent event) {
        Punishment punishment = event.getPunishment();

        MessageSender.sendPunishmentMessage(punishment.getName(), punishment.getOperator(),
                punishment.getType().getName(), punishment.getReason(), plugin);
    }

}
