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
import org.bukkit.entity.Player;

public class FishiesCommand extends OctopusCommand {

    @Override
    public String name() {
        return "fishies";
    }

    @Override
    public String desc() {
        return "Manages the fishies balance for a player.";
    }

    @Override
    public String[] alias() {
        return new String[]{};
    }

    @Override
    public Rank rankNeeded() {
        return Rank.ADMIN;
    }

    @Override
    public boolean muteable() {
        return false;
    }

    @Override
    public void execute(Player player, OctopusPlayer octopusPlayer, String[] args) {
        if(args.length > 1) {
            String action = args[0];
            String pName = args[1];

            String[] availableActions = new String[]{"check", "set", "add", "take"};

            boolean found = false;

            for(String a : availableActions) {
                if(action.equalsIgnoreCase(a)) {
                    found = true;
                }
            }

            if(found) {
                if(action.equalsIgnoreCase("check")) {
                    try {
                        OctopusPlayer frP = new OctopusPlayer(pName);

                        if(frP.existsInDatabase()) {
                            PredefinedMessages msg = PredefinedMessages.FISHIES_CHECK;
                            msg.registerPlaceholder("%username%", pName);
                            msg.registerPlaceholder("%fishies%", "" + frP.getFishies());

                            player.sendMessage(msg.build());
                        } else {
                            PredefinedMessages msg = PredefinedMessages.PLAYER_NOT_FOUND;
                            msg.registerPlaceholder("%username%", pName);

                            player.sendMessage(msg.build());
                        }
                    } catch (Exception e) {
                        PredefinedMessages msg = PredefinedMessages.PLAYER_NOT_FOUND;
                        msg.registerPlaceholder("%username%", pName);

                        player.sendMessage(msg.build());
                    }
                }

                if(action.equalsIgnoreCase("set")) {
                    try {
                        OctopusPlayer frP = new OctopusPlayer(pName);

                        if(frP.existsInDatabase()) {
                            if(args.length > 2) {
                                try {
                                    String amount = args[2];

                                    frP.setFishies(Integer.parseInt(amount));

                                    PredefinedMessages msg = PredefinedMessages.FISHIES_SET;
                                    msg.registerPlaceholder("%username%", pName);
                                    msg.registerPlaceholder("%fishies%", "" + amount);

                                    player.sendMessage(msg.build());
                                } catch (Exception e) {
                                    player.sendMessage(PredefinedMessages.COMMAND_ERROR.build());
                                }
                            } else {
                                PredefinedMessages msg = PredefinedMessages.NOT_ENOUGH_ARGUMENTS;
                                msg.registerPlaceholder("%usage%", "/fishies <check/set/add/take> <player> <amount>");

                                player.sendMessage(msg.build());
                            }
                        } else {
                            PredefinedMessages msg = PredefinedMessages.PLAYER_NOT_FOUND;
                            msg.registerPlaceholder("%username%", pName);

                            player.sendMessage(msg.build());
                        }
                    } catch (Exception e) {
                        PredefinedMessages msg = PredefinedMessages.PLAYER_NOT_FOUND;
                        msg.registerPlaceholder("%username%", pName);

                        player.sendMessage(msg.build());
                    }
                }

                if(action.equalsIgnoreCase("add")) {
                    try {
                        OctopusPlayer frP = new OctopusPlayer(pName);

                        if(frP.existsInDatabase()) {
                            if(args.length > 2) {
                                try {
                                    String amount = args[2];

                                    frP.addFishies(Integer.parseInt(amount));

                                    PredefinedMessages msg = PredefinedMessages.FISHIES_ADD;
                                    msg.registerPlaceholder("%username%", pName);
                                    msg.registerPlaceholder("%fishies%", "" + amount);

                                    player.sendMessage(msg.build());
                                } catch (Exception e) {
                                    player.sendMessage(PredefinedMessages.COMMAND_ERROR.build());
                                }
                            } else {
                                PredefinedMessages msg = PredefinedMessages.NOT_ENOUGH_ARGUMENTS;
                                msg.registerPlaceholder("%usage%", "/fishies <check/set/add/take> <player> <amount>");

                                player.sendMessage(msg.build());
                            }
                        } else {
                            PredefinedMessages msg = PredefinedMessages.PLAYER_NOT_FOUND;
                            msg.registerPlaceholder("%username%", pName);

                            player.sendMessage(msg.build());
                        }
                    } catch (Exception e) {
                        PredefinedMessages msg = PredefinedMessages.PLAYER_NOT_FOUND;
                        msg.registerPlaceholder("%username%", pName);

                        player.sendMessage(msg.build());
                    }
                }

                if(action.equalsIgnoreCase("take")) {
                    try {
                        OctopusPlayer frP = new OctopusPlayer(pName);

                        if(frP.existsInDatabase()) {
                            if(args.length > 2) {
                                try {
                                    String amount = args[2];

                                    frP.subtractFishies(Integer.parseInt(amount));

                                    PredefinedMessages msg = PredefinedMessages.FISHIES_TAKE;
                                    msg.registerPlaceholder("%username%", pName);
                                    msg.registerPlaceholder("%fishies%", "" + amount);

                                    player.sendMessage(msg.build());
                                } catch (Exception e) {
                                    player.sendMessage(PredefinedMessages.COMMAND_ERROR.build());
                                }
                            } else {
                                PredefinedMessages msg = PredefinedMessages.NOT_ENOUGH_ARGUMENTS;
                                msg.registerPlaceholder("%usage%", "/fishies <check/set/add/take> <player> <amount>");

                                player.sendMessage(msg.build());
                            }
                        } else {
                            PredefinedMessages msg = PredefinedMessages.PLAYER_NOT_FOUND;
                            msg.registerPlaceholder("%username%", pName);

                            player.sendMessage(msg.build());
                        }
                    } catch (Exception e) {
                        PredefinedMessages msg = PredefinedMessages.PLAYER_NOT_FOUND;
                        msg.registerPlaceholder("%username%", pName);

                        player.sendMessage(msg.build());
                    }
                }
            } else {
                PredefinedMessages msg = PredefinedMessages.NOT_ENOUGH_ARGUMENTS;
                msg.registerPlaceholder("%usage%", "/fishies <check/set/add/take> <player>");

                player.sendMessage(msg.build());
            }
        } else {
            PredefinedMessages msg = PredefinedMessages.NOT_ENOUGH_ARGUMENTS;
            msg.registerPlaceholder("%usage%", "/fishies <check/set/add/take> <player>");

            player.sendMessage(msg.build());
        }
    }

}
