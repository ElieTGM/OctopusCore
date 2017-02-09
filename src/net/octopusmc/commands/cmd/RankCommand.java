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

import net.octopusmc.OctoCore;
import net.octopusmc.commands.OctopusCommand;
import net.octopusmc.language.messages.PredefinedMessages;
import net.octopusmc.player.OctopusPlayer;
import net.octopusmc.rank.Rank;
import net.octopusmc.rank.RankManager;
import org.bukkit.entity.Player;

public class RankCommand extends OctopusCommand {

    @Override
    public String name() {
        return "rank";
    }

    @Override
    public String desc() {
        return "Checks or sets the player's rank";
    }

    @Override
    public Rank rankNeeded() {
        return Rank.ADMIN;
    }

    @Override
    public void execute(Player player, OctopusPlayer octopusPlayer, String[] args) {

        if (args.length > 0) {

            if(args.length > 1) {

                if(args.length > 2) {

                    PredefinedMessages msg = PredefinedMessages.NOT_ENOUGH_ARGUMENTS;
                    msg.registerPlaceholder("%usage%", "/rank <player> (rank)");
                    player.sendMessage(msg.build());

                } else if (args.length == 2) {

                    String name = args[0];

                    if(name != null) {

                        OctopusPlayer oP = new OctopusPlayer(name);

                        if(oP.existsInDatabase()) {

                            if(Rank.valueOf(args[1].toUpperCase()) != null) {
                                oP.setRank(Rank.valueOf(args[1].toUpperCase()));

                                PredefinedMessages msg = PredefinedMessages.RANK_COMMAND_SET;
                                msg.registerPlaceholder("%username%", name);
                                msg.registerPlaceholder("%rank%", OctoCore.getRankManager().getPrefix(oP.getRank()));

                                player.sendMessage(msg.build());

                                RankManager.updateTagToAll();

                            } else {
                                PredefinedMessages msg = PredefinedMessages.INVALID_RANK;
                                msg.registerPlaceholder("%rank%", args[1]);

                                player.sendMessage(msg.build());
                            }

                        } else {
                            PredefinedMessages msg = PredefinedMessages.PLAYER_NOT_FOUND;
                            msg.registerPlaceholder("%username%", name);

                            player.sendMessage(msg.build());
                        }

                    }

                }

            } else {

                PredefinedMessages msg = PredefinedMessages.NOT_ENOUGH_ARGUMENTS;
                msg.registerPlaceholder("%usage%", "/rank <player> (rank)");
                player.sendMessage(msg.build());

            }

        } else {

            PredefinedMessages msg = PredefinedMessages.NOT_ENOUGH_ARGUMENTS;
            msg.registerPlaceholder("%usage%", "/rank <player> (rank)");
            player.sendMessage(msg.build());

        }
    }

    @Override
    public boolean muteable() {
        return false;
    }

    @Override
    public String[] alias() {
        return new String[]{};
    }

}
