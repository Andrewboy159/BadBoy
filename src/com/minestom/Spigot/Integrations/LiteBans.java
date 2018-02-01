package com.minestom.Spigot.Integrations;

import com.minestom.DiscordBot.Utilities.MessageSender;
import com.minestom.Spigot.BadBoy;
import litebans.api.*;
import org.bukkit.Bukkit;

import java.util.UUID;

public class LiteBans {

    public LiteBans(BadBoy plugin) {
        Events.get().register(new Events.Listener() {
            @Override
            public void entryAdded(Entry entry) {
                if (entry.getUuid() == null) return;
                UUID uuid = UUID.fromString(entry.getUuid());
                MessageSender.sendPunishmentMessage(Bukkit.getOfflinePlayer(uuid).getName(), entry.getExecutorName(),
                        entry.getType(), entry.getReason(), plugin);
            }
        });
    }

}
