package com.minestom.Spigot.Integrations;

import com.minestom.Discord.Utilities.MessageSender;
import com.minestom.Spigot.BadBoy;
import net.craftingstore.bukkit.events.DonationReceivedEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class CraftingStore implements Listener {

    private MessageSender messageSender;

    public CraftingStore(MessageSender messageSender){
        this.messageSender = messageSender;
    }

    @EventHandler
    public void onPurchase(DonationReceivedEvent event){
        String userName = event.getUsername();
        String packageName = event.getPackageName();
        int packagePrice = event.getPackagePrice();

        messageSender.sendPurchaseMessage(userName, packageName, packagePrice);

    }

}
