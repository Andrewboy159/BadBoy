package com.minestom.DiscordBot.Commands;

import com.minestom.DiscordBot.Utilities.Cooldown;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import org.bukkit.configuration.file.YamlConfiguration;

import java.awt.*;
import java.io.File;
import java.net.URISyntaxException;
import java.util.concurrent.TimeUnit;

public class Server extends Cooldown {

    public static void sendServerBanner(MessageChannel channel, String[] args) {

        EmbedBuilder embedBuilder = new EmbedBuilder();
        if (!onCooldown("server")) {
            startCooldown("server", 5);
            embedBuilder.setImage("http://status.mclive.eu/%20/" +
                    (args.length != 2 ? getServerIp() : args[1]) + "/banner.png");
            channel.sendMessage(embedBuilder.build()).queue();

        } else {
            embedBuilder.setColor(Color.BLUE);
            embedBuilder.setDescription("You can't use this command right now. Please wait "
                    + getSecondsLeft("server") + " seconds...");
            channel.sendMessage(embedBuilder.build()).complete().delete().queueAfter(3, TimeUnit.SECONDS);
        }
    }

    private static File file;

    private static String getServerIp() {

        try {
            file = new File(Server.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().replace(".jar", ""), "discordConfig.yml");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }

        YamlConfiguration configuration = new YamlConfiguration();
        if (file.exists()) {
            try {
                configuration.load(file);
            } catch (Exception e) {
                e.printStackTrace();
            }
            return configuration.getString("defaultIp");
        }
        return "play.hypixel.net";
    }

}
