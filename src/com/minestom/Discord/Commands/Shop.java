package com.minestom.Discord.Commands;

import com.minestom.Discord.BadBoyBot;
import com.minestom.Discord.Data.ShopData;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;

public class Shop {

    private BadBoyBot badBoyBot;

    public Shop(BadBoyBot badBoyBot) {
        this.badBoyBot = badBoyBot;
    }

    public void sendMainShopDm(User user) {
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(Color.CYAN);
        embedBuilder.setTitle("Welcome to the Shop");
        embedBuilder.setDescription("To buy something type the corresponding word.");
        embedBuilder.addField("Coins Boosters", "Type ***coins***", true);
        embedBuilder.addField("EXP Boosters", "Type ***exp***", true);
        embedBuilder.addField("Ranks", "Type ***ranks***", true);

        user.openPrivateChannel().complete().sendMessage(embedBuilder.build()).complete();
    }

    public void sendCoinsShopDm(User user) {
        ShopData shopData = badBoyBot.getDataMap().get(user);
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(Color.YELLOW);
        embedBuilder.setTitle("Welcome to Coins Booster Shop");
        embedBuilder.setDescription("Type **percentage** or **time** to edit either of them");
        embedBuilder.addField("Percentage", shopData.getCoinsBoosterPercent() + "%", true);
        embedBuilder.addField("Time", shopData.getCoinsBoosterTime() + " minutes", true);
        embedBuilder.addField("Shopping Cart", shopData.getTotalPrice() + "", false);
        embedBuilder.setFooter("Type *back* go back. Type *finish* to checkout", null);

        user.openPrivateChannel().complete().sendMessage(embedBuilder.build()).complete();
    }

    public void sendExpShopDm(User user) {
        ShopData shopData = badBoyBot.getDataMap().get(user);
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(Color.ORANGE);
        embedBuilder.setTitle("Welcome to Exp Booster Shop");
        embedBuilder.setDescription("Type **percentage** or **time** to edit either of them");
        embedBuilder.addField("Percentage", shopData.getExpBoosterPercent() + "%", true);
        embedBuilder.addField("Time", shopData.getExpBoosterTime() + " minutes", true);
        embedBuilder.addField("Shopping Cart", shopData.getTotalPrice() + "", false);
        embedBuilder.setFooter("Type *back* go back. Type *finish* to checkout", null);

        user.openPrivateChannel().complete().sendMessage(embedBuilder.build()).complete();
    }

    public void sendConfirmationDm(User user) {
        ShopData shopData = badBoyBot.getDataMap().get(user);
        EmbedBuilder embedBuilder = new EmbedBuilder();

        embedBuilder.setColor(Color.green);
        embedBuilder.setTitle("Do you want to continue with the purchase?");
        embedBuilder.setDescription("Type **YES** or **NO**");
        embedBuilder.addField("Shopping Cart", shopData.getCart(), false);
        embedBuilder.addField("Total Price", shopData.getTotalPrice() + "", true);
        embedBuilder.addField("Your coins", shopData.getUserCoins() + "", true);

        user.openPrivateChannel().complete().sendMessage(embedBuilder.build()).complete();
    }

}
