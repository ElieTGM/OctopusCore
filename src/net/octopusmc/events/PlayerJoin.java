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
package net.octopusmc.events;

import net.octopusmc.OctoCore;
import net.octopusmc.player.OctopusPlayer;
import net.octopusmc.rank.Rank;
import net.octopusmc.tab.TabAPI;
import net.octopusmc.utils.ColoredUtils;
import net.octopusmc.utils.misc.ActionBar;
import net.octopusmc.utils.misc.BlinkEffect;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scheduler.BukkitRunnable;

import com.nametag.plugin.NametagEdit;

public class PlayerJoin implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent event) {
		onPlayerLoad(event.getPlayer());
	
		OctopusPlayer oP = new OctopusPlayer(event.getPlayer().getName());
		
		if(!oP.existsInDatabase()) {
			oP.createAccount();
		}
		
		new BukkitRunnable() {
			public void run() {
				updateTab(event.getPlayer(), "");
			}
		}.runTaskLater(OctoCore.getInstance(), 10L);
	}
	
	public static void updateTab(Player player, String suffix) {
		
		OctoCore.NAMETAG_API.manager.reset(player.getName());
		
		String prefix = OctoCore.getRankManager().getPrefix(OctoCore.getRankManager().getRank(OctoCore.getOctopusPlayer(player)));
		
		if(OctoCore.getRankManager().getRank(OctoCore.getOctopusPlayer(player)) == Rank.PLAYER) {
			prefix = "&7";
		} else {
			prefix = prefix + " &7";
		}
		
		NametagEdit.getApi().setNametag(player, ColoredUtils.colorizeMessage(prefix), ColoredUtils.colorizeMessage(suffix));
		OctoCore.NAMETAG_API.handler.applyTags();
	}
	
	@EventHandler
	public void onPlayerQuit(PlayerQuitEvent event) {
		TabAPI.removeTab(event.getPlayer());
	}
	
	public static void onPlayerLoad(Player player) {
		final BlinkEffect eff = new BlinkEffect("OCTOPUS", ChatColor.WHITE, ChatColor.AQUA, ChatColor.DARK_AQUA);
		eff.next();
		
		new BukkitRunnable() {
			public void run() {
				if(player.isOnline()) {
					TabAPI.setHeaderFooter(player, 
							new String[]{ColoredUtils.colorizeMessage(eff.next()), ColoredUtils.colorizeMessage("&bPlayers > &a" + "TODO" + "&7/&a1000")},
							new String[]{ColoredUtils.colorizeMessage("&6Visit www.OctopusMC.net for forums, store & more!")});
				} else {
					this.cancel();
				}
			}
		}.runTaskTimer(OctoCore.getInstance(), 0, 3L);
		ActionBar.send(player, "&b&lOCTOPUS &8&l• &3HUB-1");
	}
	
}
