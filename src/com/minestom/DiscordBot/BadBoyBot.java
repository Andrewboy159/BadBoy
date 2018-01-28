package com.minestom.DiscordBot;

import com.minestom.DiscordBot.Listener.MessageEvent;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;

import java.util.Arrays;
import java.util.List;

public class BadBoyBot {

    public final static String prefix = "-";
    public static List<String> fortune;
    public static JDA api;

    public static void main(String token) throws Exception {
        fortune = Arrays.asList("I don't know...", "Yeah, 100%", "No doubt", "I don't think so...", "One of my gods says \"NO\" ");

        api = new JDABuilder(AccountType.BOT).setToken(token).buildAsync();
        api.addEventListener(new MessageEvent());

        api.getPresence().setGame(Game.watching("PEPA PIG"));

    }
}
