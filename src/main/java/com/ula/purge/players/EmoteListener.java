package com.ula.purge.players;

import com.ula.purge.Purge;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

public class EmoteListener implements Listener {
    private final Purge plugin;
    public EmoteListener(Purge plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        String message = event.getMessage();
        // Replace emotes with emojis
        message = message.replace(":)", "😃");
        message = message.replace(":D", "😀");
        message = message.replace(":(", "☹️");
        message = message.replace(";)", "😉");
        message = message.replace(":O", "😲");
        message = message.replace(":P", "😛");
        message = message.replace("XD", "😆");
        message = message.replace("LOL", "😂");
        message = message.replace(":thumbsup:", "👍");
        message = message.replace(":thumbsdown:", "👎");
        message = message.replace("<3", "❤️");
        message = message.replace(":star:", "⭐");
        message = message.replace(":fire:", "🔥");
        message = message.replace(":money:", "💰");

        // Additional emotes
        message = message.replace(":angry:", "😡");
        message = message.replace(":cool:", "😎");
        message = message.replace(":shocked:", "😱");
        message = message.replace(":kiss:", "😘");
        message = message.replace(":confused:", "😕");
        message = message.replace(":clap:", "👏");
        message = message.replace(":sleepy:", "😴");
        message = message.replace(":laughing:", "😄");
        message = message.replace(":sunglasses:", "😎");
        message = message.replace(":hearteyes:", "😍");
        message = message.replace(":100:", "💯");

        // Set the modified message
        event.setMessage(message);
    }
}
