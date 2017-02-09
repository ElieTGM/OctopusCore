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
package net.octopusmc;

import net.octopusmc.chest.ItemCreator;
import net.octopusmc.chest.SkullCreator;
import net.octopusmc.commands.CommandManager;
import net.octopusmc.commands.OctopusPluginCommandManager;
import net.octopusmc.events.ChatListener;
import net.octopusmc.language.LanguageManager;
import net.octopusmc.player.OctopusPlayer;
import net.octopusmc.rank.RankManager;
import net.octopusmc.sql.SQLConnection;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the main starting class for OctoCore as it extends JavaPlugin.
 * You are not allowed to touch this class before taking permission from JosephGP or ElieTGM.
 * Copyright Disclaimer above package, under the law of the USA, Lebanon and Iraq.
 *
 * The class is well documented, if you encounter any problems PM ElieTGM / JosephGP.
 */

public class OctoCore extends JavaPlugin {

    /**
     * These following two methods are instances to get main methods from the main class extending java plugin.
     *
     * These pieces of code do not appear in the javadocs, only you browsing the repository can see it.
     * To be able to get main instances properties do OctoCore#getInstance()
     */
    private static OctoCore instance;
    public static OctoCore getInstance() {return instance;}

    /**
     * These following two methods are instances to get main methods from the main SQL connection class.
     *
     * These pieces of code do not appear in the javadocs, only you browsing the repository can see it.
     * To be able to get main instances properties do OctoCore#getMainSQLConnection()
     */
    private static SQLConnection sqlConnection = null;
    public static SQLConnection getMainSQLConnection() {return sqlConnection; }

    /**
     * These following two methods are instances to get main methods from the main RankManager class.
     *
     * These pieces of code do not appear in the javadocs, only you browsing the repository can see it.
     * To be able to get main instances properties do OctoCore#getRankManager()
     */
    private static RankManager rankManager = null;
    public static RankManager getRankManager() {return rankManager; }

    /**
     * These following two methods are instances to get main methods from the main LanguageManager class.
     *
     * These pieces of code do not appear in the javadocs, only you browsing the repository can see it.
     * To be able to get main instances properties do OctoCore#getLanguageManager()
     */
    private static LanguageManager languageManager = null;
    public static LanguageManager getLanguageManager() {return languageManager; }

    /**
     * These following two methods are instances to get main methods from the Chest GUI ItemCreator and Skull Creator
     * class.
     *
     * These pieces of code do not appear in the javadocs, only you browsing the repository can see it.
     * To be able to get main instances properties do OctoCore#getItemCreator() // OctoCore#getSkullCreator()
     */
    public static ItemCreator getItemCreator() { return new ItemCreator(); }
    public static SkullCreator getSkullCreator() { return new SkullCreator(); }

    /**
     * You can use this method (OctoCore{@link #getOctopusPlayer(Player)}) to get an instance of an OctopusPlayer.
     * This will get a Player object, gets it's name and transforms it to an OctopusPlayer instance.
     * @param player object
     * @return OctopusPlayer
     */
    public static OctopusPlayer getOctopusPlayer(Player player) {return new OctopusPlayer(player.getName());}

    /**
     * These following two methods are instances to get the main methods for the Octopus Command Management class.
     * 
     * You can use {@link OctoCore#getCommandManager()} to get the command manager instance.
     * 
     * The third static List is a list with all the cmdManagers, to be explained in the {@link net.octopusmc.commands.CommandManager} 
     * class. This class will create multiple commands depending on multiple Command Management installings.
     * 
     */
    private static CommandManager commandManager = null;
    public static CommandManager getCommandManager() {return commandManager; }
    private static List<OctopusPluginCommandManager> cmdManagers = new ArrayList<OctopusPluginCommandManager>();

    /**
     * The main onEnable() method of the OctoCore java plugin. Please do not touch this as changes to the Core are only
     * made when there is a global vote on it. This shouldn't be changed, as only ElieTGM and JosephGP have the rights
     * to do so.
     */
    @Override
    public void onEnable() {

    	/**
    	 * Let's initialize the instance with these so we could use getInstance();
    	 */
        instance = this;

        /**
         * Let's open up a new SQLConnection with the credentials and execute the openConnection(); operation, to create the SQL
         * database and such.
         */
        sqlConnection = new SQLConnection(instance, "localhost", "3306", "OctopusCore", "root", "D6rASGJO6HvMSnWtT0IP");
        sqlConnection.openConnection();

        /**
         * Let's initialize the RankManager class so we could use getRankManager(); and get multiple rank methods from the class 
         * itself.
         */
        rankManager = new RankManager();

        /**
         * Let's initialize the LanguageManager class so we could use getLanguageManager(); and get multiple language methods from the class 
         * itself.
         */
        languageManager = new LanguageManager();

        /**
         * Let's initialize the CommandManager class so we could use getCommandManager(); and get multiple command management methods from the class 
         * itself.
         */
        commandManager = new CommandManager();

        /**
         * Let's register our CUSTOM PlayerChatEvent class. It SHOULD be used in every event you invoke or need while using the chat event.
         * The documentation is within the PlayerChatEvent class.
         */
        getServer().getPluginManager().registerEvents(new ChatListener(), this);
    }

    /**
     * The onDisable() event will disable every method or function currently working inside the OctopusCore plugin!
     */
    @Override
    public void onDisable() {

    	/**
    	 * Let's close the SQL connection, we don't want memory leaks; do we?
    	 */
        sqlConnection.closeConnection();

        /**
         * Let's disable every commandManager, that's because if OctopusCore disables, every sub-module will be disabled too!
         */
        for(OctopusPluginCommandManager manager : cmdManagers) {
            manager.onDisable();
        }
    }

    /**
     * You can register an OctopusPluginCommandManager class in every module you accomplish. Those commands will be only available when your
     * module is enabled by default. A documentation on how to make an OctopusPluginCommandManager class is available in the CommandManager
     * class itself!
     * 
     * @param commandManager
     */
    public static void registerCommandManager(OctopusPluginCommandManager commandManager) {
        cmdManagers.add(commandManager);

        commandManager.onEnable();
    }
    
}
