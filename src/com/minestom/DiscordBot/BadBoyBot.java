package com.minestom.DiscordBot;

import com.minestom.DiscordBot.Listener.MessageEvent;
import com.minestom.Spigot.BadBoy;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

import java.util.Arrays;
import java.util.List;

public class BadBoyBot {

    public static String prefix;
    public static List<String> fortune;
    public static JDA api;

    public static void main(String token, BadBoy plugin) throws Exception {
        prefix = plugin.getConfig().getString("CommandPrefix");
        fortune = Arrays.asList("I don't know...", "Yeah, 100%", "No doubt", "I don't think so...", "One of my gods says \"NO\" ");

        api = new JDABuilder(AccountType.BOT).setToken(token).buildAsync();
        api.addEventListener(new MessageEvent(plugin));

        api.getPresence().setGame(Game.of(Game.GameType.valueOf(plugin.getDiscordConfigString("botStatus.state")),
                plugin.getDiscordConfigString("botStatus.what")));

    }
}
