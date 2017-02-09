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
package net.octopusmc.language.messages;

import net.octopusmc.utils.ColoredUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public enum PredefinedMessages {

	/**
	 * Predefined Messages will go here.
	 * 
	 * Example:
	 * PredefinedMessage.COMMAND_NOT_FOUND.build(); returns the message
	 * 
	 * Placeholders Example:
	 * PredefinedMessage message = PredefinedMessage.NOT_ENOUGH_ARGUMENTS;
	 * 
	 * message.registerPlaceholder("%usage%", "/help");
	 * 
	 * message.build(); returns the message
	 */
	
	PHOENIX_SPAM_DETECTED(Prefix.PHOENIX, "Please do not send the same message in a row."),
	PHOENIX_SWEARING_DETECTED(Prefix.PHOENIX, "Please do not swear!"),
	PHOENIX_WORDS_SPAM_DETECTED(Prefix.PHOENIX, "We have detected possible spam from you. Please try to keep your messages in words."),
	PHOENIX_CAPITAL_LETTERS_DETECTED(Prefix.PHOENIX, "Please do not send messages with a lot of capital letters."),
	PHOENIX_ADVERTIZE_DETECTED(Prefix.PHOENIX, "Please do not advertise other servers."),
	PHOENIX_BLOCK_HACK_DETECTED(Prefix.PHOENIX, "We have detected possible Block-Hacking from you. You are currently being investigated."),
	PHOENIX_DISABLED(Prefix.PHOENIX, "Phoenix is now &cdisabled&7."),
	PHOENIX_ENABLED(Prefix.PHOENIX, "Phoenix is now &aenabled&7."),
	NOT_ENOUGH_ARGUMENTS(Prefix.COMMAND, "You didn't fill in all required arguments, use the command like this: %usage%."),
	COMMAND_NOT_FOUND(Prefix.COMMAND, "Command not found!"),
	COMMAND_ERROR(Prefix.COMMAND, "&cOh no! &7It looks like something went wrong with the command execution. Please check your arguments."),
	LIST_OF_COMMANDS(Prefix.COMMANDS, "List of Commands:"),
	COMMAND_IN_LIST(Prefix.NONE, "&4/%name% &7&l- %rank%"),
	COMMAND_IN_LIST_MUTEABLE(Prefix.NONE, "&4/%name% &7&l- %rank% &6&l*"),
	COMMAND_IN_LIST_PLAYER(Prefix.NONE, "&4/%name%"),
	COMMAND_IN_LIST_MUTEABLE_PLAYER(Prefix.NONE, "&4/%name% &7&l- &6&l*"),
	COMMAND_LIST_MUTEABLE(Prefix.NONE, "&6&l* &6Muteable if spammed or abused"),
	COMMAND_IN_LIST_DESC(Prefix.NONE, "&e%desc%"),
	TELEPORT_ERROR_YOURSELF(Prefix.TELEPORT, "You can't teleport to yourself."),
	TELEPORT_TO(Prefix.TELEPORT, "You have teleported &a%username1% &7to &e%username2%&7."),
	TELEPORT_TO_ONLY_SELF(Prefix.TELEPORT, "You have teleported &7to &e%username%&7."),
	TELEPORT_ALL_TO(Prefix.TELEPORT, "You have teleported &aEveryone &7to &e%username%&7."),
	FISHIES_CHECK(Prefix.FISHIES, "&a%username% &7has &e%fishies% &7fishies."),
	FISHIES_SET(Prefix.FISHIES, "&a%username%'s &7balance has been set to &e%fishies% &7fishies."),
	FISHIES_ADD(Prefix.FISHIES, "You have added &e%fishies% &7fishies to &a%username%'s &7balance."),
	FISHIES_TAKE(Prefix.FISHIES, "You have taken &e%fishies% &7fishies from &a%username%'s &7balance."),
	PLAYER_NOT_ONLINE(Prefix.SERVER, "&4%username% &7is not currently online."),
	PLAYER_NOW_OFFLIE(Prefix.MESSAGE, "&4%username% &7is now offline."),
	CHAT_CLEARED(Prefix.CHAT, "&e%username% &7has cleared the chat."),
	GAMEMODE_CHANGED(Prefix.GAMEMODE, "Set your gamemode to &e%gamemode%"),
	PING_PLAYER(Prefix.PING, "Your ping is &a%ping%ms"),
	PING_OTHERS(Prefix.PING, "&e%player%&7's ping is &a%ping%ms"),
	REPLY_NOBODY(Prefix.MESSAGE, "&7You have nobody to reply to."),
	MESSAGE_NOT_SPECIFIED(Prefix.MESSAGE, "&7Please specify a message to send to %username%!"),
	RELOAD_RELOADING(Prefix.RELOAD, "&eReloading server..."),
	RELOAD_DONE(Prefix.RELOAD, "Done."),
	RELOAD_TITLE_TOP(Prefix.NONE, "Reloading Server"),
	RELOAD_TITLE_BOTTOM(Prefix.NONE, "&7Please wait..."),
	PLAYER_NOT_FOUND(Prefix.ACCOUNT, "&4%username% &7does not have an account on OctopusMC."),
	RANK_NEEDED(Prefix.RANK, "You need %rank%&7 to execute this action."),
	RANK_COMMAND_GET(Prefix.RANK, "&a%username%'s &7rank is %rank%&7."),
	RANK_COMMAND_SET(Prefix.RANK, "&a%username%'s &7rank has been set to %rank%&7."),
	INVALID_RANK(Prefix.RANK, "%rank% &7is an &cinvalid&7 rank."),
	SHOUT_MESSAGE(Prefix.NONE, "&e&l%username% >> &7%message%");

	public Prefix p;
	public String m;
	
	public Map<String, String> pH;
	
	/**
	 * Initializes the PredefinedMessage class
	 * @param prefix
	 * @param message
	 */
	private PredefinedMessages(Prefix prefix, String message) {
		this.p = prefix;
		this.m = message;
		this.pH = new HashMap<String, String>();
	}
	
	/**
	 * Registers a placeholder for the entire Message
	 * @param placeholder
	 * @param replaceWith
	 */
	public void registerPlaceholder(String placeholder, String replaceWith) {
		pH.put(placeholder, replaceWith);
	}
	
	/**
	 * Returns the Message with or without placeholders
	 * @param usePlaceholders
	 * @return
	 */
	public String getMessage(boolean usePlaceholders) {
		if(usePlaceholders) {
			String replaced = "" + m;
			
			for(Entry<String, String> replace: pH.entrySet()) {
				replaced = replaced.replace(replace.getKey(), replace.getValue());
			}
			
			return replaced;
		} else {
			return m;
		}
	}
	
	/**
	 * Builds and returns the final message
	 * @return
	 */
	public String build() {
		return p.build() + ColoredUtils.colorizeMessage(getMessage(true));
	}

}
