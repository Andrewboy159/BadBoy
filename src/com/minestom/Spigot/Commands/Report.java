package com.minestom.Spigot.Commands;

import com.minestom.DiscordBot.Utilities.MessageSender;
import com.minestom.Spigot.BadBoy;
import com.minestom.Spigot.Managers.LanguageManager;
import com.minestom.Spigot.Managers.MessageManager;
import com.minestom.Spigot.PrefixType;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class Report implements CommandExecutor {

    private BadBoy plugin;
    private MessageManager messageManager;
    private LanguageManager languageManager;

    public Report(BadBoy plugin, MessageManager messageManager, LanguageManager languageManager) {
        this.plugin = plugin;
        this.messageManager = messageManager;
        this.languageManager = languageManager;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String string, String[] args) {
        if (command.getName().equalsIgnoreCase("report")) {
            if (args.length <= 1) {
                messageManager.sendMessage(sender, languageManager.getMessage("mc-reports_wrongUsage"), PrefixType.REPORTS);
                return true;
            }

            if (Bukkit.getPlayer(args[0]) == null) {
                messageManager.sendMessage(sender, languageManager.getMessage("mc-playerNotOnline"), PrefixType.REPORTS);
                //return true;
            }

            StringBuilder reason = new StringBuilder();
            for (int i = 1; i < args.length; i++) {
                reason.append(args[i]).append(" ");
            }

            MessageSender.sendReportMessage(args[0], sender.getName(), reason.toString().trim(), plugin, true);
            messageManager.sendMessage(sender, languageManager.getMessage("mc-reports_sent"), PrefixType.REPORTS);
            Bukkit.getOnlinePlayers().forEach(player -> {
                if (player.hasPermission("badboy.reports.see"))
                    messageManager.sendMessage(sender, languageManager.getMessage("mc-reports_receive")
                            .replace("{reporter}", sender.getName()).replace("{reported}", args[0])
                            .replace("{reason}", reason), PrefixType.REPORTS);
            });
        }

        return true;
    }
}
