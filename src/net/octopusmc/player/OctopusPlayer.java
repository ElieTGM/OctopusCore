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
package net.octopusmc.player;

import net.octopusmc.OctoCore;
import net.octopusmc.language.Language;
import net.octopusmc.rank.Rank;
import net.octopusmc.sql.SQLConnection;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;

/**
 * This class is the main OctopusPlayer class. It takes a bukkit Player instance and converts it into an OctopusPlayer
 * account to get basic methods such as fishies, rank, languages and other useful meta.
 */

public class OctopusPlayer implements IOctopusPlayer {

    String playerName;

    public OctopusPlayer(String name) {
        playerName = name;
    }

    @Override
    public Player getOnlinePlayer() { return Bukkit.getPlayer(playerName); }

    @Override
    public String getUsername() {
        return playerName;
    }

    @Override
    public Rank getRank() {
        return OctoCore.getRankManager().getRank(this);
    }

    @Override
    public void setRank(Rank rank) {
        OctoCore.getRankManager().setRank(this, rank);
    }

    @Override
    public void setLanguage(Language language) { OctoCore.getLanguageManager().setLanguage(this, language); }

    @Override
    public Language getLanguage() { return OctoCore.getLanguageManager().getLanguage(this); }

    @Override
    public int getFishies() {
        int res = 0;
        ResultSet result = OctoCore.getMainSQLConnection().executeQuery("SELECT * FROM `Account` WHERE playername='" + getUsername() + "'", false);
        try {
            if(result.next()) {
                res = Integer.parseInt(result.getString("fishies"));
            }
        } catch (SQLException e) {}
        return res;
    }

    @Override
    public String getIPAddress() {
        String res = "";
        ResultSet result = OctoCore.getMainSQLConnection().executeQuery("SELECT * FROM `Account` WHERE playername='" + getUsername() + "'", false);
        try {
            if(result.next()) {
                res = result.getString("ip");
            }
        } catch (SQLException e) {}
        return res;
    }

    @Override
    public void setIPAdress(String ip) {
        OctoCore.getMainSQLConnection().executeUpdate("UPDATE `Account` SET ip='" + ip + "' WHERE playername='" + getUsername() + "'");
    }

    @Override
    public boolean existsInDatabase() {
        ResultSet result = OctoCore.getMainSQLConnection().executeQuery("SELECT * FROM `Account` WHERE playername='" + getUsername() + "'", false);
        try {
            if(result.next()) {
                return true;
            } else {
                return false;
            }
        } catch (SQLException e) {return false; }
    }

    @Override
    public void setFishies(int amount) {
        OctoCore.getMainSQLConnection().executeUpdate("UPDATE `Account` SET fishies='" + amount + "' WHERE playername='" + getUsername() + "'");
    }

    @Override
    public void addFishies(int amount) {
        int newAmount = getFishies() + amount;
        setFishies(newAmount);
    }

    @Override
    public void subtractFishies(int amount) {
        int newAmount = getFishies() - amount;
        setFishies(newAmount);
    }

    @Override
    public void setPassword(String password) {
        OctoCore.getMainSQLConnection().executeUpdate("UPDATE `Account` SET password='" + password + "' WHERE playername='" + getUsername() + "'");
    }

    @Override
    public String getPassword() {
        String res = "";
        ResultSet result = OctoCore.getMainSQLConnection().executeQuery("SELECT * FROM `Account` WHERE playername='" + getUsername() + "'", false);
        try {
            if(result.next()) {
                res = result.getString("password");
            }
        } catch (SQLException e) {}
        return res;
    }

    @Override
    public void createAccount() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy:MM:dd::HH:mm:ss");
        String joinTime = sdf.format(System.currentTimeMillis());
        try {
            PreparedStatement pS = SQLConnection.c.prepareStatement("INSERT INTO `Account` (playername, rank, fishies, firstjoined, lastseen, timeonline, ip, password, language) VALUES ('" + getUsername() + "', '" + Rank.PLAYER.toString().toLowerCase() + "', '100', '" + joinTime + "', 'now', 'null', '" + getOnlinePlayer().getAddress().toString() + "', 'null', 'null')");
            pS.execute();
        } catch (SQLException e) {
            System.out.print(e);
        }
    }

}
