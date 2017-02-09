/*
 * Copyright (c) 2016, OctopusMC and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions
 * are met:
 *
 *   - Redistributions of source code must retain the above copyright
 *     notice, this list of conditions and the following disclaimer.
 *
 *   - Redistributions in binary form must reproduce the above copyright
 *     notice, this list of conditions and the following disclaimer in the
 *     documentation and/or other materials provided with the distribution.
 *
 *   - Neither the name of OctopusMC or the names of its
 *     contributors may be used to endorse or promote products derived
 *     from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS
 * IS" AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO,
 * THE IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR
 * PURPOSE ARE DISCLAIMED.  IN NO EVENT SHALL THE COPYRIGHT OWNER OR
 * CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL,
 * EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO,
 * PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR
 * PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF
 * LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
 * NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 *
 */
package net.octopusmc.events;

import net.octopusmc.OctoCore;
import net.octopusmc.events.custom.PlayerChatEvent;
import net.octopusmc.rank.Rank;
import net.octopusmc.utils.ColoredUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.util.HashMap;
import java.util.Map;

public class ChatListener implements Listener {

    /**
     * Initializes a private HashMap that stores the Player's last message
     */
    private static Map<Player, String> lastMessage = new HashMap<Player, String>();

    /**
     * Calls whenever a Player chats
     *
     * @param event
     */
    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        /**
         * Cancels the Chat Event, as we are going to create our own
         */
        event.setCancelled(true);

        Player player = event.getPlayer();
        String message = event.getMessage();
        String lastMsg = "";

        if (lastMessage.containsKey(player)) {
            lastMsg = lastMessage.get(player);
        }

        /**
         * Initializes a PlayerChatEvent and sends it to the server
         */
        PlayerChatEvent chatEvent = new PlayerChatEvent(message, lastMsg, player, OctoCore.getOctopusPlayer(player));
        OctoCore.getInstance().getServer().getPluginManager().callEvent(chatEvent);

        /**
         * Checks to see if it was cancelled
         */
        if (!chatEvent.isCancelled()) {
            /**
             * Gets the message in case it was changed during the event
             */
            String newMessage = chatEvent.getMessage();
            String prefix = OctoCore.getRankManager().getPrefix(chatEvent.getPlayer().getRank());

            /**
             * Sets the last message the player sent to the server
             */
            if (lastMessage.containsKey(player)) {
                lastMessage.remove(player);
            }

            lastMessage.put(player, newMessage);

            /**
             * Broadcasts the finalized Message to the server
             */
            if(chatEvent.getPlayer().getRank() == Rank.PLAYER) {
                Bukkit.broadcastMessage(ColoredUtils.colorizeMessage("&7" + player.getName() + " &f" + newMessage));
            } else {
                Bukkit.broadcastMessage(ColoredUtils.colorizeMessage(prefix + " &7" + player.getName() + " &f" + newMessage));
            }

        }
    }

    /**
     * Calls whenever a Player leaves the server
     *
     * @param event
     */
    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        /**
         * Removes the Player's message cache
         */
        if (lastMessage.containsKey(event.getPlayer())) {
            lastMessage.remove(event.getPlayer());
        }
    }

}