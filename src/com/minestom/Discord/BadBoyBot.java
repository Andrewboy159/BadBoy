package com.minestom.Discord;

import com.minestom.Discord.Commands.Shop;
import com.minestom.Discord.Listener.DiscordToMinecraft;
import com.minestom.Discord.Listener.MessageEvent;
import com.minestom.Discord.Listener.OnReaction;
import com.minestom.Discord.Utilities.MessageSender;
import com.minestom.Discord.Data.ShopData;
import com.minestom.Spigot.BadBoy;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.User;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BadBoyBot {

    public static long uptime;
    public static String prefix;
    public static List<String> fortune;
    private Map<User, ShopData> dataMap;
    private Shop shop;
    public static JDA api;

    public void main(String token, BadBoy plugin, MessageSender messageSender) throws Exception {

        api = new JDABuilder(AccountType.BOT).setToken(token).buildAsync();
        api.getPresence().setGame(Game.of(Game.GameType.valueOf(plugin.getDiscordConfigString("botStatus.state")),
                plugin.getDiscordConfigString("botStatus.what")));

        initialize(plugin);
        registerEvents(plugin, messageSender);

    }

    public static JDA getApi() {
        return api;
    }

    private void initialize(BadBoy plugin) {
        uptime = System.currentTimeMillis();
        prefix = plugin.getConfig().getString("CommandPrefix");
        fortune = Arrays.asList("I don't know...", "Yeah, 100%", "No doubt", "I don't think so...", "One of my gods says \"NO\" ");
        this.dataMap = new HashMap<>();
        this.shop = new Shop(this);
    }

    private void registerEvents(BadBoy plugin, MessageSender messageSender) {
        api.addEventListener(new MessageEvent(plugin, this, messageSender, shop));
        api.addEventListener(new DiscordToMinecraft(plugin));
        api.addEventListener(new OnReaction(this, shop));
    }

    public Map<User, ShopData> getDataMap() {
        return dataMap;
    }
}
