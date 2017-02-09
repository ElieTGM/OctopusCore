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
package net.octopusmc.commands.cmd;

import net.octopusmc.commands.OctopusCommand;
import net.octopusmc.language.messages.PredefinedMessages;
import net.octopusmc.player.OctopusPlayer;
import net.octopusmc.rank.Rank;
import net.octopusmc.utils.ColoredUtils;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class ClearChatCommand extends OctopusCommand {

	/**
	 * Take this as an example class. To make an OctopusCommand let's first name the command:
	 * 
	 * We'll name it cc (clearchat).
	 * 
	 */
    @Override
    public String name() {
        return "cc";
    }

    /**
     * We need to add a description, why are we using it?
     */
    @Override
    public String desc() {
        return "Clears the server chat dialog.";
    }

    /**
     * Any aliases? ClearChat it is!
     */
    @Override
    public String[] alias() {
        return new String[]{"clearchat"};
    }

    /**
     * What's the rank needed to execute the command? Rank.MODERATOR will allow Moderators + to execute the command.
     * 
     */
    @Override
    public Rank rankNeeded() {
        return Rank.MODERATOR;
    }

    /**
     * Do we mute if the command is spammed?
     */
    @Override
    public boolean muteable() {
        return true;
    }

    /**
     * Let's execute the command!
     */
    @Override
    public void execute(Player player, OctopusPlayer octopusPlayer, String[] args) {

        for(int i=0; i<100; i++) {
            Bukkit.broadcastMessage(ColoredUtils.colorizeMessage("&b"));
        }

        PredefinedMessages msg = PredefinedMessages.CHAT_CLEARED;
        msg.registerPlaceholder("%username%", player.getName());

        Bukkit.broadcastMessage(msg.build());

    }
}
