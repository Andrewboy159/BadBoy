package com.minestom.Spigot.Integrations;

import com.minestom.DiscordBot.Utilities.MessageSender;
import com.minestom.Spigot.BadBoy;
import net.craftingstore.bukkit.events.DonationReceivedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CraftingStore implements Listener {

    private BadBoy plugin;

    public CraftingStore(BadBoy plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPurchase(DonationReceivedEvent event){
        String userName = event.getUsername();
        String packageName = event.getPackageName();
        int packagePrice = event.getPackagePrice();

        MessageSender.sendPurchaseMessage(userName, packageName, packagePrice, plugin);

    }

}
