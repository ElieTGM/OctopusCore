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
package net.octopusmc.commands;

import net.octopusmc.OctoCore;
import net.octopusmc.commands.cmd.*;
import net.octopusmc.language.messages.PredefinedMessages;
import net.octopusmc.player.OctopusPlayer;
import net.octopusmc.rank.Rank;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.help.HelpTopic;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CommandManager implements Listener {

    private List<OctopusCommand> activeCommands = new ArrayList<OctopusCommand>();

    public CommandManager() {
        registerCommands();
        registerListener();
    }

    /**
     * Registers the OctopusCommands for the Core.
     * <p>
     * Note: Do NOT register other plugin's commands in here!
     * <p>
     * Make a class extending to OctopusPluginCommandManager,
     * and use
     * OctoCore.registerCommandManager(OctopusPluginCommandManager commandManager);
     * <p>
     * Then, in the onEnable() method in the class, use
     * registerCommand(OctopusCommand command);
     * 
     * Check the wiki for more details if needed.
     * 
     * <p>
     * Side-note: The help menu displays the commands in the order you register
     * them in.
     */
    private void registerCommands() {

        registerCommand(new ClearChatCommand());
        registerCommand(new RankCommand());
        registerCommand(new FishiesCommand());
        registerCommand(new PingCommand());
        registerCommand(new GamemodeCommand());

    }

    private void registerListener() {
        OctoCore.getInstance().getServer().getPluginManager().registerEvents(this, OctoCore.getInstance());
    }

    public OctopusCommand[] getCommandMap() {
        return activeCommands.toArray(new OctopusCommand[activeCommands.size()]);
    }

    @EventHandler
    public void onCommandSend(PlayerCommandPreprocessEvent event) {
        boolean exists = false;

        for (OctopusCommand cmd : activeCommands) {
            if (cmd.name().equalsIgnoreCase(event.getMessage().replace("/", "").split(" ")[0])) {
                exists = true;
            }
        }

        try {
            for (HelpTopic cmdLabel : OctoCore.getInstance().getServer().getHelpMap().getHelpTopics()) {
                if (cmdLabel.getName().equalsIgnoreCase(event.getMessage().split(" ")[0])) {
                    exists = true;
                }
            }
        } catch (Exception e) {
        }

        if (!exists) {
            event.setCancelled(true);

            event.getPlayer().sendMessage(PredefinedMessages.COMMAND_NOT_FOUND.build());
        }

        if (event.getMessage().replace("/", "").split(" ")[0].equalsIgnoreCase("help")) {
            event.setCancelled(true);

            event.getPlayer().sendMessage(PredefinedMessages.LIST_OF_COMMANDS.build());

            List<OctopusCommand> usableCommands = new ArrayList<OctopusCommand>();
            for (OctopusCommand av : activeCommands) {
                Rank needed = av.rankNeeded();

                if (needed == Rank.PLAYER) {
                    Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                    if (r == Rank.PLAYER || r == Rank.VIP || r == Rank.ELITE || r == Rank.LEGEND || r == Rank.TWITCH || r == Rank.YOUTUBE || r == Rank.YOUTUBE || r == Rank.BUILDER || r == Rank.HELPER|| r == Rank.MODERATOR || r == Rank.DEV || r == Rank.ADMIN || r == Rank.OWNER) {
                        usableCommands.add(av);
                    }
                }

                if (needed == Rank.BUILDER) {

                    Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                    if(r == Rank.BUILDER || r == Rank.DEV || r == Rank.ADMIN || r == Rank.OWNER ) {

                        usableCommands.add(av);

                    }

                }

                if (needed == Rank.HELPER) {
                    Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                    if (r == Rank.HELPER || r == Rank.MODERATOR || r == Rank.DEV || r == Rank.ADMIN || r == Rank.OWNER) {
                        usableCommands.add(av);
                    }
                }

                if (needed == Rank.VIP) {
                    Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                    if (r != Rank.PLAYER) {
                        usableCommands.add(av);
                    }
                }

                if (needed == Rank.ELITE) {
                    Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                    if (r == Rank.ELITE || r == Rank.HELPER || r == Rank.MODERATOR || r == Rank.DEV || r == Rank.ADMIN || r == Rank.OWNER) {
                        usableCommands.add(av);
                    }
                }

                if (needed == Rank.LEGEND) {
                    Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                    if (r == Rank.LEGEND || r == Rank.HELPER || r == Rank.MODERATOR || r == Rank.DEV || r == Rank.ADMIN || r == Rank.OWNER) {
                        usableCommands.add(av);
                    }
                }

                if (needed == Rank.MODERATOR) {
                    Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                    if (r == Rank.MODERATOR || r == Rank.DEV || r == Rank.ADMIN || r == Rank.OWNER) {
                        usableCommands.add(av);
                    }
                }

                if (needed == Rank.YOUTUBE) {
                    Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                    if (r == Rank.YOUTUBE || r == Rank.DEV || r == Rank.ADMIN || r == Rank.OWNER) {
                        usableCommands.add(av);
                    }
                }

                if (needed == Rank.TWITCH) {
                    Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                    if (r == Rank.TWITCH || r == Rank.DEV || r == Rank.ADMIN || r == Rank.OWNER) {
                        usableCommands.add(av);
                    }
                }

                if (needed == Rank.DEV) {
                    Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                    if (r == Rank.DEV || r == Rank.ADMIN || r == Rank.OWNER) {
                        usableCommands.add(av);
                    }
                }

                if (needed == Rank.ADMIN) {
                    Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                    if (r == Rank.DEV || r == Rank.ADMIN || r == Rank.OWNER) {
                        usableCommands.add(av);
                    }
                }

                if (needed == Rank.OWNER) {
                    Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                    if (r == Rank.DEV || r == Rank.OWNER) {
                        usableCommands.add(av);
                    }
                }
            }

            for (OctopusCommand command : usableCommands) {
                if (command.muteable()) {
                    if (command.rankNeeded() == Rank.PLAYER) {
                        PredefinedMessages msg = PredefinedMessages.COMMAND_IN_LIST_MUTEABLE_PLAYER;
                        msg.registerPlaceholder("%name%", command.name());

                        event.getPlayer().sendMessage(msg.build());

                        PredefinedMessages msg2 = PredefinedMessages.COMMAND_IN_LIST_DESC;
                        msg2.registerPlaceholder("%desc%", command.desc());

                        event.getPlayer().sendMessage(msg2.build());
                    } else {
                        PredefinedMessages msg = PredefinedMessages.COMMAND_IN_LIST_MUTEABLE;
                        msg.registerPlaceholder("%name%", command.name());
                        msg.registerPlaceholder("%rank%", OctoCore.getRankManager().getPrefix(command.rankNeeded()));

                        event.getPlayer().sendMessage(msg.build());

                        PredefinedMessages msg2 = PredefinedMessages.COMMAND_IN_LIST_DESC;
                        msg2.registerPlaceholder("%desc%", command.desc());

                        event.getPlayer().sendMessage(msg2.build());
                    }
                } else {
                    if (command.rankNeeded() == Rank.PLAYER) {
                        PredefinedMessages msg = PredefinedMessages.COMMAND_IN_LIST_PLAYER;
                        msg.registerPlaceholder("%name%", command.name());

                        event.getPlayer().sendMessage(msg.build());

                        PredefinedMessages msg2 = PredefinedMessages.COMMAND_IN_LIST_DESC;
                        msg2.registerPlaceholder("%desc%", command.desc());

                        event.getPlayer().sendMessage(msg2.build());
                    } else {
                        PredefinedMessages msg = PredefinedMessages.COMMAND_IN_LIST;
                        msg.registerPlaceholder("%name%", command.name());
                        msg.registerPlaceholder("%rank%", OctoCore.getRankManager().getPrefix(command.rankNeeded()));

                        event.getPlayer().sendMessage(msg.build());

                        PredefinedMessages msg2 = PredefinedMessages.COMMAND_IN_LIST_DESC;
                        msg2.registerPlaceholder("%desc%", command.desc());

                        event.getPlayer().sendMessage(msg2.build());
                    }
                }
            }

            event.getPlayer().sendMessage(PredefinedMessages.COMMAND_LIST_MUTEABLE.build());
        }

        for (OctopusCommand command : activeCommands) {
            List<String> argu = new ArrayList<String>();

            for (String arg : command.alias()) {
                argu.add(arg);
            }

            if (command.name().equalsIgnoreCase(event.getMessage().replace("/", "").split(" ")[0]) || argu.contains(event.getMessage().replace("/", "").split(" ")[0])) {
                String[] args = event.getMessage().split(" ");
                args = Arrays.copyOfRange(args, 1, args.length);

                Rank needed = command.rankNeeded();

                if (needed == Rank.PLAYER) {
                    command.execute(event.getPlayer(), OctoCore.getOctopusPlayer(event.getPlayer()), args);
                } else {
                    if (needed == Rank.HELPER) {
                        Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                        if (r == Rank.HELPER || r == Rank.MODERATOR || r == Rank.DEV || r == Rank.ADMIN || r == Rank.OWNER) {
                            command.execute(event.getPlayer(), OctoCore.getOctopusPlayer(event.getPlayer()), args);
                        } else {
                            PredefinedMessages msg2 = PredefinedMessages.RANK_NEEDED;
                            msg2.registerPlaceholder("%rank%", OctoCore.getRankManager().getPrefix(needed));

                            event.getPlayer().sendMessage(msg2.build());
                        }
                    }

                    if (needed == Rank.VIP) {
                        Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                        if (r != Rank.PLAYER) {
                            command.execute(event.getPlayer(), OctoCore.getOctopusPlayer(event.getPlayer()), args);
                        } else {
                            PredefinedMessages msg2 = PredefinedMessages.RANK_NEEDED;
                            msg2.registerPlaceholder("%rank%", OctoCore.getRankManager().getPrefix(needed));

                            event.getPlayer().sendMessage(msg2.build());
                        }
                    }

                    if (needed == Rank.ELITE) {
                        Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                        if (r == Rank.ELITE || r == Rank.HELPER || r == Rank.MODERATOR || r == Rank.DEV || r == Rank.ADMIN || r == Rank.OWNER) {
                            command.execute(event.getPlayer(), OctoCore.getOctopusPlayer(event.getPlayer()), args);
                        } else {
                            PredefinedMessages msg2 = PredefinedMessages.RANK_NEEDED;
                            msg2.registerPlaceholder("%rank%", OctoCore.getRankManager().getPrefix(needed));

                            event.getPlayer().sendMessage(msg2.build());
                        }
                    }

                    if (needed == Rank.LEGEND) {
                        Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                        if (r == Rank.LEGEND || r == Rank.HELPER || r == Rank.MODERATOR || r == Rank.DEV || r == Rank.ADMIN || r == Rank.OWNER) {
                            command.execute(event.getPlayer(), OctoCore.getOctopusPlayer(event.getPlayer()), args);
                        } else {
                            PredefinedMessages msg2 = PredefinedMessages.RANK_NEEDED;
                            msg2.registerPlaceholder("%rank%", OctoCore.getRankManager().getPrefix(needed));

                            event.getPlayer().sendMessage(msg2.build());
                        }
                    }

                    if (needed == Rank.BUILDER) {

                        Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                        if(r == Rank.BUILDER || r == Rank.ADMIN || r == Rank.DEV || r == Rank.OWNER) {

                            command.execute(event.getPlayer(), OctoCore.getOctopusPlayer(event.getPlayer()), args);

                        } else {
                            PredefinedMessages msg2 = PredefinedMessages.RANK_NEEDED;
                            msg2.registerPlaceholder("%rank%", OctoCore.getRankManager().getPrefix(needed));

                            event.getPlayer().sendMessage(msg2.build());
                        }

                    }

                    if (needed == Rank.MODERATOR) {
                        Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                        if (r == Rank.MODERATOR || r == Rank.DEV || r == Rank.ADMIN || r == Rank.OWNER) {
                            command.execute(event.getPlayer(), OctoCore.getOctopusPlayer(event.getPlayer()), args);
                        } else {
                            PredefinedMessages msg2 = PredefinedMessages.RANK_NEEDED;
                            msg2.registerPlaceholder("%rank%", OctoCore.getRankManager().getPrefix(needed));

                            event.getPlayer().sendMessage(msg2.build());
                        }
                    }

                    if (needed == Rank.YOUTUBE) {
                        Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                        if (r == Rank.YOUTUBE || r == Rank.DEV || r == Rank.ADMIN || r == Rank.OWNER) {
                            command.execute(event.getPlayer(), OctoCore.getOctopusPlayer(event.getPlayer()), args);
                        } else {
                            PredefinedMessages msg2 = PredefinedMessages.RANK_NEEDED;
                            msg2.registerPlaceholder("%rank%", OctoCore.getRankManager().getPrefix(needed));

                            event.getPlayer().sendMessage(msg2.build());
                        }
                    }

                    if (needed == Rank.TWITCH) {
                        Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                        if (r == Rank.TWITCH || r == Rank.DEV || r == Rank.ADMIN || r == Rank.OWNER) {
                            command.execute(event.getPlayer(), OctoCore.getOctopusPlayer(event.getPlayer()), args);
                        } else {
                            PredefinedMessages msg2 = PredefinedMessages.RANK_NEEDED;
                            msg2.registerPlaceholder("%rank%", OctoCore.getRankManager().getPrefix(needed));

                            event.getPlayer().sendMessage(msg2.build());
                        }
                    }

                    if (needed == Rank.DEV) {
                        Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                        if (r == Rank.DEV || r == Rank.ADMIN || r == Rank.OWNER) {
                            command.execute(event.getPlayer(), OctoCore.getOctopusPlayer(event.getPlayer()), args);
                        } else {
                            PredefinedMessages msg2 = PredefinedMessages.RANK_NEEDED;
                            msg2.registerPlaceholder("%rank%", OctoCore.getRankManager().getPrefix(needed));

                            event.getPlayer().sendMessage(msg2.build());
                        }
                    }

                    if (needed == Rank.ADMIN) {
                        Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                        if (r == Rank.DEV || r == Rank.ADMIN || r == Rank.OWNER) {
                            command.execute(event.getPlayer(), OctoCore.getOctopusPlayer(event.getPlayer()), args);
                        } else {
                            PredefinedMessages msg2 = PredefinedMessages.RANK_NEEDED;
                            msg2.registerPlaceholder("%rank%", OctoCore.getRankManager().getPrefix(needed));

                            event.getPlayer().sendMessage(msg2.build());
                        }
                    }

                    if (needed == Rank.OWNER) {
                        Rank r = OctoCore.getRankManager().getRank(new OctopusPlayer(event.getPlayer().getName()));

                        if (r == Rank.DEV || r == Rank.OWNER) {
                            command.execute(event.getPlayer(), OctoCore.getOctopusPlayer(event.getPlayer()), args);
                        } else {
                            PredefinedMessages msg2 = PredefinedMessages.RANK_NEEDED;
                            msg2.registerPlaceholder("%rank%", OctoCore.getRankManager().getPrefix(needed));

                            event.getPlayer().sendMessage(msg2.build());
                        }
                    }
                }

                event.setCancelled(true);
            }
        }
    }

    public void registerCommand(OctopusCommand command) {
        activeCommands.add(command);
    }
}
