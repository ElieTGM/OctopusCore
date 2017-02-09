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

import net.octopusmc.language.Language;
import net.octopusmc.rank.Rank;
import org.bukkit.entity.Player;

/**
 * This class is an interface for the OctopusPlayer class file located in the same package (net.octopusmc.player)
 * It has all the methods that will be used in the OctopusPlayer class. No changes can be made to this class without
 * the approval of higher up developers.
 *
 */
public interface IOctopusPlayer {

    /**
     * Returns the online Player version
     * @return
     */
    public Player getOnlinePlayer();

    /**
     * Returns the username of the player
     * @return
     */
    public String getUsername();

    /**
     * Returns the rank of the player
     * @return
     */
    public Rank getRank();

    /**
     * Sets the rank of the player
     * @param rank
     */
    public void setRank(Rank rank);

    /**
     * Returns the amount of fishies the player has
     * @return
     */
    public int getFishies();

    /**
     * Sets the amount of fishies the player has
     * @param amount
     */
    public void setFishies(int amount);

    /**
     * Adds to the amount of fishies the player has
     * @param amount
     */
    public void addFishies(int amount);

    /**
     * Subtracts the amount of fishies the player has
     * @param amount
     */
    public void subtractFishies(int amount);

    /**
     * Returns the IP Address in a String form
     * @return
     */
    public String getIPAddress();

    /**
     * Sets the IP Address in a String form
     * @return
     */
    public void setIPAdress(String ip);

    /**
     * Returns if the player exists in the Database
     * @return
     */
    public boolean existsInDatabase();

    /**
     * Creates an account for a newly-fresh joined player.
     * @return
     */
    public void createAccount();

    /**
     * Sets the password for a player's account
     * @return
     */
    public void setPassword(String password);

    /**
     * Gets the password for a player's account
     * @return password
     */
    public String getPassword();

    /**
     * Sets the language for a player's account
     * @return
     */
    public void setLanguage(Language language);

    /**
     * Gets the language for a player's account
     * @return language
     */
    public Language getLanguage();

}
