package com.minestom.Discord.Commands;

import com.minestom.Discord.BadBoyBot;
import com.minestom.Discord.Data.ShopData;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import org.apache.commons.lang.StringUtils;

import java.awt.*;
import java.util.Random;

public class Gamble {

    private BadBoyBot badBoyBot;

    public Gamble(BadBoyBot badBoyBot) {
        this.badBoyBot = badBoyBot;
    }

    public void gambleMoney(User user, TextChannel channel, String coin) {
        EmbedBuilder embedBuilder = new EmbedBuilder();
        embedBuilder.setAuthor(user.getName(), null, user.getAvatarUrl());

        if (!StringUtils.isNumeric(coin)) {
            embedBuilder.setColor(Color.RED);
            embedBuilder.setDescription(":x: You need to enter a valid number. :x:");
            channel.sendMessage(embedBuilder.build()).complete();
            return;
        }

        Random randomChance = new Random();
        Random randomMultiplier = new Random();

        int chance = randomChance.nextInt(100) + 1;
        double a = Integer.parseInt(coin);
        double b = (randomMultiplier.nextInt(3) + 1) * a;

        ShopData shopData = badBoyBot.getDataMap().get(user);

        if (chance <= 50) {
            embedBuilder.setColor(Color.GREEN);
            embedBuilder.setDescription(":moneybag: You have won " + b + " coins. :moneybag:");
            shopData.setUserCoins(shopData.getUserCoins() + b);
        } else {
            embedBuilder.setColor(Color.RED);
            embedBuilder.setDescription(":dizzy_face: RIP. You have lost " + a + " coins.");
            shopData.setUserCoins(shopData.getUserCoins() - a);
        }

        channel.sendMessage(embedBuilder.build()).complete();
    }

}
