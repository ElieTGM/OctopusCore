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

import java.util.List;

import net.octopusmc.utils.ColoredUtils;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.PotionMeta;

public class ItemCreator {

	public ItemStack createItem(Material type, int amount, int data, String name, List<String> lore) {
		ItemStack item = new ItemStack(type, 1, (short) data);
		ItemMeta meta = item.getItemMeta();
		
		item.setAmount(amount);
		
		meta.setDisplayName(ColoredUtils.colorizeMessage(name));
		meta.setLore(ColoredUtils.colorizeList(lore));
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack createItemPotion(Material type, int amount, int data, String name, List<String> lore) {
		ItemStack item = new ItemStack(type, 1, (short) data);
		PotionMeta meta = (PotionMeta) item.getItemMeta();
		
		meta.clearCustomEffects();
		
		item.setAmount(amount);
		
		meta.setDisplayName(ColoredUtils.colorizeMessage(name));
		meta.setLore(ColoredUtils.colorizeList(lore));
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack createItem(Material type, int amount, int data, String name) {
		ItemStack item = new ItemStack(type, 1, (short) data);
		ItemMeta meta = item.getItemMeta();
		
		item.setAmount(amount);
		
		meta.setDisplayName(ColoredUtils.colorizeMessage(name));
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack createArmour(Material type, int amount, Color color, String name) {
		ItemStack item = new ItemStack(type, 1, (short) 0);
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		
		item.setAmount(amount);
		
		meta.setDisplayName(ColoredUtils.colorizeMessage(name));
		
		meta.setColor(color);
		
		item.setItemMeta(meta);
		
		return item;
	}
	
	public ItemStack createArmour(Material type, int amount, Color color, String name, List<String> lore) {
		ItemStack item = new ItemStack(type, 1, (short) 0);
		LeatherArmorMeta meta = (LeatherArmorMeta) item.getItemMeta();
		
		item.setAmount(amount);
		
		meta.setDisplayName(ColoredUtils.colorizeMessage(name));
		
		meta.setLore(ColoredUtils.colorizeList(lore));
		
		meta.setColor(color);
		
		item.setItemMeta(meta);
		
		return item;
	}
}
