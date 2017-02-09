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
package net.octopusmc.rank;

import net.octopusmc.OctoCore;
import net.octopusmc.player.OctopusPlayer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.ResultSet;
import java.sql.SQLException;

public class RankManager implements IRankManager {

    @Override
    public Rank getRank(OctopusPlayer player) {
        Rank res = null;
        ResultSet result = OctoCore.getMainSQLConnection().executeQuery("SELECT * FROM `Account` WHERE playername='" + player.getUsername() + "'", false);
        try {
            if (result.next()) {
                for (Rank r : Rank.values()) {
                    if (r.toString().toLowerCase().equalsIgnoreCase(result.getString("rank"))) {
                        res = r;
                    }
                }
            }
        } catch (SQLException e) {
            res = Rank.PLAYER;
        }
        return res;
    }

    @Override
    public void setRank(OctopusPlayer player, Rank rank) {
        String rankString = rank.toString().toLowerCase();
        rankString = "" + Character.toUpperCase(rankString.charAt(0)) + rankString.substring(1, rankString.length());

        OctoCore.getMainSQLConnection().executeUpdate("UPDATE `Account` SET rank='" + rankString + "' WHERE playername='" + player.getUsername() + "'");
    }

    @Override
    public String getPrefix(Rank rank) {
        String prefix = "";

        if (rank == Rank.PLAYER) {
            return "&7";
        }
        if (rank == Rank.VIP) {
            return "&6&lVIP";
        }
        if (rank == Rank.ELITE) {
            return "&e&lELITE";
        }
        if (rank == Rank.LEGEND) {
            return "&5&lLEGEND";
        }
        if (rank == Rank.YOUTUBE) {
            return "&c&lYOUTUBE";
        }
        if (rank == Rank.TWITCH) {
            return "&5&lTWITCH";
        }
        if (rank == Rank.BUILDER) {
            return "&d&lBUILDER";
        }
        if (rank == Rank.HELPER) {
            return "&3&lHELPER";
        }
        if (rank == Rank.MODERATOR) {
            return "&9&lMOD";
        }
        if (rank == Rank.ADMIN) {
            return "&c&lADMIN";
        }
        if (rank == Rank.DEV) {
            return "&e&LDEV";
        }
        if (rank == Rank.OWNER) {
            return "&4&lOWNER";
        }

        return prefix;
    }

    /**
     * Updates tag and tab to all players.
     */
    public static void updateTagToAll() {

        for(Player all : Bukkit.getOnlinePlayers()) {

            //TODO

        }
    }

}
