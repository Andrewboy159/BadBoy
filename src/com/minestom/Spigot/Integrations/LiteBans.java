package com.minestom.Spigot.Integrations;

import com.minestom.Discord.Utilities.MessageSender;
import litebans.api.Entry;
import litebans.api.Events;
import org.bukkit.Bukkit;

import java.util.UUID;

public class LiteBans {

    public LiteBans(MessageSender messageSender) {
        Events.get().register(new Events.Listener() {
            @Override
            public void entryAdded(Entry entry) {
                if (entry.getUuid() == null) return;
                UUID uuid = UUID.fromString(entry.getUuid());
                messageSender.sendPunishmentMessage(Bukkit.getPlayer(uuid).getName(), entry.getExecutorName(),
                        entry.getType(), entry.getReason());
            }
        });
    }

}
