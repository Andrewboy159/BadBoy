package com.minestom.Spigot.Integrations;

import com.minestom.Discord.Utilities.MessageSender;
import com.minestom.Spigot.BadBoy;
import me.leoko.advancedban.bukkit.event.PunishmentEvent;
import me.leoko.advancedban.utils.Punishment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class AdvancedBan implements Listener {

    private MessageSender messageSender;

    public AdvancedBan(MessageSender messageSender){
        this.messageSender = messageSender;
    }

    @EventHandler
    public void onPunish(PunishmentEvent event) {
        Punishment punishment = event.getPunishment();

        messageSender.sendPunishmentMessage(punishment.getName(), punishment.getOperator(),
                punishment.getType().getName(), punishment.getReason());
    }

}
