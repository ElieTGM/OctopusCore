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

public enum Prefix {

	/**
	 * Prefixes will go here.
	 * 
	 * Example:
	 * Prefix.NETWORK.build(); returns the prefix
	 */
	NETWORK("Network", PrefixType.NETWORK),
	SERVER("Server", PrefixType.NETWORK),
	FIND("Find", PrefixType.NETWORK),
	CLOUD("Cloud", PrefixType.NETWORK),
	PARTY("Party", PrefixType.OTHER),
	CHAT("Chat", PrefixType.NETWORK),
	PING("Ping", PrefixType.NETWORK),
	COMMAND("Command", PrefixType.OTHER),
	COMMANDS("Commands", PrefixType.OTHER),
	PHOENIX("Phoenix", PrefixType.ADVERT),
	RANK("Rank", PrefixType.OTHER),
	ACCOUNT("Account", PrefixType.OTHER),
	FISHIES("Fishies", PrefixType.OTHER),
	TELEPORT("Teleport", PrefixType.OTHER),
	GAMEMODE("Gamemode", PrefixType.OTHER),
	MESSAGE("Message", PrefixType.OTHER),
	RELOAD("Reload", PrefixType.NETWORK),
	OSWALD("Oswald", PrefixType.OTHER),
	FUN("Fun", PrefixType.OTHER),
	NONE("", PrefixType.ADVERT);
	
	public String t;
	public PrefixType pT;
	
	/**
	 * Initializes the Prefix class
	 * @param title
	 * @param prefixType
	 */
	private Prefix(String title, PrefixType prefixType) {
		this.t = title;
		this.pT = prefixType;
	}
	
	/**
	 * Returns the title
	 * @return
	 */
	public String getTitle() {
		return t;
	}
	
	/**
	 * Returns the PrefixType
	 * @return
	 */
	public PrefixType getPrefixType() {
		return pT;
	}
	
	/**
	 * Builds and returns the finalized Prefix
	 * @return
	 */
	public String build() {
		if(this == Prefix.NONE) {
			return "" + pT.getColor();
		} else {
			return pT.getColor() + t + ColoredUtils.colorizeMessage("&c > ") + ColoredUtils.colorizeMessage("&7");
		}
	}

}
