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
package net.octopusmc.events.custom;

import net.octopusmc.player.OctopusPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class PlayerChatEvent extends Event implements Cancellable {

    /**
     * This event will be called whenever an OctopusPlayer sends a message
     * to the server.
     *
     * Note: Using event.setCancelled(true); will cancel the event altogether.
     * This will NOT send a player any message saying it was cancelled.
     */

    private static final HandlerList handlers = new HandlerList();

    private String m;
    private String lM;
    private Player bP;
    private OctopusPlayer oP;

    private boolean cancelled;

    /**
     * Initializes the PlayerChatEvent
     * @param message
     * @param lastMessage
     * @param bukkitPlayer
     * @param octopusPlayer
     */
    public PlayerChatEvent(String message, String lastMessage, Player bukkitPlayer, OctopusPlayer octopusPlayer) {
        this.m = message;
        this.lM = lastMessage;
        this.bP = bukkitPlayer;
        this.oP = octopusPlayer;
    }

    /**
     * Returns the Player who sent it
     * @return
     */
    public Player getBukkitPlayer() {
        return bP;
    }

    /**
     * Returns the OctoPlayer who sent it.
     * @return
     */
    public OctopusPlayer getPlayer() { return oP; }

    /**
     * Returns the message sent to the server
     * @return
     */
    public String getMessage() {
        return m;
    }

    /**
     * Returns if the FrostedPlayer has messages before
     * @return
     */
    public boolean hasLastMessage() {
        if(lM.isEmpty()) {
            return false;
        } else {
            return true;
        }
    }

    /**
     * Returns the last message sent bu the FrostedPlayer
     * @return
     */
    public String getLastMessage() {
        return lM;
    }

    /**
     * Changes the message sent by the server
     * (this will change the message in the chat)
     * @param message
     */
    public void setMessage(String message) {
        m = message;
    }

    /**
     * Returns if the event was cancelled
     */
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets if the event is cancelled
     * (this can cancel the chat)
     */
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }

    /**
     * Returns the list of Handlers
     * (server-side)
     */
    public HandlerList getHandlers() {
        return handlers;
    }

    /**
     * Returns the list of Handlers
     * (server-side)
     * @return
     */
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
