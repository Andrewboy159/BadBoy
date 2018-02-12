package com.minestom.Spigot.Integrations;

import com.minestom.Discord.Utilities.MessageSender;
import com.minestom.Spigot.BadBoy;
import com.vexsoftware.votifier.model.Vote;
import com.vexsoftware.votifier.model.VotifierEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

public class Votifier implements Listener {

    private MessageSender messageSender;

    public Votifier(MessageSender messageSender){
        this.messageSender = messageSender;
    }


    @EventHandler(priority= EventPriority.NORMAL)
    public void onVote(VotifierEvent event) {
        Vote vote = event.getVote();
        messageSender.sendVoteMessage(vote.getUsername(), vote.getServiceName());
    }

}
