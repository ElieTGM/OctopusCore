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
package net.octopusmc.chest;

import net.octopusmc.OctoCore;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.scheduler.BukkitRunnable;

public class ChestGUI implements Listener {

	public Player p;
	public Inventory i;
	public GUICallback c;
	public boolean aC;
	public boolean iO;
	
	public ChestGUI iN;
	
	public ChestGUI(Player player, int size, String title, boolean allowClick, GUICallback callback) {
		this.p = player;
		this.i = Bukkit.createInventory(null, size, title);
		this.c = callback;
		this.aC = allowClick;
		this.iO = true;
		this.iN = this;
		
		c.callback(this, GUICallback.CallbackType.INIT, null);
		
		p.openInventory(i);
		
		OctoCore.getInstance().getServer().getPluginManager().registerEvents(this, OctoCore.getInstance());
		
		new BukkitRunnable() {
			public void run() {
				new BukkitRunnable() {
					public void run() {
						if(iO) {
							c.onSecond(iN);
						} else {
							this.cancel();
						}
					}
				}.runTaskTimer(OctoCore.getInstance(), 0, 20L);
			}
		}.runTaskLater(OctoCore.getInstance(), 10L);
	}
	
	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if(event.getWhoClicked().getName() == p.getName()) {
			try {
				if(!aC) {
					event.setCancelled(true);
				}
				
				c.callback(this, GUICallback.CallbackType.CLICK, event.getCurrentItem());
			} catch (Exception e) {
				//Nope
			}
		}
	}
	
	@EventHandler
	public void onInventoryClose(InventoryCloseEvent event) {
		if(event.getPlayer().getName() == p.getName()) {
			HandlerList.unregisterAll(this);
			
			c.callback(this, GUICallback.CallbackType.CLOSE, null);
		}
	}
}
