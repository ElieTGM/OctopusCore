package net.octopusmc.utils.misc;

import org.bukkit.ChatColor;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class ActionBar {

	public static void send(Player p, String msg) {
		IChatBaseComponent chat = ChatSerializer.a("{\"text\":\"" + 
		
				ChatColor.translateAlternateColorCodes('&', msg) + "\"}");
			    
			    PacketPlayOutChat actionBarText = new PacketPlayOutChat(chat, (byte)2);
			    
			    PlayerConnection playerConnection = ((CraftPlayer)p).getHandle().playerConnection;
			    
			    playerConnection.sendPacket(actionBarText);
	}
}
