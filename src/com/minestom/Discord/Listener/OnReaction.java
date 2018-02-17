package com.minestom.Discord.Listener;

import com.minestom.Discord.BadBoyBot;
import com.minestom.Discord.Commands.Shop;
import com.minestom.Discord.Data.ShopData;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.PrivateChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OnReaction extends ListenerAdapter {

    private Map<User, ShopData> dataMap;
    private Shop shop;

    public OnReaction(BadBoyBot badBoyBot, Shop shop) {
        this.dataMap = badBoyBot.getDataMap();
        this.shop = shop;
    }

    @Override
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        if (event.getAuthor().isBot()) return;
        PrivateChannel channel = event.getChannel();
        User user = event.getAuthor();
        ShopData shopData = dataMap.get(user);
        String message = event.getMessage().getContentRaw();

        if (shopData == null) return;

        if (shopData.isShopping()) {
            if (message.equalsIgnoreCase("coins")) {
                shopData.setShopping(false);
                shopData.setBuyingCoinsBooster(true);
                shop.sendCoinsShopDm(user);
            } else if (message.equalsIgnoreCase("exp")) {
                shopData.setShopping(false);
                shopData.setBuyingExpBooster(true);
                shop.sendExpShopDm(user);
            }
        } else if (shopData.isBuyingCoinsBooster())
            buyingCoinsBooster(message, shopData, user, channel);
        else if (shopData.isBuyingExpBooster())
            buyingExpBooster(message, shopData, user, channel);
        else if (shopData.isConfirming()) confirmPurchase(message, shopData, channel);
    }

    private void confirmPurchase(String message, ShopData shopData, PrivateChannel channel) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        if (message.equalsIgnoreCase("yes")) {
            if (shopData.getTotalPrice() > shopData.getUserCoins()) {
                embedBuilder.setDescription("You don't have enough coins to buy this");
                embedBuilder.addField("Coins Needed", shopData.getTotalPrice() + "", true);
                embedBuilder.addField("Your Coins", shopData.getUserCoins() + "", true);
                embedBuilder.setColor(Color.RED);
                channel.sendMessage(embedBuilder.build()).complete();
                return;
            }
            shopData.setConfirming(false);
            embedBuilder.setDescription("Transaction Completed");
            embedBuilder.setColor(Color.GREEN);
            channel.sendMessage(embedBuilder.build()).complete();
        } else if (message.equalsIgnoreCase("no")) {
            embedBuilder.setDescription("Canceling transaction. See you soon.");
            embedBuilder.setColor(Color.RED);
            channel.sendMessage(embedBuilder.build()).complete();
        }
    }

    private void buyingCoinsBooster(String message, ShopData shopData, User user, PrivateChannel channel) {
        if (message.equalsIgnoreCase("back")) {
            if (shopData.getCoinsBoosterPercent() <= 0.0 && shopData.getCoinsBoosterTime() > 1) {
                channel.sendMessage("You need to complete the order first.").complete();
                return;
            }
            shopData.setShopping(true);
            shopData.setBuyingCoinsBooster(false);
            shop.sendMainShopDm(user);
            return;
        } else if (message.equalsIgnoreCase("finish")) {
            if (shopData.getCoinsBoosterPercent() <= 0.0 && shopData.getCoinsBoosterTime() > 1) {
                channel.sendMessage("You need to complete the order first.").complete();
                return;
            }
            shopData.setConfirming(true);
            shopData.setBuyingCoinsBooster(false);
            shop.sendConfirmationDm(user);
            return;
        } else if (message.equalsIgnoreCase("percentage")) {
            shopData.setChoosingPercent(true);
            channel.sendMessage("Enter the percentage. (Number from 1 to 7)")
                    .complete();
            return;
        } else if (message.equalsIgnoreCase("time")) {
            shopData.setChoosingTime(true);
            channel.sendMessage("Enter the time in minutes. ")
                    .complete();
            return;
        } else if (!shopData.isChoosingPercent() && !shopData.isChoosingTime()) return;

        if (!StringUtils.isNumeric(message)) {
            channel.sendMessage("Please enter a valid number. Do not use letters nor any special character")
                    .complete().delete().queueAfter(5, TimeUnit.SECONDS);
            return;
        }

        if (shopData.isChoosingPercent()) {
            double percent = Double.parseDouble(message);
            if (percent < 1 || percent > 7) {
                channel.sendMessage("Please enter a valid number from 1-7")
                        .complete().delete().queueAfter(5, TimeUnit.SECONDS);
                return;
            }
            shopData.setCoinsBoosterPercent(percent);
            shopData.setChoosingPercent(false);
            shopData.setTotalPrice(shopData.getTotalPrice() + (percent * 15));
            shop.sendCoinsShopDm(user);
        } else if (shopData.isChoosingTime()) {
            double time = Double.parseDouble(message);
            shopData.setCoinsBoosterTime(time);
            shopData.setChoosingTime(false);
            shopData.setTotalPrice(shopData.getTotalPrice() + (time * 10));
            shop.sendCoinsShopDm(user);
        }
        shopData.addToCart("coins booster");
    }

    private void buyingExpBooster(String message, ShopData shopData, User user, PrivateChannel channel) {
        if (message.equalsIgnoreCase("back")) {
            if (shopData.getExpBoosterPercent() <= 0.0 && shopData.getExpBoosterTime() > 1) {
                channel.sendMessage("You need to complete the order first.").complete();
                return;
            }
            shopData.setShopping(true);
            shopData.setBuyingExpBooster(false);
            shop.sendMainShopDm(user);
            return;
        } else if (message.equalsIgnoreCase("finish")) {
            if (shopData.getExpBoosterPercent() <= 0.0 && shopData.getExpBoosterTime() > 1) {
                channel.sendMessage("You need to complete the order first.").complete();
                return;
            }
            shopData.setConfirming(true);
            shopData.setBuyingExpBooster(false);
            shop.sendConfirmationDm(user);
            return;
        } else if (message.equalsIgnoreCase("percentage")) {
            shopData.setChoosingPercent(true);
            channel.sendMessage("Enter the percentage. (Number from 1 to 7)").complete();
            return;
        } else if (message.equalsIgnoreCase("time")) {
            shopData.setChoosingTime(true);
            channel.sendMessage("Enter the time in minutes.").complete();
            return;
        } else if (!shopData.isChoosingPercent() && !shopData.isChoosingTime()) return;

        if (!StringUtils.isNumeric(message)) {
            channel.sendMessage("Please enter a valid number. Do not use letters nor any special character")
                    .complete().delete().queueAfter(5, TimeUnit.SECONDS);
            return;
        }


        if (shopData.isChoosingPercent()) {
            double percent = Double.parseDouble(message);
            if (percent < 1 || percent > 7) {
                channel.sendMessage("Please enter a valid number from 1-7")
                        .complete().delete().queueAfter(5, TimeUnit.SECONDS);
                return;
            }
            shopData.setExpBoosterPercent(percent);
            shopData.setChoosingPercent(false);
            shopData.setTotalPrice(shopData.getTotalPrice() + (percent * 15));
            shop.sendExpShopDm(user);
        } else if (shopData.isChoosingTime()) {
            double time = Double.parseDouble(message);
            shopData.setExpBoosterTime(time);
            shopData.setChoosingTime(false);
            shopData.setTotalPrice(shopData.getTotalPrice() + (time * 10));
            shop.sendExpShopDm(user);
        }
        shopData.addToCart("exp booster");
    }

}
